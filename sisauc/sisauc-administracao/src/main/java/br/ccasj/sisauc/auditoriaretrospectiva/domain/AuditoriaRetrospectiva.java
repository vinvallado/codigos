package br.ccasj.sisauc.auditoriaretrospectiva.domain;

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

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.Usuario;

@Entity
@Table(name = "auditoria_retrospectiva", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class AuditoriaRetrospectiva extends EntidadeGenerica {

	private static final long serialVersionUID = -3596885419625960809L;
	
	private static final String SEQ_NAME = "auditoria_retrospectiva_id_seq";
	
	private Double valorApresentado;
	private boolean realizado;
	private String observacaoApresentacao;
	private Double valorFinal;
	private Double valorAuditado;
	private String justificativaValorFinal;
	private String justificativaValorAuditado;
	private Motivo motivo;
	private Usuario auditorRetrospectivo;
	private Set<MetadadoValorAuditoriaRetrospectiva> valores;

	public AuditoriaRetrospectiva() {
		super();
	}

	public AuditoriaRetrospectiva(Integer id) {
		super(id);
	}
	
	public AuditoriaRetrospectiva(Double valorApresentado, boolean realizado, String observacaoApresentacao) {
		super();
		this.valorApresentado = valorApresentado;
		this.realizado = realizado;
		this.observacaoApresentacao = observacaoApresentacao;
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
	
	@ManyToOne
	@JoinColumn(name = "id_motivo")
	public Motivo getMotivo() {
		return motivo;
	}

	public void setMotivo(Motivo motivo) {
		this.motivo = motivo;
	}

	@ManyToOne
	@JoinColumn(name = "id_auditor")
	public Usuario getAuditorRetrospectivo() {
		return auditorRetrospectivo;
	}

	public void setAuditorRetrospectivo(Usuario auditorRetrospectivo) {
		this.auditorRetrospectivo = auditorRetrospectivo;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "auditoria_retrospectiva_metadado_valor_auditoria_retrospectiva", schema = EntidadeGenerica.SCHEMA_SISAUC, 
		joinColumns =  @JoinColumn(name = "id_auditoria_retrospectiva"),
		inverseJoinColumns = @JoinColumn(name = "id_metadado_valor_auditoria_retrospectiva")
	)
	public Set<MetadadoValorAuditoriaRetrospectiva> getValores() {
		return valores;
	}

	public void setValores(Set<MetadadoValorAuditoriaRetrospectiva> valores) {
		this.valores = valores;
	}
	
	public double somaValoresMetadados(){
		double resultado = 0;
		for (MetadadoValorAuditoriaRetrospectiva metadadoValorAuditoriaRetrospectiva : valores) {
			resultado += metadadoValorAuditoriaRetrospectiva.getValor();
		}
		return resultado;
	}
	
}
