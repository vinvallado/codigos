package br.mil.fab.consigext.enums;

public enum TipoHistoricoEc {	
	BLOQUEIO_MANUAL("bloqueio manual"),
	BLOQUEIO_AUTOMATICO("bloqueio automatico"),
	DESBLOQUEIO_MANUAL("desbloqueio manual"),
	ORIGEM("origem"),
	DESBLOQUEIO_AUTOMATICO("desbloqueio automatico");
	
	private String valor;
    
	TipoHistoricoEc  (String valor) {
        this.valor = valor;
    }
    @Override
    public String toString(){
        return this.valor;    
    }
}


