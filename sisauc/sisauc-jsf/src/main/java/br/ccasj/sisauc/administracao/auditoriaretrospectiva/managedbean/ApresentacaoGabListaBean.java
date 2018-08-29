package br.ccasj.sisauc.administracao.auditoriaretrospectiva.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.emissaogab.dao.GuiaApresentacaoBeneficiarioDAO;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;

@Scope(value = "view")
@Service(value = "apresentacaoGabListaBean")
public class ApresentacaoGabListaBean implements Serializable {

	private static final long serialVersionUID = 4250957745116339146L;

	private List<GuiaApresentacaoBeneficiario> listaGabImpressas;
	
	@Autowired
	private GuiaApresentacaoBeneficiarioDAO guiaApresentacaoBeneficiarioDAO;

	@PostConstruct
	private void init() {
		listaGabImpressas = guiaApresentacaoBeneficiarioDAO.obterGABsEmitidas();
	}
	
	public List<GuiaApresentacaoBeneficiario> getListaGabImpressas() {
		return listaGabImpressas;
	}

	public void setListaGabImpressas(
			List<GuiaApresentacaoBeneficiario> listaGabImpressas) {
		this.listaGabImpressas = listaGabImpressas;
	}
	
}
