package br.ccasj.sisauc.auditoriaretrospectiva.dao;

import java.util.List;

import br.ccasj.sisauc.auditoriaretrospectiva.domain.Motivo;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

public interface MotivoDAO extends GenericEntityDAO<Motivo> {
	
	public List<Motivo> obterListaMotivos();

	public List<Motivo> obterHabilitados();

	public List<Motivo> obterNaoHabilitados();
	
}
