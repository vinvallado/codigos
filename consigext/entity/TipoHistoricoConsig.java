package br.mil.fab.consigext.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the T_TIPO_HISTORICO_CONSIGNACAO database table.
 * 
 */
@Entity
@Table(name="T_TIPO_HISTORICO_CONSIG")
public class TipoHistoricoConsig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_TIPO_HISTORICO_CONSIG")
	private long id;

	@Column(name="SG_TIPO_HISTORICO")
	private String sgTipoHistorico;

	public TipoHistoricoConsig() {
	}
	
	public TipoHistoricoConsig(long id) {
		this.id = id; 
	}

	public long getId() {
		return this.id;
	}

	public void setId(long idHistoricoConsignacao) {
		this.id = idHistoricoConsignacao;
	}
	

	public String getSgTipoHistorico() {
		return sgTipoHistorico;
	}

	public void setSgTipoHistorico(String sgTipoHistorico) {
		this.sgTipoHistorico = sgTipoHistorico;
	}

}