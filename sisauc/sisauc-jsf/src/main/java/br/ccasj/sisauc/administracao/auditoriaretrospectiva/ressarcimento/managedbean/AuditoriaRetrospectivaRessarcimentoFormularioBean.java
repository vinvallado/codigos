package br.ccasj.sisauc.administracao.auditoriaretrospectiva.ressarcimento.managedbean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.domain.LocalInternacao;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoTRS;
import br.ccasj.sisauc.administracao.cadastro.service.ArquivoService;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.AuditoriaRetrospectivaRessarcimento;
import br.ccasj.sisauc.auditoriaretrospectiva.service.AuditoriaRetrospectivaRessarcimentoService;
import br.ccasj.sisauc.auditoriaretrospectiva.service.InformarInconsistenciaService;
import br.ccasj.sisauc.emissaoar.dao.AutorizacaoRessarcimentoDAO;
import br.ccasj.sisauc.emissaoar.domain.ApresentacaoAutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.EspecificacaoItemARE;
import br.ccasj.sisauc.emissaoar.domain.ItemAR;
import br.ccasj.sisauc.emissaoar.domain.TipoEspecificacaoItemARE;
import br.ccasj.sisauc.framework.exception.SisaucException;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.EntidadeGenericaUtils;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;

@Scope(value = "view")
@Service(value = "auditoriaRetrospectivaRessarcimentoFormularioBean")
public class AuditoriaRetrospectivaRessarcimentoFormularioBean implements Serializable {
	
	private static final long serialVersionUID = 5291975828963701158L;

	@Autowired
	private transient AutorizacaoRessarcimentoDAO autorizacaoRessarcimentoDAO;
	@Autowired
	private transient ArquivoService arquivoService;
	@Autowired
	private transient InformarInconsistenciaService informarInconsistenciaService;
	@Autowired
	private transient AuditoriaRetrospectivaRessarcimentoService auditoriaRetrospectivaRessarcimentoService;
	
	private AutorizacaoRessarcimento ar;
	private ItemAR itemSelecionado;
	private AuditoriaRetrospectivaRessarcimento auditoriaRetrospectiva;
	private List<TipoEspecificacaoItemARE> tipoEspecificacoesAre = new ArrayList<TipoEspecificacaoItemARE>(Arrays.asList(TipoEspecificacaoItemARE.values()));

	@PostConstruct
	private void init() {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		ar = autorizacaoRessarcimentoDAO.obterPorIdParaAuditoriaRetrospectiva(Integer.parseInt(id));
	}

	public void downloadNotaFiscal() throws SisaucException, IOException{
		ApresentacaoAutorizacaoRessarcimento apres = ar.getApresentacao();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		File file = arquivoService.obterArquivo(apres.getArquivoNotaFiscal(), 
				apres.getAr().getOrganizacaoMilitar().getId(), 
				apres.getArquivoNotaFiscal().getDataInsercao());
		arquivoService.gerarRespostaDownload(file, apres.getArquivoNotaFiscal().getNome(), response);
		context.responseComplete();
	}
	
	public void downloadRequerimento() throws SisaucException, IOException{
		ApresentacaoAutorizacaoRessarcimento apres = ar.getApresentacao();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		File file = arquivoService.obterArquivo(apres.getArquivoRequerimento(), 
				apres.getAr().getOrganizacaoMilitar().getId(), 
				apres.getArquivoRequerimento().getDataInsercao());
		arquivoService.gerarRespostaDownload(file, apres.getArquivoRequerimento().getNome(), response);
		context.responseComplete();
	}
	
	public void informarInconsistencia(){
		informarInconsistenciaService.informarInconsistencia(ar);
		ManagedBeanUtils.redirecionar("/auditoria-retrospectiva-ressarcimento/realizar-auditoria");
		Mensagem.informacao("Informação de inconsistência realizada com sucesso.");
	}
	
