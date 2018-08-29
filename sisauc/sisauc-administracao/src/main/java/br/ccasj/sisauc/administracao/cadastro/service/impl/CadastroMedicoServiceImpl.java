package br.ccasj.sisauc.administracao.cadastro.service.impl;

import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.MedicoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Especialidade;
import br.ccasj.sisauc.administracao.cadastro.domain.Medico;
import br.ccasj.sisauc.administracao.cadastro.service.CadastroMedicoService;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;

@Transactional
@Service(value = "cadastroMedicoService")
public class CadastroMedicoServiceImpl implements CadastroMedicoService {

	private static final long serialVersionUID = 5351391246111962861L;
	
	@Autowired
	private MedicoDAO medicoDAO;
	
	@Override
	public Medico salvar(Medico medico) {
		verificarProfissionalSaude(medico);
		verificarMilitar(medico);
		return medicoDAO.merge(medico);
	}

	private void verificarMilitar(Medico medico) {
		if(medico.isMilitar() && medico.getOrganizacaoMilitar() == null){
			throw new SystemRuntimeException("É necessário inserir a Organização Militar!");
		} else if(!medico.isMilitar()) {
			medico.setOrganizacaoMilitar(null);
		}
	}

	private void verificarProfissionalSaude(Medico medico) {
		if(medico.isProfissionalSaude()){
			verificarNumeroConselhoRegional(medico);
			verificarPossuiEspecialidade(medico);
		} else {
			medico.setNumeroConselhoRegional(null);
			medico.setEspecialidades(new HashSet<Especialidade>());
		}
	}

	private void verificarNumeroConselhoRegional(Medico medico) {
		if (medicoDAO.verificarNumeroConselhoRegional(medico.getId(), medico.getNumeroConselhoRegional())) {
			throw new SystemRuntimeException("O Número do Conselho Regional informado já é existente!");
		}
	}
	
	private void verificarPossuiEspecialidade(Medico medico) {
		if (medico.getEspecialidades().isEmpty()) {
			throw new SystemRuntimeException("É necessário cadastrar ao menos uma especialidade.");
		}
	}
	
}