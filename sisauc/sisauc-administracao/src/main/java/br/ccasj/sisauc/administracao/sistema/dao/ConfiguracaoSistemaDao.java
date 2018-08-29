package br.ccasj.sisauc.administracao.sistema.dao;

import br.ccasj.sisauc.administracao.sistema.domain.ConfiguracaoSistema;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

public interface ConfiguracaoSistemaDao extends GenericEntityDAO<ConfiguracaoSistema>{

	public ConfiguracaoSistema obterConfiguracaoSistema();
	
}
