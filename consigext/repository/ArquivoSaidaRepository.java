package br.mil.fab.consigext.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.mil.fab.consigext.entity.Consignacao;
import br.mil.fab.consigext.entity.Processamento;

@Repository
public interface ArquivoSaidaRepository  extends JpaRepository<Consignacao, Long>{
	
	@Query(nativeQuery = true)
	List<Processamento> findAllProcessosSaida();

}
