package br.ccasj.sisauc.gab.managedbean;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.emissaogab.dao.GuiaApresentacaoBeneficiarioDAO;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.emissaogab.service.AcompanharGABService;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;

@Scope(value = "view")
@Service(value = "acompanhamentoGABBean")
public class AcompanhamentoGABBean implements Serializable{
	
	private static final long serialVersionUID = -8651827812089122996L;

	@Autowired
	private GuiaApresentacaoBeneficiarioDAO gabDAO;
	@Autowired
	private AcompanharGABService acompanharGABService;
	
	private GuiaApresentacaoBeneficiario gab;
	
	@PostConstruct
	private void init(){
		String id = ManagedBeanUtils.obterParametroRequest("id");
		gab = gabDAO.obterComItensPorId(Integer.valueOf(id));
	}
	
	public void cancelar(){
		acompanharGABService.cancelar(gab.getId(), gab.getJustificativaCancelamentoGab());
		Mensagem.informacao("GAB cancelada com sucesso");
		ManagedBeanUtils.redirecionar("/chefe-funsa/gab/acompanhamento");
	}
	
	public GuiaApresentacaoBeneficiario getGab() {
		return gab;
	}

	public void setGab(GuiaApresentacaoBeneficiario gab) {
		this.gab = gab;
	}
	
	

}
