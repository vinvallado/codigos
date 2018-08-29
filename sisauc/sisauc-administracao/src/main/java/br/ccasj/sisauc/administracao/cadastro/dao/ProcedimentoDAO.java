package br.ccasj.sisauc.administracao.cadastro.dao;

import java.util.List;

import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoBase;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

public interface ProcedimentoDAO extends GenericEntityDAO<ProcedimentoBase> {

	public List<? extends ProcedimentoBase> listarProcedimentosPorGrupo(Integer id, Class<? extends ProcedimentoBase> clazz);
	public List<? extends ProcedimentoBase> listarProcedimentosPorSubgrupo(Integer id, Class<? extends ProcedimentoBase> clazz);

}