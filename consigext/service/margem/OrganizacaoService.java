package br.mil.fab.consigext.service.margem;

import java.util.List;

import br.mil.fab.consigext.entity.Organizacao;
import br.mil.fab.util.sigpes.entity.OrganizacaoResponse;

public interface OrganizacaoService {

		OrganizacaoResponse getOmMilitar(String matricula);
		List<Organizacao> getOrganizacoes();
}
