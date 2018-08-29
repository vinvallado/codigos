package br.ccasj.sisauc.pesquisa.service;

import java.util.List;

import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.pesquisa.domain.ParametrosPesquisaItemGAB;

public interface PesquisarItemGABService {
	
	public List<ItemGAB> pesquisar(ParametrosPesquisaItemGAB parametros);

}
