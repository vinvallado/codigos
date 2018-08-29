package br.mil.fab.consigext.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the T_PARAMETRO_VISUALIZACAO database table.
 * 
 */
@Entity
@Table(name="T_PARAMETRO_VISUALIZACAO")
@NamedQuery(name="ParametroVisualizacao.findAll", query="SELECT p FROM ParametroVisualizacao p")
public class ParametroVisualizacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_PARAMETRO_VISUALIZACAO")
	private long id;

	@Column(name="DS_PARAMETRO")
	private String dsParametro;

	//bi-directional many-to-one association to Parametro
	@OneToMany(mappedBy="parametroVisualizacao")
	private List<Parametro> parametros;

	public ParametroVisualizacao() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long idParametroVisualizacao) {
		this.id = idParametroVisualizacao;
	}

	public String getDsParametro() {
		return this.dsParametro;
	}

	public void setDsParametro(String dsParametro) {
		this.dsParametro = dsParametro;
	}

	public List<Parametro> getParametros() {
		return this.parametros;
	}

	public void setParametros(List<Parametro> TParametros) {
		this.parametros = TParametros;
	}

	public Parametro addTParametro(Parametro TParametro) {
		getParametros().add(TParametro);
		TParametro.setParametroVisualizacao(this);

		return TParametro;
	}

	public Parametro removeTParametro(Parametro TParametro) {
		getParametros().remove(TParametro);
		TParametro.setParametroVisualizacao(null);

		return TParametro;
	}

}