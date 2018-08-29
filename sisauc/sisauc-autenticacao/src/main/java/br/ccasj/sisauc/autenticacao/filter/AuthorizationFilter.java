package br.ccasj.sisauc.autenticacao.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.ccasj.sisauc.framework.context.AuthenticationContext;
import br.ccasj.sisauc.framework.domain.Usuario;

@SuppressWarnings("deprecation")
@Component("authorizationFilter")
public class AuthorizationFilter implements Filter {

	private static final String COOKIE_VERSAO = "versao";
	@Autowired
	private AuthenticationContext authenticationContext;
	@Autowired
	private PasswordEncoder passwordEncoder;



	@Autowired
	@Qualifier(value = "versaoInterna")
	private String versaoInterna;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
			ServletException {
		Usuario usuario = authenticationContext.getUsuarioLogado();
		if(usuario != null && usuario.getSenha().equals(passwordEncoder.encodePassword(usuario.getLogin(), null))
				&& !((HttpServletRequest) req).getRequestURI().contains("perfil")){
			((HttpServletResponse)resp).sendRedirect(((HttpServletRequest) req).getContextPath()+"/perfil?motivo=senhaigual");
			return;
		}
		if(usuario != null && !usuario.getLogin().equals(null)){
			verficarNotasVersao(req, resp, usuario.getLogin());
		}
		chain.doFilter(req, resp);
	}

	private void verficarNotasVersao(ServletRequest req, ServletResponse resp, String login) {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		Cookie[] cookies = request.getCookies();
		
		Cookie ultimaVersao; 
		Cookie cookieUsuario; 
		if(cookies != null && cookies.length > 0){
			ultimaVersao = obterCookie(COOKIE_VERSAO, cookies);
			cookieUsuario = obterCookie(login, cookies);

			if(ultimaVersao == null){
				ultimaVersao = new Cookie(COOKIE_VERSAO, versaoInterna);
				response.addCookie(ultimaVersao);
			}
			
			if(cookieUsuario == null){
				cookieUsuario = new Cookie(login, "true");
				cookieUsuario.setSecure(true);
				response.addCookie(cookieUsuario);
			}
									
			if(!ultimaVersao.getValue().equals(versaoInterna)){
				cookieUsuario.setValue("true");
				response.addCookie(cookieUsuario);
			}
		}
		
	}

	private Cookie obterCookie(String nome, Cookie[] cookies) {
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals(nome)){
				return cookie;
			}
		}
		return null;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
