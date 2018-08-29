package br.mil.fab.consigext.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.mil.fab.consigext.entity.Consignacao;
import br.mil.fab.consigext.entity.HistoricoConsignacao;
import br.mil.fab.consigext.entity.TipoHistoricoConsig;

@Repository
public interface HistoricoConsignacaoRepository extends GenericRepository<HistoricoConsignacao, Long>{
	
	
	static String FIND_HISTORICO_CONTRATO_LIQUIDADO = " SELECT * FROM T_HISTORICO_CONSIGNACAO HC " 
													 + " WHERE HC.ID_TIPO_HISTORICO_CONSIG = 9 " 
													 + " AND HC.ID_CONSIGNACAO = ? " 
													 + " AND HC.VL_NOVO = 15 " 
													 + " AND TO_CHAR(HC.DT_HISTORICO,'DD-MM-YY') = TO_CHAR(SYSDATE,'DD-MM-YY')";
	
	static String FIND_ULTIMO_HISTORICO = " SELECT H.* "  
													+ "FROM T_HISTORICO_CONSIGNACAO H " 
													+ "WHERE H.ID_HISTORICO_CONSIGNACAO = (SELECT MAX(HC.ID_HISTORICO_CONSIGNACAO) " 
													+ "                                    FROM T_HISTORICO_CONSIGNACAO HC " 
													+ "                                    WHERE HC.ID_CONSIGNACAO = ?) ";
	
	
	
	public HistoricoConsignacao findByIdAndTipoHistoricoConsig_SgTipoHistorico(long idTipoHistorico, String sgTipoHistorico);
	public List<HistoricoConsignacao> findByConsignacao_Id(long idConsignacao);
	public List<HistoricoConsignacao> findByConsignacaoAndTipoHistoricoConsig(Consignacao consignacao, TipoHistoricoConsig tipoHistoricoConsig);
	
	@Query(value =FIND_HISTORICO_CONTRATO_LIQUIDADO, nativeQuery=true)
	public HistoricoConsignacao findHistoricoLiquidado(Long idConsig);
	
	@Query(value =FIND_ULTIMO_HISTORICO, nativeQuery=true)
	public HistoricoConsignacao findUltimoHistorico(Long idConsig);
	
	
	public List<HistoricoConsignacao> findByConsignacaoAndTipoHistoricoConsig_id(Consignacao consignacao, Long idTipoHistorico);
	
	
}
