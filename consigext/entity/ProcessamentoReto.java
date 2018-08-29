package br.mil.fab.consigext.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProcessamentoReto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//template: org tipo posto saram caixa valorAtual valorNovo prazo nome status ade dmc tpOperacao filler plano cpf
	
	// 6 campos
	private String cdOrg;
	// 1 campo
	private String tipo;
	// 2 campos
	private String posto;
	// 6 campos
	private String nrordem;
	// 3 campos
	private String caixa;
	// 9 campos
	private BigDecimal valorAtual;
	// 9 campos
	private BigDecimal valornovo;
	// 4 campos
	private String prazo;
	// 29 campos
	private String nome;
	// 1 campo
	private String status;
	// 9 campos
	private String ade;
	 //dmc 1 campo vazio
	// 1 campo
	private String tpOperacao;
	// filler 3 campos vazios
	// 2 campos
	private String plano;
	// 11 campos
	private String cpf;
	
	public String getCdOrg() {
		return cdOrg;
	}
	public void setCdOrg(String cdOrg) {
		this.cdOrg = cdOrg;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getPosto() {
		return posto;
	}
	public void setPosto(String posto) {
		this.posto = posto;
	}
	public String getNrordem() {
		return nrordem;
	}
	public void setNrordem(String nrordem) {
		this.nrordem = nrordem;
	}
	public String getCaixa() {
		return caixa;
	}
	public void setCaixa(String caixa) {
		this.caixa = caixa;
	}
	public BigDecimal getValorAtual() {
		return valorAtual;
	}
	public void setValorAtual(BigDecimal valorAtual) {
		this.valorAtual = valorAtual;
	}
	public BigDecimal getValornovo() {
		return valornovo;
	}
	public void setValornovo(BigDecimal valornovo) {
		this.valornovo = valornovo;
	}
	public String getPrazo() {
		return prazo;
	}
	public void setPrazo(String prazo) {
		this.prazo = prazo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAde() {
		return ade;
	}
	public void setAde(String ade) {
		this.ade = ade;
	}
	public String getTpOperacao() {
		return tpOperacao;
	}
	public void setTpOperacao(String tpOperacao) {
		this.tpOperacao = tpOperacao;
	}
	public String getPlano() {
		return plano;
	}
	public void setPlano(String plano) {
		this.plano = plano;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	
	
	
	
	
	
	
	

}
