package br.mil.fab.consigext.controller.margem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.mil.fab.consigext.config.Message;
import br.mil.fab.consigext.dto.CetConsigRanking;
import br.mil.fab.consigext.entity.Consignacao;
import br.mil.fab.consigext.entity.Servico;
import br.mil.fab.consigext.enums.Restriction;
import br.mil.fab.consigext.enums.StatusAcesso;
import br.mil.fab.consigext.service.codigounico.CodigoUnicoService;
import br.mil.fab.consigext.service.margem.CetConsigRankingService;
import br.mil.fab.consigext.service.margem.CetConsigService;
import br.mil.fab.consigext.service.margem.ConsignacaoService;
import br.mil.fab.consigext.service.margem.EnderecoServidorService;
import br.mil.fab.consigext.service.margem.EntidadeConsigService;
import br.mil.fab.consigext.service.margem.ServicoConsigService;
import br.mil.fab.consigext.service.margem.ServicoService;
import br.mil.fab.consigext.service.parametro.ParametroService;
import br.mil.fab.consigext.service.util.ComboUtilService;
import br.mil.fab.consigext.service.util.UsuarioUtilService;
import br.mil.fab.consigext.util.GenericUtil;
import br.mil.fab.consigext.util.UsuarioLogado;

@Controller

@RequestMapping(value="/margemservidor")
public class MargemServidorControler {

	@Autowired
	Message msg;
	
	@Autowired
	UsuarioLogado usrLogado;
	
	@Autowired
	ServicoConsigService servicosConsigService;
	
	@Autowired
	ServicoService servServico;
	
	@Autowired
	UsuarioUtilService usrUtilService;
	
	@Autowired
	@Qualifier("consignacaoServidorServiceImp")
	ConsignacaoService consigService;
	
	@Autowired
	ParametroService parametroService;
	
	@Autowired
	ServicoConsigService servicoConsigService;
	
	@Autowired
	EntidadeConsigService entidadeConsigService;
	
	@Autowired
	ComboUtilService comboUtilService;
	
	@Autowired
	CetConsigService cetconsigService;

	@Autowired
	CodigoUnicoService codigoUnicoservice;
		
	@Autowired
	EnderecoServidorService enderecoServidorService;
	
	@Autowired
	CetConsigRankingService cetConsigRankingService;
	
	@RequestMapping("/solic")
	public String solicmargem(Model model, RedirectAttributes redirectAttrs) {		
		StatusAcesso acesso = usrUtilService.hasAccess(Restriction.SERVIDOR,null);
		if (acesso!=StatusAcesso.PERMITIDO) {					
			List<String> detailedErrors = new ArrayList<>();
			detailedErrors.add(msg.get(acesso.getStatusAcesso()));
			redirectAttrs.addFlashAttribute("detailedErrors", detailedErrors);
			return "redirect:/index/";
		}
		model.addAttribute("valMargem30", servicoConsigService.retornaValMargem30Servidor(usrLogado.getNrOrdem()));
		Servico emprestimo = servServico.findByCdServico("14");	
		String s = parametroService.findByServicoSiglaParametro(emprestimo, "INFQTDMAXPARINC");	
		int maxParcelas;
		if(s!=null)
			maxParcelas = Integer.parseInt(s.split("\\|")[0]);
		else
			maxParcelas = 1;
		model.addAttribute("maxParcelas", maxParcelas);
		return "margem/solicmargem";
	}
	
	@RequestMapping(value="/val", method = RequestMethod.POST)
	public String valmargem(@RequestParam Map<String, Object> body,  Model model, RedirectAttributes redirectAttrs) {
		StatusAcesso acesso = usrUtilService.hasAccess(Restriction.SERVIDOR,null);
		if (acesso!=StatusAcesso.PERMITIDO) {
			List<String> detailedErrors = new ArrayList<>();
			detailedErrors.add(msg.get(acesso.getStatusAcesso()));
			redirectAttrs.addFlashAttribute("detailedErrors", detailedErrors);
			return "redirect:/margemservidor/solic";
		}
		if (!consigService.validaSolicMargemFiltro(body, model)) {
			setValoresMargem(model);
			return "margem/solicmargem";
		}
		
		consigService.geraValores(body,model);
		
		List<CetConsigRanking> cets = cetConsigRankingService.montarRankingCets(usrLogado, body);
		if(cets==null)
			model.addAttribute("cets", null);
		else if (cets.size() > 0) {
			model.addAttribute("cets", cets);
		} else {
			List<String> detailedErrors = new ArrayList<String>();
			detailedErrors.add(msg.get("cet.atributo.nulo"));
			model.addAttribute("detailedErrors", detailedErrors);
			setValoresMargem(model);
			return "margem/solicmargem";
		}

		model.addAttribute("maxParcela", 96);
		model.addAttribute("dataHoraAtual", GenericUtil.getDateTimeAtual());
		model.addAttribute("numPrestSelect",body.get("numPrestSelect").toString());
		model.addAttribute("porcMargem", "30");
		model.addAttribute("valMargem30", body.get("valMargem30").toString());
		
		return "margem/valmargem";
	}
	
