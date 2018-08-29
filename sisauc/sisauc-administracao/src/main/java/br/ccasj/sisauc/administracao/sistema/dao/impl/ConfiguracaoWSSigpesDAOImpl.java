package br.ccasj.sisauc.administracao.sistema.dao.impl;

import javax.persistence.MappedSuperclass;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.administracao.sistema.dao.ConfiguracaoWSSigpesDAO;
import br.ccasj.sisauc.administracao.sistema.domain.ConfiguracaoWSSigpes;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@MappedSuperclass
@Transactional
@Repository(value = "configuracaoWSSigpesDAO")
public class ConfiguracaoWSSigpesDAOImpl  extends GenericEntityDAOImpl<ConfiguracaoWSSigpes>  implements ConfiguracaoWSSigpesDAO  {
	
	private static final long serialVersionUID = -1695520238951323442L;

	public static final String LISTAR_TODOS = "from ConfiguracaoWSSigpes";

	@Override
	public ConfiguracaoWSSigpes obterConfiguracoesWSSigpes() {
		return super.findById(1);
	}

	@Override
	public void salvar(ConfiguracaoWSSigpes configuracaoWSSigpes) {
		super.merge(configuracaoWSSigpes);
	}
}
