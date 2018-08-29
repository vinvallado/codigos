package br.ccasj.sisauc.auditoriaprospectiva.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciadoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoPedidoSolicitacao;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "resposta_procedimento_auditoria", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class RespostaProcedimentoAuditoria extends EntidadeGenerica {

	private static final long serialVersionUID = -8041108990123760547L;

	private static final String SEQ_NAME = "resposta_procedimento_auditoria_id_seq";

	private ConfiguracaoEditalCredenciadoProcedimento credenciado;
	private String justificativa;
	private String opmeDescricao;
	private Double opmeValor;
	private String opmeJustificativa;
	private String observacaoGAB;
	private boolean opme = false;
	private Boolean aprovado;
	
	public RespostaProcedimentoAuditoria() {
		super();
	}
	
	public RespostaProcedimentoAuditoria(Integer id) {
		super(id);
	}

	public RespostaProcedimentoAuditoria(ProcedimentoPedidoSolicitacao pedido){
		super();
		this.credenciado = pedido.getCredenciado();
	}
	
	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}
	
	@ManyToOne
	@JoinColumn(name = "id_configuracao_edital_credenciado_procedimento")
	public ConfiguracaoEditalCredenciadoProcedimento getCredenciado() {
		return credenciado;
	}

	public void setCredenciado(ConfiguracaoEditalCredenciadoProcedimento credenciado) {
		this.credenciado = credenciado;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
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

	@Column(name = "observacao_gab")
	public String getObservacaoGAB() {
		return observacaoGAB;
	}

	public void setObservacaoGAB(String observacaoGAB) {
		this.observacaoGAB = observacaoGAB;
	}

	public boolean isOpme() {
		return opme;
	}

	public void setOpme(boolean opme) {
		this.opme = opme;
	}

	public Boolean getAprovado() {
		return aprovado;
	}

	public void setAprovado(Boolean aprovado) {
		this.aprovado = aprovado;
	}

}
