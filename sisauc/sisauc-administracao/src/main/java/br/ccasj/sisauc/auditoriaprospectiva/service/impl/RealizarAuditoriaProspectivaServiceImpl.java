package br.ccasj.sisauc.auditoriaprospectiva.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.SolicitacaoProcedimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.EstadoSolicitacaoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoProcedimento;
import br.ccasj.sisauc.auditoriaprospectiva.dao.AuditoriaProspectivaDAO;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectiva;
import br.ccasj.sisauc.auditoriaprospectiva.domain.EstadoAuditoriaProspectiva;
import br.ccasj.sisauc.auditoriaprospectiva.domain.RespostaProcedimentoAuditoria;
import br.ccasj.sisauc.auditoriaprospectiva.service.RealizarAuditoriaProspectivaService;
import br.ccasj.sisauc.emissaogab.dao.GuiaApresentacaoBeneficiarioDAO;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.emissaogab.service.GerarGABService;
import br.ccasj.sisauc.framework.context.AuthenticationContext;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;

@Transactional
@Service(value = "realizarAuditoriaProspectivaService")
public class RealizarAuditoriaProspectivaServiceImpl implements RealizarAuditoriaProspectivaService {

	@Autowired
	private AuthenticationContext authenticationContext;
	
	@Autowired
	private SolicitacaoProcedimentoDAO solicitacaoDAO;
	@Autowired
	private AuditoriaProspectivaDAO auditoriaProspectivaDAO;
	@Autowired
	private GerarGABService gerarGABService;
	@Autowired
	private GuiaApresentacaoBeneficiarioDAO guiaApresentacaoBeneficiarioDAO;
	
	@Override
	public void informarInconsistencia(SolicitacaoProcedimento solicitacaoProcedimento) {
		if(solicitacaoProcedimento.getTextoInconsistencia() == null || solicitacaoProcedimento.getTextoInconsistencia().equals("")){
			throw new SystemRuntimeException("É necessário inserir um texto de inconsistência!");
		} else {
			solicitacaoProcedimento.setEstado(EstadoSolicitacaoProcedimento.INCONSISTENTE);
			solicitacaoDAO.merge(solicitacaoProcedimento);
		}
	}

	@Override
	public AuditoriaProspectiva finalizarAuditoria(AuditoriaProspectiva auditoriaProspectiva) {
		verificarRespostas(auditoriaProspectiva.getProcedimentos());
		auditoriaProspectiva.setAuditor(authenticationContext.getUsuarioLogado());
		auditoriaProspectiva.setDataFinalAuditoria(new Date());
		auditoriaProspectiva.setEstado(obterResultadoAuditoria(auditoriaProspectiva.getProcedimentos()));
		solicitacaoDAO.atualizarEstado(EstadoSolicitacaoProcedimento.AUDITADA, auditoriaProspectiva.getSolicitacao().getId());
		auditoriaProspectiva = auditoriaProspectivaDAO.merge(auditoriaProspectiva);
		gerarGABService.gerarAPartirAuditoriaProspectiva(auditoriaProspectiva.getId());
		return auditoriaProspectiva;
	}

	private void verificarRespostas(Set<RespostaProcedimentoAuditoria> procedimentos) {
		for (RespostaProcedimentoAuditoria respostaProcedimentoAuditoria : procedimentos) {
			verificarOPME(respostaProcedimentoAuditoria);
			verificarJustificativa(respostaProcedimentoAuditoria);
		}
	}

	private void verificarOPME(RespostaProcedimentoAuditoria respostaProcedimentoAuditoria) {
		if(respostaProcedimentoAuditoria.isOpme() && (
				respostaProcedimentoAuditoria.getOpmeDescricao() == null || 
				"".equals(respostaProcedimentoAuditoria.getOpmeDescricao().trim()) || 
				respostaProcedimentoAuditoria.getOpmeValor() == null ||
				respostaProcedimentoAuditoria.getOpmeJustificativa() == null || 
				"".equals(respostaProcedimentoAuditoria.getOpmeJustificativa().trim()))){
			throw new SystemRuntimeException("É necessário inserir um valor ou uma justificativa ou uma descrição ao OPME");
		}
	}

	private void verificarJustificativa(RespostaProcedimentoAuditoria respostaProcedimentoAuditoria) {
		if(!Boolean.TRUE.equals(respostaProcedimentoAuditoria.getAprovado()) && (
				respostaProcedimentoAuditoria.getJustificativa() == null || 
				"".equals(respostaProcedimentoAuditoria.getJustificativa().trim()) )){
			throw new SystemRuntimeException("É necessário inserir uma justificativa ao reprovar um procedimento");
		}
	}

	@Override
	public EstadoAuditoriaProspectiva obterResultadoAuditoria(Set<RespostaProcedimentoAuditoria> resultados) {
		for (RespostaProcedimentoAuditoria resultadoProcedimentoAuditoria : resultados) {
			if(Boolean.TRUE.equals(resultadoProcedimentoAuditoria.getAprovado())){
				return EstadoAuditoriaProspectiva.APROVADO;
			}
		}
		return EstadoAuditoriaProspectiva.REPROVADO;
	}

	@Override
	public String gerarMensagemSucessoComNumerosGAB(Integer idAuditoria) {
		List<GuiaApresentacaoBeneficiario> gabs = guiaApresentacaoBeneficiarioDAO.obterGABsPorAuditoriaProspectiva(idAuditoria);
		StringBuilder builder = new StringBuilder();
		if(gabs.isEmpty()){
			builder.append("Auditoria realizada com sucesso. Não foi gerada GAB.");
		} else if(gabs.size()>1){
			builder.append("Auditoria realizada com sucesso. Foram geradas as GABs: ");
		} else{
			builder.append("Auditoria realizada com sucesso. Foi gerada a GAB: ");
		}
		for (GuiaApresentacaoBeneficiario guiaApresentacaoBeneficiario : gabs) {
			builder.append("Nº " + guiaApresentacaoBeneficiario.getCodigo() + ". ");
		}
		return builder.toString();
	}

	@Override
	public void validarAprovacaoRespostas(Set<RespostaProcedimentoAuditoria> procedimentos) {
		for (RespostaProcedimentoAuditoria resposta: procedimentos){
			if (resposta.getAprovado()==null){
				throw new SystemRuntimeException("É obrigatório auditar todos itens.");
			}
		}
	}
	
	

}
