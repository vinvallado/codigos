package br.mil.fab.consigext.service.impl.margem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mil.fab.consigext.entity.ServidorConsig;
import br.mil.fab.consigext.entity.UsuarioConsig;
import br.mil.fab.consigext.repository.ServidorConsigRepository;
import br.mil.fab.consigext.service.margem.ServidorConsigService;

@Service
public class ServidorConsigServiceImpl implements ServidorConsigService{
	
	@Autowired
	ServidorConsigRepository servidorConsigRepository;
	
	@Override
	public List<ServidorConsig> findAll() {
		return servidorConsigRepository.findAll();
	}

	@Override
	public ServidorConsig findById(Long id) {
		return servidorConsigRepository.findById(id);
	}

	@Override
	public void setStServidorOfServidor(long newSt, ServidorConsig servidorConsig) {
		servidorConsig.setStServidor(newSt);
		servidorConsigRepository.save(servidorConsig);
	}
	
	@Override
	public ServidorConsig findByMatricula(String nrOrdem) {
		return servidorConsigRepository.findByMatricula(nrOrdem);
	}

	@Override
	public ServidorConsig findByCpf(String cpf) {
		return servidorConsigRepository.findByPesfis_NrCpf(cpf);
	}
	
	@Override
	public ServidorConsig findByMatriculaOrCpf(String matricula, String cpf) {
		return  servidorConsigRepository.findByMatriculaOrCpf(matricula, cpf);
	}
}
