package br.ccasj.sisauc.administracao.sistema.dao.impl;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.administracao.sistema.dao.UnidadeMultiplicadoraDao;
import br.ccasj.sisauc.administracao.sistema.domain.UnidadeMultiplicadora;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@MappedSuperclass
@Transactional
@Repository(value = "UnidadeMultiplicadoraDao")
@NamedQueries({ 
	@NamedQuery(name = UnidadeMultiplicadoraDaoImpl.OBTER_UNIDADE_MULTIPLICADORA, query = UnidadeMultiplicadoraDaoImpl.OBTER_UNIDADE_MULTIPLICADORA)
})
public class UnidadeMultiplicadoraDaoImpl extends GenericEntityDAOImpl<UnidadeMultiplicadora> implements UnidadeMultiplicadoraDao {

	private static final long serialVersionUID = -7085997866956552885L;

	public static final String OBTER_UNIDADE_MULTIPLICADORA = "select new UnidadeMultiplicadora (id, valorFilme, valorUco, valorUsm) from UnidadeMultiplicadora";
	
	@Override
	public UnidadeMultiplicadora obterUnidadeMultiplicadora() {
		List<UnidadeMultiplicadora> result = entityManager.createNamedQuery(UnidadeMultiplicadoraDaoImpl.OBTER_UNIDADE_MULTIPLICADORA, UnidadeMultiplicadora.class).getResultList();
		return result.isEmpty() ? null : result.get(0);
	}

	
}
