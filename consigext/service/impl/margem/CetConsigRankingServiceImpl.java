package br.mil.fab.consigext.service.impl.margem;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mil.fab.consigext.dto.CetConsigRanking;
import br.mil.fab.consigext.entity.Servico;
import br.mil.fab.consigext.repository.CetConsigRankingRepository;
import br.mil.fab.consigext.repository.ServicoRepository;
import br.mil.fab.consigext.service.margem.CetConsigRankingService;
import br.mil.fab.consigext.util.GenericUtil;
import br.mil.fab.consigext.util.ParcelaUtil;
import br.mil.fab.consigext.util.UsuarioLogado;

@Service
public class CetConsigRankingServiceImpl implements CetConsigRankingService{

	@Autowired
	CetConsigRankingRepository cetConsigRankRepo;
	
	@Autowired
	ServicoRepository servicoRepo;
	
	
	@Override
	public List<CetConsigRanking> findCets(long nrPrestacoes, long anomes) {
		return cetConsigRankRepo.findCets(nrPrestacoes, anomes);
	}

	@Override
	public List<CetConsigRanking> montarRankingCets(UsuarioLogado usrLogado,  Map<String,Object> body) {
		try {
			List<CetConsigRanking> cets = findCets(GenericUtil.getLongInBody(body,"numPrestSelect"), Long.parseLong(GenericUtil.getAAAAMMtual()));
			cets.forEach(c -> {
					c.setVlLiquidoLib(GenericUtil.getBigDecimalInBody(body, "vlLiquidoLib"));
					c.setVlPrestacao(GenericUtil.getBigDecimalInBody(body, "vlPrestacao"));
					c.setAutorizado(
							ParcelaUtil.autorizaValorParcela(GenericUtil.getBigDecimalInBody(body, "valMargem30"), c, GenericUtil.getLongInBody(body,"numPrestSelect")));
			});
			if(usrLogado.isServidor()){
				Servico servico = servicoRepo.findByCdServico("14");
				return cets.stream().filter(c -> c.getVlCet() > 0 && c.getIdServico().equals(servico.getId())).collect(Collectors.toList());
			}
			else
				return cets.stream().filter(c -> c.getVlCet() > 0 ).collect(Collectors.toList());
			 
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<CetConsigRanking> montarRankingCets(UsuarioLogado usrLogado, Double margem, Long nrParcelas,
			BigDecimal vlLiquidoLib, BigDecimal vlPrestacao) {
			List<CetConsigRanking> cets = findCets(nrParcelas, Long.parseLong(GenericUtil.getAAAAMMtual()));
			cets.forEach(c -> {
				c.setVlLiquidoLib(vlLiquidoLib);
				c.setVlPrestacao(vlPrestacao);
				c.setAutorizado(ParcelaUtil.autorizaValorParcela(new BigDecimal(margem, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP), 
																	c, nrParcelas));
			});
		return cets;
	}
}
