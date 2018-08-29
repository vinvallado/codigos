package br.mil.fab.consigext.service.impl.margem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mil.fab.consigext.dto.Reengajamento;
import br.mil.fab.consigext.repository.ReengajamentoRepository;
import br.mil.fab.consigext.service.margem.ReengajamentoService;

@Service
public class ReengajamentoServiceImpl implements ReengajamentoService{

	@Autowired
	ReengajamentoRepository reengRepo;
	
	@Override
	public Reengajamento retornaProximaDataReengajamento(String nrOrdem) {
		return reengRepo.retornaProximaDataReengajamento(nrOrdem);
	}
	
	
}
