package br.ccasj.sisauc.auditoriaprospectiva.service;

import java.util.Set;

import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoProcedimento;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectiva;
import br.ccasj.sisauc.auditoriaprospectiva.domain.EstadoAuditoriaProspectiva;
import br.ccasj.sisauc.auditoriaprospectiva.domain.RespostaProcedimentoAuditoria;

public interface RealizarAuditoriaProspectivaService {

	public void informarInconsistencia(SolicitacaoProcedimento solicitacaoProcedimento);
	public AuditoriaProspectiva finalizarAuditoria(AuditoriaProspectiva auditoriaProspectiva);
	public EstadoAuditoriaProspectiva obterResultadoAuditoria(Set<RespostaProcedimentoAuditoria> resultados);
	public String gerarMensagemSucessoComNumerosGAB(Integer idAuditoria);
	public void validarAprovacaoRespostas(Set<RespostaProcedimentoAuditoria> procedimentos);
	
}
