package br.ccasj.sisauc.administracao.cadastro.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.ConfiguracaoEditalCredenciadoDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.ConfiguracaoEditalCredenciadoProcedimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.EditalCredenciamentoDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.EspecialidadeDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciado;
import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciadoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.EditalCredenciamento;
import br.ccasj.sisauc.administracao.cadastro.domain.Especialidade;
import br.ccasj.sisauc.administracao.cadastro.domain.GrupoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoBase;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoCBHPM2012;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoCISSFA;
import br.ccasj.sisauc.administracao.cadastro.domain.SubGrupoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.Tabela;
import br.ccasj.sisauc.administracao.cadastro.domain.TipoCobrancaCredenciadoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.service.CadastroConfiguracaoEditalService;
import br.ccasj.sisauc.administracao.exceptions.ConfiguracaoExistenteException;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ApplicationBean;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;

@Scope(value = "view")
@Service(value = "cadastroConfiguracaoEditalFormularioBean")
public class CadastroConfiguracaoEditalFormularioBean implements Serializable {

	private static final long serialVersionUID = -287974120730833736L;
	
	@Autowired
	private EditalCredenciamentoDAO editalCredenciamentoDAO;
	@Autowired
	private EspecialidadeDAO especialidadeDAO;
	@Autowired
	private ConfiguracaoEditalCredenciadoDAO configuracaoEditalCredenciadoDAO;
	@Autowired
	private ConfiguracaoEditalCredenciadoProcedimentoDAO configuracaoEditalCredenciadoProcedimentoDAO;
	
	@Autowired
	private CadastroConfiguracaoEditalService cadastroConfiguracaoEditalService;

	private EditalCredenciamento edital;
	private GrupoProcedimento grupo;
	private SubGrupoProcedimento subGrupo;
	private ConfiguracaoEditalCredenciadoProcedimento procedimento;
	private ConfiguracaoEditalCredenciado credenciado;
	private TipoInsercaoProcedimento tipoInsercaoProcedimento = TipoInsercaoProcedimento.INDIVIDUAL;
	private SelectItem[] tipoCobrancaFiltroOptions = new SelectItem[3];
	private List<Especialidade> especialidades = new ArrayList<Especialidade>();
	private List<Especialidade> especialidadesSelecionadas = new ArrayList<Especialidade>();
	private List<ConfiguracaoEditalCredenciado> credenciados = new ArrayList<ConfiguracaoEditalCredenciado>();
	
	private LazyDataModel<ConfiguracaoEditalCredenciadoProcedimento> lazyModel;
	private List<ConfiguracaoEditalCredenciadoProcedimento> procedimentos;
	private List<ConfiguracaoEditalCredenciadoProcedimento> procedimentosSelecionados = new ArrayList<ConfiguracaoEditalCredenciadoProcedimento>();
	private List<TipoCobrancaCredenciadoProcedimento> tiposCobranca = new ArrayList<TipoCobrancaCredenciadoProcedimento>();

	@PostConstruct
	private void init(){
		carregarEdital();
		atualizarListaProcedimentos();
		credenciados = configuracaoEditalCredenciadoDAO.obterConfiguracoesPorEdital(edital.getId());
		tiposCobranca = Arrays.asList(TipoCobrancaCredenciadoProcedimento.values());
		tipoCobrancaFiltroOptions[0] = new SelectItem("", "");
		tipoCobrancaFiltroOptions[1] = new SelectItem(TipoCobrancaCredenciadoProcedimento.TAXA, TipoCobrancaCredenciadoProcedimento.TAXA.getLabel());
		tipoCobrancaFiltroOptions[2] = new SelectItem(TipoCobrancaCredenciadoProcedimento.PACOTE, TipoCobrancaCredenciadoProcedimento.PACOTE.getLabel());
		especialidades = especialidadeDAO.listarEspecialidadesAtivasNaoOdontologicas();
		instanciarLazyModel();
	}
	
