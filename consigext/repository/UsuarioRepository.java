package br.mil.fab.consigext.repository;

import br.mil.fab.consigext.entity.PesfisComgep;
import br.mil.fab.consigext.entity.Usuario;

public interface UsuarioRepository extends GenericRepository<Usuario,Long> {

	Usuario findByNrCpf(String cpf);
	Usuario findByPesfis(PesfisComgep pesfis);
}
