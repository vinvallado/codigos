package br.ccasj.sisauc.administracao.formatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import br.ccasj.sisauc.emissaogab.domain.ItemGAB;

public class ItemGABFormatter implements SisaucFormatter<ItemGAB> {
	
	public String getCodigoDescricaoValor(ItemGAB itemGAB){
		return itemGAB
				.getConfiguracao()
				.getProcedimento()
				.getTabela()
				.getNome()
				.concat(" - ").concat(itemGAB.getConfiguracao().getProcedimento().getCodigo()).concat(") ")
				.concat(itemGAB.getConfiguracao().getProcedimento().getDescricao()).concat(" - Valor: ");
	}
	
	public double getConformidade(ItemGAB itemGAB) {
		double conformidade = itemGAB.getAuditoriaRetrospectiva().getValorApresentado() - itemGAB.getAuditoriaRetrospectiva().getValorAuditado();
		return conformidade;
	}
	
	public String getNumeroItem(ItemGAB itemGAB) {
		String numeroItem = itemGAB.getCodigo();
		numeroItem = numeroItem.substring(numeroItem.length() - 3);
		return numeroItem;
	}
	
	@Override
	public String getAutocompleteLabel(ItemGAB object) {
		return getCodigoDescricaoValor(object);
	}

	public List<ItemGAB> getListaItensOrdenado(Set<ItemGAB> itens){	
		return getListaItensOrdenado(new ArrayList<ItemGAB>(itens));
	}
	
	public List<ItemGAB> getListaItensOrdenado(List<ItemGAB> itens){	
		Collections.sort(itens);
		return itens;
	}

}
