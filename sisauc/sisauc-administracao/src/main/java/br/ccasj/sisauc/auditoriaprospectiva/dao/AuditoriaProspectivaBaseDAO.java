package br.ccasj.sisauc.auditoriaprospectiva.dao;

import java.io.Serializable;
import java.util.List;

import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectivaBase;

public interface AuditoriaProspectivaBaseDAO extends Serializable {
	
	public List<AuditoriaProspectivaBase> listarAuditoriasPorBeneficiario(Integer idBeneficiario, String textoPesquisa, Class<?>... types);

}
