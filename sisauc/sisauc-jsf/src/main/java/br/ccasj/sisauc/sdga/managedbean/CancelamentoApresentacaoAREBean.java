package br.ccasj.sisauc.sdga.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.emissaoar.dao.AutorizacaoRessarcimentoDAO;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.EstadoAR;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;
import br.ccasj.sisauc.sdga.domain.CancelamentoApresentacaoRessarcimento;
import br.ccasj.sisauc.sdga.service.CancelarApresentacaoAREService;

@Service("cancelamentoApresentacaoAREBean")
@Scope("view")
public class CancelamentoApresentacaoAREBean implements Serializable {

	private static final long serialVersionUID = 364341205644289754L;

	@Autowired
	private transient AutorizacaoRessarcimentoDAO autorizacaoRessarcimentoDAO;
	@Autowired
	private transient CancelarApresentacaoAREService cancelarApresentacaoAREService;
	
	private List<AutorizacaoRessarcimento> ares;
	private AutorizacaoRessarcimento are;
	private CancelamentoApresentacaoRessarcimento cancelamento;
	
	@PostConstruct
	public void init() {
		ares = autorizacaoRessarcimentoDAO.listarPorEstados(EstadoAR.APRESENTADA, EstadoAR.EM_AUDITORIA);
	}

	public void selecionarARE(AutorizacaoRessarcimento are){
		this.are = are;
		cancelamento = new CancelamentoApresentacaoRessarcimento();
		ManagedBeanUtils.showDialog("acaoSdgaCancelarApresentacaoAREDialog");
	}
	
	public void cancelarApresentacaoARE(){
		cancelamento.setAr(are);
		cancelarApresentacaoAREService.cancelarApresentacao(cancelamento);
		cancelamento = new CancelamentoApresentacaoRessarcimento();
		ares = autorizacaoRessarcimentoDAO.listarPorEstados(EstadoAR.APRESENTADA, EstadoAR.EM_AUDITORIA);
		Mensagem.informacao("Cancelamento de apresentação da ARE " + are.getCodigo() + " efetuado com sucesso.");
		ManagedBeanUtils.hideDialog("acaoSdgaCancelarApresentacaoAREDialog");
	}
	
	public List<AutorizacaoRessarcimento> getAres() {
		return ares;
	}

	public void setAres(List<AutorizacaoRessarcimento> ares) {
		this.ares = ares;
	}

	public AutorizacaoRessarcimento getAre() {
		return are;
	}

	public void setAre(AutorizacaoRessarcimento are) {
		this.are = are;
	}

	public CancelamentoApresentacaoRessarcimento getCancelamento() {
		return cancelamento;
	}

	public void setCancelamento(CancelamentoApresentacaoRessarcimento cancelamento) {
		this.cancelamento = cancelamento;
	}
}
