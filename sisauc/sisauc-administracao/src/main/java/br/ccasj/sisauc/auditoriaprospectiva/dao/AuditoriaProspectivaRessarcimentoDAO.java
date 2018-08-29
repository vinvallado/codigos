package br.ccasj.sisauc.auditoriaprospectiva.dao;

import java.util.List;

import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectivaRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

public interface AuditoriaProspectivaRessarcimentoDAO extends GenericEntityDAO<AuditoriaProspectivaRessarcimento> {
	
	public AuditoriaProspectivaRessarcimento obterAuditoriaPorSolicitacaoRessarcimentoComProcedimentos(Integer idSolicitacao);
	public List<AuditoriaProspectivaRessarcimento> listarAuditoriasPorBeneficiario(Integer idBeneficiario, String textoPesquisa);
	public AuditoriaProspectivaRessarcimento obterAuditoriaPorIdComProcedimentosESolicitacao(Integer idAuditoria);
	public void cancelarAuditoriaProspectivaRessarcimento(AutorizacaoRessarcimento are, String justificativa);

}
