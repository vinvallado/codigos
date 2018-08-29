package br.ccasj.sisauc.administracao.auditoriaprospectiva.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.SolicitacaoRessarcimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.EstadoSolicitacaoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoRessarcimento;

@Scope(value = "view")
@Service(value = "solicitacoesRessarcimentoPendentesAuditoriaProspectivaBean")
public class SolicitacoesRessarcimentoPendentesAuditoriaProspectivaBean implements Serializable{

	private static final long serialVersionUID = 6874180871034927787L;

	@Autowired
	private SolicitacaoRessarcimentoDAO solicitacaoRessarcimentoDAO;
	
	private List<SolicitacaoRessarcimento> solicitacoes = new ArrayList<SolicitacaoRessarcimento>();
	
	@PostConstruct
	private void init(){
		solicitacoes = solicitacaoRessarcimentoDAO.listarPorEstado(EstadoSolicitacaoProcedimento.ENVIADA_PARA_AUDITORIA);
	}

	public List<SolicitacaoRessarcimento> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(List<SolicitacaoRessarcimento> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}
	
	
}
