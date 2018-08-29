package br.mil.fab.consigext.repository;

import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.mil.fab.consigext.entity.EntidadeConsig;
@Repository
public class SchedulerRepository {
	
	@Inject
	private EntityManagerFactory emFac;
    
    @Transactional
    public void configurarSituacao(EntidadeConsig obj) {
    	EntityManager em = emFac.createEntityManager();
    	em.getTransaction().begin();
    	Query query = em.createQuery("update EntidadeConsig e set stEntidade = :stEntidade where e.id = :id");
    	query.setParameter("stEntidade", obj.getStEntidade());
    	query.setParameter("id", obj.getId());
    	query.executeUpdate();
    	em.getTransaction().commit();
    }
    @Transactional
    public void insertOrUpdate(String queryStr, Map<Integer, Object> params, boolean nativeFlag) {
    	EntityManager em = emFac.createEntityManager();
    	em.getTransaction().begin();
    	Query query;
    	if(nativeFlag)
        	query = em.createNativeQuery(queryStr);
    	else
    		query = em.createQuery(queryStr);
    	if(params!=null)
		for (Map.Entry<Integer, Object> entry : params.entrySet())
		   	query.setParameter(entry.getKey(), entry.getValue());
    	query.executeUpdate();
    	em.getTransaction().commit();
    }
}
