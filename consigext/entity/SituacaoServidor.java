package br.mil.fab.consigext.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the T_SITUACAO_SERVIDOR database table.
 * 
 */
@Entity
@Table(name="T_SITUACAO_SERVIDOR")
public class SituacaoServidor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_SITUACAO_SERVIDOR")
	private long id;

	@Column(name="NM_SITUACAO")
	private String nmSituacao;

	//bi-directional many-to-one association to ServidorConsig
	@OneToMany(mappedBy="situacaoServidor")
	private List<ServidorConsig> servidorConsigs;

	public SituacaoServidor() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long idSituacaoServidor) {
		this.id = idSituacaoServidor;
	}

	public String getNmSituacao() {
		return this.nmSituacao;
	}

	public void setNmSituacao(String nmSituacao) {
		this.nmSituacao = nmSituacao;
	}

	public List<ServidorConsig> getServidorConsigs() {
		return this.servidorConsigs;
	}

	public void setServidorConsigs(List<ServidorConsig> TServidorConsigs) {
		this.servidorConsigs = TServidorConsigs;
	}

	public ServidorConsig addTServidorConsig(ServidorConsig TServidorConsig) {
		getServidorConsigs().add(TServidorConsig);
		TServidorConsig.setSituacaoServidor(this);

		return TServidorConsig;
	}

	public ServidorConsig removeTServidorConsig(ServidorConsig TServidorConsig) {
		getServidorConsigs().remove(TServidorConsig);
		TServidorConsig.setSituacaoServidor(null);

		return TServidorConsig;
	}

}