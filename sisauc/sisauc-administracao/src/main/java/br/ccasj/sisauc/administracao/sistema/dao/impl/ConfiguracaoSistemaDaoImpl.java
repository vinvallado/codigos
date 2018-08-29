package br.ccasj.sisauc.administracao.sistema.dao.impl;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.administracao.sistema.dao.ConfiguracaoSistemaDao;
import br.ccasj.sisauc.administracao.sistema.domain.ConfiguracaoSistema;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@MappedSuperclass
@Transactional
@Repository(value = "ConfiguracaoSistemaDao")
@NamedQueries({ 
	@NamedQuery(name = ConfiguracaoSistemaDaoImpl.OBTER_CONFIGURACAO_SISTEMA, query = ConfiguracaoSistemaDaoImpl.OBTER_CONFIGURACAO_SISTEMA)
})
public class ConfiguracaoSistemaDaoImpl extends GenericEntityDAOImpl<ConfiguracaoSistema> implements ConfiguracaoSistemaDao {

	private static final long serialVersionUID = 6470390105615735689L;

	public static final String OBTER_CONFIGURACAO_SISTEMA = "select new ConfiguracaoSistema (id, bloquearEmissaoGab, permitirSolicitacoesSemUpload, cidadeRegional.id, cidadeRegional.nome) from ConfiguracaoSistema";
	
	@Override
	public ConfiguracaoSistema obterConfiguracaoSistema() {
		ConfiguracaoSistema result = null;
		List<ConfiguracaoSistema> resultList = entityManager.createNamedQuery(ConfiguracaoSistemaDaoImpl.OBTER_CONFIGURACAO_SISTEMA, ConfiguracaoSistema.class).getResultList();
		if (resultList == null || resultList.size() == 0) {
			result = criarNovaConfiguracao();
		} else {
			result = resultList.get(0);
		}
		return result;
	}

	private ConfiguracaoSistema criarNovaConfiguracao() {
		ConfiguracaoSistema configuracao = new ConfiguracaoSistema();
		configuracao.setBloquearEmissaoGab(false);
		configuracao = this.merge(configuracao);
		return configuracao;
	}

	
}