	public void selecionarItem(ItemAR item){
		itemSelecionado = item;
		auditoriaRetrospectiva = item.getAuditoriaRetrospectiva();
		if (auditoriaRetrospectiva == null){
			auditoriaRetrospectiva = new AuditoriaRetrospectivaRessarcimento();
			if(itemSelecionado.getProcedimento() instanceof ProcedimentoTRS){
				if(LocalInternacao.ENFERMARIA.equals(ar.getAuditoriaProspectiva().getLocalInternacao()) || !((ProcedimentoTRS)itemSelecionado.getProcedimento()).isInternacao()){
					if(itemSelecionado.getRespostaRessarcimentoAuditoria().getValorRerenciaAuditor() != null){
						auditoriaRetrospectiva.setValorEstimado((itemSelecionado.getRespostaRessarcimentoAuditoria().getValorRerenciaAuditor()));
					}else{
						auditoriaRetrospectiva.setValorEstimado(((ProcedimentoTRS)itemSelecionado.getProcedimento()).getValorEnfermaria());
					}
				}else{
					auditoriaRetrospectiva.setValorEstimado(((ProcedimentoTRS)itemSelecionado.getProcedimento()).getValorApartamento());
				}
			}
			
			if(itemSelecionado != null){
				EspecificacaoItemARE especificacaoItemAreProcedimento = new EspecificacaoItemARE();
				especificacaoItemAreProcedimento.setTipo(TipoEspecificacaoItemARE.PROCEDIMENTO);
				especificacaoItemAreProcedimento.setDescricao(itemSelecionado.getProcedimento().getCodigo() + " - "+ itemSelecionado.getProcedimento().getDescricao());
				especificacaoItemAreProcedimento.setValorReferencia(auditoriaRetrospectiva.getValorEstimado());
				especificacaoItemAreProcedimento.setId(EntidadeGenericaUtils.gerarIdNegativo());
				if(itemSelecionado.getDente() != null){
					especificacaoItemAreProcedimento.setDente(itemSelecionado.getDente());
				}
				if(itemSelecionado.getFaceDental() != null){
					especificacaoItemAreProcedimento.setFaceDental(itemSelecionado.getFaceDental());
				}
				auditoriaRetrospectiva.getEspecificacoes().add(especificacaoItemAreProcedimento);
			}			
			if (itemSelecionado.getRespostaRessarcimentoAuditoria().isOpme()){
				EspecificacaoItemARE especificacaoItemAreOPME = new EspecificacaoItemARE();
				especificacaoItemAreOPME.setTipo(TipoEspecificacaoItemARE.OPME);
				especificacaoItemAreOPME.setDescricao(itemSelecionado.getRespostaRessarcimentoAuditoria().getOpmeDescricao());
				especificacaoItemAreOPME.setValorReferencia(itemSelecionado.getRespostaRessarcimentoAuditoria().getOpmeValor());
				especificacaoItemAreOPME.setId(EntidadeGenericaUtils.gerarIdNegativo());
				auditoriaRetrospectiva.getEspecificacoes().add(especificacaoItemAreOPME);
			}
		}
	}
	
	
	public void adicionarLinhas(){
		EspecificacaoItemARE especificacaoItemAreNovo = new EspecificacaoItemARE();
		tipoEspecificacoesAre.remove(TipoEspecificacaoItemARE.PROCEDIMENTO);
		tipoEspecificacoesAre.remove(TipoEspecificacaoItemARE.OPME);
		especificacaoItemAreNovo.setId(EntidadeGenericaUtils.gerarIdNegativo());
		auditoriaRetrospectiva.getEspecificacoes().add(especificacaoItemAreNovo);
	}
	
	public void removerLinhas(EspecificacaoItemARE especificacaoSelecionada) {
		auditoriaRetrospectiva.getEspecificacoes().remove(especificacaoSelecionada);
		recalculoValorRessarcir();
    }  
	
	public void recalculoValorRessarcir(){
		onChangeValorApresentado(null);
	}
	
	public void onChangeValorApresentado(AjaxBehaviorEvent event){
		double taxa = itemSelecionado.getAr().getAuditoriaProspectiva().isIsento() ? 1 : 0.8;
		auditoriaRetrospectiva.setValorRessarcimento(auditoriaRetrospectiva.getValorCalculado() * taxa);
	}
	
	
	public void salvarAuditoriaItemAR(){
		auditoriaRetrospectivaRessarcimentoService.salvarAuditoriaItemAR(itemSelecionado, auditoriaRetrospectiva);
		ManagedBeanUtils.hideDialog("auditoriaItemARDialog");
	}
	
	public void salvarSemFinalizar(){
		auditoriaRetrospectivaRessarcimentoService.salvarSemFinalizar(ar);
		ManagedBeanUtils.redirecionar("/auditoria-retrospectiva-ressarcimento/realizar-auditoria");
		Mensagem.informacao("Auditoria Retrospectiva salva com sucesso.");
	}
	
	public void confirmaFinalizacaoAuditoria(){
		auditoriaRetrospectivaRessarcimentoService.validarFinalizacao(ar);
		ManagedBeanUtils.showDialog("finalizarAuditoriaARDialog");
	}
	
	public void finalizar() {
		auditoriaRetrospectivaRessarcimentoService.finalizar(ar);
		Mensagem.informacao("Auditoria Retrospectiva de ARE finalizada com sucesso.");
		ManagedBeanUtils.redirecionar("/auditoria-retrospectiva-ressarcimento/realizar-auditoria");
	}
	
	public AutorizacaoRessarcimento getAr() {
		return ar;
	}

	public void setAr(AutorizacaoRessarcimento ar) {
		this.ar = ar;
	}

	public ItemAR getItemSelecionado() {
		return itemSelecionado;
	}

	public void setItemSelecionado(ItemAR itemSelecionado) {
		this.itemSelecionado = itemSelecionado;
	}

	public AuditoriaRetrospectivaRessarcimento getAuditoriaRetrospectiva() {
		return auditoriaRetrospectiva;
	}

	public void setAuditoriaRetrospectiva(AuditoriaRetrospectivaRessarcimento auditoriaRetrospectiva) {
		this.auditoriaRetrospectiva = auditoriaRetrospectiva;
	}

	public List<TipoEspecificacaoItemARE> getTipoEspecificacoesAre() {
		return tipoEspecificacoesAre;
	}

	public void setTipoEspecificacoesAre(List<TipoEspecificacaoItemARE> tipoEspecificacoesAre) {
		this.tipoEspecificacoesAre = tipoEspecificacoesAre;
	}

	
}
