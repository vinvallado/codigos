package br.ccasj.sisauc.administracao.cadastro.service;

import java.io.Serializable;

import br.ccasj.sisauc.administracao.cadastro.domain.Porte;

public interface CadastroPorteService extends Serializable{

	public Porte salvar(Porte porte);
}
