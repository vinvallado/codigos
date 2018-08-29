package br.mil.fab.consigext.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.mil.fab.consigext.entity.EntidadeConsig;
import br.mil.fab.consigext.entity.Servico;
import br.mil.fab.consigext.entity.ServicoConsig;

@Repository
public interface ServicoConsigRepository extends GenericRepository<ServicoConsig,Long> {
	
	static String RETORNA_PORC_MARGEM = "SELECT VL_PARAMETRO "  
										+ "FROM T_SERVICO_CONSIG SC " 
										+ "INNER JOIN T_SERVICO S " 
										+ "ON SC.ID_SERVICO = S.ID_SERVICO " 
										+ "INNER JOIN T_PARAMETRO_SERVICO PS " 
										+ "ON S.ID_SERVICO = PS.ID_SERVICO " 
										+ "INNER JOIN T_PARAMETRO P " 
										+ "ON PS.ID_PARAMETRO = P.ID_PARAMETRO " 
										+ "WHERE SC.ID_ENTIDADE_CONSIG = ? " 
										+ "AND SC.ID_SERVICO = ?"
										+ "AND P.SG_PARAMETRO LIKE ?"; 	

//	static String RETORNA_PORC_MARGEM_SERV = " SELECT SC.VL_MARGEM30 " +
//											" FROM T_SERVIDOR_CONSIG SC " +
//											" WHERE SC.NR_ORDEM = ? ";
											//TODO: Incluir depois da implementação completa da margem do servidor;
											//+ " AND SC.ID_SITUACAO_SERVIDOR = 1 ";
	
	public ServicoConsig findById(Long id);
	
	@Query(value = RETORNA_PORC_MARGEM, nativeQuery = true)
	public String retornaValorMargemServico(Long idEntidade, Long idServico, String sigla);
	
//	@Query(value = RETORNA_PORC_MARGEM_SERV, nativeQuery = true)
//	public String retornaValMargem30Servidor(String matricula);
	
	public ServicoConsig findByServicoAndEntidadeConsig(Servico servico, EntidadeConsig entidadeConsig);
	
	public ServicoConsig findByServico_idAndEntidadeConsig_id(Long idServico, Long idEntidadeConsig);
	
	public List<ServicoConsig> findByEntidadeConsig_id(long id);
	
	public List<ServicoConsig> findByEntidadeConsig(EntidadeConsig entidadeConsig);
	
}
