package br.mil.fab.boletim.api.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemBoletimRequest {
	@JsonProperty("cd_motivo")
	private String cdMotivo;
	@JsonProperty("st_autorizacao")
	private String stAutorizacao;
	@JsonProperty("nr_ipUsuario")
	private String nrIpUsuario;
	@JsonProperty("nr_macUsuario")
	private String nrMacUsuario;
	@JsonProperty("cd_org")
	private String cdOrg;
	@JsonProperty("id_processo")
	private long idProcesso;
	@JsonProperty("id_modulo")
	private long idModulo;
	@JsonProperty("nr_ordem")
	private String nrOrdem;
	private List<BuscarComponentesRequest> componentes;
	
	
	public String getCdMotivo() {
		return cdMotivo;
	}
	public void setCdMotivo(String cdMotivo) {
		this.cdMotivo = cdMotivo;
	}
	public String getStAutorizacao() {
		return stAutorizacao;
	}
	public void setStAutorizacao(String stAutorizacao) {
		this.stAutorizacao = stAutorizacao;
	}
	public String getNrIpUsuario() {
		return nrIpUsuario;
	}
	public void setNrIpUsuario(String nrIpUsuario) {
		this.nrIpUsuario = nrIpUsuario;
	}
	public String getNrMacUsuario() {
		return nrMacUsuario;
	}
	public void setNrMacUsuario(String nrMacUsuario) {
		this.nrMacUsuario = nrMacUsuario;
	}
	public String getCdOrg() {
		return cdOrg;
	}
	public void setCdOrg(String cdOrg) {
		this.cdOrg = cdOrg;
	}
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
	public String getNrOrdem() {
		return nrOrdem;
	}
	public void setNrOrdem(String nrOrdem) {
		this.nrOrdem = nrOrdem;
	}
	public List<BuscarComponentesRequest> getComponentes() {
		return componentes;
	}
	public void setComponentes(List<BuscarComponentesRequest> componentes) {
		this.componentes = componentes;
	}
	
	
	
	
	
	

}
