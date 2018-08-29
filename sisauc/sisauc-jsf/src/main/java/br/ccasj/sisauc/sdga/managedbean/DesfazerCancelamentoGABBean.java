package br.ccasj.sisauc.sdga.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.emissaogab.dao.GuiaApresentacaoBeneficiarioDAO;
import br.ccasj.sisauc.emissaogab.domain.EstadoGAB;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;
import br.ccasj.sisauc.sdga.domain.DesfazerCancelamentoGAB;
import br.ccasj.sisauc.sdga.service.DesfazerCancelamentoGABService;

@Service("desfazerCancelamentoGABBean")
@Scope("view")
public class DesfazerCancelamentoGABBean implements Serializable {

	private static final long serialVersionUID = 1003269776653344224L;
	
	@Autowired
	private transient GuiaApresentacaoBeneficiarioDAO guiaApresentacaoBeneficiarioDAO;
	@Autowired
	private transient DesfazerCancelamentoGABService desfazerCancelamentoGABService;
	
	private List<GuiaApresentacaoBeneficiario> gabs;
	private GuiaApresentacaoBeneficiario gab;
	private DesfazerCancelamentoGAB desfazerCancelamento;
	
	@PostConstruct
	public void carregarGABsCanceladas() {
		gabs = guiaApresentacaoBeneficiarioDAO.obterGABsPorEstados(EstadoGAB.CANCELADA);
	}
	
	public void selecionarGAB(GuiaApresentacaoBeneficiario gab){
		this.gab = gab;
		desfazerCancelamento = new DesfazerCancelamentoGAB();
		ManagedBeanUtils.showDialog("acaoSdgaDesfazerCancelamentoGABDialog");
	}
	
	public void desfazerCancelamentoGAB(){
		desfazerCancelamento.setGab(gab);
		desfazerCancelamentoGABService.desfazerCancelamento(desfazerCancelamento);
		recarregarComMensagemDeSucesso();
	}


	private void recarregarComMensagemDeSucesso() {
		desfazerCancelamento = new DesfazerCancelamentoGAB();
		carregarGABsCanceladas();
		Mensagem.informacao("Desfazer Cancelamento de GAB efetuado com sucesso.");
		ManagedBeanUtils.hideDialog("acaoSdgaDesfazerCancelamentoGABDialog");
	}
	
	public List<GuiaApresentacaoBeneficiario> getGabs() {
		return gabs;
	}
	
	public void setGabs(List<GuiaApresentacaoBeneficiario> gabs) {
		this.gabs = gabs;
	}
	
	public GuiaApresentacaoBeneficiario getGab() {
		return gab;
	}
	
	public void setGab(GuiaApresentacaoBeneficiario gab) {
		this.gab = gab;
	}
	
	public DesfazerCancelamentoGAB getDesfazerCancelamento() {
		return desfazerCancelamento;
	}
	
	public void setDesfazerCancelamento(DesfazerCancelamentoGAB desfazerCancelamento) {
		this.desfazerCancelamento = desfazerCancelamento;
	}
	
}
