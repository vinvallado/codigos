package br.mil.fab.consigext.service.margem;

import br.mil.fab.consigext.entity.PesfisComgep;

public interface PerfisComgepService {
	
	public PesfisComgep findByNrOrdem(String nrOrdem);
	public PesfisComgep findByNrCpf(String nrCpf);

}
