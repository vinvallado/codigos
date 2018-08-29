package br.mil.fab.consigext.enums;

public enum TipoMilitar {
	ATIVA(1),
	RESERVA(3),
	REFORMADO(4),
	PENSIONISTA(5);
	
	
	int value;
	
	TipoMilitar(int value){
		this.value = value;
	}
	
	
	
	
	
}
