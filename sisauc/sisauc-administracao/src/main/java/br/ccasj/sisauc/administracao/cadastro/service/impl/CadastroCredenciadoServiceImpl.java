package br.ccasj.sisauc.administracao.cadastro.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.ConfiguracaoEditalCredenciadoDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.CredenciadoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Credenciado;
import br.ccasj.sisauc.administracao.cadastro.domain.TipoPessoa;
import br.ccasj.sisauc.administracao.cadastro.service.CadastroCredenciadoService;
import br.ccasj.sisauc.framework.exception.SisaucException;

@Transactional
@Service(value = "cadastroCredenciadoService")
public class CadastroCredenciadoServiceImpl implements CadastroCredenciadoService{
	
	private static final long serialVersionUID = 687732762681645747L;
	
	@Autowired
	private CredenciadoDAO credenciadoDAO;
	@Autowired
	private ConfiguracaoEditalCredenciadoDAO configuracaoEditalCredenciadoDAO;
	
	//TODO criar um método para ativação e desativação dentro do serviço e tirar o CadastroCredenciadoListaBean
	
	@Override
	public Credenciado salvar(Credenciado credenciado) throws SisaucException  {
		verificarUnicidadeCpfCnpj(credenciado);
		validarPessoa(credenciado);
		validarCEP(credenciado);
		if (!credenciado.isAtivo()){
			configuracaoEditalCredenciadoDAO.verificarSeEditalPossuiCredenciado(credenciado.getId());
		}
		return credenciadoDAO.merge(credenciado);
	}

	private void validarPessoa(Credenciado credenciado) throws SisaucException {
		if(TipoPessoa.PESSOA_FISICA.equals(credenciado.getTipoPessoa()) && credenciado.getCpf() == null){
			throw new SisaucException("O campo CPF é obrigatório");
		} else if(TipoPessoa.PESSOA_JURIDICA.equals(credenciado.getTipoPessoa()) && credenciado.getCnpj() == null){
			throw new SisaucException("O campo CNPJ é obrigatório");
		}
	}
	
	private void validarCEP(Credenciado credenciado) throws SisaucException {
		if(credenciado.getCep() == null || credenciado.getCep().contains("_")){
			throw new SisaucException("O campo CEP é obrigatório");
		}
	}
	
	public void verificarUnicidadeCpfCnpj(Credenciado credenciado) throws SisaucException {
		if (credenciado.getTipoPessoa() == TipoPessoa.PESSOA_FISICA) {
			credenciado.setCnpj(null);
		} else {
			credenciado.setCpf(null);
		}
		if (credenciado.getCpf() != null) {
			if (credenciadoDAO.verificarUnicidadeCPF(credenciado.getId(), credenciado.getCpf())) {
				throw new SisaucException("Este número de CPF já foi cadastrado!");
			}
		} else {
			if (credenciadoDAO.verificarUnicidadeCNPJ(credenciado.getId(), credenciado.getCnpj())) {
				throw new SisaucException("Este número de CNPJ já foi cadastrado!");
			}
		}
	}

}
	