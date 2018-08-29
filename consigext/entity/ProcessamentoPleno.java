package br.mil.fab.consigext.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProcessamentoPleno implements Serializable {
   
		private static final long serialVersionUID = 1L;
		// 1 campo
		private String tipo;
		// 2 campos
		private String posto;
		// 6 campos
		private String nrOrdem;
		// 6 campos
		private String uPag;
		// 29 campos
		private String nome;
		// 11 campos
		private String identidade;
		// 11 campos
		private String cpf;
		// 3 campos
		private String banco;
		// 5 campos
		private String agencia;
		// 10 campos
		private String conta;
		// 11 campos
		private BigDecimal margem70;
		// 1 campo
		private String status;
		// 1 campo
		private String capacidadeCivil;
		// 10 campo
		private String nascimento;
		// 8 campos
		private String estabilizado;
		// 50 campos
		private String situacaoPagto;
		// 10 campos
		private String dataFimReengaj;
		// 11 campos
		
//		private BigDecimal margem30;
		
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
		public String getNrOrdem() {
			return nrOrdem;
		}
		public void setNrOrdem(String nrOrdem) {
			this.nrOrdem = nrOrdem;
		}
		public String getuPag() {
			return uPag;
		}
		public void setuPag(String uPag) {
			this.uPag = uPag;
		}
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public String getIdentidade() {
			return identidade;
		}
		public void setIdentidade(String identidade) {
			this.identidade = identidade;
		}
		public String getCpf() {
			return cpf;
		}
		public void setCpf(String cpf) {
			this.cpf = cpf;
		}
		public String getBanco() {
			return banco;
		}
		public void setBanco(String banco) {
			this.banco = banco;
		}
		public String getAgencia() {
			return agencia;
		}
		public void setAgencia(String agencia) {
			this.agencia = agencia;
		}
		public String getConta() {
			return conta;
		}
		public void setConta(String conta) {
			this.conta = conta;
		}
//		public BigDecimal getMargem70() {
//			return margem70;
//		}
//		public void setMargem70(BigDecimal margem70) {
//			this.margem70 = margem70;
//		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getCapacidadeCivil() {
			return capacidadeCivil;
		}
		public void setCapacidadeCivil(String capacidadeCivil) {
			this.capacidadeCivil = capacidadeCivil;
		}
		public String getNascimento() {
			return nascimento;
		}
		public void setNascimento(String nascimento) {
			this.nascimento = nascimento;
		}
		public String getEstabilizado() {
			return estabilizado;
		}
		public void setEstabilizado(String estabilizado) {
			this.estabilizado = estabilizado;
		}
		public String getSituacaoPagto() {
			return situacaoPagto;
		}
		public void setSituacaoPagto(String situacaoPagto) {
			this.situacaoPagto = situacaoPagto;
		}
		public String getDataFimReengaj() {
			return dataFimReengaj;
		}
		public void setDataFimReengaj(String dataFimReengaj) {
			this.dataFimReengaj = dataFimReengaj;
		}
//		public BigDecimal getMargem30() {
//			return margem30;
//		}
//		public void setMargem30(BigDecimal margem30) {
//			this.margem30 = margem30;
//		}
		
	
		
		
	

}
