package br.mil.fab.consigext.enums;

public enum StatusAcesso {
	
	BLOQUEADO("acesso.bloqueado"),
	EXCLUIDO("acesso.excluido"),
	PERMITIDO("Acesso permitido"),
	APENAS_GESTOR("acesso.apenas_gestor"),
	CONSIG_DIF_CORRENTE("acesso.consig_dif_corrente"),
	SERVIDOR_SEM_ACESSO("acesso.servidor_sem_acesso"),
	SEM_PERMISSAO("acesso.sem_permissao"),
	APENAS_SERVIDOR("acesso.apenas_servidor"),
	APENAS_CONSIG("acesso.apenas_consig"),
	APENAS_CONSIG_CORRENTE("acesso.apenas_consig_corrente");
	
	private String valor;
    
	StatusAcesso  (String valor) {
        this.valor = valor;
    }
    
    public String getStatusAcesso(){
        return this.valor;    
    }
}
