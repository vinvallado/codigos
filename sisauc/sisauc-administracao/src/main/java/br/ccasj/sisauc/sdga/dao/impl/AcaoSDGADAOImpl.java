package br.ccasj.sisauc.sdga.dao.impl;

import java.util.Date;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.framework.context.AuthenticationContext;
import br.ccasj.sisauc.sdga.dao.AcaoSDGADAO;
import br.ccasj.sisauc.sdga.domain.AcaoSDGA;

@Transactional
@Repository(value = "acaoSDGADAO")
public class AcaoSDGADAOImpl implements AcaoSDGADAO {

	@PersistenceContext
	protected EntityManager entityManager;
	@Autowired
	private AuthenticationContext authenticationContext;
	
	@Override
	public void merge(AcaoSDGA acao) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String ip = httpServletRequest.getRemoteAddr();
		String host = httpServletRequest.getRemoteHost();
		acao.setDataExecucao(new Date());
		acao.setIp(ip);
		acao.setHost(host);
		acao.setAutor(authenticationContext.getUsuarioLogado());
		entityManager.merge(acao);
	}

}
