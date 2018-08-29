package br.mil.fab.boletim.api.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import br.mil.fab.boletim.api.dao.BoletimDAO;
import br.mil.fab.boletim.api.entity.ComponenteGeral;
import br.mil.fab.boletim.api.entity.ComponenteMotivo;
import br.mil.fab.boletim.api.entity.MotivoBoletim;
import br.mil.fab.boletim.api.entity.MotivoUtilizado;
import br.mil.fab.boletim.api.helper.MotivoHelper;
import br.mil.fab.boletim.api.repository.ComponenteMotivoRepository;
import br.mil.fab.boletim.api.repository.MotivoUtilizadoRepository;
import br.mil.fab.boletim.api.request.BuscarComponentesRequest;
import br.mil.fab.boletim.api.request.ItemBoletimRequest;
import br.mil.fab.boletim.api.response.ItemBoletimResponse;

@Service
public class BoletimService {

	private BoletimDAO bRep;
	private ComponenteMotivoRepository mRep;
	private MotivoUtilizadoRepository muRep;
	private List<ComponenteMotivo> cm = new ArrayList<>();
	private MessageSource messageSource;
	
	
	

	@Autowired
	public BoletimService(BoletimDAO bRep, ComponenteMotivoRepository mRep,MessageSource messageSource,MotivoUtilizadoRepository muRep) {
		this.muRep = muRep;
		this.bRep = bRep;
		this.mRep = mRep;
		this.messageSource= messageSource;
	}

	public List<ItemBoletimResponse> inserirBoletim(List<ItemBoletimRequest> boletim) {
		List<MotivoUtilizado> mos = new ArrayList<>();
		List<ItemBoletimResponse> resps = new ArrayList<>();
		boletim.forEach(x -> {
			MotivoUtilizado mo = new MotivoUtilizado();
			MotivoBoletim mb = new MotivoBoletim();
			mb.setCdMotivo(x.getCdMotivo());
			mo.setMotivo(mb);
			mo.setDtRealizacao(new Date());
			mo.setCdOrg(x.getCdOrg());
			mo.setIdProcesso(new BigDecimal(x.getIdProcesso()));
			mo.setIdModulo(new BigDecimal(x.getIdModulo()));
			mo.setNrIpUsuario(x.getNrIpUsuario());
			mo.setNrItemBoletim(BigDecimal.ZERO);
			mo.setNrMacUsuario(x.getNrMacUsuario());
			mo.setNrOrdemUtilizado(x.getNrOrdem());
			mo.setStAutorizacao(x.getStAutorizacao());
			mo.setStConformidade("E");
			getComponentes(x.getComponentes(), mo);
		    mos.add(mo);
	
		});
		return bRep.bulkPersist(mos, resps);
	}
	
	public List<ComponenteMotivo> buscarComponenteMotivoByMotivo(String cdMotivo) {
		List<ComponenteMotivo> comp = new  ArrayList<ComponenteMotivo>();
		comp = mRep.buscarPorMotivoAndCampo(cdMotivo);
		if(comp.isEmpty()) {
			return new ArrayList<>();
		}
		return comp;
		
	}
	
	public List<MotivoUtilizado> buscarMotivoUtilizadoByIdProcesso(String idProcesso, String idModulo) {
		List<MotivoUtilizado> comps = new  ArrayList<MotivoUtilizado>();
		comps = muRep.buscarMotivoUtilizadoByIdProcesso(new BigDecimal(idModulo),new BigDecimal(idProcesso));
		MotivoHelper.formatarTextos(comps);
		if(comps.isEmpty()) {
			return new ArrayList<>();
		}
		return comps;
		
	}

	private boolean getComponentes(List<BuscarComponentesRequest> comps, MotivoUtilizado mo) {
		mo.setComponentes(new ArrayList<>());
		if (cm.isEmpty() || !cm.stream().anyMatch(x -> x.getCdMotivo().equals(mo.getMotivo().getCdMotivo()))) {
			cm = mRep.buscarPorMotivoAndCampo(mo.getMotivo().getCdMotivo());
		}
		comps.stream().forEach(y -> {
			ComponenteGeral geral = new ComponenteGeral();
			geral.setVlLancado(y.getVlLancado());
			geral.setMotivo(mo);
			Optional<ComponenteMotivo> c = cm.stream().filter(f -> f.getNmCampo().equals(y.getNmCampo())).findFirst();
			if (c != null && c.isPresent()) {
				geral.setCompMotivo(c.get());
				mo.getComponentes().add(geral);
			}
		});
		return mo.getComponentes().size() == comps.size();
	}

}
