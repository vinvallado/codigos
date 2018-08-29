package br.ccasj.sisauc.sdga.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.auditoriaprospectiva.dao.AuditoriaProspectivaRessarcimentoDAO;
import br.ccasj.sisauc.auditoriaretrospectiva.dao.GerenciarLoteRessarcimentoDAO;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.EstadoAR;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.sdga.service.CancelarAuditoriaRetrospectivaAREService;

@Transactional
@Service(value = "cancelarAuditoriaRetrospectivaAREServiceImpl")
public class CancelarAuditoriaRetrospectivaAREServiceImpl implements CancelarAuditoriaRetrospectivaAREService {

	@Autowired
	private GerenciarLoteRessarcimentoDAO loteRessarcimentoDAO;
	
	@Autowired
	private AuditoriaProspectivaRessarcimentoDAO auditoriaProspectivaRessarcimentoDAO;
	
	@Override
	public void cancelarAuditoriaRetrospectivaARE(AutorizacaoRessarcimento are, String justificativa) {
		verificarSeAuditoriaAREPodeSerCancelada(are);
		auditoriaProspectivaRessarcimentoDAO.cancelarAuditoriaProspectivaRessarcimento(are, justificativa);
	}
	
	public void verificarSeAuditoriaAREPodeSerCancelada(AutorizacaoRessarcimento are) {
		if(!EstadoAR.AUDITADA.equals(are.getEstado())){
			throw new SystemRuntimeException("Apenas AREs no estado Auditadas podem ter a Auditoria Restrspectiva cancelada.");
		}	
		List<String> numerosLoteList = loteRessarcimentoDAO.obterCodigosLotesDeUmaArePeloId(are.getId());
		if(numerosLoteList.size() > 0){
			StringBuilder numerosLote = new StringBuilder();
			for (String numeroLote : numerosLoteList) {
				numerosLote.append(numeroLote);
				numerosLote.append(" ");
			}
			throw new SystemRuntimeException("A auditoria não pode ser cancelada pois a ARE " + are.getCodigo() + " contém itens no(s) lote(s) " + numerosLote.toString() + ".");
		}			
	}
	
}
