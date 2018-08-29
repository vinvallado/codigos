package br.mil.fab.consigext.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the T_TERMINO_CONSIGNACAO database table.
 * 
 */
@Entity
@Table(name="T_TERMINO_CONSIGNACAO")
@NamedQuery(name="TerminoConsignacao.findAll", query="SELECT t FROM TerminoConsignacao t")
public class TerminoConsignacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_TERMINO_CONSIGNACAO")
	private long id;

	@Column(name="NM_TERMINO")
	private String nmTermino;

	public TerminoConsignacao() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long idTerminoConsignacao) {
		this.id = idTerminoConsignacao;
	}

	public String getNmTermino() {
		return this.nmTermino;
	}

	public void setNmTermino(String nmTermino) {
		this.nmTermino = nmTermino;
	}

}