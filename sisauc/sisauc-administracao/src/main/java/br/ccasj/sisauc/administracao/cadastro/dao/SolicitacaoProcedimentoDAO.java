package br.ccasj.sisauc.administracao.cadastro.dao;

import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoProcedimento;

public interface SolicitacaoProcedimentoDAO extends SolicitacaoMedicaDAO<SolicitacaoProcedimento> {

	public int obterQuantidadeSolicitacoesProcedimentoPorAnoEOM (Integer idOm, Integer ano);
}
