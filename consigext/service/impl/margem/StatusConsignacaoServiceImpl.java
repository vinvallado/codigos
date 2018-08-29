package br.mil.fab.consigext.service.impl.margem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mil.fab.consigext.entity.StatusConsignacao;
import br.mil.fab.consigext.repository.StatusConsignacaoRepository;
import br.mil.fab.consigext.service.margem.StatusConsignacaoService;
@Service
public class StatusConsignacaoServiceImpl implements StatusConsignacaoService{

	@Autowired
	StatusConsignacaoRepository statusonsigRepo;
		
	@Override
	public StatusConsignacao getStatusByNmStatus(String nome) {
		StatusConsignacao statusConsig = statusonsigRepo.findByNmStatus(nome);
		return statusConsig;  
	}	
}
