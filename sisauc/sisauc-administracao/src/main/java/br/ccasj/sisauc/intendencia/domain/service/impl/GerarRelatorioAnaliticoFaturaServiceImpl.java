package br.ccasj.sisauc.intendencia.domain.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.auditoriaretrospectiva.dao.AuditoriaRetrospectivaDAO;
import br.ccasj.sisauc.auditoriaretrospectiva.dao.GerenciarLoteDAO;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.Lote;
import br.ccasj.sisauc.emissaogab.dao.ItemGABDAO;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.intendencia.domain.ParametrosRelatorioAnaliticoFatura;
import br.ccasj.sisauc.intendencia.domain.service.GerarRelatorioAnaliticoFaturaService;

@Service(value = "GerarRelatorioAnaliticoFaturaService")
public class GerarRelatorioAnaliticoFaturaServiceImpl implements GerarRelatorioAnaliticoFaturaService {
	
	@Autowired
	private GerenciarLoteDAO loteDao;
	@Autowired
	private ItemGABDAO itemGabDao;
	@Autowired
	private AuditoriaRetrospectivaDAO auditoriaRetrospectivaDAO;
	
	private List<ItemGAB> itensGAB = new ArrayList<ItemGAB>();
	private Lote lote = new Lote();

	@Override
	public Lote obterLoteParaRelatorioAnaliticoFatura(Integer idLote) {
		lote = loteDao.obterLoteParaRelatorioAnaliticoFatura(idLote);
		
		itensGAB = itemGabDao.obterItensGabParaRelatorioAnaliticoFatura(idLote);
		
		Set<ItemGAB> itensParaLote = new HashSet<ItemGAB>();
		for (ItemGAB itemGAB : itensGAB) {
			itensParaLote.add(itemGAB);
			itemGAB.setAuditoriaRetrospectiva(auditoriaRetrospectivaDAO.obterAuditoriaComMetadadosPorId(itemGAB.getAuditoriaRetrospectiva().getId()));
		}
		lote.setItensGab(itensParaLote);
		return lote;
	}

	@Override
	public ParametrosRelatorioAnaliticoFatura obterValoresTotais(Lote lote){
		double somaValorApresentado = 0;
		double somaValorAuditado = 0;
		double somaValorFinal = 0;
		double somaValorTotalItem = 0;
		double somaValorMetadados = 0;
		double somaValorDesconto = 0;
		for (ItemGAB itemGAB : lote.getItensGab()) {
			somaValorApresentado += itemGAB.getAuditoriaRetrospectiva().getValorApresentado();
			somaValorAuditado += itemGAB.getAuditoriaRetrospectiva().getValorAuditado();
			somaValorFinal += itemGAB.getAuditoriaRetrospectiva().getValorFinal();
			somaValorTotalItem += itemGAB.getValorTotal();
			somaValorMetadados += itemGAB.getAuditoriaRetrospectiva().somaValoresMetadados();
			somaValorDesconto += (itemGAB.getGab().isIsento() ? 0 : (itemGAB.getAuditoriaRetrospectiva().getValorFinal() * 0.2));
		}
		return new ParametrosRelatorioAnaliticoFatura(somaValorFinal, somaValorApresentado, somaValorAuditado, somaValorTotalItem, somaValorMetadados, somaValorDesconto);
	}

	


	
}