package br.ccasj.sisauc.administracao.auditoriaprospectiva.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.ConfiguracaoEditalCredenciadoProcedimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.EspecialidadeDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.EspecificacaoIsencaoCobrancaDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.SolicitacaoProcedimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciadoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.Especialidade;
import br.ccasj.sisauc.administracao.cadastro.domain.EspecificacaoIsencaoCobranca;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoBase;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoCBHPM2012;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoPedidoSolicitacao;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoProcedimento;
import br.ccasj.sisauc.administracao.formatter.ConfiguracaoEditalCredenciadoProcedimentoFormatter;
import br.ccasj.sisauc.administracao.sistema.dao.UnidadeMultiplicadoraDao;
import br.ccasj.sisauc.administracao.sistema.domain.UnidadeMultiplicadora;
import br.ccasj.sisauc.auditoriaprospectiva.dao.AuditoriaProspectivaBaseDAO;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectiva;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectivaBase;
import br.ccasj.sisauc.auditoriaprospectiva.domain.RespostaProcedimentoAuditoria;
import br.ccasj.sisauc.auditoriaprospectiva.service.RealizarAuditoriaProspectivaService;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.EntidadeGenericaUtils;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;

@Scope(value = "view")
@Service(value = "realizarAuditoriaProspectivaBean")
public class RealizarAuditoriaProspectivaBean implements Serializable {

	private static final long serialVersionUID = 8031427213234917308L;

	@Autowired
	private SolicitacaoProcedimentoDAO solicitacaoDAO;
	@Autowired
	private AuditoriaProspectivaBaseDAO auditoriaProspectivaBaseDAO;
	@Autowired
	private RealizarAuditoriaProspectivaService realizarAuditoriaProspectivaService;
	@Autowired
	private ConfiguracaoEditalCredenciadoProcedimentoDAO configuracaoEditalCredenciadoProcedimentoDAO;
	@Autowired
	private UnidadeMultiplicadoraDao unidadeMultiplicadoraDao;
	@Autowired
	private EspecificacaoIsencaoCobrancaDAO especificacaoIsencaoCobrancaDAO;
	@Autowired
	private EspecialidadeDAO especialidadeDAO;

	private boolean auditoriaEmLote = false;
	private AuditoriaProspectiva auditoria = new AuditoriaProspectiva();
	private RespostaProcedimentoAuditoria resultadoSelecionado = new RespostaProcedimentoAuditoria();
	private RespostaProcedimentoAuditoria resposta = new RespostaProcedimentoAuditoria();
	private UnidadeMultiplicadora unidadeMultiplicadora;
	private ProcedimentoBase procedimento;
	private Especialidade especialidadeSelecionada;
	private List<EspecificacaoIsencaoCobranca> especificacoes;
	private List<AuditoriaProspectivaBase> auditorias = new ArrayList<AuditoriaProspectivaBase>();
	private List<ConfiguracaoEditalCredenciadoProcedimento> credenciados = new ArrayList<ConfiguracaoEditalCredenciadoProcedimento>();
	private List<ConfiguracaoEditalCredenciadoProcedimento> configuracoes = new ArrayList<ConfiguracaoEditalCredenciadoProcedimento>();
	private List<Especialidade> especialidades = new ArrayList<Especialidade>();
	private String procedimentoPesquisa = "";
	private List<RespostaProcedimentoAuditoria> respostasSelecionadas = new ArrayList<RespostaProcedimentoAuditoria>();
	
	private List<RespostaProcedimentoAuditoria> itensList = new ArrayList<RespostaProcedimentoAuditoria>();

	@PostConstruct
	private void init() {
		auditoria.setSolicitacao(obterSolicitacao());
		auditorias = auditoriaProspectivaBaseDAO.listarAuditoriasPorBeneficiario(
				auditoria.getSolicitacao().getBeneficiario().getId(), procedimentoPesquisa);
		preCarregarResultados();
		setEspecificacoes(especificacaoIsencaoCobrancaDAO.findAll());
		especialidades = especialidadeDAO.listarEspecialidadesAtivasNaoOdontologicas();
		atualizarItensList();
	}

	public void atualizarItensList(){
		setItensList(new ArrayList<RespostaProcedimentoAuditoria>(auditoria.getProcedimentos()));
	}
	
	private void preCarregarResultados() {
		for (ProcedimentoPedidoSolicitacao pedido : auditoria.getSolicitacao().getProcedimentos()) {
			for (int i = 0; i < pedido.getQuantidade(); i++) {
				RespostaProcedimentoAuditoria resultado = new RespostaProcedimentoAuditoria(pedido);
				resultado.setId(EntidadeGenericaUtils.gerarIdNegativo());
				auditoria.getProcedimentos().add(resultado);
			}
		}
	}

	private SolicitacaoProcedimento obterSolicitacao() {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		return solicitacaoDAO.abrirComPedidos(Integer.valueOf(id));
	}

