package br.ccasj.sisauc.pesquisa.service;

import java.util.List;

import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.pesquisa.domain.ParametrosPesquisaAR;

public interface PesquisarARService {
	
	public List<AutorizacaoRessarcimento> pesquisar(ParametrosPesquisaAR parametros);

}
