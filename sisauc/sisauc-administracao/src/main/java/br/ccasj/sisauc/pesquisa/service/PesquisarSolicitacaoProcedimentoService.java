package br.ccasj.sisauc.pesquisa.service;

import java.util.List;

import br.ccasj.sisauc.pesquisa.domain.ParametrosPesquisaSolicitacaoProcedimento;
import br.ccasj.sisauc.pesquisa.domain.ResultadoPesquisaSolicitacaoProcedimentoVO;

public interface PesquisarSolicitacaoProcedimentoService {
	
	public List<ResultadoPesquisaSolicitacaoProcedimentoVO> pesquisar(ParametrosPesquisaSolicitacaoProcedimento parametros);

}
