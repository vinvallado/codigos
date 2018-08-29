package br.ccasj.sisauc.administracao.cadastro.dao.impl;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.administracao.cadastro.dao.EspecificacaoIsencaoCobrancaDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.EspecificacaoIsencaoCobranca;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@MappedSuperclass
@Transactional
@Repository(value = "especificacaoIsencaoCobrancaDAO")
@NamedQueries({
	@NamedQuery(name = EspecificacaoIsencaoCobrancaDAOImpl.LISTAR_TODOS, query = EspecificacaoIsencaoCobrancaDAOImpl.LISTAR_TODOS)
})
public class EspecificacaoIsencaoCobrancaDAOImpl extends GenericEntityDAOImpl<EspecificacaoIsencaoCobranca> implements EspecificacaoIsencaoCobrancaDAO {

	private static final long serialVersionUID = -6839986438023399376L;
	public static final String LISTAR_TODOS = "from EspecificacaoIsencaoCobranca order by tipoCobranca";

	@Override
	public List<EspecificacaoIsencaoCobranca> findAll() {
		return entityManager.createQuery(LISTAR_TODOS, EspecificacaoIsencaoCobranca.class).getResultList();
	}
	
}
