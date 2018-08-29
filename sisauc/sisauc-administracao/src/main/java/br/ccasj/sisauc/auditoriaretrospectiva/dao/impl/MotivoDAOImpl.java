package br.ccasj.sisauc.auditoriaretrospectiva.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.auditoriaretrospectiva.dao.MotivoDAO;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.Motivo;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.MotivoUtilizacao;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@MappedSuperclass
@Transactional
@Repository(value = "motivoDAO")
@NamedQueries({
	@NamedQuery(name = MotivoDAOImpl.LISTAR_MOTIVOS_AUDITORIA_RETROSPECTIVA, query = MotivoDAOImpl.LISTAR_MOTIVOS_AUDITORIA_RETROSPECTIVA),
	@NamedQuery(name = MotivoDAOImpl.OBTER_HABILITADOS_AUDITORIA_RETROSPECTIVA, query = MotivoDAOImpl.OBTER_HABILITADOS_AUDITORIA_RETROSPECTIVA),
	@NamedQuery(name = MotivoDAOImpl.OBTER_NAO_HABILITADOS_AUDITORIA_RETROSPECTIVA, query = MotivoDAOImpl.OBTER_NAO_HABILITADOS_AUDITORIA_RETROSPECTIVA),
	@NamedQuery(name = MotivoDAOImpl.LISTAR_MOTIVOS_POR_UTILIZACAO, query = MotivoDAOImpl.LISTAR_MOTIVOS_POR_UTILIZACAO)
})
public class MotivoDAOImpl  extends GenericEntityDAOImpl<Motivo> implements MotivoDAO {

	private static final long serialVersionUID = -4426820780198747861L;
	
	public static final String LISTAR_MOTIVOS_AUDITORIA_RETROSPECTIVA = "SELECT new Motivo(m.id, m.grupo, m.codigo, m.descricao) from Motivo m where m.disponivelRetrospectiva is true and m.habilitadoRetrospectiva is true and m.ativo is true order by m.grupo, m.codigo, m.descricao";
	public static final String OBTER_HABILITADOS_AUDITORIA_RETROSPECTIVA = "from Motivo where disponivelRetrospectiva is true and habilitadoRetrospectiva is true and ativo is true order by grupo, codigo, descricao";
	public static final String OBTER_NAO_HABILITADOS_AUDITORIA_RETROSPECTIVA = "from Motivo where disponivelRetrospectiva is true and habilitadoRetrospectiva is false and ativo is true order by grupo, codigo, descricao";
	public static final String LISTAR_MOTIVOS_POR_UTILIZACAO = "select new br.ccasj.sisauc.auditoriaretrospectiva.domain.MotivoUtilizacao(a.motivo.id, count(a.motivo.id)) from AuditoriaRetrospectiva a where a.motivo.id is not null group by a.motivo.id order by count(a.motivo.id) desc";
	
	@Override
	public List<Motivo> obterListaMotivos() {
		List<Motivo> motivos = entityManager.createNamedQuery(LISTAR_MOTIVOS_AUDITORIA_RETROSPECTIVA, Motivo.class).getResultList();
		motivos = ordenarPorMaisUtilizados(motivos);
		return motivos;
	}

	private List<Motivo> ordenarPorMaisUtilizados(List<Motivo> motivos) {
		List<MotivoUtilizacao> motivosUtilizacao = (List<MotivoUtilizacao>) entityManager.createNamedQuery(
				LISTAR_MOTIVOS_POR_UTILIZACAO, MotivoUtilizacao.class).getResultList();
		List<Motivo> motivosOrdenado = new ArrayList<Motivo>();
		for (MotivoUtilizacao motivoUtilizacao: motivosUtilizacao){
			for (Motivo motivo: motivos){
				if (motivoUtilizacao.getId().equals(motivo.getId())){
					motivosOrdenado.add(motivo);
					break;
				}
			}
		}
		for (Motivo motivo: motivos){
			if (!motivosOrdenado.contains(motivo)){
				motivosOrdenado.add(motivo);
			}
		}
		return motivosOrdenado;
	}

	@Override
	public List<Motivo> obterHabilitados() {
		return entityManager.createNamedQuery(OBTER_HABILITADOS_AUDITORIA_RETROSPECTIVA, Motivo.class).getResultList();
	}

	@Override
	public List<Motivo> obterNaoHabilitados() {
		return entityManager.createNamedQuery(OBTER_NAO_HABILITADOS_AUDITORIA_RETROSPECTIVA, Motivo.class).getResultList();
	}
	
}
