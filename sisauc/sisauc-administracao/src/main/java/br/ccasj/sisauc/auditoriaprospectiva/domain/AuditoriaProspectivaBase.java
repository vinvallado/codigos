package br.ccasj.sisauc.auditoriaprospectiva.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.ccasj.sisauc.administracao.cadastro.domain.EspecificacaoIsencaoCobranca;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.Usuario;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AuditoriaProspectivaBase extends EntidadeGenerica {

	private static final long serialVersionUID = 2909860309201419643L;
	
	protected String observacoes;
	protected boolean isento;
	protected Usuario auditor;
	protected EstadoAuditoriaProspectiva estado;
	protected Date dataFinalAuditoria;
	protected EspecificacaoIsencaoCobranca especificacao;
	
	public AuditoriaProspectivaBase() {
		super();
	}

	public AuditoriaProspectivaBase(Integer id) {
		super(id);
	}	
	
	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	@Column(name = "data_final_auditoria")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataFinalAuditoria() {
		return dataFinalAuditoria;
	}

	public void setDataFinalAuditoria(Date dataFinalAuditoria) {
		this.dataFinalAuditoria = dataFinalAuditoria;
	}

	@ManyToOne
	@JoinColumn(name = "id_usuario_auditor")
	public Usuario getAuditor() {
		return auditor;
	}

	public void setAuditor(Usuario auditor) {
		this.auditor = auditor;
	}

	@Enumerated(EnumType.STRING)
	public EstadoAuditoriaProspectiva getEstado() {
		return estado;
	}

	public void setEstado(EstadoAuditoriaProspectiva estado) {
		this.estado = estado;
	}

	public boolean isIsento() {
		return isento;
	}

	public void setIsento(boolean isento) {
		this.isento = isento;
	}

	@ManyToOne
	@JoinColumn(name = "id_especificacao_isencao_cobranca")
	public EspecificacaoIsencaoCobranca getEspecificacao() {
		return especificacao;
	}

	public void setEspecificacao(EspecificacaoIsencaoCobranca especificacao) {
		this.especificacao = especificacao;
	}

}
