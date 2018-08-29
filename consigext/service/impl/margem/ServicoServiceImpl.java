package br.mil.fab.consigext.service.impl.margem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.ssl.asn1.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mil.fab.consigext.dto.ServicoDTO;
import br.mil.fab.consigext.entity.NaturezaServico;
import br.mil.fab.consigext.entity.Parametro;
import br.mil.fab.consigext.entity.ParametroServico;
import br.mil.fab.consigext.entity.ParametroServicoConsig;
import br.mil.fab.consigext.entity.Servico;
import br.mil.fab.consigext.repository.ParametroRepository;
import br.mil.fab.consigext.repository.ParametroServicoRepository;
import br.mil.fab.consigext.repository.ServicoRepository;
import br.mil.fab.consigext.service.margem.ServicoService;
import br.mil.fab.consigext.service.parametro.ParametroService;

@Service
public class ServicoServiceImpl implements ServicoService{

	@Autowired
	ServicoRepository servicoRepository;
	
	@Autowired
	ParametroServicoRepository parametroServicoRepo;

	@Autowired
	ServicoRepository servicoRepo;
	
	@Autowired
	ParametroService parametroService;
	
	@Autowired
	ParametroRepository parametroRepo;
	
	@Override
	public List<Servico> getServicos() {
		List<Servico> servicos = servicoRepository.findByStExcluidoOrderByCdServico(0L);
		return servicos;
	}
	@Override
	public List<Servico> getServicosAtivos() {
		List<Servico> servicos = servicoRepository.findByStExcluidoAndStServicoOrderByCdServico(0L, 1);
		return servicos;
	}
	@Override
	public Servico findById(long id) {
		return servicoRepository.findById(id);
	}

	@Override
	public String findNumParcelasMaximas(long idServico, String parametro) {
		return servicoRepository.findNumParcelasMaximas(idServico,parametro);
	}

	@Override
	public List<Servico> findServicoByEntidade(String cdOrg) {
		return servicoRepository.findServicoByEntidade(cdOrg);
	}

	@Override
	public void setStServiceOfService(long newSt, Servico servico) {
		servico.setStServico(newSt);
		servicoRepository.save(servico);		
	}

	@Override
	public Servico findByCdServico(String cdServico) {
		return servicoRepository.findByCdServico(cdServico);	
	}

	@Override
	public ParametroServico getParametroServicoBySiglaParametro(Servico servico, String siglaParametro) {
		return parametroServicoRepo.findByServico_idAndParametro_sgParametro(servico.getId(), siglaParametro);
	}

	@Override
	public void addRenegociarInOtherServico(long idServicoRef, String servicosAnterioresStr, String servicosNovosStr) {
		List<Long> idServicosAnteriores	= parametroService.getIdServicosFromVlParameter(servicosAnterioresStr);
		List<Long> idServicosNovos	= parametroService.getIdServicosFromVlParameter(servicosNovosStr);	
		for(Long idServicoNovo: idServicosNovos) {
			ParametroServico parametroServicoNovo = parametroServicoRepo.findByServico_idAndParametro_sgParametro(idServicoNovo, "RELRENEG");
			if(parametroServicoNovo ==null) {
				parametroServicoNovo = new ParametroServico();
				parametroServicoNovo.setParametro(parametroRepo.findBySgParametro("RELRENEG"));
				parametroServicoNovo.setServico(servicoRepo.findById(idServicoNovo));
			}
			String vlParametroServicoNovo = parametroServicoNovo.getVlParametro();
			List<Long> idServicosAux = parametroService.getIdServicosFromVlParameter(vlParametroServicoNovo);
			if(idServicosAux.contains(idServicoRef)==false) {
				idServicosAux.add(idServicoRef);
				parametroServicoNovo.setVlParametro(parametroService.getStringFromListIdServicos(idServicosAux));
				parametroServicoRepo.save(parametroServicoNovo);
			}			
		}
		for(Long idServicoAnterior: idServicosAnteriores) {
			if(idServicosNovos.contains(idServicoAnterior))
				continue;
			//servico removido
			ParametroServico parametroServicoAnterior = parametroServicoRepo.findByServico_idAndParametro_sgParametro(idServicoAnterior, "RELRENEG");
			if(parametroServicoAnterior==null)
				continue;
			String vlParametroServicoNovo = parametroServicoAnterior.getVlParametro();
			List<Long> idServicosAux = parametroService.getIdServicosFromVlParameter(vlParametroServicoNovo);
			if(idServicosAux.contains(idServicoRef)) {
				idServicosAux.remove(idServicoRef);
				parametroServicoAnterior.setVlParametro(parametroService.getStringFromListIdServicos(idServicosAux));
				parametroServicoRepo.save(parametroServicoAnterior);
			}
		}
	}

	@Override
	public Servico alterarServico(ServicoDTO servicoDTO) {
		Servico servico = servicoRepo.findById(Long.parseLong(servicoDTO.getId()));
		servico.setDsServico(Strings.toUpperCase(servicoDTO.getDsServico()));
		servico.setNrPrioridade(servicoDTO.getNrPrioridade());
		servico.setCdServico(Strings.toUpperCase(servicoDTO.getCdServico()));
		servico.setNaturezaServico(new NaturezaServico(servicoDTO.getIdNaturezaServico()));
		
		List<ParametroServico> parametrosServico = new ArrayList<ParametroServico>();
		
		List<ParametroServico> allParametroServico = servico.getParametroServicos();
		ParametroServico foundParametroServico;
		
		for (ParametroServico parametroServico : servicoDTO.getParametrosServico()) {
			foundParametroServico = allParametroServico.stream().filter(x -> parametroServico.getParametro().getId() == x.getParametro().getId()).findAny().orElse(null);
			
			if (foundParametroServico == null) {
				foundParametroServico = new ParametroServico();
				foundParametroServico.setParametro(parametroServico.getParametro());
				foundParametroServico.setServico(servico);
			}
			Parametro parametro= parametroService.getParametroByIdParametro(parametroServico.getParametro().getId());
			String sgParametro = parametro.getSgParametro();
			if(sgParametro!= null && sgParametro.equals("RELRENEG"))
				addRenegociarInOtherServico(foundParametroServico.getServico().getId(), foundParametroServico.getVlParametro(), parametroServico.getVlParametro());
			foundParametroServico.setVlParametro(parametroServico.getVlParametro());
			parametrosServico.add(foundParametroServico);
		}			
		servico.setParametroServicos(parametrosServico);
		servicoRepo.save(servico);			
		return servico;
	}
}
