package br.ccasj.sisauc.auditoriaretrospectiva.dao;

import br.ccasj.sisauc.auditoriaretrospectiva.domain.AuditoriaRetrospectivaRessarcimento;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

import java.util.List;

public interface AuditoriaRetrospectivaRessarcimentoDAO extends GenericEntityDAO<AuditoriaRetrospectivaRessarcimento> {
	
	public AuditoriaRetrospectivaRessarcimento obterAuditoriaComMetadadosPorId(Integer idAuditoria);
	public void delete(List<Integer> ids);
}
