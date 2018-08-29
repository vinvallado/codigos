package br.mil.fab.consigext.service.margem;


import java.util.List;

import br.mil.fab.consigext.entity.EntidadeConsig;
import br.mil.fab.consigext.entity.ParametroServicoConsig;
import br.mil.fab.consigext.entity.Servico;
import br.mil.fab.consigext.entity.ServicoConsig;

public interface ServicoConsigService {
	
	public String retornaValorMargemServico(Long idEntidade, Long idServico, String sigla);
	
	public ServicoConsig findById(Long id);
	public ServicoConsig findByServicoAndEntidadeConsig(Servico servico, EntidadeConsig entidadeConsig);
	public void setStServiceOfServiceConsig(long newSt, ServicoConsig servicoConsig);
	public String retornaValMargem30Servidor(String matricula);

	public List<ServicoConsig> getServicosConsigWithCet(EntidadeConsig entidadeConsig);
	
	public List<ServicoConsig> getServicosRenegociacao(ServicoConsig servicoConsig);

	ParametroServicoConsig getParametroServicoConsigsBySiglaParametro(ServicoConsig servicoConsig, String siglaParametro);

	boolean isServicoBlockedOrExcludedToConsig(Long idConsignataria, Long idServico);

	public List<Servico> getServicosAtivosParaConsig(EntidadeConsig entidadeConsig);

}
