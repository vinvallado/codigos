package br.mil.fab.consigext.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.mil.fab.consigext.entity.CargaArquivo;

public interface CargaArquivoRepository extends PagingAndSortingRepository<CargaArquivo,Long> {	
	
	static String FIND_MAX_DATE_CARGA = "SELECT MAX(DT_CARGA) DT_CARGA FROM T_CARGA_ARQUIVO WHERE TP_CARGA LIKE ?";
	
	
	Page<CargaArquivo> findByTpCargaOrderByDtCargaDesc(String tpCarga,Pageable page);

	List<CargaArquivo> findByTpCarga(String tpCarga);
	CargaArquivo findByIdCargaArquivo(Long id);
	
	@Query(value = FIND_MAX_DATE_CARGA, nativeQuery = true)
	Date findByMaxDateTpCarga(String tpCarga);
	
	List<CargaArquivo> findByIdTipoCargaAndTpCargaAndAtivoEquals(Long idTipoCarga, String tipoCarga, Integer ativo);
	

}
