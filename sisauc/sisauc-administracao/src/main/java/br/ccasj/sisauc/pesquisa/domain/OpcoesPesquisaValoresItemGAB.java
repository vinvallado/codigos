package br.ccasj.sisauc.pesquisa.domain;

public enum OpcoesPesquisaValoresItemGAB {
	
	ESTIMADO("Valor Estimado","item.valorTotal"), 
	APRESENTADO("Valor Apresentado","auditoriaRetrospectiva.valorApresentado"), 
	AUDITADO("Valor Auditado","auditoriaRetrospectiva.valorAuditado"),
	FINAL("Valor Final","auditoriaRetrospectiva.valorFinal"),
	VALOR_DIGITADO("Digitar valor", "");
	
	private String label;
	private String campo;
	
	private OpcoesPesquisaValoresItemGAB(String label, String campo){
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
