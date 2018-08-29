package br.mil.fab.consigext.enums;

public enum StatusEntidade {

	BLOQUEADO(0),
	DESBLOQUEADO(1);
	
	private Integer valor;
    
	StatusEntidade (Integer valor) {
        this.valor = valor;
    }
    
    public Integer getValor(){
        return this.valor;    
    }
    
}
