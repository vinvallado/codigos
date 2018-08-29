package br.mil.fab.consigext.dto;

import java.io.Serializable;

import javax.persistence.Id;

public class ServicoConsigParams implements Serializable{
	@Id
	Long idServicoConsig;
	
	public Long getIdServicoConsig() {
		return idServicoConsig;
	}


	public void setIdServicoConsig(Long idServicoConsig) {
		this.idServicoConsig = idServicoConsig;
	}


	public String getCdCaixa() {
		return cdCaixa;
	}


	public void setCdCaixa(String cdCaixa) {
		this.cdCaixa = cdCaixa;
	}


	public String getDsServico() {
		return dsServico;
	}


	public void setDsServico(String dsServico) {
		this.dsServico = dsServico;
	}


	String cdCaixa;
	
	String dsServico;

	private static final long serialVersionUID = 1L;

	
	public ServicoConsigParams(Long idServicoConsig, String dsServico, String cdCaixa) {
		this.idServicoConsig=idServicoConsig;
		this.dsServico=dsServico;
		this.cdCaixa=cdCaixa;
	}

	
}
