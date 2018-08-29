package br.mil.fab.consigext.service.impl.consignataria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mil.fab.consigext.entity.Localidade;
import br.mil.fab.consigext.repository.LocalidadeRespository;
import br.mil.fab.consigext.service.consignataria.LocalidadeService;

@Service
public class LocalidadeServiceImpl implements LocalidadeService{

	@Autowired
	LocalidadeRespository localidadeRepo;
	
	@Override
	public Localidade findByCdLocald(String cdLocald) {
		return localidadeRepo.findByCdLocald(cdLocald);
	}

	
}
