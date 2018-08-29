package br.mil.fab.consigext.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the T_PARCELA_CONSIGNACAO database table.
 * 
 */
@Entity
@Table(name="T_PARCELA_CONSIGNACAO")
@SequenceGenerator(name="SQ_ID_PARCELA_CONSIGNACAO",sequenceName="SQ_ID_PARCELA_CONSIGNACAO",allocationSize=1)
public class ParcelaConsignacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="SQ_ID_PARCELA_CONSIGNACAO",strategy=GenerationType.SEQUENCE)
	@Column(name="ID_PARCELA_CONSIGNACAO")
	private Long id;

	@Column(name="CD_ANOMES")
	private Long cdAnomes;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_DESCONTO")
	private Date dtDesconto;

	@Column(name="NR_PARCELA")
	private Long nrParcela;

	@Column(name="TX_OBRIGACAO")
	private String txObrigacao;
	
	@Column(name="VL_PARCELA_REALIZADO")
	private BigDecimal vlParcelaRealizado;

	public BigDecimal getVlParcelaRealizado() {
		return vlParcelaRealizado;
	}

	public void setVlParcelaRealizado(BigDecimal vlParcelaRealizado) {
		this.vlParcelaRealizado = vlParcelaRealizado;
	}

	@Column(name="VL_PARCELA")
	private BigDecimal vlParcela;

	//bi-directional many-to-one association to Consignacao
	@ManyToOne
	@JoinColumn(name="ID_CONSIGNACAO")
	private Consignacao consignacao;

	//bi-directional many-to-one association to StatusParcela
	@ManyToOne
	@JoinColumn(name="ID_STATUS_PARCELA")
	private StatusParcela statusParcela;

	public ParcelaConsignacao() {
	}
	
	public ParcelaConsignacao(Long cdAnomes, Long nrParcela, String txObrigacao, BigDecimal vlParcela,
			Consignacao consignacao, StatusParcela statusParcela) {
		super();
		this.cdAnomes = cdAnomes;
		this.nrParcela = nrParcela;
		this.txObrigacao = txObrigacao;
		this.vlParcela = vlParcela;
		this.consignacao = consignacao;
		this.statusParcela = statusParcela;
		vlParcelaRealizado = new BigDecimal(0);
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long idParcelaConsignacao) {
		this.id = idParcelaConsignacao;
	}

	public Long getCdAnomes() {
		return this.cdAnomes;
	}

	public void setCdAnomes(Long cdAnomes) {
		this.cdAnomes = cdAnomes;
	}

	public Date getDtDesconto() {
		return this.dtDesconto;
	}

	public void setDtDesconto(Date dtDesconto) {
		this.dtDesconto = dtDesconto;
	}

	public Long getNrParcela() {
		return this.nrParcela;
	}

	public void setNrParcela(Long nrParcela) {
		this.nrParcela = nrParcela;
	}

	public String getTxObrigacao() {
		return this.txObrigacao;
	}

	public void setTxObrigacao(String txObrigacao) {
		this.txObrigacao = txObrigacao;
	}

	public BigDecimal getVlParcela() {
		return this.vlParcela;
	}

	public void setVlParcela(BigDecimal vlParcela) {
		this.vlParcela = vlParcela;
	}

	public Consignacao getConsignacao() {
		return this.consignacao;
	}

	public void setConsignacao(Consignacao TConsignacao) {
		this.consignacao = TConsignacao;
	}

	public StatusParcela getStatusParcela() {
		return this.statusParcela;
	}

	public void setStatusParcela(StatusParcela TStatusParcela) {
		this.statusParcela = TStatusParcela;
	}

	

}