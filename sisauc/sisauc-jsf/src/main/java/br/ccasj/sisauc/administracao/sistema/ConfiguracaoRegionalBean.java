package br.ccasj.sisauc.administracao.sistema;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.OrganizacaoMilitarRegionalDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.OrganizacaoMilitarSubordinadaDAO;
import br.ccasj.sisauc.administracao.sistema.domain.OrganizacaoMilitarRegional;
import br.ccasj.sisauc.administracao.sistema.domain.OrganizacaoMilitarSubordinada;
import br.ccasj.sisauc.administracao.sistema.service.ConfiguracaoOrganizacaoMilitarRegionalService;
import br.ccasj.sisauc.framework.faces.Mensagem;

@Scope(value = "view")
@Service(value = "configuracaoRegionalBean")
public class ConfiguracaoRegionalBean implements Serializable {

	private static final long serialVersionUID = -4621265743625150097L;

	@Autowired
	private OrganizacaoMilitarRegionalDAO regionalDAO;
	@Autowired
	private OrganizacaoMilitarSubordinadaDAO subordinadaDAO;
	@Autowired
	private ConfiguracaoOrganizacaoMilitarRegionalService configuracaoOrganizacaoMilitarRegionalService;

	private OrganizacaoMilitarRegional regional;
	private List<OrganizacaoMilitarRegional> regionais;
	private List<OrganizacaoMilitarSubordinada> subordinadasSemRegional; 
	private List<OrganizacaoMilitarSubordinada> subordinadasDaRegionalSelecionada;
	private DualListModel<OrganizacaoMilitarSubordinada> dualList = new DualListModel<OrganizacaoMilitarSubordinada>();
	
	@PostConstruct
	private void init() {
		regionais = regionalDAO.listarRegionaisAtivasParaCombo();
		regional = configuracaoOrganizacaoMilitarRegionalService.obterRegionalSistema();
		atualizarRegionalSelecionada();
	}

	public void atualizarRegionalSelecionada() {
		subordinadasSemRegional = subordinadaDAO.listarSubordinadasAtivasSemRegional();
		if (regional != null) {
			subordinadasDaRegionalSelecionada = subordinadaDAO.listarSubordinadasAtivasPorRegional(regional.getId());
			dualList = new DualListModel<OrganizacaoMilitarSubordinada>(subordinadasSemRegional, subordinadasDaRegionalSelecionada);
		} else {
			dualList = new DualListModel<OrganizacaoMilitarSubordinada>();
		}
	}

	public void salvar() {
		configuracaoOrganizacaoMilitarRegionalService.salvar(dualList.getSource(), dualList.getTarget(), regional);
		Mensagem.informacao("Configuração salva com sucesso!");
	}

	public OrganizacaoMilitarRegional getRegional() {
		return regional;
	}

	public void setRegional(OrganizacaoMilitarRegional regional) {
		this.regional = regional;
	}

	public List<OrganizacaoMilitarRegional> getRegionais() {
		return regionais;
	}

	public void setRegionais(List<OrganizacaoMilitarRegional> regionais) {
		this.regionais = regionais;
	}

	public List<OrganizacaoMilitarSubordinada> getSubordinadasSemRegional() {
		return subordinadasSemRegional;
	}

	public void setSubordinadasSemRegional(List<OrganizacaoMilitarSubordinada> subordinadasSemRegional) {
		this.subordinadasSemRegional = subordinadasSemRegional;
	}

	public List<OrganizacaoMilitarSubordinada> getSubordinadasDaRegionalSelecionada() {
		return subordinadasDaRegionalSelecionada;
	}

	public void setSubordinadasDaRegionalSelecionada(List<OrganizacaoMilitarSubordinada> subordinadasDaRegionalSelecionada) {
		this.subordinadasDaRegionalSelecionada = subordinadasDaRegionalSelecionada;
	}

	public DualListModel<OrganizacaoMilitarSubordinada> getDualList() {
		return dualList;
	}

	public void setDualList(DualListModel<OrganizacaoMilitarSubordinada> dualList) {
		this.dualList = dualList;
	}

}
