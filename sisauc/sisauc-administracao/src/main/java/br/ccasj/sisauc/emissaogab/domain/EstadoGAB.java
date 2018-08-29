package br.ccasj.sisauc.emissaogab.domain;

public enum EstadoGAB {

	GERADA("Gerada"), 
	EMITIDA("Emitida"), 
	CANCELADA("Cancelada"), 
	APRESENTADA("Apresentada"),
	EM_AUDITORIA("Em Auditoria"),
	AUDITADA("Auditada");

	private String label;

	private EstadoGAB(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
