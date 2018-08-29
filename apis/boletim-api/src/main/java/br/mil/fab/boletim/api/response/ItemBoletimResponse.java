package br.mil.fab.boletim.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemBoletimResponse {
	@JsonProperty("code")
	private String code;
	@JsonProperty("message")
	private String message;
	@JsonProperty("id_lancamento")
	private String idLancamento;
	@JsonProperty("id_processo")
	private long idProcesso;
	@JsonProperty("id_modulo")
	private long idModulo;
	
	public long getIdProcesso() {
		return idProcesso;
	}
	public void setIdProcesso(long idProcesso) {
		this.idProcesso = idProcesso;
	}
	
	public long getIdModulo() {
		return idModulo;
	}
	public void setIdModulo(long idModulo) {
		this.idModulo = idModulo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getIdLancamento() {
		return idLancamento;
	}
	public void setIdLancamento(String idLancamento) {
		this.idLancamento = idLancamento;
	}
	
	
	
	
	

}
