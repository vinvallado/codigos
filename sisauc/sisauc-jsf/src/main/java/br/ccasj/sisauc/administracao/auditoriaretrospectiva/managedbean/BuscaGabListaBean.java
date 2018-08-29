package br.ccasj.sisauc.administracao.auditoriaretrospectiva.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.emissaogab.dao.GuiaApresentacaoBeneficiarioDAO;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;

@Scope(value = "view")
@Service(value = "buscaGabListaBean")
public class BuscaGabListaBean implements Serializable {

	private static final long serialVersionUID = 8018720287475672994L;

	@Autowired
	private GuiaApresentacaoBeneficiarioDAO gabDAO;

	private List<GuiaApresentacaoBeneficiario> gabs;
	
	private boolean exibeMsgListaVazia = false;

	@PostConstruct
	public void init() {
		gabs = new ArrayList<GuiaApresentacaoBeneficiario>();
	}

	public void buscarGab() {
		String codigo = ManagedBeanUtils.obterParametroRequest("codigo");
		validarCodigo(codigo);
		gabs = gabDAO.obterGabsPorCodigo(codigo.toUpperCase());
		validarListaGAB(gabs);
		
		if(!gabs.isEmpty())  {
	           exibeMsgListaVazia = true;
	    }else{
	    	exibeMsgListaVazia = true;
	    }
	}

	private void validarCodigo(String codigo) {
		if (codigo == null || codigo == "") {
			throw new SystemRuntimeException("Por favor, insira o Código da GAB para realizar a pesquisa.");
		}
	}
	
	private void validarListaGAB(List<GuiaApresentacaoBeneficiario> listaGABs){
		if (listaGABs.isEmpty() || listaGABs == null) {
			throw new SystemRuntimeException("O código inserido não retornou nenhuma GAB. Tente novamente.");
		}
	}

	public List<GuiaApresentacaoBeneficiario> getGabs() {
		return gabs;
	}

	public void setGabs(List<GuiaApresentacaoBeneficiario> gabs) {
		this.gabs = gabs;
	}

	public boolean isExibeMsgListaVazia() {
		return exibeMsgListaVazia;
	}

	public void setExibeMsgListaVazia(boolean exibeMsgListaVazia) {
		this.exibeMsgListaVazia = exibeMsgListaVazia;
	}

}
