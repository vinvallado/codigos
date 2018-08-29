package br.mil.fab.boletim.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.mil.fab.boletim.api.entity.ComponenteMotivo;
import br.mil.fab.boletim.api.entity.MotivoUtilizado;
import br.mil.fab.boletim.api.request.ItemBoletimRequest;
import br.mil.fab.boletim.api.response.ItemBoletimResponse;
import br.mil.fab.boletim.api.service.BoletimService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/boletim")
public class BoletimController {
	@Autowired
	BoletimService bService;


	@RequestMapping(value = "/inserir-boletim", method = RequestMethod.POST)
	@ApiOperation(value = "Inserir itens de Boletins", notes = "Inserção de itens Boletins em lote")
	public List<ItemBoletimResponse> inserirBoletim(@RequestBody List<ItemBoletimRequest> boletim) {
		return  bService.inserirBoletim(boletim);
	}

	

	@RequestMapping(value = "/buscar-componentes-por-motivo/{motivo}", method = RequestMethod.GET)
	@ApiOperation(value = "Buscar Componente pelo motivo", notes = "Buscar Componente pelo motivo")
	public List<ComponenteMotivo> buscarComponenteByMotivo(@PathVariable("motivo") String motivo) {
		return  bService.buscarComponenteMotivoByMotivo(motivo);
	}
	
	@RequestMapping(value = "/buscar-item-por-processo/{modulo}/{processo}", method = RequestMethod.GET)
	@ApiOperation(value = "Buscar o Item de Boletim pelo Processo e Módulo de sistema", notes = "Buscar o Item de Boletim pelo Processo e Módulo de sistema")
	public List<MotivoUtilizado> buscarMotivoUtilizadoByIdProcesso(@PathVariable("processo") String idProcesso,@PathVariable("modulo") String idModulo) {
		return  bService.buscarMotivoUtilizadoByIdProcesso(idProcesso, idModulo);
	}
	

	@ExceptionHandler
	public List<ItemBoletimResponse> handleException(Exception exception) {
		Logger.getLogger("BoletimController").log(Level.SEVERE,exception.getLocalizedMessage());
		List<ItemBoletimResponse> list = new ArrayList<>();
		ItemBoletimResponse resp = new ItemBoletimResponse();
		resp.setCode("500");
		resp.setMessage(exception.getLocalizedMessage());
		resp.setIdLancamento("");
		resp.setIdModulo(0);
		resp.setIdProcesso(0);
		list.add(resp);
		return list;
	}


	
}
