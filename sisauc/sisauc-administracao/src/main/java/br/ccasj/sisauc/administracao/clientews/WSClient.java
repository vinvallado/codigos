package br.ccasj.sisauc.administracao.clientews;

import br.ccasj.sisauc.administracao.sistema.domain.ConfiguracaoWSSigpes;
import br.ccasj.sisauc.framework.exception.SisaucException;
import br.ccasj.sisauc.webservices.service.SigpesSincWS;

public interface WSClient {

	SigpesSincWS criarSigpeshWS() throws SisaucException;

	SigpesSincWS criarSigpeshWS(ConfiguracaoWSSigpes wsConfig) throws SisaucException;

}
