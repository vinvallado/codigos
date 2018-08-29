package br.mil.fab.consigext.controller.margem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.mil.fab.consigext.config.Message;
import br.mil.fab.consigext.dto.CetConsig;
import br.mil.fab.consigext.entity.Consignacao;
import br.mil.fab.consigext.entity.EntidadeConsig;
import br.mil.fab.consigext.enums.Restriction;
import br.mil.fab.consigext.enums.StatusAcesso;
import br.mil.fab.consigext.service.codigounico.CodigoUnicoService;
import br.mil.fab.consigext.service.margem.CetConsigService;
import br.mil.fab.consigext.service.margem.ConsignacaoService;
//import br.mil.fab.consigext.service.margem.EnderecoServidorService;
import br.mil.fab.consigext.service.margem.EntidadeConsigService;
import br.mil.fab.consigext.service.margem.ServicoConsigService;
import br.mil.fab.consigext.service.margem.ServicoService;
import br.mil.fab.consigext.service.util.ComboUtilService;
import br.mil.fab.consigext.service.util.UsuarioUtilService;
import br.mil.fab.consigext.util.GenericUtil;
import br.mil.fab.consigext.util.ParcelaUtil;
import br.mil.fab.consigext.util.UsuarioLogado;

/**
 * @todo statusConsigService nao foi achado!
 * @todo enderecoServiceService nao foi achado!
 * @author marcelomamc
 *
 */
@Controller
@RequestMapping(value="/margemconsig")
public class MargemConsigController {

	@Autowired
	Message msg;
	
	@Autowired
	UsuarioLogado usrLogado;
	
	@Autowired
	ServicoService servServico;
	
	@Autowired
	UsuarioUtilService usrUtilService;
	
	@Autowired
	@Qualifier("consignacaoConsignatariaServiceImpl")
	ConsignacaoService consigService;
	
	@Autowired
	ServicoConsigService servicoConsigService;
	
	@Autowired
	EntidadeConsigService entidadeConsigService;
	
	@Autowired
	ComboUtilService comboUtilService;
	
	@Autowired
	CetConsigService cetconsigService;
	
	@Autowired
	ConsignacaoService consignacaoService;

	@Autowired
	CodigoUnicoService codigoUnicoservice;
	
	/*@Autowired
	EnderecoServidorService enderecoServidorService;*/

	
	@RequestMapping("/solic")
	public String solicmargem(Model model, RedirectAttributes redirectAttrs) {
		List<String> detailedErrors = new ArrayList<>();
		StatusAcesso acesso = usrUtilService.hasAccess(Restriction.QUALQUER_ENTIDADE_CONSIG,null);
		if (acesso!=StatusAcesso.PERMITIDO) {					
			detailedErrors.add(msg.get(acesso.getStatusAcesso()));
			redirectAttrs.addFlashAttribute("detailedErrors", detailedErrors);
			return "redirect:/index/";
		}
		model.addAttribute("consigSelect", usrLogado.getUsuarioCorrente().getEntidadeConsig().getId());
		EntidadeConsig ent = entidadeConsigService.findById(usrLogado.getUsuarioCorrente().getEntidadeConsig().getId());
		Map<Long, String> services = comboUtilService.getServiceComboByEntidadeConsig(ent);
		
		if (services.isEmpty()) {
			detailedErrors.add(msg.get("campo.servicos.vazio"));
		} 
		model.addAttribute("servicos", services);
		model.addAttribute("detailedErrors", new ArrayList<>());
		return "margem/solicmargem";
	}
	
	@RequestMapping(value = "/getService", method = RequestMethod.GET)
	public @ResponseBody Map<Long, String> getServiceCombo(Model model) {
		
		EntidadeConsig ent = entidadeConsigService.findById(usrLogado.getUsuarioCorrente().getEntidadeConsig().getId());
		Map<Long, String> services = comboUtilService.getServiceComboByEntidadeConsig(ent);
		model.addAttribute("servicos", services);
		return services;
	}
	
	@PostMapping( "/val" )
	public String valmargem(@RequestParam Map<String,Object> body,  Model model, RedirectAttributes redirectAttrs) {
		long idConsig = Long.parseLong(body.get("consigSelect").toString());
		StatusAcesso acesso = usrUtilService.hasAccess(Restriction.ENTIDADE_CONSIG_CORRENTE,idConsig);
		if (acesso!=StatusAcesso.PERMITIDO) {
			List<String> detailedErrors = new ArrayList<>();
			detailedErrors.add(msg.get(acesso.getStatusAcesso()));
			redirectAttrs.addFlashAttribute("detailedErrors", detailedErrors);
			return "redirect:/margemconsig/solic";
		}
		if(!consigService.validaSolicMargemFiltro(body, model)){
			model.addAttribute("consigSelect", usrLogado.getUsuarioCorrente().getEntidadeConsig().getId());
        	return "margem/solicmargem";
        }
		
		EntidadeConsig entidadeConsig = entidadeConsigService.findById(idConsig);
		if(entidadeConsig.getStEntidade()==0) {
			List<String> errors = new ArrayList<>();
			errors.add(msg.get("consignataria.bloqueada"));
			model.addAttribute("detailedErrors", errors);		
			return "margem/solicmargem";
		}	
		Consignacao consig = consigService.gerarConsignacao(body);
		model.addAttribute("consignacao", consig);
		model.addAttribute("paramPessoa", (consigService.isMatricula(body,"matInput")) ? body.get("matInput").toString() : body.get("cpfInput").toString());
		model.addAttribute("porcMargem", servicoConsigService.retornaValorMargemServico(Long.parseLong(body.get("consigSelect").toString()), 
																						Long.parseLong(body.get("servSelect").toString()), "INFINCMGREST"));
	    int maxParcela = consigService.getParcelaMaxima(body, consig, model);
		if (maxParcela > 0) {
			model.addAttribute("maxParcela", maxParcela);
		} else {
			model.addAttribute("consigSelect",consig.getEntidadeConsig().getId());
			return "margem/solicmargem";
		}
		return "margem/valmargem";
	}
	
