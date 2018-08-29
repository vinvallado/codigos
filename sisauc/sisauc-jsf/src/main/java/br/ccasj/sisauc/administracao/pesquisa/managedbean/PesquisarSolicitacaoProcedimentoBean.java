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
import br.ccasj.sisauc.administracao.cadastro.domain.EstadoSolicitacaoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.LocalInternacao;
import br.ccasj.sisauc.auditoriaprospectiva.domain.EstadoAuditoriaProspectiva;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;
import br.ccasj.sisauc.pesquisa.domain.OpcoesPesquisaBoolean;
import br.ccasj.sisauc.pesquisa.domain.OpcoesPesquisaNumero;
import br.ccasj.sisauc.pesquisa.domain.ParametrosPesquisaSolicitacaoProcedimento;
import br.ccasj.sisauc.pesquisa.domain.ResultadoPesquisaSolicitacaoProcedimentoVO;
import br.ccasj.sisauc.pesquisa.service.PesquisarSolicitacaoProcedimentoService;

@Scope(value = "view")
@Service(value = "pesquisarSolicitacaoProcedimentoBean")
public class PesquisarSolicitacaoProcedimentoBean implements Serializable {

	private static final long serialVersionUID = 8908377406326874836L;

	@Autowired
	private PesquisarSolicitacaoProcedimentoService pesquisarSolicitacaoProcedimentoService;
	@Autowired
	private OrganizacaoMilitarDAO organizacaoMilitarDAO;

	private ParametrosPesquisaSolicitacaoProcedimento parametros = new ParametrosPesquisaSolicitacaoProcedimento();
	private List<ResultadoPesquisaSolicitacaoProcedimentoVO> solicitacoes = new ArrayList<ResultadoPesquisaSolicitacaoProcedimentoVO>();
	private List<OpcoesPesquisaBoolean> opcoesPesquisaBooleanList = Arrays.asList(OpcoesPesquisaBoolean.values());
	private List<OpcoesPesquisaNumero> opcoesPesquisaNumeroList = Arrays.asList(OpcoesPesquisaNumero.values());
	private List<EstadoSolicitacaoProcedimento> estadosSolicitacaoProcedimento = Arrays.asList(EstadoSolicitacaoProcedimento.values());
	private List<EstadoAuditoriaProspectiva> estadoAuditoriaProspectiva = Arrays.asList(EstadoAuditoriaProspectiva.values());
	private List<LocalInternacao> locaisInternacao = Arrays.asList(LocalInternacao.values());
	private List<OrganizacaoMilitar> organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();
	private List<Divisao> divisoes = Arrays.asList(Divisao.values());
	private List<Boolean> colunas = new ArrayList<Boolean>();
	
	@PostConstruct
	private void init(){
		organizacoesMilitares = organizacaoMilitarDAO.listarOMSAtivasPorRegional();
		carregarValoresColunas();
	}
	
	public void toggleHander(ToggleEvent e){
		colunas.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}
	
	private void carregarValoresColunas(){
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("pesquisa-solicitacao-procedimento-form:resultado");
		for (int i = 0; i < dataTable.getColumns().size(); i++) {
			colunas.add(true);
		}
	}
	
	public void pesquisar(){
		solicitacoes = pesquisarSolicitacaoProcedimentoService.pesquisar(parametros);
	}

	public PesquisarSolicitacaoProcedimentoService getPesquisarSolicitacaoProcedimentoService() {
		return pesquisarSolicitacaoProcedimentoService;
	}

	public void setPesquisarSolicitacaoProcedimentoService(
			PesquisarSolicitacaoProcedimentoService pesquisarSolicitacaoProcedimentoService) {
		this.pesquisarSolicitacaoProcedimentoService = pesquisarSolicitacaoProcedimentoService;
	}

	public ParametrosPesquisaSolicitacaoProcedimento getParametros() {
		return parametros;
	}

	public void setParametros(ParametrosPesquisaSolicitacaoProcedimento parametros) {
		this.parametros = parametros;
	}

	public List<ResultadoPesquisaSolicitacaoProcedimentoVO> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(List<ResultadoPesquisaSolicitacaoProcedimentoVO> solicitacoes) {
		this.solicitacoes = solicitacoes;
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

	public List<EstadoSolicitacaoProcedimento> getEstadosSolicitacaoProcedimento() {
		return estadosSolicitacaoProcedimento;
	}

	public void setEstadosSolicitacaoProcedimento(List<EstadoSolicitacaoProcedimento> estadosSolicitacaoProcedimento) {
		this.estadosSolicitacaoProcedimento = estadosSolicitacaoProcedimento;
	}

	public List<EstadoAuditoriaProspectiva> getEstadoAuditoriaProspectiva() {
		return estadoAuditoriaProspectiva;
	}

	public void setEstadoAuditoriaProspectiva(List<EstadoAuditoriaProspectiva> estadoAuditoriaProspectiva) {
		this.estadoAuditoriaProspectiva = estadoAuditoriaProspectiva;
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
