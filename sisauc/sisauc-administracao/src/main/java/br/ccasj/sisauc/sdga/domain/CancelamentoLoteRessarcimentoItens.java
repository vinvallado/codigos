package br.ccasj.sisauc.sdga.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "cancelamento_lote_ressarcimento_itens", schema = EntidadeGenerica.SCHEMA_SDGA)
public class CancelamentoLoteRessarcimentoItens implements Serializable {

	private static final long serialVersionUID = -1212861023105636862L;

	@EmbeddedId
	private CancelamentoLoteRessarcimentoItensID cancRecId;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cancRecId == null) ? 0 : cancRecId.hashCode());
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
		CancelamentoLoteRessarcimentoItens other = (CancelamentoLoteRessarcimentoItens) obj;
		if (cancRecId == null) {
			if (other.cancRecId != null)
				return false;
		} else if (!cancRecId.equals(other.cancRecId))
			return false;
		return true;
	}

	public CancelamentoLoteRessarcimentoItensID getCancRecId() {
		return cancRecId;
	}

	public void setCancRecId(CancelamentoLoteRessarcimentoItensID cancRecId) {
		this.cancRecId = cancRecId;
	}

}
