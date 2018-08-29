package br.mil.fab.consigext.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.mil.fab.consigext.entity.NaturezaServico;
import br.mil.fab.consigext.entity.NaturezaServicoNoServiceProjection;

@RepositoryRestResource(excerptProjection = NaturezaServicoNoServiceProjection.class)
public interface NaturezaServicoNoServiceRepository extends GenericRepository<NaturezaServico,Long> {

}
