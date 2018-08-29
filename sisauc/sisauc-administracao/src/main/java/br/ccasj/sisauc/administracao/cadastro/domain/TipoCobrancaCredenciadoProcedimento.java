package br.ccasj.sisauc.administracao.cadastro.domain;

public enum TipoCobrancaCredenciadoProcedimento {
	
	TAXA("Taxa"),
	PACOTE("Pacote");
	
	private String label;
	
	private TipoCobrancaCredenciadoProcedimento(String label){
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
