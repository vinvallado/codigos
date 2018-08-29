package br.mil.fab.pessoa.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.mil.fab.pessoa.api.entity.PesfisComgep;
import br.mil.fab.pessoa.api.service.MilitarService;
import br.mil.fab.pessoa.api.service.PessoaService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

	@Autowired
	private MilitarService mService;
	
	@Autowired
	private PessoaService pService;
	
	@RequestMapping(value = "/fisica/cpf/{cpf}", method = RequestMethod.GET)
	@ApiOperation(value = "CPF", notes = "Consulta de dados de pessoas físicas por CPF")
	public PesfisComgep consultaPessoaByCpf(@PathVariable("cpf") String cpf) {
	return  pService.findByNrCpf(cpf);
	}
	
	@RequestMapping(value = "/militar/matricula/{matricula}", method = RequestMethod.GET)
	@ApiOperation(value = "MATRICULA", notes = "Consulta de dados de militar por Matrícula")
	public PesfisComgep consultaMilitarByMatricula(@PathVariable("matricula") String matricula) {
		return  mService.findByNrOrdem(matricula);
	}
	
	@RequestMapping(value = "/militar/cpf/{cpf}", method = RequestMethod.GET)
	@ApiOperation(value = "CPF", notes = "Consulta de dados de militar por CPF")
	public PesfisComgep consultaMilitarByCpf(@PathVariable("cpf") String cpf) {
		return  mService.findByNrCpf(cpf);
	}
}
