package br.mil.fab.consigext.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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

import br.mil.fab.consigext.enums.TipoSolicSaldoDevedor;
/**
 * The persistent class for the T_SOLIC_SALDO_DEVEDOR database table.
 * 
 */
@Entity
@Table(name="T_SOLIC_SALDO_DEVEDOR")
@SequenceGenerator(name="SQ_ID_SOLIC_SALDO_DEVEDOR",sequenceName="SQ_ID_SOLIC_SALDO_DEVEDOR",allocationSize=1)
public class SolicitacaoSaldoDevedor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="SQ_ID_SOLIC_SALDO_DEVEDOR",strategy=GenerationType.SEQUENCE)
	@Column(name="ID_SOLIC_SALDO_DEVEDOR")
	private long id;

	@Column(name="TP_SOLICITACAO")
	private String tipoSolicitacao;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_SOLICITACAO")
	private Date dtSolicitacao;

	@Column(name="NR_CPF")
	private String nrCpfResponsavel;

	@Column(name="TX_IP")
	private String txIp;
	
	@Column(name="ST_ATENDIDA")
	private long stAtendida;
	
	@Column(name="ID_FILE_DETALHES_CALCULO")
	private Long idFileDetalhesCalculo;
	
	@Column(name="NR_BANCO_DEPOSITO")
	private String nrBancoDeposito;
	
	@Column(name="NR_AGENCIA_DEPOSITO")
	private String nrAgenciaDeposito;
	
	@Column(name="NR_CONTA_DEPOSITO")
	private String nrContaDeposito;
	
	@Column(name="NM_FAVORECIDO_DEPOSITO")
	private String nmFavorecidoDeposito;
	
	@Column(name="NR_CNPJ_DEPOSITO")
	private String nrCnpjDeposito;
	
	@Column(name="TX_OBSERVACAO_DEPOSITO")
	private String observacaoDeposito;
	
	@Column(name="VL_SALDO_DEVEDOR")
	private BigDecimal vlSaldoDevedor;
	
	@Column(name="ID_FILE_DEMONSTRATIVO_CALCULO")
	private Long idFileDemonstrativoCalculo;
	
	@Column(name="ID_FILE_BOLETO")
	private Long idFileBoleto;
	
	@Column(name="TX_LINK_BOLETO")
	private String linkBoleto;
	
	//bi-directional many-to-one association to Consignacao
	@ManyToOne
	@JoinColumn(name="ID_CONSIGNACAO")
	private Consignacao consignacao;

	public SolicitacaoSaldoDevedor() {
	}

	public SolicitacaoSaldoDevedor(Date dtSolicitacao, String nrCpfResponsavel, String txIp,
			Consignacao consignacao, TipoSolicSaldoDevedor tipoSolicitacao) {
		super();
		this.dtSolicitacao = dtSolicitacao;
		this.nrCpfResponsavel = nrCpfResponsavel;
		this.txIp = txIp;
		this.consignacao = consignacao;
		this.tipoSolicitacao = tipoSolicitacao.getTipoSolicitacao();
		this.stAtendida = 0L;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTipoSolicitacao() {
		return tipoSolicitacao;
	}

	public void setTipoSolicitacao(String tipoSolicitacao) {
		this.tipoSolicitacao = tipoSolicitacao;
	}

	public Date getDtSolicitacao() {
		return dtSolicitacao;
	}

	public void setDtSolicitacao(Date dtSolicitacao) {
		this.dtSolicitacao = dtSolicitacao;
	}


	public long getStAtendida() {
		return stAtendida;
	}

	public void setStAtendida(long stAtendida) {
		this.stAtendida = stAtendida;
	}

	public String getNrCpfResponsavel() {
		return nrCpfResponsavel;
	}

	public void setNrCpfResponsavel(String nrCpfResponsavel) {
		this.nrCpfResponsavel = nrCpfResponsavel;
	}

	public String getTxIp() {
		return txIp;
	}

	public void setTxIp(String txIp) {
		this.txIp = txIp;
	}

	public Consignacao getConsignacao() {
		return consignacao;
	}

	public void setConsignacao(Consignacao consignacao) {
		this.consignacao = consignacao;
	}
	public Long getIdFileDetalhesCalculo() {
		return idFileDetalhesCalculo;
	}

	public void setIdFileDetalhesCalculo(Long idFileDetalhesCalculo) {
		this.idFileDetalhesCalculo = idFileDetalhesCalculo;
	}

	public Long getIdFileDemonstrativoCalculo() {
		return idFileDemonstrativoCalculo;
	}

	public void setIdFileDemonstrativoCalculo(Long idFileDemonstrativoCalculo) {
		this.idFileDemonstrativoCalculo = idFileDemonstrativoCalculo;
	}

	public Long getIdFileBoleto() {
		return idFileBoleto;
	}

	public void setIdFileBoleto(Long idFileBoleto) {
		this.idFileBoleto = idFileBoleto;
	}

	public String getLinkBoleto() {
		return linkBoleto;
	}

	public void setLinkBoleto(String linkBoleto) {
		this.linkBoleto = linkBoleto;
	}
	public String getNrBancoDeposito() {
		return nrBancoDeposito;
	}

	public void setNrBancoDeposito(String nrBancoDeposito) {
		this.nrBancoDeposito = nrBancoDeposito;
	}

	public String getNrAgenciaDeposito() {
		return nrAgenciaDeposito;
	}

	public void setNrAgenciaDeposito(String nrAgenciaDeposito) {
		this.nrAgenciaDeposito = nrAgenciaDeposito;
	}

	public String getNrContaDeposito() {
		return nrContaDeposito;
	}

	public void setNrContaDeposito(String nrContaDeposito) {
		this.nrContaDeposito = nrContaDeposito;
	}

	public String getNmFavorecidoDeposito() {
		return nmFavorecidoDeposito;
	}

	public void setNmFavorecidoDeposito(String nmFavorecidoDeposito) {
		this.nmFavorecidoDeposito = nmFavorecidoDeposito;
	}

	public String getNrCnpjDeposito() {
		return nrCnpjDeposito;
	}

	public void setNrCnpjDeposito(String nrCnpjDeposito) {
		this.nrCnpjDeposito = nrCnpjDeposito;
	}

	public String getObservacaoDeposito() {
		return observacaoDeposito;
	}

	public void setObservacaoDeposito(String observacaoDeposito) {
		this.observacaoDeposito = observacaoDeposito;
	}

	public BigDecimal getVlSaldoDevedor() {
		return vlSaldoDevedor;
	}

	public void setVlSaldoDevedor(BigDecimal vlSaldoDevedor) {
		this.vlSaldoDevedor = vlSaldoDevedor;
	}


}