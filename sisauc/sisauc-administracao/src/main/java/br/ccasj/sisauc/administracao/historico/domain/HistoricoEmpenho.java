package br.ccasj.sisauc.administracao.historico.domain;

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

import br.ccasj.sisauc.administracao.cadastro.domain.Credenciado;
import br.ccasj.sisauc.administracao.cadastro.domain.Empenho;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.EntidadeHistoricoGenerica;

@Entity
@Table(name = "historico_empenho", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class HistoricoEmpenho  extends EntidadeHistoricoGenerica implements Serializable  {

	private static final long serialVersionUID = -8155533108168218325L;
	private static final String SEQ_NAME = "historico_empenho_id_seq";

	private Empenho empenho;
	private String numero;
	private String verba;
	private double valor;
	private Date dataCriacao;
	private Date dataLimite;
	private Credenciado credenciado;
	
	public HistoricoEmpenho() {
		super();
	}
	
	public HistoricoEmpenho(Empenho empenho) {
		this();
		this.empenho = empenho;
		this.valor = empenho.getValor();
		this.numero = empenho.getNumero();
		this.dataCriacao = empenho.getDataCriacao();
		this.dataLimite = empenho.getDataLimite();
		this.credenciado = empenho.getCredenciado();
		this.verba = empenho.getVerba();
	}
	
	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return super.getId();
	}
	
	@ManyToOne
	@JoinColumn(name = "id_empenho")
	public Empenho getEmpenho() {
		return empenho;
	}
	
	public void setEmpenho(Empenho empenho) {
		this.empenho = empenho;
	}

	@Column(name="numero_empenho")
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	@Column(name="verba")
	public String getVerba() {
		return verba;
	}

	public void setVerba(String verba) {
		this.verba = verba;
	}

	@Column(name="valor")
	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="data_criacao")
	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="data_limite")
	public Date getDataLimite() {
		return dataLimite;
	}

	public void setDataLimite(Date dataLimite) {
		this.dataLimite = dataLimite;
	}

	@ManyToOne
	@JoinColumn(name = "id_credenciado")
	public Credenciado getCredenciado() {
		return credenciado;
	}

	public void setCredenciado(Credenciado credenciado) {
		this.credenciado = credenciado;
	}
	
	
}
