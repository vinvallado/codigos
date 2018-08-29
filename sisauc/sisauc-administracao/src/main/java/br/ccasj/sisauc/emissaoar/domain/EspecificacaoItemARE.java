package br.ccasj.sisauc.emissaoar.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.ccasj.sisauc.administracao.cadastro.domain.FaceDental;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "especificacao_item_are", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class EspecificacaoItemARE extends EntidadeGenerica{
	
	private static final long serialVersionUID = -5081333450372350707L;
	
	private TipoEspecificacaoItemARE tipo;
	private String descricao;
	private Double valorReferencia = 0.0;
	private Double valorApresentado = 0.0;
	private Integer dente;
	private FaceDental faceDental;
	
	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}

	private static final String SEQ_NAME = "especificacao_item_are_id_seq";
	
	@Column
	@Enumerated(EnumType.STRING)
	public TipoEspecificacaoItemARE getTipo() {
		return tipo;
	}
	
	public void setTipo(TipoEspecificacaoItemARE tipo) {
		this.tipo = tipo;
	}
	
	@Column
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name = "valor_referencia")
	public Double getValorReferencia() {
		return valorReferencia;
	}

	public void setValorReferencia(Double valorReferencia) {
		this.valorReferencia = valorReferencia;
	}

	@Column(name = "valor_apresentado")
	public Double getValorApresentado() {
		return valorApresentado;
	}
	
	public void setValorApresentado(Double valorApresentado) {
		this.valorApresentado = valorApresentado;
	}
	
	@Column
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

	@Transient
	public Double getValorCalculado(){
		return Math.min(valorReferencia, valorApresentado==null ? 0.0 : valorApresentado);
	}

}
