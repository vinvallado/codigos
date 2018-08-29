package br.mil.fab.consigext.enums;

public enum StatusCodigoUnico {

	NAO_USADO(0L),
	UTILIZADO(1L),
	INVALIDO(2L);
		
	private final Long vlStatus;
	
	private StatusCodigoUnico(Long vlStatus) {
		this.vlStatus = vlStatus;
	}

	public Long getVlStatus() {
		return vlStatus;
	}

}
