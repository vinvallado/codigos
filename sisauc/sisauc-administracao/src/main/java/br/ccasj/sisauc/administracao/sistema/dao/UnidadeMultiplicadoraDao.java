package br.ccasj.sisauc.administracao.sistema.dao;

import br.ccasj.sisauc.administracao.sistema.domain.UnidadeMultiplicadora;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

public interface UnidadeMultiplicadoraDao extends GenericEntityDAO<UnidadeMultiplicadora>{

	public UnidadeMultiplicadora obterUnidadeMultiplicadora();
	
}
