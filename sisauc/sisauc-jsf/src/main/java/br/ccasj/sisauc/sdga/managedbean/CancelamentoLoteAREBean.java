package br.ccasj.sisauc.sdga.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.auditoriaretrospectiva.dao.GerenciarLoteDAO;
import br.ccasj.sisauc.auditoriaretrospectiva.dao.GerenciarLoteRessarcimentoDAO;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.Lote;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.LoteRessarcimento;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;
import br.ccasj.sisauc.sdga.domain.CancelamentoLote;
import br.ccasj.sisauc.sdga.domain.CancelamentoLoteRessarcimento;
import br.ccasj.sisauc.sdga.service.CancelarLoteAREService;

@Scope(value = "view")
@Service(value = "cancelamentoLoteAREBean")
public class CancelamentoLoteAREBean implements Serializable {

	private static final long serialVersionUID = 2768364952350699166L;
	
	@Autowired
	private GerenciarLoteRessarcimentoDAO loteDao;
	
	@Autowired
	private CancelarLoteAREService cancelarLoteAreService;

	private List<LoteRessarcimento> lotes;
	private LoteRessarcimento lote;
	private CancelamentoLoteRessarcimento cancelamento;
	
	
	@PostConstruct
	private void init() {
		lotes = loteDao.findAll();
	}
 
	public void selecionar(LoteRessarcimento lote){
		this.lote = lote;
		cancelarLoteAreService.validarCancelamento(lote.getId());
		cancelamento = new CancelamentoLoteRessarcimento();
		ManagedBeanUtils.showDialog("acaoSdgaCancelarLoteAREDialog");
	}
	
	public void cancelarLote(){
		cancelamento.setLoteRessarcimento(lote);
		cancelarLoteAreService.cancelarLoteAre(cancelamento);
		lotes = loteDao.findAll();
		Mensagem.informacao("Lote " + cancelamento.getLoteRessarcimento().getNumero() + " cancelado com sucesso.");
		cancelamento = new CancelamentoLoteRessarcimento();
		ManagedBeanUtils.hideDialog("acaoSdgaCancelarLoteAREDialog");
	}
	
	public List<LoteRessarcimento> getLotes() {
		return lotes;
	}

	public void setLotes(List<LoteRessarcimento> lotes) {
		this.lotes = lotes;
	}

	public LoteRessarcimento getLote() {
		return lote;
	}

	public void setLote(LoteRessarcimento lote) {
		this.lote = lote;
	}

	public CancelamentoLoteRessarcimento getCancelamento() {
		return cancelamento;
	}

	public void setCancelamento(CancelamentoLoteRessarcimento cancelamento) {
		this.cancelamento = cancelamento;
	}

}