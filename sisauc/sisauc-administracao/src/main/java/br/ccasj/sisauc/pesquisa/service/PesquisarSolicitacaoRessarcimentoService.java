package br.ccasj.sisauc.pesquisa.service;

import java.util.List;

import br.ccasj.sisauc.pesquisa.domain.ParametrosPesquisaSolicitacaoRessarcimento;
import br.ccasj.sisauc.pesquisa.domain.ResultadoPesquisaSolicitacaoRessarcimentoVO;

public interface PesquisarSolicitacaoRessarcimentoService {
	
	public List<ResultadoPesquisaSolicitacaoRessarcimentoVO> pesquisar(ParametrosPesquisaSolicitacaoRessarcimento parametros);

}
