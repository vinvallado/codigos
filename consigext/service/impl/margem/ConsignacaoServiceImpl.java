package br.mil.fab.consigext.service.impl.margem;

import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Transient;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.mil.fab.consigext.config.Message;
import br.mil.fab.consigext.dto.CetConsigRanking;
import br.mil.fab.consigext.dto.ConfigAvancada;
import br.mil.fab.consigext.dto.DadosConsig;
import br.mil.fab.consigext.entity.CaixaPagamento;
import br.mil.fab.consigext.entity.CargaArquivo;
import br.mil.fab.consigext.entity.CfgBasicaPgm;
import br.mil.fab.consigext.entity.CodigoUnico;
import br.mil.fab.consigext.entity.Consignacao;
import br.mil.fab.consigext.entity.EntidadeConsig;
import br.mil.fab.consigext.entity.Historico;
import br.mil.fab.consigext.entity.HistoricoConsignacao;
import br.mil.fab.consigext.entity.HistoricoEc;
import br.mil.fab.consigext.entity.HistoricoParcela;
import br.mil.fab.consigext.entity.OrigemConsignacao;
import br.mil.fab.consigext.entity.ParcelaConsignacao;
import br.mil.fab.consigext.entity.Servico;
import br.mil.fab.consigext.entity.ServicoConsig;
import br.mil.fab.consigext.entity.ServidorConsig;
import br.mil.fab.consigext.entity.SolicitacaoSaldoDevedor;
import br.mil.fab.consigext.entity.StatusConsignacao;
import br.mil.fab.consigext.entity.StatusParcela;
import br.mil.fab.consigext.entity.TipoHistoricoConsig;
import br.mil.fab.consigext.entity.UsuarioEc;
import br.mil.fab.consigext.enums.StatusCodigoUnico;
import br.mil.fab.consigext.enums.TipoCarga;
import br.mil.fab.consigext.enums.TipoHistoricoEc;
import br.mil.fab.consigext.enums.TipoSolicSaldoDevedor;
import br.mil.fab.consigext.helper.EmailHelper;
import br.mil.fab.consigext.repository.AcessoBancoRepository;
import br.mil.fab.consigext.repository.CaixaPagamentoRepository;
import br.mil.fab.consigext.repository.CargaArquivoRepository;
import br.mil.fab.consigext.repository.CfgBasicaPgmRepository;
import br.mil.fab.consigext.repository.ConsignacaoRepository;
import br.mil.fab.consigext.repository.EntidadeConsigRepository;
import br.mil.fab.consigext.repository.HistoricoConsignacaoRepository;
import br.mil.fab.consigext.repository.HistoricoEcRepository;
import br.mil.fab.consigext.repository.HistoricoParcelaRepository;
import br.mil.fab.consigext.repository.HistoricoRepository;
import br.mil.fab.consigext.repository.ParcelaConsignacaoRepository;
import br.mil.fab.consigext.repository.ServicoConsigRepository;
import br.mil.fab.consigext.repository.SolicSaldoDevedorRepository;
import br.mil.fab.consigext.repository.StatusConsignacaoRepository;
import br.mil.fab.consigext.repository.StatusParcelaRepository;
import br.mil.fab.consigext.repository.UsuarioEcRepository;
import br.mil.fab.consigext.service.ParametroSistema.ParametroSistemaService;
import br.mil.fab.consigext.service.carga.CargaArquivoService;
import br.mil.fab.consigext.service.codigounico.CodigoUnicoService;
import br.mil.fab.consigext.service.margem.ConsignacaoService;
import br.mil.fab.consigext.service.margem.EntidadeConsigService;
import br.mil.fab.consigext.service.margem.HistoricoConsignacaoService;
import br.mil.fab.consigext.service.margem.HistoricoParcelaService;
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
import br.mil.fab.util.sigpes.entity.UsuarioExternoResponse;

/**
 * @todo statusConsigService nao foi achado!
 * @author marcelomamc
 *
 */
@Service(value = "consignacaoService")
@Primary
public class ConsignacaoServiceImpl implements ConsignacaoService {

	@Autowired
	Message msg;

	@Autowired
	CaixaPagamentoRepository caixaPagamentoRepo;

	@Autowired
	UsuarioUtilService usrUtilService;

	@Autowired
	UsuarioLogado usuarioLogado;

	@Autowired
	HistoricoEcRepository historicoEcRepository;

	@Autowired
	HistoricoRepository historicoRepository;

	@Autowired
	AcessoBancoRepository acessoBancoRepository;

	@Autowired
	CfgBasicaPgmRepository cfgBasicaPgmRepository;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	ConsignacaoRepository consignacaoRepository;

	@Autowired
	ParcelaService parcelaService;

	@Autowired
	UsuarioEcRepository usuarioEcRepo;

	@Autowired
	ServicoService servicoService;

	@Autowired
	SolicSaldoDevedorRepository solicSaldoDevedorRepo;

	@Autowired
	ServicoConsigRepository servicoConsigRepo;

	@Autowired
	HistoricoParcelaRepository historicoParcelaRepo;

	@Autowired
	HistoricoConsignacaoRepository historicoConsignacaoRepo;

	@Autowired
	ServidorConsigService servidorConsigService;

	@Autowired
	ComboUtilService comboUtilService;

	@Autowired
	ParcelaConsignacaoRepository parcelaRepo;

	@Autowired
	EntidadeConsigService entidadeConsigService;

	@Autowired
	EntidadeConsigRepository entidadeConsigRepo;

	@Autowired
	OrganizacaoService organizacaoService;

	@Autowired
	CodigoUnicoService codigoUnicoService;

	@Autowired
	ParametroService parametroService;

	@Autowired
	ServicoConsigService servicoConsigService;

	@Autowired
	StatusParcelaRepository statusParcelaRepository;

	@Autowired
	HistoricoConsignacaoService historicoService;

	@Autowired
	HistoricoParcelaRepository hitoricoParcelaRepository;

	@Autowired
	ParcelaUtil parcelaUtil;

	@Autowired
	CodigoUnicoService cuService;

	@Autowired
	CargaArquivoService cargaService;

	@Autowired
	ParametroSistemaService parametroSistemaService;

	@Autowired
	CargaArquivoRepository cargaArquivoRepo;

	@Autowired
	HistoricoParcelaService historicoParcelaService;

	@Autowired
	StatusConsignacaoRepository statusConsignacaoRepository;

	@Autowired
	PerfisComgepService pesfisService;
	
	@Override
	public ConsignacaoRepository getConsigRepository() {
		return consignacaoRepository;
	}

