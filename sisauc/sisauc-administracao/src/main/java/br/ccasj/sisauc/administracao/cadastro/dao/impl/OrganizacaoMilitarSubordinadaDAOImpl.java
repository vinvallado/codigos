package br.ccasj.sisauc.administracao.cadastro.dao.impl;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.OrganizacaoMilitarSubordinadaDAO;
import br.ccasj.sisauc.administracao.sistema.domain.OrganizacaoMilitarRegional;
import br.ccasj.sisauc.administracao.sistema.domain.OrganizacaoMilitarSubordinada;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;


@Transactional
@MappedSuperclass
@Service(value = "OrganizacaoMilitarSubordinadaDAO")
@NamedQueries({
	@NamedQuery(name = OrganizacaoMilitarSubordinadaDAOImpl.LISTAR_SUBORDINADAS_ATIVAS_PARA_COMBO, query = OrganizacaoMilitarSubordinadaDAOImpl.LISTAR_SUBORDINADAS_ATIVAS_PARA_COMBO),
	@NamedQuery(name = OrganizacaoMilitarSubordinadaDAOImpl.LISTAR_SUBORDINADAS_ATIVAS_POR_REGIONAL, query = OrganizacaoMilitarSubordinadaDAOImpl.LISTAR_SUBORDINADAS_ATIVAS_POR_REGIONAL),
	@NamedQuery(name = OrganizacaoMilitarSubordinadaDAOImpl.LISTAR_SUBORDINADAS_ATIVAS_SEM_REGIONAL, query = OrganizacaoMilitarSubordinadaDAOImpl.LISTAR_SUBORDINADAS_ATIVAS_SEM_REGIONAL),
	@NamedQuery(name = OrganizacaoMilitarSubordinadaDAOImpl.SALVAR_SUBORDINADAS_SEM_REGIONAL, query = OrganizacaoMilitarSubordinadaDAOImpl.SALVAR_SUBORDINADAS_SEM_REGIONAL),
	@NamedQuery(name = OrganizacaoMilitarSubordinadaDAOImpl.SALVAR_SUBORDINADAS_COM_REGIONAL_SELECIONADA, query = OrganizacaoMilitarSubordinadaDAOImpl.SALVAR_SUBORDINADAS_COM_REGIONAL_SELECIONADA),
	@NamedQuery(name = OrganizacaoMilitarSubordinadaDAOImpl.VALIDAR_EXCLUSAO_SUBORDINADA, query = OrganizacaoMilitarSubordinadaDAOImpl.VALIDAR_EXCLUSAO_SUBORDINADA),
})
public class OrganizacaoMilitarSubordinadaDAOImpl extends GenericEntityDAOImpl<OrganizacaoMilitarSubordinada> implements OrganizacaoMilitarSubordinadaDAO {

	private static final long serialVersionUID = 1467099706531195081L;

	public static final String LISTAR_SUBORDINADAS_ATIVAS_PARA_COMBO = "select new OrganizacaoMilitarSubordinada(id, sigla, nome) from OrganizacaoMilitarSubordinada where ativo = true order by sigla";
	public static final String LISTAR_SUBORDINADAS_ATIVAS_POR_REGIONAL = "select new OrganizacaoMilitarSubordinada(id, sigla, nome) from OrganizacaoMilitarSubordinada where ativo = true and regional.id = :idRegional order by sigla";
	public static final String LISTAR_SUBORDINADAS_ATIVAS_SEM_REGIONAL = "select new OrganizacaoMilitarSubordinada(id, sigla, nome) from OrganizacaoMilitarSubordinada where ativo = true and regional is null order by sigla";
	public static final String SALVAR_SUBORDINADAS_SEM_REGIONAL = "update from OrganizacaoMilitarSubordinada om set om.regional = null where om in :subordinadasSemRegional";
	public static final String SALVAR_SUBORDINADAS_COM_REGIONAL_SELECIONADA = "update OrganizacaoMilitarSubordinada om set om.regional = :regional where om in :subordinadas";
	public static final String VALIDAR_EXCLUSAO_SUBORDINADA = "select case when (count(usuario.id) > 0) then true else false end from Usuario usuario left join usuario.organizacaoMilitar om where om.id in (:idsSubordinadas) and usuario.ativo = true";
	
	@Override
	public List<OrganizacaoMilitarSubordinada> listarSubordinadasAtivasParaCombo() {
		return entityManager.createNamedQuery(LISTAR_SUBORDINADAS_ATIVAS_PARA_COMBO, OrganizacaoMilitarSubordinada.class).getResultList();
	}

	@Override
	public List<OrganizacaoMilitarSubordinada> listarSubordinadasAtivasPorRegional(Integer idRegional) {
		return entityManager.createNamedQuery(LISTAR_SUBORDINADAS_ATIVAS_POR_REGIONAL, OrganizacaoMilitarSubordinada.class).setParameter("idRegional", idRegional).getResultList();
	}
	
	@Override
	public List<OrganizacaoMilitarSubordinada> listarSubordinadasAtivasSemRegional() {
		return entityManager.createNamedQuery(LISTAR_SUBORDINADAS_ATIVAS_SEM_REGIONAL, OrganizacaoMilitarSubordinada.class).getResultList();
	}
	
	public void salvarSubordinadasSemRegional (List<OrganizacaoMilitarSubordinada> subordinadasSemRegional) {
		entityManager.createNamedQuery(SALVAR_SUBORDINADAS_SEM_REGIONAL).setParameter("subordinadasSemRegional", subordinadasSemRegional).executeUpdate();
	}
	
	public void salvarSubordinadasComRegionalSelecionada (List<OrganizacaoMilitarSubordinada> subordinadas, OrganizacaoMilitarRegional regional) {
		entityManager.createNamedQuery(SALVAR_SUBORDINADAS_COM_REGIONAL_SELECIONADA).setParameter("regional", regional).setParameter("subordinadas", subordinadas).executeUpdate();
	}

	@Override
	public boolean validarExclusaoSubordinada(List<Integer> idsSubordinadas) {
		return (boolean) entityManager.createNamedQuery(VALIDAR_EXCLUSAO_SUBORDINADA).setParameter("idsSubordinadas", idsSubordinadas).getSingleResult();
	}
}
