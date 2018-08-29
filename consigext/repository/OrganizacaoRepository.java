package br.mil.fab.consigext.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.mil.fab.consigext.entity.Organizacao;

@Repository
public interface OrganizacaoRepository extends GenericRepository<Organizacao,Long> {
	
	static String FIND_ORGANIZACAO_ENTIDADE_CONSIG = " SELECT DISTINCT O.* "
													+ " FROM SIG.T_ORGANIZACAO O " 
													+ " INNER JOIN T_ENTIDADE_CONSIG E " 
													+ " ON O.CD_ORG = E.CD_ORG "
													+ " ORDER BY trim(O.NM_ORG)";
	@Query(value=FIND_ORGANIZACAO_ENTIDADE_CONSIG, nativeQuery = true)
	public List<Organizacao> findOrganizacoesporEntidadesConsigs();
	
	public Organizacao findByCdOrg(String cdOrg);
	
	@Query(value="select * from sig.t_organizacao where ST_EXTINTA='N' OR ST_EXTINTA IS NULL ORDER BY trim(NM_ORG)", nativeQuery = true)
	public List<Organizacao> findByStExtintaOrderByNmOrg();
}