	@RequestMapping(value = "/confirma", method = RequestMethod.POST)
	public String confirmamargem(@RequestParam Map<String, Object> body, @ModelAttribute String paramPessoa,
			@ModelAttribute @Valid Consignacao consignacao, BindingResult result, Model model, RedirectAttributes redirectAttrs) {
		StatusAcesso acesso = usrUtilService.hasAccess(Restriction.QUALQUER_ENTIDADE_CONSIG,null);
		if (acesso!=StatusAcesso.PERMITIDO) {
			List<String> detailedErrors = new ArrayList<>();
			detailedErrors.add(msg.get(acesso.getStatusAcesso()));
			redirectAttrs.addFlashAttribute("detailedErrors", detailedErrors);
			return "redirect:/margemconsig/solic";
		}
		model.addAttribute("maxParcela", Integer.parseInt(body.get("maxParcela").toString()));
		model.addAttribute("porcMargem", body.get("porcMargem").toString());

		if (result.hasErrors() || !consigService.validaValMargem(body, model)) {
			return setViewValMargem(body, model, consignacao);
		}

		List<CetConsig> cets = cetconsigService.findCets(consignacao.getNrPrestacoes(),
				Long.valueOf(GenericUtil.getAAAAMMtual()));

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
	
	@RequestMapping(value = "/confirmaconsignacao", method = RequestMethod.POST, params= {"concluir=concluir"})
	public String confirmaconsig(@RequestParam Map<String, Object> body, String codUnicoInput,
			@ModelAttribute @Valid Consignacao consignacao, BindingResult result, Model model, RedirectAttributes redirectAttrs) {
		
		StatusAcesso acesso = usrUtilService.hasAccess(Restriction.QUALQUER_ENTIDADE_CONSIG,null);
		if (acesso!=StatusAcesso.PERMITIDO) {
			List<String> detailedErrors = new ArrayList<>();
			detailedErrors.add(msg.get(acesso.getStatusAcesso()));
			redirectAttrs.addFlashAttribute("detailedErrors", detailedErrors);
			return "redirect:/margemconsig/solic";
		}
		model.addAttribute("maxParcela", Integer.parseInt(body.get("maxParcela").toString()));
		model.addAttribute("porcMargem", body.get("porcMargem").toString());

		if (result.hasErrors()) {
			return setViewConfirma(body, model, consignacao);
		}

		if (codigoUnicoservice.validaCodigoUnico(body.get("codUnicoInput").toString(), consignacao.getServidorConsig().getId(), model)
				&& !consignacaoService.validaIndice(consignacao.getServidorConsig().getId(),
						consignacao.getEntidadeConsig().getId(), consignacao.getNrIndice())) {
			consignacaoService.save(consignacao, body.get("codUnicoInput").toString(),
					body.get("porcMargem").toString(), model);
		} else {
			return setViewConfirma(body, model, consignacao);
		}
		
		//model.addAttribute("enderecoAtual", enderecoServidorService.findByEnderecoAtualCompleto(consignacao.getServidorConsig().getPesfis().getNrOrdem()));
		model.addAttribute("enderecoAtual", "");
		model.addAttribute("consignacao", consignacao);

		return "/consignacao/autorizacaodesconto";
	}
	
	
	@RequestMapping(value = "/confirmaconsignacao", method = RequestMethod.POST, params = { "cancelar=cancelar" })
	public String cancelarConsig(@RequestParam Map<String, Object> body, @ModelAttribute Consignacao consignacao,
			Model model, RedirectAttributes redirectAttrs) {
		StatusAcesso acesso = usrUtilService.hasAccess(Restriction.QUALQUER_ENTIDADE_CONSIG,null);
		if (acesso!=StatusAcesso.PERMITIDO) {
			List<String> detailedErrors = new ArrayList<>();
			detailedErrors.add(msg.get(acesso.getStatusAcesso()));
			redirectAttrs.addFlashAttribute("detailedErrors", detailedErrors);
			return "redirect:/margemconsig/solic";
		}
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
		consignacao.getServidorConsig()
			.setPesfis(consigService.getPessoaConverter(body.get("paramPessoa").toString()));
		body.put("consignacao", consignacao);
		configRetorno(body, model, consignacao);
		return "margem/valmargem";
	}

	private String setViewConfirma(Map<String, Object> body, Model model, Consignacao consignacao) {
		model.addAttribute("dataHoraAtual", GenericUtil.getDateTimeAtual());
		model.addAttribute("cets", cetconsigService.findCetConsig(
				consignacao.getEntidadeConsig().getId(), consignacao.getNrPrestacoes(),
				Long.valueOf(GenericUtil.getAAAAMMtual())));
		configRetorno(body, model, consignacao);
		return "/margem/confirmamargem";
	}
	
	private void configRetorno(Map<String, Object> body, Model model, Consignacao consignacao) {
		model.addAttribute("porcMargem", body.get("porcMargem").toString());
		model.addAttribute("paramPessoa", body.get("paramPessoa").toString());
		model.addAttribute("consignacao", consignacao);
	}

}
