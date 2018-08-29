package br.mil.fab.consigext.enums;

public enum EstadoCivil {

	SOL("Solteiro"),
	DES("Desquitado"),
	DIV("Divorciado"),
	CON("Uni.Estavel"),
	VIU("Viúvo"),
	SEP("Separado"),
	CAS("Casado");
	
	private String valor;
    
	EstadoCivil  (String valor) {
        this.valor = valor;
    }
    
    public String getValor(){
        return this.valor;    
    }
}
