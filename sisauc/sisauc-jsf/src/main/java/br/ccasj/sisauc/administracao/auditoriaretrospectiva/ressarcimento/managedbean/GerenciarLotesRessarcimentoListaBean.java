package br.ccasj.sisauc.administracao.auditoriaretrospectiva.ressarcimento.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.auditoriaretrospectiva.dao.GerenciarLoteRessarcimentoDAO;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.LoteRessarcimento;

@Scope(value = "view")
@Service(value = "gerenciarLotesRessarcimentoListaBean")
public class GerenciarLotesRessarcimentoListaBean implements Serializable {

	private static final long serialVersionUID = -3531462563224038294L;

	@Autowired
	private GerenciarLoteRessarcimentoDAO loteRessarcimentoDao;

	private List<LoteRessarcimento> lotesRessarcimento;
	
	@PostConstruct
	private void init() {
		setLotesRessarcimento(loteRessarcimentoDao.findAll());
	}

	public List<LoteRessarcimento> getLotesRessarcimento() {
		return lotesRessarcimento;
	}

	public void setLotesRessarcimento(List<LoteRessarcimento> lotesRessarcimento) {
		this.lotesRessarcimento = lotesRessarcimento;
	}

	

}
