package br.ccasj.sisauc.pesquisa.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ccasj.sisauc.emissaoar.domain.EstadoAR;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;

public class ParametrosPesquisaAR {

	private List<OrganizacaoMilitar> organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();
	private String numeroAR;
	private String nomeBeneficiario;
	private String saram;
	private String procedimento;
	private Date inicioDataEmissao;
	private Date fimDataEmissao;
	private Date inicioDataApresentacao;
	private Date fimDataApresentacao;
	private Date inicioDataNotaFiscal;
	private Date fimDataNotaFiscal;
	private String cpfCnpjNotaFiscal;
	
	private Double valorNotaFiscal;
	private Double valorReembolso;
	
	//private OpcoesPesquisaBoolean naoEletiva = OpcoesPesquisaBoolean.TODOS;

	private OpcoesPesquisaBoolean titular = OpcoesPesquisaBoolean.TODOS;
	private List<EstadoAR> estadosAR = new ArrayList<EstadoAR>();
	private List<Divisao> divisoes = new ArrayList<Divisao>();
	
	
	public List<OrganizacaoMilitar> getOrganizacoesMilitares() {
		return organizacoesMilitares;
	}
	
	public void setOrganizacoesMilitares(List<OrganizacaoMilitar> organizacoesMilitares) {
		this.organizacoesMilitares = organizacoesMilitares;
	}
	
	public String getNumeroAR() {
		return numeroAR;
	}
	
	public void setNumeroAR(String numeroAR) {
		this.numeroAR = numeroAR;
	}
	
	public String getNomeBeneficiario() {
		return nomeBeneficiario;
	}
	
	public void setNomeBeneficiario(String nomeBeneficiario) {
		this.nomeBeneficiario = nomeBeneficiario;
	}
	
	public String getSaram() {
		return saram;
	}
	
	public void setSaram(String saram) {
		this.saram = saram;
	}
	
	public String getProcedimento() {
		return procedimento;
	}
	
	public void setProcedimento(String procedimento) {
		this.procedimento = procedimento;
	}
	
	public Date getInicioDataEmissao() {
		return inicioDataEmissao;
	}
	
	public void setInicioDataEmissao(Date inicioDataEmissao) {
		this.inicioDataEmissao = inicioDataEmissao;
	}
	
	public Date getFimDataEmissao() {
		return fimDataEmissao;
	}
	
	public void setFimDataEmissao(Date fimDataEmissao) {
		this.fimDataEmissao = fimDataEmissao;
	}
	
	public Date getInicioDataApresentacao() {
		return inicioDataApresentacao;
	}
	
	public void setInicioDataApresentacao(Date inicioDataApresentacao) {
		this.inicioDataApresentacao = inicioDataApresentacao;
	}
	
	public Date getFimDataApresentacao() {
		return fimDataApresentacao;
	}
	
	public void setFimDataApresentacao(Date fimDataApresentacao) {
		this.fimDataApresentacao = fimDataApresentacao;
	}
	
	public Date getInicioDataNotaFiscal() {
		return inicioDataNotaFiscal;
	}
	
	public void setInicioDataNotaFiscal(Date inicioDataNotaFiscal) {
		this.inicioDataNotaFiscal = inicioDataNotaFiscal;
	}
	
	public Date getFimDataNotaFiscal() {
		return fimDataNotaFiscal;
	}
	
	public void setFimDataNotaFiscal(Date fimDataNotaFiscal) {
		this.fimDataNotaFiscal = fimDataNotaFiscal;
	}

	public String getCpfCnpjNotaFiscal() {
		return cpfCnpjNotaFiscal;
	}

	public void setCpfCnpjNotaFiscal(String cpfCnpjNotaFiscal) {
		this.cpfCnpjNotaFiscal = cpfCnpjNotaFiscal;
	}

	public Double getValorNotaFiscal() {
		return valorNotaFiscal;
	}
	
	public void setValorNotaFiscal(Double valorNotaFiscal) {
		this.valorNotaFiscal = valorNotaFiscal;
	}
	
	public Double getValorReembolso() {
		return valorReembolso;
	}
	
	public void setValorReembolso(Double valorReembolso) {
		this.valorReembolso = valorReembolso;
	}
	
	public OpcoesPesquisaBoolean getTitular() {
		return titular;
	}
	
	public void setTitular(OpcoesPesquisaBoolean titular) {
		this.titular = titular;
	}
	
	public List<EstadoAR> getEstadosAR() {
		return estadosAR;
	}
	
	public void setEstadosAR(List<EstadoAR> estadosAR) {
		this.estadosAR = estadosAR;
	}

	public List<Divisao> getDivisoes() {
		return divisoes;
	}

	public void setDivisoes(List<Divisao> divisoes) {
		this.divisoes = divisoes;
	}

}
