package br.ccasj.sisauc.auditoriaretrospectiva.service;

import br.ccasj.sisauc.auditoriaretrospectiva.domain.Lote;


public interface GerenciarLoteService {
	
	public void salvar(Lote lote, boolean conformidade);

	public void informarNotaFiscalLote(Lote lote);

}
