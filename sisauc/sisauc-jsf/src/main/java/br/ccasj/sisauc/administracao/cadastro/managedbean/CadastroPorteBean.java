package br.ccasj.sisauc.administracao.cadastro.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.PorteDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Porte;
import br.ccasj.sisauc.administracao.cadastro.service.CadastroPorteService;
import br.ccasj.sisauc.framework.faces.Mensagem;

@Scope(value = "view")
@Service(value = "cadastroPorteBean")
public class CadastroPorteBean implements Serializable{

	private static final long serialVersionUID = -4655667657750377800L;

	@Autowired
	private PorteDAO porteDAO;
	@Autowired
	private CadastroPorteService cadastroPorteService;
	
	private Porte porte;
	private List<Porte> portes;
	
	@PostConstruct
	public void init() {
		listar();
	}
	
	private void listar(){
		portes = porteDAO.findAll();
	}
	
	public void editar(Porte porte){
		this.porte = porteDAO.findById(Integer.valueOf(porte.getId()));
	}
	
	public void salvar() {
		cadastroPorteService.salvar(porte);
		Mensagem.informacao("Porte atualizado com sucesso!");
		porte = null;
		listar();
	}
	
	public void cancelar(){
		porte = null;
	}
	
	public List<Porte> getPortes() {
		return portes;
	}

	public void setPortes(List<Porte> portes) {
		this.portes = portes;
	}
	
	public Porte getPorte() {
		return porte;
	}

	public void setPorte(Porte porte) {
		this.porte = porte;
	}
}
