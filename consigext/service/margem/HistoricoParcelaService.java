package br.mil.fab.consigext.service.margem;

import java.util.List;

import br.mil.fab.consigext.entity.HistoricoConsignacao;
import br.mil.fab.consigext.entity.HistoricoParcela;

public interface HistoricoParcelaService {
	
	public List<HistoricoParcela> findByHistoricoConsignacao(HistoricoConsignacao historicoConsignacao);

}
