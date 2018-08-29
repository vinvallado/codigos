package br.mil.fab.consigext.service.impl.util;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mil.fab.consigext.entity.EntidadeConsig;
import br.mil.fab.consigext.entity.NaturezaConsig;
import br.mil.fab.consigext.entity.Organizacao;
import br.mil.fab.consigext.entity.Servico;
import br.mil.fab.consigext.entity.ServicoConsig;
import br.mil.fab.consigext.entity.StatusConsignacao;
import br.mil.fab.consigext.repository.EntidadeConsigRepository;
import br.mil.fab.consigext.repository.NaturezaEntidadeRepository;
import br.mil.fab.consigext.repository.OrganizacaoRepository;
import br.mil.fab.consigext.repository.ServicoConsigRepository;
import br.mil.fab.consigext.repository.ServicoRepository;
import br.mil.fab.consigext.service.margem.ConsignacaoService;
import br.mil.fab.consigext.service.util.ComboUtilService;
import br.mil.fab.consigext.util.UsuarioLogado;

@Service
public class ComboUtilServiceImpl  implements ComboUtilService{

	@Autowired
	EntidadeConsigRepository entidadeConsigRepository;
	
	@Autowired
	ServicoConsigRepository servicoConsigRepository;
	
	@Autowired
	OrganizacaoRepository organizacaoRepository;
	
	@Autowired
	ServicoRepository servicoRepository;
	
	@Autowired
	ConsignacaoService consignacaoService;
	
	@Autowired 
	NaturezaEntidadeRepository naturezaRepository;
	
	@Override
	public HashMap<Long,String> findEntidadesConsig() {
		Map<Long,String> ents = new HashMap<Long,String>();
		List<EntidadeConsig> entidades = entidadeConsigRepository.findAll();
		ents =  entidades.stream().collect(Collectors.toMap(EntidadeConsig::getId, x -> (x.getCdEntidade() + " - " + x.getOrganizacao().getNmOrg())));
		return (HashMap<Long, String>) ents;											
	}
	
	@Override
	public HashMap<Long,String> findServicoEntidade(EntidadeConsig entidadeConsig) {
		Map<Long,String> servs = new HashMap<Long,String>();
		List<Servico> servicos = servicoRepository.findServicoFullByEntidade(entidadeConsig.getStEntidade());
		servs =  servicos.stream().collect(Collectors.toMap(Servico::getId, x -> (x.getCdServico() + " - " + x.getDsServico())));
		return (HashMap<Long, String>) servs;											
	}
	
	@Override
	public HashMap<String,String> findOrganizacoes() {
		Map<String,String> orgs = new HashMap<String,String>();
		List<Organizacao> organizacoes = organizacaoRepository.findOrganizacoesporEntidadesConsigs();
		orgs =  organizacoes.stream().collect(Collectors.toMap(Organizacao::getCdOrg, x -> (x.getCdOrg() + " - " + x.getNmOrg())));
		return (HashMap<String, String>) orgs;		
	}
	
	//
	
	@Override
	public HashMap<Long,String> findMotivos() {
		Map<Long,String> motivos = new HashMap<Long,String>();
		List<StatusConsignacao> statusConsignacao = consignacaoService.findByNmStatusIn(Arrays.asList("CANCELADA","CONCLUÍDA","LIQUIDADA","SUSPENSA PELA CSE."));
		motivos =  statusConsignacao.stream().collect(Collectors.toMap(StatusConsignacao::getId, x -> (x.getId() + " - " + x.getNmStatus())));
		return (HashMap<Long, String>) motivos;		
	}

	@Override
	public HashMap<Long,String> findNaturezas() {
		Map<Long,String> naturezas = new HashMap<Long,String>();
		List<NaturezaConsig> naturezaConsig = naturezaRepository.findAll();
		naturezas =  naturezaConsig.stream().collect(Collectors.toMap(NaturezaConsig::getId, x -> (x.getId() + " - " + x.getSgNatureza())));
		return (HashMap<Long, String>) naturezas;		
	}
	
	@Override
	public Map<Long, String> getServiceComboByEntidadeConsig(EntidadeConsig ent) {
		List<ServicoConsig> servicosConsignataria = new ArrayList<>();
		servicosConsignataria = ent.getServicoConsigs();
		Map<Long, String> map = new HashMap<Long, String>();
		try{
			for(ServicoConsig servConsig: servicosConsignataria) {
				Servico serv =servConsig.getServico(); 
				if(serv.getStExcluido()==0 && serv.getStServico()==1 && servConsig.getCaixaPagamento()!=null)
					map.put(serv.getId(), serv.getDsServico());
			}
		}
		catch(Exception e) {
			servicosConsignataria = servicoConsigRepository.findByEntidadeConsig_id(ent.getId());
			for(ServicoConsig servConsig: servicosConsignataria) {
				Servico serv = servConsig.getServico(); 
				if(serv.getStExcluido()==0 && serv.getStServico()==1 && servConsig.getCaixaPagamento()!=null) 
					map.put(serv.getId(), serv.getDsServico());
			}
		}		
		return map;	
	}
	@Override
	public List<String> getMotivosSuspenderReativarConsignacao() {
		List<String> motivos = new ArrayList<>();
		String[] motivosArray = new String[] {"DESBLOQUEIO", "DETERMINAÇÃO JUDICIAL", "DESLIGAMENTO", 
				"FALECIMENTO", "DEMISSÃO DO QUADRO SOCIAL", "LIQUIDAÇÃO ANTECIPADA", "PENSÃO ALIMENTÍCIA", "DECISÃO ADMINISTRATIVA", 
				"MOTIVO NÃO INFORMADO", "SOLICITAÇÃO DO MILITAR/PENS", "DESISTÊNCIA DA OPERAÇÃO", "FORMA ALTERNATIVA DE PAGAMENTO", 
				"ERRO OPERACIONAL DA EC", "IMPLANTAÇÃO DESCONTO OBRIGATÓRIO", "REMISSÃO", "DESCREDENCIAMENTO DA EC", "INFORMAÇÃO", 
				"SOLICITAÇÃO DA UPAG", "SOLICITAÇÃO DA EC", "ERRO OPERACIONAL DA EC VENDEDORA", "CORREÇÃO DE ERRO DE SISTEMA", 
				"CONFERÊNCIA NOTA VENDA EXECUTADA", "IDENTIFICAÇÃO", "LIBERACAO DE MARGEM VIA WEBSERVICE"};
		for(String m: motivosArray)
			motivos.add(m);
		return motivos;
	}
}
