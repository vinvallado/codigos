package br.ccasj.sisauc.administracao.cadastro.dao.impl;

import java.util.Arrays;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.administracao.cadastro.dao.EspecialidadeDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Especialidade;
import br.ccasj.sisauc.administracao.cadastro.domain.TipoEspecialidade;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@MappedSuperclass
@Transactional
@Repository(value = "especialidadeDAO")
@NamedQueries({
	@NamedQuery(name = EspecialidadeDAOImpl.OBTER_ATIVAS, query = EspecialidadeDAOImpl.OBTER_ATIVAS),
	@NamedQuery(name = EspecialidadeDAOImpl.OBTER_ATIVAS_POR_TIPOS, query = EspecialidadeDAOImpl.OBTER_ATIVAS_POR_TIPOS),
	@NamedQuery(name = EspecialidadeDAOImpl.OBTER_ATIVAS_NAO_ODONTOLOGICAS, query = EspecialidadeDAOImpl.OBTER_ATIVAS_NAO_ODONTOLOGICAS)
})
public class EspecialidadeDAOImpl extends GenericEntityDAOImpl<Especialidade> implements EspecialidadeDAO {

	private static final long serialVersionUID = 5530052341817490500L;

	public static final String OBTER_ATIVAS = "from Especialidade where ativo = true order by sigla";
	public static final String OBTER_ATIVAS_POR_TIPOS = "FROM Especialidade WHERE ativo = TRUE AND tipo IN :tipos ORDER BY sigla";
	public static final String OBTER_ATIVAS_NAO_ODONTOLOGICAS = "FROM Especialidade WHERE ativo = TRUE AND tipo <> 'ODONTOLOGICA' ORDER BY sigla";
	
	@Override
	public List<Especialidade> listarEspecialidadesAtivas() {
		return entityManager.createNamedQuery(OBTER_ATIVAS, Especialidade.class).getResultList();
	}

	@Override
	public List<Especialidade> listarEspecialidadesAtivasPorTipos(TipoEspecialidade... tipos) {
		return entityManager.createNamedQuery(OBTER_ATIVAS_POR_TIPOS, Especialidade.class)
				.setParameter("tipos", Arrays.asList(tipos)).getResultList();
	}
	
	@Override
	public List<Especialidade> listarEspecialidadesAtivasNaoOdontologicas() {
		return entityManager.createNamedQuery(OBTER_ATIVAS_NAO_ODONTOLOGICAS, Especialidade.class).getResultList();
	}
	
	

}
