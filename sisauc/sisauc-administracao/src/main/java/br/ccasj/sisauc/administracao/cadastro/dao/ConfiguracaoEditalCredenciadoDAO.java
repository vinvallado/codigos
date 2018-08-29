package br.ccasj.sisauc.administracao.cadastro.dao;

import java.util.List;

import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciado;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

public interface ConfiguracaoEditalCredenciadoDAO extends GenericEntityDAO<ConfiguracaoEditalCredenciado> {

	public ConfiguracaoEditalCredenciado mudarStatusAtivoConfiguracao(Integer id, boolean status);
	public List<ConfiguracaoEditalCredenciado> obterConfiguracoesPorEdital(Integer idEdital);
	public boolean verificarPresencaConfiguracaoComCredenciadoAtivoPorEdital(Integer idConfiguracao, Integer idCredenciado, Integer idEdital);
	public boolean verificarSeEditalPossuiCredenciado(Integer idCredenciado);
}