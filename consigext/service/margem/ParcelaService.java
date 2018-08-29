package br.mil.fab.consigext.service.margem;

import java.util.List;

import br.mil.fab.consigext.entity.Consignacao;
import br.mil.fab.consigext.entity.ParcelaConsignacao;

public interface ParcelaService {

	void save(ParcelaConsignacao parcela);
	void save(List<ParcelaConsignacao> parcelas);
	
	List<ParcelaConsignacao> findByConsignacao(Consignacao consignacao);
	double sumParcelas(List<ParcelaConsignacao> parcelas);
}
