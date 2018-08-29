package br.mil.fab.pessoa.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mil.fab.pessoa.api.entity.PesfisComgep;
import br.mil.fab.pessoa.api.repository.MilitarRepository;

@Service
public class MilitarService {
 
	@Autowired 
 	private MilitarRepository mRep;
	
	public PesfisComgep findByNrOrdem(String nrOrdem){
		return mRep.findByNrOrdem(nrOrdem);
	}

	public PesfisComgep findByNrCpf(String cpf) {
		return mRep.findByNrCpf(cpf);
	}
}
