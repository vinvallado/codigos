package br.mil.fab.consigext.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name="T_LOCALIDADE", schema="SIG")
public class Localidade  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CD_LOCALD")
	private String cdLocald;

	@Column(name="CD_AREA")
	private String cdArea;

	@Column(name="CD_AREA_PREESCOLAR")
	private String cdAreaPreescolar;

	@Column(name="CD_MUNICIPIO_BIEG")
	private BigDecimal cdMunicipioBieg;

	@Column(name="CD_MUNICIPIO_IBGE")
	private String cdMunicipioIbge;

	@Column(name="CD_PAIS")
	private String cdPais;

	@Column(name="FT_RETRIB_EXT")
	private BigDecimal ftRetribExt;

	@Column(name="NM_LOCALD")
	private String nmLocald;

	@Column(name="NR_COMAR")
	private String nrComar;

	@Column(name="PC_ACRES_DIARIA")
	private BigDecimal pcAcresDiaria;

	@Column(name="PC_LOCALD_ESP")
	private BigDecimal pcLocaldEsp;

	@Column(name="SG_CLASSE")
	private String sgClasse;

	@Column(name="SG_LOCALD")
	private String sgLocald;

	@Column(name="TP_LOCALD")
	private String tpLocald;

	//bi-directional many-to-one association to TEnderecoOrganizacao
	@OneToMany(mappedBy="localidade", cascade = CascadeType.ALL)
	private List<EnderecoOrganizacao> enderecoOrganizacaos;

	//bi-directional many-to-one association to TLocalidade
	@ManyToOne
	@JoinColumn(name="CD_LOCALD_SUBLOCADA")
	private Localidade localidade;

	//bi-directional many-to-one association to TLocalidade
	@OneToMany(mappedBy="localidade")
	private List<Localidade> localidades;

	public Localidade() {
	}

	public String getCdLocald() {
		return this.cdLocald;
	}

	public void setCdLocald(String cdLocald) {
		this.cdLocald = cdLocald;
	}

	public String getCdArea() {
		return this.cdArea;
	}

	public void setCdArea(String cdArea) {
		this.cdArea = cdArea;
	}

	public String getCdAreaPreescolar() {
		return this.cdAreaPreescolar;
	}

	public void setCdAreaPreescolar(String cdAreaPreescolar) {
		this.cdAreaPreescolar = cdAreaPreescolar;
	}

	public BigDecimal getCdMunicipioBieg() {
		return this.cdMunicipioBieg;
	}

	public void setCdMunicipioBieg(BigDecimal cdMunicipioBieg) {
		this.cdMunicipioBieg = cdMunicipioBieg;
	}

	public String getCdMunicipioIbge() {
		return this.cdMunicipioIbge;
	}

	public void setCdMunicipioIbge(String cdMunicipioIbge) {
		this.cdMunicipioIbge = cdMunicipioIbge;
	}

	public String getCdPais() {
		return this.cdPais;
	}

	public void setCdPais(String cdPais) {
		this.cdPais = cdPais;
	}

	public BigDecimal getFtRetribExt() {
		return this.ftRetribExt;
	}

	public void setFtRetribExt(BigDecimal ftRetribExt) {
		this.ftRetribExt = ftRetribExt;
	}

	public String getNmLocald() {
		return this.nmLocald;
	}

	public void setNmLocald(String nmLocald) {
		this.nmLocald = nmLocald;
	}

	public String getNrComar() {
		return this.nrComar;
	}

	public void setNrComar(String nrComar) {
		this.nrComar = nrComar;
	}

	public BigDecimal getPcAcresDiaria() {
		return this.pcAcresDiaria;
	}

	public void setPcAcresDiaria(BigDecimal pcAcresDiaria) {
		this.pcAcresDiaria = pcAcresDiaria;
	}

	public BigDecimal getPcLocaldEsp() {
		return this.pcLocaldEsp;
	}

	public void setPcLocaldEsp(BigDecimal pcLocaldEsp) {
		this.pcLocaldEsp = pcLocaldEsp;
	}

	public String getSgClasse() {
		return this.sgClasse;
	}

	public void setSgClasse(String sgClasse) {
		this.sgClasse = sgClasse;
	}

	public String getSgLocald() {
		return this.sgLocald;
	}

	public void setSgLocald(String sgLocald) {
		this.sgLocald = sgLocald;
	}

	public String getTpLocald() {
		return this.tpLocald;
	}

	public void setTpLocald(String tpLocald) {
		this.tpLocald = tpLocald;
	}

	public List<EnderecoOrganizacao> getEnderecoOrganizacaos() {
		return this.enderecoOrganizacaos;
	}

	public void setEnderecoOrganizacaos(List<EnderecoOrganizacao> enderecoOrganizacaos) {
		this.enderecoOrganizacaos = enderecoOrganizacaos;
	}

	public EnderecoOrganizacao addEnderecoOrganizacao(EnderecoOrganizacao enderecoOrganizacao) {
		getEnderecoOrganizacaos().add(enderecoOrganizacao);
		enderecoOrganizacao.setLocalidade(this);

		return enderecoOrganizacao;
	}

	public EnderecoOrganizacao removeEnderecoOrganizacao(EnderecoOrganizacao enderecoOrganizacao) {
		getEnderecoOrganizacaos().remove(enderecoOrganizacao);
		enderecoOrganizacao.setLocalidade(this);
		
		return enderecoOrganizacao;
	}

	public Localidade getLocalidade() {
		return this.localidade;
	}

	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}

	public List<Localidade> getLocalidades() {
		return this.localidades;
	}

	public void setLocalidades(List<Localidade> localidades) {
		this.localidades = localidades;
	}

	public Localidade addLocalidade(Localidade localidade) {
		getLocalidades().add(localidade);
		
		localidade.setLocalidade (this);

		return localidade;
	}

	public Localidade removeLocalidade(Localidade localidade) {
		getLocalidades().remove(localidade);
		
		localidade.setLocalidade(null);

		return localidade;
	}

}

