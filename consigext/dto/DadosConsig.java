package br.mil.fab.consigext.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;

public class DadosConsig implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_CONSIGNACAO")
	private long id;
	@Column(name = "NR_CARENCIA", columnDefinition = "NUMBER(10) default 0")
	private Long nrCarencia = 0L;
	@Column(name = "VL_PRESTACAO")
	private BigDecimal vlPrestacao;
	@Column(name = "NR_PRESTACOES")
	private Long nrPrestacoes;
	@Column(name = "DS_IDENTIFICADOR", length = 50)
	private String dsIdentificador;
	@Column(name = "NR_PARCNPAGAS", length = 50)
	private Integer nrparcpagas;
	
	public DadosConsig() {
		
	}
	
	public DadosConsig(long id, Long nrCarencia, BigDecimal vlPrestacao, Long nrPrestacoes, String dsIdentificador) {
		super();
		this.id = id;
		this.nrCarencia = nrCarencia;
		this.vlPrestacao = vlPrestacao;
		this.nrPrestacoes = nrPrestacoes;
		this.dsIdentificador = dsIdentificador;
	}
	
	public DadosConsig(long id, Long nrCarencia, BigDecimal vlPrestacao, Long nrPrestacoes, String dsIdentificador, Integer nrparcpagas) {
		super();
		this.id = id;
		this.nrCarencia = nrCarencia;
		this.vlPrestacao = vlPrestacao;
		this.nrPrestacoes = nrPrestacoes;
		this.dsIdentificador = dsIdentificador;
		this.nrparcpagas = nrparcpagas;
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getNrCarencia() {
		return nrCarencia;
	}
	public void setNrCarencia(Long nrCarencia) {
		this.nrCarencia = nrCarencia;
	}
	public BigDecimal getVlPrestacao() {
		return vlPrestacao;
	}
	public void setVlPrestacao(BigDecimal vlPrestacao) {
		this.vlPrestacao = vlPrestacao;
	}
	public Long getNrPrestacoes() {
		return nrPrestacoes;
	}
	public void setNrPrestacoes(Long nrPrestacoes) {
		this.nrPrestacoes = nrPrestacoes;
	}
	public String getDsIdentificador() {
		return dsIdentificador;
	}
	public void setDsIdentificador(String dsIdentificador) {
		this.dsIdentificador = dsIdentificador;
	}
	public Integer getNrparcpagas() {
		return nrparcpagas;
	}
	public void setNrparcpagas(Integer nrparcpagas) {
		this.nrparcpagas = nrparcpagas;
	}
	
}
