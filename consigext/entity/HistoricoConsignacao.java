package br.mil.fab.consigext.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the T_HISTORICO_CONSIGNACAO database table.
 * 
 */
@Entity
@Table(name="T_HISTORICO_CONSIGNACAO")
@SequenceGenerator(name="SQ_ID_HISTORICO_CONSIGNACAO",sequenceName="SQ_ID_HISTORICO_CONSIGNACAO",allocationSize=1)
public class HistoricoConsignacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="SQ_ID_HISTORICO_CONSIGNACAO",strategy=GenerationType.SEQUENCE)
	@Column(name="ID_HISTORICO_CONSIGNACAO")
	private long id;

	@Column(name="DS_HISTORICO")
	private String dsHistorico;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_HISTORICO")
	private Date dtHistorico;

	@Column(name="NR_CPF_RESPONSAVEL")
	private String nrCpfResponsavel;

	@Column(name="TX_IP")
	private String txIp;
	
	@Column(name="TX_COLUNA")
	private String txColuna;
	
	@Column(name="VL_ANTERIOR")
	private String vlAnterior;
	
	@Column(name="VL_NOVO")
	private String vlNovo;

	//bi-directional many-to-one association to Consignacao
	@ManyToOne
	@JoinColumn(name="ID_CONSIGNACAO")
	private Consignacao consignacao;

	@ManyToOne
	@JoinColumn(name="ID_TIPO_HISTORICO_CONSIG")
	private TipoHistoricoConsig tipoHistoricoConsig;
	
	// bi-directional many-to-one association to HistoricoConsignacao
	@OneToMany(mappedBy = "historicoConsignacao", fetch=FetchType.EAGER,  cascade = CascadeType.ALL)
	private List<HistoricoParcela> historicoParcela;

	public HistoricoConsignacao() {
	}

	public HistoricoConsignacao(String dsHistorico, Date dtHistorico, String nrCpfResponsavel, String txIp,
			Consignacao consignacao, TipoHistoricoConsig tipoHistoricoConsig) {
		super();
		this.dsHistorico = dsHistorico;
		this.dtHistorico = dtHistorico;
		this.nrCpfResponsavel = nrCpfResponsavel;
		this.txIp = txIp;
		this.consignacao = consignacao;
		this.tipoHistoricoConsig = tipoHistoricoConsig;
	}


	public HistoricoConsignacao(String dsHistorico, Date dtHistorico, String nrCpfResponsavel, String txIp,
			Consignacao consignacao, TipoHistoricoConsig tipoHistoricoConsig, String txColuna, String vlAnterior, String vlNovo) {
		super();
		this.dsHistorico = dsHistorico;
		this.dtHistorico = dtHistorico;
		this.nrCpfResponsavel = nrCpfResponsavel;
		this.txIp = txIp;
		this.consignacao = consignacao;
		this.tipoHistoricoConsig = tipoHistoricoConsig;
		this.vlAnterior = vlAnterior;
		this.vlNovo = vlNovo;
		this.txColuna = txColuna;		

	}
	
	public HistoricoConsignacao(String dsHistorico, Date dtHistorico, String nrCpfResponsavel, String txIp,
			Consignacao consignacao, TipoHistoricoConsig tipoHistoricoConsig, String txColuna, String vlAnterior, String vlNovo, List<HistoricoParcela> historicoParcela) {
		super();
		this.dsHistorico = dsHistorico;
		this.dtHistorico = dtHistorico;
		this.nrCpfResponsavel = nrCpfResponsavel;
		this.txIp = txIp;
		this.consignacao = consignacao;
		this.tipoHistoricoConsig = tipoHistoricoConsig;
		this.vlAnterior = vlAnterior;
		this.vlNovo = vlNovo;
		this.txColuna = txColuna;		
		this.historicoParcela = historicoParcela;

	}
	
	public long getId() {
		return this.id;
	}

	public void setId(long idHistoricoConsignacao) {
		this.id = idHistoricoConsignacao;
	}

	public String getDsHistorico() {
		return this.dsHistorico;
	}

	public void setDsHistorico(String dsHistorico) {
		this.dsHistorico = dsHistorico;
	}

	public Date getDtHistorico() {
		return this.dtHistorico;
	}

	public void setDtHistorico(Date dtHistorico) {
		this.dtHistorico = dtHistorico;
	}

	public String getNrCpfResponsavel() {
		return this.nrCpfResponsavel;
	}

	public void setNrCpfResponsavel(String nrCpfResponsavel) {
		this.nrCpfResponsavel = nrCpfResponsavel;
	}

	public String getTxIp() {
		return this.txIp;
	}

	public void setTxIp(String txIp) {
		this.txIp = txIp;
	}

	public Consignacao getConsignacao() {
		return this.consignacao;
	}

	public void setConsignacao(Consignacao TConsignacao) {
		this.consignacao = TConsignacao;
	}

	public String getTxColuna() {
		return txColuna;
	}

	public void setTxColuna(String txColuna) {
		this.txColuna = txColuna;
	}

	public String getVlAnterior() {
		return vlAnterior;
	}

	public void setVlAnterior(String vlAnterior) {
		this.vlAnterior = vlAnterior;
	}

	public String getVlNovo() {
		return vlNovo;
	}

	public void setVlNovo(String vlNovo) {
		this.vlNovo = vlNovo;
	}

	public TipoHistoricoConsig getTipoHistoricoConsig() {
		return tipoHistoricoConsig;
	}

	public void setTipoHistoricoConsig(TipoHistoricoConsig tipoHistoricoConsig) {
		this.tipoHistoricoConsig = tipoHistoricoConsig;
	}

	public List<HistoricoParcela> getHistoricoParcela() {
		return historicoParcela;
	}

	public void setHistoricoParcela(List<HistoricoParcela> historicoParcela) {
		this.historicoParcela = historicoParcela;
	}

	
}