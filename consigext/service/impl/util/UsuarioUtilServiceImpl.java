package br.mil.fab.consigext.service.impl.util;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import br.mil.fab.consigext.config.ApisProperties;
import br.mil.fab.consigext.config.Message;
import br.mil.fab.consigext.dto.MargemApiDTO;
import br.mil.fab.consigext.entity.PesfisComgep;
import br.mil.fab.consigext.entity.ServidorConsig;
import br.mil.fab.consigext.entity.Usuario;
import br.mil.fab.consigext.entity.UsuarioConsig;
import br.mil.fab.consigext.entity.UsuarioExterno;
import br.mil.fab.consigext.enums.Restriction;
import br.mil.fab.consigext.enums.StatusAcesso;
import br.mil.fab.consigext.repository.PerfisComgepRepository;
import br.mil.fab.consigext.repository.ServidorConsigRepository;
import br.mil.fab.consigext.repository.UsuarioExternoRepository;
import br.mil.fab.consigext.repository.UsuarioRepository;
import br.mil.fab.consigext.service.util.UsuarioUtilService;
import br.mil.fab.consigext.util.GenericUtil;
import br.mil.fab.consigext.util.UsuarioLogado;
import br.mil.fab.util.sigpes.entity.PesfisComgepResponse;
import br.mil.fab.util.sigpes.entity.UsuarioExternoResponse;
import br.mil.fab.util.sigpes.util.PessoaUtil;

@Service(value="usuarioUtilService")
public class UsuarioUtilServiceImpl implements UsuarioUtilService {

	@Autowired
	Message msg;
	
	@Autowired
	ServidorConsigRepository servidorRepo;
	
	@Autowired
	PerfisComgepRepository pesfisRepo;
	
	private PessoaUtil pessoaUtil;
	
	@Autowired
	private ApisProperties props;
	
	@Autowired
	UsuarioLogado usuarioLogado;
	
	@Autowired
	UsuarioExternoRepository usuarioExternoRepo;
	
	@Autowired
	UsuarioRepository usuarioRepo;
	
	@PostConstruct
	public void init() {
		pessoaUtil = new PessoaUtil(props.getEndpoint()+"/sigpesApi");
	}
	
	@Override
	public PesfisComgepResponse getMilitarByMatriculaOrCpf(String ordemOuCpf) {
		ordemOuCpf = ordemOuCpf.replaceAll("\\.", "");
		ordemOuCpf = ordemOuCpf.replace("-", "");
		return pessoaUtil.getMilitarByMatriculaOrCpf(ordemOuCpf);
	}

	@Override
	public UsuarioExternoResponse getFisicaByCpf(String cpf) {
		return pessoaUtil.getPessoaByCpf(cpf);
	}
	
	public PesfisComgep getPessoaConverter(String nrCpf) {
			PesfisComgepResponse resp =  getMilitarByMatriculaOrCpf(nrCpf);
			return new PesfisComgep(resp);
	}
	