	public void setValoresMargem(Model model) {
		model.addAttribute("valMargem30", servicoConsigService.retornaValMargem30Servidor(usrLogado.getNrOrdem()));
		model.addAttribute("maxParcelas", consigService.getMaxParcelas(servServico.findByCdServico("14")));
	}
	
	
	@RequestMapping(value = "/confirma", method = RequestMethod.POST,  params= {"concluir=concluir"})
	public String confirmamargem(@RequestParam Map<String, Object> body, Model model, RedirectAttributes redirectAttrs) {
		StatusAcesso acesso = usrUtilService.hasAccess(Restriction.SERVIDOR,null);
		if (acesso!=StatusAcesso.PERMITIDO) {
			List<String> detailedErrors = new ArrayList<>();
			detailedErrors.add(msg.get(acesso.getStatusAcesso()));
			redirectAttrs.addFlashAttribute("detailedErrors", detailedErrors);
			return "redirect:/margemservidor/solic";
		}
		model.addAttribute("maxParcela", 96);
		model.addAttribute("porcMargem", "30");
		CetConsigRanking cet = new CetConsigRanking(Long.parseLong(body.get("idCet").toString()), Long.parseLong(body.get("cdAnomes").toString()),
													Long.parseLong(body.get("nrParcela").toString()), Double.parseDouble(body.get("vlCet").toString()),
													body.get("nmOrg").toString(), Long.parseLong(body.get("idEntidadeConsig").toString()),
													Long.parseLong(body.get("idServico").toString()), GenericUtil.stringToBigDecimal(body.get("vlLiquidoLib").toString()),
													GenericUtil.stringToBigDecimal(body.get("vlPrestacao").toString()), Boolean.parseBoolean(body.get("autorizado").toString()),
													Long.parseLong(body.get("stEntidade").toString()));
		Consignacao consig = consigService.gerarConsignacao(usrLogado, cet.getIdServico(), cet.getIdEntidadeConsig(), cet);
		
		model.addAttribute("valPrtInput", body.get("valPrtInput").toString());
		model.addAttribute("valLiqInput",body.get("valLiqInput").toString());
		model.addAttribute("valMargem30", body.get("valMargem30").toString());
		
		model.addAttribute("vlLiquidoLib", body.get("vlLiquidoLib").toString());
		model.addAttribute("vlPrestacao", body.get("vlPrestacao").toString());
		model.addAttribute("numPrestSelect", body.get("nrParcela").toString());
		
		model.addAttribute("cet", cet);
		model.addAttribute("dataHoraAtual", GenericUtil.getDateTimeAtualFormat());
		model.addAttribute("paramPessoa", usrLogado.getNrOrdem());
		model.addAttribute("consignacao", consig);
		
		return "margem/confirmamargem";
	}

	@RequestMapping(value="/confirma", method = RequestMethod.POST, params= {"cancelar=cancelar"})
	public String confirmamargemcancelar(Model model, RedirectAttributes redirectAttrs) {
		StatusAcesso acesso = usrUtilService.hasAccess(Restriction.SERVIDOR,null);
		if (acesso!=StatusAcesso.PERMITIDO) {
			List<String> detailedErrors = new ArrayList<>();
			detailedErrors.add(msg.get(acesso.getStatusAcesso()));
			redirectAttrs.addFlashAttribute("detailedErrors", detailedErrors);
			return "redirect:/margemservidor/solic";
		}
		setValoresMargem(model);
		return "margem/solicmargem";
	}
	
