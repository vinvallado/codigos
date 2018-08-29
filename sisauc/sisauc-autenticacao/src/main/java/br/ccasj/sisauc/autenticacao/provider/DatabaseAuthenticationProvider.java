package br.ccasj.sisauc.autenticacao.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import br.ccasj.sisauc.autenticacao.dao.UsuarioDAO;
import br.ccasj.sisauc.framework.domain.Usuario;
import br.ccasj.sisauc.framework.domain.UsuarioUserDetails;

@SuppressWarnings("deprecation")
public class DatabaseAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken userToken = (UsernamePasswordAuthenticationToken) authentication;
		String loginFornecido = userToken.getName();
		String senhaFornecida = (String) userToken.getCredentials();
		
		Usuario usuario = usuarioDAO.obterPorNomeUsuario(loginFornecido);
		verificarUsuarioSenha(usuario, senhaFornecida);
		UsuarioUserDetails details = new UsuarioUserDetails(usuario);
		return new UsernamePasswordAuthenticationToken(details, details, details.getAuthorities());
	}

	private void verificarUsuarioSenha(Usuario usuario, String senhaFornecida) {
		if(usuario == null){
			throw new BadCredentialsException("Nome de usuário e/ou senha incorretos");
		}
		if(!passwordEncoder.encodePassword(senhaFornecida, null).equals(usuario.getSenha())){
			throw new BadCredentialsException("Nome de usuário e/ou senha incorretos");
		}
		if(!usuario.isAtivo()){
			throw new BadCredentialsException("Usuário desativado");
		}
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
