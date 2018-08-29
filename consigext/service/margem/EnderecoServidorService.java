package br.mil.fab.consigext.service.margem;

import br.mil.fab.consigext.dto.EnderecoPessoaFisica;

public interface EnderecoServidorService {

	EnderecoPessoaFisica findByEnderecoAtualCompleto(String matricula);
}
