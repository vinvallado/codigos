package br.mil.fab.pessoa.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.mil.fab.pessoa.api.entity.PesfisComgep;


public interface MilitarRepository extends JpaRepository<PesfisComgep, String> {
	
	PesfisComgep findByNrOrdem(String nrOrdem);

	PesfisComgep findByNrCpf(String cpf);
		
	
}
