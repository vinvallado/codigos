package br.ccasj.sisauc.administracao.cadastro.dao;

import java.util.List;

import br.ccasj.sisauc.administracao.sistema.domain.OrganizacaoMilitarRegional;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

public interface OrganizacaoMilitarRegionalDAO extends GenericEntityDAO<OrganizacaoMilitarRegional> {

	public List<OrganizacaoMilitarRegional> listarRegionaisAtivasParaCombo();

	public OrganizacaoMilitarRegional obterRegionalSistema();

	public boolean validarRegionalSistema();
}
