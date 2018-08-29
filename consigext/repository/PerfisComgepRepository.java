package br.mil.fab.consigext.repository;

import br.mil.fab.consigext.entity.PesfisComgep;

public interface PerfisComgepRepository extends GenericRepository<PesfisComgep,Long> {

	public PesfisComgep findByNrOrdem(String nrOrdem);
	public PesfisComgep findByNrCpf(String nrCpf);
}
