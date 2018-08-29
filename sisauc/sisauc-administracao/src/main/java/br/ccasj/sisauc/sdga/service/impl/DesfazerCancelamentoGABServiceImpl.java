package br.ccasj.sisauc.sdga.service.impl;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.emissaogab.dao.GuiaApresentacaoBeneficiarioDAO;
import br.ccasj.sisauc.emissaogab.domain.EstadoGAB;
import br.ccasj.sisauc.emissaogab.domain.EstadoItemGAB;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.sdga.dao.AcaoSDGADAO;
import br.ccasj.sisauc.sdga.domain.DesfazerCancelamentoGAB;
import br.ccasj.sisauc.sdga.service.DesfazerCancelamentoGABService;

@Transactional
@Service(value = "desfazerCancelamentoGABServiceImpl")
public class DesfazerCancelamentoGABServiceImpl implements DesfazerCancelamentoGABService {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private GuiaApresentacaoBeneficiarioDAO guiaApresentacaoBeneficiarioDAO;
	@Autowired
	private AcaoSDGADAO acaoSDGADAO;
	
	@Override
	public void desfazerCancelamento(DesfazerCancelamentoGAB desfazerCancelamento) {
		GuiaApresentacaoBeneficiario gab = guiaApresentacaoBeneficiarioDAO.findById(desfazerCancelamento.getGab().getId());
		validarDesfazerCancelamento(gab.getEstado());
		gab.setEstado(EstadoGAB.EMITIDA);
		gab.setJustificativaCancelamentoGab(null);
		atualizarEstadoItensGAB(gab.getItens());
		guiaApresentacaoBeneficiarioDAO.merge(gab);
		acaoSDGADAO.merge(desfazerCancelamento);
		entityManager.flush();

	}

	private void atualizarEstadoItensGAB(Set<ItemGAB> itens) {
		for (ItemGAB itemGAB: itens){
			itemGAB.setEstadoItemGAB(EstadoItemGAB.CRIADO);
		}
	}

	private void validarDesfazerCancelamento(EstadoGAB estadoGAB) {
		if(!EstadoGAB.CANCELADA.equals(estadoGAB)){
			throw new SystemRuntimeException("Não pode ser desfeito o Cancelamento da GAB, pois a mesma não está Cancelada!");
		}
		
	}

}
