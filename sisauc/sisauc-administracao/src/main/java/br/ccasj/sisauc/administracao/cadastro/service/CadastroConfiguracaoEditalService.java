package br.ccasj.sisauc.administracao.cadastro.service;

import java.util.List;

import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciado;
import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciadoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.Especialidade;
import br.ccasj.sisauc.administracao.cadastro.domain.GrupoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoBase;
import br.ccasj.sisauc.administracao.cadastro.domain.SubGrupoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.TipoCobrancaCredenciadoProcedimento;
import br.ccasj.sisauc.administracao.exceptions.ConfiguracaoExistenteException;

public interface CadastroConfiguracaoEditalService {


	public void salvar(ConfiguracaoEditalCredenciadoProcedimento configuracao) throws ConfiguracaoExistenteException;
	public void criarConfiguracoesPorProcedimento(ProcedimentoBase procedimento, TipoCobrancaCredenciadoProcedimento tipo, Double valor, ConfiguracaoEditalCredenciado credenciado, List<Especialidade> especialidades);
	public void criarConfiguracoesPorGrupo(GrupoProcedimento grupo, Class<? extends ProcedimentoBase> clazz, TipoCobrancaCredenciadoProcedimento tipo, Double valor, ConfiguracaoEditalCredenciado credenciado, List<Especialidade> especialidades);
	public void criarConfiguracoesPorSubGrupo(SubGrupoProcedimento subGrupoProcedimento, Class<? extends ProcedimentoBase> clazz, TipoCobrancaCredenciadoProcedimento tipo, Double valor, ConfiguracaoEditalCredenciado credenciado, List<Especialidade> especialidades);
	public void desativar(Integer id);
	public void desativar(List<ConfiguracaoEditalCredenciadoProcedimento> configuracoes);
	
}
