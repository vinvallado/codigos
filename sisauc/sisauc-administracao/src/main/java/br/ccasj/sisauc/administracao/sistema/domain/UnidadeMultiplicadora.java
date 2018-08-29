package br.ccasj.sisauc.administracao.sistema.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "unidade_multiplicadora", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class UnidadeMultiplicadora extends EntidadeGenerica {

	private static final long serialVersionUID = -488398658959350361L;
	
	private static final String SEQ_NAME = "unidade_multiplicadora_id_seq";
	
	private double valorFilme = 0.0;
	private double valorUco = 0.0;
	private double valorUsm = 0.0;
	
	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}
	
	public UnidadeMultiplicadora() {
		super();
	}

	public UnidadeMultiplicadora(Integer id, double valorFilme, double valorUco, double valorUsm) {
		super();
		this.id = id;
		this.valorFilme = valorFilme;
		this.valorUco = valorUco;
		this.valorUsm = valorUsm;
	}

	@NotNull
	@Column(name = "valor_filme")
	public double getValorFilme() {
		return valorFilme;
	}
	
	public void setValorFilme(double valorFilme) {
		this.valorFilme = valorFilme;
	}
	
	@NotNull
	@Column(name = "valor_uco")
	public double getValorUco() {
		return valorUco;
	}
	
	public void setValorUco(double valorUco) {
		this.valorUco = valorUco;
	}

	@NotNull
	@Column(name = "valor_usm")
	public double getValorUsm() {
		return valorUsm;
	}

	public void setValorUsm(double valorUsm) {
		this.valorUsm = valorUsm;
	}

	
	
}
