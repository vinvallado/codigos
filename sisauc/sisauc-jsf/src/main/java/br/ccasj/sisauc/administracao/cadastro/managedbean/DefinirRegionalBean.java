package br.ccasj.sisauc.administracao.cadastro.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.OrganizacaoMilitarRegionalDAO;
import br.ccasj.sisauc.administracao.sistema.domain.OrganizacaoMilitarRegional;
import br.ccasj.sisauc.administracao.sistema.service.ConfiguracaoOrganizacaoMilitarRegionalService;
import br.ccasj.sisauc.framework.faces.Mensagem;

@Scope(value = "view")
@Service(value = "definirRegionalBean")
public class DefinirRegionalBean implements Serializable {

	private static final long serialVersionUID = -5546334531401563390L;

	@Autowired
	private OrganizacaoMilitarRegionalDAO organizacaoMilitarRegionalDAO;
	@Autowired
	private ConfiguracaoOrganizacaoMilitarRegionalService configuracaoOrganizacaoMilitarRegionalService;
	
	private List<OrganizacaoMilitarRegional> regionais = new ArrayList<OrganizacaoMilitarRegional>();
	private OrganizacaoMilitarRegional regional;

	@PostConstruct
	private void init(){
		regionais = organizacaoMilitarRegionalDAO.listarRegionaisAtivasParaCombo();
	}
	
	public void salvar(){
		configuracaoOrganizacaoMilitarRegionalService.definirRegionalSistema(regional.getId());
		Mensagem.informacao("Regional definida corretamente");
	}
	
	public List<OrganizacaoMilitarRegional> getRegionais() {
		return regionais;
	}

	public void setRegionais(List<OrganizacaoMilitarRegional> regionais) {
		this.regionais = regionais;
	}

	public OrganizacaoMilitarRegional getRegional() {
		return regional;
	}

	public void setRegional(OrganizacaoMilitarRegional regional) {
		this.regional = regional;
	}

}
