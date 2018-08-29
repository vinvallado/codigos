package br.ccasj.sisauc.pesquisa.domain;

public enum OpcoesPesquisaBoolean {
	
	TODOS("Todos"), SIM("Sim"), NAO("Não");

	private String label;
	
	private OpcoesPesquisaBoolean(String label){
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
