package br.ccasj.sisauc.administracao.cadastro.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.beneficiario.dao.BeneficiarioDAO;
import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;

@Scope(value = "view")
@Service(value = "cadastroBeneficiarioFormularioBean")
public class CadastroBeneficiarioFormularioBean implements Serializable{
	
	private static final long serialVersionUID = -7322803090228228123L;

	Beneficiario beneficiario;
	
	@Autowired
	private BeneficiarioDAO beneficiarioDAO;
	
	private List<Beneficiario> dependentesBeneficiario;
	
	@PostConstruct
	private void init() {
		beneficiario = obterBeneficiario();
		dependentesBeneficiario =  obterDependentes(this.beneficiario);
		//oms = organizacaoMilitarDAO.listarOMSAtivasParaCombo();
	}
	
	private Beneficiario obterBeneficiario() {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		return beneficiarioDAO.findById(Integer.valueOf(id));
		
	}
	
	private List<Beneficiario> obterDependentes(Beneficiario beneficiario) {
		return beneficiarioDAO.obterDependentes(beneficiario);
	}

	public Beneficiario getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}

	public List<Beneficiario> getDependentesBeneficiario() {
		return dependentesBeneficiario;
	}
	
}
