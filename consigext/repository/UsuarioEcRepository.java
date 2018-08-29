package br.mil.fab.consigext.repository;

import br.mil.fab.consigext.entity.UsuarioEc;

public interface UsuarioEcRepository extends GenericRepository<UsuarioEc,Long> {

	
	public UsuarioEc findByIdUsuarioExterno(Long id);
}
