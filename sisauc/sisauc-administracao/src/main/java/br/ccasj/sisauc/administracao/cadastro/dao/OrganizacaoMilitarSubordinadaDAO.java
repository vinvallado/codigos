package br.ccasj.sisauc.administracao.cadastro.dao;

import java.util.List;

import br.ccasj.sisauc.administracao.sistema.domain.OrganizacaoMilitarRegional;
import br.ccasj.sisauc.administracao.sistema.domain.OrganizacaoMilitarSubordinada;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

public interface OrganizacaoMilitarSubordinadaDAO extends GenericEntityDAO<OrganizacaoMilitarSubordinada> {

	public List<OrganizacaoMilitarSubordinada> listarSubordinadasAtivasParaCombo();
	public List<OrganizacaoMilitarSubordinada> listarSubordinadasAtivasPorRegional(Integer idRegional);
	public List<OrganizacaoMilitarSubordinada> listarSubordinadasAtivasSemRegional();
	public void salvarSubordinadasSemRegional(List<OrganizacaoMilitarSubordinada> subordinadasSemRegional);
	public void salvarSubordinadasComRegionalSelecionada(List<OrganizacaoMilitarSubordinada> subordinadas, OrganizacaoMilitarRegional regional);
	public boolean validarExclusaoSubordinada(List<Integer> idsSubordinadas);
	
}
