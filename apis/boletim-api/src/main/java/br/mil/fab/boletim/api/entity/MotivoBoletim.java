package br.mil.fab.boletim.api.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="T_MOTIVO_BOLETIM")
@NamedQuery(name="MotivoBoletim.findAll", query="SELECT m FROM MotivoBoletim m")
public class MotivoBoletim implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CD_MOTIVO")
	private String cdMotivo;

	@Column(name="CD_GRUPO")
	private BigDecimal cdGrupo;

	@Column(name="CD_MOTIVO_SUBSTITUIU")
	private String cdMotivoSubstituiu;

	@Column(name="CD_TIPO_MOTIVO")
	private BigDecimal cdTipoMotivo;

	@Column(name="DS_CONSEQUENCIA")
	private String dsConsequencia;

	@Column(name="DS_LEGISLACAO")
	private String dsLegislacao;

	@Column(name="DS_MOTIVO")
	private String dsMotivo;

	@Column(name="DS_REJEICAO")
	private String dsRejeicao;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INCLUSAO")
	private Date dtInclusao;

	@Column(name="ID_MOTIVO_BOLETIM")
	private BigDecimal idMotivoBoletim;

	@Column(name="NM_FUNCAO_CONF")
	private String nmFuncaoConf;

	@Column(name="NM_FUNCAO_ENT")
	private String nmFuncaoEnt;

	@Column(name="NM_MOTIVO")
	private String nmMotivo;

	@Column(name="NM_QUERY")
	private String nmQuery;

	@Column(name="ST_ATIVIDADE")
	private String stAtividade;

	@Column(name="ST_AUTOMACAO")
	private String stAutomacao;

	@Column(name="ST_HISTORICO_PESSOAL")
	private String stHistoricoPessoal;

	@Column(name="ST_PAGAMENTO")
	private String stPagamento;

	@Column(name="ST_REGRA_COMP_CONS")
	private String stRegraCompCons;

	@Column(name="ST_REGRA_COMPONENTE")
	private String stRegraComponente;

	@Column(name="ST_REGRA_CONSEQUENCIA")
	private String stRegraConsequencia;

	@Column(name="ST_VERIFICADO")
	private String stVerificado;

	@Column(name="TP_MOTIVO")
	private String tpMotivo;

	@Column(name="TP_TRATAMENTO")
	private String tpTratamento;

	@Column(name="TX_ATIVIDADE")
	private String txAtividade;

	@Column(name="TX_CABECALHO_INDIVIDUAL")
	private String txCabecalhoIndividual;

	@Column(name="TX_INDIVIDUAL")
	private String txIndividual;

	@Column(name="TX_PADRAO")
	private String txPadrao;

	@Column(name="TX_PADRAO_ALTERACAO")
	private String txPadraoAlteracao;

	@Column(name="TX_QUALIFICACAO")
	private String txQualificacao;

	@Column(name="TX_RODAPE_BOLETIM")
	private String txRodapeBoletim;

	@Column(name="TX_RODAPE_GERAL")
	private String txRodapeGeral;
	
	@Transient
	private String txRodapeGeralFormatado;
	
	@Transient
	private String txIndividualFormatado;
	
	@Transient
	private String txPadraoFormatado;

	public MotivoBoletim() {
	}

	public String getCdMotivo() {
		return this.cdMotivo;
	}

	public void setCdMotivo(String cdMotivo) {
		this.cdMotivo = cdMotivo;
	}

	public BigDecimal getCdGrupo() {
		return this.cdGrupo;
	}

	public void setCdGrupo(BigDecimal cdGrupo) {
		this.cdGrupo = cdGrupo;
	}

	public String getCdMotivoSubstituiu() {
		return this.cdMotivoSubstituiu;
	}

	public void setCdMotivoSubstituiu(String cdMotivoSubstituiu) {
		this.cdMotivoSubstituiu = cdMotivoSubstituiu;
	}

	public BigDecimal getCdTipoMotivo() {
		return this.cdTipoMotivo;
	}

	public void setCdTipoMotivo(BigDecimal cdTipoMotivo) {
		this.cdTipoMotivo = cdTipoMotivo;
	}

	public String getDsConsequencia() {
		return this.dsConsequencia;
	}

	public void setDsConsequencia(String dsConsequencia) {
		this.dsConsequencia = dsConsequencia;
	}

	public String getDsLegislacao() {
		return this.dsLegislacao;
	}

	public void setDsLegislacao(String dsLegislacao) {
		this.dsLegislacao = dsLegislacao;
	}

	public String getDsMotivo() {
		return this.dsMotivo;
	}

	public void setDsMotivo(String dsMotivo) {
		this.dsMotivo = dsMotivo;
	}

	public String getDsRejeicao() {
		return this.dsRejeicao;
	}

	public void setDsRejeicao(String dsRejeicao) {
		this.dsRejeicao = dsRejeicao;
	}

	public Date getDtInclusao() {
		return this.dtInclusao;
	}

	public void setDtInclusao(Date dtInclusao) {
		this.dtInclusao = dtInclusao;
	}

	public BigDecimal getIdMotivoBoletim() {
		return this.idMotivoBoletim;
	}

	public void setIdMotivoBoletim(BigDecimal idMotivoBoletim) {
		this.idMotivoBoletim = idMotivoBoletim;
	}

	public String getNmFuncaoConf() {
		return this.nmFuncaoConf;
	}

	public void setNmFuncaoConf(String nmFuncaoConf) {
		this.nmFuncaoConf = nmFuncaoConf;
	}

	public String getNmFuncaoEnt() {
		return this.nmFuncaoEnt;
	}

	public void setNmFuncaoEnt(String nmFuncaoEnt) {
		this.nmFuncaoEnt = nmFuncaoEnt;
	}

	public String getNmMotivo() {
		return this.nmMotivo;
	}

	public void setNmMotivo(String nmMotivo) {
		this.nmMotivo = nmMotivo;
	}

	public String getNmQuery() {
		return this.nmQuery;
	}

	public void setNmQuery(String nmQuery) {
		this.nmQuery = nmQuery;
	}

	public String getStAtividade() {
		return this.stAtividade;
	}

	public void setStAtividade(String stAtividade) {
		this.stAtividade = stAtividade;
	}

	public String getStAutomacao() {
		return this.stAutomacao;
	}

	public void setStAutomacao(String stAutomacao) {
		this.stAutomacao = stAutomacao;
	}

	public String getStHistoricoPessoal() {
		return this.stHistoricoPessoal;
	}

	public void setStHistoricoPessoal(String stHistoricoPessoal) {
		this.stHistoricoPessoal = stHistoricoPessoal;
	}

	public String getStPagamento() {
		return this.stPagamento;
	}

	public void setStPagamento(String stPagamento) {
		this.stPagamento = stPagamento;
	}

	public String getStRegraCompCons() {
		return this.stRegraCompCons;
	}

	public void setStRegraCompCons(String stRegraCompCons) {
		this.stRegraCompCons = stRegraCompCons;
	}

	public String getStRegraComponente() {
		return this.stRegraComponente;
	}

	public void setStRegraComponente(String stRegraComponente) {
		this.stRegraComponente = stRegraComponente;
	}

	public String getStRegraConsequencia() {
		return this.stRegraConsequencia;
	}

	public void setStRegraConsequencia(String stRegraConsequencia) {
		this.stRegraConsequencia = stRegraConsequencia;
	}

	public String getStVerificado() {
		return this.stVerificado;
	}

	public void setStVerificado(String stVerificado) {
		this.stVerificado = stVerificado;
	}

	public String getTpMotivo() {
		return this.tpMotivo;
	}

	public void setTpMotivo(String tpMotivo) {
		this.tpMotivo = tpMotivo;
	}

	public String getTpTratamento() {
		return this.tpTratamento;
	}

	public void setTpTratamento(String tpTratamento) {
		this.tpTratamento = tpTratamento;
	}

	public String getTxAtividade() {
		return this.txAtividade;
	}

	public void setTxAtividade(String txAtividade) {
		this.txAtividade = txAtividade;
	}

	public String getTxCabecalhoIndividual() {
		return this.txCabecalhoIndividual;
	}

	public void setTxCabecalhoIndividual(String txCabecalhoIndividual) {
		this.txCabecalhoIndividual = txCabecalhoIndividual;
	}

	public String getTxIndividual() {
		return this.txIndividual;
	}

	public void setTxIndividual(String txIndividual) {
		this.txIndividual = txIndividual;
	}

	public String getTxPadrao() {
		return this.txPadrao;
	}

	public void setTxPadrao(String txPadrao) {
		this.txPadrao = txPadrao;
	}

	public String getTxPadraoAlteracao() {
		return this.txPadraoAlteracao;
	}

	public void setTxPadraoAlteracao(String txPadraoAlteracao) {
		this.txPadraoAlteracao = txPadraoAlteracao;
	}

	public String getTxQualificacao() {
		return this.txQualificacao;
	}

	public void setTxQualificacao(String txQualificacao) {
		this.txQualificacao = txQualificacao;
	}

	public String getTxRodapeBoletim() {
		return this.txRodapeBoletim;
	}

	public void setTxRodapeBoletim(String txRodapeBoletim) {
		this.txRodapeBoletim = txRodapeBoletim;
	}

	public String getTxRodapeGeral() {
		return this.txRodapeGeral;
	}

	public void setTxRodapeGeral(String txRodapeGeral) {
		this.txRodapeGeral = txRodapeGeral;
	}

	public String getTxRodapeGeralFormatado() {
		return txRodapeGeralFormatado;
	}

	public void setTxRodapeGeralFormatado(String txRodapeGeralFormatado) {
		this.txRodapeGeralFormatado = txRodapeGeralFormatado;
	}

	public String getTxIndividualFormatado() {
		return txIndividualFormatado;
	}

	public void setTxIndividualFormatado(String txIndividualFormatado) {
		this.txIndividualFormatado = txIndividualFormatado;
	}

	public String getTxPadraoFormatado() {
		return txPadraoFormatado;
	}

	public void setTxPadraoFormatado(String txPadraoFormatado) {
		this.txPadraoFormatado = txPadraoFormatado;
	}
	
	

}