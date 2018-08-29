package br.ccasj.sisauc.administracao.cadastro.domain;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "empenho", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class Empenho  extends EntidadeGenerica implements Serializable  {
	

	private static final long serialVersionUID = 2909768808720612752L;

	private static final String SEQ_NAME = "empenho_id_seq";
	
	private String numero;
	private String verba;
	private double valor;
	private Date dataCriacao;
	private Date dataLimite;
	private Credenciado credenciado;
	private boolean finalizado;
	
	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return super.getId();
	}

	@NotNull(message = "Campo Número obrigatório")
	@Column(name="numero")
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	@NotNull(message = "Campo Verba obrigatório")
	@Column(name="verba")
	public String getVerba() {
		return verba;
	}

	public void setVerba(String verba) {
		this.verba = verba;
	}

	@NotNull(message = "Campo Valor obrigatório")
	@Column(name="valor")
	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	@NotNull(message = "Campo Data Criação obrigatório")
	@Temporal(TemporalType.DATE)
	@Column(name="data_criacao")
	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@NotNull(message = "Campo Data Limite obrigatório")
	@Temporal(TemporalType.DATE)
	@Column(name="data_limite")
	public Date getDataLimite() {
		return dataLimite;
	}

	public void setDataLimite(Date dataLimite) {
		this.dataLimite = dataLimite;
	}
	
	@NotNull(message = "Campo Credenciado obrigatório")
	@ManyToOne
	@JoinColumn(name = "id_credenciado")
	public Credenciado getCredenciado() {
		return credenciado;
	}
	
	public void setCredenciado(Credenciado credenciado) {
		this.credenciado = credenciado;
	}
	
	@NotNull
	@Column(name = "finalizado")
	public boolean isFinalizado() {
		return finalizado;
	}
	
	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}
	
}
