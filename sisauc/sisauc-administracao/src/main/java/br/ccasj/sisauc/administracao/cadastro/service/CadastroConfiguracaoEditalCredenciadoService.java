package br.ccasj.sisauc.administracao.cadastro.service;

import java.io.Serializable;

import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciado;

public interface CadastroConfiguracaoEditalCredenciadoService extends Serializable{

	public void salvar(ConfiguracaoEditalCredenciado configuracaoEditalCredenciado);
	public void remover(Integer id);
	
}
