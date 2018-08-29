package br.mil.fab.consigext.service.impl.margem;

import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.mil.fab.consigext.config.Message;
import br.mil.fab.consigext.dto.CetConsigRanking;
import br.mil.fab.consigext.dto.ConfigAvancada;
import br.mil.fab.consigext.dto.DadosConsig;
import br.mil.fab.consigext.entity.CargaArquivo;
import br.mil.fab.consigext.entity.CodigoUnico;
import br.mil.fab.consigext.entity.Consignacao;
import br.mil.fab.consigext.entity.EntidadeConsig;
import br.mil.fab.consigext.entity.HistoricoConsignacao;
import br.mil.fab.consigext.entity.HistoricoParcela;
import br.mil.fab.consigext.entity.OrigemConsignacao;
import br.mil.fab.consigext.entity.ParcelaConsignacao;
import br.mil.fab.consigext.entity.Servico;
import br.mil.fab.consigext.entity.ServicoConsig;
import br.mil.fab.consigext.entity.ServidorConsig;
import br.mil.fab.consigext.entity.SolicitacaoSaldoDevedor;
import br.mil.fab.consigext.entity.StatusConsignacao;
import br.mil.fab.consigext.entity.TipoHistoricoConsig;
import br.mil.fab.consigext.enums.StatusCodigoUnico;
import br.mil.fab.consigext.enums.TipoCarga;
import br.mil.fab.consigext.repository.AcessoBancoRepository;
import br.mil.fab.consigext.repository.ConsignacaoRepository;
import br.mil.fab.consigext.repository.UsuarioEcRepository;
import br.mil.fab.consigext.service.codigounico.CodigoUnicoService;
import br.mil.fab.consigext.service.margem.ConsignacaoService;
import br.mil.fab.consigext.service.margem.EntidadeConsigService;
import br.mil.fab.consigext.service.margem.HistoricoConsignacaoService;
import br.mil.fab.consigext.service.margem.OrganizacaoService;
import br.mil.fab.consigext.service.margem.ParcelaService;
import br.mil.fab.consigext.service.margem.ServicoConsigService;
import br.mil.fab.consigext.service.margem.ServicoService;
import br.mil.fab.consigext.service.margem.ServidorConsigService;
import br.mil.fab.consigext.service.parametro.ParametroService;
import br.mil.fab.consigext.service.util.ComboUtilService;
import br.mil.fab.consigext.service.util.UsuarioUtilService;
import br.mil.fab.consigext.util.GenericUtil;
import br.mil.fab.consigext.util.ParcelaUtil;
import br.mil.fab.consigext.util.UsuarioLogado;
import br.mil.fab.util.sigpes.entity.PesfisComgepResponse;

@Service
public class ConsignacaoServidorServiceImp implements ConsignacaoService {

	@Autowired
	Message msg;

	@Autowired
	UsuarioUtilService usrUtilService;

	@Autowired
	ParcelaService parcelaService;
	
	@Autowired
	UsuarioLogado usuarioLogado;

	@Autowired
	AcessoBancoRepository acessoBancoRepository;

	@Autowired
	ConsignacaoRepository consigRepository;

	@Autowired
	UsuarioEcRepository usuarioEcRepo;

	@Autowired
	ServicoService servicoService;

	@Autowired
	ServidorConsigService servidorConsigService;

	@Autowired
	ComboUtilService comboUtilService;

	@Autowired
	EntidadeConsigService entidadeConsigService;

	@Autowired
	OrganizacaoService organizacaoService;

	@Autowired
	CodigoUnicoService codigoUnicoService;

	@Autowired
	ParametroService parametroService;

	@Autowired
	ServicoConsigService servicoConsigService;

	@Autowired
	HistoricoConsignacaoService historicoService;

	@Autowired
	ParcelaUtil parcelaUtil;

