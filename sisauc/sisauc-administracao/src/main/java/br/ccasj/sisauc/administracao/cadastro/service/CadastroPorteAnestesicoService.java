package br.ccasj.sisauc.administracao.cadastro.service;

import java.io.Serializable;

import br.ccasj.sisauc.administracao.cadastro.domain.PorteAnestesico;

public interface CadastroPorteAnestesicoService extends Serializable{

	public PorteAnestesico salvar(PorteAnestesico porte);
}
