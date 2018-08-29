package br.ccasj.sisauc.administracao.cadastro.managedbean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.beneficiario.dao.BeneficiarioDAO;
import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.administracao.cadastro.dao.EspecialidadeDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.EspecificacaoIsencaoCobrancaDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.SolicitacaoRessarcimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Especialidade;
import br.ccasj.sisauc.administracao.cadastro.domain.EspecificacaoIsencaoCobranca;
import br.ccasj.sisauc.administracao.cadastro.domain.EstadoSolicitacaoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.FaceDental;
import br.ccasj.sisauc.administracao.cadastro.domain.LocalInternacao;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoPedidoSolicitacaoRessarcimento;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoRessarcimento;
import br.ccasj.sisauc.administracao.cadastro.domain.Tabela;
import br.ccasj.sisauc.administracao.cadastro.service.ArquivoService;
import br.ccasj.sisauc.administracao.cadastro.service.CadastroSolicitacaoMedicaService;
import br.ccasj.sisauc.auditoriaprospectiva.dao.AuditoriaProspectivaRessarcimentoDAO;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectivaRessarcimento;
import br.ccasj.sisauc.emissaoar.dao.AutorizacaoRessarcimentoDAO;
import br.ccasj.sisauc.emissaoar.domain.ApresentacaoAutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.framework.context.AuthenticationContext;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.exception.SisaucException;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.EntidadeGenericaUtils;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;
import br.ccasj.sisauc.framework.utils.managedbean.ViewUtilsBean;
import br.ccasj.sisauc.relatorio.GeradorRelatorio;
import br.ccasj.sisauc.relatorio.domain.RelatorioRequerimentoRessarcimento;
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;

@Scope(value = "view")
@Service(value = "cadastroSolicitacaoRessarcimentoFormularioBean")
public class CadastroSolicitacaoRessarcimentoFormularioBean implements Serializable {

	private static final String EDITAR_PROCEDIMENTO_DIALOG = "editarProcedimentoDialog";

	private static final long serialVersionUID = -6674033949788572412L;
	
	@Autowired
	private SolicitacaoRessarcimentoDAO solicitacaoRessarcimentoDAO;
	@Autowired
	private EspecificacaoIsencaoCobrancaDAO especificacaoIsencaoCobrancaDAO;
	@Autowired
	private EspecialidadeDAO especialidadeDAO;
	@Autowired
	private CadastroSolicitacaoMedicaService cadastroSolicitacaoMedicaService;
	@Autowired
	private ViewUtilsBean viewUtilsBean;
	@Autowired
	private AuditoriaProspectivaRessarcimentoDAO auditoriaProspectivaRessarcimentoDAO;
	@Autowired
	private AutorizacaoRessarcimentoDAO autorizacaoRessarcimentoDAO;
	@Autowired
    private BeneficiarioDAO beneficiarioDAO;
	@Autowired
	private GeradorRelatorio geradorRelatorio;
	@Autowired
	private AuthenticationContext authenticationContext;
	@Autowired
	private ArquivoService arquivoService;
	
	private SolicitacaoRessarcimento solicitacao = new SolicitacaoRessarcimento();
	private ProcedimentoPedidoSolicitacaoRessarcimento pedido = new ProcedimentoPedidoSolicitacaoRessarcimento();
	private List<Tabela> tabelas = new ArrayList<Tabela>(Arrays.asList(Tabela.values()));
	private List<LocalInternacao> locaisInternacao = new ArrayList<>(Arrays.asList(LocalInternacao.values()));
	private List<EspecificacaoIsencaoCobranca> especificacoes = new ArrayList<EspecificacaoIsencaoCobranca>();
	private List<Especialidade> especialidades = new ArrayList<Especialidade>();
	private AuditoriaProspectivaRessarcimento auditoriaProspectivaRessarcimento;
	private List<AutorizacaoRessarcimento> arsGeradas;
	private List<Divisao> divisoes = new ArrayList<Divisao>();
	private List<FaceDental> faces = new ArrayList<FaceDental>();

	@PostConstruct
	private void init() {
		setDivisoes(Arrays.asList(Divisao.values()));
		setFaces(Arrays.asList(FaceDental.values()));
		especificacoes = especificacaoIsencaoCobrancaDAO.findAll();
		solicitacao = obterSolicitacao();
		especialidades = especialidadeDAO.listarEspecialidadesAtivasNaoOdontologicas();
		if (solicitacao.getEstado() == EstadoSolicitacaoProcedimento.AUDITADA) {
			this.auditoriaProspectivaRessarcimento = auditoriaProspectivaRessarcimentoDAO.obterAuditoriaPorSolicitacaoRessarcimentoComProcedimentos(solicitacao.getId());
			arsGeradas = new ArrayList<AutorizacaoRessarcimento>();
			arsGeradas = autorizacaoRessarcimentoDAO.obterARsPorAuditoriaProspectiva(auditoriaProspectivaRessarcimento.getId());
		}
	}

