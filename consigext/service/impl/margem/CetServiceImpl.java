package br.mil.fab.consigext.service.impl.margem;

import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.mil.fab.consigext.entity.Cet;
import br.mil.fab.consigext.entity.ServicoConsig;
import br.mil.fab.consigext.repository.CetRepository;
import br.mil.fab.consigext.service.margem.CetService;

@Service
public class CetServiceImpl implements CetService{
	@Autowired
	CetRepository cetRepository;
	
	
	@Override
	public Cet findByServicoConsigAndNrParcelaAndCdAnomes(ServicoConsig sc, long nrParcela, long cdAnoMes) {
		return cetRepository.findByServicoConsigAndNrParcelaAndCdAnomes(sc, nrParcela, cdAnoMes);
	}
	@Override
	public void setVlCet(Cet cet, ServicoConsig servicoConsig, BigDecimal vlCet, long cdAnomes, long nrParcela) {
		if(cet==null) {
			cet = new Cet();
			cet.setCdAnomes(cdAnomes);
			cet.setNrParcela(nrParcela);	
			cet.setServicoConsig(servicoConsig);
		}
		cet.setVlCet(vlCet);
		cetRepository.save(cet);
	}
	@Override
	public List<List<Cet>> getCetsOfServicoConsigOrganized(int mes, int ano, int isYearPlus1, int nCetsMax, List<Cet> cetsOfservicoConsig) {
		List<List<Cet>> cetsOfservicoConsigOrganized = new ArrayList<List<Cet>>();
		List<Cet> aux = new ArrayList<Cet>();
		int nCets=0;
		for(int i=0;nCets<nCetsMax;i++) {
			if(nCets!=0 && nCets%10==0) {				
				cetsOfservicoConsigOrganized.add(aux);
				aux=new ArrayList<Cet>();
			}
			if(i<cetsOfservicoConsig.size() && cetsOfservicoConsig.get(i).getCdAnomes()==(ano+isYearPlus1)*100+mes+1) {
				aux.add(cetsOfservicoConsig.get(i));
				nCets++;
			}
			if(i>=cetsOfservicoConsig.size()) {		
				Cet cet =new Cet();
				cet.setCdAnomes((ano+isYearPlus1)*100+mes+1);
				cet.setNrParcela(nCets+1);
				cet.setVlCet(new BigDecimal(0));
				aux.add(cet);
				nCets++;				
			}			
		}
		if(aux.isEmpty()==false)
			cetsOfservicoConsigOrganized.add(aux);
		return cetsOfservicoConsigOrganized;
	}
}
