package br.ccasj.sisauc.administracao.cadastro.dao.impl;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.administracao.cadastro.dao.EditalCredenciamentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.EditalCredenciamento;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@MappedSuperclass
@Transactional
@Repository(value = "editalCredenciamentoDAO")
@NamedQueries({
	@NamedQuery(name = EditalCredenciamentoDAOImpl.LISTAR_TODOS, query = EditalCredenciamentoDAOImpl.LISTAR_TODOS),
	@NamedQuery(name = EditalCredenciamentoDAOImpl.VERIFICAR_UNICIDADE_EDITAL, query = EditalCredenciamentoDAOImpl.VERIFICAR_UNICIDADE_EDITAL),
	@NamedQuery(name = EditalCredenciamentoDAOImpl.LISTAR_EDITAIS_ATIVOS, query = EditalCredenciamentoDAOImpl.LISTAR_EDITAIS_ATIVOS),
	@NamedQuery(name = EditalCredenciamentoDAOImpl.VERIFICAR_EDITAL_ATIVO_CONCORRENTE_POR_DATA, query = EditalCredenciamentoDAOImpl.VERIFICAR_EDITAL_ATIVO_CONCORRENTE_POR_DATA)
})
public class EditalCredenciamentoDAOImpl extends GenericEntityDAOImpl<EditalCredenciamento> implements EditalCredenciamentoDAO {

	private static final long serialVersionUID = -6789081224624816014L;

	public static final String LISTAR_TODOS = "select new EditalCredenciamento(e.id, e.numero, e.tabela, e.descricao, e.ativo, e.inicio, e.fim) from EditalCredenciamento e order by e.numero";
	public static final String LISTAR_EDITAIS_ATIVOS = "select new EditalCredenciamento(e.id, e.numero, e.tabela, e.descricao, e.ativo, e.inicio, e.fim) from EditalCredenciamento e where e.ativo = true order by e.numero";
	public static final String VERIFICAR_UNICIDADE_EDITAL = "select case when (count(*) > 0) then true else false end from EditalCredenciamento as edital where upper(edital.numero) = :numero and edital.id <> :id";
	public static final String VERIFICAR_EDITAL_ATIVO_CONCORRENTE_POR_DATA = "SELECT CASE WHEN (COUNT(*) > 0) THEN TRUE ELSE FALSE END FROM EditalCredenciamento edital WHERE "
			+ "edital.ativo = true AND ("
			+ "(:inicio BETWEEN edital.inicio AND edital.fim AND :fim BETWEEN edital.inicio AND edital.fim) OR "
			+ "(:inicio <= edital.inicio AND :fim BETWEEN edital.inicio AND edital.fim) OR "
			+ "(:fim >= edital.fim AND :inicio BETWEEN edital.inicio AND edital.fim) OR "
			+ "(:inicio <= edital.inicio AND :fim >= edital.fim))";

	@Override
	public List<EditalCredenciamento> findAll() {
		return entityManager.createNamedQuery(LISTAR_TODOS, EditalCredenciamento.class).getResultList();
	}

	@Override
	public List<EditalCredenciamento> listarEditaisAtivos() {
		return entityManager.createNamedQuery(LISTAR_EDITAIS_ATIVOS, EditalCredenciamento.class).getResultList();
	}

	@Override
	public boolean verificarUnidadeEdital(Integer id, String numero) {
		id = id == null ? -1 : id;
		return (boolean) entityManager.createNamedQuery(VERIFICAR_UNICIDADE_EDITAL)
				.setParameter("numero", numero.toUpperCase()).setParameter("id", id).getSingleResult();
	}

	@Override
	public boolean verificarEditalAtivoConcorrentePorData(EditalCredenciamento edital) {
		StringBuilder queryString = new StringBuilder(VERIFICAR_EDITAL_ATIVO_CONCORRENTE_POR_DATA)
		.append(edital.getId() == null ? "" : " AND edital.id <> :id");
		Query query = entityManager.createQuery(queryString.toString()).setParameter("inicio", edital.getInicio()).setParameter("fim", edital.getFim());
		if(edital.getId() != null){
			query.setParameter("id", edital.getId());
		}
		return (boolean) query.getSingleResult();
	}

}
