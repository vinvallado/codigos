package br.mil.fab.consigext.controller.consignacao;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.mil.fab.consigext.config.Message;
import br.mil.fab.consigext.config.SambaProperties;
import br.mil.fab.consigext.dto.ConfigAvancada;
import br.mil.fab.consigext.entity.CaixaPagamento;
import br.mil.fab.consigext.entity.CargaArquivo;
import br.mil.fab.consigext.entity.Consignacao;
import br.mil.fab.consigext.entity.HistoricoConsignacao;
import br.mil.fab.consigext.entity.ParcelaConsignacao;
import br.mil.fab.consigext.entity.PesfisComgep;
import br.mil.fab.consigext.entity.Servico;
import br.mil.fab.consigext.entity.ServicoConsig;
import br.mil.fab.consigext.entity.ServidorConsig;
import br.mil.fab.consigext.entity.SolicitacaoSaldoDevedor;
import br.mil.fab.consigext.enums.Restriction;
import br.mil.fab.consigext.enums.StatusAcesso;
import br.mil.fab.consigext.enums.TipoCarga;
import br.mil.fab.consigext.helper.EmailHelper;
import br.mil.fab.consigext.repository.ConsignacaoRepository;
import br.mil.fab.consigext.repository.EnderecoPessoaFisicaRespository;
import br.mil.fab.consigext.repository.HistoricoConsignacaoRepository;
import br.mil.fab.consigext.repository.ParcelaConsignacaoRepository;
import br.mil.fab.consigext.repository.PerfisComgepRepository;
import br.mil.fab.consigext.repository.ServidorConsigRepository;
import br.mil.fab.consigext.repository.SolicSaldoDevedorRepository;
import br.mil.fab.consigext.repository.UsuarioExternoRepository;
import br.mil.fab.consigext.repository.UsuarioRepository;
import br.mil.fab.consigext.service.CaixaPagamento.CaixaPagamentoService;
import br.mil.fab.consigext.service.ParametroSistema.ParametroSistemaService;
import br.mil.fab.consigext.service.carga.CargaArquivoService;
import br.mil.fab.consigext.service.margem.ConsignacaoService;
import br.mil.fab.consigext.service.margem.EntidadeConsigService;
import br.mil.fab.consigext.service.margem.OrganizacaoService;
import br.mil.fab.consigext.service.margem.ParcelaService;
import br.mil.fab.consigext.service.margem.ServicoConsigService;
import br.mil.fab.consigext.service.margem.ServicoService;

import br.mil.fab.consigext.service.parametro.ParametroService;
import br.mil.fab.consigext.service.usuario.UsuarioService;

import br.mil.fab.consigext.service.margem.ServidorConsigService;

import br.mil.fab.consigext.service.util.ComboUtilService;
import br.mil.fab.consigext.service.util.UsuarioUtilService;
import br.mil.fab.consigext.util.GenericUtil;
import br.mil.fab.consigext.util.PasswordUtil;
import br.mil.fab.consigext.util.SambaUtil;
import br.mil.fab.consigext.util.UsuarioLogado;

@Controller
@RequestMapping("/consignacao")
public class ConsignacaoController {

	@Autowired
	UsuarioUtilService usrUtilService;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	Message msg;

	@Autowired
	ServicoService servicoService;

	@Autowired
	ParametroService parametroService;

	@Autowired
	UsuarioLogado usuarioLogado;

	@Autowired
	ComboUtilService comboUtilService;

	@Autowired
	ConsignacaoService consignacaoService;

	@Autowired
	EntidadeConsigService consignatariaService;

	@Autowired
	SolicSaldoDevedorRepository solicSaldoDevedorRepo;

	@Autowired
	ParcelaService parcelaService;

	@Autowired
	ConsignacaoRepository consigRepo;

	@Autowired
	CaixaPagamentoService caixaPagamentoService;

	@Autowired
	PerfisComgepRepository pesfisRepo;

	@Autowired
	ParcelaConsignacaoRepository parcelaRepo;

	@Autowired
	ServidorConsigRepository servidorRepo;

	@Autowired
	ServidorConsigService servidorConsigService;

	@Autowired
	EntidadeConsigService entidadeConsigService;

	@Autowired
	SambaUtil samba;

	@Autowired
	SambaProperties sambaProp;

	@Autowired
	OrganizacaoService organizacaoService;

	@Autowired
	EnderecoPessoaFisicaRespository enderecoPessoaFisicaRespo;

	@Autowired
	UsuarioExternoRepository usuarioExternoRepo;

	@Autowired
	UsuarioRepository usuarioRepo;

	@Autowired
	ParametroSistemaService parametroSistemaService;

	@Autowired
	HistoricoConsignacaoRepository historicoConsigRepo;

	@Autowired
	HttpSession session;

	@Autowired
	CargaArquivoService cargaService;

	@Autowired
	ServicoConsigService servicoConsigService;

	@RequestMapping(value = "/getMilitar", method = RequestMethod.GET)
	public @ResponseBody String getUsuarioCombo(Model model, @RequestParam("nrOrdem") String nrOrdem) {
		ServidorConsig serv = servidorConsigService.findByMatricula(nrOrdem);
		if (serv != null)
			return serv.getPesfis().getNmPessoa();
		else
			return "";
	}

	@RequestMapping("/listar")
	public String listarConsigServidor(HttpSession session, Model model,
			@RequestParam(value = "idServidor", required = false) String idServidor, RedirectAttributes redirectAttrs)
			throws Exception {
		if (usuarioLogado.isServidor()) {
			ServidorConsig servidor = (ServidorConsig) session.getAttribute("servidor");

			model.addAttribute("servidor", servidor);
			model.addAttribute("consignacoes", consigRepo.findByServidor(servidor.getId()));
		} else {
			if (idServidor.equals(null)) {
				throw new Exception(
						"Funcionalidade habilitada apenas para Servidor. Entidades Consignatárias e Gestores deverão passar o parâmetro do ID do Servidor.");
			}

			ServidorConsig servidor = servidorRepo.findById(Long.parseLong(idServidor));
			model.addAttribute("servidor", servidor);

			if (usuarioLogado.isConsig()) {
				model.addAttribute("consignacoes",
						servidor.getConsignacoesEc(usuarioLogado.getUsuarioCorrente().getEntidadeConsig()));
			} else {
				model.addAttribute("consignacoes", servidor.getConsignacaos());
			}
		}

		return "consignacao/listar";
	}

