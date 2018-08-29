package br.ccasj.sisauc.administracao.cadastro.domain;

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
import javax.validation.constraints.NotNull;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "procedimento_pedido_solicitacao_ressarcimento", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false) )
public class ProcedimentoPedidoSolicitacaoRessarcimento extends EntidadeGenerica {

	private static final long serialVersionUID = -6450430901772998883L;

	private static final String SEQ_NAME = "procedimento_pedido_solicitacao_ressarcimento_id_seq";
	
	private String observacao;
	private String descricaoOutros;
	private Double orcamento;
	private int quantidade = 1;
	private ProcedimentoBase procedimento;
	private Especialidade especialidade;
	private Integer dente;
	private FaceDental faceDental;
	

	public ProcedimentoPedidoSolicitacaoRessarcimento() {
		super();
	}

	public ProcedimentoPedidoSolicitacaoRessarcimento(Integer id) {
		super(id);
	}

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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

	@NotNull(message = "O campo quantidade é obrigatório")
	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	@ManyToOne
	@JoinColumn(name = "id_procedimento_base")
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
