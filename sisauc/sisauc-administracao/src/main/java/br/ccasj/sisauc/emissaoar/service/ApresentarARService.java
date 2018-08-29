package br.ccasj.sisauc.emissaoar.service;

import br.ccasj.sisauc.emissaoar.domain.ApresentacaoAutorizacaoRessarcimento;

public interface ApresentarARService {

	void apresentar(ApresentacaoAutorizacaoRessarcimento apresentacao);

	void configurarArquivos(ApresentacaoAutorizacaoRessarcimento apresentacao);

}
