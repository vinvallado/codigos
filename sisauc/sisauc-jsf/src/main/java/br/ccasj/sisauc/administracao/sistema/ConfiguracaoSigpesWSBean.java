package br.ccasj.sisauc.administracao.sistema;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.clientews.ConfiguracaoSigpesWSService;
import br.ccasj.sisauc.administracao.sistema.dao.ConfiguracaoWSSigpesDAO;
import br.ccasj.sisauc.administracao.sistema.domain.ConfiguracaoWSSigpes;
import br.ccasj.sisauc.framework.faces.Mensagem;

@Scope(value = "view")
@Service(value = "configuracaoSigpesWSBean")
public class ConfiguracaoSigpesWSBean  implements Serializable {

	private static final long serialVersionUID = 7022471766353926379L;

	ConfiguracaoWSSigpes configuracaoWSSigpes;

	@Autowired
	private ConfiguracaoWSSigpesDAO configuracaoWSSigpesDAO;
	@Autowired
	private ConfiguracaoSigpesWSService configuracaoSigpesWSService;

	@PostConstruct
	private void init() {
		configuracaoWSSigpes = configuracaoWSSigpesDAO.obterConfiguracoesWSSigpes();
	}

	public void salvar() {
		configuracaoSigpesWSService.salvar(configuracaoWSSigpes);
		Mensagem.informacao("Configuração salva com sucesso!");
	}
	
	public void testarConexao() {
		configuracaoSigpesWSService.testarConexao(configuracaoWSSigpes);
		Mensagem.informacao("Conexão efetuada com sucesso.");
	}

	public ConfiguracaoWSSigpes getConfiguracaoWSSigpes() {
		return configuracaoWSSigpes;
	}
	
	public void setConfiguracaoWSSigpes(
			ConfiguracaoWSSigpes configuracaoWSSigpes) {
		this.configuracaoWSSigpes = configuracaoWSSigpes;
	}

}
