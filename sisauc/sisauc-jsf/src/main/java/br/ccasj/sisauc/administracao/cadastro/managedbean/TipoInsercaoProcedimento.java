package br.ccasj.sisauc.administracao.cadastro.managedbean;

public enum TipoInsercaoProcedimento {

	INDIVIDUAL("Individual"), GRUPO("Grupo"), SUBGRUPO("Subgrupo");

	private String label;

	private TipoInsercaoProcedimento(String label) {
		this.setLabel(label);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
