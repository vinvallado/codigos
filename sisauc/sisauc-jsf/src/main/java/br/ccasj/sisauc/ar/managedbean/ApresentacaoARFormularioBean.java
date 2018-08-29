package br.ccasj.sisauc.ar.managedbean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.ccasj.sisauc.administracao.cadastro.service.ArquivoService;
import br.ccasj.sisauc.emissaoar.dao.AutorizacaoRessarcimentoDAO;
import br.ccasj.sisauc.emissaoar.domain.ApresentacaoAutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.service.ApresentarARService;
import br.ccasj.sisauc.framework.exception.SisaucException;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;
import br.ccasj.sisauc.framework.utils.managedbean.ViewUtilsBean;
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;

@Service("apresentacaoARFormularioBean")
@Scope("view")
public class ApresentacaoARFormularioBean implements Serializable {
	
	private static final long serialVersionUID = -4800483584550436549L;
	
	@Autowired
	private transient AutorizacaoRessarcimentoDAO autorizacaoRessarcimentoDAO;
	@Autowired
	private transient ApresentarARService apresentarARService;
	@Autowired
	private transient ArquivoService arquivoService;
	@Autowired
	private ViewUtilsBean viewUtilsBean;
	
	private ApresentacaoAutorizacaoRessarcimento apresentacao;
	
	@PostConstruct
	public void init(){
		String id = ManagedBeanUtils.obterParametroRequest("id");
		AutorizacaoRessarcimento ar = autorizacaoRessarcimentoDAO.findById(Integer.valueOf(id));
		apresentacao = ar.getApresentacao();
		if (apresentacao==null){
			apresentacao = new ApresentacaoAutorizacaoRessarcimento();
			apresentacao.setAr(ar);
		}
	}
	
	public void subirRequerimento(FileUploadEvent event){
		apresentacao.setArquivoRequerimento(viewUtilsBean.subirArquivo(event));
	}
	
	public void subirNotaFiscal(FileUploadEvent event){
		apresentacao.setArquivoNotaFiscal(viewUtilsBean.subirArquivo(event));
	}

	public boolean isApresentacaoPreenchida() {
		return !StringUtils.isEmpty(apresentacao.getNumeroNotaFiscal()) &&
				apresentacao.getDataNotaFiscal() != null &&
				!StringUtils.isEmpty(apresentacao.getCpfCnpjPrestador()) &&
				!StringUtils.isEmpty(apresentacao.getNomePrestador()) &&
				apresentacao.getArquivoRequerimento() != null &&
				apresentacao.getArquivoNotaFiscal() != null &&
				isCpfCnpjPrestadorValido();
	}
	
	public void apresentar() {
		apresentarARService.apresentar(apresentacao);
		Mensagem.informacao("Autorização de Ressarcimento Nº " + apresentacao.getAr().getCodigo() + " apresentada com sucesso!");
		ManagedBeanUtils.redirecionar("/atendente/apresentar-are");
	}
	
	public boolean isCpfCnpjPrestadorValido(){
		String cpfCnpj = apresentacao.getCpfCnpjPrestador();
		if (cpfCnpj.length() == 11){
			return new CPFValidator().invalidMessagesFor(cpfCnpj).isEmpty();
		} else if (cpfCnpj.length() == 14){
			return new CNPJValidator().invalidMessagesFor(cpfCnpj).isEmpty();
		} else {
			return false;
		}
	}
	
	public void downloadNotaFiscal() throws SisaucException, IOException{
		ApresentacaoAutorizacaoRessarcimento apres = apresentacao.getAr().getApresentacao();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		File file = arquivoService.obterArquivo(apres.getArquivoNotaFiscal(), 
				apres.getAr().getOrganizacaoMilitar().getId(), 
				apres.getArquivoNotaFiscal().getDataInsercao());
		arquivoService.gerarRespostaDownload(file, apres.getArquivoNotaFiscal().getNome(), response);
		context.responseComplete();
	}
	
	public void downloadRequerimento() throws SisaucException, IOException{
		ApresentacaoAutorizacaoRessarcimento apres = apresentacao.getAr().getApresentacao();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		File file = arquivoService.obterArquivo(apres.getArquivoRequerimento(), 
				apres.getAr().getOrganizacaoMilitar().getId(), 
				apres.getArquivoRequerimento().getDataInsercao());
		arquivoService.gerarRespostaDownload(file, apres.getArquivoRequerimento().getNome(), response);
		context.responseComplete();
	}
	
	public ApresentacaoAutorizacaoRessarcimento getApresentacao() {
		return apresentacao;
	}

	public void setApresentacao(ApresentacaoAutorizacaoRessarcimento apresentacao) {
		this.apresentacao = apresentacao;
	}
}
