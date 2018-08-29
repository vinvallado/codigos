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
import br.ccasj.sisauc.emissaogab.domain.EstadoGAB;
import br.ccasj.sisauc.emissaogab.domain.EstadoItemGAB;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;
import br.ccasj.sisauc.pesquisa.domain.ComparacaoValoresItemGAB;
import br.ccasj.sisauc.pesquisa.domain.OpcoesPesquisaBoolean;
import br.ccasj.sisauc.pesquisa.domain.OpcoesPesquisaNumero;
import br.ccasj.sisauc.pesquisa.domain.OpcoesPesquisaValoresItemGAB;
import br.ccasj.sisauc.pesquisa.domain.ParametrosPesquisaItemGAB;
import br.ccasj.sisauc.pesquisa.service.PesquisarItemGABService;

@Scope(value = "view")
@Service(value = "pesquisarItemGABBean")
public class PesquisarItemGABBean {

	@Autowired
	private PesquisarItemGABService pesquisarItemGABService;
	@Autowired
	private OrganizacaoMilitarDAO organizacaoMilitarDAO;

	private ParametrosPesquisaItemGAB parametros = new ParametrosPesquisaItemGAB();
	private List<ItemGAB> itens = new ArrayList<ItemGAB>();
	private List<EstadoGAB> estadosGAB = Arrays.asList(EstadoGAB.values());
	private List<EstadoItemGAB> estadosItemGAB = Arrays.asList(EstadoItemGAB.values());
	private List<OpcoesPesquisaBoolean> opcoesPesquisaBooleanList = Arrays.asList(OpcoesPesquisaBoolean.values());
	private List<OpcoesPesquisaNumero> opcoesPesquisaNumeroList = Arrays.asList(OpcoesPesquisaNumero.values());
	private List<OpcoesPesquisaValoresItemGAB> opcoesPesquisaValoresList = Arrays.asList(OpcoesPesquisaValoresItemGAB.values());
	private List<OrganizacaoMilitar> organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();
	private List<Divisao> divisoes = Arrays.asList(Divisao.values());
	private List<Boolean> colunas = new ArrayList<Boolean>();
	private Double valorTotalEstimado = 0.0d;
	private Double valorTotalApresentado = 0.0d;
	private Double valorTotalAuditado = 0.0d;
	private Double valorTotalFinal = 0.0d;
	
	@PostConstruct
	private void init(){
		organizacoesMilitares = organizacaoMilitarDAO.listarOMSAtivasPorRegional();
		parametros.getComparacoes().add(new ComparacaoValoresItemGAB());
		carregarValoresColunas();
	}
	
	public void toggleHandler(ToggleEvent e){
		getColunas().set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}
	
	private void carregarValoresColunas(){
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("pesquisa-item-gab-form:resultado");
		for (int i = 0; i < dataTable.getColumns().size(); i++) {
			getColunas().add(true);
		}
	}
	
	public void adicionarComparacao(){
		parametros.getComparacoes().add(new ComparacaoValoresItemGAB());
	}
	
	public void removerComparacao(ComparacaoValoresItemGAB comparacao){
		parametros.getComparacoes().remove(parametros.getComparacoes().indexOf(comparacao));
	}

	public void pesquisar() {
		itens = pesquisarItemGABService.pesquisar(parametros);
		somarValorTotal();
	}

	private void somarValorTotal(){
		valorTotalEstimado = 0.0d;
		valorTotalApresentado = 0.0d;
		valorTotalAuditado = 0.0d;
		valorTotalFinal = 0.0d;
		for (ItemGAB item : itens) {
			if (item.getValorTotal() != null)
				valorTotalEstimado += item.getValorTotal();
			if (item.getAuditoriaRetrospectiva().getValorApresentado() != null)
				valorTotalApresentado += item.getAuditoriaRetrospectiva().getValorApresentado();
			if (item.getAuditoriaRetrospectiva().getValorAuditado() != null)
				valorTotalAuditado += item.getAuditoriaRetrospectiva().getValorAuditado();
			if (item.getAuditoriaRetrospectiva().getValorFinal() != null)
				valorTotalFinal += item.getAuditoriaRetrospectiva().getValorFinal();
		}
	}

	public PesquisarItemGABService getPesquisarItemGABService() {
		return pesquisarItemGABService;
	}

	public void setPesquisarItemGABService(PesquisarItemGABService pesquisarItemGABService) {
		this.pesquisarItemGABService = pesquisarItemGABService;
	}

	public ParametrosPesquisaItemGAB getParametros() {
		return parametros;
	}

	public void setParametros(ParametrosPesquisaItemGAB parametros) {
		this.parametros = parametros;
	}

	public List<ItemGAB> getItens() {
		return itens;
	}

	public void setItens(List<ItemGAB> itens) {
		this.itens = itens;
	}

	public List<EstadoGAB> getEstadosGAB() {
		return estadosGAB;
	}

	public void setEstadosGAB(List<EstadoGAB> estadosGAB) {
		this.estadosGAB = estadosGAB;
	}

	public List<EstadoItemGAB> getEstadosItemGAB() {
		return estadosItemGAB;
	}

	public void setEstadosItemGAB(List<EstadoItemGAB> estadosItemGAB) {
		this.estadosItemGAB = estadosItemGAB;
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

	public List<OpcoesPesquisaValoresItemGAB> getOpcoesPesquisaValoresList() {
		return opcoesPesquisaValoresList;
	}

	public void setOpcoesPesquisaValoresList(List<OpcoesPesquisaValoresItemGAB> opcoesPesquisaValoresList) {
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

	public Double getValorTotalAuditado() {
		return valorTotalAuditado;
	}

	public void setValorTotalAuditado(Double valorTotalAuditado) {
		this.valorTotalAuditado = valorTotalAuditado;
	}

	public Double getValorTotalFinal() {
		return valorTotalFinal;
	}

	public void setValorTotalFinal(Double valorTotalFinal) {
		this.valorTotalFinal = valorTotalFinal;
	}

}
