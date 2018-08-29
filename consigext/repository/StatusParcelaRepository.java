package br.mil.fab.consigext.repository;
import org.springframework.stereotype.Repository;

import br.mil.fab.consigext.entity.StatusParcela;;
@Repository
public interface StatusParcelaRepository extends GenericRepository<StatusParcela,Long> {
	public StatusParcela findByNmStatusParcela(String nome);
	public StatusParcela findById(Long id);

}
