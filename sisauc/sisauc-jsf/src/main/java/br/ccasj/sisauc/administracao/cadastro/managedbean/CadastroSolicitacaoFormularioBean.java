package br.ccasj.sisauc.administracao.cadastro.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.beneficiario.dao.BeneficiarioDAO;
import br.ccasj.sisauc.administracao.beneficiario.dao.impl.BeneficiarioDAOImpl;
import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.administracao.beneficiario.service.impl.BeneficiarioServiceImpl;
import br.ccasj.sisauc.administracao.beneficiario.service.BeneficiarioService;
import br.ccasj.sisauc.administracao.cadastro.dao.ConfiguracaoEditalCredenciadoProcedimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.EspecialidadeDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.EspecificacaoIsencaoCobrancaDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.SolicitacaoProcedimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Arquivo;
import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciadoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.Especialidade;
import br.ccasj.sisauc.administracao.cadastro.domain.EspecificacaoIsencaoCobranca;
import br.ccasj.sisauc.administracao.cadastro.domain.EstadoSolicitacaoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.LocalInternacao;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoBase;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoCBHPM2012;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoPedidoSolicitacao;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.service.CadastroSolicitacaoService;
import br.ccasj.sisauc.administracao.sistema.dao.UnidadeMultiplicadoraDao;
import br.ccasj.sisauc.administracao.sistema.domain.UnidadeMultiplicadora;
import br.ccasj.sisauc.auditoriaprospectiva.dao.AuditoriaProspectivaDAO;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectiva;
import br.ccasj.sisauc.emissaogab.dao.GuiaApresentacaoBeneficiarioDAO;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.EntidadeGenericaUtils;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;

@Scope(value = "view")
@Service(value = "cadastroSolicitacaoFormularioBean")
public class CadastroSolicitacaoFormularioBean implements Serializable {
	
	//TODO - refatorar este bean pois ele foi criado no começo do projeto
	//TODO -  Separar formulario de visualizacao

	private static final long serialVersionUID = 8530225410668272280L;

	@Autowired
	private SolicitacaoProcedimentoDAO solicitacaoDAO;
	@Autowired
	private ConfiguracaoEditalCredenciadoProcedimentoDAO configuracaoEditalCredenciadoProcedimentoDAO;
	@Autowired
	private EspecialidadeDAO especialidadeDAO;
	@Autowired
	private EspecificacaoIsencaoCobrancaDAO especificacaoIsencaoCobrancaDAO;
	@Autowired
	private CadastroSolicitacaoService cadastroSolicitacaoService;
	@Autowired
	private UnidadeMultiplicadoraDao unidadeMultiplicadoraDao;
	@Autowired
	private AuditoriaProspectivaDAO auditoriaProspectivaDAO;
	@Autowired
    private BeneficiarioDAO beneficiarioDAO;
	@Autowired
	private GuiaApresentacaoBeneficiarioDAO guiaApresentacaoBeneficiarioDAO;

	private SolicitacaoProcedimento solicitacao = new SolicitacaoProcedimento();
	private ProcedimentoPedidoSolicitacao pedido = new ProcedimentoPedidoSolicitacao();
	private ProcedimentoPedidoSolicitacao pedidoSelecionado = new ProcedimentoPedidoSolicitacao();
	private List<ConfiguracaoEditalCredenciadoProcedimento> credenciados = new ArrayList<ConfiguracaoEditalCredenciadoProcedimento>();
	private List<Especialidade> especialidades = new ArrayList<Especialidade>();
	private List<LocalInternacao> localInternacao;
	private List<EspecificacaoIsencaoCobranca> especificacoes;
	private UnidadeMultiplicadora unidadeMultiplicadora;
	private AuditoriaProspectiva auditoriaProspectiva;
	private ProcedimentoBase procedimento;
	private Especialidade especialidadeSelecionada;
	private List<ConfiguracaoEditalCredenciadoProcedimento> configuracoes = new ArrayList<ConfiguracaoEditalCredenciadoProcedimento>();
	private List<GuiaApresentacaoBeneficiario> gabsGeradas;
	private List<Divisao> divisoes = new ArrayList<Divisao>();

	@PostConstruct
	private void init() {
		setDivisoes(Arrays.asList(Divisao.values()));
		localInternacao = (Arrays.asList(LocalInternacao.values()));
		setEspecificacoes(especificacaoIsencaoCobrancaDAO.findAll());
		solicitacao = obterSolicitacao();
		especialidades = especialidadeDAO.listarEspecialidadesAtivasNaoOdontologicas();
		if (solicitacao.getEstado() == EstadoSolicitacaoProcedimento.AUDITADA) {
			this.auditoriaProspectiva = auditoriaProspectivaDAO.obterAuditoriaPorSolicitacaoComProcedimentos(solicitacao.getId());
			gabsGeradas = new ArrayList<GuiaApresentacaoBeneficiario>();
			gabsGeradas = guiaApresentacaoBeneficiarioDAO.obterGABsPorAuditoriaProspectiva(auditoriaProspectiva.getId());
		}
	}

