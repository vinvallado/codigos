package br.ccasj.sisauc.administracao.cadastro.dao.impl;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.administracao.cadastro.dao.EstadoDAO;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;
import br.ccasj.sisauc.framework.domain.Estado;

@MappedSuperclass
@Transactional
@Repository(value = "estadoDAO")
@NamedQueries({
	@NamedQuery(name = EstadoDAOImpl.LISTAR_TODOS, query = EstadoDAOImpl.LISTAR_TODOS)
})
public class EstadoDAOImpl extends GenericEntityDAOImpl<Estado> implements EstadoDAO {
	
	private static final long serialVersionUID = -7296623403118210038L;

	public static final String LISTAR_TODOS = "from Estado order by sigla";
	
	@Override
	public List<Estado> findAll() {
		return entityManager.createNamedQuery(LISTAR_TODOS, Estado.class).getResultList();
	}

}
