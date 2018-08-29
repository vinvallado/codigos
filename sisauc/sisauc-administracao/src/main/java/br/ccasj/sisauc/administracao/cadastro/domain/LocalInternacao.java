package br.ccasj.sisauc.administracao.cadastro.domain;

public enum LocalInternacao {
	
	ENFERMARIA("Em enfermaria"),
	APARTAMENTO("Em apartamento"),
	UTI("UTI"),
	DOMICILIAR("Domiciliar");
	
private String label;

private LocalInternacao(String label) {
	this.label = label;
}

public String getLabel() {
	return label;
}

public void setLabel(String label) {
	this.label = label;
}

}