	@RequestMapping("/escolherservidor")
	public String solicmargem(Model model, RedirectAttributes redirectAttrs) {
		// PesfisComgep servidor = usrUtilService.getFisicaByCpf("12610140738");
		// servidor = null;
		StatusAcesso acesso = usrUtilService.hasAccess(Restriction.GESTOR_OU_QUALQUER_ENTIDADE_CONSIG, null);
		if (acesso != StatusAcesso.PERMITIDO) {
			List<String> errors = new ArrayList<>();
			errors.add(msg.get(acesso.getStatusAcesso()));
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return "redirect:/index/";
		}
		return "consignacao/escolherservidor";
	}

	@RequestMapping("/exibirSuspenderReativarConsignacao/{idConsignacao}")
	public String autorizarSuspensaoReativacao(Model model, @PathVariable("idConsignacao") long idConsignacao,
			RedirectAttributes redirectAttrs) {
		// PesfisComgep servidor = usrUtilService.getFisicaByCpf("12610140738");
		// servidor = null;
		Consignacao consignacao = consignacaoService.getByIdConsignacao(idConsignacao);
		long idConsignataria = consignacaoService.getConsignatariaByConsignacao(consignacao).getId();
		String permissao = (consignacao.getStatusConsignacao().getId() == 8) ? "SUSPENDER CONSIGNAÇÃO"
				: "REATIVAR CONSIGNAÇÃO";
		StatusAcesso acesso = usrUtilService.hasAccess(permissao, Restriction.GESTOR_OU_ENTIDADE_CONSIG_CORRENTE,
				idConsignataria);
		if (acesso != StatusAcesso.PERMITIDO) {
			List<String> errors = new ArrayList<>();
			errors.add(msg.get(acesso.getStatusAcesso()));
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return "redirect:/consignacao/exibir/" + consignacao.getId();
		}
		if (usuarioLogado.isConsig())
			model.addAttribute("idConsignataria", idConsignataria);
		model.addAttribute("txtObs", "");
		model.addAttribute("consignacao", consignacao);
		model.addAttribute("motivos", comboUtilService.getMotivosSuspenderReativarConsignacao());

		return "consignacao/suspenderReativarconsignacao";
	}

	@RequestMapping("/liquidarconsignacao/{idConsignacao}")
	public String liquidarconsignacao(Model model, @PathVariable("idConsignacao") long idConsignacao,
			RedirectAttributes redirectAttrs) {

		Consignacao consignacao = consignacaoService.getByIdConsignacao(idConsignacao);
		long idConsignataria = consignacaoService.getConsignatariaByConsignacao(consignacao).getId();
		String permissao = (consignacao.getStatusConsignacao().getId() != 15) ? "LIQUIDAR CONTRATO"
				: "DESLIQUIDAR CONTRATO";
		StatusAcesso acesso = usrUtilService.hasAccess(permissao, Restriction.GESTOR_OU_ENTIDADE_CONSIG_CORRENTE,
				idConsignataria);
		if (acesso != StatusAcesso.PERMITIDO) {
			List<String> errors = new ArrayList<String>();
			errors.add(msg.get(acesso.getStatusAcesso()));
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return "redirect:/consignacao/exibir/" + consignacao.getId();
		}
		if (usuarioLogado.isConsig())
			model.addAttribute("idConsignataria", idConsignataria);
		model.addAttribute("periodocritico", consignacaoService.periodoCritico());
		model.addAttribute("txtObs", "");
		model.addAttribute("consignacao", consignacao);
		model.addAttribute("motivos", comboUtilService.getMotivosSuspenderReativarConsignacao());

		return "consignacao/liquidarconsignacao";
	}

	@RequestMapping(value = "/filterServidor", method = RequestMethod.POST)
	public ModelAndView filterServidorEC(ModelMap model, @RequestParam(value = "ade", required = false) String nrAde,
			@RequestParam(value = "matricula", required = false) String matricula,
			@RequestParam(value = "cpf", required = false) String cpf,
			@RequestParam(value = "idHash", required = false) String idHash, RedirectAttributes redirectAttrs)
			throws Exception {

		PesfisComgep pesfis = null;
		ServidorConsig servidor = null;

		if (StringUtils.isNotBlank(nrAde)) {
			Consignacao consig = consigRepo.findByNrAde(Long.valueOf(nrAde));

			if (consig == null) {
				List<String> errors = new ArrayList<>();
				errors.add("Nº ADE não encontrada.");
				redirectAttrs.addFlashAttribute("detailedErrors", errors);
				return new ModelAndView("redirect:/consignacao/escolherservidor/");
			}

			return new ModelAndView("redirect:/consignacao/exibir/" + consig.getId());
		} else if (StringUtils.isNotBlank(matricula)) {
			pesfis = pesfisRepo.findByNrOrdem(matricula);
			servidor = servidorRepo.findByPesfis(pesfis);
		} else if (StringUtils.isNotBlank(cpf)) {
			pesfis = pesfisRepo.findByNrCpf(cpf);
			servidor = servidorRepo.findByPesfis(pesfis);
		} else {
			List<String> errors = new ArrayList<>();
			errors.add("ADE, CPF ou Matrícula devem ser preenchidos");
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return new ModelAndView("redirect:/consignacao/escolherservidor/");
		}

		if (servidor == null) {
			List<String> errors = new ArrayList<>();
			errors.add("Servidor não encontrado.");
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return new ModelAndView("redirect:/consignacao/escolherservidor/");
		}

		if (usuarioLogado.isConsig()) {
			if (StringUtils.isBlank(idHash)) {
				List<String> errors = new ArrayList<>();
				errors.add("Digite a senha.");
				redirectAttrs.addFlashAttribute("detailedErrors", errors);
				return new ModelAndView("redirect:/consignacao/escolherservidor/");
			}
			
			if (!usuarioRepo.findByNrCpf(servidor.getPesfis().getNrCpf()).getPasswordSha()
					.equals(PasswordUtil.sha1x64(idHash))) {
				List<String> errors = new ArrayList<>();
				errors.add("A senha digitada não confere.");
				redirectAttrs.addFlashAttribute("detailedErrors", errors);
				return new ModelAndView("redirect:/consignacao/escolherservidor/");
			}
		}
		return new ModelAndView("redirect:/consignacao/listar?idServidor=" + servidor.getId(), model);

	}

