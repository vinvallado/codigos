package br.ccasj.sisauc.administracao.cadastro.service;

import java.io.Serializable;

import br.ccasj.sisauc.administracao.cadastro.domain.Medico;

public interface CadastroMedicoService extends Serializable{

	public Medico salvar(Medico medico);
	
	
}
