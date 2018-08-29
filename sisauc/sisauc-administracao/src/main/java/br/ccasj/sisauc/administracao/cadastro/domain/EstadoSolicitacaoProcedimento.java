package br.ccasj.sisauc.administracao.cadastro.domain;

public enum EstadoSolicitacaoProcedimento {

	CRIADA("Não Enviada para Auditoria"),
	ENVIADA_PARA_AUDITORIA("Enviada para Auditoria"),
	INCONSISTENTE("Inconsistente"),
	AUDITADA("Auditada");
	
	private String label;
	
	private EstadoSolicitacaoProcedimento(String label){
		this.label = label;
	}
	
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
