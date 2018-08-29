package br.ccasj.sisauc.sdga.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.auditoriaretrospectiva.domain.Lote;
import br.ccasj.sisauc.emissaogab.dao.ItemGABDAO;
import br.ccasj.sisauc.emissaogab.domain.EstadoGAB;
import br.ccasj.sisauc.emissaogab.domain.EstadoItemGAB;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.sdga.dao.AcaoSDGADAO;
import br.ccasj.sisauc.sdga.domain.CancelamentoAuditoriaRetrospectiva;
import br.ccasj.sisauc.sdga.service.CancelarAuditoriaRetrospectivaService;

@Transactional
@Service(value = "cancelarAuditoriaRetrospectivaServiceImpl")
public class CancelarAuditoriaRetrospectivaServiceImpl implements CancelarAuditoriaRetrospectivaService {

	@Autowired
	private ItemGABDAO itemGABDAO;
	
	@Autowired
	private AcaoSDGADAO acaoSdgaDao;
	
	
	@Override
	public void cancelarAuditoriaRetrospectiva(CancelamentoAuditoriaRetrospectiva cancelamento, ItemGAB itemGAB) {
		itemGAB = itemGABDAO.findById(itemGAB.getId());
		verificarSeAuditoriaPodeSerCanceladaItemGAB(itemGAB);
		preencherCancelamento(cancelamento, itemGAB);
		itemGAB.setEstadoItemGAB(EstadoItemGAB.AUDITORIA_INICIADA);
		itemGAB.getGab().setEstado(EstadoGAB.EM_AUDITORIA);
		itemGABDAO.merge(itemGAB);
		acaoSdgaDao.merge(cancelamento);
	}
	
	public void verificarSeAuditoriaPodeSerCanceladaItemGAB(ItemGAB itemGAB) {
		if(!EstadoItemGAB.CONFORME.equals(itemGAB.getEstadoItemGAB())){
			throw new SystemRuntimeException("Apenas Itens de Gab no estado Conforme poderão ser cancelados.");
		}		
		Lote lote = itemGABDAO.obterLotePorItemGAB(itemGAB);
		if(lote != null){
			throw new SystemRuntimeException("A auditoria não pode ser cancelada pois o item está no lote " + lote.getNumero() + ".");			
		}			
	}

	private void preencherCancelamento (CancelamentoAuditoriaRetrospectiva cancelamento, ItemGAB itemGAB){
		cancelamento.setItemGAB(itemGAB);
		cancelamento.setValorApresentado(itemGAB.getAuditoriaRetrospectiva().getValorApresentado());
		cancelamento.setRealizado(itemGAB.getAuditoriaRetrospectiva().isRealizado());
		cancelamento.setObservacaoApresentacao(itemGAB.getAuditoriaRetrospectiva().getObservacaoApresentacao());
		cancelamento.setValorFinal(itemGAB.getAuditoriaRetrospectiva().getValorFinal());
		cancelamento.setValorAuditado(itemGAB.getAuditoriaRetrospectiva().getValorAuditado());
		cancelamento.setJustificativaValorFinal(itemGAB.getAuditoriaRetrospectiva().getJustificativaValorFinal());
		cancelamento.setJustificativaValorAuditado(itemGAB.getAuditoriaRetrospectiva().getJustificativaValorAuditado());
		cancelamento.setValores(itemGAB.getAuditoriaRetrospectiva().getValores());
	}
	
}
