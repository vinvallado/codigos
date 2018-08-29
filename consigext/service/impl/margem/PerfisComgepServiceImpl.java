package br.mil.fab.consigext.service.impl.margem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mil.fab.consigext.entity.PesfisComgep;
import br.mil.fab.consigext.repository.PerfisComgepRepository;
import br.mil.fab.consigext.service.margem.PerfisComgepService;

@Service
public class PerfisComgepServiceImpl implements PerfisComgepService{

	@Autowired
	PerfisComgepRepository perfisComgepRepo;
	
	public PesfisComgep findByNrOrdem(String nrOrdem) {
		return perfisComgepRepo.findByNrOrdem(nrOrdem);
	}
	public PesfisComgep findByNrCpf(String nrCpf) {
		return perfisComgepRepo.findByNrCpf(nrCpf);
	}
}
