package br.ccasj.sisauc.administracao.perfil.managedbean;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.autenticacao.dao.UsuarioDAO;
import br.ccasj.sisauc.autenticacao.service.CadastroUsuarioService;
import br.ccasj.sisauc.framework.context.AuthenticationContext;
import br.ccasj.sisauc.framework.domain.Usuario;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;

@Scope(value = "view")
@Service(value = "editarPerfilBean")
public class EditarPerfilBean implements Serializable {

	private static final long serialVersionUID = -6712827120154568671L;
	
	private static final String TROCAR_SENHA_DIALOG = "trocarSenhaDialog";

	@Autowired
	private UsuarioDAO usuarioDAO;
	@Autowired
	private AuthenticationContext authenticationContext;
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;

	private Usuario usuario;
	private String novaSenha;
	private String repeticaoNovaSenha;
	private String velhaSenha;

	@PostConstruct
	private void init() {
		usuario = usuarioDAO.findById(authenticationContext.getUsuarioLogado().getId());
		verificarRedirecionamento();
	}

	private void verificarRedirecionamento() {
		String parametro = ManagedBeanUtils.obterParametroRequest("motivo");
		if("senhaigual".equals(parametro)){
			ManagedBeanUtils.showDialog(TROCAR_SENHA_DIALOG);
		}
	}

	public void salvar() {
		usuario = usuarioDAO.merge(usuario);
		Mensagem.informacao("Perfil atualizado com sucesso!");
	}

	public void atualizarSenha() throws IOException {
		cadastroUsuarioService.atualizarSenha(novaSenha, velhaSenha, repeticaoNovaSenha, usuario.getId());
		Mensagem.informacao("Senha atualizada com sucesso!");
		ManagedBeanUtils.hideDialog(TROCAR_SENHA_DIALOG);
		RequestContext.getCurrentInstance().update(":editar-perfil-form:msgs");
		authenticationContext.logout();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getRepeticaoNovaSenha() {
		return repeticaoNovaSenha;
	}

	public void setRepeticaoNovaSenha(String repeticaoNovaSenha) {
		this.repeticaoNovaSenha = repeticaoNovaSenha;
	}

	public String getVelhaSenha() {
		return velhaSenha;
	}

	public void setVelhaSenha(String velhaSenha) {
		this.velhaSenha = velhaSenha;
	}

}
