package br.mil.fab.consigext.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the T_PARAMETRO_SECAO database table.
 * 
 */
@Entity
@Table(name="T_PARAMETRO_SECAO")
@NamedQuery(name="ParametroSecao.findAll", query="SELECT p FROM ParametroSecao p")
public class ParametroSecao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_PARAMETRO_SECAO")
	private long id;

	@Column(name="DS_SECAO")
	private String dsSecao;

	//bi-directional many-to-one association to Parametro
	@OneToMany(mappedBy="parametroSecao")
	private List<Parametro> parametros;

	public ParametroSecao() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long idParametroSecao) {
		this.id = idParametroSecao;
	}

	public String getDsSecao() {
		return this.dsSecao;
	}

	public void setDsSecao(String dsSecao) {
		this.dsSecao = dsSecao;
	}

	public List<Parametro> getParametros() {
		return this.parametros;
	}

	public void setParametros(List<Parametro> TParametros) {
		this.parametros = TParametros;
	}

	public Parametro addTParametro(Parametro TParametro) {
		getParametros().add(TParametro);
		TParametro.setParametroSecao(this);

		return TParametro;
	}

	public Parametro removeTParametro(Parametro TParametro) {
		getParametros().remove(TParametro);
		TParametro.setParametroSecao(null);

		return TParametro;
	}

}