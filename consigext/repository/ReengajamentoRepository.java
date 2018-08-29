package br.mil.fab.consigext.repository;

import org.springframework.data.jpa.repository.Query;

import br.mil.fab.consigext.dto.Reengajamento;

public interface ReengajamentoRepository extends GenericRepository<Reengajamento,Long> {

	
	static String PROX_DT_REENGAJ = "select TO_CHAR(TO_DATE(max(dt_fim), 'DD/MM/RR') ,'DD/MM/YYYY') as DT_REENGAJAMENTO from t_reengajamento where nr_ordem = ?";
	
	
	@Query(value = PROX_DT_REENGAJ, nativeQuery = true)
	public Reengajamento retornaProximaDataReengajamento(String nrOrdem);
	

}
