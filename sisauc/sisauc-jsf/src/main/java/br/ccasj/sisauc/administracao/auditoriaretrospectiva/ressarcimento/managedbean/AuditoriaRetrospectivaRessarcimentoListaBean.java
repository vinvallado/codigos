package br.ccasj.sisauc.administracao.auditoriaretrospectiva.ressarcimento.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.emissaoar.dao.AutorizacaoRessarcimentoDAO;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.EstadoAR;

@Scope(value = "view")
@Service(value = "auditoriaRetrospectivaRessarcimentoListaBean")
public class AuditoriaRetrospectivaRessarcimentoListaBean implements Serializable {

	private static final long serialVersionUID = -4436117369223352855L;

	@Autowired
	private transient AutorizacaoRessarcimentoDAO autorizacaoRessarcimentoDAO;

	private List<AutorizacaoRessarcimento> listaARs;
		
	@PostConstruct
	private void init() {
		this.listaARs = autorizacaoRessarcimentoDAO.listarPorEstados(EstadoAR.APRESENTADA, EstadoAR.EM_AUDITORIA);
	}

	public List<AutorizacaoRessarcimento> getListaARs() {
		return listaARs;
	}

	public void setListaARs(List<AutorizacaoRessarcimento> listaARs) {
		this.listaARs = listaARs;
	}
	
	 
}
