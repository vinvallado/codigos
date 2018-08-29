package br.mil.fab.consigext.repository;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.mil.fab.consigext.entity.PesfisComgep;
import br.mil.fab.consigext.entity.ServidorConsig;
import br.mil.fab.consigext.enums.CapacidadeServidor;
import br.mil.fab.consigext.enums.StatusEstabilizado;

public interface ServidorConsigRepository extends GenericRepository<ServidorConsig,Long> {

	public static final String SERVIDOR_POR_MATRICULA = " SELECT SC.* "
														+ " FROM CONSIG.T_SERVIDOR_CONSIG SC" 
														+ " INNER JOIN SIG.T_PESFIS_COMGEP_DW PC " 
														+ " ON SC.NR_ORDEM = PC.NR_ORDEM " 
														+ " WHERE PC.NR_ORDEM = ? ";
			
	public static final String SERVIDOR_POR_CPF = " SELECT SC.ID_SERVIDOR_CONSIG " 
																+ ", SC.NR_ORDEM "  
																//+ ", VL_MARGEM30 " 
																//+ ", VL_MARGEM70 "
																+ ", ID_SITUACAO_SERVIDOR " 
																+ ", DS_CATEGORIA " 
																+ ", ST_ESTABILIZADO "
																+ ", DT_REENGANJAMENTO "  
																+ ", ST_CAPACIDADE "
													+ " FROM T_SERVIDOR_CONSIG SC " 
													+ " INNER JOIN T_PESFIS_COMGEP_DW PC "  
													+ " ON SC.NR_ORDEM = PC.NR_ORDEM " 
													+ " WHERE PC.NR_CPF = ? ";
	
	public static final String SERVIDOR_MAT_CPF = " SELECT SC.ID_SERVIDOR_CONSIG " 
													+ ", SC.NR_ORDEM "  
													//+ ", VL_MARGEM30 " 
													//+ ", VL_MARGEM70 "
													+ ", ID_SITUACAO_SERVIDOR " 
													+ ", DS_CATEGORIA " 
													+ ", ST_ESTABILIZADO "
													+ ", DT_REENGANJAMENTO "  
													+ ", ST_CAPACIDADE "
										+ " FROM T_SERVIDOR_CONSIG SC " 
										+ " INNER JOIN T_PESFIS_COMGEP_DW PC "  
										+ " ON SC.NR_ORDEM = PC.NR_ORDEM "
										+ " WHERE PC.NR_ORDEM = ? OR PC.NR_CPF = ?";
										
										
	public ServidorConsig findById(long id);
	public ServidorConsig findByPesfis(PesfisComgep pesfis);
	
	
	@Query(value=SERVIDOR_POR_MATRICULA, nativeQuery=true)
	public ServidorConsig findByMatricula  (String nrOrdem);
	
	@Query(value=SERVIDOR_POR_CPF, nativeQuery=true)
	public ServidorConsig findByCpf  (String cpf);
	
	public ServidorConsig findByPesfis_NrCpf(String cpf);
	
	@Query(value=SERVIDOR_MAT_CPF, nativeQuery=true)
	public ServidorConsig findByMatriculaOrCpf(String matricula, String cpf);
	
	
	@Modifying
	@Query("Update ServidorConsig sc SET sc.stCapacidade = :stCapacidade, "
			+ "sc.stEstabilizado = :stEstabilizado, sc.dsCategoria = :dsCategoria,sc.dtReenganjamento = :dtReengajamento "
			+ " where sc.pesfis.nrOrdem = :nrOrdem")
	void updateProcesso(@Param("stCapacidade") CapacidadeServidor stCapacidade,
			@Param("stEstabilizado") StatusEstabilizado stEstabilizado,@Param("dsCategoria") String dsCategoria, 
			@Param("dtReengajamento") Date dtReengajamento,
			@Param("nrOrdem") String nrOrdem);
//	@Query("Update ServidorConsig sc SET sc.vlMargem70 = :vlMargem70, sc.stCapacidade = :stCapacidade, "
//			+ "sc.stEstabilizado = :stEstabilizado, sc.dsCategoria = :dsCategoria,sc.dtReenganjamento = :dtReengajamento, "
//			+ "sc.vlMargem30 = :vlMargem30 where sc.pesfis.nrOrdem = :nrOrdem")
//	void updateProcesso(@Param("vlMargem70") BigDecimal vlMargem70,@Param("stCapacidade") CapacidadeServidor stCapacidade,
//			@Param("stEstabilizado") StatusEstabilizado stEstabilizado,@Param("dsCategoria") String dsCategoria, 
//			@Param("dtReengajamento") Date dtReengajamento,@Param("vlMargem30") BigDecimal vlMargem30,
//			@Param("nrOrdem") String nrOrdem);
	
}
