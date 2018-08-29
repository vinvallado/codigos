package br.ccasj.sisauc.framework.domain;

public enum Divisao {

	DIVISAO_MEDICA("Médica"),
	DIVISAO_ODONTOLOGICA("Odontológica");

	private String label;

	private Divisao(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
