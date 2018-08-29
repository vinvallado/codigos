package br.mil.fab.consigext.service.consignataria;

import java.util.List;
import java.util.Map;

import br.mil.fab.consigext.entity.EnderecoOrganizacao;
import br.mil.fab.consigext.entity.EntidadeConsig;

public interface EntidadeConsignatariaService {

	public boolean validaCamposConsig(Map<String, Object> body ,EntidadeConsig entidadeConsig, EnderecoOrganizacao enderecoOrg);
	public boolean validaCamposConsig(Map<String, Object> body ,List<?> classes);
	public boolean save(EntidadeConsig entidadeConsig, EnderecoOrganizacao enderecoOrg);
}
