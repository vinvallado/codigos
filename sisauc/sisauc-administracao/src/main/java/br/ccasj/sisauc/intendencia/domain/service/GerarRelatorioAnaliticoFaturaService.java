package br.ccasj.sisauc.intendencia.domain.service;

import br.ccasj.sisauc.auditoriaretrospectiva.domain.Lote;
import br.ccasj.sisauc.intendencia.domain.ParametrosRelatorioAnaliticoFatura;

public interface GerarRelatorioAnaliticoFaturaService {
	
	public Lote obterLoteParaRelatorioAnaliticoFatura(Integer idLote);
	public ParametrosRelatorioAnaliticoFatura obterValoresTotais(Lote lote);
}
