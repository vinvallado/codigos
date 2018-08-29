package br.mil.fab.consigext.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the T_CARGA_ARQUIVO database table.
 * 
 */
@Entity
@Table(name="T_CARGA_ARQUIVO")
@NamedQuery(name="CargaArquivo.findAll", query="SELECT c FROM CargaArquivo c")
public class CargaArquivo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SQ_ID_CARGA_ARQUIVO", sequenceName="SQ_ID_CARGA_ARQUIVO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SQ_ID_CARGA_ARQUIVO")
	@Column(name="ID_CARGA_ARQUIVO")
	private Long idCargaArquivo;

	@Column(name="CD_ANOMES")
	private String cdAnomes;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_CARGA")
	private Date dtCarga;

	@Column(name="TP_CARGA")
	private String tpCarga;

	@Column(name="TX_LINK")
	private String txLink;
	
	@Column (name="ID_TIPO_CARGA")
	private Long idTipoCarga;
	
	@Column (name="TX_DESCRICAO")
	private String  descricao;

	@Column (name="ST_ATIVO")
	private Integer ativo;

	public CargaArquivo() {
	}

	public Long getIdCargaArquivo() {
		return this.idCargaArquivo;
	}

	public void setIdCargaArquivo(Long idCargaArquivo) {
		this.idCargaArquivo = idCargaArquivo;
	}

	public String getCdAnomes() {
		return this.cdAnomes;
	}

	public void setCdAnomes(String cdAnomes) {
		this.cdAnomes = cdAnomes;
	}

	public Date getDtCarga() {
		return this.dtCarga;
	}

	public void setDtCarga(Date dtCarga) {
		this.dtCarga = dtCarga;
	}

	public String getTpCarga() {
		return this.tpCarga;
	}

	public void setTpCarga(String tpCarga) {
		this.tpCarga = tpCarga;
	}

	public String getTxLink() {
		return this.txLink;
	}

	public void setTxLink(String txLink) {
		this.txLink = txLink;
	}

	public Long getIdTipoCarga() {
		return idTipoCarga;
	}

	public void setIdTipoCarga(Long idTipoCarga) {
		this.idTipoCarga = idTipoCarga;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}

}