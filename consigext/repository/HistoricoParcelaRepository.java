package br.mil.fab.consigext.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.mil.fab.consigext.entity.HistoricoConsignacao;
import br.mil.fab.consigext.entity.HistoricoParcela;
import br.mil.fab.consigext.entity.ParcelaConsignacao;

@Repository
public interface HistoricoParcelaRepository extends GenericRepository<HistoricoParcela, Long>{
	
	
	
	public HistoricoParcela findById(Long id);
	public List<HistoricoParcela> findByParcelaAndVlNovo(ParcelaConsignacao parcela, String vlNovo);
	public List<HistoricoParcela> findByHistoricoConsignacao(HistoricoConsignacao historicoConsignacao);
	

}