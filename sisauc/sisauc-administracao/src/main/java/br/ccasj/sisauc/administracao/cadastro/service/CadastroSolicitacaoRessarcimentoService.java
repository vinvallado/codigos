package br.ccasj.sisauc.administracao.cadastro.service;

import java.io.Serializable;

import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoRessarcimento;

public interface CadastroSolicitacaoRessarcimentoService extends Serializable {

	public SolicitacaoRessarcimento salvar(SolicitacaoRessarcimento solicitacao);

	public SolicitacaoRessarcimento enviarParaAuditoria(SolicitacaoRessarcimento solicitacao);

}
