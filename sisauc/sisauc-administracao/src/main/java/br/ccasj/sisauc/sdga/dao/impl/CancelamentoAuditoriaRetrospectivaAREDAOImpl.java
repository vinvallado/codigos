package br.ccasj.sisauc.sdga.dao.impl;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.auditoriaretrospectiva.dao.impl.AuditoriaRetrospectivaRessarcimentoDAOImpl;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;
import br.ccasj.sisauc.sdga.dao.CancelamentoAuditoriaRetrospectivaAREDAO;
import br.ccasj.sisauc.sdga.domain.CancelamentoAuditoriaRetrospectivaARE;

@MappedSuperclass
@Transactional
@Repository(value = "cancelamentoAuditoriaRetrospectivaAREDAO")
@NamedQuery(name = CancelamentoAuditoriaRetrospectivaAREDAOImpl.DELETAR_CANCELAMENTOS_POR_IDS_AUDITORIA, query = CancelamentoAuditoriaRetrospectivaAREDAOImpl.DELETAR_CANCELAMENTOS_POR_IDS_AUDITORIA)
public class CancelamentoAuditoriaRetrospectivaAREDAOImpl
		extends GenericEntityDAOImpl<CancelamentoAuditoriaRetrospectivaARE>
		implements CancelamentoAuditoriaRetrospectivaAREDAO {

	private static final long serialVersionUID = 294762820650203032L;

	public static final String DELETAR_CANCELAMENTOS_POR_IDS_AUDITORIA = "DELETE FROM CancelamentoAuditoriaRetrospectivaARE cancela where cancela.auditoriaRetrospectivaRessarcimento.id in :ids";

	@Override
	public void deletePelosIdsAuditorias(List<Integer> idsAuditRestrospARE) {
		entityManager.createNamedQuery(DELETAR_CANCELAMENTOS_POR_IDS_AUDITORIA)
				.setParameter("ids", idsAuditRestrospARE).executeUpdate();
	}

}
