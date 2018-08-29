package br.ccasj.sisauc.auditoriaretrospectiva.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.hibernate.annotations.NamedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.auditoriaretrospectiva.dao.AuditoriaRetrospectivaRessarcimentoDAO;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.AuditoriaRetrospectivaRessarcimento;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.MetadadoValorAuditoriaRetrospectivaRessarcimento;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;
import br.ccasj.sisauc.sdga.dao.CancelamentoAuditoriaRetrospectivaAREDAO;
import br.ccasj.sisauc.sdga.domain.CancelamentoAuditoriaRetrospectivaARE;

@MappedSuperclass
@Transactional
@Repository(value = "auditoriaRetrospectivaRessarcimentoDAO")
@NamedQuery(name = AuditoriaRetrospectivaRessarcimentoDAOImpl.DELETAR_POR_IDS, query = AuditoriaRetrospectivaRessarcimentoDAOImpl.DELETAR_POR_IDS)
public class AuditoriaRetrospectivaRessarcimentoDAOImpl  extends GenericEntityDAOImpl<AuditoriaRetrospectivaRessarcimento> implements AuditoriaRetrospectivaRessarcimentoDAO {

	private static final long serialVersionUID = -7186645568211137953L;

	public static final String DELETAR_POR_IDS = "DELETE FROM AuditoriaRetrospectivaRessarcimento where id in :ids";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public AuditoriaRetrospectivaRessarcimento merge(AuditoriaRetrospectivaRessarcimento auditoria) {
		if(auditoria.getValores() != null){
			for (MetadadoValorAuditoriaRetrospectivaRessarcimento metadado : auditoria.getValores()) {
				if(metadado.getValor() != null && metadado.getId() < 0){
					metadado.setId(null);
				}
			}
		}
		return super.merge(auditoria);
	}
	
	@Override
	public AuditoriaRetrospectivaRessarcimento obterAuditoriaComMetadadosPorId(Integer idAuditoria){
		AuditoriaRetrospectivaRessarcimento auditoria = findById(idAuditoria);
		Hibernate.initialize(auditoria.getValores());
		return auditoria;
	}

	@Override
	public void delete(List<Integer> ids) {
		AuditoriaRetrospectivaRessarcimento auditARE;
		for (Integer id : ids) {
			auditARE = findById(id);
			entityManager.remove(auditARE);
		}
//		entityManager.createNamedQuery(DELETAR_POR_IDS).setParameter("ids", ids).executeUpdate();
	}
	
	
}
