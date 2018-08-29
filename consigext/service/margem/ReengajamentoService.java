package br.mil.fab.consigext.service.margem;

import br.mil.fab.consigext.dto.Reengajamento;

public interface ReengajamentoService {

	public Reengajamento retornaProximaDataReengajamento(String nrOrdem);
}
