package br.ccasj.sisauc.sdga.domain;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
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

import br.ccasj.sisauc.auditoriaretrospectiva.domain.MetadadoValorAuditoriaRetrospectiva;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "cancelamento_auditoria_retrospectiva", schema = EntidadeGenerica.SCHEMA_SDGA)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class CancelamentoAuditoriaRetrospectiva extends AcaoSDGA {
	
	private static final long serialVersionUID = 7268380453458766074L;

	private static final String SEQ_NAME = "cancelamento_auditoria_retrospectiva_seq";
	
	private ItemGAB itemGab;
	private Double valorApresentado;
	private boolean realizado;
	private String observacaoApresentacao;
	private Double valorFinal;
	private Double valorAuditado;
	private String justificativaValorFinal;
	private String justificativaValorAuditado;
	private Set<MetadadoValorAuditoriaRetrospectiva> valores;

	public CancelamentoAuditoriaRetrospectiva() {
		super();
	}

	public CancelamentoAuditoriaRetrospectiva(ItemGAB itemGAB) {
		this();
		this.itemGab = itemGAB;
		this.valorApresentado = itemGAB.getAuditoriaRetrospectiva().getValorApresentado();
		this.realizado = itemGAB.getAuditoriaRetrospectiva().isRealizado();
		this.observacaoApresentacao = itemGAB.getAuditoriaRetrospectiva().getObservacaoApresentacao();
		this.valorFinal = itemGAB.getAuditoriaRetrospectiva().getValorFinal();
		this.valorAuditado = itemGAB.getAuditoriaRetrospectiva().getValorAuditado();
		this.justificativaValorFinal = itemGAB.getAuditoriaRetrospectiva().getJustificativaValorFinal();
		this.justificativaValorAuditado = itemGAB.getAuditoriaRetrospectiva().getJustificativaValorAuditado();
		this.setValores(itemGAB.getAuditoriaRetrospectiva().getValores());
	}

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SDGA)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return super.getId();
	}
	
	@ManyToOne
	@JoinColumn(name = "id_item_gab")
	public ItemGAB getItemGAB() {
		return itemGab;
	}

	public void setItemGAB(ItemGAB itemGAB) {
		this.itemGab = itemGAB;
	}

	@Column(name="valor_apresentado")
	public Double getValorApresentado() {
		return valorApresentado;
	}

	public void setValorApresentado(Double valorApresentado) {
		this.valorApresentado = valorApresentado;
	}

	@NotNull
	@Column(name="procedimento_realizado")
	public boolean isRealizado() {
		return realizado;
	}

	public void setRealizado(boolean realizado) {
		this.realizado = realizado;
	}

	@Column(name="observacao_apresentacao")
	public String getObservacaoApresentacao() {
		return observacaoApresentacao;
	}

	public void setObservacaoApresentacao(String observacaoApresentacao) {
		this.observacaoApresentacao = observacaoApresentacao;
	}

	@Column(name="valor_final")
	public Double getValorFinal() {
		return valorFinal;
	}

	public void setValorFinal(Double valorFinal) {
		this.valorFinal = valorFinal;
	}
	
	@Column(name="valor_auditado")
	public Double getValorAuditado() {
		return valorAuditado;
	}

	public void setValorAuditado(Double valorAuditado) {
		this.valorAuditado = valorAuditado;
	}

	@Column(name="justificativa_valor_final")
	public String getJustificativaValorFinal() {
		return justificativaValorFinal;
	}

	public void setJustificativaValorFinal(String justificativaValorFinal) {
		this.justificativaValorFinal = justificativaValorFinal;
	}

	@Column(name="justificativa_valor_auditado")
	public String getJustificativaValorAuditado() {
		return justificativaValorAuditado;
	}

	public void setJustificativaValorAuditado(String justificativaValorAuditado) {
		this.justificativaValorAuditado = justificativaValorAuditado;
	}
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "cancelamento_auditoria_retrosp_metadado_valor_auditoria_retrosp", schema = EntidadeGenerica.SCHEMA_SDGA, 
		joinColumns =  @JoinColumn(name = "id_cancelamento_auditoria_retrospectiva"),
		inverseJoinColumns = @JoinColumn(name = "id_metadado_valor_auditoria_retrospectiva")
	)
	public Set<MetadadoValorAuditoriaRetrospectiva> getValores() {
		return valores;
	}

	public void setValores(Set<MetadadoValorAuditoriaRetrospectiva> valores) {
		this.valores = valores;
	}
}

