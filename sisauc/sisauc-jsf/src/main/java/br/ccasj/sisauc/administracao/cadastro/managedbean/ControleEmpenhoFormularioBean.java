package br.ccasj.sisauc.administracao.cadastro.managedbean;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.CredenciadoDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.EmpenhoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Credenciado;
import br.ccasj.sisauc.administracao.cadastro.domain.Empenho;
import br.ccasj.sisauc.administracao.cadastro.service.ControleEmpenhoService;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;

@Scope(value = "view")
@Service(value = "controleEmpenhoFormularioBean")
public class ControleEmpenhoFormularioBean {

	@Autowired
	private CredenciadoDAO credenciadoDAO;
	
	private List<Credenciado> credenciados;
	
	private Empenho empenho;
	
	@Autowired
	private EmpenhoDAO empenhoDAO;
	
	@Autowired
	private ControleEmpenhoService controleEmpenhoService;
	
	
	@PostConstruct
	private void init() {
		this.credenciados = credenciadoDAO.listarAtivosParaCombo();
		this.empenho = obterEmpenho();
	}


	public List<Credenciado> getCredenciados() {
		return credenciados;
	}


	public void setCredenciados(List<Credenciado> credenciados) {
		this.credenciados = credenciados;
	}


	public Empenho getEmpenho() {
		return empenho;
	}


	public void setEmpenho(Empenho empenho) {
		this.empenho = empenho;
	}
	
	public void salvar() {
		controleEmpenhoService.salvar(this.empenho);
		Mensagem.informacao("Empenho salvo com sucesso!");
		ManagedBeanUtils.redirecionar("/intendencia/empenho");
	}
	
	private Empenho obterEmpenho() {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		return id.equals("novo") ? new Empenho() : empenhoDAO.findById(Integer.valueOf(id));
	}
	
	public void finalizar() {
		//TODO Implementar a regra para finalizar após a criação da GAB completa.
		this.empenho.setFinalizado(true);
		this.salvar();
	}
	
	public void reabrir(){
		//TODO Implementar a regra para finalizar após a criação da GAB completa.
		this.empenho.setFinalizado(false);
		this.salvar();
	}
	
	public void confirmaFinalizarEmpenho(){
		ManagedBeanUtils.showDialog("finalizacaoEmpenhoDialog");
	}
	
	public void confirmaReabrirEmpenho(){
		ManagedBeanUtils.showDialog("reabertuaEmpenhoDialog");
	}
	
}
