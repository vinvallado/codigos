package br.mil.fab.consigext.enums;

public enum TipoSolicSaldoDevedor {
	CONSULTA("consulta"),
	LIQUIDACAO("liquidacao");
	
	private String valor;	
	TipoSolicSaldoDevedor (String valor) {
        this.valor = valor;
    }
    
    public String getTipoSolicitacao(){
        return this.valor;    
    }
}