	@Autowired
	CodigoUnicoService cuService;

	
	@Override
	public boolean validaSolicMargemFiltro(Map<String, Object> body, Model model) {
		List<String> detailedErrors = new ArrayList<String>();

		if ((body.get("valLiqInput") == null || StringUtils.isBlank(body.get("valLiqInput").toString()))
				&& (body.get("valPrtInput") == null || StringUtils.isBlank(body.get("valPrtInput").toString()))) {
			detailedErrors.add(msg.get("campo.margem.valorliquido.valorprestacao"));
		}
		
		if (body.get("valLiqInput") == null) {
			if ((body.get("valPrtInput").equals("0,00")  || body.get("valPrtInput").equals("0.00"))){
				detailedErrors.add(msg.get("campo.margem.valorliquido.valorprestacao"));
			}
		} else if (body.get("valPrtInput") == null) {
			if ((body.get("valLiqInput").equals("0,00") || body.get("valLiqInput").equals("0.00"))){
				detailedErrors.add(msg.get("campo.margem.valorliquido.valorprestacao"));
			}
		} else {
			if ((body.get("valLiqInput").equals("0,00") || body.get("valLiqInput").equals("0.00") 
					|| StringUtils.isBlank(body.get("valLiqInput").toString()))
					&& (body.get("valPrtInput").equals("0,00")  || body.get("valPrtInput").equals("0.00") 
							|| StringUtils.isBlank(body.get("valPrtInput").toString()))) {
				detailedErrors.add(msg.get("campo.margem.valorliquido.valorprestacao"));
			}
		} 
		
		if (!detailedErrors.isEmpty()) {
			model.addAttribute("detailedErrors", detailedErrors);
			return false;
		} 
		
		return true;
	}
	
	public void geraValores(Map<String, Object> body, Model model ) {
		
		BigDecimal vlLiquidoLib = BigDecimal.ZERO;
		BigDecimal vlPrestacao = BigDecimal.ZERO;
		
		if (body.get("valLiqInput") == null || StringUtils.isBlank(body.get("valLiqInput").toString()) 
				|| body.get("valLiqInput").toString().equals("0,00") || body.get("valLiqInput").toString().equals("0.00")){
			vlPrestacao = new BigDecimal(body.get("valPrtInput").toString().replace(",", "."));
			vlLiquidoLib = vlPrestacao.multiply(new BigDecimal(Integer.parseInt(body.get("numPrestSelect").toString())));
			model.addAttribute("valPrtInput", body.get("valPrtInput").toString());
			model.addAttribute("valLiqInput","0.00");
			model.addAttribute("vlLiquidoLib",vlPrestacao.multiply(GenericUtil.stringToBigDecimal(body.get("numPrestSelect").toString())));
		} else {
			vlLiquidoLib = new BigDecimal(body.get("valLiqInput").toString().replace(",", "."));
			vlPrestacao = vlLiquidoLib.divide(new BigDecimal(Integer.parseInt(body.get("numPrestSelect").toString())));
			model.addAttribute("valPrtInput","0.00");
			model.addAttribute("valLiqInput",body.get("valLiqInput").toString());
			model.addAttribute("vlLiquidoLib",vlLiquidoLib);
		}
		
		body.put("vlPrestacao", vlPrestacao);
		body.put("vlLiquidoLib", vlLiquidoLib);
		
		model.addAttribute("vlPrestacao",vlPrestacao);
		model.addAttribute("vlLiquidoLib",vlLiquidoLib);
		
	}

