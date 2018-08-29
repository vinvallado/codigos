package br.ccasj.sisauc.administracao.cadastro.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.EditalCredenciamentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.EditalCredenciamento;

@Scope(value = "view")
@Service(value = "cadastroConfiguracaoEditalListaBean")
public class CadastroConfiguracaoEditalListaBean implements Serializable {
	
	private static final long serialVersionUID = -6912294106654372334L;

	@Autowired
	private EditalCredenciamentoDAO editalCredenciamentoDAO;

	private List<EditalCredenciamento> editais;

	@PostConstruct
	private void init() {
		editais = editalCredenciamentoDAO.listarEditaisAtivos();
	}

	public List<EditalCredenciamento> getEditais() {
		return editais;
	}

	public void setEditais(List<EditalCredenciamento> editais) {
		this.editais = editais;
	}

}
