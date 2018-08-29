package br.ccasj.sisauc.administracao.auditoriaretrospectiva.managedbean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.auditoriaprospectiva.dao.AuditoriaProspectivaDAO;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectiva;
import br.ccasj.sisauc.auditoriaretrospectiva.dao.MotivoDAO;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.AuditoriaRetrospectiva;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.MetadadoValorAuditoriaRetrospectiva;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.Motivo;
import br.ccasj.sisauc.auditoriaretrospectiva.service.AuditoriaRetrospectivaService;
import br.ccasj.sisauc.emissaogab.dao.GuiaApresentacaoBeneficiarioDAO;
import br.ccasj.sisauc.emissaogab.dao.ItemGABDAO;
import br.ccasj.sisauc.emissaogab.domain.EstadoItemGAB;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;

@Scope(value = "view")
@Service(value = "auditoriaRetrospectivaFormularioBean")
public class AuditoriaRetrospectivaFormularioBean implements Serializable {
	
	private static final long serialVersionUID = 5291975828963701158L;

	@Autowired
	private ItemGABDAO itemGABDAO;
	
	@Autowired
	private GuiaApresentacaoBeneficiarioDAO gabDAO;
	@Autowired
	private AuditoriaProspectivaDAO auditoriaProspectivaDAO;
	@Autowired
	private MotivoDAO motivoDAO;
	@Autowired
	private AuditoriaRetrospectivaService auditoriaRetrospectivaService;
	
	private AuditoriaRetrospectiva auditoriaRetrospectiva;
	private ItemGAB itemGAB;
	private GuiaApresentacaoBeneficiario gab;
	private AuditoriaProspectiva auditoriaProspectiva;
	private boolean auditoriaAceitaCredenciado;
	private List<Motivo> motivos;
	
	@PostConstruct
	private void init() {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		Integer numeroItemGAB = Integer.parseInt(id);
		itemGAB = itemGABDAO.obterItemGABComMetadados(numeroItemGAB);
		gab = gabDAO.obterPeloItemGABId(numeroItemGAB);
		auditoriaProspectiva = auditoriaProspectivaDAO.obterAuditoriaPorIdComProcedimentosESolicitacao(gab.getAuditoriaProspectiva().getId());
		motivos = motivoDAO.obterListaMotivos();
		validarEstadoDoItem();
	}
	
	private void validarEstadoDoItem() {
		if ((this.getItemGAB().getEstadoItemGAB() != EstadoItemGAB.AGUARDANDO_AUDITORIA) && (this.getItemGAB().getEstadoItemGAB() != EstadoItemGAB.AUDITORIA_INICIADA) && (this.getItemGAB().getEstadoItemGAB() != EstadoItemGAB.NAO_CONFORME)) {
			ManagedBeanUtils.redirecionar("/auditoria-retrospectiva/realizar-auditoria");
			throw new SystemRuntimeException("Auditoria não permitida!");
		}
	}
	
	public void auditoriaAceitaCredenciado() {
		auditoriaRetrospectiva = itemGAB.getAuditoriaRetrospectiva();
		if (auditoriaAceitaCredenciado) {
			auditoriaRetrospectiva.setValorFinal(auditoriaRetrospectiva.getValorAuditado());
			auditoriaRetrospectiva.setJustificativaValorFinal("Auditoria aceita pelo Credenciado.");	
		} else {
			auditoriaRetrospectiva.setValorFinal(0.00);
			auditoriaRetrospectiva.setJustificativaValorFinal("");
		}
	}
	
	public void salvarItem(AuditoriaRetrospectiva auditoria){
		auditoriaRetrospectivaService.salvarSemFinalizar(auditoria, itemGAB);
		ManagedBeanUtils.redirecionar("/auditoria-retrospectiva/realizar-auditoria");
		Mensagem.informacao("Item " + itemGAB.getCodigo() + " salvo com sucesso!");
	}
	
	public void finalizarItemConforme(AuditoriaRetrospectiva auditoria){
		auditoriaRetrospectivaService.finalizarAuditoriaConforme(auditoria, itemGAB);
		ManagedBeanUtils.hideDialog("finalizarConformeDialog");
		ManagedBeanUtils.redirecionar("/auditoria-retrospectiva/realizar-auditoria");
		Mensagem.informacao("Item " +  itemGAB.getCodigo() + " auditado como CONFORME!");
	}

	public void finalizarItemNaoConforme(AuditoriaRetrospectiva auditoria){
		auditoriaRetrospectivaService.finalizarAuditoriaNaoConforme(auditoria, itemGAB);
		ManagedBeanUtils.hideDialog("finalizarNaoConformeDialog");
		ManagedBeanUtils.redirecionar("/auditoria-retrospectiva/realizar-auditoria");
		Mensagem.informacao("Item " +  itemGAB.getCodigo() + " auditado como NÃO CONFORME!");
	}
	
	public void copiarValorAuditado(){
		AuditoriaRetrospectiva auditoria = itemGAB.getAuditoriaRetrospectiva();
		auditoria.setValorFinal(auditoria.getValorAuditado());
	}
	
	public Set<MetadadoValorAuditoriaRetrospectiva> getValoresAuditoriaFiltrado() {
		Set<MetadadoValorAuditoriaRetrospectiva> valores = new HashSet<MetadadoValorAuditoriaRetrospectiva>();
		for (MetadadoValorAuditoriaRetrospectiva metadado: itemGAB.getAuditoriaRetrospectiva().getValores()){
			if (!StringUtils.isEmpty(metadado.getDescricao())){
				valores.add(metadado);
			}
		}
		return valores;
	}
	
	public void confirmaSalvarItem() {
		ManagedBeanUtils.showDialog("salvarAuditoriaItemGABbDialog");	
	}
	
	public void confirmaAuditoriaConforme(AuditoriaRetrospectiva auditoria) {
		ManagedBeanUtils.showDialog("finalizarConformeDialog");	
	}
	
	public void confirmaAuditoriaNaoConforme(AuditoriaRetrospectiva auditoria) {
		ManagedBeanUtils.showDialog("finalizarNaoConformeDialog");	
	}
	
	
	public AuditoriaRetrospectiva getAuditoriaRetrospectiva() {
		return auditoriaRetrospectiva;
	}

	public void setAuditoriaRetrospectiva(AuditoriaRetrospectiva auditoriaRetrospectiva) {
		this.auditoriaRetrospectiva = auditoriaRetrospectiva;
	}

	public ItemGAB getItemGAB() {
		return itemGAB;
	}

	public void setItemGAB(ItemGAB itemGAB) {
		this.itemGAB = itemGAB;
	}

	public GuiaApresentacaoBeneficiario getGab() {
		return gab;
	}

	public void setGab(GuiaApresentacaoBeneficiario gab) {
		this.gab = gab;
	}

	public AuditoriaProspectiva getAuditoriaProspectiva() {
		return auditoriaProspectiva;
	}

	public void setAuditoriaProspectiva(AuditoriaProspectiva auditoriaProspectiva) {
		this.auditoriaProspectiva = auditoriaProspectiva;
	}

	public boolean isAuditoriaAceitaCredenciado() {
		return auditoriaAceitaCredenciado;
	}

	public void setAuditoriaAceitaCredenciado(boolean auditoriaAceitaCredenciado) {
		this.auditoriaAceitaCredenciado = auditoriaAceitaCredenciado;
	}

	public List<Motivo> getMotivos() {
		return motivos;
	}

	public void setMotivos(List<Motivo> motivos) {
		this.motivos = motivos;
	}
	
}