	@Override
	public long getIdConsignatariaByConsignacao(Consignacao consignacao) {
		// TODO Auto-generated method stub
		return 0;
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
	public String solicSaldoDevedorConsultaOuLiquid() {
		String consultaOuLiquid = parametroSistemaService.getVlParamSistemaByIdParametro(10);
		if (consultaOuLiquid == null || consultaOuLiquid.isEmpty())
			return "1";
		return consultaOuLiquid;
		// 1 - liquidacao
		// 0 - consulta
	}

	@Override
	public String mensagemSolicitacaoSaldoDevedor(Long idConsignacao) {
		Consignacao consignacao = getByIdConsignacao(idConsignacao);
		String param_UtilizaDiasUteisEntreSolics = parametroSistemaService.getVlParamSistemaByIdParametro(18);
		boolean flagDiasUteis = false;
		if (param_UtilizaDiasUteisEntreSolics.equals("1"))
			flagDiasUteis = true;
		String minDiasStr = parametroService.findByServicoConsigSiglaParametro(consignacao.getServicoConsig(),
				"DIASSOLICSDDEV");
		int minDias;
		try {
			minDias = Integer.parseInt(minDiasStr);
		} catch (Exception e1) {
			// e1.printStackTrace();
			try {
				minDias = Integer.parseInt(parametroSistemaService.getVlParamSistemaByIdParametro(1));
			} catch (Exception e2) {
				e2.printStackTrace();
				minDias = 15;
			}
		}
		Date lastDateOfSolic = getLastDateOfSolic(consignacao);
		if (lastDateOfSolic == null)
			return null;
		Date proxDiaParaSolicitacao = GenericUtil.addDaysToDate(flagDiasUteis, lastDateOfSolic, minDias);
		if (proxDiaParaSolicitacao.after(new Date()))
			return "Já existe solicitação de saldo devedor para este contrato. "
					+ "A próxima solicitação poderá ser feita a partir do dia "
					+ GenericUtil.dataParaString(proxDiaParaSolicitacao);
		return null;
	}

	private Date getLastDateOfSolic(Consignacao consignacao) {
		List<SolicitacaoSaldoDevedor> solicsSaldoDevedor = solicSaldoDevedorRepo.findByConsignacao(consignacao);
		if (solicsSaldoDevedor == null || solicsSaldoDevedor.isEmpty())
			return null;
		SolicitacaoSaldoDevedor maisRecente = solicsSaldoDevedor.get(0);
		for (SolicitacaoSaldoDevedor solicitacaoAux : solicsSaldoDevedor)
			if (solicitacaoAux.getId() > maisRecente.getId())
				maisRecente = solicitacaoAux;
		return maisRecente.getDtSolicitacao();
		// List<SolicitacaoSaldoDevedor> solicsSaldoDevedor =
		// solicSaldoDevedorRepo.findByConsignacaoAndTipoHistoricoConsig_id(consignacao,
		// (long)14);
		// List<HistoricoConsignacao> historicosConsignacaoSolicSaldoParaLiquidacao =
		// historicoConsignacaoRepo.findByConsignacaoAndTipoHistoricoConsig_id(consignacao,
		// (long)15);
		// HistoricoConsignacao maisRecente = null;
		// if(historicosConsignacaoSolicSaldoParaConsulta!=null &&
		// historicosConsignacaoSolicSaldoParaConsulta.isEmpty()==false) {
		// maisRecente = historicosConsignacaoSolicSaldoParaConsulta.get(0);
		// for (HistoricoConsignacao historicoAux :
		// historicosConsignacaoSolicSaldoParaConsulta)
		// if (historicoAux.getId()>maisRecente.getId())
		// maisRecente = historicoAux;
		// }
		// if(historicosConsignacaoSolicSaldoParaLiquidacao!=null &&
		// historicosConsignacaoSolicSaldoParaLiquidacao.isEmpty()==false) {
		// if(maisRecente==null)
		// maisRecente = historicosConsignacaoSolicSaldoParaLiquidacao.get(0);
		// for (HistoricoConsignacao historicoAux :
		// historicosConsignacaoSolicSaldoParaLiquidacao)
		// if (historicoAux.getId()>maisRecente.getId())
		// maisRecente = historicoAux;
		// }
		// if(maisRecente == null)
		// return null;
	}

	@Override
	public EntidadeConsig getConsignatariaByConsignacao(Consignacao consignacao) {
		return consignacao.getEntidadeConsig();
	}

	@Override
	public boolean validaSolicMargemFiltro(Map<String, Object> body, Model model) {

		List<String> detailedErrors = new ArrayList<String>();
		if (body.get("consigSelect") == null || StringUtils.isBlank(body.get("consigSelect").toString())) {
			detailedErrors.add(msg.get("campo.consig.obrigatorio"));
		} else {
			Long idConsig = (body.get("consigSelect") == null)
					|| StringUtils.isBlank(body.get("consigSelect").toString()) ? null
							: Long.parseLong(body.get("consigSelect").toString());
			model.addAttribute("consigSelect", idConsig);
			if (idConsig != null)
				model.addAttribute("servicos",
						comboUtilService.getServiceComboByEntidadeConsig(entidadeConsigService.findById(idConsig)));

		}

		if (body.get("servSelect") == null || StringUtils.isBlank(body.get("servSelect").toString())) {
			detailedErrors.add(msg.get("campo.servico.obrigatorio"));
		} else {
			model.addAttribute("servSelect",
					(body.get("servSelect") == null) || StringUtils.isBlank(body.get("servSelect").toString()) ? ""
							: Long.parseLong(body.get("servSelect").toString()));
		}

		if ((body.get("matInput") == null || StringUtils.isBlank(body.get("matInput").toString()))
				&& (body.get("cpfInput") == null || StringUtils.isBlank(body.get("cpfInput").toString()))) {
			detailedErrors.add(msg.get("campo.matricula.cpf.invalido"));
		} else {
			if ((body.get("matInput") != null && StringUtils.isNotBlank(body.get("matInput").toString()))
					&& body.get("matInput").toString().length() == 7) {
				PesfisComgepResponse pesfisComgep = usrUtilService
						.getMilitarByMatriculaOrCpf(body.get("matInput").toString());
				if (pesfisComgep != null && StringUtils.isNotBlank(pesfisComgep.getNrOrdem())) {
					model.addAttribute("pesfiscomgep", pesfisComgep);
				} else {
					detailedErrors.add(msg.get("campo.matricula.invalido"));
				}

			} else if (body.get("cpfInput") != null && StringUtils.isNotBlank(body.get("cpfInput").toString())) {
				PesfisComgepResponse pesfisComgep = usrUtilService
						.getMilitarByMatriculaOrCpf(body.get("cpfInput").toString());
				if (pesfisComgep != null && StringUtils.isNotBlank(pesfisComgep.getNrCpf())) {
					model.addAttribute("pesfiscomgep", pesfisComgep);
				} else {
					detailedErrors.add(msg.get("campo.cpf.invalido"));
				}
			} else {
				detailedErrors.add(msg.get("campo.matricula.invalido"));
			}
		}
		model.addAttribute("consignatarias", comboUtilService.findEntidadesConsig());
		if (!detailedErrors.isEmpty()) {
			if ((body.get("consignataria") != null) && StringUtils.isNotBlank(body.get("consignataria").toString())) {
				EntidadeConsig ent = entidadeConsigService
						.findById(Long.parseLong(body.get("consignataria").toString()));
				model.addAttribute("servicos", comboUtilService.getServiceComboByEntidadeConsig(ent));
			}
			model.addAttribute("detailedErrors", detailedErrors);
			return false;
		}
		return true;
	}

	@Override
	public SolicitacaoSaldoDevedor findSolicSaldoDevedorById(long id) {
		return solicSaldoDevedorRepo.findById(id);
	}

	public Consignacao gerarConsignacao(Map<String, Object> body) {
		try {
			String paramPessoa = (isMatricula(body, "matInput")) ? body.get("matInput").toString()
					: GenericUtil.cleanChr(body.get("cpfInput").toString());

			EntidadeConsig consignataria = entidadeConsigService
					.findById(Long.valueOf(body.get("consigSelect").toString()));
			consignataria.setOrganizacao(getOrgConverter(body));
			Servico servico = servicoService.findById(Long.valueOf(body.get("servSelect").toString()));

			ServicoConsig servicoConsig = servicoConsigService.findByServicoAndEntidadeConsig(servico, consignataria);
			ServidorConsig servidorConsig = (paramPessoa.length() < 11)
					? servidorConsigService.findByMatricula(paramPessoa)
					: servidorConsigService.findByCpf(paramPessoa);

			servidorConsig.setPesfis(usrUtilService.getPessoaConverter(paramPessoa));
			return new Consignacao(servicoConsig, servidorConsig);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Consignacao gerarConsignacao(UsuarioLogado usuarioLogado, Long idServico, Long idEntidadeConsig) {
		// TODO Auto-generated method stub
		return null;
	}

	public Consignacao filterServidor(String cpf, String nrAde, String matricula, String idHash, Model model) {

		Consignacao consig = new Consignacao();

		if (usuarioLogado.getRole().equals("CONSIGEXT_SERVIDOR")) {
			ServidorConsig servidor = servidorConsigService.findByCpf(usuarioLogado.getCPF());
			model.addAttribute("servidor", servidor);
			model.addAttribute("consignacoes", consignacaoRepository.findByServidor(servidor.getId()));

		} else if (usuarioLogado.getRole().equals("CONSIGEXT_CONSIGNATARIA")) {
			UsuarioExternoResponse userExt = usrUtilService.getFisicaByCpf(usuarioLogado.getCPF());
			String pass = PasswordUtil.sha1x64(idHash);
			if (userExt.getPasswordSha().equals(pass)) {
				ServidorConsig servidor = new ServidorConsig();
				UsuarioEc ec = usuarioEcRepo.findByIdUsuarioExterno(userExt.getIdUsuario());
				if (StringUtils.isNotBlank(cpf)) {
					servidor = servidorConsigService.findByCpf(cpf);
					model.addAttribute("consignacoes", consignacaoRepository.findByConsignatariaAndServidor(
							ec.getTEntidadeConsig().getCdEntidade(), servidor.getPesfis().getNrCpf()));
				}
				if (StringUtils.isNotBlank(nrAde)) {

					List<Consignacao> consigs = consignacaoRepository.findByConsignatariaAndNrAde(
							ec.getTEntidadeConsig().getCdEntidade(), Long.parseLong(nrAde));
					Optional<Consignacao> cons = consigs.stream().findFirst();
					if (cons.isPresent()) {
						servidor = cons.get().getServidorConsig();
					}
					model.addAttribute("consignacoes", consigs);
				}
				if (StringUtils.isNotBlank(matricula)) {
					servidor = servidorConsigService.findByMatricula(matricula);
					model.addAttribute("consignacoes", consignacaoRepository.findByConsignatariaAndServidorMatricula(
							ec.getTEntidadeConsig().getCdEntidade(), matricula));
				}
				model.addAttribute("servidor", servidor);

			}

		} else {

			if (!cpf.equals("")) {
				ServidorConsig servidor = servidorConsigService.findByCpf(cpf);
				model.addAttribute("consignacoes", consignacaoRepository.findByServidor(servidor.getId()));
			}
			if (!nrAde.equals("")) {
				model.addAttribute("consignacoes", consignacaoRepository.findByNrAde(Long.valueOf(nrAde)));
			}
			if (!matricula.equals("")) {
				ServidorConsig servidor = servidorConsigService.findByMatricula(matricula);
				model.addAttribute("consignacoes", consignacaoRepository.findByServidor(servidor.getId()));
			}
		}

		return consig;

	}

	public Consignacao cancelarReserva(String id, String perfil, Model model, Map<String, Object> body) {
		List<String> detailedErrors = new ArrayList<String>();
		Consignacao consig = new Consignacao();
		consig = consignacaoRepository.findById(Long.valueOf(id));
		StatusConsignacao status = new StatusConsignacao(6);
		TipoHistoricoConsig tipoHistorico = new TipoHistoricoConsig(4);
		String valAtual = String.valueOf(consig.getStatusConsignacao().getId());
		String parametro = parametroService.findByServicoConsigSiglaParametro(consig.getServicoConsig(),
				"INFINCMGREST");
		boolean grava = false;

		if (consig.getStatusConsignacao().getId() == 16 && perfil.equals("CONSIGEXT_SERVIDOR")) {

			consig.setStatusConsignacao(status);
			grava = true;

		} else if ((consig.getStatusConsignacao().getId() == 1 || consig.getStatusConsignacao().getId() == 8
				|| consig.getStatusConsignacao().getId() == 16)
				&& (perfil.equals("CONSIGEXT_GESTOR") || (perfil.equals("CONSIGEXT_CONSIGNATARIA")))) {

			consig.setStatusConsignacao(status);
			grava = true;
		} else {

			detailedErrors.add(msg.get("campo.status.valida"));
			model.addAttribute("detailedErrors", detailedErrors);
			grava = false;
		}

		if (grava) {

			try {
				consignacaoRepository.save(consig);
				historicoService.salvarHistorico(consig, "ID_STATUS_CONSIGNACAO", valAtual, status, tipoHistorico,
						body);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return consig;
	}

	public Double getVlTotalConsignacao(Long idConsignacao) {
		Consignacao consignacao = getByIdConsignacao(idConsignacao);
		List<ParcelaConsignacao> parcelas = consignacao.getParcelaConsignacaos();
		double vlTotal = 0;
		for (ParcelaConsignacao parcela : parcelas)
			vlTotal += parcela.getVlParcela().doubleValue();
		return vlTotal;
	}

	@Override
	public String getVlTotalConsignacaoString(Long idConsignacao) {
		return GenericUtil.printNumberTwoDigits(getVlTotalConsignacao(idConsignacao));
	}

	public Consignacao confirmarReserva(String id, String perfil, Model model, Map<String, Object> body) {
		List<String> detailedErrors = new ArrayList<String>();
		Consignacao consig = new Consignacao();
		consig = consignacaoRepository.findById(Long.valueOf(id));
		TipoHistoricoConsig tipoHistorico = new TipoHistoricoConsig(4);
		StatusConsignacao status = new StatusConsignacao();
		// status = statusconsigService.getStatusByNmStatus("AGUARD. MARGEM");
		// status.addTConsignacao(consig);
		String valAtual = String.valueOf(consig.getStatusConsignacao().getId());
		boolean grava = false;

		if (perfil.equals("CONSIGEXT_SERVIDOR")) {

			detailedErrors.add(msg.get("usuario.consig.sem.permissao"));

		} else if ((consig.getStatusConsignacao().getNmStatus().equals("AGUARD. CONFIRMAÇÃO")
				|| consig.getStatusConsignacao().getNmStatus().equals("SOLICITAÇÃO"))
				&& (perfil.equals("CONSIGEXT_GESTOR") || (perfil.equals("CONSIGEXT_CONSIGNATARIA")))) {

			// consig.setStatusConsignacao(status);
			grava = true;
		} else {

			detailedErrors.add(msg.get("campo.status.valida"));
		}

		model.addAttribute("detailedErrors", detailedErrors);

		if (grava) {
			try {
				consignacaoRepository.save(consig);
				historicoService.salvarHistorico(consig, "ID_STATUS_CONSIGNACAO", valAtual, status, tipoHistorico,
						body);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return consig;

	}

	// TODO: Para os métodos até o próximo todo serão montados apenas as
	// validações de perfil, faltando montar as regras de negócio
	public Consignacao editarConsignacao(String id, String perfil, Model model) {
		List<String> detailedErrors = new ArrayList<String>();
		Consignacao consig = new Consignacao();
		consig = consignacaoRepository.findById(Long.valueOf(id));
		if (usuarioLogado.getRole().equals("CONSIGEXT_SERVIDOR")) {
			detailedErrors.add(msg.get("usuario.consig.sem.permissao"));
			model.addAttribute("detailedErrors", detailedErrors);
		} else if (!usuarioLogado.getRole().equals("CONSIGEXT_SERVIDOR") && usuarioLogado.isTemPermissao(5)) {
			// TODO: aqui vai executar o modal
		}

		return consig;
	}

	public Consignacao bloquearConsignacao(String id, String perfil, Model model, Map<String, Object> body) {
		List<String> detailedErrors = new ArrayList<String>();
		Consignacao consig = new Consignacao();
		consig = consignacaoRepository.findById(Long.valueOf(id));
		TipoHistoricoConsig tipoHistorico = new TipoHistoricoConsig(4);
		String valAtual = String.valueOf(consig.getStatusConsignacao().getId());
		StatusConsignacao status = new StatusConsignacao();
		boolean grava = false;
		if (usuarioLogado.getRole().equals("CONSIGEXT_SERVIDOR")) {
			detailedErrors.add(msg.get("usuario.consig.sem.permissao"));
		} else if (usuarioLogado.getRole().equals("CONSIGEXT_GESTOR")
				&& usuarioLogado.hasPermissao("SUSPENDER CONSIGNAÇÃO")) {
			if ((consig.getStatusConsignacao().equals("DEFERIDA")
					|| consig.getStatusConsignacao().equals("EM ANDAMENTO")
					|| consig.getStatusConsignacao().equals("ESTOQUE"))) {
				// status = statusconsigService.getStatusByNmStatus("SUSPENSA PELA CSE");
				status.addTConsignacao(consig);
				grava = true;
			} else {
				detailedErrors.add(msg.get("campo.status.valida"));
			}
		} else if (usuarioLogado.getRole().equals("CONSIGEXT_CONSIGNATARIA")
				&& usuarioLogado.hasPermissao("SUSPENDER CONSIGNAÇÃO")) {
			// status = statusconsigService.getStatusByNmStatus("SUSPENSA");
			status.addTConsignacao(consig);
			grava = true;
		} else {
			detailedErrors.add(msg.get("usuario.consig.sem.permissao"));
		}
		model.addAttribute("detailedErrors", detailedErrors);
		if (grava) {
			try {
				consignacaoRepository.save(consig);
				historicoService.salvarHistorico(consig, "ID_STATUS_CONSIGNACAO", valAtual, status, tipoHistorico,
						body);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return consig;
	}

	@Override
	public String liquidarContrato(String observacao, String nameOfFile, long idConsignacao, String motivo,
			RedirectAttributes redirectAttrs, MultipartFile multipartFile) {

		Consignacao consignacao = getByIdConsignacao(idConsignacao);
		List<String> errors = new ArrayList<>();
		File file = null;
		if (!multipartFile.getOriginalFilename().isEmpty())
			try {
				file = cargaService.convertMultiPartToFile(multipartFile);
				cargaService.save(TipoCarga.ANEXO, file,
						motivo + " (" + observacao + ") - Liquidacao (Anexo: " + nameOfFile + ")");
			} catch (Exception e) {
				e.printStackTrace();
				errors.add(msg.get("upload.erro"));
				redirectAttrs.addFlashAttribute("detailedErrors", errors);
				return "redirect:/consignacao/liquidarconsignacao/" + idConsignacao;
			}
		// if (isLastMonth(idConsignacao) == false && passouPagamento()) {
		// errors.add(msg.get("consignacao.suspender.passouPagamento"));
		// redirectAttrs.addFlashAttribute("detailedSuccess", errors);
		// }
		EmailHelper helper = new EmailHelper();
		helper.sendLiquidacaoContrato(consignacao.getNrAde(), usuarioService.emailServidorByConsig(consignacao), file,
				motivo, observacao, nameOfFile);
		String emailConsignataria = entidadeConsigService.findById(getConsignatariaByConsignacao(consignacao).getId())
				.getDsEmailEc();
		helper.sendLiquidacaoContrato(consignacao.getNrAde(), emailConsignataria, file, motivo, observacao, nameOfFile);
		liquidarConsig(idConsignacao, motivo, observacao);
		List<String> detailedSuccess = new ArrayList<String>();
		detailedSuccess.add(msg.get("consignacao.liquidar.contrato"));
		redirectAttrs.addFlashAttribute("detailedSuccess", detailedSuccess);
		return "redirect:/consignacao/exibir/" + idConsignacao;
	}

	@Override
	@Transient
	public void liquidarConsig(long idConsignacao, String motivo, String observacao) {

		Consignacao consignacao = getByIdConsignacao(idConsignacao);

		// Liquida as parcelas;

		List<ParcelaConsignacao> parcelas = parcelaService.findByConsignacao(consignacao);
		List<HistoricoParcela> historicosParcelas = new ArrayList<HistoricoParcela>();
		liquidarParcelas(parcelas, historicosParcelas);
		parcelaService.save(parcelas);

		// Liquida a consignação;
		Long statusOldConsig = consignacao.getStatusConsignacao().getId();
		consignacao.setStatusConsignacao(new StatusConsignacao(15));
		consignacaoRepository.save(consignacao);

		List<HistoricoConsignacao> historicos = consignacao.getHistoricoConsignacaos();
		String ip;
		try {
			ip = usuarioLogado.obterIP();
		} catch (Exception e) {
			ip = "";
			e.printStackTrace();
		}

		HistoricoConsignacao hc = new HistoricoConsignacao(
				"Liquidação de consignação. (motivo: " + motivo + ", observação: " + observacao + ")", (new Date()),
				usuarioLogado.getCPF(), ip, consignacao, new TipoHistoricoConsig(9), "ID_STATUS_CONSIGNACAO",
				String.valueOf(statusOldConsig), String.valueOf(15), historicosParcelas);
		for (HistoricoParcela hp : historicosParcelas) {
			hp.setHistoricoConsignacao(hc);
		}
		historicos.add(hc);

		consignacao.setHistoricoConsignacaos(historicos);
		consignacaoRepository.save(consignacao);

	}

	public void liquidarParcelas(List<ParcelaConsignacao> parcelas, List<HistoricoParcela> historicoParcelas) {
		for (ParcelaConsignacao parcela : parcelas) {
			// STATUS QUE ALTERAM PARA 'LIQUIDADA CONTRATO (10)' NA LIQUIDAÇÃO DE CONTRATO:
			// 'ABERTO(1)', 'REJEITADA FOLHA(6)', 'SEM RETORNO(8)';
			if (parcela.getStatusParcela().getId() == 1 || parcela.getStatusParcela().getId() == 6
					|| parcela.getStatusParcela().getId() == 8) {
				StatusParcela statusOld = parcela.getStatusParcela();
				parcela.setStatusParcela(new StatusParcela(10));
				historicoParcelas
						.add(new HistoricoParcela(String.valueOf(statusOld.getId()), String.valueOf(10), parcela));
				parcela.setDtDesconto(new Date());
			}
		}
	}

	@Override
	public String desliquidarContrato(String observacao, String nameOfFile, long idConsignacao, String motivo,
			RedirectAttributes redirectAttrs, MultipartFile multipartFile, Map<String, Object> body) {
		Consignacao consignacao = getByIdConsignacao(idConsignacao);
		List<String> errors = new ArrayList<>();
		File file = null;
		if (!multipartFile.getOriginalFilename().isEmpty())
			try {
				file = cargaService.convertMultiPartToFile(multipartFile);
				cargaService.save(TipoCarga.ANEXO, file,
						motivo + " (" + observacao + ") - Liquidacao (Anexo: " + nameOfFile + ")");
			} catch (Exception e) {
				e.printStackTrace();
				errors.add(msg.get("upload.erro"));
				redirectAttrs.addFlashAttribute("detailedErrors", errors);
				return "redirect:/consignacao/liquidarconsignacao/" + idConsignacao;
			}
		String nrOrdem = consignacao.getServidorConsig().getPesfis().getNrOrdem();
		BigDecimal margem = GenericUtil.stringToBigDecimal(usrUtilService.getMargem30MesAtual(nrOrdem));
		BigDecimal novaMargem = margem.subtract(consignacao.getVlPrestacao());
		boolean validarMargem = true;
		if (body.get("validarmargem") == null) {
			validarMargem = false;
		} else if (usuarioLogado.getRole().equals("CONSIGEXT_CONSIGNATARIA")
				|| (body.get("validarmargem").toString().equals("on")
						&& usuarioLogado.getRole().equals("CONSIGEXT_GESTOR"))) {
			if (novaMargem.compareTo(BigDecimal.ZERO) < 0) {
				errors.add(msg.get("margem.insuficiente.desliquidacao"));
				redirectAttrs.addFlashAttribute("detailedErrors", errors);
				return "redirect:/consignacao/liquidarconsignacao/" + idConsignacao;
			}
		}

		boolean reimplantarcontrato = true;
		// if (body.get("reimplantarcontrato") == null) {
		// reimplantarcontrato = false;
		// }
		//
		// //TODO: CONFERIR PERIODO CRITICO
		// if (periodoCritico()) {
		// errors.add(msg.get("periodo.critico"));
		// redirectAttrs.addFlashAttribute("detailedErrors", errors);
		// return "redirect:/consignacao/liquidarconsignacao/" + idConsignacao;
		// }
		//
		EmailHelper helper = new EmailHelper();
		helper.sendDesliquidacaoContrato(consignacao.getNrAde(), usuarioService.emailServidorByConsig(consignacao),
				file, motivo, observacao, nameOfFile);
		String emailConsignataria = entidadeConsigService.findById(getConsignatariaByConsignacao(consignacao).getId())
				.getDsEmailEc();
		helper.sendDesliquidacaoContrato(consignacao.getNrAde(), emailConsignataria, file, motivo, observacao,
				nameOfFile);
		desliquidarConsig(idConsignacao, motivo, observacao, validarMargem, reimplantarcontrato);
		List<String> detailedSuccess = new ArrayList<String>();
		detailedSuccess.add(msg.get("consignacao.desliquidar.contrato"));
		redirectAttrs.addFlashAttribute("detailedSuccess", detailedSuccess);

		return "redirect:/consignacao/exibir/" + idConsignacao;
	}

	@Override
	@Transient
	public void desliquidarConsig(long idConsignacao, String motivo, String observacao, boolean validar,
			boolean reimplantar) {

		Calendar c = Calendar.getInstance();
		Date data = c.getTime();

		Consignacao consignacao = getByIdConsignacao(idConsignacao);
		List<ParcelaConsignacao> parcelas = consignacao.getParcelaConsignacaos();
		parcelas.sort((ParcelaConsignacao p1, ParcelaConsignacao p2) -> p1.getNrParcela().compareTo(p2.getNrParcela()));

		HistoricoConsignacao historicoConsignacao = historicoService.findUltimoHistorico(consignacao.getId());
		List<HistoricoParcela> historicoParcelas = historicoParcelaService
				.findByHistoricoConsignacao(historicoConsignacao);

		List<HistoricoParcela> historicoParcelasAux = new ArrayList<HistoricoParcela>();

		consignacao.setStatusConsignacao(new StatusConsignacao(Long.parseLong(historicoConsignacao.getVlAnterior())));

		for (ParcelaConsignacao p : parcelas) {
			for (HistoricoParcela hp : historicoParcelas) {
				if (p.getId() == hp.getParcela().getId()) {
					StatusParcela statusOld = p.getStatusParcela();
					p.setStatusParcela(new StatusParcela(Long.parseLong(hp.getVlAnterior())));
					ParcelaUtil.definirPeriodoParcela(consignacao.getNrCarencia(), p);
					historicoParcelasAux
							.add(new HistoricoParcela(String.valueOf(statusOld.getId()), hp.getVlAnterior(), p));
				}
			}
		}
		consignacao.setParcelaConsignacaos(parcelas);

		// MARGEM
		if (validar) {
			// TODO:Alterar regra de margem !!!
//			BigDecimal novaMargem = (consignacao.getServidorConsig().getVlMargem30())
//					.subtract(consignacao.getVlPrestacao());
//			consignacao.getServidorConsig().setVlMargem30(novaMargem);
		}

		List<HistoricoConsignacao> historicos = consignacao.getHistoricoConsignacaos();
		String ip;
		try {
			ip = usuarioLogado.obterIP();
		} catch (Exception e) {
			ip = "";
			e.printStackTrace();
		}

		HistoricoConsignacao hc = new HistoricoConsignacao(
				"Desliquidação de consignação. (motivo: " + motivo + ", observação: " + observacao + ")", (new Date()),
				usuarioLogado.getCPF(), ip, consignacao, new TipoHistoricoConsig(12), "ID_STATUS_CONSIGNACAO",
				String.valueOf(historicoConsignacao.getVlNovo()), String.valueOf(historicoConsignacao.getVlAnterior()),
				historicoParcelasAux);
		for (HistoricoParcela hp : historicoParcelasAux) {
			hp.setHistoricoConsignacao(hc);
		}
		historicos.add(hc);

		if (reimplantar) {
			historicos.add(new HistoricoConsignacao("Gerar arquivo de movimento do FOPAG", (new Date()),
					usuarioLogado.getCPF(), ip, consignacao, new TipoHistoricoConsig(12), "REIMPLANTACAO", null,
					String.valueOf(reimplantar)));
		}

		consignacao.setHistoricoConsignacaos(historicos);
		consignacaoRepository.save(consignacao);
	}

	public Consignacao renegociacaoConsignacao(String id, String perfil, Model model) {
		List<String> detailedErrors = new ArrayList<String>();
		Consignacao consig = new Consignacao();
		consig = consignacaoRepository.findById(Long.valueOf(id));
		if (usuarioLogado.getRole().equals("CONSIGEXT_SERVIDOR")) {
			detailedErrors.add(msg.get("usuario.consig.sem.permissao"));
			model.addAttribute("detailedErrors", detailedErrors);
		} else if (!usuarioLogado.getRole().equals("CONSIGEXT_SERVIDOR") && usuarioLogado.isTemPermissao(20)) {
			// TODO: aqui vai executar o modal
		}

		return consig;
	}

	public Consignacao reimplantarConsignacao(String id, String perfil, Model model) {
		List<String> detailedErrors = new ArrayList<String>();
		Consignacao consig = new Consignacao();
		consig = consignacaoRepository.findById(Long.valueOf(id));
		if (usuarioLogado.getRole().equals("CONSIGEXT_SERVIDOR")) {
			detailedErrors.add(msg.get("usuario.consig.sem.permissao"));
			model.addAttribute("detailedErrors", detailedErrors);
		} else if (!usuarioLogado.getRole().equals("CONSIGEXT_SERVIDOR") && usuarioLogado.isTemPermissao(95)) {
			// TODO: aqui vai executar o modal
		}

		return consig;
	}

	@Transactional
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

			if (consignacao.getNrCarencia() == null) {
				consignacao.setNrCarencia(0L);
			}

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
			historicos.add(new HistoricoConsignacao("Inclusão de nova consignacao.", (new Date()),
					usuarioLogado.getCPF(), ip, consignacao, new TipoHistoricoConsig(1)));
			consignacao.setHistoricoConsignacaos(historicos);

			consignacaoRepository.save(consignacao);

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

		if ((body.get("vlLiquidoLiberado") != null
				&& (body.get("vlLiquidoLiberado").equals("0,00") || body.get("vlLiquidoLiberado").equals("0.00")))
				&& (body.get("vlPrestacao") != null
						&& (body.get("vlPrestacao").equals("0,00") || body.get("vlPrestacao").equals("0.00")))) {
			detailedErrors.add(msg.get("campo.margem.valorliquido.valorprestacao"));
		}

		if (!detailedErrors.isEmpty()) {
			model.addAttribute("detailedErrors", detailedErrors);
			return false;
		}
		return true;
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
	public void reativarConsig(long idConsignacao, String motivo, String observacao) {
		Consignacao consignacao = getByIdConsignacao(idConsignacao);
		long status = consignacao.getStatusConsignacao().getId();
		if (status == 17 || status == 18)
			consignacao.setStatusConsignacao(new StatusConsignacao(8));
		else
			return;
		String ip;
		try {
			ip = usuarioLogado.obterIP();
		} catch (Exception e) {
			ip = "";
			e.printStackTrace();
		}

		List<HistoricoConsignacao> historicosSuspender = historicoService
				.getHistoricoByConsignacaoAndTipoHistorico(consignacao, 8);
		HistoricoConsignacao historicoSuspender = historicosSuspender.get(0);
		for (HistoricoConsignacao historicoAux : historicosSuspender)
			if (historicoAux.getDtHistorico().after(historicoSuspender.getDtHistorico()))
				historicoSuspender = historicoAux;
		Calendar calSuspensao = Calendar.getInstance();
		Calendar calReativacao = Calendar.getInstance();
		calSuspensao.setTime(historicoSuspender.getDtHistorico());
		calReativacao.setTime(new Date());
		String novoFimAAAAMM = updateParcelasAndReturnCdUltimaParcela(consignacao, calReativacao);
		String anteriorFimAAAAMM = consignacao.getCdAnomesFim();
		if (consignacao.getCdAnomesFimReferencia() == null || consignacao.getCdAnomesFimReferencia().length() != 6)
			consignacao.setCdAnomesFimReferencia(anteriorFimAAAAMM);

		List<HistoricoConsignacao> historicos = consignacao.getHistoricoConsignacaos();
		historicos.add(new HistoricoConsignacao(
				"Reativação de consignação. (motivo: " + motivo + ", observação: " + observacao + ")"
						+ " - Fim anterior: " + anteriorFimAAAAMM + " ; Fim Atual: " + novoFimAAAAMM,
				(new Date()), usuarioLogado.getCPF(), ip, consignacao, new TipoHistoricoConsig(7),
				"ID_STATUS_CONSIGNACAO", Long.toString(status),
				Long.toString(consignacao.getStatusConsignacao().getId())));
		consignacao.setHistoricoConsignacaos(historicos);
		consignacao.setCdAnomesFim(novoFimAAAAMM);
		consignacaoRepository.save(consignacao);

	}

	private String updateParcelasAndReturnCdUltimaParcela(Consignacao consignacao, Calendar dataReativacao) {
		List<ParcelaConsignacao> parcelasConsignacao = parcelaRepo.findByConsignacao(consignacao);
		Long ultimaParcela = (long) 0;
		String cdUltimaParcela = null;
		for (ParcelaConsignacao parcela : parcelasConsignacao) {
			Long nrParcela = parcela.getNrParcela();
			if (parcela.getStatusParcela().getId() == 2) {
				if (nrParcela > ultimaParcela) {
					cdUltimaParcela = parcela.getCdAnomes().toString();
					ultimaParcela = nrParcela;
				}
				continue;
			}
			int numMesesToAdd = 0;
			if (dataReativacao.get(Calendar.DAY_OF_MONTH) < 5)
				numMesesToAdd++;
			else
				numMesesToAdd += 2;
			// -----------------------------------------
			Long cdAnoMes = parcela.getCdAnomes();
			Calendar calAnoMes = GenericUtil.getCalendarFromAAAAMM(cdAnoMes.toString());
			calAnoMes.add(Calendar.MONTH, numMesesToAdd);
			parcela.setCdAnomes(Long.parseLong(GenericUtil.getAAAAMMtual(calAnoMes)));
			// -----------------------------------------
			if (nrParcela > ultimaParcela) {
				cdUltimaParcela = parcela.getCdAnomes().toString();
				ultimaParcela = nrParcela;
			}
			// -----------------------------------------
			parcela.setStatusParcela(new StatusParcela(1));
			parcelaRepo.save(parcela);
		}
		return cdUltimaParcela;
	}

	@Override
	public void suspenderConsig(long idConsignacao, String motivo, String observacao) {
		Consignacao consignacao = getByIdConsignacao(idConsignacao);
		long status = consignacao.getStatusConsignacao().getId();
		if (status == 8 && usuarioLogado.isGestor())
			consignacao.setStatusConsignacao(new StatusConsignacao(18));
		else if (status == 8 && usuarioLogado.isConsig())
			consignacao.setStatusConsignacao(new StatusConsignacao(17));
		else
			return;
		List<HistoricoConsignacao> historicos = consignacao.getHistoricoConsignacaos();
		String ip;
		try {
			ip = usuarioLogado.obterIP();
		} catch (Exception e) {
			ip = "";
			e.printStackTrace();
		}
		historicos.add(new HistoricoConsignacao(
				"Suspensão de consignação. (motivo: " + motivo + ", observação: " + observacao + ")", (new Date()),
				usuarioLogado.getCPF(), ip, consignacao, new TipoHistoricoConsig(8), "ID_STATUS_CONSIGNACAO",
				Long.toString(status), Long.toString(consignacao.getStatusConsignacao().getId())));
		consignacao.setHistoricoConsignacaos(historicos);
		// --------------------------------------------------------
		List<ParcelaConsignacao> parcelasConsignacao = parcelaRepo.findByConsignacao(consignacao);
		for (ParcelaConsignacao parcela : parcelasConsignacao)
			if (parcela.getStatusParcela().getId() == 1) {
				parcela.setStatusParcela(new StatusParcela(4));
				parcelaRepo.save(parcela);
			}
		// --------------------------------------------------------
		consignacaoRepository.save(consignacao);
	}

	@Override
	public boolean passouPagamento() {
		List<CfgBasicaPgm> cfgBasicaPgms = cfgBasicaPgmRepository
				.findByKeyOfCfgBasicaPgm_cdAnomes(GenericUtil.getAAAAMMtual());
		CfgBasicaPgm cfgBasicaPgm = cfgBasicaPgms.get(0);
		for (CfgBasicaPgm aux : cfgBasicaPgms)
			if (aux.getKeyOfCfgBasicaPgm().getVersion() > cfgBasicaPgm.getKeyOfCfgBasicaPgm().getVersion())
				cfgBasicaPgm = aux;
		Calendar calNow = Calendar.getInstance();
		Calendar calProcessamento = Calendar.getInstance();
		calProcessamento.setTime(cfgBasicaPgm.getDtProcessamento());
		calNow.setTime(new Date());
		if (calNow.get(Calendar.DAY_OF_MONTH) < calProcessamento.get(Calendar.DAY_OF_MONTH))
			return false;
		return true;
	}

	public boolean periodoCritico() {
		Calendar calNow = Calendar.getInstance();
		Calendar calProcessamento = Calendar.getInstance();

		calProcessamento.setTime(cargaService.findByMaxDateTpCarga("SAIDA"));
		calNow.setTime(new Date());

		if (calNow.get(Calendar.DAY_OF_MONTH) >= 5 && calNow.compareTo(calProcessamento) > 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isLastMonth(long idConsignacao) {
		Consignacao consignacao = consignacaoRepository.findById(idConsignacao);
		String cdAnoMesFim = consignacao.getCdAnomesFim();
		Calendar calUltimoMes = GenericUtil.getCalendarFromAAAAMM(cdAnoMesFim);
		if (calUltimoMes.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)
				&& calUltimoMes.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH))
			return true;
		return false;
	}

	@Override
	public List<HistoricoConsignacao> getHistoricos(Consignacao consignacao) {
		List<HistoricoConsignacao> historicos = consignacao.getHistoricoConsignacaos();

		return historicos;
	}

	@Override
	public ParcelaConsignacao getParcelaConsignacaoById(Long idParcela) {
		return parcelaRepo.findById(idParcela);
	}

	@Override
	public void liquidarParcelas(Consignacao consignacao, Map<String, Object> body) {
		String motivo = body.get("motivoSelect").toString();
		String liquidacaoHistoricoString = "Liquidação das parcelas: ";
		HashMap<Long, String> emailParcelaMap = new HashMap<Long, String>();
		List<HistoricoParcela> historicosParcelas = new ArrayList<HistoricoParcela>();
		for (long nParcela = 1; nParcela <= consignacao.getNrPrestacoes(); nParcela++) {
			if (body.containsKey("parcela " + nParcela) == false)
				continue;
			Long idParcela = Long.parseLong(body.get("parcela " + nParcela).toString());
			ParcelaConsignacao parcela = getParcelaConsignacaoById(idParcela);
			BigDecimal novoRealizado = new BigDecimal(body.get("realizado " + nParcela).toString().replace(",", "."));
			BigDecimal anteriorRealizado = parcela.getVlParcelaRealizado();
			if (anteriorRealizado == null)
				anteriorRealizado = new BigDecimal(0);
			parcela.setVlParcelaRealizado(novoRealizado);
			parcela.setDtDesconto(new Date());
			StatusParcela statusParcela = parcela.getStatusParcela();
			String statusParcelaStr = statusParcela.getNmStatusParcela();
			liquidacaoHistoricoString += " -- Parcela " + nParcela + " -- " + "Antes: " + statusParcelaStr + " -- ";
			parcela.setStatusParcela(new StatusParcela(7));
			String novoRealizadoStr = novoRealizado.setScale(2, RoundingMode.HALF_UP).toString();
			liquidacaoHistoricoString += "(Realizado: " + novoRealizadoStr + ") ; ";
			emailParcelaMap.put(nParcela, " (" + parcela.getCdAnomes() + "): " + "Realizado: " + novoRealizadoStr
					+ " , Status anterior: " + statusParcelaStr);

			HistoricoParcela historicoParcela = new HistoricoParcela();
			historicoParcela.setParcela(parcela);
			historicoParcela.setVlAnterior(statusParcela.getId().toString());
			historicoParcela.setVlNovo(parcela.getStatusParcela().getId().toString());
			historicosParcelas.add(historicoParcela);
		}
		liquidacaoHistoricoString += "Motivo: " + motivo;
		String ip = "";
		try {
			ip = usuarioLogado.obterIP();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<HistoricoConsignacao> historicos = consignacao.getHistoricoConsignacaos();
		String statusConsignacao = Long.toString(consignacao.getStatusConsignacao().getId());
		HistoricoConsignacao historicoConsignacao = new HistoricoConsignacao(liquidacaoHistoricoString, (new Date()),
				usuarioLogado.getCPF(), ip, consignacao, new TipoHistoricoConsig(10), "ID_STATUS_CONSIGNACAO",
				statusConsignacao, statusConsignacao);
		historicos.add(historicoConsignacao);
		for (HistoricoParcela historicoParcela : historicosParcelas) {
			historicoParcela.setHistoricoConsignacao(historicoConsignacao);
			historicoParcelaRepo.save(historicoParcela);
		}
		consignacao.setHistoricoConsignacaos(historicos);
		EmailHelper helper = new EmailHelper();
		helper.sendLiquidacaoParcelas(consignacao.getNrAde(), usuarioService.emailServidorByConsig(consignacao), motivo,
				emailParcelaMap);
		String emailConsignataria = entidadeConsigService.findById(getConsignatariaByConsignacao(consignacao).getId())
				.getDsEmailEc();
		helper.sendLiquidacaoParcelas(consignacao.getNrAde(), emailConsignataria, motivo, emailParcelaMap);
		consignacaoRepository.save(consignacao);
	}

	@Override
	public String desliquidarParcela(Consignacao consignacao, Map<String, Object> body) {
		Long idParcelaDesliquidar = getIdParcelaDesliquidar(consignacao, body);
		ParcelaConsignacao parcela = getParcelaConsignacaoById(idParcelaDesliquidar);
		StatusParcela statusParcelaAnterior = parcela.getStatusParcela();
		String motivo = body.get("motivoSelect").toString();
		StatusParcela statusParcelaAntesDeLiquidar = getStatusParcelaAnteriorNoHistorico(consignacao, parcela);
		if (statusParcelaAntesDeLiquidar == null)
			return "consignacao.desliquidarParcela.parcelaLiquidadaPorLiquidacaoTotalDeContrato";
		String realizadoAntigo = parcela.getVlParcelaRealizado().setScale(2, RoundingMode.HALF_UP).toString();
		;
		parcela.setVlParcelaRealizado(new BigDecimal(0));
		parcela.setDtDesconto(null);
		parcela.setStatusParcela(statusParcelaAntesDeLiquidar);
		String ip = "";
		try {
			ip = usuarioLogado.obterIP();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<HistoricoConsignacao> historicos = consignacao.getHistoricoConsignacaos();
		String statusConsignacao = Long.toString(consignacao.getStatusConsignacao().getId());
		String desliquidacaoEmail = "Parcela " + parcela.getNrParcela() + " desliquidada (" + parcela.getCdAnomes()
				+ ") - Realizado anterior: " + realizadoAntigo;
		HistoricoConsignacao historicoConsignacao = new HistoricoConsignacao(desliquidacaoEmail, new Date(),
				usuarioLogado.getCPF(), ip, consignacao, new TipoHistoricoConsig(11), "ID_STATUS_CONSIGNACAO",
				statusConsignacao, statusConsignacao);
		historicos.add(historicoConsignacao);
		consignacao.setHistoricoConsignacaos(historicos);

		HistoricoParcela historicoParcela = new HistoricoParcela();
		historicoParcela.setParcela(parcela);
		historicoParcela.setVlAnterior(statusParcelaAnterior.getId().toString());
		historicoParcela.setVlNovo(parcela.getStatusParcela().getId().toString());
		historicoParcela.setHistoricoConsignacao(historicoConsignacao);
		historicoParcelaRepo.save(historicoParcela);
		consignacaoRepository.save(consignacao);

		EmailHelper helper = new EmailHelper();
		helper.sendDesliquidacaoParcela(consignacao.getNrAde(), usuarioService.emailServidorByConsig(consignacao),
				motivo, desliquidacaoEmail);
		String emailConsignataria = entidadeConsigService.findById(getConsignatariaByConsignacao(consignacao).getId())
				.getDsEmailEc();
		helper.sendDesliquidacaoParcela(consignacao.getNrAde(), emailConsignataria, motivo, desliquidacaoEmail);
		return "consignacao.desliquidarParcela.sucesso";
	}

	private Long getIdParcelaDesliquidar(Consignacao consignacao, Map<String, Object> body) {
		List<ParcelaConsignacao> parcelas = consignacao.getParcelaConsignacaos();
		for (ParcelaConsignacao parcela : parcelas) {
			if (body.containsKey("action " + parcela.getId()))
				return parcela.getId();
		}
		return null;
	}

	private StatusParcela getStatusParcelaAnteriorNoHistorico(Consignacao consignacao, ParcelaConsignacao parcela) {
		List<HistoricoParcela> historicosParcela = historicoParcelaRepo.findByParcelaAndVlNovo(parcela, "7");
		HistoricoParcela historicoParcela = null;
		if (historicosParcela != null && historicosParcela.isEmpty() == false) {
			historicoParcela = historicosParcela.get(0);
			for (HistoricoParcela historicoAux : historicosParcela)
				if (historicoAux.getId() > historicoParcela.getId())
					historicoParcela = historicoAux;
		}
		if (historicoParcela == null)
			return null;
		return statusParcelaRepository.findById(Long.parseLong(historicoParcela.getVlAnterior()));
	}

	@Override
	public String getErrorInLiquidarParcelas(Consignacao consignacao, Map<String, Object> body) {
		int nParcelasSelected = 0;
		for (int nParcela = 1; nParcela <= consignacao.getNrPrestacoes(); nParcela++) {
			if (body.containsKey("parcela " + nParcela) == false)
				continue;
			nParcelasSelected++;
			try {
				String novoRealizadoStr = body.get("realizado " + nParcela).toString();
				novoRealizadoStr = novoRealizadoStr.replace(",", ".");
				new BigDecimal(novoRealizadoStr);
			} catch (Exception e) {
				return "consignacao.parcelas.liquidar.valorInvalido";
			}
		}
		if (nParcelasSelected == 0)
			return "consignacao.parcelas.liquidar.nenhumValorSelecionado";
		return "consignacao.parcelas.liquidar.sucesso";
	}

	@Override
	public String getAction(Consignacao consignacao, Map<String, Object> body) {
		if (body.containsKey("action"))
			return "action";
		List<ParcelaConsignacao> parcelas = consignacao.getParcelaConsignacaos();
		for (ParcelaConsignacao parcela : parcelas) {
			if (body.containsKey("action " + parcela.getId()))
				return "action " + parcela.getId();
		}
		return null;
	}

	@Override
	public DadosConsig dadosParaAlteracao(Consignacao consignacao) {

		return new DadosConsig(consignacao.getId(), consignacao.getNrCarencia(), consignacao.getVlPrestacao(),
				new Long(consignacao.getParcelasPagas().size()), consignacao.getDsIdentificador(),
				consignacao.getParcelasNaoPagas().size());
	}

	@Override
	public List<StatusConsignacao> findByNmStatusIn(List<String> namesStatus) {
		return statusConsignacaoRepository.findByNmStatusIn(namesStatus);
	}

	@Override
	public void solicitarSaldoDevedor(long idconsignacao) {
		Consignacao consignacao = getByIdConsignacao(idconsignacao);
		EmailHelper helper = new EmailHelper();
		String consOuLiquid = solicSaldoDevedorConsultaOuLiquid();
		int tipoHistorico = consOuLiquid.equals("0") ? 15 : 14;
		String descHistorico = consOuLiquid.equals("0") ? "Solicitação de saldo devedor para consulta"
				: "Solicitação de saldo devedor para liquidação";
		helper.sendSolicitacaoSaldoDevedor(consignacao, consOuLiquid, descHistorico);
		String ip;
		try {
			ip = usuarioLogado.obterIP();
		} catch (Exception e) {
			ip = "";
			e.printStackTrace();
		}
		List<HistoricoConsignacao> historicos = consignacao.getHistoricoConsignacaos();
		String statusConsignacao = Long.toString(consignacao.getStatusConsignacao().getId());
		HistoricoConsignacao historicoConsignacao = new HistoricoConsignacao(descHistorico, (new Date()),
				usuarioLogado.getCPF(), ip, consignacao, new TipoHistoricoConsig(tipoHistorico),
				"ID_STATUS_CONSIGNACAO", statusConsignacao, statusConsignacao);
		historicos.add(historicoConsignacao);
		consignacao.setHistoricoConsignacaos(historicos);
		historicoConsignacaoRepo.save(historicoConsignacao);

		TipoSolicSaldoDevedor tipoSolicitacao = consOuLiquid.equals("0") ? TipoSolicSaldoDevedor.CONSULTA
				: TipoSolicSaldoDevedor.LIQUIDACAO;
		SolicitacaoSaldoDevedor solic = new SolicitacaoSaldoDevedor(new Date(), usuarioLogado.getCPF(), ip, consignacao,
				tipoSolicitacao);
		solicSaldoDevedorRepo.save(solic);
	}

	@Override
	public String getErrorMessageAddServicoConsig(Map<String, Object> body) {
		if (body.get("serviceSelect").toString().isEmpty())
			return "consignataria.criarNovoServico.servicoVazio";
		if (body.get("caixaSelect").toString().isEmpty())
			return "consignataria.criarNovoServico.caixaVazio";
		return null;
	}

	@Override
	public void addServicoConsig(Map<String, Object> body) {
		Long idConsignataria = Long.parseLong(body.get("idConsignataria").toString());
		Long idServico = Long.parseLong(body.get("serviceSelect").toString());
		EntidadeConsig entidadeConsig = entidadeConsigService.findById(idConsignataria);
		Servico serv = servicoService.findById(idServico);
		ServicoConsig servicoConsig = servicoConsigRepo.findByServicoAndEntidadeConsig(serv, entidadeConsig);
		if (servicoConsig == null) {
			servicoConsig = new ServicoConsig(entidadeConsig, serv);
			servicoConsig.setStServico(1);
		}
		String cdCaixa = body.get("caixaSelect").toString();
		CaixaPagamento caixa = caixaPagamentoRepo.findByCdCaixa(cdCaixa);
		servicoConsig.setCaixaPagamento(caixa);
		servicoConsigRepo.save(servicoConsig);
	}

	@Override
	public void addRecoveredParams(RedirectAttributes redirectAttrs, Map<String, Object> body) {
		redirectAttrs.addFlashAttribute("organizacaoRecovered", GenericUtil.getStringInBody(body, "organizacaoSelect"));
		redirectAttrs.addFlashAttribute("consignatariaRecovered",
				GenericUtil.getLongInBody(body, "consignatariaSelect"));
		redirectAttrs.addFlashAttribute("serviceRecovered", GenericUtil.getLongInBody(body, "servicoSelect"));
		redirectAttrs.addFlashAttribute("lastDateRecovered", GenericUtil.getStringInBody(body, "lastDate"));
		redirectAttrs.addFlashAttribute("firstDateRecovered", GenericUtil.getStringInBody(body, "firstDate"));
		redirectAttrs.addFlashAttribute("nrAdeRecovered", GenericUtil.getStringInBody(body, "nrAde"));
		redirectAttrs.addFlashAttribute("nrOrdemRecovered", GenericUtil.getStringInBody(body, "nrOrdem"));
		redirectAttrs.addFlashAttribute("nrCpfRecovered", GenericUtil.getStringInBody(body, "nrCpf"));
	}

	@Override
	public String getErrorInFiltroSolicSaldoDevedor(Map<String, Object> body) {
		if (body.get("situacaoSolic") != null && body.get("situacaoSolic").toString().equals("especificas")
				&& GenericUtil.getLongInBody(body, "nDiasBloqueio") == null)
			return "saldoDevedor.validacao.numDiasInvalido";
		if (body.get("nrAde").toString().isEmpty() == false && GenericUtil.getLongInBody(body, "nrAde") == null)
			return "saldoDevedor.validacao.numAdeInvalido";
		return null;
	}

	@Override
	public Object[] processFile(Map<String, Object> body, RedirectAttributes redirectAttrs, TipoCarga tipo,
			MultipartFile multipartFile, String errorCodeInSend, String descricao, long idSolic) {
		List<String> errors = new ArrayList<>();
		Object[] redirect_idFile = new Object[4];
		redirect_idFile[0] = null;
		redirect_idFile[1] = null;
		redirect_idFile[2] = null;
		redirect_idFile[3] = null;
		if (!multipartFile.getOriginalFilename().isEmpty())
			try {
				File file = cargaService.convertMultiPartToFile(multipartFile);
				String maxSizeStr = parametroSistemaService.getVlParamSistemaByIdParametro(17);
				long maxSizeKb = 0;
				if (maxSizeStr == null || maxSizeStr.isEmpty())
					maxSizeKb = 100000;
				else
					maxSizeKb = Long.parseLong(maxSizeStr);
				if (file.length() / 1000 > maxSizeKb) {
					errors.add("Arquivo com " + (int) (file.length() / 1000)
							+ " kb, o máximo possível para o tamanho do arquivo é " + maxSizeKb + " kb");
					redirectAttrs.addFlashAttribute("detailedErrors", errors);
					redirect_idFile[0] = "redirect:/consignacao/solicitacao/informarSaldo/" + idSolic;
					return redirect_idFile;
				}
				redirect_idFile[1] = cargaService.save(tipo, file, descricao + multipartFile.getOriginalFilename());
				redirect_idFile[2] = file;
				redirect_idFile[3] = file.getPath();
			} catch (Exception e) {
				e.printStackTrace();
				errors.add(msg.get(errorCodeInSend));
				redirectAttrs.addFlashAttribute("detailedErrors", errors);
				redirect_idFile[0] = "redirect:/consignacao/solicitacao/informarSaldo/" + idSolic;
				return redirect_idFile;
			}
		return redirect_idFile;
	}

	/**
	 * @TODO alterar o metodo getSolicitacoesSaldoDevedor para verificar as
	 *       solicitacoes com bloqueio para n dias
	 * @author simaoags
	 */
	@Override
	public List<SolicitacaoSaldoDevedor> getSolicitacoesSaldoDevedor(Map<String, Object> body) {
		List<SolicitacaoSaldoDevedor> solics = null;
		List<SolicitacaoSaldoDevedor> removeSolics;
		// ---------------------------------------------------------------------------------
		// ---------------------------------------------------------------------------------
		if (usuarioLogado.isGestor()) {
			// ---------------------------------------------------------------------------------
			String cdOrg = GenericUtil.getStringInBody(body, "organizacaoSelect");
			if (cdOrg != null && cdOrg.isEmpty() == false) {
				solics = solicSaldoDevedorRepo.findByConsignacao_ServicoConsig_EntidadeConsig_Organizacao_cdOrg(cdOrg);
			}
			// ---------------------------------------------------------------------------------
			Long idConsignataria = GenericUtil.getLongInBody(body, "consignatariaSelect");
			if (idConsignataria != null) {
				if (solics == null)
					solics = solicSaldoDevedorRepo.findByConsignacao_ServicoConsig_EntidadeConsig_id(idConsignataria);
				else {
					removeSolics = new ArrayList<SolicitacaoSaldoDevedor>();
					for (SolicitacaoSaldoDevedor solicAux : solics)
						if (solicAux.getConsignacao().getEntidadeConsig().getId() != idConsignataria)
							removeSolics.add(solicAux);
					for (SolicitacaoSaldoDevedor removeSolicAux : removeSolics)
						solics.remove(removeSolicAux);
				}
			}
			// ---------------------------------------------------------------------------------
			if (solics == null)
				solics = solicSaldoDevedorRepo.findAll();
		} else if (usuarioLogado.isConsig()) {
			EntidadeConsig entidade = usuarioLogado.getUsuarioCorrente().getEntidadeConsig();
			solics = solicSaldoDevedorRepo.findByConsignacao_ServicoConsig_EntidadeConsig_id(entidade.getId());
		}
		// ---------------------------------------------------------------------------------
		// ---------------------------------------------------------------------------------
		Long idServico = GenericUtil.getLongInBody(body, "servicoSelect");
		if (idServico != null) {
			removeSolics = new ArrayList<SolicitacaoSaldoDevedor>();
			for (SolicitacaoSaldoDevedor solicAux : solics)
				if (solicAux.getConsignacao().getServicoConsig().getServico().getId() != idServico)
					removeSolics.add(solicAux);
			for (SolicitacaoSaldoDevedor removeSolicAux : removeSolics)
				solics.remove(removeSolicAux);
		}
		// ---------------------------------------------------------------------------------
		Date firstDate = GenericUtil.getDateInBody(body, "firstDate");
		if (firstDate != null) {
			removeSolics = new ArrayList<SolicitacaoSaldoDevedor>();
			for (SolicitacaoSaldoDevedor solicAux : solics)
				if (solicAux.getDtSolicitacao().before(firstDate))
					removeSolics.add(solicAux);
			for (SolicitacaoSaldoDevedor removeSolicAux : removeSolics)
				solics.remove(removeSolicAux);
		}
		// ---------------------------------------------------------------------------------
		Date lastDate = GenericUtil.getDateInBody(body, "lastDate");
		if (lastDate != null) {
			removeSolics = new ArrayList<SolicitacaoSaldoDevedor>();
			for (SolicitacaoSaldoDevedor solicAux : solics)
				if (solicAux.getDtSolicitacao().after(lastDate))
					removeSolics.add(solicAux);
			for (SolicitacaoSaldoDevedor removeSolicAux : removeSolics)
				solics.remove(removeSolicAux);
		}
		// ---------------------------------------------------------------------------------
		Long nrAde = GenericUtil.getLongInBody(body, "nrAde");
		if (nrAde != null) {
			removeSolics = new ArrayList<SolicitacaoSaldoDevedor>();
			for (SolicitacaoSaldoDevedor solicAux : solics)
				if (solicAux.getConsignacao().getNrAde() != nrAde)
					removeSolics.add(solicAux);
			for (SolicitacaoSaldoDevedor removeSolicAux : removeSolics)
				solics.remove(removeSolicAux);
		}
		// ---------------------------------------------------------------------------------
		String nrOrdem = GenericUtil.getStringInBody(body, "nrOrdem");
		if (nrOrdem != null && nrOrdem.isEmpty() == false) {
			removeSolics = new ArrayList<SolicitacaoSaldoDevedor>();
			for (SolicitacaoSaldoDevedor solicAux : solics)
				if (solicAux.getConsignacao().getServidorConsig().getPesfis().getNrOrdem().equals(nrOrdem) == false)
					removeSolics.add(solicAux);
			for (SolicitacaoSaldoDevedor removeSolicAux : removeSolics)
				solics.remove(removeSolicAux);
		}
		// ---------------------------------------------------------------------------------
		String nrCpf = GenericUtil.getStringInBody(body, "nrCpf");
		if (nrCpf != null && nrCpf.isEmpty() == false) {
			removeSolics = new ArrayList<SolicitacaoSaldoDevedor>();
			for (SolicitacaoSaldoDevedor solicAux : solics)
				if (solicAux.getConsignacao().getServidorConsig().getPesfis().getNrCpf().equals(nrCpf) == false)
					removeSolics.add(solicAux);
			for (SolicitacaoSaldoDevedor removeSolicAux : removeSolics)
				solics.remove(removeSolicAux);
		}
		// ---------------------------------------------------------------------------------
		String tipoSolic = GenericUtil.getStringInBody(body, "tipoSolic");
		if (tipoSolic != null && tipoSolic.isEmpty() == false && tipoSolic.equals("todas") == false) {
			String consulta = TipoSolicSaldoDevedor.CONSULTA.getTipoSolicitacao();
			String liquidacao = TipoSolicSaldoDevedor.LIQUIDACAO.getTipoSolicitacao();
			if (tipoSolic.equals(consulta)) {
				removeSolics = new ArrayList<SolicitacaoSaldoDevedor>();
				for (SolicitacaoSaldoDevedor solicAux : solics)
					if (solicAux.getTipoSolicitacao().equals(consulta) == false)
						removeSolics.add(solicAux);
				for (SolicitacaoSaldoDevedor removeSolicAux : removeSolics)
					solics.remove(removeSolicAux);
			}
			if (tipoSolic.equals(liquidacao)) {
				removeSolics = new ArrayList<SolicitacaoSaldoDevedor>();
				for (SolicitacaoSaldoDevedor solicAux : solics)
					if (solicAux.getTipoSolicitacao().equals(liquidacao) == false)
						removeSolics.add(solicAux);
				for (SolicitacaoSaldoDevedor removeSolicAux : removeSolics)
					solics.remove(removeSolicAux);
			}
		}
		// ---------------------------------------------------------------------------------
		String situacaoSolic = GenericUtil.getStringInBody(body, "situacaoSolic");
		if (situacaoSolic != null && situacaoSolic.isEmpty() == false && situacaoSolic.equals("todas") == false) {
			removeSolics = new ArrayList<SolicitacaoSaldoDevedor>();
			for (SolicitacaoSaldoDevedor solicAux : solics)
				if (solicAux.getStAtendida() != 0L)
					removeSolics.add(solicAux);
			for (SolicitacaoSaldoDevedor removeSolicAux : removeSolics)
				solics.remove(removeSolicAux);
		}
		// ---------------------------------------------------------------------------------

		return solics;
	}

	@Override
	public String getErrorInSendSaldoDevedor(Map<String, Object> body, MultipartFile multipartFile1,
			MultipartFile multipartFile2, MultipartFile multipartFile3) {
		String linkBoleto = GenericUtil.getStringInBody(body, "linkBoleto");
		if (linkBoleto == null || linkBoleto.isEmpty()) {
			String linkObrigatorio = parametroSistemaService.getVlParamSistemaAndChangeParamIfNull(14, "0");
			if (linkObrigatorio.equals("1"))
				return "sendSaldoDevedor.erro.linkObrigatorio.vazio";
		}
		if (multipartFile3.getOriginalFilename().isEmpty()) {
			if (GenericUtil.getStringInBody(body, "detalhesObrigatorio").equals("1"))
				return "sendSaldoDevedor.erro.uploadDetalhes.noFile";
		}
		if (multipartFile1.getOriginalFilename().isEmpty()) {
			String fileObrigatorio = parametroSistemaService.getVlParamSistemaAndChangeParamIfNull(11, "1");
			if (fileObrigatorio.equals("1"))
				return "sendSaldoDevedor.erro.uploadDemonstrativo.noFile";
		}
		if (multipartFile2.getOriginalFilename().isEmpty()) {
			String fileObrigatorio = parametroSistemaService.getVlParamSistemaAndChangeParamIfNull(10, "1");
			if (fileObrigatorio.equals("1"))
				return "sendSaldoDevedor.erro.uploadBoleto.noFile";
		}
		Double saldoDevedorInformado = GenericUtil.getDoubleInBody(body, "valorSaldoDevedor");
		if (saldoDevedorInformado == null)
			return "sendSaldoDevedor.erro.saldoDevedor.invalido";

		SolicitacaoSaldoDevedor solic = findSolicSaldoDevedorById(Long.parseLong(body.get("idSolic").toString()));
		String LIMSDDEVCADEC = parametroService
				.findByServicoConsigSiglaParametro(solic.getConsignacao().getServicoConsig(), "LIMSDDEVCADEC");
		if (LIMSDDEVCADEC == null)
			LIMSDDEVCADEC = "0";
		String PERMINFSDDEVFORALIM = parametroService
				.findByServicoConsigSiglaParametro(solic.getConsignacao().getServicoConsig(), "PERMINFSDDEVFORALIM");
		if (PERMINFSDDEVFORALIM == null)
			PERMINFSDDEVFORALIM = "0";
		if (LIMSDDEVCADEC.equals("1") && PERMINFSDDEVFORALIM.equals("0")) {
			Double parcelasVincendas = parcelaService.sumParcelas(solic.getConsignacao().getParcelasNaoPagas());
			if (saldoDevedorInformado > parcelasVincendas)
				return "sendSaldoDevedor.erro.saldoDevedor.maiorQueParcelasVincendas";
		}
		return null;
	}

	@Override
	public String sendSaldoDevedor(Map<String, Object> body, Object[] redirect_idFile_file_Demonstrativo,
			Object[] redirect_idFile_file_Boleto, Object[] redirect_idFile_file_Detalhes,
			SolicitacaoSaldoDevedor solic) {
		String detalhes = "";
		if (redirect_idFile_file_Demonstrativo[1] != null)
			solic.setIdFileDemonstrativoCalculo((Long) redirect_idFile_file_Demonstrativo[1]);
		if (redirect_idFile_file_Boleto[1] != null)
			solic.setIdFileBoleto((Long) redirect_idFile_file_Boleto[1]);
		if (redirect_idFile_file_Detalhes[1] != null) {
			Long idDetalhes = (Long) redirect_idFile_file_Detalhes[1];
			detalhes = ". O arquivo com os detalhes do cálculo está nos anexos do contrato com o identificador "
					+ idDetalhes;
			solic.setIdFileDetalhesCalculo(idDetalhes);
		}
		solic.setLinkBoleto(GenericUtil.getStringInBody(body, "linkBoleto"));
		String cnpj = GenericUtil.getStringInBody(body, "cnpj");
		cnpj = cnpj.replaceAll("\\.", "");
		cnpj = cnpj.replace("-", "");
		cnpj = cnpj.replace("/", "");
		solic.setNrCnpjDeposito(cnpj);
		solic.setNrBancoDeposito(GenericUtil.getStringInBody(body, "banco"));
		solic.setVlSaldoDevedor(new BigDecimal(GenericUtil.getDoubleInBody(body, "valorSaldoDevedor")));
		solic.setNrAgenciaDeposito(GenericUtil.getStringInBody(body, "codAgencia"));
		solic.setNrContaDeposito(GenericUtil.getStringInBody(body, "codConta"));
		solic.setNmFavorecidoDeposito(GenericUtil.getStringInBody(body, "nomeFavorecido"));
		solic.setObservacaoDeposito(GenericUtil.getStringInBody(body, "observacao"));
		String enviarEmail = parametroSistemaService.getVlParamSistemaAndChangeParamIfNull(12, "1");
		Consignacao consig = solic.getConsignacao();
		String error = null;
		if (enviarEmail.equals("1")) {
			EmailHelper helper = new EmailHelper();
			error = helper.sendSaldoDevedorAndReturnError(solic, usuarioService.emailServidorByConsig(consig),
					redirect_idFile_file_Demonstrativo, redirect_idFile_file_Boleto, redirect_idFile_file_Detalhes);
		}
		String ip;
		try {
			ip = usuarioLogado.obterIP();
		} catch (Exception e) {
			ip = "";
			e.printStackTrace();
		}
		List<HistoricoConsignacao> historicos = consig.getHistoricoConsignacaos();
		String status = Long.toString(consig.getStatusConsignacao().getId());
		historicos.add(new HistoricoConsignacao(
				"INFORMAÇÃO DO SALDO DEVEDOR: R$ "
						+ GenericUtil.printNumberTwoDigits(body.get("valorSaldoDevedor").toString()) + detalhes,
				new Date(), usuarioLogado.getCPF(), ip, consig, new TipoHistoricoConsig(16), "ID_STATUS_CONSIGNACAO",
				status, status));
		consig.setHistoricoConsignacaos(historicos);
		consignacaoRepository.save(consig);
		solic.setStAtendida(1);
		solicSaldoDevedorRepo.save(solic);
		verificarSeDesbloqueiaConsignataria(solic.getConsignacao().getEntidadeConsig());
		return error;
	}

	private void verificarSeDesbloqueiaConsignataria(EntidadeConsig consignataria) {
		String desbloqueioAutomatico = parametroSistemaService.getVlParamSistemaAndChangeParamIfNull(20, "1");
		if (desbloqueioAutomatico.equals("0"))
			return;
		List<SolicitacaoSaldoDevedor> solicsNaoAtendidas = solicSaldoDevedorRepo.findByStAtendida(0);
		for (SolicitacaoSaldoDevedor solicNaoAtendida : solicsNaoAtendidas) {
			if (solicNaoAtendida.getConsignacao().getEntidadeConsig().getId() == consignataria.getId())
				return;
		}
		if (consignataria.getStEntidade().equals(1L))
			return;
		if (isBloqueioAutomatico(consignataria) == false)
			return;
		consignataria.setStEntidade(1L);
		entidadeConsigRepo.save(consignataria);

		// ------------------------------------------------------------------------
		TipoHistoricoEc tipoHistoricoEc = TipoHistoricoEc.DESBLOQUEIO_AUTOMATICO;
		HistoricoEc historicoEc = new HistoricoEc(new Date(), null, tipoHistoricoEc, consignataria, null,
				tipoHistoricoEc.toString());
		historicoEcRepository.save(historicoEc);
	}

	private boolean isBloqueioAutomatico(EntidadeConsig consignataria) {
		List<HistoricoEc> historicosEc = historicoEcRepository
				.findByTpHistoricoOrTpHistoricoAndEntidadeConsigOrderByIdDesc(
						TipoHistoricoEc.BLOQUEIO_AUTOMATICO.toString(), TipoHistoricoEc.BLOQUEIO_MANUAL.toString(),
						consignataria);
		if (historicosEc == null || historicosEc.isEmpty())
			return false;
		if (historicosEc.get(0).getTpHistorico().equals(TipoHistoricoEc.BLOQUEIO_AUTOMATICO.toString()))
			return true;
		return false;
	}

	/**
	 * @TODO pegar todos os arquivos relacionados à Consignação
	 * @author simaoags
	 */
	@Override
	public List<CargaArquivo> getAllFilesRelatedToConsignacao(Consignacao consignacao) {

		List<CargaArquivo> arquivos = cargaService.findByIdTipoCargaAndTpCargaAndAtivoEquals(consignacao.getId(),
				"CONSIGNACAO", 1);
		List<SolicitacaoSaldoDevedor> solicitacoesSaldoDevedor = solicSaldoDevedorRepo.findByConsignacao(consignacao);
		for (SolicitacaoSaldoDevedor solic : solicitacoesSaldoDevedor) {
			cargaService.addFileToListById(arquivos, solic.getIdFileDemonstrativoCalculo());
			cargaService.addFileToListById(arquivos, solic.getIdFileBoleto());
			cargaService.addFileToListById(arquivos, solic.getIdFileDetalhesCalculo());
		}
		return arquivos;
	}

	@Override
	public void suspenderConsignacao(Consignacao consignacao, File file, String motivo, String observacao,
			String nameOfFile) {
		EmailHelper helper = new EmailHelper();
		helper.sendSuspensaoConsignacao(consignacao.getNrAde(), usuarioService.emailServidorByConsig(consignacao), file,
				motivo, observacao, nameOfFile);
		String emailConsignataria = entidadeConsigService.findById(getConsignatariaByConsignacao(consignacao).getId())
				.getDsEmailEc();
		helper.sendSuspensaoConsignacao(consignacao.getNrAde(), emailConsignataria, file, motivo, observacao,
				nameOfFile);
		suspenderConsig(consignacao.getId(), motivo, observacao);

	}

	/**
	 * Funcao que recebe os valores para validacao dos itens das configuracoes
	 * avancadas para validacao das informações da consignacao.
	 * 
	 * Parametros: 1. prazonaocadastrado => permite utilizar prazo não cadastrado no
	 * servico: DEFAULT NAO. Se SIM o campo numero de prestacoes restante e alterado
	 * de lista para valor numerico, permitindo inserir qualquer valor numerico
	 * maior que zero;
	 * 
	 * @param list<ConfAvancada>
	 * @return boolean
	 */

	public boolean validarConfiguracoesAvancadas(Map<String, Object> body, List<String> detailedErrors,
			List<String> parametros, ConfigAvancada configuracao) {

		boolean retorno = true;

		parametros = body.entrySet().stream().filter(x -> "on".equals(x.getValue())).map(x -> x.getKey())
				.collect(Collectors.toList());

		configuracao.setParametros(parametros);

		if (body.get("nrCarencia") != null && StringUtils.isNotBlank(body.get("nrCarencia").toString())) {
			configuracao.setNrCarencia(Long.parseLong(body.get("nrCarencia").toString()));
		} else {
			detailedErrors.add(msg.get("alteracao.consig.carencia"));
			retorno = false;
		}

		if (body.get("vlPrestacao") != null && StringUtils.isNotBlank(body.get("vlPrestacao").toString())) {
			configuracao.setVlPrestacao(new BigDecimal(body.get("vlPrestacao").toString()));
		} else {
			detailedErrors.add(msg.get("alteracao.consig.valor.prestacao"));
			retorno = false;
		}

		if (body.get("nrPrestacoes") != null && StringUtils.isNotBlank(body.get("nrPrestacoes").toString())) {
			configuracao.setNrPrestacoes(Long.parseLong(body.get("nrPrestacoes").toString()));
		} else {
			detailedErrors.add(msg.get("alteracao.consig.numero.prestacao"));
			retorno = false;
		}

		if (body.get("nrparcpagas") != null && StringUtils.isNotBlank(body.get("nrparcpagas").toString())) {
			configuracao.setNrPrestResestantes(Long.parseLong(body.get("nrparcpagas").toString()));
		} else {
			detailedErrors.add(msg.get("alteracao.consig.numero.parcelas.pagas"));
			retorno = false;
		}

		if (body.get("dsIdentificador") != null && StringUtils.isNotBlank(body.get("dsIdentificador").toString())) {
			configuracao.setDsIdentificador(body.get("dsIdentificador").toString());
		} else {
			detailedErrors.add(msg.get("alteracao.consig.descricao.identificador"));
			retorno = false;
		}

		if (parametros.contains("alteracaoconvenio")) {
			// Servidor
			if (body.get("matInput") != null && StringUtils.isNotBlank(body.get("matInput").toString())) {
				configuracao.setMatricula(body.get("matInput").toString());
				configuracao.setServidorConsig(servidorConsigService.findByMatricula(body.get("matInput").toString()));
			} else {
				detailedErrors.add(msg.get("alteracao.consig.matricula"));
				retorno = false;
			}
	
			// Entidade Consignataria
			if (body.get("consigSelect") != null && StringUtils.isNotBlank(body.get("consigSelect").toString())) {
				configuracao.setEntidadeConsig(
						entidadeConsigService.findById(Long.parseLong(body.get("consigSelect").toString())));
			} else {
				detailedErrors.add(msg.get("alteracao.consig.entidade.consignataria"));
				retorno = false;
			}
	
			// Servico
			if (body.get("serviceSelect") != null && StringUtils.isNotBlank(body.get("serviceSelect").toString())) {
				configuracao.setServico(servicoService.findById(Long.parseLong(body.get("serviceSelect").toString())));
			} else {
				detailedErrors.add(msg.get("alteracao.consig.servico"));
				retorno = false;
			}
	
			// ServicoConsig
			if (configuracao.getEntidadeConsig() != null && configuracao.getServico() != null) {
				configuracao.setServicoConsig(servicoConsigService.findByServicoAndEntidadeConsig(configuracao.getServico(),
						configuracao.getEntidadeConsig()));
			} else {
				detailedErrors.add(msg.get("alteracao.consig.servico.consignataria"));
				retorno = false;
			}
		}
		
		return retorno;
	}

	@Override
	public boolean confirmaAlteracaoAvancada(List<String> detailedErrors, ConfigAvancada configuracao) {
		
		Consignacao consignacao = configuracao.getConsignacao();
		Iterator<String> paramsIterator = configuracao.getParametros().iterator();
		boolean validaMargem = false;
		boolean validaJuros = false;

		while (paramsIterator.hasNext()) {
			switch (paramsIterator.toString()) {
			case "prazonaocadastrado":
				if (configuracao.getNrPrestResestantes() < 0) {
					detailedErrors.add(msg.get("alteracao.consig.nrparcelasrestantes.menorquezero"));
					return false;
				} else {
					ParcelaUtil.qtdParcelasConsig(consignacao, configuracao);
				}
					
				if (consignacao.getParcelaConsignacaos().size() > 0) {
					consignacao.setStatusConsignacao(new StatusConsignacao(8));
				} else {
					consignacao.setStatusConsignacao(new StatusConsignacao(6));
				}
				
				break;
			case "alteracaoconvenio":
				consignacao.setServidorConsig(configuracao.getServidorConsig());
				consignacao.setServicoConsig(configuracao.getServicoConsig());
				break;
			case "afetarmargem":
				//TODO:Tirar duvidas com a DIRAD sobre o item.
				break;
			case "validarmargem":
				validaMargem = true;
				break;
			case "validarjuros":
				validaJuros = true;
				break;
			case "alterarlimite":
				if (consignacao.getParcelasPagas().size() > configuracao.getNrPrestacoes()) {
					detailedErrors.add(msg.get("alteracao.consig.nrprestacaoes.menor.prestacoespagas"));
					return false;
				} else {
					ParcelaUtil.qtdParcelasConsig(consignacao, configuracao);
				}
					
				if (consignacao.getParcelaConsignacaos().size() > 0) {
					consignacao.setStatusConsignacao(new StatusConsignacao(8));
				} else {
					consignacao.setStatusConsignacao(new StatusConsignacao(6));
				}
				break;
			case "calcularprazo":
				//TODO:Tirar duvidas com a DIRAD sobre o item.
				break;
			case "manterdiferenca":

				break;
			case "incluirocorrencia":

				break;
			case "integrafolha":

				break;
			case "validalimite":

				break;
			case "incidemargem":

				break;
			case "difvalmargem":

				break;
			case "novasituacao":

				break;
			default:
				break;
			}
		}

		return true;

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
