package br.mil.fab.consigext.repository;

import java.util.List;

import br.mil.fab.consigext.entity.Consignacao;
import br.mil.fab.consigext.entity.ParcelaConsignacao;

public interface ParcelaRepository extends GenericRepository<ParcelaConsignacao,Long> {

	List<ParcelaConsignacao> findByConsignacao(Consignacao consignacao);
	
}
