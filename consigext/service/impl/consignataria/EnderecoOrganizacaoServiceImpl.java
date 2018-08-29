package br.mil.fab.consigext.service.impl.consignataria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mil.fab.consigext.entity.EnderecoOrganizacao;
import br.mil.fab.consigext.repository.EnderecoOrganizacaoRespository;
import br.mil.fab.consigext.service.consignataria.EnderecoOrganizacaoService;

@Service
public class EnderecoOrganizacaoServiceImpl implements EnderecoOrganizacaoService {

	@Autowired
	EnderecoOrganizacaoRespository enderecoRepo;
	
	@Override
	public EnderecoOrganizacao findByEnderecoOrganizacao(String cdOrg) {
		return enderecoRepo.findByEnderecoOrganizacao(cdOrg);
	}
	
}
