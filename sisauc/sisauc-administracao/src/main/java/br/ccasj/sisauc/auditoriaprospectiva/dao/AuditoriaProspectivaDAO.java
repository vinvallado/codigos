package br.ccasj.sisauc.auditoriaprospectiva.dao;

import java.util.List;

import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectiva;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

public interface AuditoriaProspectivaDAO extends GenericEntityDAO<AuditoriaProspectiva> {

	public List<AuditoriaProspectiva> obterAuditoriasParaAtendente();
	public List<AuditoriaProspectiva> listarAuditoriasPorBeneficiario(Integer idBeneficiario, String textoPesquisa);
	public AuditoriaProspectiva obterAuditoriaPorSolicitacaoComProcedimentos(Integer idSolicitacao);
	public AuditoriaProspectiva obterAuditoriaPorIdComProcedimentosESolicitacao(Integer idAuditoria);
	
}
