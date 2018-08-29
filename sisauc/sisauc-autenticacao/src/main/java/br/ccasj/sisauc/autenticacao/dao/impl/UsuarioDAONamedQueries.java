package br.ccasj.sisauc.autenticacao.dao.impl;

public abstract class UsuarioDAONamedQueries {
	
	public static final String OBTER_POR_NOME_USUARIO = "from Usuario u where u.login = :login";
	public static final String MUDAR_STATUS_ATIVO_USUARIO = "update Usuario u set u.ativo = :status where u.id = :id";
	public static final String ATUALIZAR_SENHA = "update Usuario u set u.senha = :senha where u.id = :id";
	public static final String OBTER_PERFIS_POR_USUARIO = "select p from Usuario u right join u.perfis as p where u.id = :id order by p.id";

}
