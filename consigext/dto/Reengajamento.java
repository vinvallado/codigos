package br.mil.fab.consigext.dto;

import java.io.Serializable;

import javax.jdo.annotations.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Reengajamento implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="DT_REENGAJAMENTO")
	private String dtReengajamento;
	
	public Reengajamento() {
		super();
	}
	
	public Reengajamento(String dtReengajamento) {
		super();
		this.dtReengajamento = dtReengajamento;
	}

	public String getDtReengajamento() {
		return dtReengajamento;
	}

	public void setDtReengajamento(String dtReengajamento) {
		this.dtReengajamento = dtReengajamento;
	}
}
