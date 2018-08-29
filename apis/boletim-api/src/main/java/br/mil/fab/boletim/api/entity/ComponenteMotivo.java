package br.mil.fab.boletim.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the T_COMPONENTE_MOTIVO database table.
 * 
 */
@Entity
@Table(name="T_COMPONENTE_MOTIVO")
@NamedQuery(name="ComponenteMotivo.findAll", query="SELECT c FROM ComponenteMotivo c")
public class ComponenteMotivo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COMPONENTE_MOTIVO")
	private Long idComponenteMotivo;

	@Column(name="CD_MOTIVO")
	private String cdMotivo;

	@Column(name="ID_COMPONENTE")
	private BigDecimal idComponente;

	@Column(name="NM_BANCO_DADOS")
	private String nmBancoDados;

	@Column(name="NM_CAMPO")
	private String nmCampo;

	@Column(name="NM_FUNCAO_VAL")
	private String nmFuncaoVal;

	@Column(name="NM_ROTULO")
	private String nmRotulo;

	@Column(name="NM_TABELA")
	private String nmTabela;

	@Column(name="NR_SEQUENCIA")
	private BigDecimal nrSequencia;

	@Column(name="ST_NIVEL")
	private String stNivel;

	@Column(name="ST_PREENCHIMENTO")
	private String stPreenchimento;

	@Column(name="TP_DADO")
	private String tpDado;

	public ComponenteMotivo() {
	}

	public long getIdComponenteMotivo() {
		return this.idComponenteMotivo;
	}

	public void setIdComponenteMotivo(long idComponenteMotivo) {
		this.idComponenteMotivo = idComponenteMotivo;
	}

	public String getCdMotivo() {
		return this.cdMotivo;
	}

	public void setCdMotivo(String cdMotivo) {
		this.cdMotivo = cdMotivo;
	}

	public BigDecimal getIdComponente() {
		return this.idComponente;
	}

	public void setIdComponente(BigDecimal idComponente) {
		this.idComponente = idComponente;
	}

	public String getNmBancoDados() {
		return this.nmBancoDados;
	}

	public void setNmBancoDados(String nmBancoDados) {
		this.nmBancoDados = nmBancoDados;
	}

	public String getNmCampo() {
		return this.nmCampo;
	}

	public void setNmCampo(String nmCampo) {
		this.nmCampo = nmCampo;
	}

	public String getNmFuncaoVal() {
		return this.nmFuncaoVal;
	}

	public void setNmFuncaoVal(String nmFuncaoVal) {
		this.nmFuncaoVal = nmFuncaoVal;
	}

	public String getNmRotulo() {
		return this.nmRotulo;
	}

	public void setNmRotulo(String nmRotulo) {
		this.nmRotulo = nmRotulo;
	}

	public String getNmTabela() {
		return this.nmTabela;
	}

	public void setNmTabela(String nmTabela) {
		this.nmTabela = nmTabela;
	}

	public BigDecimal getNrSequencia() {
		return this.nrSequencia;
	}

	public void setNrSequencia(BigDecimal nrSequencia) {
		this.nrSequencia = nrSequencia;
	}

	public String getStNivel() {
		return this.stNivel;
	}

	public void setStNivel(String stNivel) {
		this.stNivel = stNivel;
	}

	public String getStPreenchimento() {
		return this.stPreenchimento;
	}

	public void setStPreenchimento(String stPreenchimento) {
		this.stPreenchimento = stPreenchimento;
	}

	public String getTpDado() {
		return this.tpDado;
	}

	public void setTpDado(String tpDado) {
		this.tpDado = tpDado;
	}

}