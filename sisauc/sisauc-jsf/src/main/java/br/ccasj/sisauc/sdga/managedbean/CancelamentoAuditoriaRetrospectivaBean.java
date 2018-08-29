package br.ccasj.sisauc.sdga.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.emissaogab.dao.ItemGABDAO;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;
import br.ccasj.sisauc.sdga.domain.CancelamentoAuditoriaRetrospectiva;
import br.ccasj.sisauc.sdga.service.CancelarAuditoriaRetrospectivaService;

@Scope(value = "view")
@Service(value = "cancelamentoAuditoriaRetrospectivaBean")
public class CancelamentoAuditoriaRetrospectivaBean implements Serializable {
	
	private static final long serialVersionUID = -5920076290921691386L;

	@Autowired
	private ItemGABDAO itemGABDAO;
	@Autowired
	private CancelarAuditoriaRetrospectivaService cancelarAuditoriaRetrospectivaService;

	private List<ItemGAB> listaItens;
	private CancelamentoAuditoriaRetrospectiva cancelamento;
	private ItemGAB itemGAB;
	
	@PostConstruct
	private void init() {
		listaItens = itemGABDAO.obterItensGABParaCancelamentoAuditoriaRetrospectiva();
	}

	public void cancelarAuditoriaRetrospectiva(){
		cancelarAuditoriaRetrospectivaService.cancelarAuditoriaRetrospectiva(getCancelamento(), itemGAB);
		listaItens = itemGABDAO.obterItensGABParaCancelamentoAuditoriaRetrospectiva();
		Mensagem.informacao("Auditoria do Item de GAB " + itemGAB.getCodigo() + " cancelada com sucesso.");
	}
	
	public void selecionarItemGAB(ItemGAB itemGAB){
		this.itemGAB = itemGAB;
		cancelarAuditoriaRetrospectivaService.verificarSeAuditoriaPodeSerCanceladaItemGAB(itemGAB);
		setCancelamento(new CancelamentoAuditoriaRetrospectiva());
		ManagedBeanUtils.showDialog("acaoSdgaCancelarAuditoriaRetrospectivaDialog");
	}
	
	public ItemGAB getItemGAB() {
		return itemGAB;
	}

	public void setItemGAB(ItemGAB itemGAB) {
		this.itemGAB = itemGAB;
	}

	public List<ItemGAB> getListaItens() {
		return listaItens;
	}

	public void setListaItens(List<ItemGAB> listaItens) {
		this.listaItens = listaItens;
	}

	public CancelamentoAuditoriaRetrospectiva getCancelamento() {
		return cancelamento;
	}

	public void setCancelamento(CancelamentoAuditoriaRetrospectiva cancelamento) {
		this.cancelamento = cancelamento;
	}
	
}
