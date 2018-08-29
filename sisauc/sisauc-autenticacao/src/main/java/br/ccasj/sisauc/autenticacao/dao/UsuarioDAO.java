package br.ccasj.sisauc.autenticacao.dao;

import java.util.List;

import br.ccasj.sisauc.framework.dao.GenericEntityDAO;
import br.ccasj.sisauc.framework.domain.Perfil;
import br.ccasj.sisauc.framework.domain.Usuario;

public interface UsuarioDAO extends GenericEntityDAO<Usuario> {

	public Usuario obterPorNomeUsuario(String nomeUsuario);
	public void atualizarSenha(String novaSenha, Integer idUsuario);
	public List<Perfil> obterPerfisPorUsuario(Integer id);
	public boolean verificarUnidadeLogin(Integer id, String login);
	public void mudarStatusAtivoUsuario(Integer id, boolean b);
	public boolean verificarOmUsuario(Integer idUsuario, List<Integer> idsOms);
	public void atualizarLeuNotasVersao(Integer idUsuario);

}
