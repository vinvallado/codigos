package br.mil.fab.consigext.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the T_ORIGEM_CONSIGNACAO database table.
 * 
 */
@Entity
@Table(name="T_ORIGEM_CONSIGNACAO")
@NamedQuery(name="OrigemConsignacao.findAll", query="SELECT o FROM OrigemConsignacao o")
public class OrigemConsignacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_ORIGEM_CONSIGNACAO")
	private long id;

	@Column(name="NM_ORIGEM")
	private String nmOrigem;

	//bi-directional many-to-one association to Consignacao
	@OneToMany(mappedBy="origemConsignacao")
	private List<Consignacao> consignacaos;

	public OrigemConsignacao() {
	}
	
	public OrigemConsignacao(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long idOrigemConsignacao) {
		this.id = idOrigemConsignacao;
	}

	public String getNmOrigem() {
		return this.nmOrigem;
	}

	public void setNmOrigem(String nmOrigem) {
		this.nmOrigem = nmOrigem;
	}

	public List<Consignacao> getConsignacaos() {
		return this.consignacaos;
	}

	public void setConsignacaos(List<Consignacao> TConsignacaos) {
		this.consignacaos = TConsignacaos;
	}

	public Consignacao addTConsignacao(Consignacao TConsignacao) {
		getConsignacaos().add(TConsignacao);
		TConsignacao.setOrigemConsignacao(this);

		return TConsignacao;
	}

	public Consignacao removeTConsignacao(Consignacao TConsignacao) {
		getConsignacaos().remove(TConsignacao);
		TConsignacao.setOrigemConsignacao(null);

		return TConsignacao;
	}

}