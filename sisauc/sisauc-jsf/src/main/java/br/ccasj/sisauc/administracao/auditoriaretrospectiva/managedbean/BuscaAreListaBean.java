package br.ccasj.sisauc.administracao.auditoriaretrospectiva.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.emissaoar.dao.AutorizacaoRessarcimentoDAO;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;

@Scope(value = "view")
@Service(value = "buscaAreListaBean")
public class BuscaAreListaBean implements Serializable {

	private static final long serialVersionUID = -6612314498609512130L;

	@Autowired
	private AutorizacaoRessarcimentoDAO areDAO;

	private List<AutorizacaoRessarcimento> ares;
	
	private boolean exibeMsgListaVazia = false;

	@PostConstruct
	public void init() {
		ares = new ArrayList<AutorizacaoRessarcimento>();
	}

	public void buscarAre() {
		String codigo = ManagedBeanUtils.obterParametroRequest("codigo");
		validarCodigo(codigo);
		ares = areDAO.obterAresPorCodigo(codigo.toUpperCase());
		validarListaARE(ares);
		
		if(!ares.isEmpty())  {
	           exibeMsgListaVazia = true;
	    }else{
	    	exibeMsgListaVazia = true;
	    }
	}

	private void validarCodigo(String codigo) {
		if (codigo == null || codigo == "") {
			throw new SystemRuntimeException("Por favor, insira o Código da ARE para realizar a pesquisa.");
		}
	}
	
	private void validarListaARE(List<AutorizacaoRessarcimento> listaAREs){
		if (listaAREs.isEmpty() || listaAREs == null) {
			throw new SystemRuntimeException("O código inserido não retornou nenhuma ARE. Tente novamente.");
		}
	}

	public List<AutorizacaoRessarcimento> getAres() {
		return ares;
	}

	public void setAres(List<AutorizacaoRessarcimento> ares) {
		this.ares = ares;
	}

	public boolean isExibeMsgListaVazia() {
		return exibeMsgListaVazia;
	}

	public void setExibeMsgListaVazia(boolean exibeMsgListaVazia) {
		this.exibeMsgListaVazia = exibeMsgListaVazia;
	}

	

}