	@RequestMapping("/exibir/{idConsignacao}")
	public String exibir(Model model, @PathVariable("idConsignacao") long idConsignacao,
			RedirectAttributes redirectAttrs) {

		Consignacao consignacao = consignacaoService.getByIdConsignacao(idConsignacao);
		StatusAcesso acesso = usrUtilService.isBlockedOrExcluded();
		if (acesso != StatusAcesso.PERMITIDO) {
			List<String> errors = new ArrayList<>();
			errors.add(msg.get(acesso.getStatusAcesso()));
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return "redirect:/index/";
		}
		if (usuarioLogado.isConsig()) {
			Long idEntidadeConsignacao = consignacao.getEntidadeConsig().getId();
			Long idEntidadeUsuario = usuarioLogado.getUsuarioCorrente().getEntidadeConsig().getId();
			model.addAttribute("idConsignataria", idEntidadeConsignacao);
			if (idEntidadeConsignacao != idEntidadeUsuario) {
				List<String> errors = new ArrayList<>();
				errors.add("O usuário logado não tem acesso a esta consignação");
				redirectAttrs.addFlashAttribute("detailedErrors", errors);
				return "redirect:/consignacao/escolherservidor/";
			}
		} else if (usuarioLogado.isServidor()) {
			ServidorConsig servidor = (ServidorConsig) session.getAttribute("servidor");
			if (consignacao.getServidorConsig().getId() != servidor.getId()) {
				List<String> errors = new ArrayList<>();
				errors.add("O usuário logado não tem acesso a esta consignação");
				redirectAttrs.addFlashAttribute("detailedErrors", errors);
				return "redirect:/index/";
			}
		}

		model.addAttribute("consignacao", consignacao);
		model.addAttribute("historicos", consignacaoService.getHistoricos(consignacao));

		model.addAttribute("consignacao", consignacao);
		model.addAttribute("historicos", consignacao.getHistoricoConsignacaos());

		return "consignacao/exibir";
	}

	@RequestMapping(value = "/cancelar", method = RequestMethod.POST)
	public String cancelar(Model model, @RequestParam Map<String, Object> body) {

		Consignacao consig = consignacaoService.cancelarReserva(body.get("idConsig").toString(),
				usuarioLogado.getRole(), model, body);

		return "redirect:/consignacao/exibir/" + consig.getId();
	}

	@RequestMapping(value = "/confirmar", method = RequestMethod.POST)
	public String confirmar(Model model, @RequestParam Map<String, Object> body) {

		Consignacao consig = consignacaoService.confirmarReserva(body.get("idConsig").toString(),
				usuarioLogado.getRole(), model, body);

		return "redirect:/consignacao/exibir/" + consig.getId();
	}

	@RequestMapping(value = "/supenderReativar", method = RequestMethod.POST)
	public String supenderReativar(RedirectAttributes redirectAttrs, Model model,
			@RequestParam Map<String, Object> body, @RequestParam("file") MultipartFile multipartFile) {

		String action = body.get("action").toString();
		String nameOfFile = body.get("fileName").toString();
		long idConsignacao = Long.parseLong(body.get("idConsignacao").toString());
		String motivo = body.get("motivoSelect").toString();
		String observacao = body.get("txtObservacao").toString();
		List<String> errors = new ArrayList<>();
		if (motivo.isEmpty()) {
			errors.add(msg.get("consignacao.motivo.naoselecionado"));
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return "redirect:/consignacao/exibirSuspenderReativarConsignacao/" + idConsignacao;
		}
		String idHash = body.get("idHash").toString();
		if (StringUtils.isBlank(idHash)) {
			errors.add(msg.get("senha.vazia"));
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return "redirect:/consignacao/exibirSuspenderReativarConsignacao/" + idConsignacao;
		}
		PesfisComgep pesfis = pesfisRepo.findByNrCpf(usuarioLogado.getCPF());
		ServidorConsig servidorConsig = servidorRepo.findByPesfis(pesfis);
		if (!usuarioRepo.findByNrCpf(servidorConsig.getPesfis().getNrCpf()).getPasswordSha()
				.equals(PasswordUtil.sha1x64(idHash))) {
			errors.add(msg.get("senha.errada"));
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return "redirect:/consignacao/exibirSuspenderReativarConsignacao/" + idConsignacao;
		}
		if (consignacaoService.passouPagamento() && consignacaoService.isLastMonth(idConsignacao)) {
			errors.add(msg.get("consignacao.reativarSuspender.ultimaParcela.passouPagamento"));
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return "redirect:/consignacao/exibirSuspenderReativarConsignacao/" + idConsignacao;
		}
		if (action.equals("SUSPENDER CONTRATO"))
			return suspender(observacao, nameOfFile, idConsignacao, motivo, redirectAttrs, multipartFile);
		else if (action.equals("REATIVAR CONTRATO"))
			return reativar(observacao, nameOfFile, idConsignacao, motivo, redirectAttrs, multipartFile);
		return null;
	}

	@RequestMapping(value = "/liquidardesliquidar", method = RequestMethod.POST)
	public String liquidarDesliquidar(RedirectAttributes redirectAttrs, Model model,
			@RequestParam Map<String, Object> body, @RequestParam("file") MultipartFile multipartFile) {

		String nameOfFile = body.get("fileName").toString();
		long idConsignacao = Long.parseLong(body.get("idConsignacao").toString());
		String motivo = body.get("motivoSelect").toString();
		String observacao = body.get("txtObservacao").toString();
		List<String> errors = new ArrayList<>();
		if (motivo.isEmpty()) {
			errors.add(msg.get("consignacao.motivo.naoselecionado"));
		}
		if ("".equals(observacao.trim())) {
			errors.add(msg.get("consignacao.observacao.naoselecionado"));
		}

		if (errors.size() > 0) {
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return "redirect:/consignacao/liquidarconsignacao/" + idConsignacao;
		}

		if (body.get("action").toString().equals("LIQUIDAR CONTRATO"))
			return consignacaoService.liquidarContrato(observacao, nameOfFile, idConsignacao, motivo, redirectAttrs,
					multipartFile);
		else {
			return consignacaoService.desliquidarContrato(observacao, nameOfFile, idConsignacao, motivo, redirectAttrs,
					multipartFile, body);
		}
	}

	private String reativar(String observacao, String nameOfFile, long idConsignacao, String motivo,
			RedirectAttributes redirectAttrs, MultipartFile multipartFile) {
		List<String> errors = new ArrayList<>();
		Consignacao consignacao = consignacaoService.getByIdConsignacao(idConsignacao);
		File file = null;
		if (consignacao.getStatusConsignacao().getId() == 18 && usuarioLogado.isGestor() == false) {
			errors.add(msg.get("consignacao.reativar.naoautorizado"));
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return "redirect:/consignacao/exibir/" + idConsignacao;
		}
		if (!multipartFile.getOriginalFilename().isEmpty())
			try {
				file = cargaService.convertMultiPartToFile(multipartFile);
				cargaService.save(TipoCarga.ANEXO, file,
						motivo + " (" + observacao + ") - Reativacao (Anexo: " + nameOfFile + ")");
			} catch (Exception e) {
				e.printStackTrace();
				errors.add(msg.get("upload.erro"));
				redirectAttrs.addFlashAttribute("detailedErrors", errors);
				return "redirect:/consignacao/exibirSuspenderReativarConsignacao/" + idConsignacao;
			}
		if (consignacaoService.isLastMonth(idConsignacao) == false && consignacaoService.passouPagamento()) {
			errors.add(msg.get("consignacao.reativar.passouPagamento"));
			redirectAttrs.addFlashAttribute("detailedSuccess", errors);
		}
		EmailHelper helper = new EmailHelper();
		helper.sendReativacaoConsignacao(consignacao.getNrAde(), usuarioService.emailServidorByConsig(consignacao),
				file, motivo, observacao, nameOfFile);
		String emailConsignataria = entidadeConsigService
				.findById(consignacaoService.getConsignatariaByConsignacao(consignacao).getId()).getDsEmailEc();
		helper.sendReativacaoConsignacao(consignacao.getNrAde(), emailConsignataria, file, motivo, observacao,
				nameOfFile);
		consignacaoService.reativarConsig(idConsignacao, motivo, observacao);
		List<String> detailedSuccess = new ArrayList<String>();
		detailedSuccess.add(msg.get("consignacao.reativar.sucesso"));
		redirectAttrs.addFlashAttribute("detailedSuccess", detailedSuccess);

		return "redirect:/consignacao/exibir/" + idConsignacao;
	}

