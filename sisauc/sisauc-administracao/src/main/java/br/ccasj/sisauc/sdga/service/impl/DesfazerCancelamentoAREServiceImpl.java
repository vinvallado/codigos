package br.ccasj.sisauc.sdga.service.impl;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.emissaoar.dao.AutorizacaoRessarcimentoDAO;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.EstadoAR;
import br.ccasj.sisauc.emissaoar.domain.EstadoItemAR;
import br.ccasj.sisauc.emissaoar.domain.ItemAR;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.sdga.dao.AcaoSDGADAO;
import br.ccasj.sisauc.sdga.domain.DesfazerCancelamentoARE;
import br.ccasj.sisauc.sdga.service.DesfazerCancelamentoAREService;

@Transactional
@Service(value = "desfazerCancelamentoAREServiceImpl")
public class DesfazerCancelamentoAREServiceImpl implements DesfazerCancelamentoAREService {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private AutorizacaoRessarcimentoDAO autorizacaoRessarcimentoDAO;
	@Autowired
	private AcaoSDGADAO acaoSDGADAO;
	
	@Override
	public void desfazerCancelamento(DesfazerCancelamentoARE desfazerCancelamentoARE) {
		AutorizacaoRessarcimento are = autorizacaoRessarcimentoDAO.findById(desfazerCancelamentoARE.getAre().getId());
		validarDesfazerCancelamento(are);
		are.setEstado(EstadoAR.EMITIDA);
		are.setJustificativaCancelamentoAR(null);
		atualizarEstadoItensARE(are.getItens());
		autorizacaoRessarcimentoDAO.merge(are);
		acaoSDGADAO.merge(desfazerCancelamentoARE);
		entityManager.flush();
	}

	private void atualizarEstadoItensARE(Set<ItemAR> itens) {
		for (ItemAR itemAR: itens){
			itemAR.setEstadoItemAR(EstadoItemAR.APROVADO);
		}
	}

	private void validarDesfazerCancelamento(AutorizacaoRessarcimento are) {
		if(!EstadoAR.CANCELADA.equals(are.getEstado())){
			throw new SystemRuntimeException("Não pode ser desfeito o Cancelamento da ARE " + are.getCodigo() + " , pois a mesma não está Cancelada!");
		}
	}

}
