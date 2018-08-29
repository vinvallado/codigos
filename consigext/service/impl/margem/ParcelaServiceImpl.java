package br.mil.fab.consigext.service.impl.margem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mil.fab.consigext.entity.Consignacao;
import br.mil.fab.consigext.entity.ParcelaConsignacao;
import br.mil.fab.consigext.repository.ParcelaRepository;
import br.mil.fab.consigext.service.margem.ParcelaService;

@Service
public class ParcelaServiceImpl implements ParcelaService{

	@Autowired
	ParcelaRepository parcelaRepo;
	
	@Override
	public void save(ParcelaConsignacao parcela) {
		parcelaRepo.save(parcela);
	}

	@Override
	public void save(List<ParcelaConsignacao> parcelas) {
		parcelaRepo.save(parcelas);
	}

	@Override
	public List<ParcelaConsignacao> findByConsignacao(Consignacao consignacao) {
		return parcelaRepo.findByConsignacao(consignacao);
	}
	
	@Override
	public double sumParcelas(List<ParcelaConsignacao> parcelas) {
		if(parcelas==null || parcelas.isEmpty())
			return 0;
		double sum =0;
		for(ParcelaConsignacao parcela: parcelas) {
			sum+=parcela.getVlParcela().doubleValue();
		}
		return sum;
	}
}