	public void initNaoEletiva() {
		solicitacao.setNaoEletiva(true);
		if (solicitacao.getApresentacao() == null){
			solicitacao.setApresentacao(new ApresentacaoAutorizacaoRessarcimento());
		}
	}
	
	private SolicitacaoRessarcimento obterSolicitacao() {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		return id.equals("novo") ? new SolicitacaoRessarcimento() : solicitacaoRessarcimentoDAO.abrirComPedidos(Integer.valueOf(id));
	}
	
	public void enviarParaAuditoria() {
		solicitacao = (SolicitacaoRessarcimento) cadastroSolicitacaoMedicaService.enviarParaAuditoria(solicitacao);
		Mensagem.informacao("Solicitação Nº " + solicitacao.getNumero() + " enviada com sucesso!");
		ManagedBeanUtils.redirecionar("/atendente/cadastro/solicitacao-ressarcimento");
	}

	public void salvar() {
		solicitacao = (SolicitacaoRessarcimento) cadastroSolicitacaoMedicaService.salvar(solicitacao);
		Mensagem.informacao("Solicitação Nº " + solicitacao.getNumero() + " salva com sucesso!");
		ManagedBeanUtils.redirecionar("/atendente/cadastro/solicitacao-ressarcimento");
	}

	public void criarPedido() {
		pedido = new ProcedimentoPedidoSolicitacaoRessarcimento(EntidadeGenericaUtils.gerarIdNegativo());
		ManagedBeanUtils.showDialog(EDITAR_PROCEDIMENTO_DIALOG);
	}

	public void editarPedido(ProcedimentoPedidoSolicitacaoRessarcimento pedido) {
		this.pedido = pedido;
		if (pedido.getProcedimento() == null){
			viewUtilsBean.setTipoTabelaAutocomplete("OUTROS");
		} else {
			viewUtilsBean.setTipoTabelaAutocomplete(pedido.getProcedimento().getTabela().name());
		}
		ManagedBeanUtils.showDialog(EDITAR_PROCEDIMENTO_DIALOG);
	}
	
	public void addPedido(){
		if(isPedidoValido()){
			solicitacao.getProcedimentos().add(pedido);
			ManagedBeanUtils.hideDialog(EDITAR_PROCEDIMENTO_DIALOG);
		}
	}

	private boolean isPedidoValido(){
		if(pedido.getProcedimento() == null && pedido.getDescricaoOutros() == null){
			throw new SystemRuntimeException("Selecione ao menos um procedimento");
		}
		return true;
	}
	
	public void removerPedido(ProcedimentoPedidoSolicitacaoRessarcimento pedido) {
		solicitacao.getProcedimentos().remove(pedido);
	}

	public void subirArquivo(FileUploadEvent event){
		solicitacao.setArquivo(viewUtilsBean.subirArquivo(event));
	}
	
	public void onSelectBeneficiario(SelectEvent event){
		Beneficiario selecionado = (Beneficiario) event.getObject();
		
		solicitacao.setBeneficiario(beneficiarioDAO.findById(selecionado.getId()));
		
		Beneficiario titular = solicitacao.getBeneficiario().getBeneficiarioTitular()==null ? solicitacao.getBeneficiario() : solicitacao.getBeneficiario().getBeneficiarioTitular();
		if ("ISENTO".equals(titular.getTipoContribuicao()) || "FATORCUSTO".equals(titular.getTipoContribuicao())){
			solicitacao.setIsento(true);
			solicitacao.setEspecificacao(getEspecificacaoMilitarNaoContribuinte());
		} else {
			solicitacao.setIsento(false);
		}
		
	}
	
	private EspecificacaoIsencaoCobranca getEspecificacaoMilitarNaoContribuinte() {
		for (EspecificacaoIsencaoCobranca especificacao: especificacoes){
			if(especificacao.getId()==1){
				return especificacao;
			}
		}
		return null;
	}
	
	public void onSelectTabela(){
		pedido.setProcedimento(null);
		pedido.setEspecialidade(null);
		pedido.setDescricaoOutros(null);
	}
	
