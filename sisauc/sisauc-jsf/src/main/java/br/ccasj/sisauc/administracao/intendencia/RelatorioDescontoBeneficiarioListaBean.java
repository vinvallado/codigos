package br.ccasj.sisauc.administracao.intendencia;

import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;
import br.ccasj.sisauc.intendencia.dao.RelatorioDescontoBeneficiarioDAO;
import br.ccasj.sisauc.intendencia.domain.EstadoRelatorioFolhaBeneficiario;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiario;
import br.ccasj.sisauc.intendencia.domain.RelatorioAdapter;
import br.ccasj.sisauc.intendencia.domain.service.EnvioDescontosSisconsigService;

@Service("relatorioDescontoBeneficiarioListaBean")
@Scope("view")
public class RelatorioDescontoBeneficiarioListaBean {

	private RelatorioDescontoBeneficiario relatorioEnvio;
	
	@Autowired
	private RelatorioDescontoBeneficiarioDAO relatorioDescontoBeneficiarioDAO;
	
	@Autowired
	private EnvioDescontosSisconsigService envioDescontosSisconsigService;
	
	private List<RelatorioDescontoBeneficiario> relatorios;

	@PostConstruct
	public void init(){
		relatorios = relatorioDescontoBeneficiarioDAO.listarAtivos();
	}
	
	public void selecionar(RelatorioDescontoBeneficiario relatorioEnvio){
		this.relatorioEnvio = relatorioEnvio;
		ManagedBeanUtils.showDialog("confirmarEnvioSisconsigDialog");
	}
	
	public void enviarParaPagamento(){
		RelatorioDescontoBeneficiario relatorioDescontoBeneficiario = getRelatorioEnvio();
		relatorioDescontoBeneficiario.setEstadoEnvioFolha(EstadoRelatorioFolhaBeneficiario.INICIADO);
		this.relatorioDescontoBeneficiarioDAO.mudarEstadoRelatorio(relatorioDescontoBeneficiario); 
		
		Mensagem.informacao("Relatório " + relatorioDescontoBeneficiario.getCodigo() + " iniciado Envio ao SISCONSIG !");
		envioDescontosSisconsigService.setRelatorioAdapterSisconsig(new RelatorioAdapter(relatorioDescontoBeneficiario));
		envioDescontosSisconsigService.enviarRelatorioSisconsig();
		
		// Abrir o dialog
		ManagedBeanUtils.showDialog("visualizarEnvioDialog");
		// inicializar o timer
		//RequestContext.getCurrentInstance().update(":table-relatorios-desconto");
	} 
	
	public List<RelatorioDescontoBeneficiario> getRelatorios() {
		return relatorios;
	}

	public void setRelatorios(List<RelatorioDescontoBeneficiario> relatorios) {
		this.relatorios = relatorios;
	}
	
	public void atualizarTabelaEnvios(){
		RequestContext.getCurrentInstance().update(":table-envio-itens");
	}
	
	public RelatorioDescontoBeneficiario getRelatorioEnvio() {
		return relatorioEnvio;
	}

	public void setRelatorioEnvio(RelatorioDescontoBeneficiario relatorioEnvio) {
		this.relatorioEnvio = relatorioEnvio;
	}


	
}
