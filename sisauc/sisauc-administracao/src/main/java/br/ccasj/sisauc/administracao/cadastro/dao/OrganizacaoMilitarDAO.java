package br.ccasj.sisauc.administracao.cadastro.dao;

import java.util.List;

import br.ccasj.sisauc.framework.dao.GenericEntityDAO;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;

public interface OrganizacaoMilitarDAO extends GenericEntityDAO<OrganizacaoMilitar> {
	public List<OrganizacaoMilitar> listarOMSAtivasParaCombo();
	public void definirStatusAtivo(Integer id, boolean status);
	public OrganizacaoMilitar obterOrganizacaoMilitarPorSigla(String sigla);
	public List<OrganizacaoMilitar> listarOMSAtivasPorRegional();
}
