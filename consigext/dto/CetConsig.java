package br.mil.fab.consigext.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import br.mil.fab.consigext.util.CalculoUtil;



@Entity
public class CetConsig implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	private Long idCet;
	private Long cdAnomes;
	private Long nrParcela;
	private Double vlCet;
	private String nmOrg;
	
	public CetConsig() {
		
	}
	
	public CetConsig(Long idCet) {
		super();
		this.idCet = idCet;
	}


	public CetConsig(Long idCet, Long cdAnomes, Long nrParcela, Double vlCet, String nmOrg) {
		super();
		this.idCet = idCet;
		this.cdAnomes = cdAnomes;
		this.nrParcela = nrParcela;
		this.vlCet =  vlCet;
		this.nmOrg = nmOrg;
	}


	public Long getIdCet() {
		return idCet;
	}

	public void setIdCet(Long idCet) {
		this.idCet = idCet;
	}

	public Long getCdAnomes() {
		return cdAnomes;
	}

	public void setCdAnomes(Long cdAnomes) {
		this.cdAnomes = cdAnomes;
	}

	public Long getNrParcela() {
		return nrParcela;
	}

	public void setNrParcela(Long nrParcela) {
		this.nrParcela = nrParcela;
	}

	public Double getVlCet() {
		return vlCet;
	}

	public void setVlCet(Double vlCet) {
		this.vlCet = vlCet;
	}

	public String getNmOrg() {
		return nmOrg;
	}

	public void setNmOrg(String nmOrg) {
		this.nmOrg = nmOrg;
	}
	
	public BigDecimal getCetAnual() {
		return CalculoUtil.cetMensalToAnual(vlCet.doubleValue());
	}

	
}
