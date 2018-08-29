package br.mil.fab.consigext.repository;

import br.mil.fab.consigext.entity.Localidade;

public interface LocalidadeRespository extends GenericRepository<Localidade,String> {

	public Localidade findByCdLocald(String cdLocald);		
	
}

