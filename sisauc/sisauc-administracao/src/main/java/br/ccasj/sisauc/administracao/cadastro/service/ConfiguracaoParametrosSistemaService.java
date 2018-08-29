package br.ccasj.sisauc.administracao.cadastro.service;

import java.io.Serializable;

import br.ccasj.sisauc.administracao.sistema.domain.ConfiguracaoSistema;

public interface ConfiguracaoParametrosSistemaService extends Serializable{

	public void salvar(ConfiguracaoSistema configuracaoSistema);

}
