package br.mil.fab.consigext.service.impl.margem;

import java.io.File;
import java.math.BigDecimal;
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
import br.mil.fab.consigext.entity.PesfisComgep;
import br.mil.fab.consigext.entity.Servico;
import br.mil.fab.consigext.entity.ServicoConsig;
import br.mil.fab.consigext.entity.ServidorConsig;
import br.mil.fab.consigext.entity.SolicitacaoSaldoDevedor;
import br.mil.fab.consigext.entity.StatusConsignacao;
import br.mil.fab.consigext.entity.TipoHistoricoConsig;
import br.mil.fab.consigext.entity.Usuario;
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
import br.mil.fab.consigext.service.margem.PerfisComgepService;
import br.mil.fab.consigext.service.margem.ServicoConsigService;
import br.mil.fab.consigext.service.margem.ServicoService;
import br.mil.fab.consigext.service.margem.ServidorConsigService;
import br.mil.fab.consigext.service.parametro.ParametroService;
import br.mil.fab.consigext.service.usuario.UsuarioService;
import br.mil.fab.consigext.service.util.ComboUtilService;
import br.mil.fab.consigext.service.util.UsuarioUtilService;
import br.mil.fab.consigext.util.GenericUtil;
import br.mil.fab.consigext.util.ParcelaUtil;
import br.mil.fab.consigext.util.PasswordUtil;
import br.mil.fab.consigext.util.UsuarioLogado;
import br.mil.fab.util.sigpes.entity.PesfisComgepResponse;

@Service
public class ConsignacaoConsignatariaServiceImpl implements ConsignacaoService {

	@Autowired
	Message msg;

	@Autowired
	ParcelaService parcelaService;
	
	@Autowired
	UsuarioUtilService usrUtilService;

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
	
	@Autowired
	PerfisComgepService perfisComgepService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Override
	public ConsignacaoRepository getConsigRepository() {
		return consigRepository;
	}
	
	@Override
	public UsuarioUtilService getUsrUtilService() {
		return usrUtilService;
	}

	@Override 
	public ParcelaUtil getParcelaUtil() {
		return parcelaUtil;
	}
	
	@Override 
	public Message getMsg() {
		return msg;
	}
	
	@Override 
	public ComboUtilService getComboUtilService() {
		return comboUtilService;
	}
	@Override 
	public EntidadeConsigService getEntidadeConsigService() {
		return entidadeConsigService;
	}
	
	@Override
	public boolean validaSolicMargemFiltro(Map<String, Object> body, Model model) {
		List<String> detailedErrors = new ArrayList<String>();
		
		if (body.get("servSelect") == null || StringUtils.isBlank(body.get("servSelect").toString())) {
			detailedErrors.add(msg.get("campo.servico.obrigatorio"));
		} else {
			model.addAttribute("servico",
					(body.get("servSelect") == null) || StringUtils.isBlank(body.get("servSelect").toString()) ? ""
							: Long.parseLong(body.get("servSelect").toString()));
		}

		if ((body.get("matInput") == null || StringUtils.isBlank(body.get("matInput").toString()))
				&& (body.get("cpfInput") == null || StringUtils.isBlank(body.get("cpfInput").toString()))) {
			detailedErrors.add(msg.get("campo.matricula.cpf.invalido"));
		} else {
			PesfisComgepResponse pesfisComgep = new PesfisComgepResponse();
			if ((body.get("matInput") != null && StringUtils.isNotBlank(body.get("matInput").toString()))
					&& body.get("matInput").toString().length() == 7) {
				 pesfisComgep = usrUtilService
						.getMilitarByMatriculaOrCpf(body.get("matInput").toString());
				if (pesfisComgep != null && StringUtils.isNotBlank(pesfisComgep.getNrOrdem())) {
					model.addAttribute("pesfiscomgep", pesfisComgep);
				} else {
					detailedErrors.add(msg.get("campo.matricula.invalido"));
				}

			} else if (body.get("cpfInput") != null && StringUtils.isNotBlank(body.get("cpfInput").toString())) {
				 pesfisComgep = usrUtilService
						.getMilitarByMatriculaOrCpf(body.get("cpfInput").toString());
				if (pesfisComgep != null && StringUtils.isNotBlank(pesfisComgep.getNrCpf())) {
					model.addAttribute("pesfiscomgep", pesfisComgep);
				} else {
					detailedErrors.add(msg.get("campo.cpf.invalido"));
				}
			} else {
				detailedErrors.add(msg.get("campo.matricula.invalido"));
			}
			PesfisComgep pf = perfisComgepService.findByNrOrdem(pesfisComgep.getNrOrdem());
			Usuario usuario = usuarioService.findByPesfis(pf);
			if(!usuario.getPasswordSha().equals(PasswordUtil.sha1x64(body.get("senhaConsigInput").toString()))) {
				detailedErrors.add(msg.get("campo.senha.invalida"));		
			}
		}
	
		if (!detailedErrors.isEmpty()) {
			EntidadeConsig ent = entidadeConsigService.findById(usuarioLogado.getUsuarioCorrente().getEntidadeConsig().getId());
			model.addAttribute("servicos", comboUtilService.getServiceComboByEntidadeConsig(ent));
			model.addAttribute("detailedErrors", detailedErrors);
			return false;
		}
	
		return true;

	}

	

