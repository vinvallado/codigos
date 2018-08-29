package br.mil.fab.consigext.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the T_STATUS_PARCELA database table.
 * 
 */
@Entity
@Table(name="T_STATUS_PARCELA")
@NamedQuery(name="StatusParcela.findAll", query="SELECT s FROM StatusParcela s")
public class StatusParcela implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_STATUS_PARCELA")
	private Long id;

	@Column(name="NM_STATUS_PARCELA")
	private String nmStatusParcela;

	//bi-directional many-to-one association to ParcelaConsignacao
	@OneToMany(mappedBy="statusParcela")
	private List<ParcelaConsignacao> parcelaConsignacaos;

	public StatusParcela() {
	}

	public StatusParcela(long id) {
		super();
		this.id = id;
	}
	public Long getId() {
		return this.id;
	}

	public void setId(long idStatusParcela) {
		this.id = idStatusParcela;
	}

	public String getNmStatusParcela() {
		return this.nmStatusParcela;
	}

	public void setNmStatusParcela(String nmStatusParcela) {
		this.nmStatusParcela = nmStatusParcela;
	}

	public List<ParcelaConsignacao> getParcelaConsignacaos() {
		return this.parcelaConsignacaos;
	}

	public void setParcelaConsignacaos(List<ParcelaConsignacao> TParcelaConsignacaos) {
		this.parcelaConsignacaos = TParcelaConsignacaos;
	}

	public ParcelaConsignacao addTParcelaConsignacao(ParcelaConsignacao TParcelaConsignacao) {
		getParcelaConsignacaos().add(TParcelaConsignacao);
		TParcelaConsignacao.setStatusParcela(this);

		return TParcelaConsignacao;
	}

	public ParcelaConsignacao removeTParcelaConsignacao(ParcelaConsignacao TParcelaConsignacao) {
		getParcelaConsignacaos().remove(TParcelaConsignacao);
		TParcelaConsignacao.setStatusParcela(null);

		return TParcelaConsignacao;
	}

}