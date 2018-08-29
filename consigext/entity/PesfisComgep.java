package br.mil.fab.consigext.entity;

import java.io.Serializable;
import javax.persistence.*;

import br.mil.fab.consigext.enums.EstadoCivil;
import br.mil.fab.consigext.util.GenericUtil;
import br.mil.fab.util.sigpes.entity.PesfisComgepResponse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(schema = "SIG", name="T_PESFIS_COMGEP_DW")
public class PesfisComgep implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NR_ORDEM")
	private String nrOrdem;
	
	@Column(name="CD_AGENCIA")
	private String cdAgencia;

	@Column(name="CD_BANCO")
	private String cdBanco;

	@Column(name="CD_HRQ")
	private String cdHrq;

	@Column(name="CD_LOCALD")
	private String cdLocald;

	@Column(name="CD_LOCALD_UF_TIT_ELEITOR")
	private String cdLocaldUfTitEleitor;
	
	@ManyToOne
	@JoinColumn(name="CD_ORG")
	private Organizacao organizacao;

	@Column(name="CD_ORG_ADM")
	private String cdOrgAdm;

	@Column(name="CD_ORG_SVC")
	private String cdOrgSvc;

	@Column(name="CD_ORG_VINC")
	private String cdOrgVinc;

	@Column(name="CD_PAIS")
	private String cdPais;

	@Column(name="CD_QDR")
	private String cdQdr;

	@Column(name="CD_RELIGIAO")
	private String cdReligiao;

	@Column(name="CD_SIT_ESP")
	private String cdSitEsp;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_APRES_ATUAL")
	private Date dtApresAtual;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_APRES_LOC")
	private Date dtApresLoc;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_CONC_FRM")
	private Date dtConcFrm;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FALECIMENTO")
	private Date dtFalecimento;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_MOV_ATUAL")
	private Date dtMovAtual;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_NASC")
	private Date dtNasc;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_PRACA")
	private Date dtPraca;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_PROMOCAO_ATUAL")
	private Date dtPromocaoAtual;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_ULT_APRES")
	private Date dtUltApres;
	
	@Column(name="EST_CIVIL")
	@Enumerated(EnumType.STRING)
	private EstadoCivil estCivil;

	@Column(name="NM_CONJUGE")
	private String nmConjuge;

	@Column(name="NM_GUERRA")
	private String nmGuerra;

	@Column(name="NM_LOCALD_EST")
	private String nmLocaldEst;

	@Column(name="NM_LOCALD_MUN")
	private String nmLocaldMun;

	@Column(name="NM_MAE")
	private String nmMae;

	@Column(name="NM_PAI")
	private String nmPai;

	@Column(name="NM_PESSOA")
	private String nmPessoa;

	@Column(name="NR_ANTIG")
	private String nrAntig;

	@Column(name="NR_CPF")
	private String nrCpf;

	@Column(name="NR_CTA_CORR")
	private String nrCtaCorr;

	@Column(name="NR_IDENT_AER")
	private String nrIdentAer;

	@Column(name="NR_ORDEM_E_INSTITUIDO_PENSAO")
	private String nrOrdemEInstituidoPensao;

	@Column(name="NR_ORDEM_TEM_RELACIONAMENTO")
	private String nrOrdemTemRelacionamento;

	@Column(name="NR_PARTE")
	private BigDecimal nrParte;

	@Column(name="NR_RC")
	private String nrRc;

	@Column(name="NR_SECAO")
	private String nrSecao;

	@Column(name="NR_TIT_ELEITOR")
	private String nrTitEleitor;

	@Column(name="NR_ZONA")
	private String nrZona;

	@Column(name="PESFIS_TYPE")
	private String pesfisType;

	@Column(name="SG_COMAR")
	private String sgComar;

	@Column(name="SG_CUTIS")
	private String sgCutis;

	@Column(name="SG_ESPD")
	private String sgEspd;

	@Column(name="SG_FNCO_LOCAL")
	private String sgFncoLocal;

	@Column(name="SG_ORG")
	private String sgOrg;

	@Column(name="SG_ORG_ADICAO")
	private String sgOrgAdicao;

	@Column(name="SG_ORG_ADM")
	private String sgOrgAdm;

	@Column(name="SG_ORG_ATUAL_PREV")
	private String sgOrgAtualPrev;

	@Column(name="SG_ORG_SVC")
	private String sgOrgSvc;

	@Column(name="SG_ORG_VINC")
	private String sgOrgVinc;

	@Column(name="SG_POSTO")
	private String sgPosto;

	@Column(name="SG_QDR")
	private String sgQdr;

	@Column(name="SG_SBP")
	private String sgSbp;

	@Column(name="SG_SEXO")
	private String sgSexo;

	@Column(name="SG_SIT_ORG")
	private String sgSitOrg;

	@Column(name="SG_SIT_QDR")
	private String sgSitQdr;

	@Column(name="ST_CARTAO_SARAM")
	private String stCartaoSaram;

	@Column(name="ST_DOADOR")
	private String stDoador;

	@Column(name="ST_ESPECIAL")
	private String stEspecial;

	@Column(name="ST_MOV")
	private String stMov;

	@Column(name="TP_CONT_FUNSA")
	private String tpContFunsa;

	@Column(name="TP_ESCOL")
	private String tpEscol;

	@Column(name="TP_SANGUE_RH")
	private String tpSangueRh;

	@Column(name="TX_TEMPO_SERVICO")
	private String txTempoServico;
	
	@Transient
	PesfisComgepResponse pesfisResponse;

	public PesfisComgep() {
	}
	
	public PesfisComgep(String nrOrdem) {
		this.nrOrdem = nrOrdem;
	}

	
	public PesfisComgep(PesfisComgepResponse pesfisResponse) {
		this.nrOrdem = pesfisResponse.getNrOrdem();
		this.pesfisResponse = pesfisResponse;
	}

	public String getCdAgencia() {
		return this.cdAgencia;
	}

	public void setCdAgencia(String cdAgencia) {
		this.cdAgencia = cdAgencia;
	}

	public String getCdBanco() {
		return this.cdBanco;
	}

	public void setCdBanco(String cdBanco) {
		this.cdBanco = cdBanco;
	}

	public String getCdHrq() {
		return this.cdHrq;
	}

	public void setCdHrq(String cdHrq) {
		this.cdHrq = cdHrq;
	}

	public String getCdLocald() {
		return this.cdLocald;
	}

	public void setCdLocald(String cdLocald) {
		this.cdLocald = cdLocald;
	}

	public String getCdLocaldUfTitEleitor() {
		return this.cdLocaldUfTitEleitor;
	}

	public void setCdLocaldUfTitEleitor(String cdLocaldUfTitEleitor) {
		this.cdLocaldUfTitEleitor = cdLocaldUfTitEleitor;
	}

	/*public String getCdOrg() {
		return this.cdOrg;
	}

	public void setCdOrg(String cdOrg) {
		this.cdOrg = cdOrg;
	}*/

	public String getCdOrgAdm() {
		return this.cdOrgAdm;
	}

	public void setCdOrgAdm(String cdOrgAdm) {
		this.cdOrgAdm = cdOrgAdm;
	}

	public String getCdOrgSvc() {
		return this.cdOrgSvc;
	}

	public void setCdOrgSvc(String cdOrgSvc) {
		this.cdOrgSvc = cdOrgSvc;
	}

	public String getCdOrgVinc() {
		return this.cdOrgVinc;
	}

	public void setCdOrgVinc(String cdOrgVinc) {
		this.cdOrgVinc = cdOrgVinc;
	}

	public String getCdPais() {
		return this.cdPais;
	}

	public void setCdPais(String cdPais) {
		this.cdPais = cdPais;
	}

	public String getCdQdr() {
		return this.cdQdr;
	}

	public void setCdQdr(String cdQdr) {
		this.cdQdr = cdQdr;
	}

	public String getCdReligiao() {
		return this.cdReligiao;
	}

	public void setCdReligiao(String cdReligiao) {
		this.cdReligiao = cdReligiao;
	}

	public String getCdSitEsp() {
		return this.cdSitEsp;
	}

	public void setCdSitEsp(String cdSitEsp) {
		this.cdSitEsp = cdSitEsp;
	}

	public Date getDtApresAtual() {
		return this.dtApresAtual;
	}

	public void setDtApresAtual(Date dtApresAtual) {
		this.dtApresAtual = dtApresAtual;
	}

	public Date getDtApresLoc() {
		return this.dtApresLoc;
	}

	public void setDtApresLoc(Date dtApresLoc) {
		this.dtApresLoc = dtApresLoc;
	}

	public Date getDtConcFrm() {
		return this.dtConcFrm;
	}

	public void setDtConcFrm(Date dtConcFrm) {
		this.dtConcFrm = dtConcFrm;
	}

	public Date getDtFalecimento() {
		return this.dtFalecimento;
	}

	public void setDtFalecimento(Date dtFalecimento) {
		this.dtFalecimento = dtFalecimento;
	}

	public Date getDtMovAtual() {
		return this.dtMovAtual;
	}

	public void setDtMovAtual(Date dtMovAtual) {
		this.dtMovAtual = dtMovAtual;
	}

	public Date getDtNasc() {
		return this.dtNasc;
	}

	public void setDtNasc(Date dtNasc) {
		this.dtNasc = dtNasc;
	}

	public Date getDtPraca() {
		return this.dtPraca;
	}

	public void setDtPraca(Date dtPraca) {
		this.dtPraca = dtPraca;
	}

	public Date getDtPromocaoAtual() {
		return this.dtPromocaoAtual;
	}

	public void setDtPromocaoAtual(Date dtPromocaoAtual) {
		this.dtPromocaoAtual = dtPromocaoAtual;
	}

	public Date getDtUltApres() {
		return this.dtUltApres;
	}

	public void setDtUltApres(Date dtUltApres) {
		this.dtUltApres = dtUltApres;
	}

	public String getEstCivil() {
		return this.estCivil.getValor();
	}

	public void setEstCivil(EstadoCivil estCivil) {
		this.estCivil = estCivil;
	}

	public String getNmConjuge() {
		return this.nmConjuge;
	}

	public void setNmConjuge(String nmConjuge) {
		this.nmConjuge = nmConjuge;
	}

	public String getNmGuerra() {
		return this.nmGuerra;
	}

	public void setNmGuerra(String nmGuerra) {
		this.nmGuerra = nmGuerra;
	}

	public String getNmLocaldEst() {
		return this.nmLocaldEst;
	}

	public void setNmLocaldEst(String nmLocaldEst) {
		this.nmLocaldEst = nmLocaldEst;
	}

	public String getNmLocaldMun() {
		return this.nmLocaldMun;
	}

	public void setNmLocaldMun(String nmLocaldMun) {
		this.nmLocaldMun = nmLocaldMun;
	}

	public String getNmMae() {
		return this.nmMae;
	}

	public void setNmMae(String nmMae) {
		this.nmMae = nmMae;
	}

	public String getNmPai() {
		return this.nmPai;
	}

	public void setNmPai(String nmPai) {
		this.nmPai = nmPai;
	}

	public String getNmPessoa() {
		return this.nmPessoa;
	}

	public void setNmPessoa(String nmPessoa) {
		this.nmPessoa = nmPessoa;
	}

	public String getNrAntig() {
		return this.nrAntig;
	}

	public void setNrAntig(String nrAntig) {
		this.nrAntig = nrAntig;
	}

	public String getNrCpf() {
		return this.nrCpf;
	}

	public void setNrCpf(String nrCpf) {
		this.nrCpf = nrCpf;
	}

	public String getNrCtaCorr() {
		return this.nrCtaCorr;
	}

	public void setNrCtaCorr(String nrCtaCorr) {
		this.nrCtaCorr = nrCtaCorr;
	}

	public String getNrIdentAer() {
		return this.nrIdentAer;
	}

	public void setNrIdentAer(String nrIdentAer) {
		this.nrIdentAer = nrIdentAer;
	}

	public String getNrOrdem() {
		return this.nrOrdem;
	}

	public void setNrOrdem(String nrOrdem) {
		this.nrOrdem = nrOrdem;
	}

	public String getNrOrdemEInstituidoPensao() {
		return this.nrOrdemEInstituidoPensao;
	}

	public void setNrOrdemEInstituidoPensao(String nrOrdemEInstituidoPensao) {
		this.nrOrdemEInstituidoPensao = nrOrdemEInstituidoPensao;
	}

	public String getNrOrdemTemRelacionamento() {
		return this.nrOrdemTemRelacionamento;
	}

	public void setNrOrdemTemRelacionamento(String nrOrdemTemRelacionamento) {
		this.nrOrdemTemRelacionamento = nrOrdemTemRelacionamento;
	}

	public BigDecimal getNrParte() {
		return this.nrParte;
	}

	public void setNrParte(BigDecimal nrParte) {
		this.nrParte = nrParte;
	}

	public String getNrRc() {
		return this.nrRc;
	}

	public void setNrRc(String nrRc) {
		this.nrRc = nrRc;
	}

	public String getNrSecao() {
		return this.nrSecao;
	}

	public void setNrSecao(String nrSecao) {
		this.nrSecao = nrSecao;
	}

	public String getNrTitEleitor() {
		return this.nrTitEleitor;
	}

	public void setNrTitEleitor(String nrTitEleitor) {
		this.nrTitEleitor = nrTitEleitor;
	}

	public String getNrZona() {
		return this.nrZona;
	}

	public void setNrZona(String nrZona) {
		this.nrZona = nrZona;
	}

	public String getPesfisType() {
		return this.pesfisType;
	}

	public void setPesfisType(String pesfisType) {
		this.pesfisType = pesfisType;
	}

	public String getSgComar() {
		return this.sgComar;
	}

	public void setSgComar(String sgComar) {
		this.sgComar = sgComar;
	}

	public String getSgCutis() {
		return this.sgCutis;
	}

	public void setSgCutis(String sgCutis) {
		this.sgCutis = sgCutis;
	}

	public String getSgEspd() {
		return this.sgEspd;
	}

	public void setSgEspd(String sgEspd) {
		this.sgEspd = sgEspd;
	}

	public String getSgFncoLocal() {
		return this.sgFncoLocal;
	}

	public void setSgFncoLocal(String sgFncoLocal) {
		this.sgFncoLocal = sgFncoLocal;
	}

	public String getSgOrg() {
		return this.sgOrg;
	}

	public void setSgOrg(String sgOrg) {
		this.sgOrg = sgOrg;
	}

	public String getSgOrgAdicao() {
		return this.sgOrgAdicao;
	}

	public void setSgOrgAdicao(String sgOrgAdicao) {
		this.sgOrgAdicao = sgOrgAdicao;
	}

	public String getSgOrgAdm() {
		return this.sgOrgAdm;
	}

	public void setSgOrgAdm(String sgOrgAdm) {
		this.sgOrgAdm = sgOrgAdm;
	}

	public String getSgOrgAtualPrev() {
		return this.sgOrgAtualPrev;
	}

	public void setSgOrgAtualPrev(String sgOrgAtualPrev) {
		this.sgOrgAtualPrev = sgOrgAtualPrev;
	}

	public String getSgOrgSvc() {
		return this.sgOrgSvc;
	}

	public void setSgOrgSvc(String sgOrgSvc) {
		this.sgOrgSvc = sgOrgSvc;
	}

	public String getSgOrgVinc() {
		return this.sgOrgVinc;
	}

	public void setSgOrgVinc(String sgOrgVinc) {
		this.sgOrgVinc = sgOrgVinc;
	}

	public String getSgPosto() {
		return this.sgPosto;
	}

	public void setSgPosto(String sgPosto) {
		this.sgPosto = sgPosto;
	}

	public String getSgQdr() {
		return this.sgQdr;
	}

	public void setSgQdr(String sgQdr) {
		this.sgQdr = sgQdr;
	}

	public String getSgSbp() {
		return this.sgSbp;
	}

	public void setSgSbp(String sgSbp) {
		this.sgSbp = sgSbp;
	}

	public String getSgSexo() {
		return this.sgSexo;
	}

	public void setSgSexo(String sgSexo) {
		this.sgSexo = sgSexo;
	}

	public String getSgSitOrg() {
		return this.sgSitOrg;
	}

	public void setSgSitOrg(String sgSitOrg) {
		this.sgSitOrg = sgSitOrg;
	}

	public String getSgSitQdr() {
		return this.sgSitQdr;
	}

	public void setSgSitQdr(String sgSitQdr) {
		this.sgSitQdr = sgSitQdr;
	}

	public String getStCartaoSaram() {
		return this.stCartaoSaram;
	}

	public void setStCartaoSaram(String stCartaoSaram) {
		this.stCartaoSaram = stCartaoSaram;
	}

	public String getStDoador() {
		return this.stDoador;
	}

	public void setStDoador(String stDoador) {
		this.stDoador = stDoador;
	}

	public String getStEspecial() {
		return this.stEspecial;
	}

	public void setStEspecial(String stEspecial) {
		this.stEspecial = stEspecial;
	}

	public String getStMov() {
		return this.stMov;
	}

	public void setStMov(String stMov) {
		this.stMov = stMov;
	}

	public String getTpContFunsa() {
		return this.tpContFunsa;
	}

	public void setTpContFunsa(String tpContFunsa) {
		this.tpContFunsa = tpContFunsa;
	}

	public String getTpEscol() {
		return this.tpEscol;
	}

	public void setTpEscol(String tpEscol) {
		this.tpEscol = tpEscol;
	}

	public String getTpSangueRh() {
		return this.tpSangueRh;
	}

	public void setTpSangueRh(String tpSangueRh) {
		this.tpSangueRh = tpSangueRh;
	}

	public String getTxTempoServico() {
		return this.txTempoServico;
	}

	public void setTxTempoServico(String txTempoServico) {
		this.txTempoServico = txTempoServico;
	}

	public Organizacao getOrganizacao() {
		return organizacao;
	}

	public void setOrganizacao(Organizacao organizacao) {
		this.organizacao = organizacao;
	}

	public PesfisComgepResponse getPesfisResponse() {
		return pesfisResponse;
	}

	public void setPesfisResponse(PesfisComgepResponse pesfisResponse) {
		this.pesfisResponse = pesfisResponse;
	}
	public boolean isContaServidorRecebe(String bancoTeste, String agenciaTeste, String contaCorrenteTeste) {
		if(bancoTeste == null || agenciaTeste == null || contaCorrenteTeste == null)
			return false;
		if(GenericUtil.removeLeftZeros(bancoTeste).equals(GenericUtil.removeLeftZeros(cdBanco))==false)
			return false;
		if(GenericUtil.removeLeftZeros(agenciaTeste).equals(GenericUtil.removeLeftZeros(cdAgencia))==false)
			return false;
		if(GenericUtil.removeLeftZeros(contaCorrenteTeste).equals(GenericUtil.removeLeftZeros(nrCtaCorr))==false)
			return false;
		return true;
	}	

}