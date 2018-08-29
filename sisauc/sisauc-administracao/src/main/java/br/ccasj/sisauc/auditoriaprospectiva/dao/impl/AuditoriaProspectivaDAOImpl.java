package br.ccasj.sisauc.auditoriaprospectiva.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.auditoriaprospectiva.dao.AuditoriaProspectivaDAO;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectiva;
import br.ccasj.sisauc.auditoriaprospectiva.domain.RespostaProcedimentoAuditoria;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@MappedSuperclass
@Transactional
@Repository(value = "auditoriaProspectivaDAO")
@NamedQueries({
	@NamedQuery(name = AuditoriaProspectivaDAOImpl.OBTER_POR_ID, query = AuditoriaProspectivaDAOImpl.OBTER_POR_ID),
	@NamedQuery(name = AuditoriaProspectivaDAOImpl.OBTER_POR_SOLICITACAO, query = AuditoriaProspectivaDAOImpl.OBTER_POR_SOLICITACAO),
	@NamedQuery(name = AuditoriaProspectivaDAOImpl.OBTER_POR_BENEFICIARIO, query = AuditoriaProspectivaDAOImpl.OBTER_POR_BENEFICIARIO)
})
public class AuditoriaProspectivaDAOImpl extends GenericEntityDAOImpl<AuditoriaProspectiva> implements AuditoriaProspectivaDAO {

	private static final long serialVersionUID = -4614964455194415401L;

	public static final String OBTER_POR_ID = "FROM AuditoriaProspectiva a WHERE a.id=:idAuditoria";
	public static final String OBTER_POR_SOLICITACAO = "FROM AuditoriaProspectiva a WHERE a.solicitacao.id=:idSolicitacao";
	public static final String OBTER_POR_BENEFICIARIO = "select new AuditoriaProspectiva(auditoria.id, auditoria.estado, auditoria.dataFinalAuditoria, solicitacao.id, solicitacao.numero, "
			+ "resposta.id, resposta.justificativa, resposta.aprovado, credenciado.id, "
			+ "procedimento.id, procedimento.tabela, procedimento.codigo, procedimento.descricao, "
			+ "especialidade.id, especialidade.sigla) "
			+ "from AuditoriaProspectiva auditoria "
			+ "LEFT JOIN auditoria.solicitacao as solicitacao "
			+ "RIGHT JOIN auditoria.procedimentos as resposta "
			+ "LEFT JOIN resposta.credenciado as credenciado "
			+ "LEFT JOIN credenciado.procedimento as procedimento "
			+ "LEFT JOIN credenciado.especialidade as especialidade "
			+ "LEFT JOIN solicitacao.beneficiario as beneficiario "
			+ "WHERE beneficiario.id = :idBeneficiario and (UPPER(procedimento.codigo) LIKE :textoPesquisa or UPPER(procedimento.descricao) LIKE :textoPesquisa or UPPER(especialidade.sigla) LIKE :textoPesquisa) "
			+ "order by auditoria.dataFinalAuditoria desc";
	
	@Override
	public List<AuditoriaProspectiva> obterAuditoriasParaAtendente() {
		return null;
	}
	
	@Override
	public List<AuditoriaProspectiva> listarAuditoriasPorBeneficiario(Integer idBeneficiario, String textoPesquisa) {
		if(textoPesquisa == null){
			textoPesquisa = "";
		}
		textoPesquisa = "%"+textoPesquisa+"%";
		List<AuditoriaProspectiva> listaCompleta = entityManager.createNamedQuery(OBTER_POR_BENEFICIARIO, AuditoriaProspectiva.class).setParameter("idBeneficiario", idBeneficiario).setParameter("textoPesquisa", textoPesquisa.toUpperCase()).getResultList();
		List<AuditoriaProspectiva> result = new ArrayList<AuditoriaProspectiva>();

		for (AuditoriaProspectiva auditoria : listaCompleta) {
			if(!result.contains(auditoria)){
				result.add(auditoria);
			}
			result.get(result.indexOf(auditoria)).getProcedimentos().addAll(auditoria.getProcedimentos());
		}
		return result;
	}
	
	@Override
	public AuditoriaProspectiva merge(AuditoriaProspectiva object) {
		for (RespostaProcedimentoAuditoria resposta : object.getProcedimentos()) {
			if(resposta.getId() != null && resposta.getId() < 0){
				resposta.setId(null);
			}
		}
		return super.merge(object);
	}

	@Override
	public AuditoriaProspectiva obterAuditoriaPorSolicitacaoComProcedimentos(Integer idSolicitacao) {
		AuditoriaProspectiva auditoria = entityManager.createNamedQuery(OBTER_POR_SOLICITACAO, AuditoriaProspectiva.class)
				.setParameter("idSolicitacao", idSolicitacao).getSingleResult();
		Hibernate.initialize(auditoria.getProcedimentos());
		return auditoria;
	}

	@Override
	public AuditoriaProspectiva obterAuditoriaPorIdComProcedimentosESolicitacao(Integer idAuditoria) {
		AuditoriaProspectiva auditoria = entityManager.createNamedQuery(OBTER_POR_ID, AuditoriaProspectiva.class)
				.setParameter("idAuditoria", idAuditoria).getSingleResult();
		Hibernate.initialize(auditoria.getProcedimentos());
		Hibernate.initialize(auditoria.getSolicitacao().getProcedimentos());
		return auditoria;
	}

}