	private String suspender(String observacao, String nameOfFile, long idConsignacao, String motivo,
			RedirectAttributes redirectAttrs, MultipartFile multipartFile) {
		Consignacao consignacao = consignacaoService.getByIdConsignacao(idConsignacao);
		List<String> errors = new ArrayList<>();
		File file = null;
		if (!multipartFile.getOriginalFilename().isEmpty())
			try {
				file = cargaService.convertMultiPartToFile(multipartFile);
				cargaService.save(TipoCarga.ANEXO, file,
						motivo + " (" + observacao + ") - Suspensao (Anexo: " + nameOfFile + ")");
			} catch (Exception e) {
				e.printStackTrace();
				errors.add(msg.get("upload.erro"));
				redirectAttrs.addFlashAttribute("detailedErrors", errors);
				return "redirect:/consignacao/exibirSuspenderReativarConsignacao/" + idConsignacao;
			}
		if (consignacaoService.isLastMonth(idConsignacao) == false && consignacaoService.passouPagamento()) {
			errors.add(msg.get("consignacao.suspender.passouPagamento"));
			redirectAttrs.addFlashAttribute("detailedSuccess", errors);
		}
		consignacaoService.suspenderConsignacao(consignacao, file, motivo, observacao, nameOfFile);
		List<String> detailedSuccess = new ArrayList<String>();
		detailedSuccess.add(msg.get("consignacao.suspender.sucesso"));
		redirectAttrs.addFlashAttribute("detailedSuccess", detailedSuccess);
		return "redirect:/consignacao/exibir/" + idConsignacao;
	}

	@RequestMapping("/listarParcelas/{idConsignacao}")
	public String listarParcelasParaLiquidar(Model model, @PathVariable("idConsignacao") long idConsignacao,
			RedirectAttributes redirectAttrs) {
		Consignacao consignacao = consignacaoService.getByIdConsignacao(idConsignacao);
		long idConsignataria = consignacao.getEntidadeConsig().getId();
		StatusAcesso acesso = usrUtilService.hasAccess("LIQUIDAR PARCELA",
				Restriction.GESTOR_OU_ENTIDADE_CONSIG_CORRENTE, idConsignataria);
		if (acesso != StatusAcesso.PERMITIDO) {
			List<String> errors = new ArrayList<>();
			errors.add(msg.get(acesso.getStatusAcesso()));
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			if (acesso == StatusAcesso.SERVIDOR_SEM_ACESSO)
				return "redirect:/index/";
			return "redirect:/consignacao/exibir/" + consignacao.getId();
		}
		model.addAttribute("idConsignataria", idConsignataria);
		List<ParcelaConsignacao> parcelas = consignacao.getParcelaConsignacaos();

		model.addAttribute("parcelas", parcelas);
		model.addAttribute("idConsignacao", consignacao.getId());
		model.addAttribute("motivos", comboUtilService.getMotivosSuspenderReativarConsignacao());
		return "consignacao/listarParcelasParaLiquidar";
	}

	@RequestMapping(value = "/liquidarDesliquidarParcelas", method = RequestMethod.POST)
	public String liquidarDesliquidarParcelas(Model model, @RequestParam Map<String, Object> body,
			RedirectAttributes redirectAttrs) {
		long idConsignacao = Long.parseLong(body.get("idConsignacao").toString());
		Consignacao consignacao = consignacaoService.getByIdConsignacao(idConsignacao);
		Long idConsignataria = consignacaoService.getConsignatariaByConsignacao(consignacao).getId();
		List<String> errors = new ArrayList<>();
		StatusAcesso acesso = usrUtilService.hasAccess("LIQUIDAR PARCELA",
				Restriction.GESTOR_OU_ENTIDADE_CONSIG_CORRENTE, idConsignataria);
		if (acesso != StatusAcesso.PERMITIDO) {
			errors.add(msg.get(acesso.getStatusAcesso()));
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return "redirect:/consignacao/listarParcelas/" + consignacao.getId();
		}
		String action = body.get(consignacaoService.getAction(consignacao, body)).toString();
		if (action.equals("desliquidar")) {
			String msgErrorSuccess = consignacaoService.desliquidarParcela(consignacao, body);
			if (msgErrorSuccess.equals("consignacao.desliquidarParcela.sucesso")) {
				List<String> success = new ArrayList<>();
				success.add(msg.get(msgErrorSuccess));
				redirectAttrs.addFlashAttribute("detailedSuccess", success);
			} else {
				errors.add(msg.get(msgErrorSuccess));
				redirectAttrs.addFlashAttribute("detailedErrors", errors);
			}
			return "redirect:/consignacao/listarParcelas/" + consignacao.getId();
		}
		if (action.equals("liquidar") == false)
			return null;
		String msgErrorSuccess = consignacaoService.getErrorInLiquidarParcelas(consignacao, body);
		if (msgErrorSuccess == "consignacao.parcelas.liquidar.sucesso") {
			List<String> success = new ArrayList<>();
			success.add(msg.get(msgErrorSuccess));
			redirectAttrs.addFlashAttribute("detailedSuccess", success);
			consignacaoService.liquidarParcelas(consignacao, body);
		} else {
			errors.add(msg.get(msgErrorSuccess));
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return "redirect:/consignacao/listarParcelas/" + consignacao.getId();
		}
		return "redirect:/consignacao/listarParcelas/" + consignacao.getId();
	}

	// @RequestMapping(value = "/desliquidar", method = RequestMethod.POST)
	// public String desliquidar(Model model, @RequestParam Map<String, Object>
	// body) {
	//
	// Consignacao consig =
	// consignacaoService.desliquidarConsignacao(body.get("idConsig").toString(),
	// usuarioLogado.getRole(), model);
	//
	// return "redirect:/consignacao/exibir/" + consig.getId();
	// }

