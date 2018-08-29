package br.mil.fab.consigext.repository;

import org.springframework.data.jpa.repository.Query;
import br.mil.fab.consigext.entity.EnderecoOrganizacao;
import br.mil.fab.consigext.entity.EnderecoOrganizacaoPK;


public interface EnderecoOrganizacaoRespository extends GenericRepository<EnderecoOrganizacao, EnderecoOrganizacaoPK> {


	static String FIND_ENDERECO_ORGANIZACAO = " SELECT * " 
										   + " FROM SIG.T_ENDERECO_ORGANIZACAO E "
										   + " WHERE E.CD_ORG = ? " 
										   + " AND E.ST_SEDE = 'S' "; 		
			
			
	@Query(value = FIND_ENDERECO_ORGANIZACAO, nativeQuery = true)
	public EnderecoOrganizacao findByEnderecoOrganizacao(String cdOrg);		
}

