package br.ccasj.sisauc.pesquisa.domain;

public enum OpcoesPesquisaNumero {
	
	IGUAL("Igual a", "="), 
	MAIOR("Maior que", ">"), 
	MENOR("Menor que", "<");
	
	private String label;
	private String campo;
	
	private OpcoesPesquisaNumero(String label, String campo){
		this.label = label;
		this.campo = campo;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}
	
}
