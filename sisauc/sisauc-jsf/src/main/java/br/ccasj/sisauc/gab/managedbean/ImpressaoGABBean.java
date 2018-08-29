package br.ccasj.sisauc.gab.managedbean;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.emissaogab.dao.GuiaApresentacaoBeneficiarioDAO;
import br.ccasj.sisauc.emissaogab.domain.EstadoGAB;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.emissaogab.service.ImpressaoGABService;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;
import br.ccasj.sisauc.relatorio.GeradorRelatorio;
import br.ccasj.sisauc.relatorio.domain.RelatorioGAB;

@Scope(value = "view")
@Service(value = "impressaoGABBean")
public class ImpressaoGABBean implements Serializable {

	private static final long serialVersionUID = -8651827812089122996L;

	@Autowired
	private GuiaApresentacaoBeneficiarioDAO gabDAO;
	@Autowired
	private ImpressaoGABService impressaoGabService;
	@Autowired
	private GeradorRelatorio geradorRelatorio;

	private GuiaApresentacaoBeneficiario gab;

	@PostConstruct
	private void init() throws IOException {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		gab = gabDAO.obterComItensPorId(Integer.valueOf(id));
	}

	public void imprimir() {
		boolean gabReimpressa;
		if(gab.getEstado().equals(EstadoGAB.GERADA)){
			gabReimpressa = false;
		}else{
			gabReimpressa = true;
		}
		impressaoGabService.imprimir(gab);
		gab = gabDAO.obterComItensPorId(Integer.valueOf(gab.getId()));
		geradorRelatorio.gerar(new RelatorioGAB(gab, gabReimpressa));
	}

	public GuiaApresentacaoBeneficiario getGab() {
		return gab;
	}

	public void setGab(GuiaApresentacaoBeneficiario gab) {
		this.gab = gab;
	}

}
