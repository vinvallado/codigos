package br.mil.fab.consigext.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EstabilidadeQuadroTempoServico implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TX_TEMPO_SERVICO")
	public String txTempoServico;
	
	@Column(name="ST_TEMPORARIO")
	public String stTemporario;
	
	@Column(name="SG_QDR")
	public String sgQdr;
	
	@Column(name="CD_QDR")
	public String cdQdr;
	
	
	public EstabilidadeQuadroTempoServico() {
		super();
	}

	public EstabilidadeQuadroTempoServico(String txTempoServico, String stTemporario, String sgQdr, String cdQdr) {
		super();
		this.txTempoServico = txTempoServico;
		this.stTemporario = stTemporario;
		this.sgQdr = sgQdr;
		this.cdQdr = cdQdr;
	}

	public String getTxTempoServico() {
		return txTempoServico;
	}

	public void setTxTempoServico(String txTempoServico) {
		this.txTempoServico = txTempoServico;
	}

	public String getStTemporario() {
		return stTemporario;
	}

	public void setStTemporario(String stTemporario) {
		this.stTemporario = stTemporario;
	}

	public String getSgQdr() {
		return sgQdr;
	}

	public void setSgQdr(String sgQdr) {
		this.sgQdr = sgQdr;
	}

	public String getCdQdr() {
		return cdQdr;
	}

	public void setCdQdr(String cdQdr) {
		this.cdQdr = cdQdr;
	}
	
}
