package br.ccasj.sisauc.administracao.cadastro.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.OrganizacaoMilitarDAO;
import br.ccasj.sisauc.autenticacao.dao.UsuarioDAO;
import br.ccasj.sisauc.autenticacao.service.CadastroUsuarioService;
import br.ccasj.sisauc.framework.context.AuthenticationContext;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;
import br.ccasj.sisauc.framework.domain.Perfil;
import br.ccasj.sisauc.framework.domain.Usuario;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;

@Scope(value = "view")
@Service(value = "cadastroUsuarioFormularioBean")
public class CadastroUsuarioFormularioBean implements Serializable {

	private static final long serialVersionUID = -1415471227703831628L;

	@Autowired
	private UsuarioDAO usuarioDAO;
	@Autowired
	private OrganizacaoMilitarDAO organizacaoMilitarDAO;
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	@Autowired
	private AuthenticationContext authenticationContext;

	private Usuario usuario;
	private List<Perfil> perfis;
	private List<OrganizacaoMilitar> oms;

	@PostConstruct
	private void init() {
		usuario = obterUsuario();
		initPerfis();
		oms = organizacaoMilitarDAO.listarOMSAtivasPorRegional();
	}

	private Usuario obterUsuario() {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		return "novo".equals(id) ? new Usuario() : usuarioDAO.findById(Integer.valueOf(id));
	}
	
	private void initPerfis(){
		perfis = new ArrayList<Perfil>(Arrays.asList(Perfil.values()));
		// Removemos o perfil de usuário pois serve apenas para ajudar o spring
		// security, ou seja, esse perfil nao é mapeado no banco
		perfis.remove(Perfil.PERFIL_USUARIO);
		perfis.remove(Perfil.PERFIL_SDGA);
		
		Usuario usuarioLogado = authenticationContext.getUsuarioLogado();
		if (!usuarioLogado.getLogin().equals("admin") && !usuarioLogado.getOrganizacaoMilitar().getSigla().equals("DIRSA")){
			perfis.remove(Perfil.PERFIL_DIRSA);
		}
	}

	public void salvar() {
		cadastroUsuarioService.salvar(usuario);
		Mensagem.informacao("Usuário salvo com sucesso!");
		ManagedBeanUtils.redirecionar("/administracao/cadastro/usuario");
	}

	public void resetarSenha() {
		usuario = cadastroUsuarioService.resetarSenha(usuario.getId());
		Mensagem.informacao("Senha reiniciada com sucesso! A senha agora é igual ao nome de usuário.");
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	public List<OrganizacaoMilitar> getOms() {
		return oms;
	}

	public void setOms(List<OrganizacaoMilitar> oms) {
		this.oms = oms;
	}



}
