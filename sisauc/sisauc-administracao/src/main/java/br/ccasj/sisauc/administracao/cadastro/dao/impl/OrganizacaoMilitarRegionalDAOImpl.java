package br.ccasj.sisauc.administracao.cadastro.dao.impl;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.OrganizacaoMilitarRegionalDAO;
import br.ccasj.sisauc.administracao.sistema.domain.OrganizacaoMilitarRegional;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@Transactional
@MappedSuperclass
@Service(value = "OrganizacaoMilitarRegionalDAO")
@NamedQueries({
		@NamedQuery(name = OrganizacaoMilitarRegionalDAOImpl.LISTAR_REGIONAIS_ATIVAS_PARA_COMBO, query = OrganizacaoMilitarRegionalDAOImpl.LISTAR_REGIONAIS_ATIVAS_PARA_COMBO),
		@NamedQuery(name = OrganizacaoMilitarRegionalDAOImpl.OBTER_REGIONAL_SISTEMA, query = OrganizacaoMilitarRegionalDAOImpl.OBTER_REGIONAL_SISTEMA),
		@NamedQuery(name = OrganizacaoMilitarRegionalDAOImpl.VALIDAR_REGIONAL_SISTEMA, query = OrganizacaoMilitarRegionalDAOImpl.VALIDAR_REGIONAL_SISTEMA) })
public class OrganizacaoMilitarRegionalDAOImpl extends GenericEntityDAOImpl<OrganizacaoMilitarRegional> implements
		OrganizacaoMilitarRegionalDAO {

	private static final long serialVersionUID = 8932041325013963266L;

	public static final String LISTAR_REGIONAIS_ATIVAS_PARA_COMBO = "select new OrganizacaoMilitarRegional(id, sigla, nome) from OrganizacaoMilitarRegional where ativo = true and id > 0 order by sigla";

	public static final String OBTER_REGIONAL_SISTEMA = "select new OrganizacaoMilitarRegional(id, sigla, nome) from OrganizacaoMilitarRegional where regionalSistema = true and id > 0";

	public static final String VALIDAR_REGIONAL_SISTEMA = "select case when (count(*) = 1) then true else false end from OrganizacaoMilitarRegional where regionalSistema = true and id > 0";

	@Override
	public List<OrganizacaoMilitarRegional> listarRegionaisAtivasParaCombo() {
		return entityManager.createNamedQuery(LISTAR_REGIONAIS_ATIVAS_PARA_COMBO, OrganizacaoMilitarRegional.class)
				.getResultList();

	}

	@Override
	public OrganizacaoMilitarRegional obterRegionalSistema() {
		return entityManager.createNamedQuery(OBTER_REGIONAL_SISTEMA, OrganizacaoMilitarRegional.class)
				.getSingleResult();
	}

	@Override
	public boolean validarRegionalSistema() {
		return (boolean) entityManager.createNamedQuery(OrganizacaoMilitarRegionalDAOImpl.VALIDAR_REGIONAL_SISTEMA)
				.getSingleResult();
	}

}
