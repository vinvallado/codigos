package br.ccasj.sisauc.administracao.cadastro.service;

import java.io.Serializable;

import br.ccasj.sisauc.administracao.cadastro.domain.EditalCredenciamento;

public interface CadastroEditalCredenciamentoService extends Serializable {
	
	public EditalCredenciamento salvar(EditalCredenciamento editalCredenciamento);
	public void mudarStatusAtivoCredenciado(Integer id, boolean status) ;

}
