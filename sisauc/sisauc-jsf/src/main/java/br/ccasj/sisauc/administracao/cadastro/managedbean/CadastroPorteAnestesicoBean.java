package br.ccasj.sisauc.administracao.cadastro.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.PorteAnestesicoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.PorteAnestesico;
import br.ccasj.sisauc.administracao.cadastro.service.CadastroPorteAnestesicoService;
import br.ccasj.sisauc.framework.faces.Mensagem;

@Scope(value = "view")
@Service(value = "cadastroPorteAnestesicoBean")
public class CadastroPorteAnestesicoBean implements Serializable{

	private static final long serialVersionUID = 7319053846815309679L;

	@Autowired
	private PorteAnestesicoDAO porteAnestesicoDAO;
	@Autowired
	private CadastroPorteAnestesicoService cadastroPorteAnestesicoService;
	
	private PorteAnestesico porteAnestesico;
	private List<PorteAnestesico> portesAnestesicos;
	
	@PostConstruct
	public void init() {
		listar();
	}
	
	private void listar(){
		portesAnestesicos = porteAnestesicoDAO.findAll();
	}

	public void editar(PorteAnestesico porte){
		this.porteAnestesico = porteAnestesicoDAO.findById(Integer.valueOf(porte.getId()));
	}
	
	public void salvar() {
		cadastroPorteAnestesicoService.salvar(porteAnestesico);
		Mensagem.informacao("Porte Anestésico atualizado com sucesso!");
		porteAnestesico = null;
		listar();
	}
	
	public void cancelar(){
		porteAnestesico = null;
	}
	
	public List<PorteAnestesico> getPortesAnestesicos() {
		return portesAnestesicos;
	}

	public void setPortesAnestesicos(List<PorteAnestesico> portesAnestesicos) {
		this.portesAnestesicos = portesAnestesicos;
	}
	
	public PorteAnestesico getPorteAnestesico() {
		return porteAnestesico;
	}

	public void setPorte(PorteAnestesico porteAnestesico) {
		this.porteAnestesico = porteAnestesico;
	}
}
