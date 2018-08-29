package br.ccasj.sisauc.framework.dao.impl;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.framework.context.AuthenticationContext;
import br.ccasj.sisauc.framework.dao.GenericEntityHistoricoDAO;
import br.ccasj.sisauc.framework.domain.EntidadeHistoricoGenerica;

@Transactional
@Service(value = "genericEntityHitoricoDAO")
public class GenericEntityHitoricoDAOImpl<T extends EntidadeHistoricoGenerica> implements GenericEntityHistoricoDAO<T>{
	
	private static final long serialVersionUID = 7409665157660114806L;

	@PersistenceContext
	protected transient EntityManager entityManager;
	@Autowired
	private AuthenticationContext authenticationContext;
	
	@SuppressWarnings("unused")
	private Class<T> type;

//	@SuppressWarnings("unchecked")
//	public GenericEntityHitoricoDAOImpl() {
//        Type t = getClass().getGenericSuperclass();
//        ParameterizedType pt = (ParameterizedType) t;
//        type = (Class<T>) pt.getActualTypeArguments()[0];
//    }

	@Override
	public T merge(T object) {
		object.setUsuario(authenticationContext.getUsuarioLogado());
		object.setDataAlteracao(new Date());
		return entityManager.merge(object);
	}

}
