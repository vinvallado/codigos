package br.mil.fab.consigext.service.impl.margem;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mil.fab.consigext.entity.HistoricoConsignacao;
import br.mil.fab.consigext.entity.HistoricoParcela;
import br.mil.fab.consigext.repository.HistoricoParcelaRepository;
import br.mil.fab.consigext.service.margem.HistoricoParcelaService;

@Service
public class HistoricoParcelaServiceImpl implements HistoricoParcelaService{

	@Autowired
	HistoricoParcelaRepository historicoParcelaRepository;
	
	@Override
	public List<HistoricoParcela> findByHistoricoConsignacao(HistoricoConsignacao historicoConsignacao) {
		return historicoParcelaRepository.findByHistoricoConsignacao(historicoConsignacao);
	}

	
}
