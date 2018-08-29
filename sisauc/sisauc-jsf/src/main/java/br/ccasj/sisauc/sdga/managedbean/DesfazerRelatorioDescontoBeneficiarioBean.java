package br.ccasj.sisauc.sdga.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;
import br.ccasj.sisauc.intendencia.dao.RelatorioDescontoBeneficiarioDAO;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiario;
import br.ccasj.sisauc.sdga.domain.DesfazerRelatorioDescontoBeneficiario;
import br.ccasj.sisauc.sdga.service.DesfazerRelatorioDescontoBeneficiarioService;

@Service("desfazerRelatorioDescontoBeneficiarioBean")
@Scope("view")
public class DesfazerRelatorioDescontoBeneficiarioBean implements Serializable {

	private static final long serialVersionUID = -180788842650724497L;
	
	@Autowired
	private transient RelatorioDescontoBeneficiarioDAO relatorioDescontoBeneficiarioDAO;
	@Autowired
	private transient DesfazerRelatorioDescontoBeneficiarioService desfazerRelatorioDescontoBeneficiarioService;
	
	private List<RelatorioDescontoBeneficiario> relatorios;
	private RelatorioDescontoBeneficiario relatorio;
	private DesfazerRelatorioDescontoBeneficiario desfazerRelatorioDescontoBeneficiario;
	
	@PostConstruct
	public void carregarRelatoriosDescontoBeneficiario() {
		relatorios = relatorioDescontoBeneficiarioDAO.listarTodos();
	}
	
	public void selecionarRelatorioDescontoBenefiario(RelatorioDescontoBeneficiario relatorio){
		this.relatorio = relatorio;
		desfazerRelatorioDescontoBeneficiario = new DesfazerRelatorioDescontoBeneficiario();
		ManagedBeanUtils.showDialog("acaoSdgaDesfazerRelatorioDescontoBeneficiarioDialog");
	}
	
	public void desfazerRelatorioDescontoBeneficiario(){
		desfazerRelatorioDescontoBeneficiario.setRelatorio(relatorio);
		desfazerRelatorioDescontoBeneficiarioService.desfazerRelatorioDescontoBeneficiario(desfazerRelatorioDescontoBeneficiario);
		recarregarComMensagemDeSucesso();
	}


	private void recarregarComMensagemDeSucesso() {
		desfazerRelatorioDescontoBeneficiario = new DesfazerRelatorioDescontoBeneficiario();
		carregarRelatoriosDescontoBeneficiario();
		Mensagem.informacao("Desfazer Relatorio Desconto Beneficiario efetuado com sucesso.");
		ManagedBeanUtils.hideDialog("acaoSdgaDesfazerRelatorioDescontoBeneficiarioDialog");
	}
		
	public List<RelatorioDescontoBeneficiario> getRelatorios() {
		return relatorios;
	}

	public void setRelatorios(List<RelatorioDescontoBeneficiario> relatorios) {
		this.relatorios = relatorios;
	}
	
	public RelatorioDescontoBeneficiario getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(RelatorioDescontoBeneficiario relatorio) {
		this.relatorio = relatorio;
	}

	public DesfazerRelatorioDescontoBeneficiario getDesfazerRelatorioDescontoBeneficiario() {
		return desfazerRelatorioDescontoBeneficiario;
	}

	public void setDesfazerRelatorioDescontoBeneficiario(DesfazerRelatorioDescontoBeneficiario desfazerRelatorioDescontoBeneficiario) {
		this.desfazerRelatorioDescontoBeneficiario = desfazerRelatorioDescontoBeneficiario;
	}
	
}
