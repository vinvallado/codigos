package br.ccasj.sisauc.framework.domain;

public enum Perfil {
	
	PERFIL_USUARIO("Usuário"),
	PERFIL_ADMINISTRADOR_SISTEMA("Administrador do Sistema"),
	PERFIL_ATENDENTE_FUNSA("Atendente FUNSA"),
	PERFIL_AUDITOR_FUNSA("Auditor Prospectivo"),
	PERFIL_AUDITOR_FINANCEIRO("Auditor Retrospectivo"),
	PERFIL_CHEFE_FUNSA("Chefe FUNSA"),
	PERFIL_DIRETOR("Diretor"),
	PERFIL_INTENDENCIA("Intendência"),
	PERFIL_SDGA("SDGA"),
	PERFIL_DIRSA("DIRSA");
	
	private String label;

	private Perfil(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
