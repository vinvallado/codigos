package br.ccasj.sisauc.administracao.historico.domain;

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

import br.ccasj.sisauc.administracao.sistema.domain.UnidadeMultiplicadora;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.EntidadeHistoricoGenerica;

@Entity
@Table(name = "historico_valor_unidade_multiplicadora", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class HistoricoValorUnidadeMultiplicadora extends EntidadeHistoricoGenerica {

	private static final long serialVersionUID = 5088467589458690095L;
	private static final String SEQ_NAME = "historico_valor_unidade_multiplicadora_id_seq";

	private double valorFilme;
	private double valorUco;
	private double valorUsm;
	private UnidadeMultiplicadora unidadeMultiplicadora;

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}
	
	public HistoricoValorUnidadeMultiplicadora() {
		super();
	}

	public HistoricoValorUnidadeMultiplicadora(double valorFilme, double valorUco, double valorUsm,
			UnidadeMultiplicadora unidadeMultiplicadora) {
		super();
		this.valorFilme = valorFilme;
		this.valorUco = valorUco;
		this.valorUsm = valorUsm;
		this.unidadeMultiplicadora = unidadeMultiplicadora;
	}


	@Column(name = "valor_filme")
	public double getValorFilme() {
		return valorFilme;
	}

	public void setValorFilme(double valorFilme) {
		this.valorFilme = valorFilme;
	}

	@Column(name = "valor_uco")
	public double getValorUco() {
		return valorUco;
	}

	public void setValorUco(double valorUco) {
		this.valorUco = valorUco;
	}

	@Column(name = "valor_usm")
	public double getValorUsm() {
		return valorUsm;
	}

	public void setValorUsm(double valorUsm) {
		this.valorUsm = valorUsm;
	}

	@ManyToOne
	@JoinColumn(name = "id_unidade_multiplicadora")
	public UnidadeMultiplicadora getUnidadeMultiplicadora() {
		return unidadeMultiplicadora;
	}

	public void setUnidadeMultiplicadora(UnidadeMultiplicadora unidadeMultiplicadora) {
		this.unidadeMultiplicadora = unidadeMultiplicadora;
	}

}
