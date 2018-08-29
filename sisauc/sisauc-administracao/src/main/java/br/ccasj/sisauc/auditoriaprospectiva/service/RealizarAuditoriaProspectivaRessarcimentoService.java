package br.ccasj.sisauc.auditoriaprospectiva.service;

import java.util.Set;

import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoRessarcimento;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectivaRessarcimento;
import br.ccasj.sisauc.auditoriaprospectiva.domain.EstadoAuditoriaProspectiva;
import br.ccasj.sisauc.auditoriaprospectiva.domain.RespostaRessarcimentoAuditoria;

public interface RealizarAuditoriaProspectivaRessarcimentoService {

	public AuditoriaProspectivaRessarcimento finalizarAuditoria(AuditoriaProspectivaRessarcimento auditoriaProspectivaRessarcimento);
	public EstadoAuditoriaProspectiva obterResultadoAuditoria(Set<RespostaRessarcimentoAuditoria> procedimentos);
	public void informarInconsistencia(SolicitacaoRessarcimento solicitacao);
	public void validarFinalizacao(AuditoriaProspectivaRessarcimento auditoriaProspectivaRessarcimento);
	public String gerarMensagemSucessoComNumerosAR(Integer id);

}
