package br.mil.fab.consigext.service.margem;

import java.util.List;

import br.mil.fab.consigext.entity.ServidorConsig;

public interface ServidorConsigService {
	
	public List<ServidorConsig> findAll();
	public ServidorConsig findById(Long id);

	public ServidorConsig findByMatricula  (String nrOrdem);
	public ServidorConsig findByCpf  (String cpf);
	public ServidorConsig findByMatriculaOrCpf(String matricula, String cpf);
	void setStServidorOfServidor(long newSt, ServidorConsig servidorConsig);

}
