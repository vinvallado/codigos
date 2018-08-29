package br.ccasj.sisauc.administracao.cadastro.dao.impl;

public class CredenciadoDAONamedQueries {

	public static final String OBTER_POR_NOME_CREDENCIADO = "from Credenciado c where c.nomeFantasia = :nomeFantasia";
	public static final String MUDAR_STATUS_ATIVO_CREDENCIADO = "update Credenciado c set c.ativo = :status where c.id = :id";
	public static final String VERIFICAR_CPF_EXISTENTE = "select case when (count(*) > 0) then true else false end from Credenciado as c where c.cpf = :cpf and c.id <> :id";
	public static final String VERIFICAR_CNPJ_EXISTENTE = "select case when (count(*) > 0) then true else false end from Credenciado as c where c.cnpj = :cnpj and c.id <> :id";
}
