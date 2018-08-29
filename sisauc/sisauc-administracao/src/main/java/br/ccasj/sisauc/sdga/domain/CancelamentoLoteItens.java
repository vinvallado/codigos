package br.ccasj.sisauc.sdga.domain;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "cancelamento_lote_itens", schema = EntidadeGenerica.SCHEMA_SDGA)
public class CancelamentoLoteItens extends AcaoSDGA {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5971714564670380114L;
	
	@EmbeddedId private CancelamentoLoteItensID cancelamentoId;

	public CancelamentoLoteItensID getCancelamentoId() {
		return cancelamentoId;
	}

	public void setCancelamentoId(CancelamentoLoteItensID cancelamentoId) {
		this.cancelamentoId = cancelamentoId;
	}

	
	
}
