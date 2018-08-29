package br.ccasj.sisauc.administracao.beneficiario.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.beneficiario.dao.BeneficiarioDAO;
import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.administracao.beneficiario.domain.HistoricoSincronizacaoBeneficiario;
import br.ccasj.sisauc.administracao.beneficiario.service.GerenciadorBeneficiarioService;
import br.ccasj.sisauc.administracao.clientews.BeneficiarioWSClient;
import br.ccasj.sisauc.framework.exception.SisaucException;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.integracao.entity.PacienteDTO;

//@Transactional
@Service(value = "gerenciadorBeneficiarioService")
public class GerenciadorBeneficiarioServiceImpl implements GerenciadorBeneficiarioService {

	@Autowired
	BeneficiarioWSClient beneficiarioWSClient;

	@Autowired
	private BeneficiarioDAO beneficiarioDAO;
	
//	@Autowired
//	private HistoricoSincronizacaoBeneficiarioDAO historicoSincronizacaoBeneficiarioDAO;
//
//	@Autowired
//	private OrganizacaoMilitarDAO organizacaoMilitarDAO;
	
	HistoricoSincronizacaoBeneficiario historicoSincronizacao;
	
	@Autowired
	private GerenciadorBeneficiarioUtil gerenciadorBeneficiarioUtil;
	

	@Override
	public Beneficiario salvar(Beneficiario beneficiario) {
		return beneficiarioDAO.merge(beneficiario);
	}

	@Override
	public List<Beneficiario> buscarBeneficiarioSigpes(String cpf, String saram) throws SystemRuntimeException {

		List<Beneficiario> retorno = new ArrayList<Beneficiario>();
		PacienteDTO pacienteDTO = null;
		try {
			if (cpf != null && !cpf.isEmpty()) {
				pacienteDTO = beneficiarioWSClient.obterPacienteCPF(cpf);
				retorno = gerenciadorBeneficiarioUtil.converterPacienteDTOBeneficiario(pacienteDTO);
			} else if (saram != null && !saram.isEmpty()) {
				pacienteDTO = beneficiarioWSClient.obterPacienteSaram(saram);
				retorno = gerenciadorBeneficiarioUtil.converterPacienteDTOBeneficiario(pacienteDTO);
			} 
		} catch (SisaucException e) {
			e.printStackTrace();
			throw new SystemRuntimeException("Erro no Acesso ao Serviço. Consulte o Administrador! " + e.getMessage());
		}
		return retorno;
	}

}