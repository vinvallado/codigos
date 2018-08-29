package br.ccasj.sisauc.administracao.cadastro.dao;

import br.ccasj.sisauc.administracao.cadastro.domain.PorteAnestesico;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

public interface PorteAnestesicoDAO extends GenericEntityDAO<PorteAnestesico> {

	public boolean verificarCodigoPorteAnestesicoExistente(Integer id, String codigo);
	
}
