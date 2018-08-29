package br.ccasj.sisauc.administracao.cadastro.dao.impl;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.administracao.cadastro.dao.EmpenhoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Empenho;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@MappedSuperclass
@Transactional
@Repository(value = "empenhoDAO")
@NamedQueries({
	@NamedQuery(name = EmpenhoDAOImpl.LISTAR_TODOS, query = EmpenhoDAOImpl.LISTAR_TODOS)
})
public class EmpenhoDAOImpl extends GenericEntityDAOImpl<Empenho> implements EmpenhoDAO {

	private static final long serialVersionUID = -4373363084625369452L;

	public static final String LISTAR_TODOS = "from Empenho";
	
	@Override
	public List<Empenho> obterEmpenhos() {
		return entityManager.createNamedQuery(LISTAR_TODOS, Empenho.class).getResultList();
	}

	@Override
	public Empenho obterEmpenho(Integer id) {
		return super.findById(id);
	}


}
