package br.ccasj.sisauc.administracao.cadastro.managedbean;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.OrganizacaoMilitarDAO;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;
import br.ccasj.sisauc.framework.faces.Mensagem;

@Scope(value = "view")
@Service(value = "cadastroOrganizacaoMilitarListaBean")
public class CadastroOrganizacaoMilitarListaBean {
	
	@Autowired
	private OrganizacaoMilitarDAO organizacaoMilitarDAO;
	
	private List<OrganizacaoMilitar> oms;
	
	@PostConstruct
	public void init(){
		oms = organizacaoMilitarDAO.listarOMSAtivasParaCombo();
	}
	
	public void mudarStatusAtivo(Integer id, boolean status){
		organizacaoMilitarDAO.definirStatusAtivo(id, status);
		String msg = status ? "ativada" : "desativada";
		Mensagem.informacao("OM " + msg + " com sucesso!");
		init();
	}

	public List<OrganizacaoMilitar> getOms() {
		return oms;
	}

	public void setOms(List<OrganizacaoMilitar> oms) {
		this.oms = oms;
	}

}
