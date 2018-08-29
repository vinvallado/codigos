package br.ccasj.sisauc.sdga.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.emissaogab.dao.GuiaApresentacaoBeneficiarioDAO;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;
import br.ccasj.sisauc.sdga.domain.CancelamentoApresentacaoGAB;
import br.ccasj.sisauc.sdga.service.CancelarApresentacaoGABService;

@Service("cancelamentoApresentacaoGABBean")
@Scope("view")
public class CancelamentoApresentacaoGABBean implements Serializable {

	private static final long serialVersionUID = 364341205644289754L;

	@Autowired
	private transient GuiaApresentacaoBeneficiarioDAO guiaApresentacaoBeneficiarioDAO;
	@Autowired
	private transient CancelarApresentacaoGABService cancelarApresentacaoGABService;
	
	private List<GuiaApresentacaoBeneficiario> gabs;
	private GuiaApresentacaoBeneficiario gab;
	private CancelamentoApresentacaoGAB cancelamento;
	
	@PostConstruct
	public void init() {
		gabs = guiaApresentacaoBeneficiarioDAO.obterGABsApresentadasEEmAuditoria();
	}

	public void selecionarGAB(GuiaApresentacaoBeneficiario gab){
		this.gab = gab;
		cancelamento = new CancelamentoApresentacaoGAB();
		ManagedBeanUtils.showDialog("acaoSdgaCancelarApresentacaoGABDialog");
	}
	
	public void cancelarApresentacaoGAB(){
		cancelamento.setGab(gab);
		cancelarApresentacaoGABService.cancelarApresentacao(cancelamento);
		cancelamento = new CancelamentoApresentacaoGAB();
		gabs = guiaApresentacaoBeneficiarioDAO.obterGABsApresentadasEEmAuditoria();
		Mensagem.informacao("Cancelamento de apresentação de GAB efetuado com sucesso.");
		ManagedBeanUtils.hideDialog("acaoSdgaCancelarApresentacaoGABDialog");
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

	public CancelamentoApresentacaoGAB getCancelamento() {
		return cancelamento;
	}

	public void setCancelamento(CancelamentoApresentacaoGAB cancelamento) {
		this.cancelamento = cancelamento;
	}
}
