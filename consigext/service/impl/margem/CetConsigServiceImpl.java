package br.mil.fab.consigext.service.impl.margem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mil.fab.consigext.dto.CetConsig;
import br.mil.fab.consigext.repository.CetConsigRepository;
import br.mil.fab.consigext.service.margem.CetConsigService;

@Service
public class CetConsigServiceImpl implements CetConsigService{
	
	@Autowired
	CetConsigRepository cetConsigrepository;
	
	@Override
	public List<CetConsig> findCetConsig(Long id, long nrPrestacoes, long anomes){
		return cetConsigrepository.findCetConsig(id, nrPrestacoes, anomes);
	}

	@Override
	public List<CetConsig> findCets(long nrPrestacoes, long anomes) {
		return cetConsigrepository.findCets(nrPrestacoes, anomes);
	}


}
