package br.mil.fab.consigext.repository;

import java.util.List;

import br.mil.fab.consigext.entity.StatusConsignacao;

public interface StatusConsignacaoRepository extends GenericRepository<StatusConsignacao,Long> {

	public StatusConsignacao findByNmStatus(String nome);
	public List<StatusConsignacao> findByNmStatusIn(List<String> namesStatus);
		
}
