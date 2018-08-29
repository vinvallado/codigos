package br.ccasj.sisauc.administracao.clientews.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.clientews.ConfiguracaoSigpesWSService;
import br.ccasj.sisauc.administracao.clientews.WSClient;
import br.ccasj.sisauc.administracao.historico.domain.HistoricoConfiguracaoWSSigpes;
import br.ccasj.sisauc.administracao.sistema.dao.ConfiguracaoWSSigpesDAO;
import br.ccasj.sisauc.administracao.sistema.domain.ConfiguracaoWSSigpes;
import br.ccasj.sisauc.framework.dao.GenericEntityHistoricoDAO;
import br.ccasj.sisauc.framework.exception.SisaucException;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.integracao.entity.MensagemRetornoDTO;
import br.ccasj.sisauc.integracao.entity.MensagemRetornoDTO.Status;
import br.ccasj.sisauc.webservices.service.SigpesSincWS;

@Service("configuracaoSigpesWSService")
public class ConfiguracaoSigpesWSServiceImpl implements ConfiguracaoSigpesWSService {

	@Autowired
	private WSClient wsClient;
	@Autowired
	private ConfiguracaoWSSigpesDAO configuracaoWSSigpesDAO;
	@Autowired
	private GenericEntityHistoricoDAO<HistoricoConfiguracaoWSSigpes> genericEntityHistoricoDAO;
	
	@Override
	public void salvar(ConfiguracaoWSSigpes configuracaoWSSigpes) {
		configuracaoWSSigpesDAO.salvar(configuracaoWSSigpes);
		genericEntityHistoricoDAO.merge(new HistoricoConfiguracaoWSSigpes(configuracaoWSSigpes));
	}
	
	@Override
	public void testarConexao(ConfiguracaoWSSigpes configuracaoWSSigpes) {
		try {
			SigpesSincWS sigpesSincWS = wsClient.criarSigpeshWS(configuracaoWSSigpes);
			MensagemRetornoDTO mensagemRetorno = sigpesSincWS.buscarPacienteTitularCPF("22222222222");
			if (mensagemRetorno.getStatusRetorno().equals(Status.ACESSO_NEGADO)){
				throw new SystemRuntimeException("Erro no Acesso ao Serviço. Consulte o Administrador! Não foi possível acessar o WebService Sisauc-Sigpes! Acesso Negado.");
			}
		} catch (SisaucException e) {
			throw new SystemRuntimeException(e.getMessage());
		}
	}


}
