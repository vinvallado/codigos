package br.ccasj.sisauc.sdga.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.emissaogab.dao.ItemGABDAO;
import br.ccasj.sisauc.emissaogab.domain.EstadoItemGAB;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.intendencia.dao.RelatorioDescontoBeneficiarioDAO;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiario;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiarioItem;
import br.ccasj.sisauc.sdga.dao.AcaoSDGADAO;
import br.ccasj.sisauc.sdga.domain.DesfazerRelatorioDescontoBeneficiario;
import br.ccasj.sisauc.sdga.service.DesfazerRelatorioDescontoBeneficiarioService;

@Transactional
@Service(value = "desfazerRelatorioDescontoBeneficiarioServiceImpl")
public class DesfazerRelatorioDescontoBeneficiarioServiceImpl implements DesfazerRelatorioDescontoBeneficiarioService {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private RelatorioDescontoBeneficiarioDAO relatorioDescontoBeneficiarioDAO;
	@Autowired
	private AcaoSDGADAO acaoSDGADAO;
	@Autowired
	private ItemGABDAO itemGABDAO;
	
	@Override
	public void desfazerRelatorioDescontoBeneficiario(DesfazerRelatorioDescontoBeneficiario desfazerRelatorioDescontoBeneficiario) {
		RelatorioDescontoBeneficiario relatorio = relatorioDescontoBeneficiarioDAO.findById(desfazerRelatorioDescontoBeneficiario.getRelatorio().getId());
		
		if(!relatorio.getItens().isEmpty()){
			List<ItemGAB> itensGab = new ArrayList<ItemGAB>();
			for (RelatorioDescontoBeneficiarioItem itemRelat : relatorio.getItens()) {
				itensGab.add(itemRelat.getItemGab());
			}
			
			itemGABDAO.atualizarEstadoItens(itensGab, EstadoItemGAB.CONFORME);
		}

		if(!relatorio.getItensIsentos().isEmpty()){
			itemGABDAO.atualizarEstadoItens(new ArrayList<ItemGAB>(relatorio.getItensIsentos()), EstadoItemGAB.CONFORME);
		}
		
		
		relatorio.setCancelado(true);	
		relatorio.setId(desfazerRelatorioDescontoBeneficiario.getRelatorio().getId());
		
		/* Linhas comentadas devido a erro de chave ao persistir na tabela de  relatorio_desconto_beneficiario - Vinicius Vallado (CDS)
			relatorio.setItens(new HashSet<RelatorioDescontoBeneficiarioItem>());
			relatorio.setItensIsentos(new HashSet<ItemGAB>());
		*/
		
		relatorioDescontoBeneficiarioDAO.merge(relatorio);
		
		acaoSDGADAO.merge(desfazerRelatorioDescontoBeneficiario);
		
		entityManager.flush();

	}

}
