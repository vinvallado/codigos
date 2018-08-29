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

import br.ccasj.sisauc.auditoriaretrospectiva.domain.AuditoriaRetrospectivaRessarcimento;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.MetadadoValorAuditoriaRetrospectiva;
import br.ccasj.sisauc.emissaoar.dao.impl.AutorizacaoRessarcimentoDAOImpl;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.ItemAR;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.Usuario;

@Entity
@Table(name = "cancelamento_auditoria_retrospectiva_ar", schema = EntidadeGenerica.SCHEMA_SDGA)
@AttributeOverride(name = "id", 
column = @Column(name = "id", 
insertable = false, 
updatable = false))
public class CancelamentoAuditoriaRetrospectivaARE extends AcaoSDGA {
	
	private static final long serialVersionUID = 7268380453458766074L;

	private static final String SEQ_NAME = "cancelamento_auditoria_retrospectiva_ar_seq";
	
	private Double valorApresentado;
	private Double valorEstimado;
	private Double valorRessarcimento;
	private boolean naoRealizado;
	private String justificativaAuditRetrospARE;
	private AuditoriaRetrospectivaRessarcimento auditoriaRetrospectivaRessarcimento;
	private Usuario auditor;
	private ItemAR itemAR;
	
	/*
		 CREATE TABLE sch_sdga.cancelamento_auditoria_retrospectiva_ar (
	  */
	  
	public CancelamentoAuditoriaRetrospectivaARE() {
		super();
	}

	public CancelamentoAuditoriaRetrospectivaARE(ItemAR itemAR) {
		this();
		this.itemAR = itemAR;
		this.valorApresentado = itemAR.getAuditoriaRetrospectiva().getValorApresentado();
		this.valorEstimado = itemAR.getAuditoriaRetrospectiva().getValorEstimado();
		this.valorRessarcimento = itemAR.getAuditoriaRetrospectiva().getValorRessarcimento();
		this.naoRealizado  = itemAR.getAuditoriaRetrospectiva().getNaoRealizado();
		this.justificativaAuditRetrospARE  = itemAR.getAuditoriaRetrospectiva().getJustificativa();
		this.auditoriaRetrospectivaRessarcimento = itemAR.getAuditoriaRetrospectiva();
		this.auditor  = itemAR.getAuditoriaRetrospectiva().getAuditorRetrospectivo();
	}

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SDGA)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return super.getId();
	}
	
	
	@Column(name="valor_apresentado")
	public Double getValorApresentado() {
		return valorApresentado;
	}

	@Column(name="valor_estimado")
	public Double getValorEstimado() {
		return valorEstimado;
	}

	@Column(name="valor_ressarcimento")
	public Double getValorRessarcimento() {
		return valorRessarcimento;
	}

	@NotNull
	@Column(name="nao_realizado")
	public boolean isNaoRealizado() {
		return naoRealizado;
	}

	@Column(name="justificativa_audit_retrosp_ar")
	public String getJustificativaAuditRetrospARE() {
		return justificativaAuditRetrospARE;
	}

	@ManyToOne
	@JoinColumn(name="id_auditoria_retrospectiva_ar")
	public AuditoriaRetrospectivaRessarcimento getAuditoriaRetrospectivaRessarcimento(){
		return auditoriaRetrospectivaRessarcimento;
	}
	
	@ManyToOne
	@JoinColumn(name="id_auditor")
	public Usuario getAuditor(){
		return auditor;
	}
	
	@ManyToOne
	@JoinColumn(name="id_item_ar")
	public ItemAR getItemAR(){
		return itemAR;
	}
	
	public void setValorApresentado(Double valorApresentado) {
		this.valorApresentado = valorApresentado;
	}

	public void setValorEstimado(Double valorEstimado) {
		this.valorEstimado = valorEstimado;
	}

	public void setValorRessarcimento(Double valorRessarcimento) {
		this.valorRessarcimento = valorRessarcimento;
	}

	public void setNaoRealizado(boolean naoRealizado) {
		this.naoRealizado = naoRealizado;
	}

	public void setJustificativaAuditRetrospARE(String justificativaAuditRetrospARE) {
		this.justificativaAuditRetrospARE = justificativaAuditRetrospARE;
	}

	public void setAuditoriaRetrospectivaRessarcimento(
			AuditoriaRetrospectivaRessarcimento auditoriaRetrospectivaRessarcimento) {
		this.auditoriaRetrospectivaRessarcimento = auditoriaRetrospectivaRessarcimento;
	}

	public void setAuditor(Usuario auditor) {
		this.auditor = auditor;
	}

	public void setItemAR(ItemAR itemAR) {
		this.itemAR = itemAR;
	}
	
	
	
//	@OneToMany(cascade = CascadeType.ALL)
//	@JoinTable(name = "cancelamento_auditoria_retrosp_metadado_valor_auditoria_retrosp", schema = EntidadeGenerica.SCHEMA_SDGA, 
//		joinColumns =  @JoinColumn(name = "id_cancelamento_auditoria_retrospectiva"),
//		inverseJoinColumns = @JoinColumn(name = "id_metadado_valor_auditoria_retrospectiva")
//	)
//	public Set<MetadadoValorAuditoriaRetrospectiva> getValores() {
//		return valores;
//	}
//
//	public void setValores(Set<MetadadoValorAuditoriaRetrospectiva> valores) {
//		this.valores = valores;
//	}
	
}