	@Override
	public StatusAcesso isBlockedOrExcluded () {
		if(usuarioLogado.isServidor()) {
			PesfisComgep pessoa = pesfisRepo.findByNrCpf(usuarioLogado.getCPF());
			ServidorConsig servidor = servidorRepo.findByPesfis(pessoa);
			if(servidor.getStServidor()==1)
				return StatusAcesso.PERMITIDO;
			return StatusAcesso.SERVIDOR_SEM_ACESSO;	
		}		
		if(usuarioLogado.getUsuarioCorrente()==null )
			return StatusAcesso.SERVIDOR_SEM_ACESSO;	
		if(usuarioLogado.getUsuarioCorrente().getStExcluido()==1)
			return StatusAcesso.EXCLUIDO;
		UsuarioConsig u = usuarioLogado.getUsuarioCorrente();
		if(usuarioLogado.getUsuarioCorrente().getStUsuario()==0)
			return StatusAcesso.BLOQUEADO;
		return StatusAcesso.PERMITIDO;
	}
	public StatusAcesso isBlockedOrExcluded (String permission) {
		if(usuarioLogado.hasPermissao(permission)==false)
			return StatusAcesso.SEM_PERMISSAO;
		if(usuarioLogado.isServidor()) {
			PesfisComgep pessoa = pesfisRepo.findByNrCpf(usuarioLogado.getCPF());
			ServidorConsig servidor = servidorRepo.findByPesfis(pessoa);
			if(servidor.getStServidor()==1)
				return StatusAcesso.PERMITIDO;
			return StatusAcesso.SERVIDOR_SEM_ACESSO;	
		}		
		if(usuarioLogado.getUsuarioCorrente()==null )
			return StatusAcesso.SERVIDOR_SEM_ACESSO;	
		if(usuarioLogado.getUsuarioCorrente().getStExcluido()==1)
			return StatusAcesso.EXCLUIDO;
		UsuarioConsig u = usuarioLogado.getUsuarioCorrente();
		if(usuarioLogado.getUsuarioCorrente().getStUsuario()==0)
			return StatusAcesso.BLOQUEADO;
		return StatusAcesso.PERMITIDO;
	}
	public StatusAcesso isBlockedOrExcluded (List<String> permissions) {
		for(String permission: permissions)
			if(usuarioLogado.hasPermissao(permission)==false)
				return StatusAcesso.SEM_PERMISSAO;		
		if(usuarioLogado.isServidor()) {
			PesfisComgep pessoa = pesfisRepo.findByNrCpf(usuarioLogado.getCPF());
			ServidorConsig servidor = servidorRepo.findByPesfis(pessoa);
			if(servidor.getStServidor()==1)
				return StatusAcesso.PERMITIDO;
			return StatusAcesso.SERVIDOR_SEM_ACESSO;	
		}		
		if(usuarioLogado.getUsuarioCorrente()==null )
			return StatusAcesso.SERVIDOR_SEM_ACESSO;	
		if(usuarioLogado.getUsuarioCorrente().getStExcluido()==1)
			return StatusAcesso.EXCLUIDO;
		UsuarioConsig u = usuarioLogado.getUsuarioCorrente();
		if(usuarioLogado.getUsuarioCorrente().getStUsuario()==0)
			return StatusAcesso.BLOQUEADO;
		return StatusAcesso.PERMITIDO;
	}
	
