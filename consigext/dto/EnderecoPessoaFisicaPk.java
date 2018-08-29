package br.mil.fab.consigext.dto;

import java.io.Serializable;

import javax.jdo.annotations.Column;

public class EnderecoPessoaFisicaPk implements Serializable {

	private static final long serialVersionUID = -6689492291819407510L;
	
	@Column(name="CD_END_PES")
	private String cdEndPes;
	@Column(name="NR_ORDEM")
	private String nrOrdem;

	public String getCdEndPes() {
		return cdEndPes;
	}

	public void setCdEndPes(String cdEndPes) {
		this.cdEndPes = cdEndPes;
	}

	public String getNrOrdem() {
		return nrOrdem;
	}

	public void setNrOrdem(String nrOrdem) {
		this.nrOrdem = nrOrdem;
	}


}
