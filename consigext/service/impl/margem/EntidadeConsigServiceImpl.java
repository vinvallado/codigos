package br.mil.fab.consigext.service.impl.margem;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.mil.fab.consigext.entity.EntidadeConsig;
import br.mil.fab.consigext.entity.HistoricoEc;
import br.mil.fab.consigext.entity.UsuarioConsig;
import br.mil.fab.consigext.enums.TipoHistoricoEc;
import br.mil.fab.consigext.repository.EntidadeConsigRepository;
import br.mil.fab.consigext.repository.HistoricoEcRepository;
import br.mil.fab.consigext.repository.UsuarioConsignatariaRepository;
import br.mil.fab.consigext.service.margem.EntidadeConsigService;
import br.mil.fab.consigext.service.util.UsuarioUtilService;
import br.mil.fab.consigext.util.UsuarioLogado;

@Service
public class EntidadeConsigServiceImpl implements EntidadeConsigService{

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	@Autowired
	UsuarioLogado usuarioLogado;
	
	@Autowired
    HistoricoEcRepository historicoEcRepository;
	
	@Autowired
	EntidadeConsigRepository entidadeConsigRepository;
	
	@Autowired
	EntidadeConsigService entidadeConsigService;
	
	@Autowired
	UsuarioUtilService usrUtilService;
	
	@Autowired
	UsuarioConsignatariaRepository usuarioConsigRepository;
	

	@Override
	public List<EntidadeConsig> findAll() {
		List<EntidadeConsig> consignatarias = entidadeConsigRepository.findAllIgnoreCaseByOrderByCdEntidade();
		return consignatarias;
	}

	@Override
	public EntidadeConsig findById(Long id) {
		return entidadeConsigRepository.findById(id);
	}

	@Override
	public String findByCdEntidade() {
		return entidadeConsigRepository.findByCdEntidade();
	}
	
	public EntidadeConsig getEntidadeConsig(String id) {
		EntidadeConsig entidadeConsig = new EntidadeConsig();
		entidadeConsig = entidadeConsigRepository.findById(Long.valueOf(id));
		
		return entidadeConsig;
		
	}
	
	@Override
	public void setStEntidadeOfConsig(long newSt, EntidadeConsig consig) {
		if(consig.getStEntidade().equals(newSt))
			return;
		consig.setStEntidade(newSt);
		entidadeConsigRepository.save(consig);
		TipoHistoricoEc tipoHistoricoEc = null;
		if(newSt == 1) tipoHistoricoEc = TipoHistoricoEc.DESBLOQUEIO_MANUAL;
		if(newSt == 0) tipoHistoricoEc = TipoHistoricoEc.BLOQUEIO_MANUAL;
		String ip;
		try {ip=usuarioLogado.obterIP();}
		catch(Exception e) {ip=""; e.printStackTrace();}	
        HistoricoEc historicoEc = new HistoricoEc(new Date(), ip, tipoHistoricoEc, consig, usuarioLogado.getCPF(), tipoHistoricoEc.toString());
        historicoEcRepository.save(historicoEc);
	}
	
	public List<UsuarioConsig> getUsuarioConsigPorEntidadeConsig(EntidadeConsig entidadeConsig) {
		 List<UsuarioConsig> usuarioConsig = usuarioConsigRepository.findByEntidadeConsig(entidadeConsig);
		/*if(usuarioConsig.getTpUsuario() == 0){
			usuarioConsig.setPesfis(usrUtilService.getPessoaConverter(usuarioConsig.getNrCpf()));
		}*/
		return usuarioConsig;
		
	}

	@Override
	public void changeStEntidadeOfConsig(long idConsig) {
		EntidadeConsig consig = entidadeConsigService.findById(idConsig);
		long st = consig.getStEntidade();
		entidadeConsigService.setStEntidadeOfConsig(++st%2, consig);					
	}

	
}
