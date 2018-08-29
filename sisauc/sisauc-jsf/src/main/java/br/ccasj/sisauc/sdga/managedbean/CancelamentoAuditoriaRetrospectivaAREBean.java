package br.ccasj.sisauc.sdga.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.emissaoar.dao.AutorizacaoRessarcimentoDAO;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;
import br.ccasj.sisauc.sdga.service.CancelarAuditoriaRetrospectivaAREService;

@Scope(value = "view")
@Service(value = "cancelamentoAuditoriaRetrospectivaAREBean")
public class CancelamentoAuditoriaRetrospectivaAREBean implements Serializable {

	private static final long serialVersionUID = -5920076290921691386L;

	@Autowired
	private AutorizacaoRessarcimentoDAO areDAO;

	@Autowired
	private CancelarAuditoriaRetrospectivaAREService cancelarAuditoriaRetrospectivaAREService;

	private List<AutorizacaoRessarcimento> listaAres;

	private AutorizacaoRessarcimento are;

	private String justificativa;

	@PostConstruct
	private void init() {
		this.listaAres = areDAO.obterItensARESParaCancelamentoAuditoriaRetrospectiva();
	}

	public void cancelarAuditoriaRetrospectivaARE() {
		if (this.are != null) {
			cancelarAuditoriaRetrospectivaAREService.cancelarAuditoriaRetrospectivaARE(this.are, justificativa);
			listaAres = areDAO.obterItensARESParaCancelamentoAuditoriaRetrospectiva();
			Mensagem.informacao("Auditoria Retrospectivado da ARE " + are.getCodigo() + " cancelada com sucesso.");
		} else {
			Mensagem.erro("Selecione uma ARE para cancelamento!");
		}
	}

	public void selecionarARE(AutorizacaoRessarcimento are) {
		this.are = are;
		cancelarAuditoriaRetrospectivaAREService.verificarSeAuditoriaAREPodeSerCancelada(are);
		ManagedBeanUtils.showDialog("acaoSdgaCancelarAuditoriaRetrospectivaAREDialog");
	}

	public List<AutorizacaoRessarcimento> getListaItens() {
		return listaAres;
	}

	public void setListaItens(List<AutorizacaoRessarcimento> listaAres) {
		this.listaAres = listaAres;
	}

	public AutorizacaoRessarcimento getAre() {
		return are;
	}

	public void setAre(AutorizacaoRessarcimento are) {
		this.are = are;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

}
