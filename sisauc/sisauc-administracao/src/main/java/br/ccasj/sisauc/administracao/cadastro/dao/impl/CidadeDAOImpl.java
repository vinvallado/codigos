package br.ccasj.sisauc.administracao.cadastro.dao.impl;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.administracao.cadastro.dao.CidadeDAO;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;
import br.ccasj.sisauc.framework.domain.Cidade;

@MappedSuperclass
@Transactional
@Repository(value = "cidadeDAO")
@NamedQueries({ 
	@NamedQuery(name = CidadeDAOImpl.LISTAR_TODAS, query = CidadeDAOImpl.LISTAR_TODAS),
})
public class CidadeDAOImpl extends GenericEntityDAOImpl<Cidade> implements CidadeDAO {

	private static final long serialVersionUID = 1685601447106869466L;

	public static final String LISTAR_TODAS = "select new Cidade(cidade.id, cidade.nome, estado.sigla) from Cidade cidade left join cidade.estado as estado order by cidade.nome, estado.sigla";
	
	@Override
	public List<Cidade> findAll() {
		return entityManager.createNamedQuery(LISTAR_TODAS, Cidade.class).getResultList();
	}
	
}
