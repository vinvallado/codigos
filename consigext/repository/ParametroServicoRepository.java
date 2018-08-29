package br.mil.fab.consigext.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.mil.fab.consigext.entity.Parametro;
import br.mil.fab.consigext.entity.ParametroServico;
@Repository
public interface ParametroServicoRepository extends GenericRepository<ParametroServico,Long> {

	
	public ParametroServico findByServico_idAndParametro_sgParametro(long idServico, String siglaParametro);
	public List<ParametroServico> findByServico_id(long idServico);

}
