package br.mil.fab.consigext.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the T_PARAMETRO_TIPO database table.
 * 
 */
@Entity
@Table(name="T_PARAMETRO_TIPO")
@NamedQuery(name="ParametroTipo.findAll", query="SELECT p FROM ParametroTipo p")
public class ParametroTipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_PARAMETRO_TIPO")
	private long id;

	@Column(name="DS_TIPO")
	private String dsTipo;

	@Column(name="NM_TIPO")
	private String nmTipo;

	//bi-directional many-to-one association to Parametro
	@OneToMany(mappedBy="parametroTipo")
	private List<Parametro> parametros;

	public ParametroTipo() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long idParametroTipo) {
		this.id = idParametroTipo;
	}

	public String getDsTipo() {
		return this.dsTipo;
	}

	public void setDsTipo(String dsTipo) {
		this.dsTipo = dsTipo;
	}

	public String getNmTipo() {
		return this.nmTipo;
	}

	public void setNmTipo(String nmTipo) {
		this.nmTipo = nmTipo;
	}

	public List<Parametro> getParametros() {
		return this.parametros;
	}

	public void setParametros(List<Parametro> TParametros) {
		this.parametros = TParametros;
	}

	public Parametro addTParametro(Parametro TParametro) {
		getParametros().add(TParametro);
		TParametro.setParametroTipo(this);

		return TParametro;
	}

	public Parametro removeTParametro(Parametro TParametro) {
		getParametros().remove(TParametro);
		TParametro.setParametroTipo(null);

		return TParametro;
	}

}