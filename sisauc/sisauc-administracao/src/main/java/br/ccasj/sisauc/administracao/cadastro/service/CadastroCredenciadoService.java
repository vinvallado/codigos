package br.ccasj.sisauc.administracao.cadastro.service;

import java.io.Serializable;

import br.ccasj.sisauc.administracao.cadastro.domain.Credenciado;
import br.ccasj.sisauc.framework.exception.SisaucException;

public interface CadastroCredenciadoService extends Serializable {
	
	public Credenciado salvar(Credenciado credenciado)  throws SisaucException ;

}
