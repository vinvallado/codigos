package br.mil.fab.boletim.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BuscarComponenteMotivoByMotivoRequest {
	@JsonProperty("cd_motivo")
	private String cdMotivo;

	public String getCdMotivo() {
		return cdMotivo;
	}

	public void setCdMotivo(String cdMotivo) {
		this.cdMotivo = cdMotivo;
	}
	
	
}
