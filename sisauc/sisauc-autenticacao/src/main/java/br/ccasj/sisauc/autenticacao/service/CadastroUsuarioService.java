package br.ccasj.sisauc.autenticacao.service;

import java.io.Serializable;

import br.ccasj.sisauc.framework.domain.Usuario;

public interface CadastroUsuarioService extends Serializable{
	
	public Usuario salvar(Usuario usuario);
	public Usuario resetarSenha(Integer id);
	public void atualizarSenha(String novaSenha, String velhaSenha, String repeticaoNovaSenha, Integer idUsuario);
	public void validarOMAoAtivarUsuario(Integer idUsuario);

}
