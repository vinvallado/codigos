package br.ccasj.sisauc.administracao.formatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import br.ccasj.sisauc.emissaoar.domain.ItemAR;

public class ItemARFormatter implements SisaucFormatter<ItemAR> {
	

	public List<ItemAR> getListaItensOrdenado(Set<ItemAR> itens){	
		return getListaItensOrdenado(new ArrayList<ItemAR>(itens));
	}
	
	public List<ItemAR> getListaItensOrdenado(List<ItemAR> itens){	
		Collections.sort(itens);
		return itens;
	}

	@Override
	public String getAutocompleteLabel(ItemAR object) {
		// TODO Auto-generated method stub
		return null;
	}

}
