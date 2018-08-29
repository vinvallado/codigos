package br.ccasj.sisauc.administracao.cadastro.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.ConfiguracaoEditalCredenciadoDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.CredenciadoDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.EditalCredenciamentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciado;
import br.ccasj.sisauc.administracao.cadastro.domain.Credenciado;
import br.ccasj.sisauc.administracao.cadastro.domain.EditalCredenciamento;
import br.ccasj.sisauc.administracao.cadastro.service.CadastroConfiguracaoEditalCredenciadoService;
import br.ccasj.sisauc.administracao.cadastro.service.CadastroEditalCredenciamentoService;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ApplicationBean;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;

@Scope(value = "view")
@Service(value = "cadastroEditalCredenciamentoFormularioBean")
public class CadastroEditalCredenciamentoFormularioBean {

	@Autowired
	private CredenciadoDAO credenciadoDAO;
	@Autowired
	private EditalCredenciamentoDAO editalCredenciamentoDAO;
	@Autowired
	private ConfiguracaoEditalCredenciadoDAO configuracaoEditalCredenciadoDAO;
	@Autowired
	private CadastroEditalCredenciamentoService cadastroEditalCredenciamentoService;
	@Autowired
	private CadastroConfiguracaoEditalCredenciadoService cadastroConfiguracaoEditalCredenciadoService;

	private EditalCredenciamento editalCredenciamento;
	private ConfiguracaoEditalCredenciado configuracao = new ConfiguracaoEditalCredenciado();
	
	private List<Credenciado> credenciados;
	private List<ConfiguracaoEditalCredenciado> configuracoes;

	@PostConstruct
	private void init() {
		credenciados = credenciadoDAO.listarAtivosParaCombo();
		editalCredenciamento = obterEdital();
		atualizarListaConfiguracoes();
	}
	
	private EditalCredenciamento obterEdital() {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		return id.equals("novo") ? new EditalCredenciamento() : editalCredenciamentoDAO.findById(Integer.valueOf(id));
	}
	
	private void atualizarListaConfiguracoes(){
		configuracoes = editalCredenciamento.getId() == null ? new ArrayList<ConfiguracaoEditalCredenciado>() : configuracaoEditalCredenciadoDAO.obterConfiguracoesPorEdital(editalCredenciamento.getId());
	}

	public void salvar() {
		editalCredenciamento = cadastroEditalCredenciamentoService.salvar(editalCredenciamento);
		atualizarListaConfiguracoes();
		ManagedBeanUtils.obter("applicationBean", ApplicationBean.class).initConfiguracoes();
		Mensagem.informacao("Edital salvo com sucesso!");
		ManagedBeanUtils.redirecionar("/administracao/cadastro/edital-credenciamento");
	}

	public void prepararNovaConfiguracao() {
		configuracao = new ConfiguracaoEditalCredenciado(editalCredenciamento);
		ManagedBeanUtils.showDialog("adicionarCredenciadoDialog");
	}

	public void editarConfiguracao(Integer id) {
		configuracao = configuracaoEditalCredenciadoDAO.findById(id);
		ManagedBeanUtils.showDialog("adicionarCredenciadoDialog");
	}

	public void salvarConfiguracao() {
		cadastroConfiguracaoEditalCredenciadoService.salvar(configuracao);
		atualizarListaConfiguracoes();
		ManagedBeanUtils.obter("applicationBean", ApplicationBean.class).initConfiguracoes();
		configuracao = null;
		ManagedBeanUtils.hideDialog("adicionarCredenciadoDialog");
	}
	
	public void selecionarConfiguracao(ConfiguracaoEditalCredenciado configuracaoSelecionada){
		configuracao = configuracaoSelecionada;
		ManagedBeanUtils.showDialog("removerCredenciadoDialog");
	}

	public void removerConfiguracao() {
		cadastroConfiguracaoEditalCredenciadoService.remover(configuracao.getId());
		atualizarListaConfiguracoes();
		ManagedBeanUtils.obter("applicationBean", ApplicationBean.class).initConfiguracoes();
		Mensagem.informacao("Credenciado removido com sucesso!");
		ManagedBeanUtils.hideDialog("removerCredenciadoDialog");
	}

	public EditalCredenciamento getEditalCredenciamento() {
		return editalCredenciamento;
	}

	public void setEditalCredenciamento(EditalCredenciamento editalCredenciamento) {
		this.editalCredenciamento = editalCredenciamento;
	}

	public ConfiguracaoEditalCredenciado getConfiguracao() {
		return configuracao;
	}

	public void setConfiguracao(ConfiguracaoEditalCredenciado configuracao) {
		this.configuracao = configuracao;
	}

	public List<Credenciado> getCredenciados() {
		return credenciados;
	}

	public void setCredenciados(List<Credenciado> credenciados) {
		this.credenciados = credenciados;
	}

	public List<ConfiguracaoEditalCredenciado> getConfiguracoes() {
		return configuracoes;
	}

	public void setConfiguracoes(List<ConfiguracaoEditalCredenciado> configuracoes) {
		this.configuracoes = configuracoes;
	}

}
