package br.ccasj.sisauc.administracao.cadastro.dao;

import br.ccasj.sisauc.administracao.cadastro.domain.Porte;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

public interface PorteDAO extends GenericEntityDAO<Porte> {

	public boolean verificarCodigoPorteExistente(Integer id, String codigo);
	
}
