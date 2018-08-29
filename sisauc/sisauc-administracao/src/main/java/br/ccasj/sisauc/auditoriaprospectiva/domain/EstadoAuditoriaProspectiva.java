package br.ccasj.sisauc.auditoriaprospectiva.domain;

public enum EstadoAuditoriaProspectiva {

	APROVADO("Aprovado"), REPROVADO("Reprovado");

	private String label;

	private EstadoAuditoriaProspectiva(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
