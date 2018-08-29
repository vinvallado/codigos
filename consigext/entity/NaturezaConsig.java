package br.mil.fab.consigext.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;


/**
 * The persistent class for the T_NATUREZA_CONSIG database table.
 * 
 */
@Entity
@Table(name="T_NATUREZA_CONSIG")
@NamedQuery(name="NaturezaConsig.findAll", query="SELECT n FROM NaturezaConsig n")
public class NaturezaConsig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_NATUREZA")
	private long id;

	@Column(name="SG_NATUREZA")
	private String sgNatureza;

	//bi-directional many-to-one association to EntidadeConsig
	@JsonIgnore
	@OneToMany(mappedBy="naturezaConsig")
	private List<EntidadeConsig> entidadeConsigs;

	public NaturezaConsig() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long idNatureza) {
		this.id = idNatureza;
	}

	public String getSgNatureza() {
		return this.sgNatureza;
	}

	public void setSgNatureza(String sgNatureza) {
		this.sgNatureza = sgNatureza;
	}

	public List<EntidadeConsig> getEntidadeConsigs() {
		return this.entidadeConsigs;
	}

	public void setEntidadeConsigs(List<EntidadeConsig> TEntidadeConsigs) {
		this.entidadeConsigs = TEntidadeConsigs;
	}

	public EntidadeConsig addTEntidadeConsig(EntidadeConsig TEntidadeConsig) {
		getEntidadeConsigs().add(TEntidadeConsig);
		TEntidadeConsig.setNaturezaConsig(this);

		return TEntidadeConsig;
	}

	public EntidadeConsig removeTEntidadeConsig(EntidadeConsig TEntidadeConsig) {
		getEntidadeConsigs().remove(TEntidadeConsig);
		TEntidadeConsig.setNaturezaConsig(null);

		return TEntidadeConsig;
	}

}