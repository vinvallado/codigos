package br.ccasj.sisauc.administracao.sistema.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.ccasj.sisauc.auditoriaretrospectiva.domain.Motivo;

@MappedSuperclass
@Transactional
@Service(value = "configuracaoMensagensService")
@NamedQueries({
	@NamedQuery(name = ConfiguracaoMensagensServiceImpl.DESABILITAR_MENSAGENS, query = ConfiguracaoMensagensServiceImpl.DESABILITAR_MENSAGENS),
	@NamedQuery(name = ConfiguracaoMensagensServiceImpl.HABILITAR_MENSAGENS, query = ConfiguracaoMensagensServiceImpl.HABILITAR_MENSAGENS)
})
public class ConfiguracaoMensagensServiceImpl implements ConfiguracaoMensagensService {

	@PersistenceContext
	private EntityManager entityManager;
	
	public static final String DESABILITAR_MENSAGENS = "update Motivo m set m.habilitadoRetrospectiva = false where m in :naoHabilidatos";
	public static final String HABILITAR_MENSAGENS = "update Motivo m set m.habilitadoRetrospectiva = true where m in :habilidatos";
	
	@Override
	public void salvar(List<Motivo> naoHabilitados, List<Motivo> habilitados) {
		if(naoHabilitados != null && !naoHabilitados.isEmpty()){
			entityManager.createNamedQuery(DESABILITAR_MENSAGENS).setParameter("naoHabilidatos", naoHabilitados).executeUpdate();
		}
		if(habilitados != null && !habilitados.isEmpty()){
			entityManager.createNamedQuery(HABILITAR_MENSAGENS).setParameter("habilidatos", habilitados).executeUpdate();
		}
	}

}
