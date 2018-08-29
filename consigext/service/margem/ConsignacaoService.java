package br.mil.fab.consigext.service.margem;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.mil.fab.consigext.config.Message;
import br.mil.fab.consigext.dto.CetConsigRanking;
import br.mil.fab.consigext.dto.ConfigAvancada;
import br.mil.fab.consigext.dto.DadosConsig;
import br.mil.fab.consigext.entity.CargaArquivo;
import br.mil.fab.consigext.entity.Consignacao;
import br.mil.fab.consigext.entity.EntidadeConsig;
import br.mil.fab.consigext.entity.HistoricoConsignacao;
import br.mil.fab.consigext.entity.HistoricoParcela;
import br.mil.fab.consigext.entity.Organizacao;
import br.mil.fab.consigext.entity.ParcelaConsignacao;
import br.mil.fab.consigext.entity.PesfisComgep;
import br.mil.fab.consigext.entity.Servico;
import br.mil.fab.consigext.entity.SolicitacaoSaldoDevedor;
import br.mil.fab.consigext.entity.StatusConsignacao;
import br.mil.fab.consigext.enums.TipoCarga;
import br.mil.fab.consigext.repository.ConsignacaoRepository;
import br.mil.fab.consigext.service.util.ComboUtilService;
import br.mil.fab.consigext.service.util.UsuarioUtilService;
import br.mil.fab.consigext.util.ParcelaUtil;
import br.mil.fab.consigext.util.UsuarioLogado;
import br.mil.fab.util.sigpes.entity.PesfisComgepResponse;

public interface ConsignacaoService {

	boolean validaSolicMargemFiltro(Map<String, Object> body, Model model);

	Consignacao cancelarReserva(String id, String perfil, Model model, Map<String, Object> body);

	Consignacao confirmarReserva(String id, String perfil, Model model, Map<String, Object> body);

	boolean validaValMargem(Map<String, Object> body, Model model);

	Consignacao gerarConsignacao(Map<String, Object> body);

	public Consignacao gerarConsignacao(UsuarioLogado usrLogado, Long idServico, Long idEntidadeConsig,
			CetConsigRanking cet);

	public Consignacao filterServidor(String cpf, String nrAde, String matricula, String idHash, Model model);

	public Consignacao editarConsignacao(String id, String perfil, Model model);

	public Consignacao bloquearConsignacao(String id, String perfil, Model model, Map<String, Object> body);


	//public Consignacao liquidarParcela(String id, String perfil, Model model);
	
	public void liquidarParcelas(List<ParcelaConsignacao> parcelas, List<HistoricoParcela> historicoParcelas);


	public Consignacao renegociacaoConsignacao(String id, String perfil, Model model);

	public Consignacao reimplantarConsignacao(String id, String perfil, Model model);

	void save(Consignacao consignacao, String cu, String porcMargem, Model model);

	public ConsignacaoRepository getConsigRepository();

	public UsuarioUtilService getUsrUtilService();

	public ParcelaUtil getParcelaUtil();

	public Message getMsg();

	public ComboUtilService getComboUtilService();

	public EntidadeConsigService getEntidadeConsigService();

	public void geraValores(Map<String, Object> body, Model model);
	
	public String liquidarContrato(String observacao, String nameOfFile, long idConsignacao, String motivo,
			RedirectAttributes redirectAttrs, MultipartFile multipartFile);
	
	public String desliquidarContrato(String observacao, String nameOfFile, long idConsignacao, String motivo,
			RedirectAttributes redirectAttrs, MultipartFile multipartFile, Map<String, Object> body);
	
	public void liquidarConsig(long idConsignacao, String motivo, String observacao);
	
	public void desliquidarConsig(long idConsignacao, String motivo, String observacao, boolean validar, boolean reimplantar);
	
	public int getMaxParcelas(Servico servico);
	
	public boolean periodoCritico();
	
	public DadosConsig dadosParaAlteracao(Consignacao consignacao);
	
	public List<StatusConsignacao> findByNmStatusIn(List<String> namesStatus);
	
	public long getIdConsignatariaByConsignacao(Consignacao consignacao);
	
	public boolean validarConfiguracoesAvancadas(Map<String, Object> body, List<String> detailedErrors, List<String> parametros, ConfigAvancada configuracao);
	
	public boolean confirmaAlteracaoAvancada(List<String> detailedErrors, ConfigAvancada configuracao);
	

	public default boolean isMatricula(Map<String, Object> body) {
		return (StringUtils.isNotBlank(body.entrySet().stream().filter(map -> "matInput".equals(map.getKey()))
				.map(map -> map.getValue().toString()).collect(Collectors.joining()))) ? true : false;
	}

	public default boolean isMatricula(Map<String, Object> body, String param) {

		return (StringUtils.isNotBlank(body.entrySet().stream().filter(map -> param.equals(map.getKey()))
				.map(map -> map.getValue().toString()).collect(Collectors.joining()))) ? true : false;
	}

	public default Consignacao getByIdConsignacao(long idConsig) {
		Consignacao consig = getConsigRepository().findById(idConsig);

		return consig;

	}