	public void pesquisarHistorico() {
		auditorias = auditoriaProspectivaBaseDAO.listarAuditoriasPorBeneficiario(
				auditoria.getSolicitacao().getBeneficiario().getId(), procedimentoPesquisa);
	}

	public void criarNovoResultado() {
		unidadeMultiplicadora = unidadeMultiplicadoraDao.obterUnidadeMultiplicadora();
		if (unidadeMultiplicadora == null) {
			throw new SystemRuntimeException(
					"É necessário cadastrar uma unidade multiplicadora para adicionar um procedimento.");
		} else {
			resetarConfiguracao();
			resposta = new RespostaProcedimentoAuditoria();
			resposta.setId(EntidadeGenericaUtils.gerarIdNegativo());
			ManagedBeanUtils.showDialog("adicionarProcedimentoDialog");
			setAuditoriaEmLote(false);
		}
	}

	private void resetarConfiguracao() {
		procedimento = null;
		configuracoes = null;
	}

	public void resetarEspecialidadeSelecionada() {
		if (procedimento instanceof ProcedimentoCBHPM2012
				&& !((ProcedimentoCBHPM2012) procedimento).getSubGrupo().getCodigo().equals("10101004")) {
			especialidadeSelecionada = null;
		}
	}

	public void adicionarResultado() {
		auditoria.getProcedimentos().add(resposta);
		Mensagem.informacao("Procedimento auditado com sucesso");
		ManagedBeanUtils.hideDialog("respostaDialog");
		setAuditoriaEmLote(false);
		atualizarItensList();
	}

	public void adicionarResultadoEmLote(){
		for (RespostaProcedimentoAuditoria selecionada : respostasSelecionadas) {
			auditoria.getProcedimentos().add(copiarResultado(resposta, selecionada));
		}
		Mensagem.informacao("Procedimentos auditados com sucesso");
		ManagedBeanUtils.hideDialog("respostaDialog");
		respostasSelecionadas = new ArrayList<RespostaProcedimentoAuditoria>();
		setAuditoriaEmLote(false);
		atualizarItensList();
	}
	
	private RespostaProcedimentoAuditoria copiarResultado(RespostaProcedimentoAuditoria origem, RespostaProcedimentoAuditoria destino){
		destino.setAprovado(origem.getAprovado());
		destino.setJustificativa(origem.getJustificativa());
		destino.setObservacaoGAB(origem.getObservacaoGAB());
		destino.setOpme(origem.isOpme());
		destino.setOpmeDescricao(origem.getOpmeDescricao());
		destino.setOpmeJustificativa(origem.getOpmeJustificativa());
		destino.setOpmeValor(origem.getOpmeValor());
		return destino;
	}
	
	public void editarResultado(RespostaProcedimentoAuditoria respostaProcedimento) {
		setAuditoriaEmLote(false);
		resposta = respostaProcedimento;
		ManagedBeanUtils.showDialog("respostaDialog");
		atualizarItensList();
	}

	public void selecionarResultado(RespostaProcedimentoAuditoria resultado) {
		resultadoSelecionado = resultado;
		ManagedBeanUtils.showDialog("removerResultadoDialog");
	}

	public void selecionarResultadoJustificativa(RespostaProcedimentoAuditoria resultado) {
		resultadoSelecionado = resultado;
		if (resultadoSelecionado.getJustificativa() != null) {
			ManagedBeanUtils.showDialog("justificativaDialog");
		}
	}

	public void removerResultado() {
		auditoria.getProcedimentos().remove(resultadoSelecionado);
		Mensagem.informacao("Procedimento removido com sucesso");
		ManagedBeanUtils.hideDialog("removerResultadoDialog");
		atualizarItensList();
	}

	public void atualizarListaCredenciados() {
		credenciados = configuracaoEditalCredenciadoProcedimentoDAO
				.listarCredenciadosVigentesPorProcedimentoEspecialidade(resposta.getCredenciado().getProcedimento(),
						resposta.getCredenciado().getEspecialidade());
	}

	public void prepararResultadoAuditoria() {
		realizarAuditoriaProspectivaService.validarAprovacaoRespostas(auditoria.getProcedimentos());
		auditoria.setEstado(realizarAuditoriaProspectivaService.obterResultadoAuditoria(auditoria.getProcedimentos()));
		ManagedBeanUtils.showDialog("finalizarDialog");
	}

	public void salvar() {
		auditoria.setIsento(auditoria.getSolicitacao().isIsento());
		auditoria.setEspecificacao(auditoria.getSolicitacao().getEspecificacao());
		auditoria = realizarAuditoriaProspectivaService.finalizarAuditoria(auditoria);
		ManagedBeanUtils.redirecionar("/auditoria-prospectiva/pendentes");
		Mensagem.informacao(realizarAuditoriaProspectivaService.gerarMensagemSucessoComNumerosGAB(auditoria.getId()));
		atualizarItensList();
	}

