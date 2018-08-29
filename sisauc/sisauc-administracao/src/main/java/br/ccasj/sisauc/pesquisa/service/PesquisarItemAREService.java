package br.ccasj.sisauc.pesquisa.service;

import java.util.List;

import br.ccasj.sisauc.emissaoar.domain.ItemAR;
import br.ccasj.sisauc.pesquisa.domain.ParametrosPesquisaItemARE;

public interface PesquisarItemAREService {
	
	public List<ItemAR> pesquisar(ParametrosPesquisaItemARE parametros);

}
