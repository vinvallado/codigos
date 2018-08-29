package br.mil.fab.consigext.repository;

import java.util.List;

import br.mil.fab.consigext.entity.EntidadeConsig;
import br.mil.fab.consigext.entity.UsuarioConsig;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioConsignatariaRepository extends GenericRepository<UsuarioConsig,Long> {

	public List<UsuarioConsig> findByEntidadeConsig(EntidadeConsig entidadeConsig);
	
	public List<UsuarioConsig> findByNrCpf(String nrCpf);

	public UsuarioConsig findById(Long idConsig);
	
	public UsuarioConsig findByNmUsuarioConsig(String nmUsuarioConsig);

}
