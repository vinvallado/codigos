package br.ccasj.sisauc.auditoriaprospectiva.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.auditoriaprospectiva.service.ApresentarGABService;
import br.ccasj.sisauc.auditoriaretrospectiva.service.AuditoriaRetrospectivaService;
import br.ccasj.sisauc.emissaogab.dao.GuiaApresentacaoBeneficiarioDAO;
import br.ccasj.sisauc.emissaogab.dao.ItemGABDAO;
import br.ccasj.sisauc.emissaogab.domain.EstadoGAB;
import br.ccasj.sisauc.emissaogab.domain.EstadoItemGAB;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;

@Transactional
@Service(value = "apresentarGABService")
public class ApresentarGABServiceServiceImpl implements ApresentarGABService {
	
	@Autowired
	private GuiaApresentacaoBeneficiarioDAO gabDAO;
	@Autowired
	private AuditoriaRetrospectivaService auditoriaRetrospectivaService;
	@Autowired
	private ItemGABDAO itemGABDAO;

	@Override
	public void apresentarGAB(GuiaApresentacaoBeneficiario gab, List<ItemGAB> itens) {
		gab = gabDAO.findById(gab.getId());
		validarGAB(gab);
		validarItens(itens);
		prepararItensParaSalvar(itens);
		gab.setEstado(EstadoGAB.APRESENTADA);
		gab.setDataApresentacao(new Date());
		gabDAO.merge(gab);
	}

	private void prepararItensParaSalvar(List<ItemGAB> itens) {
		for (ItemGAB itemGAB : itens) {
			itemGAB.setEstadoItemGAB(itemGAB.getAuditoriaRetrospectiva().isRealizado() ? EstadoItemGAB.AGUARDANDO_AUDITORIA : EstadoItemGAB.NAO_REALIZADO);
			itemGAB.setAuditoriaRetrospectiva(auditoriaRetrospectivaService.salvar(itemGAB.getAuditoriaRetrospectiva(), itemGAB));
			itemGABDAO.merge(itemGAB);
		}
	}

	private void validarItens(List<ItemGAB> itens) {
		int itensRealizados = 0;
		for (ItemGAB itemGAB : itens) {
			if(itemGAB.getAuditoriaRetrospectiva() == null)
				throw new SystemRuntimeException("Todos os itens da gab devem ser marcados com o valor apresentado ou não realizado!");
			if(itemGAB.getAuditoriaRetrospectiva().isRealizado() && 
					(itemGAB.getAuditoriaRetrospectiva().getValorApresentado() == null || itemGAB.getAuditoriaRetrospectiva().getValorApresentado() <= 0))
				throw new SystemRuntimeException("Os valores apresentados devem ser maior que R$ 0,00!");
			if(itemGAB.getAuditoriaRetrospectiva().isRealizado())
				itensRealizados++;
		}
		if(itensRealizados == 0){
			throw new SystemRuntimeException("Pelo menos um item da GAB deve ser realizado!");
		}
	}

	private void validarGAB(GuiaApresentacaoBeneficiario gab) {
		if(!EstadoGAB.EMITIDA.equals(gab.getEstado()))
			throw new SystemRuntimeException("Esta GAB não está no estado 'Emitida'!");
	}

	
	
}

