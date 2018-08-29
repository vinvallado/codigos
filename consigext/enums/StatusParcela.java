package br.mil.fab.consigext.enums;

public enum StatusParcela {

	ABERTO("Em Aberto"),
	LIQUIDADA_FOLHA("Liquidada Folha"),
	CANCELADA("Cancelada"),
	SUSPENSO("Suspenso"),
	PROCESSAMENTO("Em Processamento"),
	REJEITADA("Rejeitada Folha"),
	LIQUIDACAO_MANUAL("Liquidação Manual"),
	SEM_RETORNO("Sem Retorno"),
	AGUARDANDO("Aguardando Processamento"),
	LIQUIDADA_CONTRATO("Liquidada por Contrato");
	
	private String valor;
    
	StatusParcela  (String valor) {
        this.valor = valor;
    }
    
    public String getValor(){
        return this.valor;    
    }
}
