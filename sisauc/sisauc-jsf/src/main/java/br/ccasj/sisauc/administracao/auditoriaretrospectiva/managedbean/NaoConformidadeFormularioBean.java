package br.ccasj.sisauc.administracao.auditoriaretrospectiva.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.CredenciadoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Credenciado;
import br.ccasj.sisauc.administracao.sistema.dao.ConfiguracaoSistemaDao;
import br.ccasj.sisauc.administracao.sistema.domain.ConfiguracaoSistema;
import br.ccasj.sisauc.administracao.sistema.service.ConfiguracaoOrganizacaoMilitarRegionalService;
import br.ccasj.sisauc.emissaogab.dao.ItemGABDAO;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.relatorio.GeradorRelatorio;
import br.ccasj.sisauc.relatorio.domain.RelatorioNaoConformidade;

@Scope(value = "view")
@Service(value = "naoConformidadeFormularioBean")
public class NaoConformidadeFormularioBean implements Serializable {

	private static final long serialVersionUID = 2768364952350699166L;
	
	@Autowired
	private ItemGABDAO itemGabDao;
	@Autowired
	private CredenciadoDAO credenciadoDao;
	@Autowired
	private ConfiguracaoOrganizacaoMilitarRegionalService configuracaoOrganizacaoMilitarRegionalService;
	@Autowired
	private ConfiguracaoSistemaDao configuracaoSistemaDao;
	@Autowired
	private GeradorRelatorio geradorRelatorio;
	
	private OrganizacaoMilitar regional;
	private Credenciado credenciado;
	private ConfiguracaoSistema configuracaoSistema;
	private List<ItemGAB> itensGab;
	private List<ItemGAB> itensGabSelecionados;
	private List<Credenciado> credenciados;
	
	@PostConstruct
	private void init() {
		regional = configuracaoOrganizacaoMilitarRegionalService.obterRegionalSistema();
		credenciados = credenciadoDao.listarAtivosParaCombo();
		itensGab = itemGabDao.obterItensGabNaoConformes();
	}
	
	public void atualizarItensGabPorCredenciado(){
		if(credenciado == null){
			init();
		}else{
			itensGab = itemGabDao.obterItensGabNaoConformesPorCredenciado(credenciado.getId());
		}
	}
	
	public void validarRelatorio(){
		if(itensGabSelecionados == null || itensGabSelecionados.isEmpty() ){
			throw new SystemRuntimeException("É necessário selecionar algum item para emitir um relatório de Não Conformidade.");
		} else{
			configuracaoSistema = configuracaoSistemaDao.obterConfiguracaoSistema();
			geradorRelatorio.gerar(new RelatorioNaoConformidade(itensGabSelecionados, credenciado, regional, configuracaoSistema));
		}
	}

	public Credenciado getCredenciado() {
		return credenciado;
	}

	public void setCredenciado(Credenciado credenciado) {
		this.credenciado = credenciado;
	}

	public ConfiguracaoSistema getConfiguracaoSistema() {
		return configuracaoSistema;
	}

	public void setConfiguracaoSistema(ConfiguracaoSistema configuracaoSistema) {
		this.configuracaoSistema = configuracaoSistema;
	}

	public List<ItemGAB> getItensGab() {
		return itensGab;
	}

	public void setItensGab(List<ItemGAB> itensGab) {
		this.itensGab = itensGab;
	}

	public List<ItemGAB> getItensGabSelecionados() {
		return itensGabSelecionados;
	}

	public void setItensGabSelecionados(List<ItemGAB> itensGabSelecionados) {
		this.itensGabSelecionados = itensGabSelecionados;
	}

	public List<Credenciado> getCredenciados() {
		return credenciados;
	}

	public void setCredenciados(List<Credenciado> credenciados) {
		this.credenciados = credenciados;
	}

	public OrganizacaoMilitar getRegional() {
		return regional;
	}

	public void setRegional(OrganizacaoMilitar regional) {
		this.regional = regional;
	}

}
