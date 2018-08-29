package br.ccasj.sisauc.administracao.cadastro.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.SolicitacaoProcedimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.EstadoSolicitacaoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoProcedimento;
import br.ccasj.sisauc.framework.context.AuthenticationContext;

@Scope(value = "view")
@Service(value = "cadastroSolicitacaoListaBean")
public class CadastroSolicitacaoListaBean implements Serializable {

	private static final long serialVersionUID = -179418544602186372L;

	@Autowired
	private SolicitacaoProcedimentoDAO solicitacaoDAO;
	@Autowired
	private AuthenticationContext authenticationContext;
	
	private List<SolicitacaoProcedimento> solicitacoes;
	
	@PostConstruct
	public void init() {
		solicitacoes = solicitacaoDAO.listarPorEstadoEOrganizacaoMilitar(authenticationContext.getUsuarioLogado().getOrganizacaoMilitar().getId(), EstadoSolicitacaoProcedimento.CRIADA, 
				EstadoSolicitacaoProcedimento.INCONSISTENTE);
	}

	public List<SolicitacaoProcedimento> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(List<SolicitacaoProcedimento> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}
	
}
