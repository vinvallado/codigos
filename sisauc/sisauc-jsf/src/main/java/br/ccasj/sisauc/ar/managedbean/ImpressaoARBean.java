package br.ccasj.sisauc.ar.managedbean;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.emissaoar.dao.AutorizacaoRessarcimentoDAO;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.EstadoAR;
import br.ccasj.sisauc.emissaoar.service.ImpressaoARService;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;
import br.ccasj.sisauc.relatorio.GeradorRelatorio;
import br.ccasj.sisauc.relatorio.domain.RelatorioAR;

@Scope(value = "view")
@Service(value = "impressaoARBean")
public class ImpressaoARBean implements Serializable {

	private static final long serialVersionUID = -8651827812089122996L;

	@Autowired
	private AutorizacaoRessarcimentoDAO arDAO;
	@Autowired
	private ImpressaoARService impressaoARService;
	@Autowired
	private GeradorRelatorio geradorRelatorio;

	private AutorizacaoRessarcimento ar;

	@PostConstruct
	private void init() throws IOException {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		ar = arDAO.obterComItensPorId(Integer.valueOf(id));
	}

	public void imprimir() {
		boolean arReimpressa;
		if(ar.getEstado().equals(EstadoAR.GERADA)){
			arReimpressa = false;
		}else{
			arReimpressa = true;
		}		
		impressaoARService.imprimir(ar);
		ar = arDAO.obterComItensPorId(Integer.valueOf(ar.getId()));
		geradorRelatorio.gerar(new RelatorioAR(ar, arReimpressa));
	}

	public AutorizacaoRessarcimento getAr() {
		return ar;
	}

	public void setAr(AutorizacaoRessarcimento ar) {
		this.ar = ar;
	}


}
