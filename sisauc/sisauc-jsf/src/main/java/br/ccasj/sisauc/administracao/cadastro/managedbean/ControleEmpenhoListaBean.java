package br.ccasj.sisauc.administracao.cadastro.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.EmpenhoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Empenho;

@Scope(value = "view")
@Service(value = "controleEmpenhoListaBean")
public class ControleEmpenhoListaBean implements Serializable{

	private static final long serialVersionUID = 2433803029337632262L;

	@Autowired
	private EmpenhoDAO empenhoDAO;
	
	
	private List<Empenho> empenhos;
	
	@PostConstruct
	public void init() {
		empenhos = empenhoDAO.obterEmpenhos();
	}
	
	public List<Empenho> getEmpenhos() {
		return empenhos;
	}
	
	public void setEmpenhos(List<Empenho> empenhos) {
		this.empenhos = empenhos;
	}

}
