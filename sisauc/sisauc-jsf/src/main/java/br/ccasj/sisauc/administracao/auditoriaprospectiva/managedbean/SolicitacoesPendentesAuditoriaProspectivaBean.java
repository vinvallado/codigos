package br.ccasj.sisauc.administracao.auditoriaprospectiva.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.SolicitacaoProcedimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.EstadoSolicitacaoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoProcedimento;

@Scope(value = "view")
@Service(value = "solicitacoesPendentesAuditoriaProspectivaBean")
public class SolicitacoesPendentesAuditoriaProspectivaBean implements Serializable{

	private static final long serialVersionUID = 3994850299075224313L;

	@Autowired
	private SolicitacaoProcedimentoDAO solicitacaoDAO;
	
	private List<SolicitacaoProcedimento> solicitacoes = new ArrayList<SolicitacaoProcedimento>();
	
	@PostConstruct
	private void init(){
		solicitacoes = solicitacaoDAO.listarPorEstado(EstadoSolicitacaoProcedimento.ENVIADA_PARA_AUDITORIA);
	}

	public List<SolicitacaoProcedimento> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(List<SolicitacaoProcedimento> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}
	
	
}
