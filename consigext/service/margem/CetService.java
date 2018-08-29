package br.mil.fab.consigext.service.margem;

import java.math.BigDecimal;
import java.util.List;

import br.mil.fab.consigext.entity.Cet;
import br.mil.fab.consigext.entity.ServicoConsig;

public interface CetService {
	public Cet findByServicoConsigAndNrParcelaAndCdAnomes(ServicoConsig sc, long nrParcela, long cdAnomes);
	
	public void setVlCet(Cet cet, ServicoConsig servicoConsig, BigDecimal vlCet, long cdAnomes, long nrParcela);

	public List<List<Cet>> getCetsOfServicoConsigOrganized(int mes, int ano, int isYearPlus1, int nCetsMax, List<Cet> cetsOfservicoConsig);
}
