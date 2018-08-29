package br.mil.fab.consigext.service.margem;

import java.util.List;

import br.mil.fab.consigext.dto.CetConsig;

public interface CetConsigService {

	public List<CetConsig> findCetConsig(Long id, long nrPrestacoes, long anomes);
	public List<CetConsig> findCets(long nrPrestacoes, long anomes);
}
