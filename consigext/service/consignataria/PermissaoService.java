package br.mil.fab.consigext.service.consignataria;

import java.util.List;

import br.mil.fab.consigext.entity.Permissao;

public interface PermissaoService {
	
	public List<Permissao> findByIdPerfil(Long idPerfil);
}
