package br.mil.fab.consigext.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import br.mil.fab.consigext.entity.CodigoUnico;
import br.mil.fab.consigext.entity.ServidorConsig;

public interface CodigoUnicoRepository extends GenericRepository<CodigoUnico,Long> {
	static String FIND_NAO_USADO_BY_SERVIDOR = "SELECT * FROM T_CODIGO_UNICO CU WHERE CU.ID_SERVIDOR_CONSIG = ? AND CU.ST_CODIGO_UNICO = 0"; 
	
	static String CHECK_CODIGO_UNICO_VALIDO = " SELECT CU.ID_CODIGO_UNICO, "
										    + " CU.VL_CODIGO_UNICO, "
										    + " CU.DT_VALIDADE, "
										    + " CU.ID_SERVIDOR_CONSIG, "
										    + " CU.ST_CODIGO_UNICO "
										+ " FROM T_CODIGO_UNICO CU "
										+ " WHERE CU.VL_CODIGO_UNICO = ? "
										+ " AND CU.ID_SERVIDOR_CONSIG = ? "
										+ " AND CU.ST_CODIGO_UNICO = ? ";

	List<CodigoUnico> findByServidorConsig(ServidorConsig servidorConsig);
	
	@Query(value = FIND_NAO_USADO_BY_SERVIDOR, nativeQuery = true)
	List<CodigoUnico> findNaoUsadoByServidorConsig(Long idServidor);
	
	@Query(value = CHECK_CODIGO_UNICO_VALIDO, nativeQuery = true)
	CodigoUnico checkCodigoUnicoValido(String vlCodigoUnico,Long idServidor, Long stCodigoUnico);
	
	CodigoUnico findByVlCodigoUnico(String vlCodigoUnico);
	CodigoUnico findByVlCodigoUnicoAndServidorConsig(String vlCodigoUnico,ServidorConsig servidorConsig);
	CodigoUnico findByVlCodigoUnicoAndServidorConsigAndStCodigoUnicoEquals(String vlCodigoUnico,ServidorConsig servidorConsig, Long stCodigoUnico);
	CodigoUnico findByIdAndServidorConsig(Long id,ServidorConsig servidorConsig);

}
