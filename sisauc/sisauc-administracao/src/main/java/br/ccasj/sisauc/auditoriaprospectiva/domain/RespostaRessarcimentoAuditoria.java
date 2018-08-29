package br.ccasj.sisauc.auditoriaprospectiva.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.ccasj.sisauc.administracao.cadastro.domain.Especialidade;
import br.ccasj.sisauc.administracao.cadastro.domain.FaceDental;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoBase;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoPedidoSolicitacaoRessarcimento;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "resposta_ressarcimento_auditoria", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class RespostaRessarcimentoAuditoria extends EntidadeGenerica {

	private static final long serialVersionUID = -8041108990123760547L;

	private static final String SEQ_NAME = "resposta_ressarcimento_auditoria_id_seq";

	private ProcedimentoBase procedimento;
	private Especialidade especialidade;
	private String descricaoOutros;
	private Double orcamento;
	private String observacao;
	private Boolean aprovado;
	private String justificativa;
	private boolean opme = false;
	private String opmeDescricao;
	private Double opmeValor;
	private String opmeJustificativa;
	private String observacaoARE;
	private Double valorRerenciaAuditor;
	private Integer dente;
	private FaceDental faceDental;
	
	public RespostaRessarcimentoAuditoria(){}
	
	public RespostaRessarcimentoAuditoria(ProcedimentoPedidoSolicitacaoRessarcimento pedido) {
		this.procedimento = pedido.getProcedimento();
		this.especialidade = pedido.getEspecialidade();
		this.descricaoOutros = pedido.getDescricaoOutros();
		this.orcamento = pedido.getOrcamento();
		this.dente = pedido.getDente();
		this.faceDental = pedido.getFaceDental();
	}

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}
	
	@ManyToOne
	@JoinColumn(name = "id_procedimento")
	public ProcedimentoBase getProcedimento() {
		return procedimento;
	}
	public void setProcedimento(ProcedimentoBase procedimento) {
		this.procedimento = procedimento;
	}


	@ManyToOne
	@JoinColumn(name = "id_especialidade")
	public Especialidade getEspecialidade() {
		return especialidade;
	}
	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	@Column(name = "descricao_outros")
	public String getDescricaoOutros() {
		return descricaoOutros;
	}
	public void setDescricaoOutros(String descricaoOutros) {
		this.descricaoOutros = descricaoOutros;
	}

	@Column
	public Double getOrcamento() {
		return orcamento;
	}
	public void setOrcamento(Double orcamento) {
		this.orcamento = orcamento;
	}

	@Column
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Column
	public Boolean getAprovado() {
		return aprovado;
	}
	public void setAprovado(Boolean aprovado) {
		this.aprovado = aprovado;
	}

	@Column
	public String getJustificativa() {
		return justificativa;
	}
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	@Column
	public boolean isOpme() {
		return opme;
	}
	public void setOpme(boolean opme) {
		this.opme = opme;
	}

	@Column(name = "opme_descricao")
	public String getOpmeDescricao() {
		return opmeDescricao;
	}
	public void setOpmeDescricao(String opmeDescricao) {
		this.opmeDescricao = opmeDescricao;
	}

	@Column(name = "opme_valor")
	public Double getOpmeValor() {
		return opmeValor;
	}
	public void setOpmeValor(Double opmeValor) {
		this.opmeValor = opmeValor;
	}

	@Column(name = "opme_justificativa")
	public String getOpmeJustificativa() {
		return opmeJustificativa;
	}
	public void setOpmeJustificativa(String opmeJustificativa) {
		this.opmeJustificativa = opmeJustificativa;
	}

	@Column(name = "observacao_are")
	public String getObservacaoARE() {
		return observacaoARE;
	}

	public void setObservacaoARE(String observacaoARE) {
		this.observacaoARE = observacaoARE;
	}

	@Column(name = "valor_referencia_auditor")
	public Double getValorRerenciaAuditor() {
		return valorRerenciaAuditor;
	}

	public void setValorRerenciaAuditor(Double valorRerenciaAuditor) {
		this.valorRerenciaAuditor = valorRerenciaAuditor;
	}
	
	public Integer getDente() {
		return dente;
	}

	public void setDente(Integer dente) {
		this.dente = dente;
	}

	@Column(name = "face_dental")
	@Enumerated(EnumType.STRING)
	public FaceDental getFaceDental() {
		return faceDental;
	}

	public void setFaceDental(FaceDental faceDental) {
		this.faceDental = faceDental;
	}
	
}