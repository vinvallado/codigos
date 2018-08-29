package br.mil.fab.pessoa.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mil.fab.pessoa.api.entity.PesfisComgep;
import br.mil.fab.pessoa.api.repository.PessoaFisicaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaFisicaRepository pRep;
	
	public PesfisComgep findByNrCpf(String cpf) {
		return pRep.findByNrCpf(cpf);
	}

	
	
/*	

	@Autowired
	public PessoaService(BoletimDAO bRep, ComponenteMotivoRepository mRep,MessageSource messageSource,MotivoUtilizadoRepository muRep) {
		this.muRep = muRep;
		this.bRep = bRep;
		this.mRep = mRep;
		this.messageSource= messageSource;
	}
*/


}
