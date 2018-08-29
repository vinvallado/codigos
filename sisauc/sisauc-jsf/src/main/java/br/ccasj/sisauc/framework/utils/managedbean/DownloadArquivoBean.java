package br.ccasj.sisauc.framework.utils.managedbean;

import java.io.File;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.SolicitacaoMedicaDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Arquivo;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoMedica;
import br.ccasj.sisauc.administracao.cadastro.service.ArquivoService;

@Scope(value = "view")
@Service(value = "downloadArquivoBean")
public class DownloadArquivoBean {

	@Autowired
	private SolicitacaoMedicaDAO<SolicitacaoMedica> solicitacaoMedicaDao;
	@Autowired
	private ArquivoService arquivoService;
	
	Arquivo arquivo = new Arquivo();

	public void downloadArquivo() throws Exception{
		String id = ManagedBeanUtils.obterParametroRequest("id");
		SolicitacaoMedica solicitacao = solicitacaoMedicaDao.findById(Integer.valueOf(id));
		arquivo = solicitacao.getArquivo();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		File file = arquivoService.obterArquivo(arquivo, solicitacao.getOrganizacaoMilitarSolicitante().getId(), 
				solicitacao.getDataInsercaoSistema());
		arquivoService.gerarRespostaDownload(file, arquivo.getNome(), response);
		context.responseComplete();
	}
	

}