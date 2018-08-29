package br.mil.fab.consigext.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.mil.fab.consigext.entity.EntidadeConsig;
@Repository
public interface EntidadeConsigRepository extends GenericRepository<EntidadeConsig,Long> {
	
	
	public List<EntidadeConsig> findAllIgnoreCaseByOrderByCdEntidade();
	
	public EntidadeConsig findById(long id);
	
	@Query(value = "SELECT CD_ENTIDADE FROM T_ENTIDADE_CONSIG E WHERE E.ID_ENTIDADE_CONSIG=1", nativeQuery = true)
	public String findByCdEntidade();

}
