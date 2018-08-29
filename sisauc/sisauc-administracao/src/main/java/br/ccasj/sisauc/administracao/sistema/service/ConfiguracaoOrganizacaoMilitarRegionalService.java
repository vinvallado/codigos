package br.ccasj.sisauc.administracao.sistema.service;

import java.io.Serializable;
import java.util.List;

import br.ccasj.sisauc.administracao.sistema.domain.OrganizacaoMilitarRegional;
import br.ccasj.sisauc.administracao.sistema.domain.OrganizacaoMilitarSubordinada;

public interface ConfiguracaoOrganizacaoMilitarRegionalService extends Serializable {

	public void salvar(List<OrganizacaoMilitarSubordinada> subordinadasSemRegional,
			List<OrganizacaoMilitarSubordinada> subordinadas, OrganizacaoMilitarRegional regional);

	public OrganizacaoMilitarRegional obterRegionalSistema();
	
	public void definirRegionalSistema(Integer idOmRegional);

}