	private void instanciarLazyModel() {
		lazyModel = new LazyDataModel<ConfiguracaoEditalCredenciadoProcedimento>() {

			private static final long serialVersionUID = 930958326600636518L;
			@Override
			public List<ConfiguracaoEditalCredenciadoProcedimento> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				String sort = sortOrder == SortOrder.DESCENDING ? "desc" : null;
				filters.put("edital", edital.getId());
				if(credenciado != null){
					filters.put("credenciado", credenciado);
				}
				procedimentos = configuracaoEditalCredenciadoProcedimentoDAO.listarAtivasPorEditalLazyModel(first, pageSize, sortField, sort, filters);
				setRowCount(configuracaoEditalCredenciadoProcedimentoDAO.obterQuantidadeResultadosLazyModel(filters));
				return procedimentos;
			}
			
			@Override
			public Object getRowKey(ConfiguracaoEditalCredenciadoProcedimento object) {
				return object.getId();
			}
			
			@Override
			public ConfiguracaoEditalCredenciadoProcedimento getRowData(String rowKey) {
				for (ConfiguracaoEditalCredenciadoProcedimento procedimento : procedimentos) {
					if(procedimento.getId().equals(Integer.valueOf(rowKey))){
						return procedimento;
					}
				}
				return null;
			}
			
		};
	}

	private void carregarEdital() {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		edital = editalCredenciamentoDAO.findById(Integer.valueOf(id));
		if(edital == null){
			throw new SystemRuntimeException("O edital não foi encontrado");
		}
	}

	public void atualizarListaProcedimentos(){
		instanciarLazyModel();
	}
	
	public void iniciarNovasConfiguracaoes(){
		tipoInsercaoProcedimento = TipoInsercaoProcedimento.INDIVIDUAL;
		mudarFormaInsercaoProcedimento();
		ManagedBeanUtils.showDialog("adicionarProcedimentoDialog");
	}
	
	public void mudarFormaInsercaoProcedimento(){
		procedimento = new ConfiguracaoEditalCredenciadoProcedimento();
		grupo = new GrupoProcedimento();
		subGrupo = new SubGrupoProcedimento();
		especialidadesSelecionadas = new ArrayList<Especialidade>();
		procedimento.setConfiguracaoEditalCredenciado(credenciado);
	}
	
	public void salvarNovasConfiguracoes(){
		switch (tipoInsercaoProcedimento) {
		case INDIVIDUAL:
			salvarIndividual();
			break;
		case GRUPO:
			salvarPorGrupo();
			break;
		case SUBGRUPO:
			salvarPorSubGrupo();
			break;
		default:
			break;
		}
		atualizarListaProcedimentos();
		ManagedBeanUtils.obter("applicationBean", ApplicationBean.class).initConfiguracoes();
		Mensagem.informacao("Operação realizada com sucesso");
	}
	
	private void salvarIndividual(){
		cadastroConfiguracaoEditalService.criarConfiguracoesPorProcedimento(procedimento.getProcedimento(), procedimento.getTipo(), procedimento.getValor(), procedimento.getConfiguracaoEditalCredenciado(), especialidadesSelecionadas);
	}
	
	private void salvarPorGrupo(){
		Class<? extends ProcedimentoBase> clazz = edital.getTabela().equals(Tabela.CISSFA) ? ProcedimentoCISSFA.class : ProcedimentoCBHPM2012.class;
		cadastroConfiguracaoEditalService.criarConfiguracoesPorGrupo(grupo, clazz, procedimento.getTipo(), procedimento.getValor(), procedimento.getConfiguracaoEditalCredenciado(), especialidadesSelecionadas);
	}
	
	private void salvarPorSubGrupo(){
		Class<? extends ProcedimentoBase> clazz = edital.getTabela().equals(Tabela.CISSFA) ? ProcedimentoCISSFA.class : ProcedimentoCBHPM2012.class;
		cadastroConfiguracaoEditalService.criarConfiguracoesPorSubGrupo(subGrupo, clazz, procedimento.getTipo(), procedimento.getValor(), procedimento.getConfiguracaoEditalCredenciado(), especialidadesSelecionadas);
	}
	
	public void editar(Integer id){
		procedimento = configuracaoEditalCredenciadoProcedimentoDAO.findById(id);
		ManagedBeanUtils.showDialog("editarProcedimentoDialog");
		Mensagem.informacao("Operação realizada com sucesso");
	}
	
	public void salvar(){	
		try {
			cadastroConfiguracaoEditalService.salvar(procedimento);
		} catch (ConfiguracaoExistenteException e) {
			Mensagem.erro(e.getMessage());
		}
		Mensagem.informacao("Operação realizada com sucesso");
		ManagedBeanUtils.obter("applicationBean", ApplicationBean.class).initConfiguracoes();
	}
		
	public void desativar(Integer id){
		cadastroConfiguracaoEditalService.desativar(id);
		atualizarListaProcedimentos();
		ManagedBeanUtils.obter("applicationBean", ApplicationBean.class).initConfiguracoes();
		Mensagem.informacao("Operação realizada com sucesso");
	}
	
	public void desativarSelecionados(){
		cadastroConfiguracaoEditalService.desativar(procedimentosSelecionados);
		atualizarListaProcedimentos();
		ManagedBeanUtils.obter("applicationBean", ApplicationBean.class).initConfiguracoes();
		Mensagem.informacao("Operação realizada com sucesso");
	}
	
	public EditalCredenciamento getEdital() {
		return edital;
	}

	public void setEdital(EditalCredenciamento edital) {
		this.edital = edital;
	}

	public GrupoProcedimento getGrupo() {
		return grupo;
	}

	public void setGrupo(GrupoProcedimento grupo) {
		this.grupo = grupo;
	}

	public SubGrupoProcedimento getSubGrupo() {
		return subGrupo;
	}

	public void setSubGrupo(SubGrupoProcedimento subGrupo) {
		this.subGrupo = subGrupo;
	}

	public ConfiguracaoEditalCredenciadoProcedimento getProcedimento() {
		return procedimento;
	}

	public void setProcedimento(ConfiguracaoEditalCredenciadoProcedimento procedimento) {
		this.procedimento = procedimento;
	}

	public ConfiguracaoEditalCredenciado getCredenciado() {
		return credenciado;
	}

	public void setCredenciado(ConfiguracaoEditalCredenciado credenciado) {
		this.credenciado = credenciado;
	}

	public TipoInsercaoProcedimento getTipoInsercaoProcedimento() {
		return tipoInsercaoProcedimento;
	}

	public void setTipoInsercaoProcedimento(TipoInsercaoProcedimento tipoInsercaoProcedimento) {
		this.tipoInsercaoProcedimento = tipoInsercaoProcedimento;
	}

	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	public SelectItem[] getTipoCobrancaFiltroOptions() {
		return tipoCobrancaFiltroOptions;
	}

	public void setTipoCobrancaFiltroOptions(SelectItem[] tipoCobrancaFiltroOptions) {
		this.tipoCobrancaFiltroOptions = tipoCobrancaFiltroOptions;
	}

	public List<Especialidade> getEspecialidadesSelecionadas() {
		return especialidadesSelecionadas;
	}

	public void setEspecialidadesSelecionadas(List<Especialidade> especialidadesSelecionadas) {
		this.especialidadesSelecionadas = especialidadesSelecionadas;
	}

	public List<ConfiguracaoEditalCredenciado> getCredenciados() {
		return credenciados;
	}

	public void setCredenciados(List<ConfiguracaoEditalCredenciado> credenciados) {
		this.credenciados = credenciados;
	}

	public List<ConfiguracaoEditalCredenciadoProcedimento> getProcedimentosSelecionados() {
		return procedimentosSelecionados;
	}

	public void setProcedimentosSelecionados(List<ConfiguracaoEditalCredenciadoProcedimento> procedimentosSelecionados) {
		this.procedimentosSelecionados = procedimentosSelecionados;
	}

	public List<TipoCobrancaCredenciadoProcedimento> getTiposCobranca() {
		return tiposCobranca;
	}

	public void setTiposCobranca(List<TipoCobrancaCredenciadoProcedimento> tiposCobranca) {
		this.tiposCobranca = tiposCobranca;
	}

	public LazyDataModel<ConfiguracaoEditalCredenciadoProcedimento> getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<ConfiguracaoEditalCredenciadoProcedimento> lazyModel) {
		this.lazyModel = lazyModel;
	}

}
