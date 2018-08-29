package br.mil.fab.consigext.repository;

import br.mil.fab.consigext.dto.AcessoBanco;

public interface AcessoBancoRepository extends GenericRepository<AcessoBanco,Long> {
	AcessoBanco findByCdBanco(String cdBanco);
}


