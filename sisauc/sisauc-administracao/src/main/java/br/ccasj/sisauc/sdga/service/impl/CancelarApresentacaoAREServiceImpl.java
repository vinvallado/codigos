package br.ccasj.sisauc.sdga.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.auditoriaretrospectiva.dao.AuditoriaRetrospectivaRessarcimentoDAO;
import br.ccasj.sisauc.emissaoar.dao.AutorizacaoRessarcimentoDAO;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.EstadoAR;
import br.ccasj.sisauc.emissaoar.domain.EstadoItemAR;
import br.ccasj.sisauc.emissaoar.domain.ItemAR;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.sdga.dao.AcaoSDGADAO;
import br.ccasj.sisauc.sdga.dao.CancelamentoAuditoriaRetrospectivaAREDAO;
import br.ccasj.sisauc.sdga.domain.CancelamentoApresentacaoRessarcimento;
import br.ccasj.sisauc.sdga.domain.CancelamentoAuditoriaRetrospectivaARE;
import br.ccasj.sisauc.sdga.service.CancelarApresentacaoAREService;

@Transactional
@Service(value = "cancelarApresentacaoAREServiceImpl")
public class CancelarApresentacaoAREServiceImpl implements CancelarApresentacaoAREService{

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private AutorizacaoRessarcimentoDAO autorizacaoRessarcimentoDAO;
	@Autowired
	private AuditoriaRetrospectivaRessarcimentoDAO auditoriaRetrospectivaDAO;
	@Autowired
	private CancelamentoAuditoriaRetrospectivaAREDAO cancelamentoAuditoriaRetrospectivaAREDAO;
	
	
	@Autowired
	private AcaoSDGADAO acaoSDGADAO;
	
	@Override
	public void cancelarApresentacao(CancelamentoApresentacaoRessarcimento cancelamento) {
		AutorizacaoRessarcimento are = autorizacaoRessarcimentoDAO.findById(cancelamento.getAr().getId());
		cancelamento.setAr(are);
		validarCancelamento(are.getItens());
		are.setEstado(EstadoAR.EMITIDA);
		List<Integer> auditoriasADeletar = atualizarItens(are.getItens());
		autorizacaoRessarcimentoDAO.merge(are);
		acaoSDGADAO.merge(cancelamento);
		entityManager.flush();
		deletarAuditoriasRetrospectivas(auditoriasADeletar);
	}
	
	private void validarCancelamento(Set<ItemAR> itens) {
		String prefixo = null;
		List<String> frase= new ArrayList<String>();
		for (ItemAR itemAR: itens){
			if (EstadoItemAR.REALIZADO.equals(itemAR.getEstadoItemAR())){
				String[] div = itemAR.getCodigo().split("-");
				frase.add(div[1]);
				if(prefixo==null){
					prefixo=div[0];
				}
			}
		}
		Collections.sort(frase);
		if(!frase.isEmpty()){
			throw new SystemRuntimeException("ARE não pode ser cancelada! O(s) seguinte(s) item(ens) estão no estado REALIZADO: " + prefixo + "-" +frase.toString());
		}
	}

	private List<Integer> atualizarItens(Set<ItemAR> itens) {
		List<Integer> auditoriasADeletar = new ArrayList<Integer>();
		for (ItemAR itemAR: itens){
			itemAR.setEstadoItemAR(EstadoItemAR.APROVADO);
			if (itemAR.getAuditoriaRetrospectiva() != null){
				auditoriasADeletar.add(itemAR.getAuditoriaRetrospectiva().getId());
			}
			itemAR.setAuditoriaRetrospectiva(null);
		}
		return auditoriasADeletar;
	}
	
	private void deletarAuditoriasRetrospectivas(List<Integer> auditoriasADeletar) {
		if (auditoriasADeletar.isEmpty()){
			return;
		}
//		deletarHistoricoAuditoriasRetrospectivasARE(auditoriasADeletar);
		cancelamentoAuditoriaRetrospectivaAREDAO.deletePelosIdsAuditorias(auditoriasADeletar);
		auditoriaRetrospectivaDAO.delete(auditoriasADeletar);
	}

//	private void deletarHistoricoAuditoriasRetrospectivasARE(List<Integer> auditoriasADeletar) {
//		String sql = "DELETE FROM HistoricoAuditoriaRetrospectiva WHERE auditoriaRetrospectiva.id IN :idsAuditorias";
//		entityManager.createQuery(sql).setParameter("idsAuditorias", auditoriasADeletar).executeUpdate();
//	}

}