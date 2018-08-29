package br.mil.fab.pessoa.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.mil.fab.pessoa.api.entity.Organizacao;


public interface OrganizacaoRepository extends JpaRepository<Organizacao, String> {
	
	Organizacao findByCdOrg(String cdOrg);
}
