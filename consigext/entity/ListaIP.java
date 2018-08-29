package br.mil.fab.consigext.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="T_LISTA_IP")
public class ListaIP implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_LISTA_IP")
	private long id;

	@Column(name="TX_IP")
	private String txIp;

	//bi-directional many-to-one association to EntidadeConsig
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="ID_ENTIDADE_CONSIG")
	private EntidadeConsig entidadeConsig;

	public ListaIP() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long idListaIp) {
		this.id = idListaIp;
	}

	public String getTxIp() {
		return this.txIp;
	}

	public void setTxIp(String txIp) {
		this.txIp = txIp;
	}

	public EntidadeConsig getEntidadeConsig() {
		return this.entidadeConsig;
	}

	public void setEntidadeConsig(EntidadeConsig TEntidadeConsig) {
		this.entidadeConsig = TEntidadeConsig;
	}

}