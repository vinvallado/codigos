package br.ccasj.sisauc.administracao.auditoriaprospectiva.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.EspecialidadeDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.EspecificacaoIsencaoCobrancaDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.SolicitacaoRessarcimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Especialidade;
import br.ccasj.sisauc.administracao.cadastro.domain.EspecificacaoIsencaoCobranca;
import br.ccasj.sisauc.administracao.cadastro.domain.FaceDental;
import br.ccasj.sisauc.administracao.cadastro.domain.LocalInternacao;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoBase;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoPedidoSolicitacaoRessarcimento;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoTRS;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoRessarcimento;
import br.ccasj.sisauc.administracao.cadastro.domain.Tabela;
import br.ccasj.sisauc.administracao.formatter.ProcedimentoFormatter;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectivaRessarcimento;
import br.ccasj.sisauc.auditoriaprospectiva.domain.EstadoAuditoriaProspectiva;
import br.ccasj.sisauc.auditoriaprospectiva.domain.RespostaRessarcimentoAuditoria;
import br.ccasj.sisauc.auditoriaprospectiva.service.RealizarAuditoriaProspectivaRessarcimentoService;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.EntidadeGenericaUtils;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;
import br.ccasj.sisauc.framework.utils.managedbean.ViewUtilsBean;

@Scope("view")
@Service("realizarAuditoriaProspectivaRessarcimentoBean")
public class RealizarAuditoriaProspectivaRessarcimentoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private transient SolicitacaoRessarcimentoDAO solicitacaoRessarcimentoDAO;
	@Autowired
	private transient EspecificacaoIsencaoCobrancaDAO especificacaoIsencaoCobrancaDAO;
	@Autowired
	private transient RealizarAuditoriaProspectivaRessarcimentoService realizarAuditoriaProspectivaRessarcimentoService;
	@Autowired
	private transient EspecialidadeDAO especialidadeDAO;
	
	private AuditoriaProspectivaRessarcimento auditoria = new AuditoriaProspectivaRessarcimento();
	private List<EspecificacaoIsencaoCobranca> especificacoes;
	private RespostaRessarcimentoAuditoria resultadoSelecionado = new RespostaRessarcimentoAuditoria();
	private RespostaRessarcimentoAuditoria resposta = new RespostaRessarcimentoAuditoria();
	private List<Especialidade> especialidades = new ArrayList<Especialidade>();
	private int quantidadeRespostas = 1;
	private ProcedimentoTRS procedimentoTRSSelecionado;
	private List<FaceDental> faces = new ArrayList<FaceDental>();
	
	private List<RespostaRessarcimentoAuditoria> itensList = new ArrayList<RespostaRessarcimentoAuditoria>();

	@PostConstruct
	private void init() {
		auditoria.setSolicitacao(obterSolicitacao());
		auditoria.setIsento(auditoria.getSolicitacao().isIsento());
		auditoria.setInternacao(auditoria.getSolicitacao().isInternacao());
		auditoria.setLocalInternacao(auditoria.getSolicitacao().getLocalInternacao());
		auditoria.setEspecificacao(auditoria.getSolicitacao().getEspecificacao());
		especificacoes = especificacaoIsencaoCobrancaDAO.findAll();
		especialidades = especialidadeDAO.listarEspecialidadesAtivasNaoOdontologicas();
		faces = Arrays.asList(FaceDental.values());
		preCarregarResultados();
		atualizarItensList();
	}
	
	private SolicitacaoRessarcimento obterSolicitacao() {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		return solicitacaoRessarcimentoDAO.abrirComPedidos(Integer.valueOf(id));
	}
	
	public void atualizarItensList(){
		setItensList(new ArrayList<RespostaRessarcimentoAuditoria>(auditoria.getProcedimentos()));
	}

	private void preCarregarResultados() {
		for (ProcedimentoPedidoSolicitacaoRessarcimento pedido : auditoria.getSolicitacao().getProcedimentos()) {
			for (int i = 0; i < pedido.getQuantidade(); i++) {
				RespostaRessarcimentoAuditoria resultado = new RespostaRessarcimentoAuditoria(pedido);
				resultado.setId(EntidadeGenericaUtils.gerarIdNegativo());
				auditoria.getProcedimentos().add(resultado);
			}
		}
	}
	
	public void prepararResultadoAuditoria() {
		realizarAuditoriaProspectivaRessarcimentoService.validarFinalizacao(auditoria);
		auditoria.setEstado(realizarAuditoriaProspectivaRessarcimentoService.obterResultadoAuditoria(auditoria.getProcedimentos()));
		ManagedBeanUtils.showDialog("finalizarDialog");
	}
	

	public void salvar() {
		auditoria.setIsento(auditoria.getSolicitacao().isIsento());
		auditoria.setEspecificacao(auditoria.getSolicitacao().getEspecificacao());
		auditoria = realizarAuditoriaProspectivaRessarcimentoService.finalizarAuditoria(auditoria);
		ManagedBeanUtils.redirecionar("/auditoria-prospectiva-ressarcimento/pendentes");
		if (EstadoAuditoriaProspectiva.APROVADO.equals(auditoria.getEstado())){
			Mensagem.informacao(realizarAuditoriaProspectivaRessarcimentoService.gerarMensagemSucessoComNumerosAR(auditoria.getId()));
		} else {
			Mensagem.informacao(auditoria.getSolicitacao().getNumero() + " reprovada com sucesso.");
		}
		atualizarItensList();
	}
	
	public void removerResultado(RespostaRessarcimentoAuditoria resultado) {
		auditoria.getProcedimentos().remove(resultado);
		Mensagem.informacao("Procedimento removido com sucesso");
		atualizarItensList();
	}
	
	public void criarNovaResposta() {
		quantidadeRespostas = 1;
		resposta = new RespostaRessarcimentoAuditoria();
		resposta.setId(EntidadeGenericaUtils.gerarIdNegativo());
		ManagedBeanUtils.showDialog("adicionarProcedimentoDialog");
		atualizarItensList();
	}
	
	public void addResposta() {
		auditoria.getProcedimentos().add(resposta);
		for (int i=2; i<=quantidadeRespostas; i++){
			RespostaRessarcimentoAuditoria copia = copiarResposta(resposta);
			auditoria.getProcedimentos().add(copia);
		}
		Mensagem.informacao("Procedimento adicionado com sucesso");
		ManagedBeanUtils.hideDialog("adicionarProcedimentoDialog");
		atualizarItensList();
	}
	
	public void auditarItem() {
		if(procedimentoTRSSelecionado != null){
			resposta.setProcedimento(procedimentoTRSSelecionado);
		}
		if(resposta.getProcedimento() == null){
			throw new SystemRuntimeException("Selecione um procedimento pertencente a uma tabela.");
		}
		if(Boolean.TRUE.equals(resposta.getAprovado()) && (resposta.getProcedimento() == null || !(resposta.getProcedimento() instanceof ProcedimentoTRS))){
			throw new SystemRuntimeException("Selecione um procedimento TRS");
		}
		Mensagem.informacao("Procedimento auditado com sucesso");
		ManagedBeanUtils.hideDialog("respostaDialog");
		atualizarItensList();
	}
	
	private RespostaRessarcimentoAuditoria copiarResposta(RespostaRessarcimentoAuditoria resposta) {
		RespostaRessarcimentoAuditoria copia = new RespostaRessarcimentoAuditoria();
		copia.setId(EntidadeGenericaUtils.gerarIdNegativo());
		copia.setProcedimento(resposta.getProcedimento());
		copia.setEspecialidade(resposta.getEspecialidade());
		copia.setDescricaoOutros(resposta.getDescricaoOutros());
		copia.setOrcamento(resposta.getOrcamento());
		copia.setObservacao(resposta.getObservacao());
		copia.setObservacaoARE(resposta.getObservacaoARE());
		return copia;
	}

	public void selecionarResultadoJustificativa(RespostaRessarcimentoAuditoria resposta) {
		resultadoSelecionado = resposta;
		if (resultadoSelecionado.getJustificativa() != null) {
			ManagedBeanUtils.showDialog("justificativaDialog");
		}
	}
	
	public void editarResposta(RespostaRessarcimentoAuditoria respostaRessarcimento) {
		procedimentoTRSSelecionado = null;
		resposta = respostaRessarcimento;
		ManagedBeanUtils.obter("viewUtilsBean", ViewUtilsBean.class).setTipoTabelaAutocomplete(Tabela.TRS.name());
		ManagedBeanUtils.showDialog("respostaDialog");
		atualizarItensList();
	}

	public void informarInconsistencia() {
		realizarAuditoriaProspectivaRessarcimentoService.informarInconsistencia(auditoria.getSolicitacao());
		Mensagem.informacao("Inconsistência informada com sucesso");
		ManagedBeanUtils.redirecionar("/auditoria-prospectiva-ressarcimento/pendentes");
	}	
	
	public int sortByModel(Object procedimento, Object procedimento2){
		ProcedimentoFormatter formatter = new ProcedimentoFormatter();
		return formatter.getDescricaoCompleta((ProcedimentoBase) procedimento)
				.compareTo(formatter.getDescricaoCompleta((ProcedimentoBase)procedimento2));
	}
	
	public void onSelectTabela(){
		resposta.setEspecialidade(null);
		resposta.setProcedimento(null);
		resposta.setDescricaoOutros(null);
	}
	
	public void onSelectProcedimento(){
		resposta.setEspecialidade(null);
		resposta.setProcedimento(null);
		resposta.setDescricaoOutros(null);
	}
	
	public LocalInternacao[] getLocaisInternacao() {
		return LocalInternacao.values();
	}
	
	public AuditoriaProspectivaRessarcimento getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(AuditoriaProspectivaRessarcimento auditoria) {
		this.auditoria = auditoria;
	}

	public List<EspecificacaoIsencaoCobranca> getEspecificacoes() {
		return especificacoes;
	}

	public void setEspecificacoes(List<EspecificacaoIsencaoCobranca> especificacoes) {
		this.especificacoes = especificacoes;
	}

	public RespostaRessarcimentoAuditoria getResultadoSelecionado() {
		return resultadoSelecionado;
	}

	public void setResultadoSelecionado(RespostaRessarcimentoAuditoria resultadoSelecionado) {
		this.resultadoSelecionado = resultadoSelecionado;
	}

	public RespostaRessarcimentoAuditoria getResposta() {
		return resposta;
	}

	public void setResposta(RespostaRessarcimentoAuditoria resposta) {
		this.resposta = resposta;
	}

	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	public int getQuantidadeRespostas() {
		return quantidadeRespostas;
	}

	public void setQuantidadeRespostas(int quantidadeRespostas) {
		this.quantidadeRespostas = quantidadeRespostas;
	}

	public ProcedimentoTRS getProcedimentoTRSSelecionado() {
		return procedimentoTRSSelecionado;
	}

	public void setProcedimentoTRSSelecionado(ProcedimentoTRS procedimentoTRSSelecionado) {
		this.procedimentoTRSSelecionado = procedimentoTRSSelecionado;
	}

	public List<RespostaRessarcimentoAuditoria> getItensList() {
		return itensList;
	}

	public void setItensList(List<RespostaRessarcimentoAuditoria> itensList) {
		this.itensList = itensList;
	}

	public List<FaceDental> getFaces() {
		return faces;
	}

	public void setFaces(List<FaceDental> faces) {
		this.faces = faces;
	}

}
