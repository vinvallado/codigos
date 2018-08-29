package br.ccasj.sisauc.sdga.domain;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.ccasj.sisauc.auditoriaretrospectiva.domain.Lote;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "cancelamento_lote", schema = EntidadeGenerica.SCHEMA_SDGA)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class CancelamentoLote extends AcaoSDGA {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4624964026261827832L;

	private static final String SEQ_NAME = "cancelamento_lote_seq";
	
	private Lote lote;
	private Set<ItemGAB> itens;

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SDGA)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return super.getId();
	}
	
	@ManyToOne
	@NotNull(message = "É necessário inserir um lote.")
	@JoinColumn(name = "id_lote")
	public Lote getLote() {
		return lote;
	}
	
	public void setLote(Lote lote) {
		this.lote = lote;
	}

	@OneToMany
	@JoinTable(name = "cancelamento_lote_itens", schema = EntidadeGenerica.SCHEMA_SDGA, 
		joinColumns =  @JoinColumn(name = "id_cancelamento_lote"),
		inverseJoinColumns = @JoinColumn(name = "id_item_gab")
	)
	public Set<ItemGAB> getItens() {
		return itens;
	}

	public void setItens(Set<ItemGAB> itens) {
		this.itens = itens;
	}

}
