package br.mil.fab.pessoa.api.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

	@Column(name="CD_GRP_PROC")
	private String cdGrpProc;

	@Column(name="CD_GRUPO_ACANTUS")
	private String cdGrupoAcantus;

	@Column(name="CD_ORG_ACANTUS")
	private String cdOrgAcantus;

	@Column(name="CD_ORG_SISAC")
	private String cdOrgSisac;

	@Column(name="CD_SIAFI")
	private String cdSiafi;

	@Column(name="CD_TP_ORG")
	private String cdTpOrg;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_ANIVERSARIO")
	private Date dtAniversario;

	@Column(name="NM_BRASAO")
	private String nmBrasao;

	@Column(name="NM_EMAIL")
	private String nmEmail;

	@Column(name="NM_HOMEPAGE")
	private String nmHomepage;

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

	@Column(name="ST_ARRANCHADORA")
	private String stArranchadora;

	@Column(name="ST_AUX_ALIMENTACAO")
	private String stAuxAlimentacao;

	@Column(name="ST_EMAIL_CORPORATIVO")
	private String stEmailCorporativo;

	@Column(name="ST_EXTINTA")
	private String stExtinta;

	@Column(name="ST_GDE_COMANDO")
	private String stGdeComando;

	@Column(name="ST_JUNTA")
	private String stJunta;

	@Column(name="ST_PUBLICA_BOLETIM")
	private String stPublicaBoletim;

	@Column(name="ST_REALIZA_AVALIACAO_CPG")
	private String stRealizaAvaliacaoCpg;

	@Column(name="ST_RECADASTRADORA")
	private String stRecadastradora;

	@Column(name="TP_ATIVIDADE")
	private String tpAtividade;

	@Column(name="TX_GRUPO_ORG")
	private String txGrupoOrg;

	//bi-directional many-to-one association to Organizacao
	@ManyToOne
	@JoinColumn(name="CD_ORG_UJ")
	@JsonIgnore
	private Organizacao organizacaoUj;

	//bi-directional many-to-one association to Organizacao
	@OneToMany(mappedBy="organizacaoUj")
	@JsonIgnore
	private List<Organizacao> organizacaoUjs;

	//bi-directional many-to-one association to Organizacao
	@ManyToOne
	@JoinColumn(name="CD_ORG_ARRANCHA")
	@JsonIgnore
	private Organizacao organizacaoArrancha;

	//bi-directional many-to-one association to Organizacao
	@OneToMany(mappedBy="organizacaoArrancha")
	@JsonIgnore
	private List<Organizacao> organizacaoArranchas;

	//bi-directional many-to-one association to Organizacao
	@ManyToOne
	@JoinColumn(name="CD_ORG_COMANDADA")
	@JsonIgnore
	private Organizacao organizacaoComanda;

	//bi-directional many-to-one association to Organizacao
	@OneToMany(mappedBy="organizacaoComanda")
	@JsonIgnore
	private List<Organizacao> organizacaoComandas;

	//bi-directional many-to-one association to Organizacao
	@ManyToOne
	@JoinColumn(name="CD_ORG_SUB_OPER")
	@JsonIgnore
	private Organizacao organizacaoSubOper;

	//bi-directional many-to-one association to Organizacao
	@OneToMany(mappedBy="organizacaoSubOper")
	@JsonIgnore
	private List<Organizacao> organizacaoSubOpers;

	//bi-directional many-to-one association to Organizacao
	@ManyToOne
	@JoinColumn(name="CD_ORG_SACADA")
	@JsonIgnore
	private Organizacao organizacaoSacada;

	//bi-directional many-to-one association to Organizacao
	@OneToMany(mappedBy="organizacaoSacada")
	@JsonIgnore
	private List<Organizacao> organizacaoSacadas;

	//bi-directional many-to-one association to Organizacao
	@ManyToOne
	@JoinColumn(name="CD_ORG_SUB_ADM")
	@JsonIgnore
	private Organizacao organizacaoSubAdm;

	//bi-directional many-to-one association to Organizacao
	@OneToMany(mappedBy="organizacaoSubAdm")
	@JsonIgnore
	private List<Organizacao> organizacaoSubAdms;

	//bi-directional many-to-one association to Organizacao
	@ManyToOne
	@JoinColumn(name="CD_ORG_SUB_BOLETIM")
	@JsonIgnore
	private Organizacao organizacaoSubBoletim;

	//bi-directional many-to-one association to Organizacao
	@OneToMany(mappedBy="organizacaoSubBoletim")
	@JsonIgnore
	private List<Organizacao> organizacaoSubBoletims;

	//bi-directional many-to-one association to Organizacao
	@ManyToOne
	@JoinColumn(name="CD_ORG_COMAR")
	@JsonIgnore
	private Organizacao organizacaoComar;

	//bi-directional many-to-one association to Organizacao
	@OneToMany(mappedBy="organizacaoComar")
	@JsonIgnore
	private List<Organizacao> organizacaoComars;

	public Organizacao() {
	}

	public String getCdOrg() {
		return this.cdOrg;
	}

	public void setCdOrg(String cdOrg) {
		this.cdOrg = cdOrg;
	}

	public String getCdAreaPabx() {
		return this.cdAreaPabx;
	}

	public void setCdAreaPabx(String cdAreaPabx) {
		this.cdAreaPabx = cdAreaPabx;
	}

	public String getCdGrpProc() {
		return this.cdGrpProc;
	}

	public void setCdGrpProc(String cdGrpProc) {
		this.cdGrpProc = cdGrpProc;
	}

	public String getCdGrupoAcantus() {
		return this.cdGrupoAcantus;
	}

	public void setCdGrupoAcantus(String cdGrupoAcantus) {
		this.cdGrupoAcantus = cdGrupoAcantus;
	}

	public String getCdOrgAcantus() {
		return this.cdOrgAcantus;
	}

	public void setCdOrgAcantus(String cdOrgAcantus) {
		this.cdOrgAcantus = cdOrgAcantus;
	}

	public String getCdOrgSisac() {
		return this.cdOrgSisac;
	}

	public void setCdOrgSisac(String cdOrgSisac) {
		this.cdOrgSisac = cdOrgSisac;
	}

	public String getCdSiafi() {
		return this.cdSiafi;
	}

	public void setCdSiafi(String cdSiafi) {
		this.cdSiafi = cdSiafi;
	}

	public String getCdTpOrg() {
		return this.cdTpOrg;
	}

	public void setCdTpOrg(String cdTpOrg) {
		this.cdTpOrg = cdTpOrg;
	}

	public Date getDtAniversario() {
		return this.dtAniversario;
	}

	public void setDtAniversario(Date dtAniversario) {
		this.dtAniversario = dtAniversario;
	}

	public String getNmBrasao() {
		return this.nmBrasao;
	}

	public void setNmBrasao(String nmBrasao) {
		this.nmBrasao = nmBrasao;
	}

	public String getNmEmail() {
		return this.nmEmail;
	}

	public void setNmEmail(String nmEmail) {
		this.nmEmail = nmEmail;
	}

	public String getNmHomepage() {
		return this.nmHomepage;
	}

	public void setNmHomepage(String nmHomepage) {
		this.nmHomepage = nmHomepage;
	}

	public String getNmOrg() {
		return this.nmOrg;
	}

	public void setNmOrg(String nmOrg) {
		this.nmOrg = nmOrg;
	}

	public String getNrCgc() {
		return this.nrCgc;
	}

	public void setNrCgc(String nrCgc) {
		this.nrCgc = nrCgc;
	}

	public String getNrPabxOrg() {
		return this.nrPabxOrg;
	}

	public void setNrPabxOrg(String nrPabxOrg) {
		this.nrPabxOrg = nrPabxOrg;
	}

	public String getNrProtocolo() {
		return this.nrProtocolo;
	}

	public void setNrProtocolo(String nrProtocolo) {
		this.nrProtocolo = nrProtocolo;
	}

	public String getNrRamalPabx() {
		return this.nrRamalPabx;
	}

	public void setNrRamalPabx(String nrRamalPabx) {
		this.nrRamalPabx = nrRamalPabx;
	}

	public String getSgOrg() {
		return this.sgOrg;
	}

	public void setSgOrg(String sgOrg) {
		this.sgOrg = sgOrg;
	}

	public String getSgOrgAcesso() {
		return this.sgOrgAcesso;
	}

	public void setSgOrgAcesso(String sgOrgAcesso) {
		this.sgOrgAcesso = sgOrgAcesso;
	}

	public String getSgOrgContracheque() {
		return this.sgOrgContracheque;
	}

	public void setSgOrgContracheque(String sgOrgContracheque) {
		this.sgOrgContracheque = sgOrgContracheque;
	}

	public String getStArranchadora() {
		return this.stArranchadora;
	}

	public void setStArranchadora(String stArranchadora) {
		this.stArranchadora = stArranchadora;
	}

	public String getStAuxAlimentacao() {
		return this.stAuxAlimentacao;
	}

	public void setStAuxAlimentacao(String stAuxAlimentacao) {
		this.stAuxAlimentacao = stAuxAlimentacao;
	}

	public String getStEmailCorporativo() {
		return this.stEmailCorporativo;
	}

	public void setStEmailCorporativo(String stEmailCorporativo) {
		this.stEmailCorporativo = stEmailCorporativo;
	}

	public String getStExtinta() {
		return this.stExtinta;
	}

	public void setStExtinta(String stExtinta) {
		this.stExtinta = stExtinta;
	}

	public String getStGdeComando() {
		return this.stGdeComando;
	}

	public void setStGdeComando(String stGdeComando) {
		this.stGdeComando = stGdeComando;
	}

	public String getStJunta() {
		return this.stJunta;
	}

	public void setStJunta(String stJunta) {
		this.stJunta = stJunta;
	}

	public String getStPublicaBoletim() {
		return this.stPublicaBoletim;
	}

	public void setStPublicaBoletim(String stPublicaBoletim) {
		this.stPublicaBoletim = stPublicaBoletim;
	}

	public String getStRealizaAvaliacaoCpg() {
		return this.stRealizaAvaliacaoCpg;
	}

	public void setStRealizaAvaliacaoCpg(String stRealizaAvaliacaoCpg) {
		this.stRealizaAvaliacaoCpg = stRealizaAvaliacaoCpg;
	}

	public String getStRecadastradora() {
		return this.stRecadastradora;
	}

	public void setStRecadastradora(String stRecadastradora) {
		this.stRecadastradora = stRecadastradora;
	}

	public String getTpAtividade() {
		return this.tpAtividade;
	}

	public void setTpAtividade(String tpAtividade) {
		this.tpAtividade = tpAtividade;
	}

	public String getTxGrupoOrg() {
		return this.txGrupoOrg;
	}

	public void setTxGrupoOrg(String txGrupoOrg) {
		this.txGrupoOrg = txGrupoOrg;
	}

	public Organizacao getOrganizacaoUj() {
		return organizacaoUj;
	}

	public void setOrganizacaoUj(Organizacao organizacaoUj) {
		this.organizacaoUj = organizacaoUj;
	}

	public List<Organizacao> getOrganizacaoUjs() {
		return organizacaoUjs;
	}

	public void setOrganizacaoUjs(List<Organizacao> organizacaoUjs) {
		this.organizacaoUjs = organizacaoUjs;
	}

	public Organizacao getOrganizacaoArrancha() {
		return organizacaoArrancha;
	}

	public void setOrganizacaoArrancha(Organizacao organizacaoArrancha) {
		this.organizacaoArrancha = organizacaoArrancha;
	}

	public List<Organizacao> getOrganizacaoArranchas() {
		return organizacaoArranchas;
	}

	public void setOrganizacaoArranchas(List<Organizacao> organizacaoArranchas) {
		this.organizacaoArranchas = organizacaoArranchas;
	}

	public Organizacao getOrganizacaoComanda() {
		return organizacaoComanda;
	}

	public void setOrganizacaoComanda(Organizacao organizacaoComanda) {
		this.organizacaoComanda = organizacaoComanda;
	}

	public List<Organizacao> getOrganizacaoComandas() {
		return organizacaoComandas;
	}

	public void setOrganizacaoComandas(List<Organizacao> organizacaoComandas) {
		this.organizacaoComandas = organizacaoComandas;
	}

	public Organizacao getOrganizacaoSubOper() {
		return organizacaoSubOper;
	}

	public void setOrganizacaoSubOper(Organizacao organizacaoSubOper) {
		this.organizacaoSubOper = organizacaoSubOper;
	}

	public List<Organizacao> getOrganizacaoSubOpers() {
		return organizacaoSubOpers;
	}

	public void setOrganizacaoSubOpers(List<Organizacao> organizacaoSubOpers) {
		this.organizacaoSubOpers = organizacaoSubOpers;
	}

	public Organizacao getOrganizacaoSacada() {
		return organizacaoSacada;
	}

	public void setOrganizacaoSacada(Organizacao organizacaoSacada) {
		this.organizacaoSacada = organizacaoSacada;
	}

	public List<Organizacao> getOrganizacaoSacadas() {
		return organizacaoSacadas;
	}

	public void setOrganizacaoSacadas(List<Organizacao> organizacaoSacadas) {
		this.organizacaoSacadas = organizacaoSacadas;
	}

	public Organizacao getOrganizacaoSubAdm() {
		return organizacaoSubAdm;
	}

	public void setOrganizacaoSubAdm(Organizacao organizacaoSubAdm) {
		this.organizacaoSubAdm = organizacaoSubAdm;
	}

	public List<Organizacao> getOrganizacaoSubAdms() {
		return organizacaoSubAdms;
	}

	public void setOrganizacaoSubAdms(List<Organizacao> organizacaoSubAdms) {
		this.organizacaoSubAdms = organizacaoSubAdms;
	}

	public Organizacao getOrganizacaoSubBoletim() {
		return organizacaoSubBoletim;
	}

	public void setOrganizacaoSubBoletim(Organizacao organizacaoSubBoletim) {
		this.organizacaoSubBoletim = organizacaoSubBoletim;
	}

	public List<Organizacao> getOrganizacaoSubBoletims() {
		return organizacaoSubBoletims;
	}

	public void setOrganizacaoSubBoletims(List<Organizacao> organizacaoSubBoletims) {
		this.organizacaoSubBoletims = organizacaoSubBoletims;
	}

	public Organizacao getOrganizacaoComar() {
		return organizacaoComar;
	}

	public void setOrganizacaoComar(Organizacao organizacaoComar) {
		this.organizacaoComar = organizacaoComar;
	}

	public List<Organizacao> getOrganizacaoComars() {
		return organizacaoComars;
	}

	public void setOrganizacaoComars(List<Organizacao> organizacaoComars) {
		this.organizacaoComars = organizacaoComars;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}