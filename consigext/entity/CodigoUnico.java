package br.mil.fab.consigext.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the T_CODIGO_UNICO database table.
 * 
 */
@Entity
@Table(name="T_CODIGO_UNICO")
@SequenceGenerator(name="SQ_ID_CODIGO_UNICO",sequenceName="SQ_ID_CODIGO_UNICO",allocationSize=1)
@NamedQuery(name="CodigoUnico.findAll", query="SELECT c FROM CodigoUnico c")
public class CodigoUnico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator="SQ_ID_CODIGO_UNICO",strategy=GenerationType.SEQUENCE)
	@Column(name="ID_CODIGO_UNICO")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_VALIDADE")
	private Date dtValidade;

	@Column(name="ST_CODIGO_UNICO")
	private Long stCodigoUnico;

	@Column(name="VL_CODIGO_UNICO")
	private String vlCodigoUnico;

	//bi-directional many-to-one association to ServidorConsig
	@ManyToOne
	@JoinColumn(name="ID_SERVIDOR_CONSIG")
	private ServidorConsig servidorConsig;

	public CodigoUnico() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long idCodigoUnico) {
		this.id = idCodigoUnico;
	}

	public Date getDtValidade() {
		return this.dtValidade;
	}

	public void setDtValidade(Date dtValidade) {
		this.dtValidade = dtValidade;
	}

	public Long getStCodigoUnico() {
		return this.stCodigoUnico;
	}

	public void setStCodigoUnico(Long stCodigoUnico) {
		this.stCodigoUnico = stCodigoUnico;
	}

	public String getVlCodigoUnico() {
		return this.vlCodigoUnico;
	}
	
	public String getDtValidadeFormatado() {
		SimpleDateFormat f = new SimpleDateFormat("dd/MM/YYYY");
		return f.format(this.getDtValidade());
	}

	public void setVlCodigoUnico(String vlCodigoUnico) {
		this.vlCodigoUnico = vlCodigoUnico;
	}

	public ServidorConsig getServidorConsig() {
		return this.servidorConsig;
	}

	public void setServidorConsig(ServidorConsig servidorConsig) {
		this.servidorConsig = servidorConsig;
	}

}