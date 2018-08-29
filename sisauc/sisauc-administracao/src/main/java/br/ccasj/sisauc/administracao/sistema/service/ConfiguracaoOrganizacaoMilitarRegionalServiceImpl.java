package br.ccasj.sisauc.administracao.sistema.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.OrganizacaoMilitarRegionalDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.OrganizacaoMilitarSubordinadaDAO;
import br.ccasj.sisauc.administracao.sistema.domain.OrganizacaoMilitarRegional;
import br.ccasj.sisauc.administracao.sistema.domain.OrganizacaoMilitarSubordinada;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;


@Transactional
@Service(value = "configuracaoOrganizacaoMilitarRegionalService")
public class ConfiguracaoOrganizacaoMilitarRegionalServiceImpl implements ConfiguracaoOrganizacaoMilitarRegionalService {

	private static final long serialVersionUID = -2746009140022389425L;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private OrganizacaoMilitarSubordinadaDAO organizacaoMilitarSubordinadaDAO;
	@Autowired
	private OrganizacaoMilitarRegionalDAO organizacaoMilitarRegionalDAO;
		
	@Override
	public void salvar(List<OrganizacaoMilitarSubordinada> subordinadasSemRegional,
			List<OrganizacaoMilitarSubordinada> subordinadas, OrganizacaoMilitarRegional regional) {
		
		validarExclusaoSubordinada(subordinadasSemRegional);
		
		if (!subordinadas.isEmpty()) {
			organizacaoMilitarSubordinadaDAO.salvarSubordinadasComRegionalSelecionada(subordinadas, regional);
		}
		if (!subordinadasSemRegional.isEmpty()) {
			organizacaoMilitarSubordinadaDAO.salvarSubordinadasSemRegional(subordinadasSemRegional);
		}
	}
	
	public void validarExclusaoSubordinada(List<OrganizacaoMilitarSubordinada> subordinadasSemRegional){
		List<Integer> idsSubordinadas = new ArrayList<Integer>();
		for (OrganizacaoMilitarSubordinada organizacaoMilitarSubordinada : subordinadasSemRegional) {
			idsSubordinadas.add(organizacaoMilitarSubordinada.getId());
		}
		if(organizacaoMilitarSubordinadaDAO.validarExclusaoSubordinada(idsSubordinadas)){
			throw new SystemRuntimeException("Impossível remover Organização Militar pois a mesma possui vínculo com usuários ativos");
		}
	}
	
	public List<Integer> gerarListaDeIds (List<OrganizacaoMilitarSubordinada> subordinadas) {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for (OrganizacaoMilitarSubordinada organizacaoMilitarSubordinada : subordinadas) {
			ids.add(organizacaoMilitarSubordinada.getId());
		}
		return ids;
	}

	//TODO MENSAGEM DE ERRO NÃO APARECE NA TELA
	@Override
	public OrganizacaoMilitarRegional obterRegionalSistema() {
		boolean validacao = organizacaoMilitarRegionalDAO.validarRegionalSistema();
		if (validacao) {
			return organizacaoMilitarRegionalDAO.obterRegionalSistema();
		} else {
			throw new SystemRuntimeException("Problema com a Configuração de Regional! Entre em contato com o Administrador do Sistema.");
		}
	}
	
	@Override
	public void definirRegionalSistema(Integer idOmRegional) {
		String queryRegional = "UPDATE OrganizacaoMilitarRegional set regionalSistema = true where id = :id";
		String queryNaoRegional = "UPDATE OrganizacaoMilitarRegional set regionalSistema = false where id <> :id";
		entityManager.createQuery(queryRegional).setParameter("id", idOmRegional).executeUpdate();
		entityManager.createQuery(queryNaoRegional).setParameter("id", idOmRegional).executeUpdate();
	}
	
}
