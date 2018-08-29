package br.ccasj.sisauc.emissaoar.domain;

public enum EstadoAR {

	GERADA("Gerada"), 
	EMITIDA("Emitida"), 
	CANCELADA("Cancelada"), 
	APRESENTADA("Apresentada"),
	EM_AUDITORIA("Em Auditoria"),
	INCONSISTENTE("Inconsistente"),
	AUDITADA("Auditada")
	;
	// ,EFETIVADA("Efetivada"); // ENVIADA PARA O SISTEMA DE PAGAMENTO SISCONSIG 

	private String label;

	private EstadoAR(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