	@RequestMapping(value = "/confirmaconsignacao", method = RequestMethod.POST, params= {"concluir=concluir"})
	public String confirmaconsig(@RequestParam Map<String, Object> body, String codUnicoInput, RedirectAttributes redirectAttrs,
			@ModelAttribute @Valid Consignacao consignacao, @ModelAttribute CetConsigRanking cet, BindingResult result, Model model) {
		StatusAcesso acesso = usrUtilService.hasAccess(Restriction.SERVIDOR,null);
		if (acesso!=StatusAcesso.PERMITIDO) {
			List<String> detailedErrors = new ArrayList<>();
			detailedErrors.add(msg.get(acesso.getStatusAcesso()));
			redirectAttrs.addFlashAttribute("detailedErrors", detailedErrors);
			return "redirect:/margemservidor/solic";
		}
		model.addAttribute("maxParcela", Integer.parseInt(body.get("maxParcela").toString()));
		model.addAttribute("porcMargem", body.get("porcMargem").toString());

		if (codigoUnicoservice.validaCodigoUnico(body.get("codUnicoInput").toString(), consignacao.getServidorConsig().getId(), model)) {
			consigService.save(consignacao, body.get("codUnicoInput").toString(),
					body.get("porcMargem").toString(), model);
		}  else {
			Consignacao consig =  consigService.gerarConsignacao(usrLogado, consignacao.getServicoConsig().getId(), 
					consignacao.getEntidadeConsig().getId(),cet);
			
			consig.setVlLiquidoLiberado(GenericUtil.stringToBigDecimal(body.get("vlLiquidoLiberado").toString()));
			consig.setVlPrestacao(GenericUtil.stringToBigDecimal(body.get("vlPrestacao").toString()));
			consig.setNrPrestacoes(Long.parseLong(body.get("numPrestSelect").toString()));
			consig.setServicoConsig(consignacao.getServicoConsig());
			
			model.addAttribute("valLiqInput", body.get("valLiqInput").toString());
			model.addAttribute("valPrtInput", body.get("valPrtInput").toString());
			model.addAttribute("valMargem30", body.get("valMargem30").toString());
			model.addAttribute("numPrestSelect", body.get("numPrestSelect").toString());
			model.addAttribute("consignacao", consig);
			model.addAttribute("dataHoraAtual", GenericUtil.getDateTimeAtualFormat());
			return "margem/confirmamargem";
		}
		
		//TODO: Como verificar o indice no caso do servidor?
		/*	&& !consignacaoService.validaIndice(consignacao.getServidorConsig().getId(),
					consignacao..getEntidadeConsig().getId(), consignacao.getNrIndice())
		} else {
			return setViewConfirma(body, model, consignacao);
		}*/
		
		model.addAttribute("enderecoAtual", enderecoServidorService.findByEnderecoAtualCompleto(consignacao.getServidorConsig().getPesfis().getNrOrdem()));
		model.addAttribute("consignacao", consignacao);

		return "/consignacao/autorizacaodesconto";
	}
	

	@RequestMapping(value = "/confirmaconsignacao", method = RequestMethod.POST, params = { "cancelar=cancelar" })
	public String cancelarConsig(@RequestParam Map<String, Object> body, @ModelAttribute Consignacao consignacao, RedirectAttributes redirectAttrs,
			Model model) {
		StatusAcesso acesso = usrUtilService.hasAccess(Restriction.SERVIDOR,null);
		if (acesso!=StatusAcesso.PERMITIDO) {
			List<String> detailedErrors = new ArrayList<>();
			detailedErrors.add(msg.get(acesso.getStatusAcesso()));
			redirectAttrs.addFlashAttribute("detailedErrors", detailedErrors);
			return "redirect:/margemservidor/solic";
		}
		model.addAttribute("valLiqInput", body.get("valLiqInput").toString());
		model.addAttribute("valPrtInput", body.get("valPrtInput").toString());
		model.addAttribute("valMargem30", body.get("valMargem30").toString());
		model.addAttribute("numPrestSelect", body.get("numPrestSelect").toString());
		
		model.addAttribute("dataHoraAtual", GenericUtil.getDateTimeAtualFormat());
		
		consigService.geraValores(body,model);
		
		//TODO: Validar parcela máxima !
		//int maxParcela = consigService.getParcelaMaxima(body, consig, model);
		model.addAttribute("cets", cetConsigRankingService.montarRankingCets(usrLogado, body));
		model.addAttribute("maxParcela", body.get("numPrestSelect").toString());
		model.addAttribute("porcMargem", "30");
		
		return "margem/valmargem";
	}
	
}
