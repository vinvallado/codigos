package br.ccasj.sisauc.sdga.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.auditoriaretrospectiva.dao.GerenciarLoteDAO;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.Lote;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;
import br.ccasj.sisauc.sdga.domain.CancelamentoLote;
import br.ccasj.sisauc.sdga.service.CancelarLoteService;

@Scope(value = "view")
@Service(value = "cancelamentoLoteBean")
public class CancelamentoLoteBean implements Serializable {

	private static final long serialVersionUID = 2768364952350699166L;
	
	@Autowired
	private GerenciarLoteDAO loteDao;
	@Autowired
	private CancelarLoteService cancelarLoteService;

	private List<Lote> lotes;
	private Lote lote;
	private CancelamentoLote cancelamento;
	
	
	@PostConstruct
	private void init() {
		lotes = loteDao.findAll();
	}

	public void selecionarLote(Lote lote){
		this.lote = lote;
		cancelarLoteService.validarCancelamento(lote.getId());
		cancelamento = new CancelamentoLote();
		ManagedBeanUtils.showDialog("acaoSdgaCancelarLoteDialog");
	}
	
	public void cancelarLote(){
		cancelamento.setLote(lote);
		cancelarLoteService.cancelarLote(cancelamento);
		lotes = loteDao.findAll();
		Mensagem.informacao("Lote " + cancelamento.getLote().getNumero() + " cancelado com sucesso.");
		cancelamento = new CancelamentoLote();
		ManagedBeanUtils.hideDialog("acaoSdgaCancelarLoteDialog");
	}
	
	public List<Lote> getLotes() {
		return lotes;
	}

	public void setLotes(List<Lote> lotes) {
		this.lotes = lotes;
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public CancelamentoLote getCancelamento() {
		return cancelamento;
	}

	public void setCancelamento(CancelamentoLote cancelamento) {
		this.cancelamento = cancelamento;
	}

}