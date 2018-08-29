package br.ccasj.sisauc.administracao.pesquisa.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.OrganizacaoMilitarDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.LocalInternacao;
import br.ccasj.sisauc.emissaogab.domain.EstadoGAB;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;
import br.ccasj.sisauc.pesquisa.domain.OpcoesPesquisaBoolean;
import br.ccasj.sisauc.pesquisa.domain.OpcoesPesquisaNumero;
import br.ccasj.sisauc.pesquisa.domain.ParametrosPesquisaGAB;
import br.ccasj.sisauc.pesquisa.service.PesquisarGABService;

@Scope(value = "view")
@Service(value = "pesquisarGABBean")
public class PesquisarGABBean implements Serializable {

	private static final long serialVersionUID = 8908377406326874836L;

	@Autowired
	private PesquisarGABService pesquisarGABService;
	@Autowired
	private OrganizacaoMilitarDAO organizacaoMilitarDAO;

	private Double valorTotal = 0.0d;
	private ParametrosPesquisaGAB parametros = new ParametrosPesquisaGAB();
	private List<GuiaApresentacaoBeneficiario> gabs = new ArrayList<GuiaApresentacaoBeneficiario>();
	private List<OpcoesPesquisaBoolean> opcoesPesquisaBooleanList = Arrays.asList(OpcoesPesquisaBoolean.values());
	private List<OpcoesPesquisaNumero> opcoesPesquisaNumeroList = Arrays.asList(OpcoesPesquisaNumero.values());
	private List<EstadoGAB> estadosGAB = Arrays.asList(EstadoGAB.values());
	private List<LocalInternacao> locaisInternacao = Arrays.asList(LocalInternacao.values());
	private List<OrganizacaoMilitar> organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();
	private List<Divisao> divisoes = Arrays.asList(Divisao.values());

	private List<Boolean> colunas = new ArrayList<Boolean>();
	
	@PostConstruct
	private void init(){
		organizacoesMilitares = organizacaoMilitarDAO.listarOMSAtivasPorRegional();
		carregarValoresColunas();
	}

	public void toggleHandler(ToggleEvent e) {
        colunas.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }	
	
	private void carregarValoresColunas() {
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("pesquisa-gab-form:resultado");	
		for (int i = 0; i < dataTable.getColumns().size(); i++) {
			colunas.add(true);
		}
	}

	public void pesquisar(){
		gabs = pesquisarGABService.pesquisar(parametros);
		calcularValorTotal();
	}

	private void calcularValorTotal(){
		valorTotal = 0.0d;
		for (GuiaApresentacaoBeneficiario gab : gabs) {
			valorTotal += gab.getValorTotal();
		}
	}
	
	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public ParametrosPesquisaGAB getParametros() {
		return parametros;
	}

	public void setParametros(ParametrosPesquisaGAB parametros) {
		this.parametros = parametros;
	}

	public List<GuiaApresentacaoBeneficiario> getGabs() {
		return gabs;
	}

	public void setGabs(List<GuiaApresentacaoBeneficiario> gabs) {
		this.gabs = gabs;
	}

	public List<OpcoesPesquisaBoolean> getOpcoesPesquisaBooleanList() {
		return opcoesPesquisaBooleanList;
	}

	public void setOpcoesPesquisaBooleanList(List<OpcoesPesquisaBoolean> opcoesPesquisaBooleanList) {
		this.opcoesPesquisaBooleanList = opcoesPesquisaBooleanList;
	}

	public List<OpcoesPesquisaNumero> getOpcoesPesquisaNumeroList() {
		return opcoesPesquisaNumeroList;
	}

	public void setOpcoesPesquisaNumeroList(List<OpcoesPesquisaNumero> opcoesPesquisaNumeroList) {
		this.opcoesPesquisaNumeroList = opcoesPesquisaNumeroList;
	}

	public List<EstadoGAB> getEstadosGAB() {
		return estadosGAB;
	}

	public void setEstadosGAB(List<EstadoGAB> estadosGAB) {
		this.estadosGAB = estadosGAB;
	}

	public List<LocalInternacao> getLocaisInternacao() {
		return locaisInternacao;
	}

	public void setLocaisInternacao(List<LocalInternacao> locaisInternacao) {
		this.locaisInternacao = locaisInternacao;
	}

	public List<OrganizacaoMilitar> getOrganizacoesMilitares() {
		return organizacoesMilitares;
	}

	public void setOrganizacoesMilitares(List<OrganizacaoMilitar> organizacoesMilitares) {
		this.organizacoesMilitares = organizacoesMilitares;
	}

	public List<Divisao> getDivisoes() {
		return divisoes;
	}

	public void setDivisoes(List<Divisao> divisoes) {
		this.divisoes = divisoes;
	}

	public List<Boolean> getColunas() {
		return colunas;
	}

	public void setColunas(List<Boolean> colunas) {
		this.colunas = colunas;
	}


}
