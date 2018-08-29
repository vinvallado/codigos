package br.mil.fab.boletim.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BuscarComponentesRequest {

	@JsonProperty("nm_campo")
	private String nmCampo;
	@JsonProperty("vl_lancado")
	private String vlLancado;
	
	public String getNmCampo() {
		return nmCampo;
	}
	public void setNmCampo(String nmCampo) {
		this.nmCampo = nmCampo;
	}
	public String getVlLancado() {
		return vlLancado;
	}
	public void setVlLancado(String vlLancado) {
		this.vlLancado = vlLancado;
	}

	
	
}
