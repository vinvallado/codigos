package br.ccasj.sisauc.auditoriaretrospectiva.historico.domain;

import br.ccasj.sisauc.auditoriaretrospectiva.domain.AuditoriaRetrospectiva;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.EntidadeHistoricoGenerica;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "historico_auditoria_retrospectiva", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class HistoricoAuditoriaRetrospectivaRessarcimento extends EntidadeHistoricoGenerica {

	private static final long serialVersionUID = 6808736677958406001L;

	private static final String SEQ_NAME = "historico_auditoria_retrospectiva_id_seq";

	private Double valorApresentado;
	private Boolean realizado;
	private String observacaoApresentacao;
	private Double valorFinal;
	private Double valorAuditado;
	private String justificativaValorFinal;
	private String justificativaValorAuditado;
	private AuditoriaRetrospectiva auditoriaRetrospectiva;
	private String estadoItemGAB;

	public HistoricoAuditoriaRetrospectivaRessarcimento() {
		super();
	}
	public HistoricoAuditoriaRetrospectivaRessarcimento(AuditoriaRetrospectiva auditoriaRetrospectiva) {
		this();
		this.auditoriaRetrospectiva = auditoriaRetrospectiva;
		this.valorApresentado = auditoriaRetrospectiva.getValorApresentado();
		this.realizado = auditoriaRetrospectiva.isRealizado();
		this.observacaoApresentacao = auditoriaRetrospectiva.getObservacaoApresentacao();
		this.valorFinal = auditoriaRetrospectiva.getValorFinal();
		this.valorAuditado = auditoriaRetrospectiva.getValorAuditado();
		this.justificativaValorFinal = auditoriaRetrospectiva.getJustificativaValorFinal();
		this.justificativaValorAuditado = auditoriaRetrospectiva.getJustificativaValorAuditado();
	}

	public HistoricoAuditoriaRetrospectivaRessarcimento(AuditoriaRetrospectiva auditoriaRetrospectiva, ItemGAB itemGAB) {
		this();
		this.auditoriaRetrospectiva = auditoriaRetrospectiva;
		this.valorApresentado = auditoriaRetrospectiva.getValorApresentado();
		this.realizado = auditoriaRetrospectiva.isRealizado();
		this.observacaoApresentacao = auditoriaRetrospectiva.getObservacaoApresentacao();
		this.valorFinal = auditoriaRetrospectiva.getValorFinal();
		this.valorAuditado = auditoriaRetrospectiva.getValorAuditado();
		this.justificativaValorFinal = auditoriaRetrospectiva.getJustificativaValorFinal();
		this.justificativaValorAuditado = auditoriaRetrospectiva.getJustificativaValorAuditado();
		this.setEstadoItemGAB(itemGAB.getEstadoItemGAB().name());
	}
	
	
	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return super.getId();
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
	public Boolean isRealizado() {
		return realizado;
	}

	public void setRealizado(Boolean realizado) {
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
	
	@ManyToOne
	@JoinColumn(name = "id_auditoria_retrospectiva")
	public AuditoriaRetrospectiva getAuditoriaRetrospectiva() {
		return auditoriaRetrospectiva;
	}
	
	public void setAuditoriaRetrospectiva(
			AuditoriaRetrospectiva auditoriaRetrospectiva) {
		this.auditoriaRetrospectiva = auditoriaRetrospectiva;
	}
	
	@Column(name="estado_item_gab")
	public String getEstadoItemGAB() {
		return estadoItemGAB;
	}
	public void setEstadoItemGAB(String estadoItemGAB) {
		this.estadoItemGAB = estadoItemGAB;
	}

	
	

}
