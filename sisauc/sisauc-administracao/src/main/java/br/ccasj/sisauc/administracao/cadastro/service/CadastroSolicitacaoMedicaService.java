package br.ccasj.sisauc.administracao.cadastro.service;

import java.io.Serializable;

import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoMedica;

public interface CadastroSolicitacaoMedicaService extends Serializable {

	public SolicitacaoMedica salvar(SolicitacaoMedica solicitacao);
	public SolicitacaoMedica enviarParaAuditoria(SolicitacaoMedica solicitacao);

}
