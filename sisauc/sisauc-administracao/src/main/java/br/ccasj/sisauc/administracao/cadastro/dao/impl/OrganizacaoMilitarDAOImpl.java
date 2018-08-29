package br.ccasj.sisauc.administracao.cadastro.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.OrganizacaoMilitarDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.OrganizacaoMilitarRegionalDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.OrganizacaoMilitarSubordinadaDAO;
import br.ccasj.sisauc.administracao.sistema.domain.OrganizacaoMilitarRegional;
import br.ccasj.sisauc.administracao.sistema.domain.OrganizacaoMilitarSubordinada;
import br.ccasj.sisauc.administracao.sistema.service.ConfiguracaoOrganizacaoMilitarRegionalService;
import br.ccasj.sisauc.framework.comparator.OrganizacaoMilitarSiglaComparator;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;

@Transactional
@MappedSuperclass
@Service(value = "organizacaoMilitarDAO")
@NamedQueries({
	@NamedQuery(name = OrganizacaoMilitarDAOImpl.MUDAR_STATUS_ATIVO, query = OrganizacaoMilitarDAOImpl.MUDAR_STATUS_ATIVO),
	@NamedQuery(name = OrganizacaoMilitarDAOImpl.BUSCAR_OM_POR_SIGLA, query = OrganizacaoMilitarDAOImpl.BUSCAR_OM_POR_SIGLA)
})
public class OrganizacaoMilitarDAOImpl  extends GenericEntityDAOImpl<OrganizacaoMilitar> implements OrganizacaoMilitarDAO{
	
	private static final long serialVersionUID = 5961417840887540891L;

	public static final String MUDAR_STATUS_ATIVO = "update OrganizacaoMilitar om set om.ativo = :status where om.id = :id";
	public static final String BUSCAR_OM_POR_SIGLA = "select new OrganizacaoMilitar(id, sigla, nome) from OrganizacaoMilitar where sigla = :sigla";

	@Autowired
	private OrganizacaoMilitarRegionalDAO regionalDAO;
	@Autowired
	private ConfiguracaoOrganizacaoMilitarRegionalService regionalService;
	@Autowired
	private OrganizacaoMilitarSubordinadaDAO subordinadaDAO;
	
	@Override
	public List<OrganizacaoMilitar> findAll() {
		List<OrganizacaoMilitarRegional> regionais = regionalDAO.findAll();
		List<OrganizacaoMilitarSubordinada> subordinadas = subordinadaDAO.findAll();
		List<OrganizacaoMilitar> result = new ArrayList<OrganizacaoMilitar>();
		result.addAll(subordinadas);
		result.addAll(regionais);
		Collections.sort(result, new OrganizacaoMilitarSiglaComparator());
		return result;
	}

	@Override
	public List<OrganizacaoMilitar> listarOMSAtivasParaCombo() {
		List <OrganizacaoMilitarRegional> regionais = regionalDAO.listarRegionaisAtivasParaCombo();
		List <OrganizacaoMilitarSubordinada> subordinadas = subordinadaDAO.listarSubordinadasAtivasParaCombo();
		List <OrganizacaoMilitar> result = new ArrayList<OrganizacaoMilitar>();
		result.addAll(regionais);
		result.addAll(subordinadas);
		Collections.sort(result, new OrganizacaoMilitarSiglaComparator());
		return result;
	}

	@Override
	public void definirStatusAtivo(Integer id, boolean status) {
		entityManager.createNamedQuery(MUDAR_STATUS_ATIVO).setParameter("id", id).setParameter("status", status).executeUpdate();
	}
	
	@Override
	public OrganizacaoMilitar obterOrganizacaoMilitarPorSigla(String sigla) {
		List<OrganizacaoMilitar> result = entityManager.createNamedQuery(BUSCAR_OM_POR_SIGLA, OrganizacaoMilitar.class).setParameter("sigla", sigla).getResultList();
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public List<OrganizacaoMilitar> listarOMSAtivasPorRegional() {
		OrganizacaoMilitarRegional regional = regionalService.obterRegionalSistema();
		List<OrganizacaoMilitarSubordinada> subordinadas = subordinadaDAO.listarSubordinadasAtivasPorRegional(regional.getId());
		List<OrganizacaoMilitar> result = new ArrayList<OrganizacaoMilitar>();
		result.addAll(subordinadas);
		result.add(regional);
		Collections.sort(result, new OrganizacaoMilitarSiglaComparator());
		return result;
		
	}
	
}
