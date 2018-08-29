package br.mil.fab.consigext.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import br.mil.fab.consigext.dto.CetConsigRanking;

public interface CetConsigRankingRepository extends GenericRepository<CetConsigRanking, Long>{

	
	static String FIND_CETS = " SELECT CT.ID_CET, "
			+ "      CT.CD_ANOMES, " 
			+ "      CT.NR_PARCELA, " 
			+ "      CT.VL_CET, "
			+ "      O.NM_ORG, " 
			+ "      EC.ID_ENTIDADE_CONSIG, " 
			+ "      SC.ID_SERVICO,"
			+ "	     EC.ST_ENTIDADE         "
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
	
	@Query(value = FIND_CETS, nativeQuery = true)
	public List<CetConsigRanking> findCets(long nrPrestacoes, long anomes);

}
