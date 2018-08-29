package br.mil.fab.consigext.service.impl.consignataria;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mil.fab.consigext.entity.EntidadeConsig;
import br.mil.fab.consigext.entity.Perfil;
import br.mil.fab.consigext.entity.Permissao;
import br.mil.fab.consigext.repository.PerfilRepository;
import br.mil.fab.consigext.repository.PermissaoRepository;
import br.mil.fab.consigext.service.consignataria.PerfilService;


@Service
public class PerfilServiceImpl implements PerfilService {
	@Autowired
	PerfilRepository perfilRepos;
	@Autowired
	PermissaoRepository permissaoRepos;

	@Override
	public Perfil salvar(Map<String,Object> body, EntidadeConsig entidadeConsig){
		Perfil perfil = new Perfil();
		List<Permissao> permissoesValores = new ArrayList<Permissao>();
		try {
			perfil.setNmPerfil(body.get("nome").toString().toUpperCase());
			perfil.setEntidadeConsig(entidadeConsig);
			List<Permissao> permissoes = permissaoRepos.findAll();
			for( Permissao permissao : permissoes){
				if (body.get(permissao.getNmPermissao()) != null) {
					permissoesValores.add(permissao);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		perfil.setPermissao(permissoesValores);
		perfil = perfilRepos.save(perfil);
		return perfil;
	}

	@Override
	public List<Perfil> findByEntidadeConsig(EntidadeConsig entidade) {
		return perfilRepos.findByEntidadeConsig(entidade);
	}	
	
	
	@Override
	public void setStBloqPerfil(long newSt, long idperfil) {
		Perfil perf = new Perfil();
		perf = perfilRepos.findById(idperfil);
		perf.setStBloqueioPerfil(newSt);
		perfilRepos.save(perf);					
	}

	@Override
	public List<Perfil> findByEntidadeConsigAndStBloqueioPerfil(EntidadeConsig entidade, long bloqueio) {
		return perfilRepos.findByEntidadeConsigAndStBloqueioPerfil(entidade, bloqueio);
	}

	
	

}
