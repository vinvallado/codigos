package br.mil.fab.consigext.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(schema = "SIG", name="T_BANCO")
	
public class AcessoBanco implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="CD_BANCO")
	private String cdBanco;
	@Column(name="SG_BANCO")
	private String sgBanco;
	@Column(name="NM_BANCO")
	private String nmBanco;
	@Column(name="ST_EXTINTO")
	private String stExtinto;
	@Column(name="NM_BANCO_PAGTO")
	private String nmBanco_Pagto;	
	
	public AcessoBanco() {
		
	}

	public AcessoBanco(String cdBanco, String sgBanco, String nmBanco, String stExtinto, String nmBanco_Pagto) {
		super();
		this.cdBanco = cdBanco;
		this.sgBanco = sgBanco;
		this.nmBanco = nmBanco;
		this.stExtinto = stExtinto;
		this.nmBanco_Pagto = nmBanco_Pagto;
	}

	
	public String getCdBanco() {
		return cdBanco;
	}


	public void setSgBanco(String sgBanco) {
		this.sgBanco = sgBanco;
	}


	public String getNmBanco() {
		return nmBanco;
	}


	public void setNmBanco(String nmBanco) {
		this.nmBanco = nmBanco;
	}


	public String getStExtinto() {
		return stExtinto;
	}


	public void setStExtinto(String stExtinto) {
		this.stExtinto = stExtinto;
	}


	public String getNmBanco_Pagto() {
		return nmBanco_Pagto;
	}


	public void setNmBanco_Pagto(String nmBanco_Pagto) {
		this.nmBanco_Pagto = nmBanco_Pagto;
	}
}