	private SolicitacaoProcedimento obterSolicitacao() {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		return id.equals("novo") ? new SolicitacaoProcedimento() : solicitacaoDAO.abrirComPedidos(Integer.valueOf(id));
	}

	public void enviarAuditoria() {
		try {
			cadastroSolicitacaoService.verificarSolicitacaoPossuiArquivo(solicitacao);
			cadastroSolicitacaoService.enviarAuditoria(solicitacao);
			Mensagem.informacao("Solicitação Nº " + solicitacao.getNumero() + " enviada com sucesso!");
			ManagedBeanUtils.redirecionar("/atendente/cadastro/solicitacao");
		} catch (Exception e) {
			ManagedBeanUtils.hideDialog("enviarAuditoriaDialog");
			throw new SystemRuntimeException(e.getMessage());
		}
	}

	//TODO este metodo foi passado ao ViewUtilsBean
	public void subirArquivo(FileUploadEvent event) {
		try {
			Arquivo arquivo = new Arquivo();
			arquivo.setStream(event.getFile().getInputstream());
			arquivo.setNome(event.getFile().getFileName());
			arquivo.setTamanho(event.getFile().getInputstream().available());
			solicitacao.setArquivo(arquivo);
		} catch (IOException e) {
			throw new SystemRuntimeException("Falha ao subir arquivo: " + e.getLocalizedMessage());
		}
	}

	public void criarNovoPedido() {
		unidadeMultiplicadora = unidadeMultiplicadoraDao.obterUnidadeMultiplicadora();
		if (unidadeMultiplicadora == null) {
			throw new SystemRuntimeException("É necessário cadastrar uma unidade multiplicadora para adicionar um procedimento.");
		} else {
		pedido = new ProcedimentoPedidoSolicitacao();
		credenciados = new ArrayList<ConfiguracaoEditalCredenciadoProcedimento>();
		pedido.setId(EntidadeGenericaUtils.gerarIdNegativo());
		resetarConfiguracao();
		ManagedBeanUtils.showDialog("pedidoDialog");
		}
	}
	
	public void resetarEspecialidadeSelecionada(){
		if (procedimento instanceof ProcedimentoCBHPM2012 && !((ProcedimentoCBHPM2012)procedimento).getSubGrupo().getCodigo().equals("10101004")) {
			especialidadeSelecionada = null;
		}
	}

	private void resetarConfiguracao() {
		procedimento = null;
		configuracoes = null;
	}

	public void adicionarPedido(ConfiguracaoEditalCredenciadoProcedimento configuracao) {
		pedido.setCredenciado(configuracao);
		solicitacao.getProcedimentos().add(pedido);
		ManagedBeanUtils.hideDialog("pedidoDialog");
		Mensagem.informacao("Procedimento adicionado com sucesso");
	}

	public void editarPedido(ProcedimentoPedidoSolicitacao pedido) {
		this.pedido = pedido;
		ManagedBeanUtils.showDialog("pedidoDialog");
	}

	public void selecionarPedido(ProcedimentoPedidoSolicitacao pedido) {
		setPedidoSelecionado(pedido);
		ManagedBeanUtils.showDialog("removerPedidoDialog");
	}

	public void removerPedido() {
		solicitacao.getProcedimentos().remove(pedidoSelecionado);
		Mensagem.informacao("Procedimento removido com sucesso");
		ManagedBeanUtils.hideDialog("removerPedidoDialog");
	}
	
	public void verificarArquivo(){
		ManagedBeanUtils.showDialog("enviarAuditoriaDialog");
	}

	public void salvar() {
		cadastroSolicitacaoService.salvar(solicitacao);
		Mensagem.informacao("Solicitação Nº " + solicitacao.getNumero() + " salva com sucesso!");
		ManagedBeanUtils.redirecionar("/atendente/cadastro/solicitacao");
	}

	public void atualizarListaCredenciados() {
		credenciados = configuracaoEditalCredenciadoProcedimentoDAO
				.listarCredenciadosVigentesPorProcedimentoEspecialidade(pedido.getCredenciado().getProcedimento(),
						pedido.getCredenciado().getEspecialidade());
	}
	
