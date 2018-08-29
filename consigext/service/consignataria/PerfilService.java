package br.mil.fab.consigext.service.consignataria;

import java.util.List;
import java.util.Map;

import br.mil.fab.consigext.entity.EntidadeConsig;
import br.mil.fab.consigext.entity.Perfil;

public interface PerfilService {	
	
	public Perfil salvar(Map<String,Object> body, EntidadeConsig entidadeConsig);
	
	public List<Perfil> findByEntidadeConsig(EntidadeConsig entidade);	
	
	public List<Perfil> findByEntidadeConsigAndStBloqueioPerfil(EntidadeConsig entidade, long bloqueio);	
		
	void setStBloqPerfil(long newSt, long idperfil);	
	
	
}
