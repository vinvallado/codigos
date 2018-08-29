package br.mil.fab.consigext.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class EnderecoOrganizacaoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CD_ORG")
	private String cdOrg;

	@Column(name="CD_END_ORG")
	private String cdEndOrg;

	public EnderecoOrganizacaoPK() {
	}
	
	public String getCdOrg() {
		return this.cdOrg;
	}
	public void setCdOrg(String cdOrg) {
		this.cdOrg = cdOrg;
	}
	public String getCdEndOrg() {
		return this.cdEndOrg;
	}
	public void setCdEndOrg(String cdEndOrg) {
		this.cdEndOrg = cdEndOrg;
	}


}