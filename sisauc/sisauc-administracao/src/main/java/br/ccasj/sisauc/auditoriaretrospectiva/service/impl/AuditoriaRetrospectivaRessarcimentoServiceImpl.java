package br.ccasj.sisauc.auditoriaretrospectiva.service.impl;

import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.auditoriaretrospectiva.domain.AuditoriaRetrospectivaRessarcimento;
import br.ccasj.sisauc.auditoriaretrospectiva.service.AuditoriaRetrospectivaRessarcimentoService;
import br.ccasj.sisauc.emissaoar.dao.AutorizacaoRessarcimentoDAO;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.EspecificacaoItemARE;
import br.ccasj.sisauc.emissaoar.domain.EstadoAR;
import br.ccasj.sisauc.emissaoar.domain.EstadoItemAR;
import br.ccasj.sisauc.emissaoar.domain.ItemAR;
import br.ccasj.sisauc.framework.context.AuthenticationContext;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;

@Service("auditoriaRetrospectivaRessarcimentoService")
@Transactional
public class AuditoriaRetrospectivaRessarcimentoServiceImpl implements AuditoriaRetrospectivaRessarcimentoService {

	@Autowired
	private AuthenticationContext authenticationContext;
	
	@Autowired
	private AutorizacaoRessarcimentoDAO autorizacaoRessarcimentoDAO;
	
	@Override
	public void salvarSemFinalizar(AutorizacaoRessarcimento ar) {
		ar.setEstado(EstadoAR.EM_AUDITORIA);
		atualizarEstadoItens(ar.getItens());
		autorizacaoRessarcimentoDAO.merge(ar);
	}

	@Override
	public void validarFinalizacao(AutorizacaoRessarcimento ar) {
		for (ItemAR item: ar.getItens()){
			if (!EstadoItemAR.NAO_APROVADO.equals(item.getEstadoItemAR()) && item.getAuditoriaRetrospectiva() == null){
				throw new SystemRuntimeException("É obrigatório auditar todos os itens.");
			}
		}
	}

	@Override
	public void finalizar(AutorizacaoRessarcimento ar) {
		ar.setEstado(EstadoAR.AUDITADA);
		atualizarEstadoItens(ar.getItens());
		autorizacaoRessarcimentoDAO.merge(ar);
	}
	
	private void atualizarEstadoItens(Set<ItemAR> itens) {
		for (ItemAR item: itens){
			
			if (EstadoItemAR.NAO_APROVADO.equals(item.getEstadoItemAR())){
				continue;
			}
			
			if (item.getAuditoriaRetrospectiva() == null){
				item.setEstadoItemAR(EstadoItemAR.EM_AUDITORIA);
			} else if (item.getAuditoriaRetrospectiva().getNaoRealizado()){
				item.setEstadoItemAR(EstadoItemAR.NAO_REALIZADO);
				item.getAuditoriaRetrospectiva().setAuditorRetrospectivo(authenticationContext.getUsuarioLogado());
			} else {
				item.setEstadoItemAR(EstadoItemAR.REALIZADO);
				item.getAuditoriaRetrospectiva().setAuditorRetrospectivo(authenticationContext.getUsuarioLogado());
			}
		}
	}

	@Override
	public void salvarAuditoriaItemAR(ItemAR itemSelecionado, AuditoriaRetrospectivaRessarcimento auditoriaRetrospectiva) {
		if (auditoriaRetrospectiva.getNaoRealizado() && StringUtils.isEmpty(auditoriaRetrospectiva.getJustificativa())){
			throw new SystemRuntimeException("A justificativa é obrigatória para itens não realizados.");
		}
		
		itemSelecionado.setAuditoriaRetrospectiva(auditoriaRetrospectiva);

		Double valorApresentado = 0.0d;
		for(EspecificacaoItemARE valoEspecificacaoItemARE : itemSelecionado.getAuditoriaRetrospectiva().getEspecificacoes()){
			valorApresentado += valoEspecificacaoItemARE.getValorApresentado();
		}
		itemSelecionado.getAuditoriaRetrospectiva().setValorApresentado(valorApresentado);
	}

}