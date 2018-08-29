package br.mil.fab.pessoa.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mil.fab.pessoa.api.entity.Organizacao;
import br.mil.fab.pessoa.api.repository.OrganizacaoRepository;

@Service
public class OrganizacaoService {
	
	@Autowired
	private OrganizacaoRepository oRep;
	
	public Organizacao findByCdOrg(String cdOrg) {
		return oRep.findByCdOrg(cdOrg);
	}

}
