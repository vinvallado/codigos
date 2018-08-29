package br.mil.fab.consigext.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the T_NATUREZA_SERVICO database table.
 * 
 */
@Entity
@Table(name="T_NATUREZA_SERVICO")
@NamedQuery(name="NaturezaServico.findAll", query="SELECT n FROM NaturezaServico n")
public class NaturezaServico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_NATUREZA_SERVICO")
	private long id;

	@Column(name="SG_NATUREZA")
	private String sgNatureza;

	//bi-directional many-to-one association to Servico
	@OneToMany(mappedBy="naturezaServico")
	@JsonIgnore
	private List<Servico> servicos;

	public NaturezaServico() {
	}
	
	public NaturezaServico(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long idNaturezaServico) {
		this.id = idNaturezaServico;
	}

	public String getSgNatureza() {
		return this.sgNatureza;
	}

	public void setSgNatureza(String sgNatureza) {
		this.sgNatureza = sgNatureza;
	}

	public List<Servico> getServicos() {
		return this.servicos;
	}

	public void setServicos(List<Servico> TServicos) {
		this.servicos = TServicos;
	}

	public Servico addTServico(Servico TServico) {
		getServicos().add(TServico);
		TServico.setNaturezaServico(this);

		return TServico;
	}

	public Servico removeTServico(Servico TServico) {
		getServicos().remove(TServico);
		TServico.setNaturezaServico(null);

		return TServico;
	}

}