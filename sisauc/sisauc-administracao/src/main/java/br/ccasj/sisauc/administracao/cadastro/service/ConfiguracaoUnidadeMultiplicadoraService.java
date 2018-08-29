package br.ccasj.sisauc.administracao.cadastro.service;

import java.io.Serializable;

import br.ccasj.sisauc.administracao.sistema.domain.UnidadeMultiplicadora;

public interface ConfiguracaoUnidadeMultiplicadoraService extends Serializable{

	public UnidadeMultiplicadora salvar(UnidadeMultiplicadora unidadeMultiplicadora);

}