	@Override
	public Consignacao cancelarReserva(String id, String perfil, Model model, Map<String, Object> body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Consignacao confirmarReserva(String id, String perfil, Model model, Map<String, Object> body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validaValMargem(Map<String, Object> body, Model model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Consignacao gerarConsignacao(UsuarioLogado usrLogado, Long idServico, Long idEntidadeConsig, CetConsigRanking cet) {
		
		EntidadeConsig consignataria = entidadeConsigService
				.findById(idEntidadeConsig);
		Servico servico = servicoService.findById(idServico); 

		ServicoConsig servicoConsig = servicoConsigService.findByServicoAndEntidadeConsig(servico, consignataria);
		ServidorConsig servidorConsig = servidorConsigService.findByMatricula(usrLogado.getNrOrdem());

		servidorConsig.setPesfis(usrUtilService.getPessoaConverter(usrLogado.getNrOrdem()));

		return new Consignacao(servicoConsig, servidorConsig, cet.getVlLiquidoLib(), cet.getVlPrestacao(), 
								new BigDecimal(cet.getVlCet(), MathContext.DECIMAL64), cet.getNrParcela());
	}
	

	@Override
	public Consignacao filterServidor(String cpf, String nrAde, String matricula, String idHash, Model model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Consignacao editarConsignacao(String id, String perfil, Model model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Consignacao bloquearConsignacao(String id, String perfil, Model model, Map<String, Object> body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Consignacao renegociacaoConsignacao(String id, String perfil, Model model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Consignacao reimplantarConsignacao(String id, String perfil, Model model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Consignacao consignacao, String cu, String porcMargem, Model model) {
		// TODO:Ver uma forma melhor de buscar o IP do usrLogado;
		
				// consignataria (REGRA DE NEGÓCIO - 05)
				Calendar dataAnoMes = Calendar.getInstance();
				consignacao.setCdAnomesInicio(GenericUtil.getAAAAMMtual(dataAnoMes));
		
				dataAnoMes.add(Calendar.MONTH, Integer.parseInt(consignacao.getNrPrestacoes().toString()));
				consignacao.setCdAnomesFim(GenericUtil.getAAAAMMtual(dataAnoMes));
				//TODO: Verificar o status consignação para o servidor na hora de persistir a margem.
				consignacao.setStatusConsignacao(new StatusConsignacao(16));
				consignacao.setOrigemConsignacao(new OrigemConsignacao(1));
				consignacao.setDtReserva(new Date());
				consignacao.setParcelaConsignacaos(ParcelaUtil.gerarParcelas(consignacao));
				
				consignacao.setNrCarencia(Long.parseLong("1"));
				
				PesfisComgepResponse pesfis = usrUtilService.getMilitarByMatriculaOrCpf(usuarioLogado.getNrOrdem());
				consignacao.setNrBanco(pesfis.getCdBanco());
				consignacao.setNrAgencia(pesfis.getCdAgencia());
				consignacao.setNrConta(pesfis.getNrCtaCorr());
				
				
				if (StringUtils.isNotBlank(consignacao.getNrIndice())) {
					consignacao.setNrIndice(consignacao.getNrIndice().toUpperCase());
				} else {
					consignacao.setNrIndice("XX");
				}
		
				if (StringUtils.isNotBlank(consignacao.getDsIdentificador())) {
					consignacao.setDsIdentificador(consignacao.getDsIdentificador().toUpperCase());
				}
		
				// Atualiza as margens 30 e 70;
				// TODO : Verificar a regra de desconto de margem;
//				if (porcMargem.equals("70")) {
//					consignacao.getServidorConsig().setVlMargem70(
//							consignacao.getServidorConsig().getVlMargem70().subtract(consignacao.getVlPrestacao()));
//				} else {
//					consignacao.getServidorConsig().setVlMargem30(
//							consignacao.getServidorConsig().getVlMargem30().subtract(consignacao.getVlPrestacao()));
//				}
				
				try {
					
					List<HistoricoConsignacao> historicos = new ArrayList<HistoricoConsignacao>();
					historicos.add(new HistoricoConsignacao("Inclusão de nova consignacao.", (new Date()), usuarioLogado.getCPF(), usuarioLogado.obterIP(),
							consignacao, new TipoHistoricoConsig(1)));
					consignacao.setHistoricoConsignacaos(historicos);
					
					consigRepository.save(consignacao);
			
					if (StringUtils.isNotBlank(cu)) {
						CodigoUnico codigoUnico = codigoUnicoService.findByVlCodigoUnico(cu);
						codigoUnico.setStCodigoUnico(StatusCodigoUnico.UTILIZADO.getVlStatus());
						codigoUnicoService.save(codigoUnico);
					}
			
					List<String> detailedSuccess = new ArrayList<String>();
					detailedSuccess.add(msg.get("margem.persistida.sucesso"));
					model.addAttribute("detailedSuccess", detailedSuccess);
				} catch (Exception e) {
					e.printStackTrace();
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					List<String> detailedErrors = new ArrayList<String>();
					detailedErrors.add(msg.get("margem.persistida.sucesso"));
					model.addAttribute("detailedErrors", detailedErrors);
				}
		
	}

	@Override
	public ConsignacaoRepository getConsigRepository() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UsuarioUtilService getUsrUtilService() {
		return usrUtilService;
	}

	@Override
	public ParcelaUtil getParcelaUtil() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message getMsg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComboUtilService getComboUtilService() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntidadeConsigService getEntidadeConsigService() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Consignacao gerarConsignacao(Map<String, Object> body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getIdConsignatariaByConsignacao(Consignacao consignacao) {
		  return  consignacao.getEntidadeConsig().getId();
	}
	
	@Override
	public EntidadeConsig getConsignatariaByConsignacao(Consignacao consignacao) {
		return consignacao.getEntidadeConsig();
	}

	@Override
	public void reativarConsig(long idConsignacao, String motivo, String observacao) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suspenderConsig(long idConsignacao, String motivo, String observacao) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean passouPagamento() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLastMonth(long idConsignacao) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<HistoricoConsignacao> getHistoricos(Consignacao consignacao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String liquidarContrato(String observacao, String nameOfFile, long idConsignacao, String motivo,
			RedirectAttributes redirectAttrs, MultipartFile multipartFile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void liquidarParcelas(Consignacao consignacao, Map<String, Object> body) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getErrorInLiquidarParcelas(Consignacao consignacao, Map<String, Object> body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String desliquidarParcela(Consignacao consignacao, Map<String, Object> body) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String desliquidarContrato(String observacao, String nameOfFile, long idConsignacao, String motivo,
			RedirectAttributes redirectAttrs, MultipartFile multipartFile, Map<String, Object> body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void liquidarConsig(long idConsignacao, String motivo, String observacao) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAction(Consignacao consignacao, Map<String, Object> body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean periodoCritico() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ParcelaConsignacao getParcelaConsignacaoById(Long idParcela) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void liquidarParcelas(List<ParcelaConsignacao> parcelas,
			List<HistoricoParcela> historicoParcelas) {
		// TODO Auto-generated method stub
	}

	@Override
	public void desliquidarConsig(long idConsignacao, String motivo, String observacao, boolean validar,
			boolean reimplantar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DadosConsig dadosParaAlteracao(Consignacao consignacao) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String mensagemSolicitacaoSaldoDevedor(Long idConsignacao) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<StatusConsignacao> findByNmStatusIn(List<String> namesStatus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void solicitarSaldoDevedor(long idconsignacao) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String solicSaldoDevedorConsultaOuLiquid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getVlTotalConsignacaoString(Long idConsignacao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getErrorMessageAddServicoConsig(Map<String, Object> body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addServicoConsig(Map<String, Object> body) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRecoveredParams(RedirectAttributes redirectAttrs, Map<String, Object> body) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getErrorInFiltroSolicSaldoDevedor(Map<String, Object> body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SolicitacaoSaldoDevedor> getSolicitacoesSaldoDevedor(Map<String, Object> body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SolicitacaoSaldoDevedor findSolicSaldoDevedorById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean validarConfiguracoesAvancadas(Map<String, Object> body, List<String> detailedErrors, List<String> parametros, ConfigAvancada configuracao) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String[] processFile(Map<String, Object> body, RedirectAttributes redirectAttrs, TipoCarga tipo, MultipartFile multipartFile,
			String errorCodeInSend, String descricao, long idSolic) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getErrorInSendSaldoDevedor(Map<String, Object> body, MultipartFile multipartFile1,
			MultipartFile multipartFile2, MultipartFile multipartFile3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sendSaldoDevedor(Map<String, Object> body, Object[] redirect_idFile_file_Demonstrativo,
			Object[] redirect_idFile_file_Boleto, Object[] redirect_idFile_file_Detalhes,
			SolicitacaoSaldoDevedor solic) {
		return null;		
	}

	@Override
	public List<CargaArquivo> getAllFilesRelatedToConsignacao(Consignacao byIdConsignacao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void suspenderConsignacao(Consignacao consignacao, File file, String motivo, String observacao,
			String nameOfFile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean confirmaAlteracaoAvancada(List<String> detailedErrors, ConfigAvancada configuracao) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BigDecimal getMargem(String porcMargem, Consignacao consig) {
		if (porcMargem.equals("70")) {
			return GenericUtil.stringToBigDecimal(usrUtilService.getMargem70MesAtual(consig.getNrOrdem()));
		}
		return GenericUtil.stringToBigDecimal(usrUtilService.getMargem30MesAtual(consig.getNrOrdem()));
	}

	@Override
	public int getMaxParcelas(Servico servico) {
		String s = parametroService.findByServicoSiglaParametro(servico, "INFQTDMAXPARINC");	
		int maxParcelas;
		if(s!=null)
			maxParcelas = Integer.parseInt(s.split("\\|")[0]);
		else
			maxParcelas = 1;
		return maxParcelas;
	}	
}
