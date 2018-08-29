package br.mil.fab.consigext.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;


public class ParametroServicoDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public long id;
	public long idTipoParametro;
	public String vlParametro;
	public String sgParametro;
	

	public ParametroServicoDTO() {
		super();
	}
	
	public ParametroServicoDTO(long id, String vlParametro, String sgParametro) {
		super();
		this.id = id;
		this.vlParametro = vlParametro;
		this.sgParametro = sgParametro;
	}
	
	public ParametroServicoDTO(long id, long idTipoParametro, String vlParametro, String sgParametro) {
		super();
		this.id = id;
		this.idTipoParametro = idTipoParametro;
		this.vlParametro = vlParametro;
		this.sgParametro = sgParametro; 
	}
	
	public long getid() {
		return id;
	}
	public void setid(long id) {
		this.id = id;
	}
	public String getVlParametro() {
		return vlParametro;
	}
	public void setVlParametro(String vlParametro) {
		this.vlParametro = vlParametro;
	}

	public long getIdTipoParametro() {
		return idTipoParametro;
	}

	public void setIdTipoParametro(long idTipoParametro) {
		this.idTipoParametro = idTipoParametro;
	}
	
	public String getSgParametro() {
		return sgParametro;
	}

	public void setSgParametro(String sgParametro) {
		this.sgParametro = sgParametro;
	}
}