	public void buscarConfiguracoesPorProcedimento(){
		configuracoes = (configuracaoEditalCredenciadoProcedimentoDAO.listarCredenciadosVigentesPorProcedimentoEspecialidade(procedimento, getEspecialidadeSelecionada()));
	}

	public void onSelectBeneficiario(SelectEvent event){
		Beneficiario selecionado = (Beneficiario) event.getObject();
		
		solicitacao.setBeneficiario(beneficiarioDAO.findById(selecionado.getId()));

		Beneficiario titular = solicitacao.getBeneficiario().getBeneficiarioTitular()==null ? solicitacao.getBeneficiario() : solicitacao.getBeneficiario().getBeneficiarioTitular();
		if ("ISENTO".equals(titular.getTipoContribuicao()) || "FATORCUSTO".equals(titular.getTipoContribuicao())){
			solicitacao.setIsento(true);
			solicitacao.setEspecificacao(getEspecificacaoMilitarNaoContribuinte());
		} else {
			solicitacao.setIsento(false);
		}
		
		
	}
	
	private EspecificacaoIsencaoCobranca getEspecificacaoMilitarNaoContribuinte() {
		for (EspecificacaoIsencaoCobranca especificacao: especificacoes){
			if(especificacao.getId()==1){
				return especificacao;
			}
		}
		return null;
	}

	public SolicitacaoProcedimento getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(SolicitacaoProcedimento solicitacao) {
		this.solicitacao = solicitacao;
	}

	public ProcedimentoPedidoSolicitacao getPedido() {
		return pedido;
	}

	public void setPedido(ProcedimentoPedidoSolicitacao pedido) {
		this.pedido = pedido;
	}

	public ProcedimentoPedidoSolicitacao getPedidoSelecionado() {
		return pedidoSelecionado;
	}

	public void setPedidoSelecionado(ProcedimentoPedidoSolicitacao pedidoSelecionado) {
		this.pedidoSelecionado = pedidoSelecionado;
	}

	public List<ConfiguracaoEditalCredenciadoProcedimento> getCredenciados() {
		return credenciados;
	}

	public void setCredenciados(List<ConfiguracaoEditalCredenciadoProcedimento> credenciados) {
		this.credenciados = credenciados;
	}

	/**
	 * @return the especialidades
	 */
	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}

	/**
	 * @param especialidades
	 *            the especialidades to set
	 */
	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	public List<LocalInternacao> getLocalInternacao() {
		return localInternacao;
	}

	public void setLocalInternacao(List<LocalInternacao> localInternacao) {
		this.localInternacao = localInternacao;
	}

	public UnidadeMultiplicadora getUnidadeMultiplicadora() {
		return unidadeMultiplicadora;
	}

	public void setUnidadeMultiplicadora(UnidadeMultiplicadora unidadeMultiplicadora) {
		this.unidadeMultiplicadora = unidadeMultiplicadora;
	}
	
	public AuditoriaProspectiva getAuditoriaProspectiva() {
		return auditoriaProspectiva;
	}
	
	public void setAuditoriaProspectiva(AuditoriaProspectiva auditoriaProspectiva) {
		this.auditoriaProspectiva = auditoriaProspectiva;
	}

	public List<EspecificacaoIsencaoCobranca> getEspecificacoes() {
		return especificacoes;
	}

	public void setEspecificacoes(List<EspecificacaoIsencaoCobranca> especificacoes) {
		this.especificacoes = especificacoes;
	}

	public ProcedimentoBase getProcedimento() {
		return procedimento;
	}

	public void setProcedimento(ProcedimentoBase procedimento) {
		this.procedimento = procedimento;
	}

	public Especialidade getEspecialidadeSelecionada() {
		return especialidadeSelecionada;
	}
	
	public void setEspecialidadeSelecionada(Especialidade especialidadeSelecionada) {
		this.especialidadeSelecionada = especialidadeSelecionada;
	}

	public List<ConfiguracaoEditalCredenciadoProcedimento> getConfiguracoes() {
		return configuracoes;
	}

	public void setConfiguracoes(List<ConfiguracaoEditalCredenciadoProcedimento> configuracoes) {
		this.configuracoes = configuracoes;
	}

	public List<GuiaApresentacaoBeneficiario> getGabsGeradas() {
		return gabsGeradas;
	}

	public void setGabsGeradas(List<GuiaApresentacaoBeneficiario> gabsGeradas) {
		this.gabsGeradas = gabsGeradas;
	}

	public List<Divisao> getDivisoes() {
		return divisoes;
	}

	public void setDivisoes(List<Divisao> divisoes) {
		this.divisoes = divisoes;
	}

}
