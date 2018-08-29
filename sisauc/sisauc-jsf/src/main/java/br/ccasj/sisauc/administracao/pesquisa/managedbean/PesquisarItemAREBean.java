package br.ccasj.sisauc.administracao.pesquisa.managedbean;

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
import br.ccasj.sisauc.emissaoar.domain.EstadoAR;
import br.ccasj.sisauc.emissaoar.domain.EstadoItemAR;
import br.ccasj.sisauc.emissaoar.domain.ItemAR;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;
import br.ccasj.sisauc.pesquisa.domain.ComparacaoValoresItemARE;
import br.ccasj.sisauc.pesquisa.domain.OpcoesPesquisaBoolean;
import br.ccasj.sisauc.pesquisa.domain.OpcoesPesquisaNumero;
import br.ccasj.sisauc.pesquisa.domain.OpcoesPesquisaValoresItemARE;
import br.ccasj.sisauc.pesquisa.domain.ParametrosPesquisaItemARE;
import br.ccasj.sisauc.pesquisa.service.PesquisarItemAREService;

@Scope(value = "view")
@Service(value = "pesquisarItemAREBean")
public class PesquisarItemAREBean {

	@Autowired
	private PesquisarItemAREService pesquisarItemAREService;
	@Autowired
	private OrganizacaoMilitarDAO organizacaoMilitarDAO;

	private ParametrosPesquisaItemARE parametros = new ParametrosPesquisaItemARE();
	private List<ItemAR> itens = new ArrayList<ItemAR>();
	private List<EstadoAR> estadosARE = Arrays.asList(EstadoAR.values());
	private List<EstadoItemAR> estadosItemARE = Arrays.asList(EstadoItemAR.values());
	private List<OpcoesPesquisaBoolean> opcoesPesquisaBooleanList = Arrays.asList(OpcoesPesquisaBoolean.values());
	private List<OpcoesPesquisaNumero> opcoesPesquisaNumeroList = Arrays.asList(OpcoesPesquisaNumero.values());
	private List<OpcoesPesquisaValoresItemARE> opcoesPesquisaValoresList = Arrays.asList(OpcoesPesquisaValoresItemARE	.values());
	private List<OrganizacaoMilitar> organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();
	private List<Divisao> divisoes = Arrays.asList(Divisao.values());
	private List<Boolean> colunas = new ArrayList<Boolean>();
	private Double valorTotalEstimado = 0.0d;
	private Double valorTotalApresentado = 0.0d;
	private Double valorTotalRessarcimento = 0.0d;
	
	
	@PostConstruct
	private void init(){
		organizacoesMilitares = organizacaoMilitarDAO.listarOMSAtivasPorRegional();
		parametros.getComparacoes().add(new ComparacaoValoresItemARE());
		carregarValoresColunas();
	}
	
	public void toggleHandler(ToggleEvent e){
		getColunas().set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}
	
	private void carregarValoresColunas(){
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("pesquisa-item-are-form:resultado");
		for (int i = 0; i < dataTable.getColumns().size(); i++) {
			getColunas().add(true);
		}
	}
	
	public void adicionarComparacao(){
		parametros.getComparacoes().add(new ComparacaoValoresItemARE());
	}
	
	public void removerComparacao(ComparacaoValoresItemARE comparacao){
		parametros.getComparacoes().remove(parametros.getComparacoes().indexOf(comparacao));
	}

	public void pesquisar() {
		setItens(pesquisarItemAREService.pesquisar(parametros));
		somarValorTotal();
	}
	
	private void somarValorTotal(){
		valorTotalEstimado = 0.0d;
		valorTotalApresentado = 0.0d;
		valorTotalRessarcimento = 0.0d;
		for (ItemAR item : itens) {
			if (item.getAuditoriaRetrospectiva().getValorApresentado() != null)
				valorTotalApresentado += item.getAuditoriaRetrospectiva().getValorApresentado();
			if (item.getAuditoriaRetrospectiva().getValorEstimado() != null)
				valorTotalEstimado += item.getAuditoriaRetrospectiva().getValorEstimado();
			if (item.getAuditoriaRetrospectiva().getValorRessarcimento() != null)
				valorTotalRessarcimento += item.getAuditoriaRetrospectiva().getValorRessarcimento();
		}
	}

	public PesquisarItemAREService getPesquisarItemAREService() {
		return pesquisarItemAREService;
	}

	public void setPesquisarItemAREService(PesquisarItemAREService pesquisarItemAREService) {
		this.pesquisarItemAREService = pesquisarItemAREService;
	}

	public ParametrosPesquisaItemARE getParametros() {
		return parametros;
	}

	public void setParametros(ParametrosPesquisaItemARE parametros) {
		this.parametros = parametros;
	}

	public List<ItemAR> getItens() {
		return itens;
	}

	public void setItens(List<ItemAR> itens) {
		this.itens = itens;
	}

	public List<EstadoItemAR> getEstadosItemARE() {
		return estadosItemARE;
	}

	public void setEstadosItemARE(List<EstadoItemAR> estadosItemARE) {
		this.estadosItemARE = estadosItemARE;
	}

	public List<EstadoAR> getEstadosARE() {
		return estadosARE;
	}

	public void setEstadosARE(List<EstadoAR> estadosARE) {
		this.estadosARE = estadosARE;
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

	public List<OpcoesPesquisaValoresItemARE> getOpcoesPesquisaValoresList() {
		return opcoesPesquisaValoresList;
	}

	public void setOpcoesPesquisaValoresList(List<OpcoesPesquisaValoresItemARE> opcoesPesquisaValoresList) {
		this.opcoesPesquisaValoresList = opcoesPesquisaValoresList;
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

	public Double getValorTotalEstimado() {
		return valorTotalEstimado;
	}

	public void setValorTotalEstimado(Double valorTotalEstimado) {
		this.valorTotalEstimado = valorTotalEstimado;
	}

	public Double getValorTotalApresentado() {
		return valorTotalApresentado;
	}

	public void setValorTotalApresentado(Double valorTotalApresentado) {
		this.valorTotalApresentado = valorTotalApresentado;
	}

	public Double getValorTotalRessarcimento() {
		return valorTotalRessarcimento;
	}

	public void setValorTotalRessarcimento(Double valorTotalRessarcimento) {
		this.valorTotalRessarcimento = valorTotalRessarcimento;
	}	

}
