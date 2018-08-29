package br.ccasj.sisauc.auditoriaretrospectiva.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.hibernate.annotations.NamedQuery;
import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.auditoriaretrospectiva.dao.AuditoriaRetrospectivaDAO;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.AuditoriaRetrospectiva;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.MetadadoValorAuditoriaRetrospectiva;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@MappedSuperclass
@Transactional
@Repository(value = "auditoriaRetrospectivaDAO")
@NamedQuery(name = AuditoriaRetrospectivaDAOImpl.DELETAR_POR_IDS, query = AuditoriaRetrospectivaDAOImpl.DELETAR_POR_IDS)
public class AuditoriaRetrospectivaDAOImpl  extends GenericEntityDAOImpl<AuditoriaRetrospectiva> implements AuditoriaRetrospectivaDAO {

	private static final long serialVersionUID = -7186645568211137953L;

	public static final String DELETAR_POR_IDS = "DELETE FROM AuditoriaRetrospectiva where id in :ids";
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public AuditoriaRetrospectiva merge(AuditoriaRetrospectiva auditoria) {
		if(auditoria.getValores() != null){
			for (MetadadoValorAuditoriaRetrospectiva metadado : auditoria.getValores()) {
				if(metadado.getValor() != null && metadado.getId() < 0){
					metadado.setId(null);
				}
			}
		}
		return super.merge(auditoria);
	}
	
	@Override
	public AuditoriaRetrospectiva obterAuditoriaComMetadadosPorId(Integer idAuditoria){
		AuditoriaRetrospectiva auditoria = findById(idAuditoria);
		Hibernate.initialize(auditoria.getValores());
		return auditoria;
	}

	@Override
	public void delete(List<Integer> ids) {
		entityManager.createNamedQuery(DELETAR_POR_IDS).setParameter("ids", ids).executeUpdate();
	}
	
	
}