	public void informarInconsistencia() {
		realizarAuditoriaProspectivaService.informarInconsistencia(auditoria.getSolicitacao());
		Mensagem.informacao("Inconsistência informada com sucesso");
		ManagedBeanUtils.redirecionar("/auditoria-prospectiva/pendentes");
	}

	public void buscarConfiguracoesPorProcedimento() {
		configuracoes = (configuracaoEditalCredenciadoProcedimentoDAO.listarCredenciadosVigentesPorProcedimentoEspecialidade(procedimento, especialidadeSelecionada));
	}

	public void adicionarProcedimento(ConfiguracaoEditalCredenciadoProcedimento configuracao) {
		resposta.setCredenciado(configuracao);
		auditoria.getProcedimentos().add(resposta);
		ManagedBeanUtils.hideDialog("adicionarProcedimentoDialog");
		Mensagem.informacao("Procedimento adicionado com sucesso");
		atualizarItensList();
	}

	public void auditarEmLote(){
		validarRespostasSelecionadas();
		setAuditoriaEmLote(true);
		resposta = new RespostaProcedimentoAuditoria();
		resposta.setCredenciado(respostasSelecionadas.get(0).getCredenciado());
		ManagedBeanUtils.showDialog("respostaDialog");
	}
	
	private void validarRespostasSelecionadas() {
		if(respostasSelecionadas.size() < 2){
			throw new SystemRuntimeException("Selecione ao menos dois procedimentos para realizar auditoria em lote.");
		}
		ConfiguracaoEditalCredenciadoProcedimento procedimentoSelecionado = respostasSelecionadas.get(0).getCredenciado();
		for (RespostaProcedimentoAuditoria resposta : respostasSelecionadas) {
			if(!resposta.getCredenciado
					().equals(procedimentoSelecionado)){
				throw new SystemRuntimeException("Para realizar auditoria em lote é necessário que todos os itens selecionados possuam o mesmo procedimento e credenciado.");
			}
		}
	}
	
	public int sortByModel(Object procedimento, Object procedimento2){
		ConfiguracaoEditalCredenciadoProcedimentoFormatter formatter = new ConfiguracaoEditalCredenciadoProcedimentoFormatter();
		return formatter
				.getCodigoNomeSiglaEspecialidade((ConfiguracaoEditalCredenciadoProcedimento) procedimento)
				.compareTo(formatter.getCodigoNomeSiglaEspecialidade((ConfiguracaoEditalCredenciadoProcedimento)procedimento2));
	}

	public AuditoriaProspectiva getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(AuditoriaProspectiva auditoria) {
		this.auditoria = auditoria;
	}

	public RespostaProcedimentoAuditoria getResultadoSelecionado() {
		return resultadoSelecionado;
	}

	public void setResultadoSelecionado(RespostaProcedimentoAuditoria resultadoSelecionado) {
		this.resultadoSelecionado = resultadoSelecionado;
	}

	public RespostaProcedimentoAuditoria getResposta() {
		return resposta;
	}

	public void setResposta(RespostaProcedimentoAuditoria resposta) {
		this.resposta = resposta;
	}

	public List<ConfiguracaoEditalCredenciadoProcedimento> getCredenciados() {
		return credenciados;
	}

	public void setCredenciados(List<ConfiguracaoEditalCredenciadoProcedimento> credenciados) {
		this.credenciados = credenciados;
	}

	public List<AuditoriaProspectivaBase> getAuditorias() {
		return auditorias;
	}

	public void setAuditorias(List<AuditoriaProspectivaBase> auditorias) {
		this.auditorias = auditorias;
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

	public List<EspecificacaoIsencaoCobranca> getEspecificacoes() {
		return especificacoes;
	}

	public void setEspecificacoes(List<EspecificacaoIsencaoCobranca> especificacoes) {
		this.especificacoes = especificacoes;
	}

	public List<ConfiguracaoEditalCredenciadoProcedimento> getConfiguracoes() {
		return configuracoes;
	}

	public void setConfiguracoes(List<ConfiguracaoEditalCredenciadoProcedimento> configuracoes) {
		this.configuracoes = configuracoes;
	}

	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	public String getProcedimentoPesquisa() {
		return procedimentoPesquisa;
	}

	public void setProcedimentoPesquisa(String procedimentoPesquisa) {
		this.procedimentoPesquisa = procedimentoPesquisa;
	}

	public List<RespostaProcedimentoAuditoria> getRespostasSelecionadas() {
		return respostasSelecionadas;
	}

	public void setRespostasSelecionadas(List<RespostaProcedimentoAuditoria> respostasSelecionadas) {
		this.respostasSelecionadas = respostasSelecionadas;
	}

	public boolean isAuditoriaEmLote() {
		return auditoriaEmLote;
	}

	public void setAuditoriaEmLote(boolean auditoriaEmLote) {
		this.auditoriaEmLote = auditoriaEmLote;
	}

	public List<RespostaProcedimentoAuditoria> getItensList() {
		return itensList;
	}

	public void setItensList(List<RespostaProcedimentoAuditoria> itensList) {
		this.itensList = itensList;
	}

}
