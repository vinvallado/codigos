package br.ccasj.sisauc.administracao.cadastro.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.SolicitacaoProcedimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoProcedimento;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;

@Scope(value = "view")
@Service(value = "cadastroSolicitacaoBuscaBean")
public class CadastroSolicitacaoBuscaBean implements Serializable {

	private static final long serialVersionUID = -179418544602186372L;

	@Autowired
	private SolicitacaoProcedimentoDAO solicitacaoDAO;
	
	private List<SolicitacaoProcedimento> solicitacoes;
	
	@PostConstruct
	public void init() {
		solicitacoes = new ArrayList<SolicitacaoProcedimento>();
	}
	
	public void buscarSolicitacaoSaram() {
		String codigo = ManagedBeanUtils.obterParametroRequest("codigo");
		codigo = codigo.toUpperCase();
		String saram =  ManagedBeanUtils.obterParametroRequest("saram");
		String cpf =  ManagedBeanUtils.obterParametroRequest("cpf");
		
		if ( ( (!codigo.isEmpty()) && (!saram.isEmpty() || !cpf.isEmpty() ) || ( !cpf.isEmpty() && !saram.isEmpty() ) )  ) {
			throw new SystemRuntimeException("Por favor, insira apenas o Código da Solicitação,  CPF ou o SARAM do Títular");
		}
		
		if (!codigo.isEmpty()) {
			solicitacoes = solicitacaoDAO.buscarSolicitacaoPorCodigo(codigo);
		}
		
		if (!saram.isEmpty()) {
			solicitacoes = solicitacaoDAO.buscarSolicitacaoPorSaram(saram);
			solicitacoes.addAll(solicitacaoDAO.buscarSolicitacaoPorSaramTitular(saram));
		}
	
		if (!cpf.isEmpty()) {
			solicitacoes = solicitacaoDAO.buscarSolicitacaoPorCpf(cpf);
			solicitacoes.addAll(solicitacaoDAO.buscarSolicitacaoPorCpfTitular(cpf));
		}
	}

	public List<SolicitacaoProcedimento> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(List<SolicitacaoProcedimento> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}
	
}
