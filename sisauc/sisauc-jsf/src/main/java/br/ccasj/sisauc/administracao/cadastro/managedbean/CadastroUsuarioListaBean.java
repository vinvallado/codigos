package br.ccasj.sisauc.administracao.cadastro.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.autenticacao.dao.UsuarioDAO;
import br.ccasj.sisauc.autenticacao.service.CadastroUsuarioService;
import br.ccasj.sisauc.framework.domain.Perfil;
import br.ccasj.sisauc.framework.domain.Usuario;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;

@Scope(value = "view")
@Service(value = "cadastroUsuarioListaBean")
public class CadastroUsuarioListaBean implements Serializable {

	private static final long serialVersionUID = -1415471227703831628L;

	@Autowired
	private UsuarioDAO usuarioDAO;
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;

	private List<Usuario> usuarios;
	private Usuario usuario;
	private Usuario usuarioSelecionado;

	@PostConstruct
	public void init() {
		usuarios = usuarioDAO.findAll();
	}
	
	public void selectionarUsuario(Usuario usuarioEdicao){
		usuario = usuarioEdicao;
		ManagedBeanUtils.showDialog("mudarStatusUsuarioDialog");
	}
	
	public void mudarStatusAtivoUsuario(){
		cadastroUsuarioService.validarOMAoAtivarUsuario(usuario.getId());
		usuarioDAO.mudarStatusAtivoUsuario(usuario.getId(), !usuario.isAtivo());
		usuarios = usuarioDAO.findAll();
		String message = "Usuário status com sucesso";
		Mensagem.informacao(message.replace("status", usuario.isAtivo() ? "desativado" : "ativado"));
		ManagedBeanUtils.hideDialog("mudarStatusUsuarioDialog");
	}

	public void selecionarUsuarioMostrarPerfis(Usuario usuario){
		setUsuarioSelecionado(usuario);
		ManagedBeanUtils.showDialog("perfisDialog");
	}
	
	public List<Perfil> listarPerfisUsuarioSelecionado(){
		return usuarioSelecionado == null ? new ArrayList<Perfil>() : usuarioDAO.obterPerfisPorUsuario(usuarioSelecionado.getId());
	}
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

}
