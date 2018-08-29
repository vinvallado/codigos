package br.mil.fab.consigext.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import br.mil.fab.consigext.util.CalculoUtil;


@Entity
public class CetConsigRanking {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_CET")
	private Long idCet;
	@Column(name="CD_ANOMES")
	private Long cdAnomes;
	@Column(name="NR_PARCELA")
	private Long nrParcela;
	@Column(name="VL_CET")
	private Double vlCet;
	@Column(name="NM_ORG")
	private String nmOrg;
	@Column(name="ID_ENTIDADE_CONSIG")
	private Long idEntidadeConsig;
	@Column(name="ID_SERVICO")
	private Long idServico;
	@Column(name="ST_ENTIDADE")
	private Long stEntidade;
	
	
	
	@Transient
	private BigDecimal vlLiquidoLib;
	@Transient
	private BigDecimal vlPrestacao;
	@Transient
	private boolean autorizado;
	
	public CetConsigRanking() {
		
	}
	
	public CetConsigRanking(Long idCet) {
		super();
		this.idCet = idCet;
	}


	public CetConsigRanking(Long idCet, Long cdAnomes, Long nrParcela, Double vlCet, String nmOrg,
			Long idEntidadeConsig, Long idServico, long stEntidade) {
		super();
		this.idCet = idCet;
		this.cdAnomes = cdAnomes;
		this.nrParcela = nrParcela;
		this.vlCet = vlCet;
		this.nmOrg = nmOrg;
		this.idEntidadeConsig = idEntidadeConsig;
		this.idServico = idServico;
		this.stEntidade = stEntidade;
	}
	
	public CetConsigRanking(Long idCet, Long cdAnomes, Long nrParcela, Double vlCet, String nmOrg,
			Long idEntidadeConsig, Long idServico, BigDecimal vlLiquidoLib, BigDecimal vlPrestacao,
			boolean autorizado, long stEntidade) {
		super();
		this.idCet = idCet;
		this.cdAnomes = cdAnomes;
		this.nrParcela = nrParcela;
		this.vlCet = vlCet;
		this.nmOrg = nmOrg;
		this.idEntidadeConsig = idEntidadeConsig;
		this.idServico = idServico;
		this.vlLiquidoLib = vlLiquidoLib;
		this.vlPrestacao = vlPrestacao;
		this.autorizado = autorizado;
		this.stEntidade = stEntidade;
	}

	public Long getIdCet() {
		return idCet;
	}

	public void setIdCet(Long idCet) {
		this.idCet = idCet;
	}
	
	public Long getStEntidade() {
		return stEntidade;
	}

	public void setStEntidade(Long stEntidade) {
		this.stEntidade = stEntidade;
	}

	public Long getCdAnomes() {
		return cdAnomes;
	}

	public void setCdAnomes(Long cdAnomes) {
		this.cdAnomes = cdAnomes;
	}

	public Long getNrParcela() {
		return nrParcela;
	}

	public void setNrParcela(Long nrParcela) {
		this.nrParcela = nrParcela;
	}

	public Double getVlCet() {
		return vlCet;
	}

	public void setVlCet(Double vlCet) {
		this.vlCet = vlCet;
	}

	public String getNmOrg() {
		return nmOrg;
	}

	public void setNmOrg(String nmOrg) {
		this.nmOrg = nmOrg;
	}
	
	public BigDecimal getCetAnual() {
		return CalculoUtil.cetMensalToAnual(vlCet.doubleValue());
	}

	public BigDecimal getVlLiquidoLib() {
		return vlLiquidoLib;
	}

	public Long getIdEntidadeConsig() {
		return idEntidadeConsig;
	}

	public void setIdEntidadeConsig(Long idEntidadeConsig) {
		this.idEntidadeConsig = idEntidadeConsig;
	}
	
	public Long getIdServico() {
		return idServico;
	}

	public void setIdServico(Long idServico) {
		this.idServico = idServico;
	}

	public void setVlLiquidoLib(BigDecimal vlLiquidoLib) {
		this.vlLiquidoLib = vlLiquidoLib;
	}

	public BigDecimal getVlPrestacao() {
		return vlPrestacao;
	}

	public void setVlPrestacao(BigDecimal vlPrestacao) {
		this.vlPrestacao = vlPrestacao;
	}

	public boolean isAutorizado() {
		return autorizado;
	}

	public void setAutorizado(boolean autorizado) {
		this.autorizado = autorizado;
	}
	
}
