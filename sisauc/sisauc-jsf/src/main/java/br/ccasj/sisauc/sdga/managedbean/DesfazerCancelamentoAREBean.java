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
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;
import br.ccasj.sisauc.sdga.domain.DesfazerCancelamentoARE;
import br.ccasj.sisauc.sdga.domain.DesfazerCancelamentoGAB;
import br.ccasj.sisauc.sdga.service.DesfazerCancelamentoAREService;

@Service("desfazerCancelamentoAREBean")
@Scope("view")
public class DesfazerCancelamentoAREBean implements Serializable {

	private static final long serialVersionUID = 1003269776653344224L;
	
	@Autowired
	private transient AutorizacaoRessarcimentoDAO autorizacaoRessarcimentoDAO;
	@Autowired
	private transient DesfazerCancelamentoAREService desfazerCancelamentoAREService;
	
	private List<AutorizacaoRessarcimento> ares;
	private AutorizacaoRessarcimento are;
	private DesfazerCancelamentoARE desfazerCancelamentoARE;
	
	@PostConstruct
	public void carregarAREsCanceladas() {
		ares = autorizacaoRessarcimentoDAO.listarPorEstados(EstadoAR.CANCELADA);
	}
	
	public void selecionarARE(AutorizacaoRessarcimento are){
		this.are = are;
		desfazerCancelamentoARE = new DesfazerCancelamentoARE();
		ManagedBeanUtils.showDialog("acaoSdgaDesfazerCancelamentoAREDialog");
	}
	
	public void desfazerCancelamentoARE(){
		desfazerCancelamentoARE.setAre(are);
		desfazerCancelamentoAREService.desfazerCancelamento(desfazerCancelamentoARE);
		recarregarComMensagemDeSucesso(are);
	}


	private void recarregarComMensagemDeSucesso(AutorizacaoRessarcimento are) {
		desfazerCancelamentoARE = new DesfazerCancelamentoARE();
		carregarAREsCanceladas();
		Mensagem.informacao("Desfazer Cancelamento de ARE " + are.getCodigo() + " efetuado com sucesso.");
		ManagedBeanUtils.hideDialog("acaoSdgaDesfazerCancelamentoAREDialog");
	}
	
	public List<AutorizacaoRessarcimento> getAres() {
		return ares;
	}
	
	public void setGabs(List<AutorizacaoRessarcimento> ares) {
		this.ares = ares;
	}
	
	public AutorizacaoRessarcimento getAre() {
		return are;
	}
	
	public void setAre(AutorizacaoRessarcimento are) {
		this.are = are;
	}
	
	public DesfazerCancelamentoARE getDesfazerCancelamento() {
		return desfazerCancelamentoARE;
	}
	
	public void setDesfazerCancelamento(DesfazerCancelamentoARE desfazerCancelamento) {
		this.desfazerCancelamentoARE = desfazerCancelamento;
	}
	
}
