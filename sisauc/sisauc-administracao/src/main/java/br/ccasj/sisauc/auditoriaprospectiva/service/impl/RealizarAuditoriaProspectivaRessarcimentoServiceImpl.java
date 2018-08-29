package br.ccasj.sisauc.auditoriaprospectiva.service.impl;

import java.util.Date;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.SolicitacaoRessarcimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.EstadoSolicitacaoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoTRS;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoRessarcimento;
import br.ccasj.sisauc.administracao.cadastro.domain.Tabela;
import br.ccasj.sisauc.auditoriaprospectiva.dao.AuditoriaProspectivaRessarcimentoDAO;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectivaRessarcimento;
import br.ccasj.sisauc.auditoriaprospectiva.domain.EstadoAuditoriaProspectiva;
import br.ccasj.sisauc.auditoriaprospectiva.domain.RespostaRessarcimentoAuditoria;
import br.ccasj.sisauc.auditoriaprospectiva.service.RealizarAuditoriaProspectivaRessarcimentoService;
import br.ccasj.sisauc.emissaoar.dao.AutorizacaoRessarcimentoDAO;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.service.GerarARService;
import br.ccasj.sisauc.framework.context.AuthenticationContext;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;

@Transactional
@Service(value = "realizarAuditoriaProspectivaRessarcimentoService")
public class RealizarAuditoriaProspectivaRessarcimentoServiceImpl implements RealizarAuditoriaProspectivaRessarcimentoService{

	@Autowired
	private AuthenticationContext authenticationContext;
	@Autowired
	private SolicitacaoRessarcimentoDAO solicitacaoRessarcimentoDAO;
	@Autowired
	private AuditoriaProspectivaRessarcimentoDAO auditoriaProspectivaRessarcimentoDAO;
	@Autowired
	private GerarARService gerarARService;
	@Autowired
	private AutorizacaoRessarcimentoDAO arDAO;
	
	@Override
	public EstadoAuditoriaProspectiva obterResultadoAuditoria(Set<RespostaRessarcimentoAuditoria> respostas) {
		for (RespostaRessarcimentoAuditoria resposta : respostas) {
			if(Boolean.TRUE.equals(resposta.getAprovado())){
				return EstadoAuditoriaProspectiva.APROVADO;
			}
		}
		return EstadoAuditoriaProspectiva.REPROVADO;
	}

	@Override
	public AuditoriaProspectivaRessarcimento finalizarAuditoria(AuditoriaProspectivaRessarcimento auditoriaProspectivaRessarcimento) {
		verificarRespostas(auditoriaProspectivaRessarcimento.getProcedimentos());
		auditoriaProspectivaRessarcimento.setAuditor(authenticationContext.getUsuarioLogado());
		auditoriaProspectivaRessarcimento.setDataFinalAuditoria(new Date());
 		auditoriaProspectivaRessarcimento.setEstado(obterResultadoAuditoria(auditoriaProspectivaRessarcimento.getProcedimentos()));
		solicitacaoRessarcimentoDAO.atualizarEstado(EstadoSolicitacaoProcedimento.AUDITADA, auditoriaProspectivaRessarcimento.getSolicitacao().getId());
		auditoriaProspectivaRessarcimento = auditoriaProspectivaRessarcimentoDAO.merge(auditoriaProspectivaRessarcimento);
		if (EstadoAuditoriaProspectiva.APROVADO.equals(auditoriaProspectivaRessarcimento.getEstado())){
			gerarARService.gerarAPartirAuditoriaProspectiva(auditoriaProspectivaRessarcimento.getId());
		}
		return auditoriaProspectivaRessarcimento;
	}
	
	private void verificarRespostas(Set<RespostaRessarcimentoAuditoria> procedimentos) {
		for (RespostaRessarcimentoAuditoria respostaProcedimentoAuditoria : procedimentos) {
			verificarOPME(respostaProcedimentoAuditoria);
			verificarJustificativa(respostaProcedimentoAuditoria);
			verificarProcedimentoPadrao(respostaProcedimentoAuditoria);
		}
	}
	
