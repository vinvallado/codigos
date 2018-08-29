package br.ccasj.sisauc.pesquisa.domain;


public class ComparacaoValoresItemGAB {

	private OpcoesPesquisaValoresItemGAB valor1;
	private OpcoesPesquisaValoresItemGAB valor2;
	private OpcoesPesquisaNumero opcaoComparacao;
	private Double valorDigitado;
	
	public OpcoesPesquisaValoresItemGAB getValor1() {
		return valor1;
	}
	
	public void setValor1(OpcoesPesquisaValoresItemGAB valor1) {
		this.valor1 = valor1;
	}
	
	public OpcoesPesquisaValoresItemGAB getValor2() {
		return valor2;
	}
	
	public void setValor2(OpcoesPesquisaValoresItemGAB valor2) {
		this.valor2 = valor2;
	}
	
	public OpcoesPesquisaNumero getOpcaoComparacao() {
		return opcaoComparacao;
	}
	
	public void setOpcaoComparacao(OpcoesPesquisaNumero opcaoComparacao) {
		this.opcaoComparacao = opcaoComparacao;
	}
	
	public Double getValorDigitado() {
		return valorDigitado;
	}
	
	public void setValorDigitado(Double valorDigitado) {
		this.valorDigitado = valorDigitado;
	}


}
