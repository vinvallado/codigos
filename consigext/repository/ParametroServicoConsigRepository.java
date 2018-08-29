package br.mil.fab.consigext.repository;

import org.springframework.stereotype.Repository;

import br.mil.fab.consigext.entity.ParametroServicoConsig;
import br.mil.fab.consigext.entity.ServicoConsig;


@Repository

public interface ParametroServicoConsigRepository extends GenericRepository<ParametroServicoConsig,Long>{
	public ParametroServicoConsig findByServicoConsigAndParametro_SgParametro(ServicoConsig servicoConsig, String sgParametro);
	
}
