package br.mil.fab.consigext.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import br.mil.fab.consigext.dto.CetConsig;

public interface CetConsigRepository extends GenericRepository<CetConsig, Long>{
	
	static String FIND_CET_CONSIG = " SELECT CT.ID_CET, "
			+ "      CT.CD_ANOMES, " 
			+ "      CT.NR_PARCELA, " 
			+ "      CT.VL_CET, "
			+ "      O.NM_ORG " 
			+ " FROM T_CET CT" 
			+ " INNER JOIN T_SERVICO_CONSIG SC"
			+ " ON CT.ID_SERVICO_CONSIG = SC.ID_SERVICO_CONSIG"
			+ " INNER JOIN T_ENTIDADE_CONSIG E" 
			+ " ON SC.ID_ENTIDADE_CONSIG = E.ID_ENTIDADE_CONSIG" 
			+ " INNER JOIN T_ORGANIZACAO O" 
			+ " ON E.CD_ORG = O.CD_ORG" 
			+ " WHERE E.ID_ENTIDADE_CONSIG = ? " 
			+ " AND CT.NR_PARCELA = ? " 
			+ " AND CT.CD_ANOMES = ? "; 
	
	static String FIND_CETS = " SELECT CT.ID_CET, "
			+ "      CT.CD_ANOMES, " 
			+ "      CT.NR_PARCELA, " 
			+ "      CT.VL_CET, "
			+ "      O.NM_ORG "
			+ " FROM T_CET CT " 
			+ " INNER JOIN T_SERVICO_CONSIG SC"
			+ " ON CT.ID_SERVICO_CONSIG = SC.ID_SERVICO_CONSIG"
			+ " INNER JOIN T_ENTIDADE_CONSIG EC" 
			+ " ON SC.ID_ENTIDADE_CONSIG = EC.ID_ENTIDADE_CONSIG" 
			+ " INNER JOIN T_ORGANIZACAO O" 
			+ " ON EC.CD_ORG = O.CD_ORG" 
			+ " WHERE CT.NR_PARCELA = ? " 
			+ " AND CT.CD_ANOMES = ? "
			+ " ORDER BY CT.VL_CET "; 

	@Query(value = FIND_CET_CONSIG, nativeQuery = true)
	public List<CetConsig> findCetConsig(Long id, long nrPrestacoes, long anomes);
	
	@Query(value = FIND_CETS, nativeQuery = true)
	public List<CetConsig> findCets(long nrPrestacoes, long anomes);

}
