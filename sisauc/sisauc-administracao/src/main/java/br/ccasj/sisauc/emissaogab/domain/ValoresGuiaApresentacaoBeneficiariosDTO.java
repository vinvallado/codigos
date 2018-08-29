package br.ccasj.sisauc.emissaogab.domain;

import java.util.HashSet;
import java.util.Set;

public class ValoresGuiaApresentacaoBeneficiariosDTO {

	private Double valorTotal;
	private Set<MetadadoValorItemGAB> itens = new HashSet<MetadadoValorItemGAB>();

	public ValoresGuiaApresentacaoBeneficiariosDTO() {
		super();
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Set<MetadadoValorItemGAB> getItens() {
		return itens;
	}

	public void setItens(Set<MetadadoValorItemGAB> itens) {
		this.itens = itens;
	}

	
	
}
