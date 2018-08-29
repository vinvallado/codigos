package br.mil.fab.consigext.service.margem;

import java.util.List;

import br.mil.fab.consigext.entity.EntidadeConsig;
import br.mil.fab.consigext.entity.UsuarioConsig;

public interface EntidadeConsigService {
	
	public List<EntidadeConsig> findAll();
	public EntidadeConsig findById(Long id);
	public void setStEntidadeOfConsig(long newSt, EntidadeConsig consig);
	EntidadeConsig getEntidadeConsig(String id);
	List<UsuarioConsig> getUsuarioConsigPorEntidadeConsig(EntidadeConsig entidadeConsig);
	public String findByCdEntidade();
	public void changeStEntidadeOfConsig(long idConsig);
}
