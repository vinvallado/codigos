package br.mil.fab.consigext.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

//import br.mil.fab.consigext.enums.PermissaoEnum;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import br.fab.mil.loginunico.login.enums.AtributosEnum;
import br.mil.fab.consigext.config.Message;
import br.mil.fab.consigext.config.ServicosSigpes;
import br.mil.fab.consigext.entity.Permissao;
import br.mil.fab.consigext.entity.PesfisComgep;
import br.mil.fab.consigext.entity.ServidorConsig;
import br.mil.fab.consigext.entity.UsuarioConsig;
import br.mil.fab.consigext.repository.PerfisComgepRepository;
import br.mil.fab.consigext.repository.PermissaoRepository;
import br.mil.fab.consigext.repository.ServidorConsigRepository;
import br.mil.fab.consigext.service.usuario.UsuarioConsigService;

@Component(value = "usrLogado")
@Scope(value = "session", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class UsuarioLogado {

	@Autowired
	private HttpSession session;

	@Autowired
	ServicosSigpes srvSigpes;
	
	@Autowired
	UsuarioConsigService usuarioConsigService;
	
	@Autowired
	PermissaoRepository permissaoRepo;
	
	@Autowired
	PerfisComgepRepository pesfisRepo;
	
	@Autowired
	ServidorConsigRepository servidorRepo;
	
	@Autowired
	Message msg;
	
	private String codOm;

	private boolean usrTemPermissao;
	
	private List<Permissao> permissoes = new ArrayList<Permissao>();
	
	private List<UsuarioConsig> usuariosConsig = new ArrayList<UsuarioConsig>();
	

	public String getZimbraUid() {
		return this.getSessionAttr().get(AtributosEnum.FABZIMBRAUID.getValor());
	}

	public String getCPF() {
		return this.getSessionAttr().get(AtributosEnum.UID.getValor());
	}

	public String getNrOrdem() {
		return this.getSessionAttr().get(AtributosEnum.FABNRORDEM.getValor());
	}

	public String getOMPrestFormatada() {
		String temp = this.getSessionAttr().get(AtributosEnum.FABOMPREST.getValor());

		return temp.replace("-", "");
	}

	public String getEmailAlternativo() {
		return this.getSessionAttr().get(AtributosEnum.FABMAILRECOVER.getValor());
	}

	public String getPostoGrad() {
		return this.getSessionAttr().get(AtributosEnum.FABPOSTOGRAD.getValor());
	}

	public String getEmail() {
		return this.getSessionAttr().get(AtributosEnum.MAIL.getValor());
	}

	public String getAcessaEmail() {
		return this.getSessionAttr().get(AtributosEnum.FABMAIL.getValor());
	}

	public String getAcessaProxy() {
		return this.getSessionAttr().get(AtributosEnum.FABPROXY.getValor());
	}

	public String getAcessaSigpes() {
		return this.getSessionAttr().get(AtributosEnum.FABSIGPES.getValor());
	}

	public String getAcessaSiloms() {
		return this.getSessionAttr().get(AtributosEnum.FABSILOMS.getValor());
	}

	public String getAcessaFabChat() {
		return this.getSessionAttr().get(AtributosEnum.FABCHAT.getValor());
	}

	public String getCn() {
		return this.getSessionAttr().get(AtributosEnum.CN.getValor());
	}

	public String getTipoPessoa() {
		return this.getSessionAttr().get(AtributosEnum.FABTIPOPESSOA.getValor());
	}

	public String getSn() {
		return this.getSessionAttr().get(AtributosEnum.SN.getValor());
	}

	public String getNomeGuerra() {
		return this.getSessionAttr().get(AtributosEnum.FABGUERRA.getValor());
	}

	public String getFabOm() {
		return this.getSessionAttr().get(AtributosEnum.FABOM.getValor());
	}

	public String getContaExpirada() {
		return this.getSessionAttr().get(AtributosEnum.FABCONTAEXPIRADA.getValor());
	}

	public String getOmPrestadora() {
		return this.getSessionAttr().get(AtributosEnum.FABOMPREST.getValor());
	}

	
	public void init() {
		loadUsuarioCorrente();
	}

	/**
	 * @throws JSONException 
	 *
	 */
	private void initCodOm() throws JSONException {
		if(this.codOm == null) {
			this.codOm = srvSigpes.buscarCodOmUsuarioLogado(this.getFabOm());
			getSessionAttr().put("codOm", this.codOm);
		}
	}

	private void initPermissaoPelaOm() throws JSONException {
		if(!this.usrTemPermissao) {
			this.usrTemPermissao = srvSigpes.buscarUsrComPerfilPelaOm("110550", this.getCodOm(), this.getCPF());
			getSessionAttr().put("temPermissaoPelaOm", String.valueOf(this.usrTemPermissao));
		}
	}

	/**
	 * Verifica se o usuário logado tem permissao pela om que faz parte
	 * @return boolean
	 * @throws JSONException 
	 */
	public boolean usrTemPermissaoPelaOm() throws JSONException {
		this.initPermissaoPelaOm();
		return Boolean.valueOf(getSessionAttr().get("temPermissaoPelaOm"));
	}


	/**
	 * Retorna o código da om do usuário logado
	 * @return String
	 * @throws JSONException
	 */
	public String getCodOm() throws JSONException {
		this.initCodOm();
		return getSessionAttr().get("codOm");
	}
	
	public String obterIP() throws  UnknownHostException{
		return InetAddress.getLocalHost().getHostAddress();  
	}

	/**
	 * @TODO alterar para buscar as permissoes do usuÃ¡rio logado 
	 */
	@SuppressWarnings("unchecked")
	public List<Permissao> getPermissoes() {
		
		if (this.getRole() == "CONSIGEXT_SERVIDOR") return null;
		
		UsuarioConsig usuarioCorrente = getUsuarioCorrente();
				
		if (permissoes.isEmpty()) {
			permissoes = usuarioCorrente.getPerfil().getPermissao();
			this.session.setAttribute("permissoes", permissoes);
		}
		
		return permissoes;
	}
	
	public boolean hasPermissao(String permissao) {
		if (isServidor()) return false;
		if (getPermissoes() == null) return false;
		
		List<String> nomePermissoes = getPermissoes().stream().map(p -> p.getNmPermissao()).collect(Collectors.toList());
		
		return nomePermissoes.contains(permissao);
	}
	
	

	public void alterarUsuarioCorrente(Long idUsuarioConsig) throws Exception {
		
		//alterar para ROLE - SERVIDOR
		if (idUsuarioConsig == null) {
			session.setAttribute("usuarioCorrente", null);
			session.setAttribute("role", "CONSIGEXT_SERVIDOR");
			permissoes = new ArrayList<Permissao>();
    		PesfisComgep pessoa = pesfisRepo.findByNrCpf(getCPF());
    		ServidorConsig servidor = servidorRepo.findByPesfis(pessoa);
    		session.setAttribute("servidor", servidor);
			return;
		}
		

		String cpfLogado = getCPF();
		
		UsuarioConsig usuario = usuarioConsigService.findById(idUsuarioConsig);
		if (usuario == null) throw new Exception(msg.get("usuariologado.funcao.alterar.usuarioinexistente"));

		String cpfNovoUsuarioCorrente = usuario.getNrCpf();
		if (!cpfLogado.equals(cpfNovoUsuarioCorrente)) throw new Exception(msg.get("usuariologado.funcao.alterar.usuarioinvalido"));
		
		session.setAttribute("usuarioCorrente", usuario);
		session.setAttribute("role", getRoleByTipoUsuario(usuario.getTpUsuario()));
		permissoes = usuario.getPerfil().getPermissao();
	}
	
	public boolean isGestor() {
		return this.session.getAttribute("role") == "CONSIGEXT_GESTOR";
	}
	
	public boolean isServidor() {
		return this.session.getAttribute("role") == "CONSIGEXT_SERVIDOR";
	}
	
	public boolean isConsig() {
		return this.session.getAttribute("role") == "CONSIGEXT_CONSIGNATARIA";
	}
	
	public boolean isPessoaVinculoFab() {
		return (this.getTipoPessoa().equals("mi") || this.getTipoPessoa().equals("ma") || this.getTipoPessoa().equals("pn"));
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getSessionAttr() {
		return (Map<String, String>) this.session.getAttribute("result");
	}

	public String getRole() {
    	if (this.session.getAttribute("role") != null) return (String) this.session.getAttribute("role");
    	
    	UsuarioConsig usuarioCorrente = this.getUsuarioCorrente();
    	String role = null;
    	
    	if (usuarioCorrente == null) {
    		role = getRoleByTipoUsuario(null);
    	}else {
    		role = getRoleByTipoUsuario(usuarioCorrente.getTpUsuario());
    	}
    	
    	this.session.setAttribute("role",role);
		return role;
	}
	
	/**
	 * @TODO acoplamento!
	 */
	public String getRoleByTipoUsuario(Long tipoUsuario) {

		if (isPessoaVinculoFab()) {
 			if (this.session.getAttribute("usuarioCorrente") != null  && tipoUsuario == 0){ //0 - GESTOR
				return "CONSIGEXT_GESTOR";
 			}else if(this.session.getAttribute("usuarioCorrente") != null  && tipoUsuario == 2) { //2 - CONSIG FAB
 				return "CONSIGEXT_CONSIGNATARIA";
 			}
 			
			return "CONSIGEXT_SERVIDOR";
		}
		
		// CIVIL EXTERNO ENT CONSIG EXTERNO
		if (this.getTipoPessoa().equals("cec")) {
			return "CONSIGEXT_CONSIGNATARIA";
		}

		return  null;
	}
	
	public String getRoleByUsuario(UsuarioConsig usuario) {
		if (isPessoaVinculoFab()) {
			if (usuario == null) return "CONSIGEXT_SERVIDOR";
			
 			if (usuario.getTpUsuario() == 0){ //0 - GESTOR
				return "CONSIGEXT_GESTOR";
 			}else { //1- consig ext || 2 - CONSIG FAB
 				return "CONSIGEXT_CONSIGNATARIA";
 			}
 			
		}
		
		// CIVIL EXTERNO ENT CONSIG EXTERNO
		if (this.getTipoPessoa().equals("cec")) {
			return "CONSIGEXT_CONSIGNATARIA";
		}

		return  null;
	}
	
	
	public String getStyle() {
		return (isConsig()) ? "/css/stylered.css" : "/css/styleblue.css";
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	
	public UsuarioConsig getUsuarioCorrente() {
		if (isServidor()) return null;
		
		if (this.session.getAttribute("usuarioCorrente") == null && !this.getRole().equals("CONSIGEXT_SERVIDOR")) {
			loadUsuarioCorrente();
		}
		
		return (UsuarioConsig) this.session.getAttribute("usuarioCorrente");
	}
	
	private void loadUsuarioCorrente() {
		if (session.getAttribute("usuarioCorrente") != null || session.getAttribute("role") == "CONSIGEXT_SERVIDOR") return;
		
		List<UsuarioConsig> usuarios = usuarioConsigService.findByNrCpf(this.getCPF());
		
		if (usuarios.isEmpty()) {
			if (isPessoaVinculoFab()) {
				session.setAttribute("role", "CONSIGEXT_SERVIDOR");
			}else {
				//Lançar Exception, pois nao existe o caso de uma pessoa sem vinculo fab (Civil Externo) nao ter usuario cadastrado no sistema
			}
			session.setAttribute("usuarioCorrente", null);
			return;
		}
		//permissoes = getPermissoes();
		UsuarioConsig usuarioMaisAtual = usuarios.get(0);
		Long idMaisAtual = usuarioMaisAtual.getId();
		boolean todosExcluidos = true;
		for(UsuarioConsig userAux: usuarios) {
			if(userAux.getStExcluido()==0)
				todosExcluidos = false;
			if(userAux.getId()>idMaisAtual) {
				idMaisAtual=userAux.getId();
				usuarioMaisAtual=userAux;
			}
		}
		session.setAttribute("usuarioCorrente", usuarioMaisAtual);
		session.setAttribute("role", getRoleByTipoUsuario(usuarioMaisAtual.getTpUsuario()));
		if(todosExcluidos && isPessoaVinculoFab())
			try {
				alterarUsuarioCorrente(null);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
	}
	
	public List<UsuarioConsig> getUsuariosConsig(){
		
		if (usuariosConsig.isEmpty())
			usuariosConsig = usuarioConsigService.findByNrCpf(this.getCPF());
		Collections.sort(usuariosConsig, new Comparator<UsuarioConsig>() {
			public int compare(UsuarioConsig self, UsuarioConsig other) {
				if(self.getStExcluido()<other.getStExcluido())
					return -1;
				if(self.getStExcluido()>other.getStExcluido())
					return 1;
				if (self.getId() < other.getId())
					return 1;
				return -1;
			}
		});
		return usuariosConsig;
	}
	public int getQuantidadeFuncoes() {
		int quantidade = 0;
		if (isPessoaVinculoFab()) ++quantidade;
		List<UsuarioConsig> usuariosConsig = getUsuariosConsig();
		for(UsuarioConsig usuarioConsig: usuariosConsig)
			if(usuarioConsig.getStExcluido()==0)
				quantidade++;
	//quantidade += getUsuariosConsig().size();
		
		return quantidade;
	}
	
	public void logout() {
		this.session.invalidate();
	}
	
	public boolean isTemPermissao(long idPermissao){
		ArrayList<Permissao> permiss = (ArrayList<Permissao>)getPermissoes();				
		return permiss.stream().filter(x->x.getId()==idPermissao).findFirst().isPresent();
	}
	
}