	public boolean isCpfCnpjPrestadorValido(){
		String cpfCnpj = solicitacao.getApresentacao().getCpfCnpjPrestador();
		if (cpfCnpj.length() == 11){
			return new CPFValidator().invalidMessagesFor(cpfCnpj).isEmpty();
		} else if (cpfCnpj.length() == 14){
			return new CNPJValidator().invalidMessagesFor(cpfCnpj).isEmpty();
		} else {
			return false;
		}
	}
	
	public void subirRequerimento(FileUploadEvent event){
		solicitacao.getApresentacao().setArquivoRequerimento(viewUtilsBean.subirArquivo(event));
	}
	
	public void subirNotaFiscal(FileUploadEvent event){
		solicitacao.getApresentacao().setArquivoNotaFiscal(viewUtilsBean.subirArquivo(event));
	}
	
	public void validarImpressaoRequerimento(){
		if (solicitacao.getBeneficiario() == null){
			Mensagem.erro("Selecione um beneficiário.");
		} else {
			RequestContext.getCurrentInstance().execute("document.getElementById('formulario-solicitacao-ressarcimento:downloadButton').click();");
		}
	}
	
	public void imprimirRequerimento() {
		geradorRelatorio.gerar(new RelatorioRequerimentoRessarcimento(
				authenticationContext.getUsuarioLogado().getOrganizacaoMilitar(), 
				solicitacao.getBeneficiario()));
	}
	
	public void downloadNotaFiscal() throws SisaucException, IOException{
		ApresentacaoAutorizacaoRessarcimento apres = solicitacao.getApresentacao();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		File file = arquivoService.obterArquivo(apres.getArquivoNotaFiscal(), 
				solicitacao.getOrganizacaoMilitarSolicitante().getId(), 
				apres.getArquivoNotaFiscal().getDataInsercao());
		arquivoService.gerarRespostaDownload(file, apres.getArquivoNotaFiscal().getNome(), response);
		context.responseComplete();
	}
	
	public void downloadRequerimento() throws SisaucException, IOException{
		ApresentacaoAutorizacaoRessarcimento apres = solicitacao.getApresentacao();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		File file = arquivoService.obterArquivo(apres.getArquivoRequerimento(), 
				solicitacao.getOrganizacaoMilitarSolicitante().getId(), 
				apres.getArquivoRequerimento().getDataInsercao());
		arquivoService.gerarRespostaDownload(file, apres.getArquivoRequerimento().getNome(), response);
		context.responseComplete();
	}
	
	public SolicitacaoRessarcimento getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(SolicitacaoRessarcimento solicitacao) {
		this.solicitacao = solicitacao;
	}

	public List<LocalInternacao> getLocaisInternacao() {
		return locaisInternacao;
	}

	public void setLocaisInternacao(List<LocalInternacao> locaisInternacao) {
		this.locaisInternacao = locaisInternacao;
	}

	public List<EspecificacaoIsencaoCobranca> getEspecificacoes() {
		return especificacoes;
	}

	public void setEspecificacoes(List<EspecificacaoIsencaoCobranca> especificacoes) {
		this.especificacoes = especificacoes;
	}

	public ProcedimentoPedidoSolicitacaoRessarcimento getPedido() {
		return pedido;
	}

	public void setPedido(ProcedimentoPedidoSolicitacaoRessarcimento pedido) {
		this.pedido = pedido;
	}

	public List<Tabela> getTabelas() {
		return tabelas;
	}

	public void setTabelas(List<Tabela> tabelas) {
		this.tabelas = tabelas;
	}

	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	public AuditoriaProspectivaRessarcimento getAuditoriaProspectivaRessarcimento() {
		return auditoriaProspectivaRessarcimento;
	}

	public void setAuditoriaProspectivaRessarcimento(AuditoriaProspectivaRessarcimento auditoriaProspectivaRessarcimento) {
		this.auditoriaProspectivaRessarcimento = auditoriaProspectivaRessarcimento;
	}

	public List<AutorizacaoRessarcimento> getArsGeradas() {
		return arsGeradas;
	}

	public void setArsGeradas(List<AutorizacaoRessarcimento> arsGeradas) {
		this.arsGeradas = arsGeradas;
	}

	public List<Divisao> getDivisoes() {
		return divisoes;
	}

	public void setDivisoes(List<Divisao> divisoes) {
		this.divisoes = divisoes;
	}

	public List<FaceDental> getFaces() {
		return faces;
	}

	public void setFaces(List<FaceDental> faces) {
		this.faces = faces;
	}

}
