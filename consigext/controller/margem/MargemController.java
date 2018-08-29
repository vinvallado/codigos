package br.mil.fab.consigext.controller.margem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import br.mil.fab.consigext.config.ApisProperties;
import br.mil.fab.consigext.config.Message;
import br.mil.fab.consigext.dto.CetConsig;
import br.mil.fab.consigext.dto.MargemApiDTO;
import br.mil.fab.consigext.entity.Consignacao;
import br.mil.fab.consigext.entity.EntidadeConsig;
import br.mil.fab.consigext.service.codigounico.CodigoUnicoService;
import br.mil.fab.consigext.service.margem.CetConsigService;
import br.mil.fab.consigext.service.margem.ConsignacaoService;
import br.mil.fab.consigext.service.margem.EnderecoServidorService;
import br.mil.fab.consigext.service.margem.EntidadeConsigService;
import br.mil.fab.consigext.service.margem.OrganizacaoService;
import br.mil.fab.consigext.service.margem.ServicoConsigService;
import br.mil.fab.consigext.service.margem.ServicoService;
import br.mil.fab.consigext.service.margem.ServidorConsigService;
import br.mil.fab.consigext.service.util.ComboUtilService;
import br.mil.fab.consigext.service.util.UsuarioUtilService;
import br.mil.fab.consigext.util.GenericUtil;
import br.mil.fab.consigext.util.ParcelaUtil;
import br.mil.fab.consigext.util.UsuarioLogado;

@Controller

@RequestMapping(value = "/margem")
public class MargemController {

	@Autowired
	UsuarioLogado usuarioLogado;

	@Autowired
	EntidadeConsigService entidadeConsigService;

	@Autowired
	Message msg;

	@Autowired
	ServicoService servicoService;

	@Autowired
	ComboUtilService comboUtilService;

	@Autowired
	OrganizacaoService organizacaoService;

	@Autowired
	ServidorConsigService servidorConsigService;

	@Autowired
	ServicoConsigService servicoConsigService;

	@Autowired
	ConsignacaoService consigService;

	@Autowired
	UsuarioUtilService usrUtilService;

	@Autowired
	CetConsigService cetconsigService;

	@Autowired
	ConsignacaoService consignacaoService;

	@Autowired
	CodigoUnicoService codigoUnicoservice;

	@Autowired
	private ApisProperties props;
	
	@Autowired
	EnderecoServidorService enderecoServidorService;

	@RequestMapping("/solic")
	public String solicmargem(Model model) {
		model.addAttribute("consignatarias", comboUtilService.findEntidadesConsig());
		model.addAttribute("detailedErrors", new ArrayList<>());
		return "margem/solicmargem";
	}
	
	@RequestMapping(value="/val", method = RequestMethod.GET)
	public String solic() {
		return "redirect:/margem/solic";
	}
	@RequestMapping(value = "/getService/{id}", method = RequestMethod.GET)
	public @ResponseBody Map<Long, String> getServiceCombo(@PathVariable("id") Long id, Model model) {
		EntidadeConsig ent = entidadeConsigService.findById(id);
		model.addAttribute("consignatariaSelected", id);
		Map<Long, String> services = comboUtilService.getServiceComboByEntidadeConsig(ent);
		model.addAttribute("servicos", services);
		return services;
	}

	@PostMapping("/val")	
	public String valmargem(@RequestParam Map<String, Object> body, Model model) {
		List<String> errors = new ArrayList<>();
		if (!consigService.validaSolicMargemFiltro(body, model)) {
			return "margem/solicmargem";
		}
		long idConsig = Long.parseLong(body.get("consigSelect").toString());
		EntidadeConsig entidadeConsig = entidadeConsigService.findById(idConsig);
		if (entidadeConsig.getStEntidade() == 0) {
			errors.add(msg.get("consignataria.bloqueada"));
			model.addAttribute("detailedErrors", errors);
			return "margem/solicmargem";
		}		
		Consignacao consig = consigService.gerarConsignacao(body);
		if (consig == null) {
			errors.add(msg.get("margem.erro.processamento"));
			model.addAttribute("detailedErrors", errors);		
			return "margem/solicmargem";
		}
		model.addAttribute("consignacao", consig);
		model.addAttribute("paramPessoa",
				(consigService.isMatricula(body, "matInput")) ? body.get("matInput").toString()
						: body.get("cpfInput").toString());
		model.addAttribute("porcMargem", servicoConsigService.retornaValorMargemServico(idConsig,
				Long.parseLong(body.get("servSelect").toString()), "INFINCMGREST"));
		int maxParcela = consigService.getParcelaMaxima(body, consig, model);
		if (maxParcela > 0) {
			model.addAttribute("maxParcela", maxParcela);
		} else {
			return "margem/solicmargem";
		}
		return "margem/valmargem";
	}

	@RequestMapping(value = "/confirma", method = RequestMethod.POST)
	public String confirmamargem(@RequestParam Map<String, Object> body, @ModelAttribute String paramPessoa,
			@ModelAttribute @Valid Consignacao consignacao, BindingResult result, Model model) {

		model.addAttribute("maxParcela", Integer.parseInt(body.get("maxParcela").toString()));
		model.addAttribute("porcMargem", body.get("porcMargem").toString());

		if (result.hasErrors() || !consigService.validaValMargem(body, model)) {
			return setViewValMargem(body, model, consignacao);
		}

		List<CetConsig> cets = cetconsigService.findCetConsig(
				consignacao.getEntidadeConsig().getId(), consignacao.getNrPrestacoes(),
				Long.valueOf(GenericUtil.getAAAAMMtual()));
		
		for(CetConsig c : cets) {
			if (c.getVlCet() <= 0) cets.remove(c);
		}
		
		if ((cets.size() == 0) || (cets.size() > 0 && !ParcelaUtil.autorizaValorParcela(
				consigService.getMargem(body.get("porcMargem").toString(), consignacao), cets.get(0).getVlCet(),
				consignacao))) {
			consigService.retornaMsgErroVlMargem(cets.size(), model);
			return setViewValMargem(body, model, consignacao);
		}

		model.addAttribute("dataHoraAtual", GenericUtil.getDateTimeAtual());
		model.addAttribute("paramPessoa", body.get("paramPessoa").toString());
		model.addAttribute("porcMargem", body.get("porcMargem").toString());
		model.addAttribute("cets", cets);
		model.addAttribute("consignacao", consignacao);

		return "margem/confirmamargem";
	}

