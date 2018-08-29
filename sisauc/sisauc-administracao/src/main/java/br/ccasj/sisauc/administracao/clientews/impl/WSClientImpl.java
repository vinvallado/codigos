package br.ccasj.sisauc.administracao.clientews.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.clientews.WSClient;
import br.ccasj.sisauc.administracao.sistema.dao.ConfiguracaoWSSigpesDAO;
import br.ccasj.sisauc.administracao.sistema.domain.ConfiguracaoWSSigpes;
import br.ccasj.sisauc.framework.exception.SisaucException;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.webservices.service.SigpesSincWS;

@Service(value = "wsClient")
public class WSClientImpl implements WSClient {

	@Autowired
	ConfiguracaoWSSigpesDAO configuracaoWSSigpesDAO;
	
	private static final String TAG_USUARIO = "Username";
	private static final String TAG_SENHA = "Password";
	
	@Override
	public SigpesSincWS criarSigpeshWS() throws SisaucException {
		ConfiguracaoWSSigpes wsConfig = configuracaoWSSigpesDAO.obterConfiguracoesWSSigpes();
		return criarSigpeshWS(wsConfig);
		
	}
	
	@Override
	public SigpesSincWS criarSigpeshWS(ConfiguracaoWSSigpes wsConfig) throws SisaucException {
		URL url = null;
		SigpesSincWS sigpesSincWS = null;
		try {
			url = new URL(wsConfig.getUrlWSDL());

			QName qname = new QName(wsConfig.getUrlServico(),wsConfig.getNomeServico());

			javax.xml.ws.Service service = javax.xml.ws.Service.create(url, qname);
			sigpesSincWS = service.getPort(SigpesSincWS.class);
			BindingProvider provider = (BindingProvider) sigpesSincWS;
			Map<String, Object> req_ctx = provider.getRequestContext();
			req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsConfig.getUrlWSDL());
			Map<String, List<String>> headers = criarCabecalho(wsConfig);
			req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
		}catch (WebServiceException e){
			Mensagem.erro("Não foi possível acessar o WebService! Não foi possível acessar o WSDL no endereço configurado.");
			e.printStackTrace();
			throw new SisaucException(e.getMessage());
		} catch (MalformedURLException e) {
			Mensagem.erro("Não foi possível acessar o WebService! Problema na configuração da URL do Serviço.");
			e.printStackTrace();
			throw new SisaucException(e.getMessage());
		}


		return sigpesSincWS;
	}

	private Map<String, List<String>> criarCabecalho(ConfiguracaoWSSigpes wsConfig) {
		Map<String, List<String>> headers =  new HashMap<String, List<String>>();
		headers.put(TAG_USUARIO, Collections.singletonList(wsConfig.getUsuario()));
		headers.put(TAG_SENHA, Collections.singletonList(wsConfig.getSenha()));
		return headers;
	}

}
