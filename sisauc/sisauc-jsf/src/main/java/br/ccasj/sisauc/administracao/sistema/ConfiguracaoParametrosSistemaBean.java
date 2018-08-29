package br.ccasj.sisauc.administracao.sistema;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.service.ConfiguracaoParametrosSistemaService;
import br.ccasj.sisauc.administracao.sistema.dao.ConfiguracaoSistemaDao;
import br.ccasj.sisauc.administracao.sistema.domain.ConfiguracaoSistema;
import br.ccasj.sisauc.framework.faces.Mensagem;

@Scope(value = "view")
@Service(value = "configuracaoParametrosSistemaBean")
public class ConfiguracaoParametrosSistemaBean implements Serializable {

	private static final long serialVersionUID = 8418562147871278848L;
	
	@Autowired
	private ConfiguracaoSistemaDao configuracaoSistemaDao;	
	@Autowired
	private ConfiguracaoParametrosSistemaService configuracaoBloquearEmissaoGabService;

	private ConfiguracaoSistema configuracaoSistema;
	
	@PostConstruct
	private void init() {
		configuracaoSistema = configuracaoSistemaDao.obterConfiguracaoSistema();
		if (configuracaoSistema == null) {
			configuracaoSistema = new ConfiguracaoSistema();
		}
	}
	
	public void salvar() {
		configuracaoBloquearEmissaoGabService.salvar(configuracaoSistema);
		Mensagem.informacao("Configuração salva com sucesso!");
		configuracaoSistema = configuracaoSistemaDao.obterConfiguracaoSistema();
	}

	public ConfiguracaoSistema getConfiguracaoSistema() {
		return configuracaoSistema;
	}

	public void setConfiguracaoSistema(ConfiguracaoSistema configuracaoSistema) {
		this.configuracaoSistema = configuracaoSistema;
	}

}
