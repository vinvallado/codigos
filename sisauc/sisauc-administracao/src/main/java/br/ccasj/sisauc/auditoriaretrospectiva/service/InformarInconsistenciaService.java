package br.ccasj.sisauc.auditoriaretrospectiva.service;

import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;

public interface InformarInconsistenciaService {

	public void informarInconsistencia(AutorizacaoRessarcimento ar);
}
