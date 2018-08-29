package br.ccasj.sisauc.framework.context;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.ccasj.sisauc.framework.domain.Usuario;
import br.ccasj.sisauc.framework.domain.UsuarioUserDetails;

@Transactional
@Component("authenticationContext")
public class AuthenticationContext implements Serializable {
	
	private static final long serialVersionUID = 6466837638503986718L;
	
	public Usuario getUsuarioLogado(){
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		if(authentication == null || !(authentication.getPrincipal() instanceof UsuarioUserDetails)){
			return null;
		}
		return ((UsuarioUserDetails) authentication.getPrincipal()).getUsuario();
	}
	
	public void logout() throws IOException{
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		((HttpSession)context.getSession(false)).invalidate();
		context.redirect(context.encodeResourceURL(((HttpServletRequest)context.getRequest()).getContextPath()+"/j_spring_security_logout"));
	}
		
}
