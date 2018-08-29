package br.ccasj.sisauc.auditoriaretrospectiva.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.ccasj.sisauc.emissaogab.domain.ItemGAB;

@Embeddable
public class LoteItensID implements Serializable{
	
	
	private static final long serialVersionUID = -8528920864450334060L;

	@ManyToOne
	@JoinColumn(name="id_lote")
	private Lote lote;
	
	@ManyToOne
	@JoinColumn(name="id_item_gab")
	private ItemGAB itemGAB;

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public ItemGAB getItemGAB() {
		return itemGAB;
	}

	public void setItemGAB(ItemGAB itemGAB) {
		this.itemGAB = itemGAB;
	}
	
	
	

}
