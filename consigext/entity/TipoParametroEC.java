package br.mil.fab.consigext.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the T_TIPO_PARAMETRO_EC database table.
 * 
 */
@Entity
@Table(name="T_TIPO_PARAMETRO_EC")

public class TipoParametroEC implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_TIPO_PARAMETRO_EC")
	private long id;

	@Column(name="DS_PARAMETRO")
	private String dsParametro;

	@Column(name="NM_PARAMETRO")
	private String nmParametro;

	public TipoParametroEC() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long idTipoParametroEc) {
		this.id = idTipoParametroEc;
	}

	public String getDsParametro() {
		return this.dsParametro;
	}

	public void setDsParametro(String dsParametro) {
		this.dsParametro = dsParametro;
	}

	public String getNmParametro() {
		return this.nmParametro;
	}

	public void setNmParametro(String nmParametro) {
		this.nmParametro = nmParametro;
	}

}