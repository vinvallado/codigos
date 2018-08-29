package br.mil.fab.consigext.repository;

import java.util.List;

import br.mil.fab.consigext.entity.EntidadeConsig;
import br.mil.fab.consigext.entity.Perfil;

public interface PerfilRepository extends GenericRepository<Perfil,Long> {
	
	List<Perfil> findByEntidadeConsig(EntidadeConsig entidade);
	
	List<Perfil> findByEntidadeConsigAndStBloqueioPerfil(EntidadeConsig entidade, long bloqueio);
	
	public Perfil findById(long idPerfil);
}
