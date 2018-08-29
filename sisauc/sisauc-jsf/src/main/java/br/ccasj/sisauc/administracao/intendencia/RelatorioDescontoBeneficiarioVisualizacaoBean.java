package br.ccasj.sisauc.administracao.intendencia;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;
import br.ccasj.sisauc.intendencia.dao.RelatorioDescontoBeneficiarioDAO;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiario;
import br.ccasj.sisauc.intendencia.domain.service.DescontoBeneficiariosService;
import br.ccasj.sisauc.relatorio.GeradorRelatorio;
import br.ccasj.sisauc.relatorio.domain.RelatorioRelatorioDescontoBeneficiario;

@Service("relatorioDescontoBeneficiarioVisualizacaoBean")
@Scope("view")
public class RelatorioDescontoBeneficiarioVisualizacaoBean {

	@Autowired
	private transient RelatorioDescontoBeneficiarioDAO relatorioDescontoBeneficiarioDAO;
	@Autowired
	private transient DescontoBeneficiariosService descontoBeneficiarioService;
	@Autowired
	private transient GeradorRelatorio geradorRelatorio;
	
	private RelatorioDescontoBeneficiario relatorio;
	
	@PostConstruct
	public void init() {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		relatorio = relatorioDescontoBeneficiarioDAO.findById(Integer.valueOf(id));
	}

	public void imprimirPDF(){
		geradorRelatorio.gerar(new RelatorioRelatorioDescontoBeneficiario(relatorio), true);
	}
	
	public StreamedContent getRelatorioXLS() throws IOException{
		InputStream is = descontoBeneficiarioService.obterStreamRelatorioXLS(relatorio);
	    return new DefaultStreamedContent(is, "application/xls", "relatorio.xls");
	}
	
	public RelatorioDescontoBeneficiario getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(RelatorioDescontoBeneficiario relatorio) {
		this.relatorio = relatorio;
	}
}
