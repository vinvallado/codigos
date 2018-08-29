package br.ccasj.sisauc.autenticacao.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.OrganizacaoMilitarRegionalDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.OrganizacaoMilitarSubordinadaDAO;
import br.ccasj.sisauc.administracao.sistema.domain.OrganizacaoMilitarRegional;
import br.ccasj.sisauc.administracao.sistema.domain.OrganizacaoMilitarSubordinada;
import br.ccasj.sisauc.autenticacao.dao.UsuarioDAO;
import br.ccasj.sisauc.autenticacao.service.CadastroUsuarioService;
import br.ccasj.sisauc.framework.domain.Perfil;
import br.ccasj.sisauc.framework.domain.Usuario;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;

@SuppressWarnings("deprecation")
@Transactional
@Service(value = "cadastroUsuarioService")
public class CadastroUsuarioServiceImpl implements CadastroUsuarioService {

	private static final long serialVersionUID = 5781220041681693493L;

	@Autowired
	private UsuarioDAO usuarioDAO;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private OrganizacaoMilitarRegionalDAO organizacaoMilitarRegionalDAO;
	@Autowired
	private OrganizacaoMilitarSubordinadaDAO organizacaoMilitarSubordinadaDAO;

	@Override
	public Usuario salvar(Usuario usuario) {
		verificarUnicidadeLogin(usuario);
		if (usuario.getId() == null) {
			usuario.setSenha(passwordEncoder.encodePassword(usuario.getLogin(), null));
		}
		verificarPerfisUsuario(usuario.getPerfis());
		return usuarioDAO.merge(usuario);
	}

	private void verificarUnicidadeLogin(Usuario usuario) {
		if(usuarioDAO.verificarUnidadeLogin(usuario.getId(), usuario.getLogin())){
			throw new SystemRuntimeException("Já existe um usuário com este login!");
		}
	}

	private void verificarPerfisUsuario(Set<Perfil> perfis) {
		if(perfis == null || perfis.isEmpty()){
			throw new SystemRuntimeException("É obrigatório que o usuário possua ao menos um perfil!");
		}
	}

	@Override
	public Usuario resetarSenha(Integer id) {
		Usuario usuario = usuarioDAO.findById(id);
		usuario.setSenha(passwordEncoder.encodePassword(usuario.getLogin(),null));
		return usuarioDAO.merge(usuario);
	}


	@Override
	public void atualizarSenha(String novaSenha, String velhaSenha, String repeticaoNovaSenha, Integer idUsuario) {
		Usuario usuario = usuarioDAO.findById(idUsuario);
		validarVelhaSenha(velhaSenha, usuario.getSenha());
		validarNovaSenha(novaSenha, repeticaoNovaSenha);
		validarMudouSenha(velhaSenha, novaSenha);
		usuarioDAO.atualizarSenha(novaSenha, idUsuario);
	}


	private void validarVelhaSenha(String velhaSenha, String senha) {
		if(!passwordEncoder.encodePassword(velhaSenha, null).equals(senha)){
			throw new SystemRuntimeException("Senha atual incorreta!");
		}
	}

	private void validarNovaSenha(String novaSenha, String repeticaoNovaSenha) {
		if(!novaSenha.equals(repeticaoNovaSenha)){
			throw new SystemRuntimeException("Repetição da senha incorreta!");
		}
	}
	
	private void validarMudouSenha(String velhaSenha, String novaSenha) {
		if(novaSenha.equals(velhaSenha)){
			throw new SystemRuntimeException("Digite uma nova senha!");
		}
	}
	
	public void validarOMAoAtivarUsuario(Integer idUsuario){
		OrganizacaoMilitarRegional regional = organizacaoMilitarRegionalDAO.obterRegionalSistema();
		List<OrganizacaoMilitarSubordinada> subordinadas = organizacaoMilitarSubordinadaDAO.listarSubordinadasAtivasPorRegional(regional.getId());
		List<Integer> organizacoesMilitares = new ArrayList<Integer>();
		if(subordinadas != null && !subordinadas.isEmpty()){
			for (OrganizacaoMilitarSubordinada organizacaoMilitarSubordinada : subordinadas) {
				organizacoesMilitares.add(organizacaoMilitarSubordinada.getId());
			}
		}
		organizacoesMilitares.add(regional.getId());
		if(!usuarioDAO.verificarOmUsuario(idUsuario, organizacoesMilitares)){
			throw new SystemRuntimeException("O usuário não pode ser ativado pois a sua Organização Militar foi removida desta Regional");
		}
	}

	
}
