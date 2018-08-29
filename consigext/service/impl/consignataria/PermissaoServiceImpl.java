package br.mil.fab.consigext.service.impl.consignataria;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mil.fab.consigext.entity.Permissao;
import br.mil.fab.consigext.repository.PermissaoRepository;
import br.mil.fab.consigext.service.consignataria.PermissaoService;


@Service
public class PermissaoServiceImpl implements PermissaoService {
	
	@Autowired
	PermissaoRepository permissaoRepo;

	@Override
	public List<Permissao> findByIdPerfil(Long idPerfil) {
		
		return permissaoRepo.findByIdPerfil(idPerfil);
	}
	
	

}
