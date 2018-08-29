package br.mil.fab.consigext.repository;

import org.springframework.stereotype.Repository;

import br.mil.fab.consigext.entity.Cet;
import br.mil.fab.consigext.entity.ServicoConsig;

@Repository
public interface CetRepository extends GenericRepository<Cet, Long>{	
	public Cet findByServicoConsigAndNrParcelaAndCdAnomes(ServicoConsig sc, long nrParcela, long cdAnomes);
	
}
