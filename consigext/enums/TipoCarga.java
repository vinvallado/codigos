package br.mil.fab.consigext.enums;

public enum TipoCarga {
	SAIDA("SAIDA"),
	RETO("RETO"),
	PLENO("PLENO"),
	ANEXO("ANEXO"),
	GENERICOS("GENERICOS"),
	CONSIGNACAO("CONSIGNACAO"),
	BOLETO_SALDO_DEVEDOR("BOLETO_SALDO_DEVEDOR"),
	DEMONSTRATIVO_SALDO_DEVEDOR("DEMONSTRATIVO_SALDO_DEVEDOR"),
	DETALHES_SALDO_DEVEDOR("DETALHES_SALDO_DEVEDOR");

	private String tipoCarga;
    
	TipoCarga (String tipoCarga) {
        this.tipoCarga = tipoCarga;
    }
    @Override
    public String toString(){
        return tipoCarga;    
    }

}
