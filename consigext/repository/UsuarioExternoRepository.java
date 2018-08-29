package br.mil.fab.consigext.repository;

import br.mil.fab.consigext.entity.UsuarioExterno;

public interface UsuarioExternoRepository extends GenericRepository<UsuarioExterno,Long> {

	public UsuarioExterno findByNrCpf(String cpf);
}
