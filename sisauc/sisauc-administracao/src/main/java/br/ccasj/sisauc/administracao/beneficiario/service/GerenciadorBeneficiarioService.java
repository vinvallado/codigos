package br.ccasj.sisauc.administracao.beneficiario.service;

import java.util.List;

import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;

public interface GerenciadorBeneficiarioService {
	public Beneficiario salvar(Beneficiario beneficiario);
	public List<Beneficiario> buscarBeneficiarioSigpes(String cpf, String saram) throws SystemRuntimeException;
}
