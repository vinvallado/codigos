package br.mil.fab.consigext.service.impl.margem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mil.fab.consigext.entity.EstabilidadeQuadroTempoServico;
import br.mil.fab.consigext.repository.EstabilidadeQuadroTempoServicoRepository;
import br.mil.fab.consigext.service.margem.EstabilidadeQuadroTempoServicoService;

@Service
public class EstabilidadeQuadroTempoServicoServiceImpl implements EstabilidadeQuadroTempoServicoService{

	@Autowired
	EstabilidadeQuadroTempoServicoRepository estQdrTmpSvcRepo;
	
	@Override
	public EstabilidadeQuadroTempoServico retornaEstabilidadeQuadroTempoServico(String nrOrdem) {
		return estQdrTmpSvcRepo.retornaEstabilidadeQuadroTempoServico(nrOrdem);
	}
	
	
}
