package br.ccasj.sisauc.administracao.cadastro.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.ConfiguracaoEditalCredenciadoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciado;
import br.ccasj.sisauc.administracao.cadastro.service.CadastroConfiguracaoEditalCredenciadoService;
import br.ccasj.sisauc.administracao.historico.domain.HistoricoValorCadastroEditalCredenciado;
import br.ccasj.sisauc.framework.dao.GenericEntityHistoricoDAO;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.framework.faces.Mensagem;

@Transactional
@Service(value = "cadastroConfiguracaoEditalCredenciadoService")
public class CadastroConfiguracaoEditalCredenciadoServiceImpl implements CadastroConfiguracaoEditalCredenciadoService {

	private static final long serialVersionUID = 1901236125226401443L;

	@Autowired
	private ConfiguracaoEditalCredenciadoDAO configuracaoEditalCredenciadoDAO;
	
	@Autowired
	private GenericEntityHistoricoDAO<HistoricoValorCadastroEditalCredenciado> genericEntityHistoricoDAO;
	
	//TODO ainda falta fazer o histórico aqui e no cadastro de edital (Alterar nomes dos históricos) - Záccaro
	@Override
	public void salvar(ConfiguracaoEditalCredenciado configuracaoEditalCredenciado) {
		verificarCredenciado(configuracaoEditalCredenciado);
		verificarUniciadeConfiguracaoAtiva(configuracaoEditalCredenciado);
		if(configuracaoEditalCredenciado.getId() == null){
			configuracaoEditalCredenciado.setDataInsercao(new Date());
		}
		if (!(configuracaoEditalCredenciado.getIndiceReajusteAuxiliares() < -100 || configuracaoEditalCredenciado.getIndiceReajusteCustoOperacional() < -100 || configuracaoEditalCredenciado.getIndiceReajustePorte() < -100 || configuracaoEditalCredenciado.getIndiceReajustePorteAnestesico() < -100 )) {
			ConfiguracaoEditalCredenciado configuracaoSalva = configuracaoEditalCredenciadoDAO.merge(configuracaoEditalCredenciado);
			genericEntityHistoricoDAO.merge(new HistoricoValorCadastroEditalCredenciado(configuracaoEditalCredenciado.getIndiceReajustePorte(), configuracaoEditalCredenciado.getIndiceReajustePorteAnestesico(), configuracaoEditalCredenciado.getIndiceReajusteCustoOperacional(),configuracaoEditalCredenciado.getCredenciado(), configuracaoSalva));			
			Mensagem.informacao("Credenciado adicionado com sucesso!");
		} else {
			throw new SystemRuntimeException("Os valores deflatores não devem ser menores que -100%.");
		}
	}

	private void verificarUniciadeConfiguracaoAtiva(ConfiguracaoEditalCredenciado configuracaoEditalCredenciado) {
		if(configuracaoEditalCredenciadoDAO.verificarPresencaConfiguracaoComCredenciadoAtivoPorEdital(configuracaoEditalCredenciado.getId(), configuracaoEditalCredenciado.getCredenciado().getId(), configuracaoEditalCredenciado.getEdital().getId())){
			throw new SystemRuntimeException("Este credenciado já foi cadastrado anteriormente neste edital");
		}
	}

	@Override
	public void remover(Integer id) {
		ConfiguracaoEditalCredenciado configuracao = configuracaoEditalCredenciadoDAO.findById(id);
		configuracao.setAtivo(false);
		configuracao.setDataExclusao(new Date());
		configuracaoEditalCredenciadoDAO.merge(configuracao);
	}

	private void verificarCredenciado(ConfiguracaoEditalCredenciado configuracaoEditalCredenciado) {
		if (configuracaoEditalCredenciado.getCredenciado() == null) {
			throw new SystemRuntimeException("É necessário cadastrar um credenciado");
		}
	}
	


}
