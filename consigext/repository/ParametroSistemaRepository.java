package br.mil.fab.consigext.repository;

import org.springframework.stereotype.Repository;

import br.mil.fab.consigext.entity.ParametroSistema;

@Repository
public interface ParametroSistemaRepository extends GenericRepository<ParametroSistema,Long> {
	
	public ParametroSistema findById(Long id);
	
}
