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
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.EstadoAR;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;
import br.ccasj.sisauc.pesquisa.domain.OpcoesPesquisaBoolean;
import br.ccasj.sisauc.pesquisa.domain.OpcoesPesquisaNumero;
import br.ccasj.sisauc.pesquisa.domain.ParametrosPesquisaAR;
import br.ccasj.sisauc.pesquisa.service.PesquisarARService;

@Scope(value = "view")
@Service(value = "pesquisarARBean")
public class PesquisarARBean implements Serializable {

	private static final long serialVersionUID = 8908377406326874836L;

	@Autowired
	private PesquisarARService pesquisarARService;
	@Autowired
	private OrganizacaoMilitarDAO organizacaoMilitarDAO;

	private ParametrosPesquisaAR parametros = new ParametrosPesquisaAR();
	private List<AutorizacaoRessarcimento> ars = new ArrayList<AutorizacaoRessarcimento>();
	private List<OpcoesPesquisaBoolean> opcoesPesquisaBooleanList = Arrays.asList(OpcoesPesquisaBoolean.values());
	private List<OpcoesPesquisaNumero> opcoesPesquisaNumeroList = Arrays.asList(OpcoesPesquisaNumero.values());
	private List<EstadoAR> estadosAR = Arrays.asList(EstadoAR.values());
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
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("pesquisa-ar-form:resultado");	
		for (int i = 0; i < dataTable.getColumns().size(); i++) {
			colunas.add(true);
		}
	}

	public void pesquisar(){
		ars = pesquisarARService.pesquisar(parametros);
	}

	public ParametrosPesquisaAR getParametros() {
		return parametros;
	}

	public void setParametros(ParametrosPesquisaAR parametros) {
		this.parametros = parametros;
	}

	public List<AutorizacaoRessarcimento> getArs() {
		return ars;
	}

	public void setArs(List<AutorizacaoRessarcimento> ars) {
		this.ars = ars;
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
	
	public List<EstadoAR> getEstadosAR() {
		return estadosAR;
	}

	public void setEstadosAR(List<EstadoAR> estadosAR) {
		this.estadosAR = estadosAR;
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
