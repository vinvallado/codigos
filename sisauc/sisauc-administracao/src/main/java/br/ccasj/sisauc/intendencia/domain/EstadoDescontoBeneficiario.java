package br.ccasj.sisauc.intendencia.domain;

public enum EstadoDescontoBeneficiario {
	NAO_FINALIZADO("Não Finalizado"),//Criado ou sendo enviado.
	INTERROMPIDO_ERRO("Interrompido por Erro"),//Interrompido por erro dos dados
	FINALIZADO("Finalizado")//Desconto enviado e finalizado no sisauc.
	;
	private String label;
	
	private EstadoDescontoBeneficiario(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
}
