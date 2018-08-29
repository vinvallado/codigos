package br.mil.fab.consigext.repository;

import org.springframework.data.jpa.repository.Query;

import br.mil.fab.consigext.dto.EnderecoPessoaFisica;


public interface EnderecoPessoaFisicaRespository extends GenericRepository<EnderecoPessoaFisica,String> {

	static String FIND_ENDERECO_ATUAL = "SELECT * FROM T_ENDERECO_PESSOA_FISICA WHERE ST_ATUAL = 'S' AND NR_ORDEM = ?";
	static String FIND_ENDERECO_COMPLETO = " SELECT PF.CD_END_PES, " + 
											" PF.NM_END, " + 
											" PF.NM_BAIRRO, " + 
											" PF.NR_END, " +
											" PF.DS_COMPLEMENTO " + 
											" FROM T_ENDERECO_PESSOA_FISICA PF " + 
											" INNER JOIN  T_LOCALIDADE L ON PF.CD_LOCALD = L.CD_LOCALD " + 
											" WHERE PF.NR_ORDEM = ? " + 
											" AND PF.ST_ATUAL = 'S'";
			
	@Query(value = FIND_ENDERECO_ATUAL, nativeQuery = true)
	public EnderecoPessoaFisica findByEnderecoAtual(String id);
	
	public EnderecoPessoaFisica findByStAtualAndNrOrdem(String stAtual, String nrOrdem);
	
	
	@Query(nativeQuery = true)
	public EnderecoPessoaFisica findByEnderecoAtualCompleto(String matricula);
}

