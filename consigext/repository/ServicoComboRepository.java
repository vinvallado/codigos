package br.mil.fab.consigext.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.mil.fab.consigext.entity.Servico;
import br.mil.fab.consigext.entity.ServicoComboProjection;

@RepositoryRestResource(excerptProjection = ServicoComboProjection.class)
public interface ServicoComboRepository extends CrudRepository<Servico,Long> {

}
