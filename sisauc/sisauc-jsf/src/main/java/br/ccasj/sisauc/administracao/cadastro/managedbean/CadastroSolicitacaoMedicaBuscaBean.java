package br.ccasj.sisauc.administracao.cadastro.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.SolicitacaoMedicaDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoMedica;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;

@Scope(value = "view")
@Service(value = "cadastroSolicitacaoMedicaBuscaBean")
public class CadastroSolicitacaoMedicaBuscaBean implements Serializable {

	private static final long serialVersionUID = 7650829599513337365L;

	@Autowired
	private SolicitacaoMedicaDAO<SolicitacaoMedica> solicitacaoMedicaDAO;
	
	private List<SolicitacaoMedica> solicitacoes;
	
	private boolean exibeMsgListaVazia = false;
	
	@PostConstruct
	public void init() {
		solicitacoes = new ArrayList<SolicitacaoMedica>();
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
			solicitacoes = solicitacaoMedicaDAO.buscarSolicitacaoPorCodigo(codigo);
		}
		
		if (!saram.isEmpty()) {
			solicitacoes = solicitacaoMedicaDAO.buscarSolicitacaoPorSaram(saram);
			solicitacoes.addAll(solicitacaoMedicaDAO.buscarSolicitacaoPorSaramTitular(saram));
		}
	
		if (!cpf.isEmpty()) {
			solicitacoes = solicitacaoMedicaDAO.buscarSolicitacaoPorCpf(cpf);
			solicitacoes.addAll(solicitacaoMedicaDAO.buscarSolicitacaoPorCpfTitular(cpf));
		}
		
		if(!solicitacoes.isEmpty())  {
	           exibeMsgListaVazia = true;
	    }else{
	    	exibeMsgListaVazia = true;
	    }
	}
	
	public List<SolicitacaoMedica> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(List<SolicitacaoMedica> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}

	public boolean isExibeMsgListaVazia() {
		return exibeMsgListaVazia;
	}

	public void setExibeMsgListaVazia(boolean exibeMsgListaVazia) {
		this.exibeMsgListaVazia = exibeMsgListaVazia;
	}
	
}
