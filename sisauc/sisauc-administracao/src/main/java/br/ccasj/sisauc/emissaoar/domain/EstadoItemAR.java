package br.ccasj.sisauc.emissaoar.domain;

public enum EstadoItemAR {
	APROVADO("Aprovado"),
	NAO_APROVADO("Não Aprovado"),
	EM_AUDITORIA("Em Auditoria"),
	REALIZADO("Realizado"),
	NAO_REALIZADO("Não Realizado"),
	CANCELADO("Cancelado");
	
	private String label;

	private EstadoItemAR(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
