package br.ccasj.sisauc.administracao.cadastro.managedbean;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.EditalCredenciamentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.EditalCredenciamento;
import br.ccasj.sisauc.administracao.cadastro.service.CadastroEditalCredenciamentoService;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ApplicationBean;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;

@Scope(value = "view")
@Service(value = "cadastroEditalCredenciamentoListaBean")
public class CadastroEditalCredenciamentoListaBean {

	@Autowired
	private EditalCredenciamentoDAO editalCredenciamentoDAO;
	@Autowired
	private CadastroEditalCredenciamentoService cadastroEditalCredenciamentoService;

	private List<EditalCredenciamento> editais;
	private EditalCredenciamento edital;

	@PostConstruct
	private void init() {
		editais = editalCredenciamentoDAO.findAll();
	}
	
	public void selectionarEdital(EditalCredenciamento editalCredenciamento){
		edital = editalCredenciamento;
		ManagedBeanUtils.showDialog("mudarStatusEditalDialog");
	}
	
	public void mudarStatusAtivoEdital(){
		cadastroEditalCredenciamentoService.mudarStatusAtivoCredenciado(edital.getId(), !edital.isAtivo());
		editais = editalCredenciamentoDAO.findAll();
		ManagedBeanUtils.obter("applicationBean", ApplicationBean.class).initConfiguracoes();
		String message = "Edital " + edital.getNumero() + " status com sucesso";
		Mensagem.informacao(message.replace("status", edital.isAtivo() ? "desativado" : "ativado"));
		ManagedBeanUtils.hideDialog("mudarStatusEditalDialog");
	}

	public List<EditalCredenciamento> getEditais() {
		return editais;
	}

	public void setEditais(List<EditalCredenciamento> editais) {
		this.editais = editais;
	}

	public EditalCredenciamentoDAO getEditalCredenciamentoDAO() {
		return editalCredenciamentoDAO;
	}

	public void setEditalCredenciamentoDAO(EditalCredenciamentoDAO editalCredenciamentoDAO) {
		this.editalCredenciamentoDAO = editalCredenciamentoDAO;
	}

	public EditalCredenciamento getEdital() {
		return edital;
	}

	public void setEdital(EditalCredenciamento edital) {
		this.edital = edital;
	}

}
