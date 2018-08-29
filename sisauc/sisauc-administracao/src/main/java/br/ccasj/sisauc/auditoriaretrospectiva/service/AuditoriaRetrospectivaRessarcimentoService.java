package br.ccasj.sisauc.auditoriaretrospectiva.service;

import br.ccasj.sisauc.auditoriaretrospectiva.domain.AuditoriaRetrospectivaRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.ItemAR;

public interface AuditoriaRetrospectivaRessarcimentoService {

	void salvarSemFinalizar(AutorizacaoRessarcimento ar);

	void validarFinalizacao(AutorizacaoRessarcimento ar);

	void finalizar(AutorizacaoRessarcimento ar);

	void salvarAuditoriaItemAR(ItemAR itemSelecionado, AuditoriaRetrospectivaRessarcimento auditoriaRetrospectiva);

}