	@Override
	public MargemApiDTO getMargemApiDTO(String nrOrdem, String cdAnoMes) {
		RestTemplate restTemplate = new RestTemplate();
		MargemApiDTO margemApi=null;
		try{
			margemApi = restTemplate.getForObject(props.getEndpoint()+"/pagamento-api/api/margens/"+nrOrdem+"/"+cdAnoMes, MargemApiDTO.class);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return margemApi;
	}
	@Override
	public String getMargem30(String nrOrdem, String cdAnoMes) {
		MargemApiDTO margem = getMargemApiDTO(nrOrdem, cdAnoMes);
		if(margem==null)
			return null;
		return GenericUtil.printNumberTwoDigits(margem.getValorMargem30());
		
	}
	@Override
	public String getMargem40(String nrOrdem, String cdAnoMes)  {
		MargemApiDTO margem = getMargemApiDTO(nrOrdem, cdAnoMes);
		if(margem==null)
			return null;
		return GenericUtil.printNumberTwoDigits(margem.getValorMargem40());
	}
	@Override
	public String getMargem70(String nrOrdem, String cdAnoMes)  {
		MargemApiDTO margem = getMargemApiDTO(nrOrdem, cdAnoMes);
		if(margem==null)
			return null;
		return GenericUtil.printNumberTwoDigits(margem.getValorMargem70());
	}
	@Override
	public String getMargem30MesAtual(String nrOrdem) {
		MargemApiDTO margem = getMargemApiDTO(nrOrdem, GenericUtil.getAAAAMMtual());
		if(margem==null)
			return null;
		return GenericUtil.printNumberTwoDigits(margem.getValorMargem30());
		
	}
	@Override
	public String getMargem40MesAtual(String nrOrdem){
		MargemApiDTO margem = getMargemApiDTO(nrOrdem, GenericUtil.getAAAAMMtual());
		if(margem==null)
			return null;
		return GenericUtil.printNumberTwoDigits(margem.getValorMargem40());
	}
	@Override
	public String getMargem70MesAtual(String nrOrdem) {
		MargemApiDTO margem = getMargemApiDTO(nrOrdem, GenericUtil.getAAAAMMtual());
		if(margem==null)
			return null;
		return GenericUtil.printNumberTwoDigits(margem.getValorMargem70());
	}
	@Override
	public StatusAcesso hasAccess (Restriction restriction, Long idConsignatariaChamada) {
		if(usuarioLogado.isServidor()) {
			PesfisComgep pessoa = pesfisRepo.findByNrCpf(usuarioLogado.getCPF());
			ServidorConsig servidor = servidorRepo.findByPesfis(pessoa);
			if(servidor.getStServidor()==0)
				return StatusAcesso.BLOQUEADO;
		}		
		if(usuarioLogado.isServidor() ==false && usuarioLogado.getUsuarioCorrente()==null )
			return StatusAcesso.SERVIDOR_SEM_ACESSO;
		long idEntConsigUsuarioCorrente =-1;
		if(usuarioLogado.getUsuarioCorrente()!=null) {
			if(usuarioLogado.getUsuarioCorrente().getStExcluido()==1)
				return StatusAcesso.EXCLUIDO;
			if(usuarioLogado.getUsuarioCorrente().getStUsuario()==0)
				return StatusAcesso.BLOQUEADO;			
			idEntConsigUsuarioCorrente = usuarioLogado.getUsuarioCorrente().getEntidadeConsig().getId();
		}
		switch(restriction) {
		case GESTOR:
			if(usuarioLogado.isGestor())
				return StatusAcesso.PERMITIDO;
			return StatusAcesso.APENAS_GESTOR;		
		case SERVIDOR:
			if(usuarioLogado.isServidor())
				return StatusAcesso.PERMITIDO;
			return StatusAcesso.APENAS_SERVIDOR;			
		case ENTIDADE_CONSIG_CORRENTE:
			if(usuarioLogado.isConsig() && idConsignatariaChamada == idEntConsigUsuarioCorrente)
				return StatusAcesso.PERMITIDO;
			if(usuarioLogado.isConsig())
				return StatusAcesso.CONSIG_DIF_CORRENTE;
			return StatusAcesso.APENAS_CONSIG_CORRENTE;				
		case GESTOR_OU_ENTIDADE_CONSIG_CORRENTE:
			if(usuarioLogado.isGestor())
				return StatusAcesso.PERMITIDO;
			if(usuarioLogado.isConsig() && idConsignatariaChamada == idEntConsigUsuarioCorrente)
				return StatusAcesso.PERMITIDO;
			if(usuarioLogado.isConsig())
				return StatusAcesso.CONSIG_DIF_CORRENTE;
			return StatusAcesso.SERVIDOR_SEM_ACESSO;
		case QUALQUER_ENTIDADE_CONSIG:
			if(usuarioLogado.isConsig())
				return StatusAcesso.PERMITIDO;
			return StatusAcesso.APENAS_CONSIG;
		case GESTOR_OU_QUALQUER_ENTIDADE_CONSIG:
			if(usuarioLogado.isGestor())
				return StatusAcesso.PERMITIDO;
			if(usuarioLogado.isConsig())
				return StatusAcesso.PERMITIDO;
			return StatusAcesso.SERVIDOR_SEM_ACESSO;
		case TODOS_USUARIOS:
			return StatusAcesso.PERMITIDO;
		}	
		return null;
	}
	@Override
	public StatusAcesso hasAccess (String permission, Restriction restriction, Long idConsignatariaChamada) {
		if(usuarioLogado.hasPermissao(permission)==false)
			return StatusAcesso.SEM_PERMISSAO;
		return hasAccess(restriction, idConsignatariaChamada);
	}
	@Override
	public StatusAcesso hasAccess (List<String> permissions, Restriction restriction, Long idConsignatariaChamada) {
		for(String permission: permissions)
			if(usuarioLogado.hasPermissao(permission)==false)
				return StatusAcesso.SEM_PERMISSAO;		
		return hasAccess(restriction, idConsignatariaChamada);
	}	
	@Override
	public boolean isPessoaFab(String cpf) {
		return getMilitarByMatriculaOrCpf(cpf).getNrCpf() != null;
	}

	@Override
	public UsuarioExterno findUsuarioExternoByNrCpf(String cpf) {
		
		return usuarioExternoRepo.findByNrCpf(cpf);
	}

	@Override
	public Usuario findUsuarioByNrCpf(String cpf) {
		return usuarioRepo.findByNrCpf(cpf);
	}
}
