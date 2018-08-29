package br.mil.fab.consigext.service.impl.margem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mil.fab.consigext.dto.EnderecoPessoaFisica;
import br.mil.fab.consigext.repository.EnderecoPessoaFisicaRespository;
import br.mil.fab.consigext.service.margem.EnderecoServidorService;

@Service
public class EnderecoServidorServiceImpl implements EnderecoServidorService{

	@Autowired
	EnderecoPessoaFisicaRespository enderecoRepo;
	
	@Override
	public EnderecoPessoaFisica findByEnderecoAtualCompleto(String matricula) {
		return enderecoRepo.findByEnderecoAtualCompleto(matricula);
	}

	
}