	public default Organizacao getOrgConverter(Map<String, Object> body) {
		PesfisComgepResponse org = isMatricula(body, "matInput")
				? getUsrUtilService().getMilitarByMatriculaOrCpf(body.get("matInput").toString())
				: getUsrUtilService()
						.getMilitarByMatriculaOrCpf(body.get("cpfInput").toString().replaceAll("\\.", "").replace("-", ""));
		return new Organizacao(org.getOrg());
	}

	public default Organizacao getOrgConverter(Map<String, Object> body, String param) {
		PesfisComgepResponse org = isMatricula(body, param)
				? getUsrUtilService().getMilitarByMatriculaOrCpf(body.get(param).toString())
				: getUsrUtilService()
						.getMilitarByMatriculaOrCpf(body.get(param).toString().replaceAll(".", "").replace("-", ""));
		return new Organizacao(org.getOrg());
	}

	public default Organizacao getOrgConverter(String matricula) {
		PesfisComgepResponse org = getUsrUtilService().getMilitarByMatriculaOrCpf(matricula);
		return new Organizacao(org.getOrg());
	}

	public default int getParcelaMaxima(Map<String, Object> body, Consignacao consig, Model model) {
		List<String> detailedErrors = new ArrayList<String>();
		int maxParcelas = getParcelaUtil().getParcelaMaxima(consig.getServidorConsig(), consig.getServicoConsig());
		if (maxParcelas == 0) {
			detailedErrors.add(getMsg().get("campo.margem.numero.parcelas.invalido"));
			model.addAttribute("servicos", getComboUtilService()
					.getServiceComboByEntidadeConsig(consig.getEntidadeConsig()));
			model.addAttribute("detailedErrors", detailedErrors);
		}
		return maxParcelas;
	}

//	public default BigDecimal getMargem(String porcMargem, Consignacao consig) {
//		if (porcMargem.equals("70")) {
//			return consig.getServidorConsig().getVlMargem70();
//		}
//		return consig.getServidorConsig().getVlMargem30();
//	}
	public BigDecimal getMargem(String porcMargem, Consignacao consig);
	
	public default void retornaMsgErroVlMargem(int valor, Model model) {
		List<String> detailedErrors = new ArrayList<String>();
		if (valor == 0) {
			detailedErrors.add(getMsg().get("cet.atributo.nulo"));
		} else {
			detailedErrors.add(getMsg().get("campo.margem.margem.vlmargem"));
		}

		model.addAttribute("detailedErrors", detailedErrors);
	}

	public default PesfisComgep getPessoaConverter(String paramPessoa) {
		PesfisComgepResponse resp = getUsrUtilService().getMilitarByMatriculaOrCpf(paramPessoa);

		return new PesfisComgep(resp);
	}

	public default boolean validaIndice(Long idServidorConsig, Long idEntidade, String nrIndice) {
		return getConsigRepository().findByIndiceConsig(idServidorConsig, idEntidade, nrIndice.toUpperCase()) != null;
				
	}


	EntidadeConsig getConsignatariaByConsignacao(Consignacao consignacao);

	void reativarConsig(long idConsignacao, String motivo, String observacao);

	void suspenderConsig(long idConsignacao, String motivo, String observacao);

	boolean passouPagamento();

	boolean isLastMonth(long idConsignacao);

	List<HistoricoConsignacao> getHistoricos(Consignacao consignacao);

	ParcelaConsignacao getParcelaConsignacaoById(Long idParcela);

	void liquidarParcelas(Consignacao consignacao, Map<String, Object> body);

	String getErrorInLiquidarParcelas(Consignacao consignacao, Map<String, Object> body);

	String desliquidarParcela(Consignacao consignacao, Map<String, Object> body);

	String getAction(Consignacao consignacao, Map<String, Object> body);

	String mensagemSolicitacaoSaldoDevedor(Long idConsignacao);

	void solicitarSaldoDevedor(long idconsignacao);

	String solicSaldoDevedorConsultaOuLiquid();

	String getVlTotalConsignacaoString(Long idConsignacao);

	String getErrorMessageAddServicoConsig(Map<String, Object> body);

	void addServicoConsig(Map<String, Object> body);

	void addRecoveredParams(RedirectAttributes redirectAttrs, Map<String, Object> body);

	String getErrorInFiltroSolicSaldoDevedor(Map<String, Object> body);

	List<SolicitacaoSaldoDevedor> getSolicitacoesSaldoDevedor(Map<String, Object> body);

	SolicitacaoSaldoDevedor findSolicSaldoDevedorById(long id);

	Object[] processFile(Map<String, Object> body, RedirectAttributes redirectAttrs, TipoCarga tipo, MultipartFile multipartFile,
			String errorCodeInSend, String descricao, long idSolic);

	String getErrorInSendSaldoDevedor(Map<String, Object> body, MultipartFile multipartFile1,
			MultipartFile multipartFile2, MultipartFile multipartFile3);

	String sendSaldoDevedor(Map<String, Object> body, Object[] redirect_idFile_file_Demonstrativo, Object[] redirect_idFile_file_Boleto,
			Object[] redirect_idFile_file_Detalhes, SolicitacaoSaldoDevedor solic);

	List<CargaArquivo> getAllFilesRelatedToConsignacao(Consignacao byIdConsignacao);

	void suspenderConsignacao(Consignacao consignacao, File file, String motivo, String observacao, String nameOfFile);
}
