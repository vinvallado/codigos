package br.mil.fab.consigext.service.impl.consignataria;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mil.fab.consigext.entity.EnderecoOrganizacao;
import br.mil.fab.consigext.entity.EntidadeConsig;
import br.mil.fab.consigext.entity.Localidade;
import br.mil.fab.consigext.repository.EnderecoOrganizacaoRespository;
import br.mil.fab.consigext.repository.EntidadeConsigRepository;
import br.mil.fab.consigext.service.consignataria.EntidadeConsignatariaService;
import br.mil.fab.consigext.util.GenericUtil;

@Service
public class EntidadeConsignatariaServiceImpl implements EntidadeConsignatariaService {
	
	@Autowired
	EntidadeConsigRepository entidadeConsigRepo;
	
	@Autowired
	EnderecoOrganizacaoRespository enderecoOrgRepo;

	@Override
	public boolean validaCamposConsig(Map<String, Object> body, EntidadeConsig entidadeConsig,
			EnderecoOrganizacao enderecoOrg) {
		boolean retorno = true;
		
		Localidade localidade = enderecoOrg.getLocalidade();
		body.entrySet().stream().forEach(x -> {
			for (Field f : entidadeConsig.getClass().getDeclaredFields()) {
				if (f.getName().equals(x.getKey())) {
					try {
						f.setAccessible(true);
						f.set(entidadeConsig, x.getValue());
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			for (Field f : localidade.getClass().getDeclaredFields()) {
				if (f.getName().equals(x.getKey())) {
					try {
						f.setAccessible(true);
						f.set(localidade, x.getValue());
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			for (Field f : enderecoOrg.getClass().getDeclaredFields()) {
				if (f.getName().equals(x.getKey())) {
					try {
						f.setAccessible(true);
						f.set(enderecoOrg, x.getValue());
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		});	
	
		enderecoOrg.setLocalidade(localidade);
		
		return false;
	}
	
	@Override
	public boolean validaCamposConsig(Map<String, Object> body, List<?> classes) {
		Optional<List<?>> formulario = Optional.ofNullable(GenericUtil.validaCamposFormumlario(body, classes));
		if (formulario.isPresent()) {
			EntidadeConsig entidadeConsig = null;
			EnderecoOrganizacao endereco = null;
			Localidade localidade = null;
			for(Object item : formulario.get()) {
				if (item instanceof EntidadeConsig) {
					entidadeConsig = (EntidadeConsig) item;
				} else if (item instanceof EnderecoOrganizacao) {
					endereco = (EnderecoOrganizacao) item;
				} else if (item instanceof Localidade) {
					localidade = (Localidade) item;
				}
			};
			endereco.setLocalidade(localidade);
			save(entidadeConsig, endereco);
		};
		
		return true;
	}
	
	@Transactional
	@Override
	public boolean save(EntidadeConsig entidadeConsig, EnderecoOrganizacao enderecoOrg) {
		
		try {
			entidadeConsigRepo.save(entidadeConsig);
			enderecoOrgRepo.save(enderecoOrg);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}


	
	


