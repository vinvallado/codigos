package br.ccasj.sisauc.administracao.cadastro.domain;


public enum Tabela {

	CBHPM2012("CBHPM-2012", "Classificação Brasileira Hierarquizada de Procedimentos Médicos da Associação Médica Brasileira - Ed. 2012"),
	CISSFA("CISSFA", "Catálogo de Indenizações dos Serviços de Saúde das Forças Armadas"),
	TRS("TRS", "Tabela de Ressarcimento da SARAM");

	private String nome;
	private String descricao;

	private Tabela(String nome, String descricao) {
		this.nome = nome;
		this.descricao = descricao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
