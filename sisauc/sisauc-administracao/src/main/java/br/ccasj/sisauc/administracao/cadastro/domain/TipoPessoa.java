package br.ccasj.sisauc.administracao.cadastro.domain;

public enum TipoPessoa {

	PESSOA_JURIDICA("Pessoa Jurídica"), PESSOA_FISICA("Pessoa Física");

	private String label;

	private TipoPessoa(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