	@RequestMapping(value = "/renegociar", method = RequestMethod.POST)
	public String renegociar(Model model, @RequestParam Map<String, Object> body) {

		Consignacao consig = consignacaoService.renegociacaoConsignacao(body.get("idConsig").toString(),
				usuarioLogado.getRole(), model);

		return "redirect:/consignacao/exibir/" + consig.getId();
	}

	@RequestMapping(value = "/reimplantar", method = RequestMethod.POST)
	public String reimplantar(Model model, @RequestParam Map<String, Object> body) {

		Consignacao consig = consignacaoService.reimplantarConsignacao(body.get("idConsig").toString(),
				usuarioLogado.getRole(), model);

		return "redirect:/consignacao/exibir/" + consig.getId();
	}

	@RequestMapping("/autorizacaodesconto/{idconsig}")
	public String autorizacaodesconto(Model model, @PathVariable("idconsig") long idConsig) {

		// verificar se a pessoa logada tem acesso para acessar as informações
		// da consignação (ADE)
		model.addAttribute("consignacao", consigRepo.findById(idConsig));
		model.addAttribute("historico",
				historicoConsigRepo.findByIdAndTipoHistoricoConsig_SgTipoHistorico(idConsig, "INCLUSÃO"));
		model.addAttribute("enderecoAtual", enderecoPessoaFisicaRespo.findByStAtualAndNrOrdem("S",
				consigRepo.findById(idConsig).getServidorConsig().getPesfis().getNrOrdem()));

		return "consignacao/autorizacaodesconto";
	}

	@RequestMapping("/anexararquivos/{idconsig}")
	public String anexararquivos(Model model, @PathVariable("idconsig") long idConsig) {
		model.addAttribute("consignacao", consignacaoService.getByIdConsignacao(idConsig));
		model.addAttribute("idconsig", idConsig);
		List<CargaArquivo> arquivos = consignacaoService
				.getAllFilesRelatedToConsignacao(consignacaoService.getByIdConsignacao(idConsig));
		model.addAttribute("arquivos", arquivos);
		if (arquivos.size() == 0) {
			model.addAttribute("noFiles", msg.get("consig.no.file"));
		}
		boolean mostraParaServidorAnexosSaldoDevedor = true;
		String param_ModoDisponibilizacaoParaServidor = parametroSistemaService
				.getVlParamSistemaAndChangeParamIfNull(16, "3");
		String param_ExibeAnexosParaServidor = parametroSistemaService.getVlParamSistemaAndChangeParamIfNull(188, "1");
		if (param_ModoDisponibilizacaoParaServidor.equals("1") || param_ExibeAnexosParaServidor.equals("0"))
			mostraParaServidorAnexosSaldoDevedor = false;
		model.addAttribute("mostraParaServidorAnexosSaldoDevedor", mostraParaServidorAnexosSaldoDevedor);
		return "consignacao/anexararquivos";
	}

	@RequestMapping("/listar/solicitacoes/saldoDevedor/filtro")
	public String filtroListarSolicitacaoSaldoDevedor(Model model, RedirectAttributes redirectAttrs) {
		StatusAcesso acesso = usrUtilService.hasAccess(Restriction.GESTOR_OU_QUALQUER_ENTIDADE_CONSIG, null);
		if (acesso != StatusAcesso.PERMITIDO) {
			List<String> errors = new ArrayList<>();
			errors.add(msg.get(acesso.getStatusAcesso()));
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return "redirect:/navegacao/operacional";
		}
		model.addAttribute("consignataria", usuarioLogado.getUsuarioCorrente().getEntidadeConsig());
		model.addAttribute("consignatarias", consignatariaService.findAll());
		model.addAttribute("servicos", servicoService.getServicos());
		model.addAttribute("organizacoes", organizacaoService.getOrganizacoes());
		return "consignacao/filtroSolicitacoesSaldoDevedor";
	}

	@RequestMapping(value = "/listar/solicitacoes/saldoDevedor", method = RequestMethod.POST)
	public String listarSolicitacoesSaldoDevedor(Model model, @RequestParam Map<String, Object> body,
			RedirectAttributes redirectAttrs) {
		StatusAcesso acesso = usrUtilService.hasAccess(Restriction.GESTOR_OU_QUALQUER_ENTIDADE_CONSIG, null);
		if (acesso != StatusAcesso.PERMITIDO) {
			List<String> errors = new ArrayList<>();
			errors.add(msg.get(acesso.getStatusAcesso()));
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return "redirect:/navegacao/operacional";
		}
		String errorMessage = consignacaoService.getErrorInFiltroSolicSaldoDevedor(body);
		if (errorMessage != null) {
			consignacaoService.addRecoveredParams(redirectAttrs, body);
			List<String> errors = new ArrayList<>();
			errors.add(msg.get(errorMessage));
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return "redirect:/consignacao/listar/solicitacoes/saldoDevedor/filtro";
		}
		model.addAttribute("solicitacoes", consignacaoService.getSolicitacoesSaldoDevedor(body));
		return "consignacao/listarSolicitacoesSaldoDevedor";
	}

	@RequestMapping("/solicitacao/informarSaldo/{idSolicitacao}")
	public String informarSaldoDevedor(Model model, RedirectAttributes redirectAttrs,
			@PathVariable("idSolicitacao") long idSolicitacao) {
		SolicitacaoSaldoDevedor solic = consignacaoService.findSolicSaldoDevedorById(idSolicitacao);
		long idConsig = solic.getConsignacao().getEntidadeConsig().getId();
		StatusAcesso acesso = usrUtilService.hasAccess(Restriction.ENTIDADE_CONSIG_CORRENTE, idConsig);
		if (acesso != StatusAcesso.PERMITIDO) {
			List<String> errors = new ArrayList<>();
			errors.add(msg.get(acesso.getStatusAcesso()));
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return "redirect:/navegacao/operacional";
		}
		model.addAttribute("solic", solic);
		String linkObrigatorio = parametroSistemaService.getVlParamSistemaByIdParametro(14);
		if (linkObrigatorio == null)
			linkObrigatorio = "0";
		model.addAttribute("mostrarLink", linkObrigatorio);
		String LIMSDDEVCADEC = parametroService
				.findByServicoConsigSiglaParametro(solic.getConsignacao().getServicoConsig(), "LIMSDDEVCADEC");
		if (LIMSDDEVCADEC == null)
			LIMSDDEVCADEC = "0";
		String PERMINFSDDEVFORALIM = parametroService
				.findByServicoConsigSiglaParametro(solic.getConsignacao().getServicoConsig(), "PERMINFSDDEVFORALIM");
		if (PERMINFSDDEVFORALIM == null)
			PERMINFSDDEVFORALIM = "0";
		String detalhesObrigatorio = "0";
		if (LIMSDDEVCADEC.equals("1") && PERMINFSDDEVFORALIM.equals("1"))
			detalhesObrigatorio = "1";
		model.addAttribute("detalhesObrigatorio", detalhesObrigatorio);

		String fileObrigatorio = parametroSistemaService.getVlParamSistemaAndChangeParamIfNull(11, "1");
		model.addAttribute("demonstrativoObrigatorio", fileObrigatorio);

		fileObrigatorio = parametroSistemaService.getVlParamSistemaAndChangeParamIfNull(10, "1");
		model.addAttribute("boletoObrigatorio", fileObrigatorio);

		return "consignacao/mostrarSolicitacao";
	}

