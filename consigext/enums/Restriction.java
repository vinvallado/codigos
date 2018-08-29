package br.mil.fab.consigext.enums;

public enum Restriction {
	GESTOR("Apenas gestor possui acesso"),
	GESTOR_OU_ENTIDADE_CONSIG_CORRENTE("Apenas gestor ou a referida entidade consignataria possuem acesso a página requerida"),
	ENTIDADE_CONSIG_CORRENTE("Apenas a referida entidade consignataria possue acesso a página requerida"),
	QUALQUER_ENTIDADE_CONSIG("Apenas uma entidade consignataria possue acesso a página requerida"),
	SERVIDOR("Apenas um servidor possue acesso a página requerida"),
	TODOS_USUARIOS("Todos usuários possuem acesso a página requerida"),
	GESTOR_OU_QUALQUER_ENTIDADE_CONSIG("Apenas gestor ou entidades consignatarias possuem acesso a página requerida");
	private String valor;
    
	Restriction  (String valor) {
        this.valor = valor;
    }
    
    public String getValor(){
        return this.valor;    
    }
    
}
