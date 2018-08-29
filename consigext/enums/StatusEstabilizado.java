package br.mil.fab.consigext.enums;

public enum StatusEstabilizado {

	S("SIM"),
	N("NAO");
	
	private String valor;
    
	StatusEstabilizado  (String valor) {
        this.valor = valor;
    }
    
    public String getValor(){
        return this.valor;    
    }
}
