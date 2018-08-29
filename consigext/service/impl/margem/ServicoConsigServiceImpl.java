package br.mil.fab.consigext.service.impl.margem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mil.fab.consigext.entity.EntidadeConsig;
import br.mil.fab.consigext.entity.ParametroServicoConsig;
import br.mil.fab.consigext.entity.Servico;
import br.mil.fab.consigext.entity.ServicoConsig;
import br.mil.fab.consigext.repository.EntidadeConsigRepository;
import br.mil.fab.consigext.repository.ParametroServicoConsigRepository;
import br.mil.fab.consigext.repository.ServicoConsigRepository;
import br.mil.fab.consigext.repository.ServicoRepository;
import br.mil.fab.consigext.service.margem.ServicoConsigService;
import br.mil.fab.consigext.service.margem.ServicoService;
import br.mil.fab.consigext.service.parametro.ParametroService;
import br.mil.fab.consigext.service.util.UsuarioUtilService;

@Service (value="servicoConsigService")
public  class ServicoConsigServiceImpl implements ServicoConsigService{
	
	@Autowired
	ParametroService parametroService;
	
	@Autowired
	ParametroServicoConsigRepository parametroServicoConsigRepo;
	
	@Autowired
	EntidadeConsigRepository entidadeConsigRepo;
	
	@Autowired
	ServicoRepository servicoRepo;
	
	@Autowired
	ServicoService servicoService;
	
	@Autowired
	ServicoConsigRepository servicoConsigRepo;
	
	@Autowired
	ServicoConsigService servicoConsigService;
	
	@Autowired
	UsuarioUtilService usuarioUtilService;
	
	@Override
	public String retornaValorMargemServico(Long idEntidade, Long idServico, String sigla) {
		return servicoConsigRepo.retornaValorMargemServico(idEntidade, idServico, sigla);
	}

	@Override
	public ServicoConsig findById(Long id) {
		return servicoConsigRepo.findById(id);
	}

	@Override
	public ServicoConsig findByServicoAndEntidadeConsig(Servico servico, EntidadeConsig entidadeConsig) {
		return servicoConsigRepo.findByServicoAndEntidadeConsig(servico, entidadeConsig);
	}
	@Override
	public void setStServiceOfServiceConsig(long newSt, ServicoConsig servicoConsig) {
		servicoConsig.setStServico(newSt);
		servicoConsigRepo.save(servicoConsig);
	}

	@Override
	public String retornaValMargem30Servidor(String matricula) {
		return usuarioUtilService.getMargem30MesAtual(matricula);
	}
	
	@Override
	public boolean isServicoBlockedOrExcludedToConsig(Long idConsignataria, Long idServico) {
		EntidadeConsig entidade = entidadeConsigRepo.findById(idConsignataria);
		Servico servico = servicoRepo.findById(idServico);
		ServicoConsig servicoConsig = servicoConsigRepo.findByServicoAndEntidadeConsig(servico, entidade);
		if(servico.getStExcluido()==1)
			return true;
		if(servico.getStServico()==0)
			return true;
		if(servicoConsig!= null && servicoConsig.getStServico()==0)
			return true;
		return false;
	}
	
	@Override
	public ParametroServicoConsig getParametroServicoConsigsBySiglaParametro(ServicoConsig servicoConsig, String siglaParametro) {
		return parametroServicoConsigRepo.findByServicoConsigAndParametro_SgParametro(servicoConsig, siglaParametro);
	}

	@Override
	public List<ServicoConsig> getServicosConsigWithCet(EntidadeConsig entidadeConsig) {
		List<ServicoConsig> servicosConsigWithCet = new ArrayList<ServicoConsig>();
		List<ServicoConsig> servicosConsig = entidadeConsig.getServicoConsigs();
		for (ServicoConsig sc : servicosConsig)
			if (parametroService.findByServicoConsigSiglaParametro(sc, "EXCET") != null
					&& parametroService.findByServicoConsigSiglaParametro(sc, "EXCET").equals("1"))
				servicosConsigWithCet.add(sc);
		return servicosConsigWithCet;
	}

	@Override
	public List<ServicoConsig> getServicosRenegociacao(ServicoConsig servicoConsig) {
		List<ServicoConsig> servicosConsig = new ArrayList<ServicoConsig>();
		
		servicosConsig = servicoConsigRepo.findByEntidadeConsig(servicoConsig.getEntidadeConsig());
		
		String relreneg = parametroService.findByServicoConsigSiglaParametro(servicoConsig, "RELRENEG");
		
		if (relreneg == null) {
			servicosConsig.add(servicoConsig);
			return servicosConsig;
		}
		
		return servicosConsig;
	}

	@Override
	public List<Servico> getServicosAtivosParaConsig(EntidadeConsig entidadeConsig) {
		List<Servico> servicosAtivos = servicoService.getServicosAtivos();
		List<Servico> servicosParaExcluir = new ArrayList<Servico>();
		for(Servico s: servicosAtivos) {
			if(servicoConsigService.isServicoBlockedOrExcludedToConsig(entidadeConsig.getId(), s.getId()))
				servicosParaExcluir.add(s);
		}
		for(Servico s: servicosParaExcluir)
			servicosAtivos.remove(s);
		return servicosAtivos;
	}

}