	@RequestMapping(value = "/sendSaldoDevedor", method = RequestMethod.POST)
	public String sendSaldoDevedor(Model model, @RequestParam Map<String, Object> body,
			RedirectAttributes redirectAttrs, @RequestParam("file1") MultipartFile multipartFile1,
			@RequestParam("file2") MultipartFile multipartFile2, @RequestParam("file3") MultipartFile multipartFile3) {
		long idSolicitacao = Long.parseLong(body.get("idSolic").toString());
		SolicitacaoSaldoDevedor solic = consignacaoService.findSolicSaldoDevedorById(idSolicitacao);
		long idConsig = solic.getConsignacao().getEntidadeConsig().getId();
		StatusAcesso acesso = usrUtilService.hasAccess(Restriction.ENTIDADE_CONSIG_CORRENTE, idConsig);
		if (acesso != StatusAcesso.PERMITIDO) {
			List<String> errors = new ArrayList<>();
			errors.add(msg.get(acesso.getStatusAcesso()));
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return "redirect:/navegacao/operacional";
		}
		String error = consignacaoService.getErrorInSendSaldoDevedor(body, multipartFile1, multipartFile2,
				multipartFile3);
		if (error != null) {
			List<String> errors = new ArrayList<>();
			errors.add(msg.get(error));
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			GenericUtil.addRecoveredAttributes(body, redirectAttrs);
			return "redirect:/consignacao/solicitacao/informarSaldo/" + idSolicitacao;
		}

		String descricao1 = "Arquivo demonstrativo de cálculo do saldo devedor. Nome: ";
		Object[] redirect_idFile_file_1 = consignacaoService.processFile(body, redirectAttrs,
				TipoCarga.DEMONSTRATIVO_SALDO_DEVEDOR, multipartFile1, "sendSaldoDevedor.erro.uploadDemonstrativo",
				descricao1, idSolicitacao);
		if (redirect_idFile_file_1[0] != null) {
			GenericUtil.addRecoveredAttributes(body, redirectAttrs);
			return (String) redirect_idFile_file_1[0];
		}
		String descricao2 = "Boleto Bancário. Nome: ";
		Object[] redirect_idFile_file_2 = consignacaoService.processFile(body, redirectAttrs,
				TipoCarga.BOLETO_SALDO_DEVEDOR, multipartFile2, "sendSaldoDevedor.erro.uploadBoleto", descricao2,
				idSolicitacao);
		if (redirect_idFile_file_2[0] != null) {
			GenericUtil.addRecoveredAttributes(body, redirectAttrs);
			return (String) redirect_idFile_file_2[0];
		}
		String descricao3 = "Boleto Bancário. Nome: ";
		Object[] redirect_idFile_file_3 = consignacaoService.processFile(body, redirectAttrs,
				TipoCarga.DETALHES_SALDO_DEVEDOR, multipartFile3, "sendSaldoDevedor.erro.uploadDetalhes", descricao3,
				idSolicitacao);
		if (redirect_idFile_file_3[0] != null) {
			GenericUtil.addRecoveredAttributes(body, redirectAttrs);
			return (String) redirect_idFile_file_3[0];
		}
		error = consignacaoService.sendSaldoDevedor(body, redirect_idFile_file_1, redirect_idFile_file_2,
				redirect_idFile_file_3, solic);
		if (error != null) {
			GenericUtil.addRecoveredAttributes(body, redirectAttrs);
			List<String> errors = new ArrayList<>();
			errors.add(error);
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return "redirect:/consignacao/solicitacao/informarSaldo/" + idSolicitacao;
		}
		List<String> detailedSuccess = new ArrayList<String>();
		detailedSuccess.add(msg.get("sendSaldoDevedor.sucesso"));
		redirectAttrs.addFlashAttribute("detailedSuccess", detailedSuccess);
		return "redirect:/consignacao/listar/solicitacoes/saldoDevedor/filtro";
	}

	@RequestMapping("/solicitarSaldoDevedor/{idconsignacao}")
	public String solicitarSaldoDevedor(Model model, RedirectAttributes redirectAttrs,
			@PathVariable("idconsignacao") long idconsignacao) {
		StatusAcesso acesso = usrUtilService.isBlockedOrExcluded();
		List<String> errors = new ArrayList<>();
		List<String> success = new ArrayList<>();
		if (acesso != StatusAcesso.PERMITIDO) {
			errors.add(msg.get(acesso.getStatusAcesso()));
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return "redirect:/index/";
		}
		consignacaoService.solicitarSaldoDevedor(idconsignacao);
		success.add(msg.get("solicitacaoSaldoDevedor.solicitacaoServidor.sucesso"));
		redirectAttrs.addFlashAttribute("detailedSuccess", success);
		return "redirect:/consignacao/exibir/" + idconsignacao;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST, params = { "carregar=carregar" })
	public String tratarArquivo(Model model, RedirectAttributes redirectAttrs, @RequestParam Map<String, Object> body,
			@ModelAttribute CargaArquivo cargaArquivo, @RequestParam("file") MultipartFile multipartFile) {

		List<String> detailedErrors = new ArrayList<String>();
		List<String> detailed = new ArrayList<String>();

		String nameOfFile = body.get("fileName").toString();
		File file = null;

		if (body.get("descricao") == null || body.get("descricao").toString().equals("")) {
			detailedErrors.add(msg.get("consig.descricao.nula"));
		}
		if (multipartFile == null) {
			detailedErrors.add(msg.get("consig.no.file.selected"));
		}

		if (detailedErrors.size() == 0) {
			if (!multipartFile.getOriginalFilename().isEmpty())
				try {
					file = cargaService.convertMultiPartToFile(multipartFile);
					cargaService.save(Long.parseLong(body.get("idconsig").toString()), TipoCarga.CONSIGNACAO, file,
							body.get("descricao").toString() + " (Anexo: " + nameOfFile + ")");
					detailed.add(msg.get("consig.file.selected"));
					redirectAttrs.addFlashAttribute("detailed", detailed);
				} catch (Exception e) {
					e.printStackTrace();
					detailedErrors.add(msg.get("upload.erro"));
				}
		}

		List<CargaArquivo> arquivos = cargaService.findByIdTipoCargaAndTpCargaAndAtivoEquals(
				Long.parseLong(body.get("idconsig").toString()), "CONSIGNACAO", 1);
		redirectAttrs.addFlashAttribute("arquivos", arquivos);

		if (detailedErrors.size() > 0) {
			redirectAttrs.addFlashAttribute("detailedErrors", detailedErrors);
		}

		return "redirect:/consignacao/anexararquivos/" + Long.parseLong(body.get("idconsig").toString());
	}

