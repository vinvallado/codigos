package br.mil.fab.consigext.service.impl.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mil.fab.consigext.entity.Organizacao;
import br.mil.fab.consigext.entity.Servico;
import br.mil.fab.consigext.repository.OrganizacaoRepository;
import br.mil.fab.consigext.service.margem.OrganizacaoService;
import br.mil.fab.consigext.service.util.UsuarioUtilService;
import br.mil.fab.util.sigpes.entity.OrganizacaoResponse;
import br.mil.fab.util.sigpes.entity.PesfisComgepResponse;

@Service
public class OrganizacaoServiceImpl implements OrganizacaoService{

	@Autowired
	UsuarioUtilService usuarioUtilService;
	
	@Autowired
	OrganizacaoRepository organizacaoRepository;
	
	@Override
	public OrganizacaoResponse getOmMilitar(String matricula) {
		return getOMMilitar(usuarioUtilService.getMilitarByMatriculaOrCpf(matricula));
	}


	
	private OrganizacaoResponse getOMMilitar(PesfisComgepResponse pesfisComgep) {
		
		if (pesfisComgep.getOrgAdido() != null) {
			return pesfisComgep.getOrgAdido();
		}
		if (pesfisComgep.getOrgSvc() != null) {
			return pesfisComgep.getOrgSvc();
		}
		if (pesfisComgep.getOrg() != null) {
			return pesfisComgep.getOrg();
		}
		
		return null;
	}



	@Override
	public List<Organizacao> getOrganizacoes() {
		List<Organizacao> organizacoes = organizacaoRepository.findByStExtintaOrderByNmOrg();
		return organizacoes;
	}

	
}
