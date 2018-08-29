package br.ccasj.sisauc.sdga.service;

import br.ccasj.sisauc.sdga.domain.CancelamentoLote;


public interface CancelarLoteService {
	
	public void cancelarLote(CancelamentoLote cancelamento);

	public void validarCancelamento(int idLote);
}
