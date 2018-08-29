package br.ccasj.sisauc.administracao.sistema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.beneficiario.service.SincronizadorBeneficiarioService;
import br.ccasj.sisauc.intendencia.domain.service.DescontoBeneficiariosService;

@Deprecated
@Service(value = "verificacaoAgendamentoService")
public class VerificacaoAgendamentoServiceImpl implements VerificacaoAgendamentoService {

	@Autowired
	DescontoBeneficiariosService descontoBeneficiariosService;
	
	@Autowired
	SincronizadorBeneficiarioService sincronizadorBeneficiarioService;
	 
	@Override
	public void verificarTarefaAgendada() {
		descontoBeneficiariosService.verificarEnvioDescontos();
	}

}
