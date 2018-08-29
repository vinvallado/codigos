package br.mil.fab.consigext.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.mil.fab.consigext.entity.EntidadeConsig;
import br.mil.fab.consigext.entity.HistoricoEc;
@Repository
public interface HistoricoEcRepository extends GenericRepository<HistoricoEc, Long>{
	public List<HistoricoEc> findByTpHistoricoOrTpHistoricoAndEntidadeConsigOrderByIdDesc(String tpHistorico1, String tpHistorico2, EntidadeConsig consignataria);
}

