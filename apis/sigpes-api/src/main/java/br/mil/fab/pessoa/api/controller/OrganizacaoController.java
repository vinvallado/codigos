package br.mil.fab.pessoa.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.mil.fab.pessoa.api.entity.Organizacao;
import br.mil.fab.pessoa.api.service.OrganizacaoService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/org")
public class OrganizacaoController {
	@Autowired
	private OrganizacaoService oService;
	
	@RequestMapping(value = "/{org}", method = RequestMethod.GET)
	@ApiOperation(value = "Código da Organização", notes = "Consulta organização do indivíduo por CPF")
	public Organizacao consultaOrganizacaoByCpf(@PathVariable("org") String cdOrg) {
		return  oService.findByCdOrg(cdOrg);
	}
}
