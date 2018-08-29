package br.mil.fab.boletim.api.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the T_BOLETIM database table.
 * 
 */
@Entity
@Table(name="T_BOLETIM")
@NamedQuery(name="Boletim.findAll", query="SELECT b FROM Boletim b")
public class Boletim implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_BOLETIM")
	private long idBoletim;

	@Column(name="CD_GRAU_SIGILO")
	private String cdGrauSigilo;

	@Column(name="CD_ORG")
	private String cdOrg;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_BOLETIM")
	private Date dtBoletim;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_IMPRESSAO")
	private Date dtImpressao;

	@Column(name="NR_BOLETIM")
	private String nrBoletim;

	@Column(name="ST_BLOQUEIO")
	private String stBloqueio;

	@Column(name="ST_IMPRESSAO_ORIGINAL")
	private String stImpressaoOriginal;

	@Column(name="ST_VALIDACAO")
	private String stValidacao;

	@Column(name="TP_BOLETIM")
	private String tpBoletim;

	@Column(name="TX_ASSINATURA_BOLETIM")
	private String txAssinaturaBoletim;

	@Column(name="TX_ASSINATURA_CONFERE")
	private String txAssinaturaConfere;

	@Column(name="TX_ASSINATURA_IMPEDIDO")
	private String txAssinaturaImpedido;

	@Column(name="TX_FUNCAO_BOLETIM")
	private String txFuncaoBoletim;

	@Column(name="TX_FUNCAO_CONFERE")
	private String txFuncaoConfere;

	@Column(name="TX_FUNCAO_IMPEDIDO")
	private String txFuncaoImpedido;

	@Column(name="TX_URL_SIMBOLO")
	private String txUrlSimbolo;

	//bi-directional many-to-one association to Boletim
	@ManyToOne
	@JoinColumn(name="ID_BOLETIM_ORIGINAL")
	private Boletim boletim;

	//bi-directional many-to-one association to Boletim
	@OneToMany(mappedBy="boletim")
	private List<Boletim> boletins;

	public Boletim() {
	}

	public long getIdBoletim() {
		return this.idBoletim;
	}

	public void setIdBoletim(long idBoletim) {
		this.idBoletim = idBoletim;
	}

	public String getCdGrauSigilo() {
		return this.cdGrauSigilo;
	}

	public void setCdGrauSigilo(String cdGrauSigilo) {
		this.cdGrauSigilo = cdGrauSigilo;
	}

	public String getCdOrg() {
		return this.cdOrg;
	}

	public void setCdOrg(String cdOrg) {
		this.cdOrg = cdOrg;
	}

	public Date getDtBoletim() {
		return this.dtBoletim;
	}

	public void setDtBoletim(Date dtBoletim) {
		this.dtBoletim = dtBoletim;
	}

	public Date getDtImpressao() {
		return this.dtImpressao;
	}

	public void setDtImpressao(Date dtImpressao) {
		this.dtImpressao = dtImpressao;
	}

	public String getNrBoletim() {
		return this.nrBoletim;
	}

	public void setNrBoletim(String nrBoletim) {
		this.nrBoletim = nrBoletim;
	}

	public String getStBloqueio() {
		return this.stBloqueio;
	}

	public void setStBloqueio(String stBloqueio) {
		this.stBloqueio = stBloqueio;
	}

	public String getStImpressaoOriginal() {
		return this.stImpressaoOriginal;
	}

	public void setStImpressaoOriginal(String stImpressaoOriginal) {
		this.stImpressaoOriginal = stImpressaoOriginal;
	}

	public String getStValidacao() {
		return this.stValidacao;
	}

	public void setStValidacao(String stValidacao) {
		this.stValidacao = stValidacao;
	}

	public String getTpBoletim() {
		return this.tpBoletim;
	}

	public void setTpBoletim(String tpBoletim) {
		this.tpBoletim = tpBoletim;
	}

	public String getTxAssinaturaBoletim() {
		return this.txAssinaturaBoletim;
	}

	public void setTxAssinaturaBoletim(String txAssinaturaBoletim) {
		this.txAssinaturaBoletim = txAssinaturaBoletim;
	}

	public String getTxAssinaturaConfere() {
		return this.txAssinaturaConfere;
	}

	public void setTxAssinaturaConfere(String txAssinaturaConfere) {
		this.txAssinaturaConfere = txAssinaturaConfere;
	}

	public String getTxAssinaturaImpedido() {
		return this.txAssinaturaImpedido;
	}

	public void setTxAssinaturaImpedido(String txAssinaturaImpedido) {
		this.txAssinaturaImpedido = txAssinaturaImpedido;
	}

	public String getTxFuncaoBoletim() {
		return this.txFuncaoBoletim;
	}

	public void setTxFuncaoBoletim(String txFuncaoBoletim) {
		this.txFuncaoBoletim = txFuncaoBoletim;
	}

	public String getTxFuncaoConfere() {
		return this.txFuncaoConfere;
	}

	public void setTxFuncaoConfere(String txFuncaoConfere) {
		this.txFuncaoConfere = txFuncaoConfere;
	}

	public String getTxFuncaoImpedido() {
		return this.txFuncaoImpedido;
	}

	public void setTxFuncaoImpedido(String txFuncaoImpedido) {
		this.txFuncaoImpedido = txFuncaoImpedido;
	}

	public String getTxUrlSimbolo() {
		return this.txUrlSimbolo;
	}

	public void setTxUrlSimbolo(String txUrlSimbolo) {
		this.txUrlSimbolo = txUrlSimbolo;
	}

	public Boletim getBoletim() {
		return this.boletim;
	}

	public void setBoletim(Boletim boletim) {
		this.boletim = boletim;
	}

	public List<Boletim> getBoletins() {
		return this.boletins;
	}

	public void setBoletins(List<Boletim> boletins) {
		this.boletins = boletins;
	}

	public Boletim addBoletin(Boletim boletin) {
		getBoletins().add(boletin);
		boletin.setBoletim(this);

		return boletin;
	}

	public Boletim removeBoletin(Boletim boletin) {
		getBoletins().remove(boletin);
		boletin.setBoletim(null);

		return boletin;
	}

}