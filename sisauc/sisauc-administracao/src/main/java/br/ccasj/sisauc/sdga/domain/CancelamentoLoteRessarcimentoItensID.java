package br.ccasj.sisauc.sdga.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.ccasj.sisauc.emissaoar.domain.ItemAR;

@Embeddable
public class CancelamentoLoteRessarcimentoItensID implements Serializable {

	private static final long serialVersionUID = 8724405690128925594L;

	@ManyToOne
	@JoinColumn(name = "id_cancelamento_lote")
	private CancelamentoLoteRessarcimento cancelamentoLoteRessarcimento;

	@ManyToOne
	@JoinColumn(name = "id_item_ar")
	private ItemAR itemAR;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cancelamentoLoteRessarcimento == null) ? 0 : cancelamentoLoteRessarcimento.hashCode());
		result = prime * result + ((itemAR == null) ? 0 : itemAR.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CancelamentoLoteRessarcimentoItensID other = (CancelamentoLoteRessarcimentoItensID) obj;
		if (cancelamentoLoteRessarcimento == null) {
			if (other.cancelamentoLoteRessarcimento != null)
				return false;
		} else if (!cancelamentoLoteRessarcimento.equals(other.cancelamentoLoteRessarcimento))
			return false;
		if (itemAR == null) {
			if (other.itemAR != null)
				return false;
		} else if (!itemAR.equals(other.itemAR))
			return false;
		return true;
	}

	public CancelamentoLoteRessarcimento getCancelamentoLoteRessarcimento() {
		return cancelamentoLoteRessarcimento;
	}

	public void setCancelamentoLoteRessarcimento(CancelamentoLoteRessarcimento cancelamentoLoteRessarcimento) {
		this.cancelamentoLoteRessarcimento = cancelamentoLoteRessarcimento;
	}

	public ItemAR getItemAR() {
		return itemAR;
	}

	public void setItemAR(ItemAR itemAR) {
		this.itemAR = itemAR;
	}

}
