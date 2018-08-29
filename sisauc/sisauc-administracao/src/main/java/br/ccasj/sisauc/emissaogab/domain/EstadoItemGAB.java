package br.ccasj.sisauc.emissaogab.domain;

public enum EstadoItemGAB {
	CRIADO("Criado"),
	CANCELADO("Cancelado"),
	NAO_REALIZADO("Não Realizado"),
	AGUARDANDO_AUDITORIA("Aguardando Auditoria"),
	AUDITORIA_INICIADA("Auditoria Iniciada"),
	NAO_CONFORME("Não Conforme"),
	CONFORME("Conforme"),
	ENVIADO_PARA_DESCONTO("Enviado para Desconto");
	
	private String label;

	private EstadoItemGAB(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
