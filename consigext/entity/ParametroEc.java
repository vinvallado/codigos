package br.mil.fab.consigext.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the T_PARAMETRO_EC database table.
 * 
 */
@Entity
@Table(name="T_PARAMETRO_EC")
@NamedQuery(name="ParametroEc.findAll", query="SELECT p FROM ParametroEc p")
public class ParametroEc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_PARAMETRO_EC")
	private long id;

	@Column(name="VL_PARAMETRO")
	private String vlParametro;

	//bi-directional many-to-one association to EntidadeConsig
	@ManyToOne
	@JoinColumn(name="ID_ENTIDADE_CONSIG")
	private EntidadeConsig entidadeConsig;

	//bi-directional many-to-one association to TipoParametroEc
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="ID_TIPO_PARAMETRO_EC")
	private TipoParametroEC tipoParametroEc;

	public ParametroEc() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long idParametroEc) {
		this.id = idParametroEc;
	}

	public String getVlParametro() {
		return this.vlParametro;
	}

	public void setVlParametro(String vlParametro) {
		this.vlParametro = vlParametro;
	}

	public EntidadeConsig getEntidadeConsig() {
		return this.entidadeConsig;
	}

	public void setEntidadeConsig(EntidadeConsig TEntidadeConsig) {
		this.entidadeConsig = TEntidadeConsig;
	}

	public TipoParametroEC getTipoParametroEc() {
		return this.tipoParametroEc;
	}

	public void setTipoParametroEc(TipoParametroEC TTipoParametroEc) {
		this.tipoParametroEc = TTipoParametroEc;
	}

}