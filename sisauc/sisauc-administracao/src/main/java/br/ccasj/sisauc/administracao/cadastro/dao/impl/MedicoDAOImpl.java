package br.ccasj.sisauc.administracao.cadastro.dao.impl;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.administracao.cadastro.dao.MedicoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Especialidade;
import br.ccasj.sisauc.administracao.cadastro.domain.Medico;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@MappedSuperclass
@Transactional
@Repository(value = "medicoDAO")
@NamedQueries({
	@NamedQuery(name = MedicoDAOImpl.MUDAR_STATUS_ATIVO_MEDICO, query = MedicoDAOImpl.MUDAR_STATUS_ATIVO_MEDICO),
	@NamedQuery(name = MedicoDAOImpl.LISTAR_TODOS, query = MedicoDAOImpl.LISTAR_TODOS),
	@NamedQuery(name = MedicoDAOImpl.LISTAR_TODOS_ATIVOS_PARA_COMBO, query = MedicoDAOImpl.LISTAR_TODOS_ATIVOS_PARA_COMBO),
	@NamedQuery(name = MedicoDAOImpl.VERIFICAR_NUMERO_CONSELHO_REGIONAL_UNICO, query = MedicoDAOImpl.VERIFICAR_NUMERO_CONSELHO_REGIONAL_UNICO),
	@NamedQuery(name = MedicoDAOImpl.LISTAR_ESPECIALIDADES_POR_ID_MEDICO, query = MedicoDAOImpl.LISTAR_ESPECIALIDADES_POR_ID_MEDICO) 
})
public class MedicoDAOImpl extends GenericEntityDAOImpl<Medico> implements MedicoDAO {

	private static final long serialVersionUID = -6472722259304833682L;

	public static final String MUDAR_STATUS_ATIVO_MEDICO = "update Medico m set m.ativo = :status where m.id = :id";
	public static final String LISTAR_TODOS = "select new Medico(id, nome, numeroConselhoRegional, ativo,profissionalSaude, militar, tipoProfissionalSaude) from Medico order by nome";
	public static final String LISTAR_TODOS_ATIVOS_PARA_COMBO = "select new Medico(id, nome, numeroConselhoRegional, ativo,profissionalSaude, militar, tipoProfissionalSaude) from Medico where ativo = true order by nome";
	public static final String LISTAR_ESPECIALIDADES_POR_ID_MEDICO = "select new Especialidade(e.id, e.nome) from Medico m right join m.especialidades as e where m.id = :id order by e.nome";
	public static final String VERIFICAR_NUMERO_CONSELHO_REGIONAL_UNICO = "select case when (count(*) > 0) then true else false end from Medico as m where m.numeroConselhoRegional = :numeroConselhoRegional and m.id <> :id";

	@Override
	public void definirStatusAtivoMedico(Integer id, boolean status) {
		entityManager.createNamedQuery(MedicoDAOImpl.MUDAR_STATUS_ATIVO_MEDICO).setParameter("id", id).setParameter("status", status).executeUpdate();
	}

	@Override
	public List<Medico> listarAtivosParaCombo() {
		return entityManager.createNamedQuery(LISTAR_TODOS_ATIVOS_PARA_COMBO, Medico.class).getResultList();
	}

	@Override
	public List<Especialidade> listarEspecialidadesPorIdMedico(Integer id) {
		return entityManager.createNamedQuery(LISTAR_ESPECIALIDADES_POR_ID_MEDICO, Especialidade.class).setParameter("id", id).getResultList();
	}

	@Override
	public boolean verificarNumeroConselhoRegional(Integer id, String numeroConselhoRegional) {
		id = id == null ? -1 : id;
		return (boolean) entityManager.createNamedQuery(VERIFICAR_NUMERO_CONSELHO_REGIONAL_UNICO).setParameter("numeroConselhoRegional", numeroConselhoRegional).setParameter("id", id).getSingleResult();
	}

}
