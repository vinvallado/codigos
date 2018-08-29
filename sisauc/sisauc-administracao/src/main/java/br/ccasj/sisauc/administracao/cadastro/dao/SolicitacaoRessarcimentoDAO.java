package br.ccasj.sisauc.administracao.cadastro.dao;

import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoRessarcimento;

public interface SolicitacaoRessarcimentoDAO extends SolicitacaoMedicaDAO<SolicitacaoRessarcimento> {

	public int obterQuantidadeSolicitacoesRessarcimentoPorAnoEOM (Integer idOm, Integer ano);
}
