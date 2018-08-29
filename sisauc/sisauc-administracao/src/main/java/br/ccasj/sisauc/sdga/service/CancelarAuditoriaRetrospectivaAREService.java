package br.ccasj.sisauc.sdga.service;

import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.ItemAR;
import br.ccasj.sisauc.sdga.domain.CancelamentoAuditoriaRetrospectiva;
import br.ccasj.sisauc.sdga.domain.CancelamentoAuditoriaRetrospectivaARE;

public interface CancelarAuditoriaRetrospectivaAREService {

	public void verificarSeAuditoriaAREPodeSerCancelada(AutorizacaoRessarcimento are);
	public void cancelarAuditoriaRetrospectivaARE(AutorizacaoRessarcimento are, String justificativa);
}
