package br.ccasj.sisauc.pesquisa.domain;

public enum OpcoesPesquisaValoresItemARE {
	
	APRESENTADO("Valor Apresentado","auditoriaRetrospectiva.valorApresentado"), 
	TRS("Valor TRS","auditoriaRetrospectiva.valorEstimado"), 
	RESSARCIDO("Valor Ressarcido","auditoriaRetrospectiva.valorRessarcimento"),
	VALOR_DIGITADO("Digitar valor", "");
	
	private String label;
	private String campo;
	
	private OpcoesPesquisaValoresItemARE(String label, String campo){
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