	@Override
	public boolean validaValMargem(Map<String, Object> body, Model model) {

		List<String> detailedErrors = new ArrayList<String>();
		if (body.get("nrBanco") == null || StringUtils.isBlank(body.get("nrBanco").toString())) {
			detailedErrors.add(msg.get("campo.margem.numero.banco.orbigatorio"));
		} else if (acessoBancoRepository.findByCdBanco(body.get("nrBanco").toString()) == null) {
			detailedErrors.add(msg.get("campo.margem.numero.banco.naoExiste"));
		}

		if (body.get("nrAgencia") == null || StringUtils.isBlank(body.get("nrAgencia").toString())) {
			detailedErrors.add(msg.get("campo.margem.numero.agencia.obrigatorio"));
		}

		if (body.get("nrConta") == null || StringUtils.isBlank(body.get("nrConta").toString())) {
			detailedErrors.add(msg.get("campo.margem.numero.conta.orbigatorio"));
		}

		if ((body.get("vlLiquidoLiberado") == null || StringUtils.isBlank(body.get("vlLiquidoLiberado").toString()))
				&& (body.get("vlPrestacao") == null || StringUtils.isBlank(body.get("vlPrestacao").toString()))) {
			detailedErrors.add(msg.get("campo.margem.valorliquido.valorprestacao"));
		}
		
		if ((body.get("vlLiquidoLiberado").equals("0,00") || body.get("vlLiquidoLiberado").equals("0.00"))
				&& (body.get("vlPrestacao").equals("0,00")  || body.get("vlPrestacao").equals("0.00") )) {
			detailedErrors.add(msg.get("campo.margem.valorliquido.valorprestacao"));
		}

		if (!detailedErrors.isEmpty()) {
			model.addAttribute("detailedErrors", detailedErrors);
			return false;
		}
		return true;
		
	}

	@Override
	public Consignacao gerarConsignacao(Map<String, Object> body) {
		String paramPessoa = (isMatricula(body, "matInput")) ? body.get("matInput").toString()
				: GenericUtil.cleanChr(body.get("cpfInput").toString());

		EntidadeConsig consignataria = entidadeConsigService
				.findById(Long.valueOf(body.get("consigSelect").toString()));
		consignataria.setOrganizacao(getOrgConverter(body));
		Servico servico = servicoService.findById(Long.valueOf(body.get("servSelect").toString()));

		ServicoConsig servicoConsig = servicoConsigService.findByServicoAndEntidadeConsig(servico, consignataria);
		ServidorConsig servidorConsig = (paramPessoa.length() < 11) ? servidorConsigService.findByMatricula(paramPessoa)
				: servidorConsigService.findByCpf(paramPessoa);

		servidorConsig.setPesfis(usrUtilService.getPessoaConverter(paramPessoa));
		return new Consignacao(servicoConsig, servidorConsig);
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
		String ip = "";
		try {
			ip = usuarioLogado.obterIP();
	
			// TODO: Implementar o perfil de confirmar reserva para gestor e
	
			// consignataria (REGRA DE NEGÓCIO - 05)
			Calendar dataAnoMes = Calendar.getInstance();
			consignacao.setCdAnomesInicio(GenericUtil.getAAAAMMtual(dataAnoMes));
	
			dataAnoMes.add(Calendar.MONTH, Integer.parseInt(consignacao.getNrPrestacoes().toString()));
			consignacao.setCdAnomesFim(GenericUtil.getAAAAMMtual(dataAnoMes));
			consignacao.setStatusConsignacao(new StatusConsignacao(8));
			consignacao.setOrigemConsignacao(new OrigemConsignacao(1));
			consignacao.setDtReserva(new Date());
			consignacao.setParcelaConsignacaos(ParcelaUtil.gerarParcelas(consignacao));
	
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
//			if (porcMargem.equals("70")) {
//				consignacao.getServidorConsig().setVlMargem70(
//						consignacao.getServidorConsig().getVlMargem70().subtract(consignacao.getVlPrestacao()));
//			} else {
//				consignacao.getServidorConsig().setVlMargem30(
//						consignacao.getServidorConsig().getVlMargem30().subtract(consignacao.getVlPrestacao()));
//			}
	
			List<HistoricoConsignacao> historicos = new ArrayList<HistoricoConsignacao>();
			historicos.add(new HistoricoConsignacao("Inclusão de nova consignacao.", (new Date()), usuarioLogado.getCPF(), ip,
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
	public Consignacao bloquearConsignacao(String id, String perfil, Model model, Map<String, Object> body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Consignacao gerarConsignacao(UsuarioLogado usrLogado, Long idServico, Long idEntidadeConsig,
			CetConsigRanking cet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void geraValores(Map<String, Object> body, Model model) {
		// TODO Auto-generated method stub
		
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
	public ParcelaConsignacao getParcelaConsignacaoById(Long idParcela) {
		// TODO Auto-generated method stub
		return null;
	}
	
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

	public boolean validarConfiguracoesAvancadas(Map<String, Object> body, List<String> detailedErrors, List<String> parametros, ConfigAvancada configuracao) {
		// TODO Auto-generated method stub
		return false;
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
