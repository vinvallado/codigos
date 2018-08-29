package br.ccasj.sisauc.administracao.cadastro.dao.impl;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.administracao.cadastro.dao.PorteDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Porte;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@MappedSuperclass
@Transactional
@Repository(value = "porteDAO")
@NamedQueries({ 
	@NamedQuery(name = PorteDAOImpl.VERIFICAR_CODIGO_PORTE_EXISTENTE, query = PorteDAOImpl.VERIFICAR_CODIGO_PORTE_EXISTENTE)
})
public class PorteDAOImpl extends GenericEntityDAOImpl<Porte> implements PorteDAO {
	
	private static final long serialVersionUID = -3612216098172722865L;

	public static final String VERIFICAR_CODIGO_PORTE_EXISTENTE = "select case when (count(*) > 0) then true else false end from Porte as p where upper(p.codigo) = :codigo and p.id <> :id";
	
	@Override
	public boolean verificarCodigoPorteExistente(Integer id, String codigo) {
		id = id == null ? -1 : id;
		return (boolean) entityManager.createNamedQuery(VERIFICAR_CODIGO_PORTE_EXISTENTE).setParameter("codigo",codigo.toUpperCase()).setParameter("id", id).getSingleResult();
	}
}
