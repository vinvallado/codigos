package br.mil.fab.consigext.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;


public class ParametroDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	long id;
	String dsParametro;
	long idParametroTipo;
	long idParametroVisualizacao; //1,3
	long idParametroSecao; // 2 - 14
	String sgParametro;
	Map<String,String> opcoes;
	
	public ParametroDTO() {
		super();
	}
	
	public ParametroDTO(long id, String dsParametro, long idParametroTipo, long idParametroVisualizacao, long idParametroSecao, String sgParametro, Map<String,String> opcoes) {
		super();
		this.id = id;
		this.dsParametro = dsParametro;
		this.idParametroTipo = idParametroTipo;
		this.idParametroVisualizacao = idParametroVisualizacao;
		this.idParametroSecao = idParametroSecao;
		this.sgParametro = sgParametro;
		this.opcoes = opcoes;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDsParametro() {
		return dsParametro;
	}
	public void setDsParametro(String dsParametro) {
		this.dsParametro = dsParametro;
	}
	public long getIdParametroTipo() {
		return idParametroTipo;
	}
	public void setIdParametroTipo(long idParametroTipo) {
		this.idParametroTipo = idParametroTipo;
	}
	public long getIdParametroVisualizacao() {
		return idParametroVisualizacao;
	}
	public void setIdParametroVisualizacao(long idParametroVisualizacao) {
		this.idParametroVisualizacao = idParametroVisualizacao;
	}
	public long getIdParametroSecao() {
		return idParametroSecao;
	}
	public void setIdParametroSecao(long idParametroSecao) {
		this.idParametroSecao = idParametroSecao;
	}
	public Map<String,String> getOpcoes() {
		return opcoes;
	}
	public void setOpcoes(Map<String,String> opcoes) {
		this.opcoes = opcoes;
	}

	public String getSgParametro() {
		return sgParametro;
	}

	public void setSgParametro(String sgParametro) {
		this.sgParametro = sgParametro;
	}
	
}
