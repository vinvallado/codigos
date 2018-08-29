package br.ccasj.sisauc.intendencia.domain;

public enum EstadoRelatorioFolhaBeneficiario {
	
	NAO_ENVIADO("Não Enviado"),
	INICIADO("Envio Iniciado"),
	INTERROMPIDO_ERRO("Envio Parcial (Erros)"),
	ENVIADO("Enviado (Sucesso)");
	
	private String label;
	
	private EstadoRelatorioFolhaBeneficiario(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}

	public String setLabel(String label) {
		return this.label = label;
	}
	
}
