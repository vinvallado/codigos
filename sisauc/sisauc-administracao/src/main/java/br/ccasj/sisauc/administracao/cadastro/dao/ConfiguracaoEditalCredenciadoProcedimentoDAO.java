package br.ccasj.sisauc.administracao.cadastro.dao;

import java.util.List;
import java.util.Map;

import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciadoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.Especialidade;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoBase;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

public interface ConfiguracaoEditalCredenciadoProcedimentoDAO extends GenericEntityDAO<ConfiguracaoEditalCredenciadoProcedimento> {

	public List<ConfiguracaoEditalCredenciadoProcedimento> listarAtivasPorEdital(Integer idEdital);
	public boolean verificarPresencaCredenciadoProcedimentoEspecialidadePorEdital(Integer idConfig, Integer idConfiguracaoEditalCredenciado, ProcedimentoBase procedimento, Especialidade especialidade);
	public List<ConfiguracaoEditalCredenciadoProcedimento> listarCredenciadosVigentesPorProcedimentoEspecialidade(ProcedimentoBase procedimento, Especialidade especialidade);
	public List<ConfiguracaoEditalCredenciadoProcedimento> listarConfiguracoesAtivasEmEditaisVigentes();
	public List<ConfiguracaoEditalCredenciadoProcedimento> listarAtivasPorEditalECredenciado(Integer idEdital, Integer idCredenciado);
	public void desativar(List<ConfiguracaoEditalCredenciadoProcedimento> configuracoes);
	
	public List<ConfiguracaoEditalCredenciadoProcedimento> listarAtivasPorEditalLazyModel(int first, int pageSize, String sortField, String sortOrder, Map<String, Object> filters);
	public int obterQuantidadeResultadosLazyModel(Map<String, Object> filters);
	
}
