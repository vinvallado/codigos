package br.ccasj.sisauc.administracao.sistema;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.service.ConfiguracaoUnidadeMultiplicadoraService;
import br.ccasj.sisauc.administracao.sistema.dao.UnidadeMultiplicadoraDao;
import br.ccasj.sisauc.administracao.sistema.domain.UnidadeMultiplicadora;
import br.ccasj.sisauc.framework.faces.Mensagem;

@Scope(value = "view")
@Service(value = "configuracaoUnidadeMultiplicadoraBean")
public class ConfiguracaoUnidadeMultiplicadoraBean implements Serializable {

	private static final long serialVersionUID = 8418562147871278848L;
	
	@Autowired
	private UnidadeMultiplicadoraDao unidadeMultiplicadoraDao;
	@Autowired
	private ConfiguracaoUnidadeMultiplicadoraService configuracaoUnidadeMultiplicadoraService;
	
	private UnidadeMultiplicadora unidadeMultiplicadora;
	
	@PostConstruct
	private void init() {
		unidadeMultiplicadora = unidadeMultiplicadoraDao.obterUnidadeMultiplicadora();
		if(unidadeMultiplicadora == null){
			unidadeMultiplicadora = new UnidadeMultiplicadora();
		}
	}
	
	public void salvar() {
		configuracaoUnidadeMultiplicadoraService.salvar(unidadeMultiplicadora);
		Mensagem.informacao("Unidade atualizada com sucesso!");
		unidadeMultiplicadora = unidadeMultiplicadoraDao.obterUnidadeMultiplicadora();
	}

	public UnidadeMultiplicadora getUnidadeMultiplicadora() {
		return unidadeMultiplicadora;
	}

	public void setUnidadeMultiplicadora(UnidadeMultiplicadora unidadeMultiplicadora) {
		this.unidadeMultiplicadora = unidadeMultiplicadora;
	}

}
