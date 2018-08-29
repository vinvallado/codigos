package br.mil.fab.consigext.enums;

public enum RoleEnum {

	CONSIGEXT_SERVIDOR("Servidor"),
	CONSIGEXT_CONSIGNATARIA("Consignataria"),
	CONSIGEXT_GESTOR("Gestor");
		
	private final String nmRole;
	
	private RoleEnum(String nmRole) {
		this.nmRole = nmRole;
	}

	public String getNmRole() {
		return nmRole;
	}

}