	private void verificarProcedimentoPadrao(RespostaRessarcimentoAuditoria respostaRessarcimentoAuditoria) {
		if(respostaRessarcimentoAuditoria.getProcedimento().getId().equals(9871) && (
				respostaRessarcimentoAuditoria.getObservacaoARE() == null || 
				"".equals(respostaRessarcimentoAuditoria.getObservacaoARE().trim()) || 
				respostaRessarcimentoAuditoria.getValorRerenciaAuditor() == null )){
			throw new SystemRuntimeException("É necessário inserir um valor referência ou uma observação da are");
		}
	}
	
	private void verificarOPME(RespostaRessarcimentoAuditoria respostaRessarcimentoAuditoria) {
		if(respostaRessarcimentoAuditoria.isOpme() && (
				respostaRessarcimentoAuditoria.getOpmeDescricao() == null || 
				"".equals(respostaRessarcimentoAuditoria.getOpmeDescricao().trim()) || 
				respostaRessarcimentoAuditoria.getOpmeValor() == null ||
				respostaRessarcimentoAuditoria.getOpmeJustificativa() == null || 
				"".equals(respostaRessarcimentoAuditoria.getOpmeJustificativa().trim()))){
			throw new SystemRuntimeException("É necessário inserir um valor ou uma justificativa ou uma descrição ao OPME");
		}
	}

	private void verificarJustificativa(RespostaRessarcimentoAuditoria respostaRessarcimentoAuditoria) {
		if(!Boolean.TRUE.equals(respostaRessarcimentoAuditoria.getAprovado()) && (
				respostaRessarcimentoAuditoria.getJustificativa() == null || 
				"".equals(respostaRessarcimentoAuditoria.getJustificativa().trim()) )){
			throw new SystemRuntimeException("É necessário inserir uma justificativa ao reprovar um procedimento");
		}
	}

	@Override
	public void informarInconsistencia(SolicitacaoRessarcimento solicitacaoRessarcimento) {
		if(solicitacaoRessarcimento.getTextoInconsistencia() == null || solicitacaoRessarcimento.getTextoInconsistencia().equals("")){
			throw new SystemRuntimeException("É necessário inserir um texto de inconsistência!");
		} else {
			solicitacaoRessarcimento.setEstado(EstadoSolicitacaoProcedimento.INCONSISTENTE);
			solicitacaoRessarcimentoDAO.merge(solicitacaoRessarcimento);
		}
	}

	@Override
	public void validarFinalizacao(AuditoriaProspectivaRessarcimento auditoria){
		validarAprovacaoRespostas(auditoria.getProcedimentos());
		validarProcedimentosComInternacao(auditoria);
	}
	
	private void validarAprovacaoRespostas(Set<RespostaRessarcimentoAuditoria> procedimentos) {
		for (RespostaRessarcimentoAuditoria resposta: procedimentos){
			if (resposta.getAprovado()==null){
				throw new SystemRuntimeException("É obrigatório auditar todos itens.");
			}
		}
	}
	
	private void validarProcedimentosComInternacao(AuditoriaProspectivaRessarcimento auditoria) {
		for (RespostaRessarcimentoAuditoria resposta: auditoria.getProcedimentos()){
			if (resposta.getProcedimento() != null && resposta.getAprovado() &&
					resposta.getProcedimento().getTabela().equals(Tabela.TRS) &&
					((ProcedimentoTRS)resposta.getProcedimento()).isInternacao() && 
					!auditoria.isInternacao()){
				throw new SystemRuntimeException("Existem procedimentos TRS adicionados que exigem internação. " + 
						"Marque a opção 'Internação' e selecione a 'Acomodação'.");
			}
		}
	}
	
	@Override
	public String gerarMensagemSucessoComNumerosAR(Integer idAuditoria) {
		AutorizacaoRessarcimento ar = arDAO.obterARPorAuditoriaProspectiva(idAuditoria);
		StringBuilder builder = new StringBuilder();
		builder.append("Auditoria realizada com sucesso. Foi gerada a ARE: ");
		builder.append("Nº " + ar.getCodigo() + ". ");
		return builder.toString();
	}	


}
