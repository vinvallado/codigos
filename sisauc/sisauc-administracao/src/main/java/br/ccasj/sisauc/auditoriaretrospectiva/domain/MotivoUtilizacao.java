package br.ccasj.sisauc.auditoriaretrospectiva.domain;

public class MotivoUtilizacao {
	
	private Integer id;
	private Long qtd;
	
	public MotivoUtilizacao(Integer id, Long qtd){
		this.id = id;
		this.qtd = qtd;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getQtd() {
		return qtd;
	}
	public void setQtd(Long qtd) {
		this.qtd = qtd;
	}
}
