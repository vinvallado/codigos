package br.mil.fab.consigext.service.margem;

import java.util.List;
import java.util.Map;

import br.mil.fab.consigext.entity.Consignacao;
import br.mil.fab.consigext.entity.HistoricoConsignacao;
import br.mil.fab.consigext.entity.StatusConsignacao;
import br.mil.fab.consigext.entity.TipoHistoricoConsig;

public interface HistoricoConsignacaoService {
	void save(HistoricoConsignacao historicos);
	void save(List<HistoricoConsignacao> historicos);
	public HistoricoConsignacao salvarHistorico (Consignacao consig, String txColuna, String valAtual, StatusConsignacao status, TipoHistoricoConsig tipoHistorico, Map<String, Object> body);
	public List<HistoricoConsignacao> getHistoricoByConsignacaoAndTipoHistorico(Consignacao consignacao, long i);
	public HistoricoConsignacao findHistoricoLiquidado(Long idConsig);
	public HistoricoConsignacao findUltimoHistorico(Long idConsig);
}
