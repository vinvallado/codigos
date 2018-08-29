package br.mil.fab.consigext.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the T_CAIXA_PAGAMENTO database table.
 * 
 */
@Entity
@Table(name="T_CAIXA_PAGAMENTO", schema="SIG")
@NamedQuery(name="CaixaPagamento.findAll", query="SELECT c FROM CaixaPagamento c")
public class CaixaPagamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CD_CAIXA")
	private String cdCaixa;

	@Column(name="CD_AUTORIZACAO")
	private String cdAutorizacao;

	@Column(name="CD_CAIXA_ACANTUS")
	private String cdCaixaAcantus;

	@Column(name="CD_CONTROLE_IR")
	private String cdControleIr;

	@Column(name="CD_DOTACAO_ORCAMEN")
	private String cdDotacaoOrcamen;

	@Column(name="CD_ELEM_DESPESA")
	private String cdElemDespesa;

	@Column(name="CD_GRUPO_CAIXA")
	private String cdGrupoCaixa;

	@Column(name="CD_PARCELA")
	private String cdParcela;

	@Column(name="CD_REGISTRO_BIEG")
	private String cdRegistroBieg;

	@Column(name="CD_REMUNERACAO")
	private String cdRemuneracao;

	@Column(name="CD_TRIBUTACAO_FONTE")
	private String cdTributacaoFonte;

	@Column(name="DS_CAIXA")
	private String dsCaixa;

	@Column(name="DS_FORMULA")
	private String dsFormula;

	@Column(name="DS_LEG_AMP")
	private String dsLegAmp;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_ALTERACAO")
	private Date dtAlteracao;

	@Column(name="ID_PARCELA_BIEG")
	private String idParcelaBieg;

	@Column(name="NR_MESES_INTERVALO")
	private Long nrMesesIntervalo;

	@Column(name="NR_ORDEM_ALTERA")
	private String nrOrdemAltera;

	@Column(name="NR_PRIORIDADE")
	private Long nrPrioridade;

	@Column(name="SG_ACANTUS")
	private String sgAcantus;

	@Column(name="SG_CAIXA")
	private String sgCaixa;

	@Column(name="SG_CONTRAPARTIDA")
	private String sgContrapartida;

	@Column(name="ST_ADC_NATALINO")
	private String stAdcNatalino;

	@Column(name="ST_APROVACAO_DIRINT")
	private String stAprovacaoDirint;

	@Column(name="ST_AUTORIZACAO_LIMITE")
	private String stAutorizacaoLimite;

	@Column(name="ST_CONFERENCIA_CAIXA")
	private String stConferenciaCaixa;

	@Column(name="ST_CONSIDERA_PRAZO")
	private String stConsideraPrazo;

	@Column(name="ST_DESCONTO_INTERNO")
	private String stDescontoInterno;

	@Column(name="ST_DUPLICADO")
	private String stDuplicado;

	@Column(name="ST_EXTINTO")
	private String stExtinto;

	@Column(name="ST_LANCAMENTO")
	private String stLancamento;

	@Column(name="ST_MRG_CONSIG")
	private String stMrgConsig;

	@Column(name="ST_PENSAO_FS")
	private String stPensaoFs;

	@Column(name="ST_PGM_IMEDIATO")
	private String stPgmImediato;

	@Column(name="ST_PRAZO")
	private String stPrazo;

	@Column(name="ST_PROCESSA_BOLETIM")
	private String stProcessaBoletim;

	@Column(name="ST_REMUNERACAO")
	private String stRemuneracao;

	@Column(name="ST_VERIFICADO")
	private String stVerificado;

	@Column(name="TP_CAIXA")
	private String tpCaixa;

	@Column(name="TP_OPERACAO")
	private String tpOperacao;

	@Column(name="TP_PARCELA_REMUNERATORIA")
	private String tpParcelaRemuneratoria;

	@Column(name="TX_OBS")
	private String txObs;

	@Column(name="VL_LIMITE_DIGITACAO")
	private BigDecimal vlLimiteDigitacao;

	@Column(name="VL_RESSARCI_CUSTOS")
	private BigDecimal vlRessarciCustos;

	//bi-directional many-to-one association to CaixaPagamento
	@ManyToOne
	@JoinColumn(name="CD_CAIXA_ANULA")
	private CaixaPagamento TCaixaPagamento;

	//bi-directional many-to-one association to CaixaPagamento
	@OneToMany(mappedBy="TCaixaPagamento")
	private List<CaixaPagamento> TCaixaPagamentos;

	public CaixaPagamento() {
	}

	public String getCdCaixa() {
		return this.cdCaixa;
	}

	public void setCdCaixa(String cdCaixa) {
		this.cdCaixa = cdCaixa;
	}

	public String getCdAutorizacao() {
		return this.cdAutorizacao;
	}

	public void setCdAutorizacao(String cdAutorizacao) {
		this.cdAutorizacao = cdAutorizacao;
	}

	public String getCdCaixaAcantus() {
		return this.cdCaixaAcantus;
	}

	public void setCdCaixaAcantus(String cdCaixaAcantus) {
		this.cdCaixaAcantus = cdCaixaAcantus;
	}

	public String getCdControleIr() {
		return this.cdControleIr;
	}

	public void setCdControleIr(String cdControleIr) {
		this.cdControleIr = cdControleIr;
	}

	public String getCdDotacaoOrcamen() {
		return this.cdDotacaoOrcamen;
	}

	public void setCdDotacaoOrcamen(String cdDotacaoOrcamen) {
		this.cdDotacaoOrcamen = cdDotacaoOrcamen;
	}

	public String getCdElemDespesa() {
		return this.cdElemDespesa;
	}

	public void setCdElemDespesa(String cdElemDespesa) {
		this.cdElemDespesa = cdElemDespesa;
	}

	public String getCdGrupoCaixa() {
		return this.cdGrupoCaixa;
	}

	public void setCdGrupoCaixa(String cdGrupoCaixa) {
		this.cdGrupoCaixa = cdGrupoCaixa;
	}

	public String getCdParcela() {
		return this.cdParcela;
	}

	public void setCdParcela(String cdParcela) {
		this.cdParcela = cdParcela;
	}

	public String getCdRegistroBieg() {
		return this.cdRegistroBieg;
	}

	public void setCdRegistroBieg(String cdRegistroBieg) {
		this.cdRegistroBieg = cdRegistroBieg;
	}

	public String getCdRemuneracao() {
		return this.cdRemuneracao;
	}

	public void setCdRemuneracao(String cdRemuneracao) {
		this.cdRemuneracao = cdRemuneracao;
	}

	public String getCdTributacaoFonte() {
		return this.cdTributacaoFonte;
	}

	public void setCdTributacaoFonte(String cdTributacaoFonte) {
		this.cdTributacaoFonte = cdTributacaoFonte;
	}

	public String getDsCaixa() {
		return this.dsCaixa;
	}

	public void setDsCaixa(String dsCaixa) {
		this.dsCaixa = dsCaixa;
	}

	public String getDsFormula() {
		return this.dsFormula;
	}

	public void setDsFormula(String dsFormula) {
		this.dsFormula = dsFormula;
	}

	public String getDsLegAmp() {
		return this.dsLegAmp;
	}

	public void setDsLegAmp(String dsLegAmp) {
		this.dsLegAmp = dsLegAmp;
	}

	public Date getDtAlteracao() {
		return this.dtAlteracao;
	}

	public void setDtAlteracao(Date dtAlteracao) {
		this.dtAlteracao = dtAlteracao;
	}

	public String getIdParcelaBieg() {
		return this.idParcelaBieg;
	}

	public void setIdParcelaBieg(String idParcelaBieg) {
		this.idParcelaBieg = idParcelaBieg;
	}

	public Long getNrMesesIntervalo() {
		return this.nrMesesIntervalo;
	}

	public void setNrMesesIntervalo(Long nrMesesIntervalo) {
		this.nrMesesIntervalo = nrMesesIntervalo;
	}

	public String getNrOrdemAltera() {
		return this.nrOrdemAltera;
	}

	public void setNrOrdemAltera(String nrOrdemAltera) {
		this.nrOrdemAltera = nrOrdemAltera;
	}

	public Long getNrPrioridade() {
		return this.nrPrioridade;
	}

	public void setNrPrioridade(Long nrPrioridade) {
		this.nrPrioridade = nrPrioridade;
	}

	public String getSgAcantus() {
		return this.sgAcantus;
	}

	public void setSgAcantus(String sgAcantus) {
		this.sgAcantus = sgAcantus;
	}

	public String getSgCaixa() {
		return this.sgCaixa;
	}

	public void setSgCaixa(String sgCaixa) {
		this.sgCaixa = sgCaixa;
	}

	public String getSgContrapartida() {
		return this.sgContrapartida;
	}

	public void setSgContrapartida(String sgContrapartida) {
		this.sgContrapartida = sgContrapartida;
	}

	public String getStAdcNatalino() {
		return this.stAdcNatalino;
	}

	public void setStAdcNatalino(String stAdcNatalino) {
		this.stAdcNatalino = stAdcNatalino;
	}

	public String getStAprovacaoDirint() {
		return this.stAprovacaoDirint;
	}

	public void setStAprovacaoDirint(String stAprovacaoDirint) {
		this.stAprovacaoDirint = stAprovacaoDirint;
	}

	public String getStAutorizacaoLimite() {
		return this.stAutorizacaoLimite;
	}

	public void setStAutorizacaoLimite(String stAutorizacaoLimite) {
		this.stAutorizacaoLimite = stAutorizacaoLimite;
	}

	public String getStConferenciaCaixa() {
		return this.stConferenciaCaixa;
	}

	public void setStConferenciaCaixa(String stConferenciaCaixa) {
		this.stConferenciaCaixa = stConferenciaCaixa;
	}

	public String getStConsideraPrazo() {
		return this.stConsideraPrazo;
	}

	public void setStConsideraPrazo(String stConsideraPrazo) {
		this.stConsideraPrazo = stConsideraPrazo;
	}

	public String getStDescontoInterno() {
		return this.stDescontoInterno;
	}

	public void setStDescontoInterno(String stDescontoInterno) {
		this.stDescontoInterno = stDescontoInterno;
	}

	public String getStDuplicado() {
		return this.stDuplicado;
	}

	public void setStDuplicado(String stDuplicado) {
		this.stDuplicado = stDuplicado;
	}

	public String getStExtinto() {
		return this.stExtinto;
	}

	public void setStExtinto(String stExtinto) {
		this.stExtinto = stExtinto;
	}

	public String getStLancamento() {
		return this.stLancamento;
	}

	public void setStLancamento(String stLancamento) {
		this.stLancamento = stLancamento;
	}

	public String getStMrgConsig() {
		return this.stMrgConsig;
	}

	public void setStMrgConsig(String stMrgConsig) {
		this.stMrgConsig = stMrgConsig;
	}

	public String getStPensaoFs() {
		return this.stPensaoFs;
	}

	public void setStPensaoFs(String stPensaoFs) {
		this.stPensaoFs = stPensaoFs;
	}

	public String getStPgmImediato() {
		return this.stPgmImediato;
	}

	public void setStPgmImediato(String stPgmImediato) {
		this.stPgmImediato = stPgmImediato;
	}

	public String getStPrazo() {
		return this.stPrazo;
	}

	public void setStPrazo(String stPrazo) {
		this.stPrazo = stPrazo;
	}

	public String getStProcessaBoletim() {
		return this.stProcessaBoletim;
	}

	public void setStProcessaBoletim(String stProcessaBoletim) {
		this.stProcessaBoletim = stProcessaBoletim;
	}

	public String getStRemuneracao() {
		return this.stRemuneracao;
	}

	public void setStRemuneracao(String stRemuneracao) {
		this.stRemuneracao = stRemuneracao;
	}

	public String getStVerificado() {
		return this.stVerificado;
	}

	public void setStVerificado(String stVerificado) {
		this.stVerificado = stVerificado;
	}

	public String getTpCaixa() {
		return this.tpCaixa;
	}

	public void setTpCaixa(String tpCaixa) {
		this.tpCaixa = tpCaixa;
	}

	public String getTpOperacao() {
		return this.tpOperacao;
	}

	public void setTpOperacao(String tpOperacao) {
		this.tpOperacao = tpOperacao;
	}

	public String getTpParcelaRemuneratoria() {
		return this.tpParcelaRemuneratoria;
	}

	public void setTpParcelaRemuneratoria(String tpParcelaRemuneratoria) {
		this.tpParcelaRemuneratoria = tpParcelaRemuneratoria;
	}

	public String getTxObs() {
		return this.txObs;
	}

	public void setTxObs(String txObs) {
		this.txObs = txObs;
	}

	public BigDecimal getVlLimiteDigitacao() {
		return this.vlLimiteDigitacao;
	}

	public void setVlLimiteDigitacao(BigDecimal vlLimiteDigitacao) {
		this.vlLimiteDigitacao = vlLimiteDigitacao;
	}

	public BigDecimal getVlRessarciCustos() {
		return this.vlRessarciCustos;
	}

	public void setVlRessarciCustos(BigDecimal vlRessarciCustos) {
		this.vlRessarciCustos = vlRessarciCustos;
	}

	public CaixaPagamento getTCaixaPagamento() {
		return this.TCaixaPagamento;
	}

	public void setTCaixaPagamento(CaixaPagamento TCaixaPagamento) {
		this.TCaixaPagamento = TCaixaPagamento;
	}

	public List<CaixaPagamento> getTCaixaPagamentos() {
		return this.TCaixaPagamentos;
	}

	public void setTCaixaPagamentos(List<CaixaPagamento> TCaixaPagamentos) {
		this.TCaixaPagamentos = TCaixaPagamentos;
	}

	public CaixaPagamento addTCaixaPagamento(CaixaPagamento TCaixaPagamento) {
		getTCaixaPagamentos().add(TCaixaPagamento);
		TCaixaPagamento.setTCaixaPagamento(this);

		return TCaixaPagamento;
	}

	public CaixaPagamento removeTCaixaPagamento(CaixaPagamento TCaixaPagamento) {
		getTCaixaPagamentos().remove(TCaixaPagamento);
		TCaixaPagamento.setTCaixaPagamento(null);

		return TCaixaPagamento;
	}

}