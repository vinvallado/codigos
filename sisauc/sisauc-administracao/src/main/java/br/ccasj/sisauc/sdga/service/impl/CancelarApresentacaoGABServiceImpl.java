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

import br.ccasj.sisauc.auditoriaretrospectiva.dao.AuditoriaRetrospectivaDAO;
import br.ccasj.sisauc.emissaogab.dao.GuiaApresentacaoBeneficiarioDAO;
import br.ccasj.sisauc.emissaogab.domain.EstadoGAB;
import br.ccasj.sisauc.emissaogab.domain.EstadoItemGAB;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.sdga.dao.AcaoSDGADAO;
import br.ccasj.sisauc.sdga.domain.CancelamentoApresentacaoGAB;
import br.ccasj.sisauc.sdga.service.CancelarApresentacaoGABService;

@Transactional
@Service(value = "cancelarApresentacaoGABServiceImpl")
public class CancelarApresentacaoGABServiceImpl implements CancelarApresentacaoGABService{

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private GuiaApresentacaoBeneficiarioDAO guiaApresentacaoBeneficiarioDAO;
	@Autowired
	private AuditoriaRetrospectivaDAO auditoriaRetrospectivaDAO;
	@Autowired
	private AcaoSDGADAO acaoSDGADAO;
	
	@Override
	public void cancelarApresentacao(CancelamentoApresentacaoGAB cancelamento) {
		GuiaApresentacaoBeneficiario gab = guiaApresentacaoBeneficiarioDAO.findById(cancelamento.getGab().getId());
		validarCancelamento(gab.getItens());
		gab.setEstado(EstadoGAB.EMITIDA);
		List<Integer> auditoriasADeletar = atualizarItens(gab.getItens());
		guiaApresentacaoBeneficiarioDAO.merge(gab);
		acaoSDGADAO.merge(cancelamento);
		entityManager.flush();
		deletarAuditoriasRetrospectivas(auditoriasADeletar);
	}
	
	private void validarCancelamento(Set<ItemGAB> itens) {
		String prefixo = null;
		List<String> frase= new ArrayList<String>();
		for (ItemGAB itemGAB: itens){
			if (EstadoItemGAB.CONFORME.equals(itemGAB.getEstadoItemGAB())){
				String[] div = itemGAB.getCodigo().split("-");
				frase.add(div[1]);
				if(prefixo==null){
					prefixo=div[0];
				}
			}
		}
		Collections.sort(frase);
		if(!frase.isEmpty()){
			throw new SystemRuntimeException("GAB não pode ser cancelada! O(s) seguinte(s) item(ens) estão no estado CONFORME: "+prefixo+"-" +frase.toString());			
		}
	}

	private List<Integer> atualizarItens(Set<ItemGAB> itens) {
		List<Integer> auditoriasADeletar = new ArrayList<Integer>();
		for (ItemGAB itemGAB: itens){
			itemGAB.setEstadoItemGAB(EstadoItemGAB.CRIADO);
			if (itemGAB.getAuditoriaRetrospectiva() != null){
				auditoriasADeletar.add(itemGAB.getAuditoriaRetrospectiva().getId());
			}
			itemGAB.setAuditoriaRetrospectiva(null);
		}
		return auditoriasADeletar;
	}
	
	private void deletarAuditoriasRetrospectivas(List<Integer> auditoriasADeletar) {
		if (auditoriasADeletar.isEmpty()){
			return;
		}
		deletarHistoricoAuditoriasRetrospectivas(auditoriasADeletar);
		auditoriaRetrospectivaDAO.delete(auditoriasADeletar);
	}

	private void deletarHistoricoAuditoriasRetrospectivas(List<Integer> auditoriasADeletar) {
		String sql = "DELETE FROM HistoricoAuditoriaRetrospectiva WHERE auditoriaRetrospectiva.id IN :idsAuditorias";
		entityManager.createQuery(sql).setParameter("idsAuditorias", auditoriasADeletar).executeUpdate();
	}

}