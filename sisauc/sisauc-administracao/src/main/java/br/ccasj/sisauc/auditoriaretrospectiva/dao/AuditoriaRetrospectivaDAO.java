package br.ccasj.sisauc.auditoriaretrospectiva.dao;

import java.util.List;

import br.ccasj.sisauc.auditoriaretrospectiva.domain.AuditoriaRetrospectiva;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

public interface AuditoriaRetrospectivaDAO extends GenericEntityDAO<AuditoriaRetrospectiva> {
	
	public AuditoriaRetrospectiva obterAuditoriaComMetadadosPorId(Integer idAuditoria);
	public void delete(List<Integer> ids);
}
