package br.mil.fab.consigext.service.margem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import br.mil.fab.consigext.dto.CetConsigRanking;
import br.mil.fab.consigext.util.UsuarioLogado;

public interface CetConsigRankingService {

	public List<CetConsigRanking> findCets(long nrPrestacoes, long anomes);
	public List<CetConsigRanking> montarRankingCets(UsuarioLogado usrLogado, Double margem, Long nrParcelas, BigDecimal vlLiquidoLib, BigDecimal vlPrestacao);
	public List<CetConsigRanking> montarRankingCets(UsuarioLogado usrLogado, Map<String,Object> body);
}
