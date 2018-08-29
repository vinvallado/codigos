package br.ccasj.sisauc.autenticacao.dao.impl;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.autenticacao.dao.UsuarioDAO;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;
import br.ccasj.sisauc.framework.domain.Perfil;
import br.ccasj.sisauc.framework.domain.Usuario;

@SuppressWarnings("deprecation")
@MappedSuperclass
@Transactional
@Repository(value = "usuarioDAO")
@NamedQueries({ 
	@NamedQuery(name = UsuarioDAONamedQueries.OBTER_POR_NOME_USUARIO, query = UsuarioDAONamedQueries.OBTER_POR_NOME_USUARIO),
	@NamedQuery(name = UsuarioDAONamedQueries.MUDAR_STATUS_ATIVO_USUARIO, query = UsuarioDAONamedQueries.MUDAR_STATUS_ATIVO_USUARIO),
	@NamedQuery(name = UsuarioDAONamedQueries.ATUALIZAR_SENHA, query = UsuarioDAONamedQueries.ATUALIZAR_SENHA),
	@NamedQuery(name = UsuarioDAOImpl.VERIFICAR_UNICIDADE_LOGIN, query = UsuarioDAOImpl.VERIFICAR_UNICIDADE_LOGIN),
	@NamedQuery(name = UsuarioDAONamedQueries.OBTER_PERFIS_POR_USUARIO, query = UsuarioDAONamedQueries.OBTER_PERFIS_POR_USUARIO),
	@NamedQuery(name = UsuarioDAOImpl.VERIFICAR_OM_USUARIO_PARA_ATIVACAO, query = UsuarioDAOImpl.VERIFICAR_OM_USUARIO_PARA_ATIVACAO),
	@NamedQuery(name = UsuarioDAOImpl.ATUALIZAR_LEU_NOTAS_VERSAO, query = UsuarioDAOImpl.ATUALIZAR_LEU_NOTAS_VERSAO)
})
public class UsuarioDAOImpl extends GenericEntityDAOImpl<Usuario> implements UsuarioDAO {

	private static final long serialVersionUID = 985209674434102966L;

	public static final String VERIFICAR_UNICIDADE_LOGIN = "select case when (count(*) > 0) then true else false end from Usuario as u where upper(u.login) = :login and u.id <> :id";
	public static final String VERIFICAR_OM_USUARIO_PARA_ATIVACAO = "select case when (count(usuario.id) > 0) then true else false end from Usuario usuario left join usuario.organizacaoMilitar om where om.id in (:idsOms) and usuario.id = :idUsuario";
	public static final String ATUALIZAR_LEU_NOTAS_VERSAO = "UPDATE Usuario set leuNotasVersao = true where id = :id";
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Usuario obterPorNomeUsuario(String nomeUsuario) {
		List<Usuario> result = entityManager.createNamedQuery(UsuarioDAONamedQueries.OBTER_POR_NOME_USUARIO, Usuario.class).setParameter("login", nomeUsuario).getResultList();
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public void atualizarSenha(String novaSenha, Integer idUsuario) {
		entityManager.createNamedQuery(UsuarioDAONamedQueries.ATUALIZAR_SENHA).setParameter("id", idUsuario)
				.setParameter("senha", passwordEncoder.encodePassword(novaSenha, null)).executeUpdate();
	}
	
	@Override
	public List<Perfil> obterPerfisPorUsuario(Integer id) {
		return entityManager.createNamedQuery(UsuarioDAONamedQueries.OBTER_PERFIS_POR_USUARIO, Perfil.class).setParameter("id", id).getResultList();
	}

	@Override
	public boolean verificarUnidadeLogin(Integer id, String login) {
		id = id == null ? -1 : id;
		return (boolean) entityManager.createNamedQuery(VERIFICAR_UNICIDADE_LOGIN).setParameter("login", login.toUpperCase()).setParameter("id", id).getSingleResult();
	}

	@Override
	public void mudarStatusAtivoUsuario(Integer id, boolean b) {
		entityManager.createNamedQuery(UsuarioDAONamedQueries.MUDAR_STATUS_ATIVO_USUARIO).setParameter("id", id).setParameter("status", b).executeUpdate();
	}

	@Override
	public boolean verificarOmUsuario(Integer idUsuario, List<Integer> idsOms) {
		return (boolean) entityManager.createNamedQuery(VERIFICAR_OM_USUARIO_PARA_ATIVACAO).setParameter("idUsuario", idUsuario).setParameter("idsOms", idsOms).getSingleResult();
	}
	
	@Override
	public void atualizarLeuNotasVersao(Integer idUsuario) {
		entityManager.createNamedQuery(ATUALIZAR_LEU_NOTAS_VERSAO).setParameter("id", idUsuario).executeUpdate();
	}
	
}