	public String voltarconsig(Model model, @RequestParam Map<String, Object> body,
			@ModelAttribute CargaArquivo cargaArquivo, HttpServletRequest request) {

		Consignacao consignacao = consignacaoService
				.getByIdConsignacao(Long.parseLong(body.get("idconsig").toString()));

		model.addAttribute("consignacao", consignacao);
		model.addAttribute("historicos", consignacao.getHistoricoConsignacaos());
		return "consignacao/exibir";
	}

	@RequestMapping(value = "/download/{id}", method = RequestMethod.GET, produces = "text/plain")
	@ResponseBody
	public byte[] downloadFile(Model uiMOdel, @PathVariable("id") long id) {
		CargaArquivo arquivo = cargaService.find(id);
		byte[] file = cargaService.download(arquivo.getTxLink());
		return file;
	}

	@RequestMapping(value = "/excluirfile", method = RequestMethod.POST)
	@ResponseBody
	public String excluirFile(@RequestParam("idconsig") String idconsig, @RequestParam("idArq") Long idArq) {

		try {
			CargaArquivo arquivo = cargaService.find(idArq);
			arquivo.setAtivo(0);
			cargaService.save(arquivo);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	/*
	 * @RequestMapping(value = "/upload", method = RequestMethod.POST, params=
	 * {"carregar=carregar"}) public String tratarArquivo2(Model model,
	 * RedirectAttributes redirectAttrs, @RequestParam Map<String, Object> body,
	 * 
	 * @ModelAttribute CargaArquivo cargaArquivo, HttpServletRequest request) {
	 * 
	 * List<String> detailedErrors = new ArrayList<String>(); List<String> detailed
	 * = new ArrayList<String>();
	 * 
	 * if (body.get("descricao") == null ||
	 * body.get("descricao").toString().equals("")) {
	 * detailedErrors.add(msg.get("consig.descricao.nula")); } else if
	 * (body.get("file-name") == null ||
	 * body.get("file-name").toString().equals("")) {
	 * detailedErrors.add(msg.get("consig.no.file.selected")); } if
	 * (detailedErrors.size()>0) { List<CargaArquivo> arquivos =
	 * cargaService.findByIdTipoCargaAndTpCargaAndAtivoEquals(Long.parseLong(body.
	 * get("idconsig").toString()), "CONSIGNACAO", 1); if (arquivos.size() > 0) {
	 * redirectAttrs.addFlashAttribute("arquivos",arquivos); } else {
	 * detailedErrors.add(msg.get("consig.no.files")); }
	 * redirectAttrs.addFlashAttribute("detailedErrors",detailedErrors); return
	 * "redirect:/consignacao/anexararquivos/" +
	 * Long.parseLong(body.get("idconsig").toString()); }
	 * 
	 * if (cargaService.upload(detailed, detailedErrors,
	 * body.get("descricao").toString(),
	 * Long.parseLong(body.get("idconsig").toString()), cargaArquivo, request)) {
	 * detailed.add(msg.get("consig.file.selected"));
	 * redirectAttrs.addFlashAttribute("detailed",detailed); } else {
	 * detailedErrors.add(msg.get("consig.falha.upload"));
	 * redirectAttrs.addFlashAttribute("detailedErrors",detailedErrors);
	 * 
	 * }
	 * 
	 * return "redirect:/consignacao/anexararquivos/" +
	 * Long.parseLong(body.get("idconsig").toString()); }
	 */

	@RequestMapping(value = "/novo/servicoConsig/{idConsig}/criar", method = RequestMethod.POST)
	public String criarNovoServicoConsig(Model model, @RequestParam Map<String, Object> body,
			RedirectAttributes redirectAttrs) {
		Long idConsignataria = Long.parseLong(body.get("idConsignataria").toString());
		StatusAcesso acesso = usrUtilService.hasAccess(Restriction.GESTOR, null);
		if (acesso != StatusAcesso.PERMITIDO) {
			List<String> errors = new ArrayList<>();
			errors.add(msg.get(acesso.getStatusAcesso()));
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return "redirect:/consignataria/editar/" + idConsignataria;
		}
		String errorMessage = consignacaoService.getErrorMessageAddServicoConsig(body);
		if (errorMessage != null) {
			List<String> errors = new ArrayList<>();
			errors.add(msg.get(errorMessage));
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			Long serviceSelect = null;
			try {
				serviceSelect = Long.parseLong(body.get("serviceSelect").toString());
			} catch (Exception e) {
			}
			redirectAttrs.addFlashAttribute("serviceRecovered", serviceSelect);
			redirectAttrs.addFlashAttribute("caixaRecovered", body.get("caixaSelect").toString());
			return "redirect:/consignacao/novo/servicoConsig/" + idConsignataria;
		}
		List<String> success = new ArrayList<>();
		success.add(msg.get("consignataria.criarNovoServico.sucesso"));
		redirectAttrs.addFlashAttribute("detailedSuccess", success);
		consignacaoService.addServicoConsig(body);
		return "redirect:/consignataria/listar/servicos-consignataria/" + idConsignataria;
	}

	@RequestMapping("/novo/servicoConsig/{idConsig}")
	public String showNovoServicoConsig(Model model, @PathVariable("idConsig") long idConsignataria,
			RedirectAttributes redirectAttrs) {
		StatusAcesso acesso = usrUtilService.hasAccess(Restriction.GESTOR, null);
		if (acesso != StatusAcesso.PERMITIDO) {
			List<String> errors = new ArrayList<>();
			errors.add(msg.get(acesso.getStatusAcesso()));
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return "redirect:/consignataria/editar/" + idConsignataria;
		}
		model.addAttribute("idConsignataria", idConsignataria);
		model.addAttribute("consignataria", entidadeConsigService.findById(idConsignataria));
		List<Servico> servicos = servicoService.getServicos();
		model.addAttribute("servicos", servicos);
		List<CaixaPagamento> caixas = caixaPagamentoService.getCaixasPagamentos();
		model.addAttribute("caixas", caixas);
		return "consignataria/novoServicoConsig";
	}

	@RequestMapping(value = "/filterUsuServPens", method = RequestMethod.POST)
	public ModelAndView filterServidorEC(ModelMap model,
			@RequestParam(value = "matricula", required = false) String matricula,
			@RequestParam(value = "cpf", required = false) String cpf,
			@RequestParam(value = "idHash", required = false) String idHash, RedirectAttributes redirectAttrs)
			throws Exception {

		PesfisComgep pesfis = null;
		ServidorConsig servidorConsig = null;

		if (StringUtils.isNotBlank(matricula)) {
			pesfis = pesfisRepo.findByNrOrdem(matricula);
			servidorConsig = servidorRepo.findByPesfis(pesfis);
		} else if (StringUtils.isNotBlank(cpf)) {
			pesfis = pesfisRepo.findByNrCpf(cpf);
			servidorConsig = servidorRepo.findByPesfis(pesfis);
		} else {
			List<String> errors = new ArrayList<>();
			errors.add("CPF ou Matrícula devem ser preenchidos");
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return new ModelAndView("redirect:/usuario/escolher/");
		}

		if (servidorConsig == null) {
			List<String> errors = new ArrayList<>();
			errors.add("Servidor não encontrado.");
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return new ModelAndView("redirect:/usuario/escolher/");
		}

		if (usuarioLogado.isConsig()) {
			if (StringUtils.isBlank(idHash)) {
				List<String> errors = new ArrayList<>();
				errors.add("Digite a senha.");
				redirectAttrs.addFlashAttribute("detailedErrors", errors);
				return new ModelAndView("redirect:/usuario/escolher/");
			}

			if (!usuarioRepo.findByNrCpf(servidorConsig.getPesfis().getNrCpf()).getPasswordSha()
					.equals(PasswordUtil.sha1x64(idHash))) {
				List<String> errors = new ArrayList<>();
				errors.add("A senha digitada não confere.");
				redirectAttrs.addFlashAttribute("detailedErrors", errors);
				return new ModelAndView("redirect:/usuario/escolher/");
			}
		}
		return new ModelAndView("redirect:/consignacao/exibirusupens/" + servidorConsig.getId(), model);
	}

	@RequestMapping("/exibirusupens/{idServidorConsig}")
	public String exibirusupens(Model model, @PathVariable("idServidorConsig") long idServidorConsig,
			RedirectAttributes redirectAttrs) {
		ServidorConsig servidorConsig = servidorRepo.findById(idServidorConsig);
		Consignacao consignacao = consignacaoService.getByIdConsignacao(idServidorConsig);

		if (usuarioLogado.isConsig()) {
			Long idEntidadeConsignacao = consignacao.getEntidadeConsig().getId();
			Long idEntidadeUsuario = usuarioLogado.getUsuarioCorrente().getEntidadeConsig().getId();

			if (idEntidadeConsignacao != idEntidadeUsuario) {
				List<String> errors = new ArrayList<>();
				errors.add("O usuário logado não tem acesso a esta consignação");
				redirectAttrs.addFlashAttribute("detailedErrors", errors);
				return "redirect:/consignacao/escolherservidor/";
			}
		} else if (usuarioLogado.isServidor()) {
			ServidorConsig servidor = (ServidorConsig) session.getAttribute("servidor");
			if (consignacao.getServidorConsig().getId() != servidor.getId()) {
				List<String> errors = new ArrayList<>();
				errors.add("O usuário logado não tem acesso a esta consignação");
				redirectAttrs.addFlashAttribute("detailedErrors", errors);
				return "redirect:/index/";
			}
		}
		model.addAttribute("servidorConsig", servidorConsig);
		model.addAttribute("consignacao", consignacao);
		List<HistoricoConsignacao> historico = null;
		if (consignacao != null)
			historico = consignacao.getHistoricoConsignacaos();
		model.addAttribute("historicos", historico);
		return "consignacao/exibirusupens";
	}

	@RequestMapping("/alterar/{idConsignacao}")
	public String alterarModel(Model model, @PathVariable("idConsignacao") long idConsignacao,
			RedirectAttributes redirectAttrs) {

		Consignacao consignacao = consignacaoService.getByIdConsignacao(idConsignacao);
		long idConsignataria = consignacaoService.getConsignatariaByConsignacao(consignacao).getId();

		model.addAttribute("idConsignataria", idConsignataria);
		model.addAttribute("consignacao", consignacao);
		model.addAttribute("maxParcela", consignacaoService.getParcelaUtil()
				.getParcelaMaxima(consignacao.getServidorConsig(), consignacao.getServicoConsig()));
		model.addAttribute("presRestantes", consignacao.getParcelasNaoPagas().size());
		model.addAttribute("numPrest", consignacao.getNrPrestacoes());
		model.addAttribute("dadosConsig", consignacaoService.dadosParaAlteracao(consignacao));

		// model.addAttribute("nrOrdemMil",
		// consignacao.getServidorConsig().getPesfis().getNrOrdem());
		// model.addAttribute("nomeMilitar",
		// consignacao.getServidorConsig().getPesfis().getNmGuerra());

		model.addAttribute("consignatarias", comboUtilService.findEntidadesConsig());
		model.addAttribute("servicos",
				comboUtilService.findServicoEntidade(consignacao.getServicoConsig().getEntidadeConsig()));
		model.addAttribute("idConsig", consignacao.getServicoConsig().getEntidadeConsig().getId());
		model.addAttribute("idServ", consignacao.getServicoConsig().getId());

		model.addAttribute("motivos", comboUtilService.findMotivos());

		return "consignacao/alterarconsignacao";
	}

	@RequestMapping("/confirmaralteracao")
	public String confirmaralteracao(Model model, @RequestParam Map<String, Object> body,
			RedirectAttributes redirectAttrs) {

		List<String> detailedErrors = new ArrayList<String>();
		List<String> parametros = new ArrayList<String>();
		ConfigAvancada configuracao = new ConfigAvancada();

		if (!consignacaoService.validarConfiguracoesAvancadas(body, detailedErrors, parametros, configuracao)) {
			redirectAttrs.addFlashAttribute("detailedErrors", detailedErrors);
			return "redirect:/consignacao/alterar/" + body.get("idConsig").toString();
		}

		if (consignacaoService.confirmaAlteracaoAvancada(detailedErrors, configuracao)) {
			redirectAttrs.addFlashAttribute("detailedErrors", detailedErrors);
		} else {
			redirectAttrs.addFlashAttribute("detailedErrors", detailedErrors);
			
		}
		return "redirect:/consignacao/alterar/" + body.get("idConsig").toString();
	}

}
