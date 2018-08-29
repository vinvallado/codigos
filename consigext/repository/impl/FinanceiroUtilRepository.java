package br.mil.fab.consigext.repository.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class FinanceiroUtilRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	private static final String SOLDO_QUERY = "SELECT  HPR.VL_PARCELA AS SOLDO FROM SIG.T_HISTORICO_PARCELA_REGULAR HPR "
			+ "WHERE HPR.NR_ORDEM = ?nrOrdem "
			+ "AND HPR.CD_ANO_MES = "
			+ "(SELECT MAX(CF.CD_ANO_MES) FROM T_CFG_BASICA_PGM CF "
			+ "WHERE CF.TP_VERSAO = 'O') AND HPR.CD_CAIXA IN "
			+ "(SELECT CP.CD_CAIXA FROM T_CAIXA_PAGAMENTO CP "
			+ "INNER JOIN T_AUTORIZACAO_CAIXA AC ON AC.CD_AUTORIZACAO = CP.CD_AUTORIZACAO "
			+ "WHERE CP.CD_PARCELA = '001' AND NVL(CP.ST_EXTINTO,'N') = 'N')";
	
	private static final String MARGEM_VARIAVEL = "SELECT nvl(sum(VL_PARCELA),0 ) as saldo FROM SIG.T_HISTORICO_PARCELA_VARIAVEL HP "
			+ "INNER JOIN SIG.T_CAIXA_PAGAMENTO CP ON CP.CD_CAIXA = HP.CD_CAIXA "
			+ "INNER JOIN SIG.T_HISTORICO_PAGAMENTO HPC ON HPC.NR_ORDEM = HP.NR_ORDEM "
			+ "AND HPC.CD_ANO_MES = HP.CD_ANO_MES "
			+ "AND HPC.NR_VERSAO = HP.NR_VERSAO "
			+ "WHERE HP.NR_ORDEM = ?nrOrdem "
			+ "AND HP.NR_VERSAO = 1 "
			+ "AND CP.ST_MRG_CONSIG = 'S' "
			+ "AND HPC.ST_PAGTO_NEGATIVO = 'N' "
			+ "AND CP.TP_OPERACAO = '-' "
			+ "AND HP.cd_ano_mes = "
			+ "(SELECT MAX(CF.CD_ANO_MES) FROM T_CFG_BASICA_PGM CF "
			+ "WHERE CF.TP_VERSAO = 'O') "
			+ "AND (DT_FIM_PRAZO IS NULL OR TO_CHAR(DT_FIM_PRAZO,'mm/yyyy') > TO_CHAR(sysdate,'mm/yyyy'))";
	
	private static final String MARGEM_REGULAR = "SELECT nvl(sum(VL_PARCELA),0) as saldo FROM SIG.T_HISTORICO_PARCELA_REGULAR HP "
			+ "INNER JOIN SIG.T_CAIXA_PAGAMENTO CP ON CP.CD_CAIXA = HP.CD_CAIXA "
			+ "INNER JOIN SIG.T_HISTORICO_PAGAMENTO HPC ON HPC.NR_ORDEM = HP.NR_ORDEM "
			+ "AND HPC.CD_ANO_MES = HP.CD_ANO_MES "
			+ "AND HPC.NR_VERSAO = HP.NR_VERSAO "
			+ "WHERE HP.NR_ORDEM = ?nrOrdem "
			+ "AND HP.NR_VERSAO = 1 "
			+ "AND CP.ST_MRG_CONSIG = 'S' "
			+ "AND HPC.ST_PAGTO_NEGATIVO = 'N' "
			+ "AND CP.TP_OPERACAO = '-' "
			+ "AND HP.cd_ano_mes = (SELECT MAX(CF.CD_ANO_MES) FROM T_CFG_BASICA_PGM CF "
			+ "WHERE CF.TP_VERSAO = 'O') "
			+ "AND (DT_FIM_PRAZO IS NULL OR TO_CHAR(DT_FIM_PRAZO,'mm/yyyy') > TO_CHAR(sysdate,'mm/yyyy'))";
	
	public BigDecimal getSoldoByNrOrdem(String nrOrdem) {
		return getDecimalValueQueriesByQueryAndNrOrdem(SOLDO_QUERY,nrOrdem);
	}
	
	public BigDecimal getMargemParcelaVariavelByNrOrdem(String nrOrdem) {
		return getDecimalValueQueriesByQueryAndNrOrdem(MARGEM_VARIAVEL,nrOrdem);
	}
	
	public BigDecimal getMargemParcelaRegularByNrOrdem(String nrOrdem) {
		return getDecimalValueQueriesByQueryAndNrOrdem(MARGEM_REGULAR,nrOrdem);
	}
	
	
	private BigDecimal getDecimalValueQueriesByQueryAndNrOrdem(String queryString,String nrOrdem) {
		Query query = em.createNativeQuery(queryString,BigDecimal.class);
		query.setParameter("nrOrdem", nrOrdem);
		List<BigDecimal> results = query.getResultList();
		return results != null && !results.isEmpty()? results.get(0) 
				: BigDecimal.ZERO;
		
	}
	
	

}
