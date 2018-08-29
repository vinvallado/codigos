package br.mil.fab.consigext.enums;

public enum CapacidadeServidor {

	C("CURATELADO"),
	P("PLENAMENTE"),
	T("TUTELADO");
	
	private String valor;
    
	CapacidadeServidor  (String valor) {
        this.valor = valor;
    }
    
    public String getValor(){
        return this.valor;    
    }

}
