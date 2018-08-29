package br.ccasj.sisauc.auditoriaretrospectiva.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "lote_itens", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class LoteItens extends EntidadeGenerica {

	private static final long serialVersionUID = -3596885419625960809L;
	
	@EmbeddedId private LoteItensID itensId;

	public LoteItensID getItensId() {
		return itensId;
	}

	public void setItensId(LoteItensID itensId) {
		this.itensId = itensId;
	}



}
