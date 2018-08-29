package br.ccasj.sisauc.administracao.cadastro.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.ConfiguracaoEditalCredenciadoDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.CredenciadoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Credenciado;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ApplicationBean;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;

@Scope(value = "view")
@Service(value = "cadastroCredenciadoListaBean")
public class CadastroCredenciadoListaBean implements Serializable{

	private static final long serialVersionUID = 2433803029337632262L;

	@Autowired
	private CredenciadoDAO credenciadoDAO;
	@Autowired
	private ConfiguracaoEditalCredenciadoDAO configuracaoEditalCredenciadoDAO;
	
	private List<Credenciado> credenciados;
	private Credenciado credenciadoSelecionado;
	
	@PostConstruct
	public void init() {
		credenciados = credenciadoDAO.findAll();
	}
	
	public void selecionarCredenciadoAlterarAtivo(Credenciado credenciadoEdicao){
		credenciadoSelecionado = credenciadoEdicao;
		ManagedBeanUtils.showDialog("mudarStatusCredenciadoDialog");
	}
	
	public void mudarStatusAtivoCredenciado(){
		if(credenciadoSelecionado.isAtivo()){
			configuracaoEditalCredenciadoDAO.verificarSeEditalPossuiCredenciado(credenciadoSelecionado.getId());
		}
		credenciadoDAO.definirStatusAtivoCredenciado(credenciadoSelecionado.getId(), !credenciadoSelecionado.isAtivo());
		ManagedBeanUtils.obter("applicationBean", ApplicationBean.class).initConfiguracoes();
		credenciados = credenciadoDAO.findAll();
		String message = "Credenciado status com sucesso";
		Mensagem.informacao(message.replace("status", credenciadoSelecionado.isAtivo() ? "desativado" : "ativado"));
		ManagedBeanUtils.hideDialog("mudarStatusCredenciadoDialog");
	}

	public List<Credenciado> getCredenciados() {
		return credenciados;
	}

	public void setCredenciados(List<Credenciado> credenciados) {
		this.credenciados = credenciados;
	}

	public Credenciado getCredenciadoSelecionado() {
		return credenciadoSelecionado;
	}

	public void setCredenciadoSelecionado(Credenciado credenciadoSelecionado) {
		this.credenciadoSelecionado = credenciadoSelecionado;
	}
	
}
