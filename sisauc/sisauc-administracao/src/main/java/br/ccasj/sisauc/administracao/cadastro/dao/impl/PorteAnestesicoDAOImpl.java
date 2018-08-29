package br.ccasj.sisauc.administracao.cadastro.dao.impl;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.administracao.cadastro.dao.PorteAnestesicoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.PorteAnestesico;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@MappedSuperclass
@Transactional
@Repository(value = "porteAnestesicoDAO")
@NamedQueries({ 
	@NamedQuery(name = PorteAnestesicoDAOImpl.VERIFICAR_CODIGO_PORTE_ANESTESICO_EXISTENTE, query = PorteAnestesicoDAOImpl.VERIFICAR_CODIGO_PORTE_ANESTESICO_EXISTENTE)
})
public class PorteAnestesicoDAOImpl extends GenericEntityDAOImpl<PorteAnestesico> implements PorteAnestesicoDAO {
	
	private static final long serialVersionUID = -6534459349142724926L;

	public static final String VERIFICAR_CODIGO_PORTE_ANESTESICO_EXISTENTE = "select case when (count(*) > 0) then true else false end from PorteAnestesico as pa where upper(pa.codigo) = :codigo and pa.id <> :id";

	@Override
	public boolean verificarCodigoPorteAnestesicoExistente(Integer id, String codigo) {
		id = id == null ? -1 : id;
		return (boolean) entityManager.createNamedQuery(VERIFICAR_CODIGO_PORTE_ANESTESICO_EXISTENTE).setParameter("codigo",codigo.toUpperCase()).setParameter("id", id).getSingleResult();
	}
}
