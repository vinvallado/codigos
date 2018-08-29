package br.mil.fab.consigext.enums;

public enum StatusServico {
	
	BLOQUEADO(0),
	DESBLOQUEADO(1);
	
	private Integer valor;
    
	StatusServico (Integer valor) {
        this.valor = valor;
    }
    
    public Integer getValor(){
        return this.valor;    
    }

}
