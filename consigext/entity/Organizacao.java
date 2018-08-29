package br.mil.fab.consigext.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.mil.fab.util.sigpes.entity.OrganizacaoResponse;


/**
 * The persistent class for the T_ORGANIZACAO database table.
 * 
 */
@Entity
@Table(name="T_ORGANIZACAO", schema="SIG")
public class Organizacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CD_ORG")
	private String cdOrg;

	@Column(name="CD_AREA_PABX")
	private String cdAreaPabx;

	@Column(name="CD_GRUPO_ACANTUS")
	private String cdGrupoAcantus;

	@Column(name="CD_ORG_ACANTUS")
	private String cdOrgAcantus;

	@Column(name="CD_TP_ORG")
	private String cdTpOrg;

	@Column(name="NM_EMAIL")
	private String nmEmail;

	@Column(name="NM_ORG")
	private String nmOrg;

	@Column(name="NR_CGC")
	private String nrCgc;

	@Column(name="NR_PABX_ORG")
	private String nrPabxOrg;

	@Column(name="NR_PROTOCOLO")
	private String nrProtocolo;

	@Column(name="NR_RAMAL_PABX")
	private String nrRamalPabx;

	@Column(name="SG_ORG")
	private String sgOrg;

	@Column(name="SG_ORG_ACESSO")
	private String sgOrgAcesso;

	@Column(name="SG_ORG_CONTRACHEQUE")
	private String sgOrgContracheque;

	@Column(name="ST_EMAIL_CORPORATIVO")
	private String stEmailCorporativo;

	@Column(name="ST_EXTINTA")
	private String stExtinta;

	@Column(name="ST_GDE_COMANDO")
	private String stGdeComando;

	@Column(name="ST_PUBLICA_BOLETIM")
	private String stPublicaBoletim;

	@Column(name="TP_ATIVIDADE")
	private String tpAtividade;

	@Column(name="TX_GRUPO_ORG")
	private String txGrupoOrg;

	//bi-directional many-to-one association to Organizacao
	@ManyToOne
	@JoinColumn(name="CD_ORG_SACADA")
	@JsonIgnore
	private Organizacao sacada;


	@Transient
	OrganizacaoResponse organizacaoResponse;
	


	public Organizacao() {
	}
	
	public Organizacao( OrganizacaoResponse organizacaoResponse) {
		this.organizacaoResponse = organizacaoResponse;
		this.cdOrg = organizacaoResponse.getCdOrg();
	}


	public String getCdOrg() {
		return cdOrg;
	}

	public void setCdOrg(String cdOrg) {
		this.cdOrg = cdOrg;
	}

	public String getCdAreaPabx() {
		return cdAreaPabx;
	}

	public void setCdAreaPabx(String cdAreaPabx) {
		this.cdAreaPabx = cdAreaPabx;
	}

	public String getCdGrupoAcantus() {
		return cdGrupoAcantus;
	}

	public void setCdGrupoAcantus(String cdGrupoAcantus) {
		this.cdGrupoAcantus = cdGrupoAcantus;
	}

	public String getCdOrgAcantus() {
		return cdOrgAcantus;
	}

	public void setCdOrgAcantus(String cdOrgAcantus) {
		this.cdOrgAcantus = cdOrgAcantus;
	}

	public String getCdTpOrg() {
		return cdTpOrg;
	}
	
	public void setCdTpOrg(String cdTpOrg) {
		this.cdTpOrg = cdTpOrg;
	}

	public String getNmEmail() {
		return nmEmail;
	}
	
	public void setNmEmail(String nmEmail) {
		this.nmEmail = nmEmail;
	}

	public String getNmOrg() {
		return nmOrg;
	}

	public void setNmOrg(String nmOrg) {
		this.nmOrg = nmOrg;
	}

	public String getNrCgc() {
		return nrCgc;
	}
	
	public void setNrCgc(String nrCgc) {
		this.nrCgc = nrCgc;
	}

	public String getNrPabxOrg() {
		return nrPabxOrg;
	}

	public void setNrPabxOrg(String nrPabxOrg) {
		this.nrPabxOrg = nrPabxOrg;
	}

	public String getNrProtocolo() {
		return nrProtocolo;
	}

	public void setNrProtocolo(String nrProtocolo) {
		this.nrProtocolo = nrProtocolo;
	}
	
	public String getNrRamalPabx() {
		return nrRamalPabx;
	}

	public void setNrRamalPabx(String nrRamalPabx) {
		this.nrRamalPabx = nrRamalPabx;
	}

	public String getSgOrg() {
		return sgOrg;
	}

	public void setSgOrg(String sgOrg) {
		this.sgOrg = sgOrg;
	}

	public String getSgOrgAcesso() {
		return sgOrgAcesso;
	}

	public void setSgOrgAcesso(String sgOrgAcesso) {
		this.sgOrgAcesso = sgOrgAcesso;
	}

	public String getSgOrgContracheque() {
		return sgOrgContracheque;
	}

	public void setSgOrgContracheque(String sgOrgContracheque) {
		this.sgOrgContracheque = sgOrgContracheque;
	}

	public String getStEmailCorporativo() {
		return stEmailCorporativo;
	}

	public void setStEmailCorporativo(String stEmailCorporativo) {
		this.stEmailCorporativo = stEmailCorporativo;
	}

	public String getStExtinta() {
		return stExtinta;
	}

	public void setStExtinta(String stExtinta) {
		this.stExtinta = stExtinta;
	}

	public String getStGdeComando() {
		return stGdeComando;
	}

	public void setStGdeComando(String stGdeComando) {
		this.stGdeComando = stGdeComando;
	}

	public String getStPublicaBoletim() {
		return stPublicaBoletim;
	}

	public void setStPublicaBoletim(String stPublicaBoletim) {
		this.stPublicaBoletim = stPublicaBoletim;
	}

	public String getTpAtividade() {
		return tpAtividade;
	}

	public void setTpAtividade(String tpAtividade) {
		this.tpAtividade = tpAtividade;
	}

	public String getTxGrupoOrg() {
		return txGrupoOrg;
	}

	public void setTxGrupoOrg(String txGrupoOrg) {
		this.txGrupoOrg = txGrupoOrg;
	}

	public Organizacao getSacada() {
		return sacada;
	}

	public void setSacada(Organizacao sacada) {
		this.sacada = sacada;
	}



	public OrganizacaoResponse getOrganizacaoResponse() {
		return organizacaoResponse;
	}

	public void setOrganizacaoResponse(OrganizacaoResponse organizacaoResponse) {
		this.organizacaoResponse = organizacaoResponse;
	}
	

}