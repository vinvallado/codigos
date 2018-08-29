package br.mil.fab.consigext.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import br.mil.fab.consigext.entity.Servico;

public interface ServicoRepository extends GenericRepository<Servico,Long> {
	
	static String SERVICOS_ENTIDADE_CONSIG = " SELECT S.ID_SERVICO, S.CD_SERVICO, S.DS_SERVICO, S.ST_SERVICO " +
													" FROM T_SERVICO_CONSIG SC " +
													" INNER JOIN T_SERVICO S " +
													" ON SC.ID_SERVICO = S.ID_SERVICO " +
													" INNER JOIN T_ENTIDADE_CONSIG EC " +
													" ON SC.ID_ENTIDADE_CONSIG = EC.ID_ENTIDADE_CONSIG " +
													" WHERE EC.ID_ENTIDADE_CONSIG = ? ";
	
	
	static String NUM_PARC_MAXIMA_SERVICO = " SELECT PS.VL_PARAMETRO " + 
											" FROM T_SERVICO S, T_PARAMETRO_SERVICO PS, T_PARAMETRO P " + 
											" WHERE S.ID_SERVICO = PS.ID_SERVICO " + 
											" AND PS.ID_PARAMETRO = P.ID_PARAMETRO " + 
											" AND S.ID_SERVICO = ? " +
											" AND SG_PARAMETRO =  ? ";
	
	static String SERVICO_POR_ENTIDADE = " SELECT  S.ID_SERVICO, " +
									        " S.DS_SERVICO, " +
									        " S.NR_PRIORIDADE, " +
									        " S.CD_SERVICO, " +
									        " S.ID_NATUREZA_SERVICO , S.ST_SERVICO " + 
											"FROM T_SERVICO S " + 
											"INNER JOIN T_SERVICO_CONSIG SC " + 
											"ON S.ID_SERVICO = SC.ID_SERVICO_CONSIG " + 
											"INNER JOIN T_ENTIDADE_CONSIG E " + 
											"ON SC.ID_ENTIDADE_CONSIG = E.ID_ENTIDADE_CONSIG " + 
											"WHERE E.CD_ORG = ? ";
	
	//TODO:INCLUIR A LINHA COMENTADA ABAIXO APOS TESTES UNITÁRIOS E MERGE 25/04: VINICIUS VALLADO;
	static String SERVICO_FULL_POR_ENTIDADE = "SELECT DISTINCT S.* "
											+ " FROM T_SERVICO S " 
											+ " INNER JOIN T_SERVICO_CONSIG SC "
											+ " ON SC.ID_SERVICO = S.ID_SERVICO "
											+ " INNER JOIN T_ENTIDADE_CONSIG EC "
										    + " ON SC.ID_ENTIDADE_CONSIG = EC.ID_ENTIDADE_CONSIG "
											+ " INNER JOIN T_ORGANIZACAO O "
											+ " ON EC.CD_ORG = O.CD_ORG "
											+ " WHERE EC.ID_ENTIDADE_CONSIG = ? "
											+ " OR O.ST_EXTINTA LIKE 'N' "
											+ " ORDER BY S.ID_SERVICO";
									
	
	
	public Servico findById(long id);
	
	public List<Servico> findByStExcluidoOrderByCdServico(Long stExcluido);
	
	public Servico findByCdServico(String cdServico);
	@Query(value = SERVICOS_ENTIDADE_CONSIG, nativeQuery = true)
	public List<Servico> findServicosEntidadeConsig(Long idEntidadeConsig);

	@Query(value = NUM_PARC_MAXIMA_SERVICO, nativeQuery = true)
	public String findNumParcelasMaximas(Long idServico, String parametro);
	
	@Query(value = SERVICO_POR_ENTIDADE, nativeQuery = true)
	public List<Servico> findServicoByEntidade(String cdOrg);
	
	@Query(value = SERVICO_FULL_POR_ENTIDADE, nativeQuery = true)
	public List<Servico> findServicoFullByEntidade(Long idEntidadeConsig);

	public List<Servico> findByStExcluidoAndStServicoOrderByCdServico(long stExcluido, long stServico);
}
