package br.ccasj.sisauc.ar.managedbean;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.emissaoar.dao.AutorizacaoRessarcimentoDAO;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.service.AcompanharARService;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;
import br.ccasj.sisauc.relatorio.GeradorRelatorio;
import br.ccasj.sisauc.relatorio.domain.RelatorioRequerimentoRessarcimento;

@Scope(value = "view")
@Service(value = "acompanhamentoARBean")
public class AcompanhamentoARBean implements Serializable{
	
	private static final long serialVersionUID = -8651827812089122996L;

	@Autowired
	private AutorizacaoRessarcimentoDAO arDAO;
	@Autowired
	private AcompanharARService acompanharARService;
	@Autowired
	private GeradorRelatorio geradorRelatorio;
	
	private AutorizacaoRessarcimento ar;
	
	@PostConstruct
	private void init(){
		String id = ManagedBeanUtils.obterParametroRequest("id");
		ar = arDAO.obterComItensPorId(Integer.valueOf(id));
	}
	
	public void cancelar(){
		acompanharARService.cancelar(ar.getId(), ar.getJustificativaCancelamentoAR());
		Mensagem.informacao("AR cancelada com sucesso");
		ManagedBeanUtils.redirecionar("/chefe-funsa/ar/acompanhamento");
	}

	public void imprimirRequerimento() {
		geradorRelatorio.gerar(new RelatorioRequerimentoRessarcimento(ar));
	}
	
	public AutorizacaoRessarcimento getAr() {
		return ar;
	}

	public void setAr(AutorizacaoRessarcimento ar) {
		this.ar = ar;
	}
	
	
	

}
