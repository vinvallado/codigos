package br.mil.fab.pessoa.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.mil.fab.pessoa.api.entity.PesfisComgep;


public interface PessoaFisicaRepository extends JpaRepository<PesfisComgep, String> {

	PesfisComgep findByNrCpf(String cpf);
	
	

}
