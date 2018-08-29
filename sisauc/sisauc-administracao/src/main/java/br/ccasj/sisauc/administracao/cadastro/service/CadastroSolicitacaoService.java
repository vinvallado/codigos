package br.ccasj.sisauc.administracao.cadastro.service;

import java.io.Serializable;

import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoProcedimento;

public interface CadastroSolicitacaoService extends Serializable {

	public void salvar(SolicitacaoProcedimento solicitacao);

	public SolicitacaoProcedimento enviarAuditoria(SolicitacaoProcedimento solicitacao) throws Exception;

	public void verificarSolicitacaoPossuiArquivo(SolicitacaoProcedimento solicitacao);
	
}
