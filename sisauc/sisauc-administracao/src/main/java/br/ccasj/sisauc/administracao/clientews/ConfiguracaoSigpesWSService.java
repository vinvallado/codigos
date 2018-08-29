package br.ccasj.sisauc.administracao.clientews;

import br.ccasj.sisauc.administracao.sistema.domain.ConfiguracaoWSSigpes;

public interface ConfiguracaoSigpesWSService {

	void testarConexao(ConfiguracaoWSSigpes configuracaoWSSigpes);
	void salvar(ConfiguracaoWSSigpes configuracaoWSSigpes);

}
