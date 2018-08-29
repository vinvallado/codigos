package br.ccasj.sisauc.sdga.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.ccasj.sisauc.emissaogab.domain.ItemGAB;

@Embeddable
public class CancelamentoLoteItensID implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "id_cancelamento_lote")
	private CancelamentoLote cancelamentoLote;
	
	@ManyToOne
	@JoinColumn(name = "id_item_gab")
	private ItemGAB itemGab;

	public CancelamentoLote getCancelamentoLote() {
		return cancelamentoLote;
	}
	
	public void setCancelamentoLote(CancelamentoLote cancelamentoLote) {
		this.cancelamentoLote = cancelamentoLote;
	}

	public ItemGAB getItemGab() {
		return itemGab;
	}

	public void setItemGab(ItemGAB itemGab) {
		this.itemGab = itemGab;
	}

}
