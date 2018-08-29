package br.ccasj.sisauc.administracao.cadastro.domain;

public enum Sexo {

	//TODO: verificar como é inserido no banco
	M("Masculino"), F("Feminino");

	private String descricao;

	private Sexo(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Sexo getSexo(String descricao) {
		if (descricao != null && F.getDescricao().toUpperCase().equals(descricao.toUpperCase())) {
			return F;
		}
		return M;
	}
}
