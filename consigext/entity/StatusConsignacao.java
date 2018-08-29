package br.mil.fab.consigext.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the T_STATUS_CONSIGNACAO database table.
 * 
 */
@Entity
@Table(name = "T_STATUS_CONSIGNACAO")
@NamedQuery(name = "StatusConsignacao.findAll", query = "SELECT s FROM StatusConsignacao s")
public class StatusConsignacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_STATUS_CONSIGNACAO")
	private long id;

	@Column(name = "NM_STATUS")
	private String nmStatus;

	// bi-directional many-to-one association to Consignacao
	@OneToMany(mappedBy = "statusConsignacao")
	private List<Consignacao> consignacaos;

	
	public StatusConsignacao() {

	}
	
	public StatusConsignacao(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long idStatusConsignacao) {
		this.id = idStatusConsignacao;
	}

	public String getNmStatus() {
		return this.nmStatus;
	}

	public void setNmStatus(String nmStatus) {
		this.nmStatus = nmStatus;
	}

	public List<Consignacao> getConsignacaos() {
		return this.consignacaos;
	}

	public void setConsignacaos(List<Consignacao> TConsignacaos) {
		this.consignacaos = TConsignacaos;
	}

	public Consignacao addTConsignacao(Consignacao TConsignacao) {
		getConsignacaos().add(TConsignacao);
		TConsignacao.setStatusConsignacao(this);

		return TConsignacao;
	}

	public Consignacao removeTConsignacao(Consignacao TConsignacao) {
		getConsignacaos().remove(TConsignacao);
		TConsignacao.setStatusConsignacao(null);

		return TConsignacao;
	}

}