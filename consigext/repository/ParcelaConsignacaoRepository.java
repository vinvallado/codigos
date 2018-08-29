package br.mil.fab.consigext.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.mil.fab.consigext.entity.Consignacao;
import br.mil.fab.consigext.entity.ParcelaConsignacao;

@Repository
public interface ParcelaConsignacaoRepository extends GenericRepository<ParcelaConsignacao,Long> {
	public List<ParcelaConsignacao> findByConsignacao(Consignacao consignacao);
	public ParcelaConsignacao findById(Long id);
}
