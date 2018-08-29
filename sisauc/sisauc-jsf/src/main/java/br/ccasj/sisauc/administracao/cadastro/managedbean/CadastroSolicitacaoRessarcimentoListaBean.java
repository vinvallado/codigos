package br.ccasj.sisauc.administracao.cadastro.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.SolicitacaoRessarcimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.EstadoSolicitacaoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoRessarcimento;
import br.ccasj.sisauc.framework.context.AuthenticationContext;

@Scope(value = "view")
@Service(value = "cadastroSolicitacaoRessarcimentoListaBean")
public class CadastroSolicitacaoRessarcimentoListaBean implements Serializable {

	private static final long serialVersionUID = 5365081021760143749L;
	
	@Autowired
	private SolicitacaoRessarcimentoDAO solicitacaoRessarcimentoDAO;
	@Autowired
	private AuthenticationContext authenticationContext;
	
	private List<SolicitacaoRessarcimento> solicitacaoRessarcimentos;

	@PostConstruct
	public void init(){
		solicitacaoRessarcimentos = solicitacaoRessarcimentoDAO.listarPorEstadoEOrganizacaoMilitar(authenticationContext.getUsuarioLogado().getOrganizacaoMilitar().getId(),EstadoSolicitacaoProcedimento.CRIADA,
				EstadoSolicitacaoProcedimento.INCONSISTENTE);
	}
	
	public List<SolicitacaoRessarcimento> getSolicitacaoRessarcimentos() {
		return solicitacaoRessarcimentos;
	}

	public void setSolicitacaoRessarcimentos(List<SolicitacaoRessarcimento> solicitacaoRessarcimentos) {
		this.solicitacaoRessarcimentos = solicitacaoRessarcimentos;
	}
	
	
}
