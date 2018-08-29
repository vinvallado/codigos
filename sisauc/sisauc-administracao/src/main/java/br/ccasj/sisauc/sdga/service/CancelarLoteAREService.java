package br.ccasj.sisauc.sdga.service;

import br.ccasj.sisauc.sdga.domain.CancelamentoLoteRessarcimento;


public interface CancelarLoteAREService { 
	
	public void cancelarLoteAre(CancelamentoLoteRessarcimento cancelamentoAr);

	public void validarCancelamento(int idLote);
}
