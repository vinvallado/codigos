package br.ccasj.sisauc.administracao.sistema.dao;

import br.ccasj.sisauc.administracao.sistema.domain.ConfiguracaoWSSigpes;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

public interface ConfiguracaoWSSigpesDAO extends GenericEntityDAO<ConfiguracaoWSSigpes> {
	public ConfiguracaoWSSigpes obterConfiguracoesWSSigpes();

	public void salvar(ConfiguracaoWSSigpes configuracaoWSSigpes);
}
