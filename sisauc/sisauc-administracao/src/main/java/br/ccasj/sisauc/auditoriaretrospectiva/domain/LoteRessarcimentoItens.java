package br.ccasj.sisauc.auditoriaretrospectiva.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "lote_ressarcimento_itens", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class LoteRessarcimentoItens extends EntidadeGenerica {

	private static final long serialVersionUID = 3585003573973676901L;
	
	@EmbeddedId private LoteRessarcimentoItensID itensId;

	public LoteRessarcimentoItensID getItensId() {
		return itensId;
	}

	public void setItensId(LoteRessarcimentoItensID itensId) {
		this.itensId = itensId;
	}



}