	@RequestMapping(value = "/confirmaconsignacao", method = RequestMethod.POST, params = { "concluir=concluir" })
	public String confirmaconsig(@RequestParam Map<String, Object> body, String codUnicoInput,
			@ModelAttribute @Valid Consignacao consignacao, BindingResult result, Model model) {

		model.addAttribute("maxParcela", Integer.parseInt(body.get("maxParcela").toString()));
		model.addAttribute("porcMargem", body.get("porcMargem").toString());

		if (result.hasErrors()) {
			return setViewConfirma(body, model, consignacao);
		}

		if (codigoUnicoservice.validaCodigoUnico(body.get("codUnicoInput").toString(), consignacao.getServidorConsig().getId(), model)) {
			if(!consignacaoService.validaIndice(consignacao.getServidorConsig().getId(),
					consignacao.getEntidadeConsig().getId(), consignacao.getNrIndice())) {
				consignacaoService.save(consignacao, body.get("codUnicoInput").toString(),
						body.get("porcMargem").toString(), model);
			}
			else {
				List<String> detailedErrors = new ArrayList<>();
				detailedErrors.add(msg.get("indice.invalido"));
				model.addAttribute("detailedErrors", detailedErrors);
				return setViewConfirma(body, model, consignacao);
			}
		}
		else{
			List<String> detailedErrors = new ArrayList<>();
			detailedErrors.add(msg.get("codigoUnico.invalido"));
			model.addAttribute("detailedErrors", detailedErrors);
			return setViewConfirma(body, model, consignacao);
		} 

		model.addAttribute("enderecoAtual", enderecoServidorService
				.findByEnderecoAtualCompleto(consignacao.getServidorConsig().getPesfis().getNrOrdem()));
		model.addAttribute("consignacao", consignacao);

		return "/consignacao/autorizacaodesconto";
	}

	@RequestMapping(value = "/confirmaconsignacao", method = RequestMethod.POST, params = { "cancelar=cancelar" })
	public String cancelarConsig(@RequestParam Map<String, Object> body, @ModelAttribute Consignacao consignacao,
			Model model) {

		model.addAttribute("maxParcela", Integer.parseInt(body.get("maxParcela").toString()));
		model.addAttribute("porcMargem", body.get("porcMargem").toString());

		if (StringUtils.isNotBlank(consignacao.getServidorConsig().getPesfis().getNrOrdem())) {
			body.put("matInput", consignacao.getServidorConsig().getPesfis().getNrOrdem());
		} else {
			body.put("cpfInput", consignacao.getServidorConsig().getPesfis().getNrCpf());
		}

		body.put("consigSelect", consignacao.getEntidadeConsig().getId());
		body.put("servSelect", consignacao.getServicoConsig().getServico().getId());

		return setViewValMargem(body, model, consignacao);
	}

	private String setViewValMargem(Map<String, Object> body, Model model, Consignacao consignacao) {
		consignacao.getEntidadeConsig()
				.setOrganizacao(consigService.getOrgConverter(body, "paramPessoa"));
		consignacao.getServidorConsig().setPesfis(consigService.getPessoaConverter(body.get("paramPessoa").toString()));
		body.put("consignacao", consignacao);
		configRetorno(body, model, consignacao);
		return "margem/valmargem";
	}

	private String setViewConfirma(Map<String, Object> body, Model model, Consignacao consignacao) {
		model.addAttribute("dataHoraAtual", GenericUtil.getDateTimeAtual());
		model.addAttribute("cets",
				cetconsigService.findCetConsig(consignacao.getEntidadeConsig().getId(),
						consignacao.getNrPrestacoes(), Long.valueOf(GenericUtil.getAAAAMMtual())));
		configRetorno(body, model, consignacao);
		return "/margem/confirmamargem";
	}

	private void configRetorno(Map<String, Object> body, Model model, Consignacao consignacao) {
		model.addAttribute("porcMargem", body.get("porcMargem").toString());
		model.addAttribute("paramPessoa", body.get("paramPessoa").toString());
		model.addAttribute("consignacao", consignacao);
	}
	@RequestMapping(value = "/getServices/{idConsignataria}", method = RequestMethod.GET)
	public @ResponseBody Map<Long, String> getServiceComboWithId(@PathVariable("idConsignataria") Long id, Model model) {
		EntidadeConsig ent = entidadeConsigService.findById(id);
		model.addAttribute("idConsignatariaSelected", id);
		Map<Long, String> services = comboUtilService.getServiceComboByEntidadeConsig(ent);
		model.addAttribute("servicos", services);
		return services;
	}

	@RequestMapping(value = "/getServices/", method = RequestMethod.GET)
	public @ResponseBody Map<Long, String> getServiceCombo(Model model) {
		Map<Long, String> services = new HashMap<Long, String>();
		model.addAttribute("servicos", services);
		return services;
	}
}
