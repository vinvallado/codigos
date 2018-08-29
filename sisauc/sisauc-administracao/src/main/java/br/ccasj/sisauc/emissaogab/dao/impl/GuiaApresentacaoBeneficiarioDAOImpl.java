package br.ccasj.sisauc.emissaogab.dao.impl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.auditoriaretrospectiva.domain.AuditoriaRetrospectiva;
import br.ccasj.sisauc.emissaogab.dao.GuiaApresentacaoBeneficiarioDAO;
import br.ccasj.sisauc.emissaogab.domain.EstadoGAB;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.emissaogab.domain.MetadadoValorItemGAB;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;
import br.ccasj.sisauc.framework.domain.Usuario;

@MappedSuperclass
@Transactional
@Repository(value = "guiaApresentacaoBeneficiarioDAO")
@NamedQueries({
	@NamedQuery(name = "GAB_OBTER_QUANTIDADE_GABS_POR_OM_E_ANO", query = GuiaApresentacaoBeneficiarioDAOImpl.OBTER_QUANTIDADE_GABS_POR_OM_E_ANO),
	@NamedQuery(name = "GAB_OBTER_GABS_POR_OM", query = GuiaApresentacaoBeneficiarioDAOImpl.OBTER_GABS_POR_OM),
	@NamedQuery(name = "GAB_ATUALIZAR_ESTADO_GAB_POR_ID", query = GuiaApresentacaoBeneficiarioDAOImpl.ATUALIZAR_ESTADO_GAB_POR_ID),
	@NamedQuery(name = "GAB_ATUALIZAR_DATA_GAB_POR_ID", query = GuiaApresentacaoBeneficiarioDAOImpl.ATUALIZAR_DATA_GAB_POR_ID),
	@NamedQuery(name = "GAB_PERSISTIR_USUARIO", query = GuiaApresentacaoBeneficiarioDAOImpl.PERSISTIR_USUARIO),
	@NamedQuery(name = "GAB_CANCELAR_GAB_POR_ID", query = GuiaApresentacaoBeneficiarioDAOImpl.CANCELAR_GAB_POR_ID),
	@NamedQuery(name = "GAB_CANCELAR_ITEM_GAB_POR_ID_GAB", query = GuiaApresentacaoBeneficiarioDAOImpl.CANCELAR_ITEM_GAB_POR_ID_GAB),
	@NamedQuery(name = "GAB_OBTER_GAB_POR_ITEM_GAB_ID", query = GuiaApresentacaoBeneficiarioDAOImpl.OBTER_GAB_POR_ITEM_GAB_ID),
	@NamedQuery(name = "GAB_OBTER_GABS_POR_ESTADOS", query = GuiaApresentacaoBeneficiarioDAOImpl.OBTER_GABS_POR_ESTADOS),
	@NamedQuery(name = "GAB_OBTER_POR_AUDITORIA_PROSPECTIVA", query = GuiaApresentacaoBeneficiarioDAOImpl.OBTER_POR_AUDITORIA_PROSPECTIVA),
	@NamedQuery(name = "GAB_ATUALIZAR_ESTADO_EM_AUDITORIA", query = GuiaApresentacaoBeneficiarioDAOImpl.ATUALIZAR_ESTADO_EM_AUDITORIA),
	@NamedQuery(name = "GAB_ATUALIZAR_ESTADO_AUDITADA", query = GuiaApresentacaoBeneficiarioDAOImpl.ATUALIZAR_ESTADO_AUDITADA),
	@NamedQuery(name = "GAB_OBTER_GAB_POR_CODIGO", query = GuiaApresentacaoBeneficiarioDAOImpl.OBTER_GAB_POR_CODIGO)
})
public class GuiaApresentacaoBeneficiarioDAOImpl extends GenericEntityDAOImpl<GuiaApresentacaoBeneficiario> implements GuiaApresentacaoBeneficiarioDAO {
	
	private static final long serialVersionUID = 3455708952997798058L;

	public static final String OBTER_QUANTIDADE_GABS_POR_OM_E_ANO = "SELECT COUNT(id) FROM GuiaApresentacaoBeneficiario WHERE YEAR(dataGeracao) = :ano AND organizacaoMilitar.id = :idOm";
	public static final String OBTER_GABS_POR_OM = "SELECT new GuiaApresentacaoBeneficiario(gab.id, gab.divisao, gab.codigo, gab.dataGeracao, gab.dataEmissao, gab.estado, b.nome, b.saram, beneficiarioTitular.saram, c.nomeFantasia, auditoria.id, solicitacao.urgente) "
			+ "from GuiaApresentacaoBeneficiario gab "
			+ "left join gab.beneficiario as b "
			+ "left join b.beneficiarioTitular as beneficiarioTitular "
			+ "left join gab.credenciado as c "
			+ "left join gab.organizacaoMilitar as om "
			+ "left join gab.auditoriaProspectiva as auditoria "
			+ "left join auditoria.solicitacao as solicitacao "
			+ "where om.id = :idOm order by solicitacao.urgente desc, gab.dataGeracao";
	public static final String ATUALIZAR_ESTADO_GAB_POR_ID = "UPDATE GuiaApresentacaoBeneficiario SET estado = :estado where id = :id";
	public static final String ATUALIZAR_DATA_GAB_POR_ID = "UPDATE GuiaApresentacaoBeneficiario SET dataEmissao = :data where id = :id";
	public static final String PERSISTIR_USUARIO = "UPDATE GuiaApresentacaoBeneficiario SET usuarioEmissorGab = :usuario where id = :id";
	public static final String CANCELAR_GAB_POR_ID = "UPDATE GuiaApresentacaoBeneficiario SET estado = 'CANCELADA', justificativaCancelamentoGab = :texto where id = :id";
	public static final String CANCELAR_ITEM_GAB_POR_ID_GAB = "UPDATE ItemGAB SET estado = 'CANCELADO' where gab.id = :id";
	public static final String OBTER_GABS_POR_ESTADOS = "SELECT new GuiaApresentacaoBeneficiario(gab.id, gab.divisao, gab.codigo, gab.dataGeracao, gab.dataEmissao, b.nome, c.nomeFantasia, gab.estado) from GuiaApresentacaoBeneficiario gab "
			+ "left join gab.beneficiario as b left join gab.credenciado as c left join gab.organizacaoMilitar as om  where gab.estado in (:estadosGAB)";
	public static final String OBTER_GAB_POR_ITEM_GAB_ID = "SELECT gab "
			+ "FROM GuiaApresentacaoBeneficiario gab LEFT JOIN gab.itens AS i LEFT JOIN gab.credenciado AS c WHERE i.id=:idItemGAB";
	public static final String OBTER_POR_AUDITORIA_PROSPECTIVA = " select distinct gab from GuiaApresentacaoBeneficiario gab left join fetch gab.itens "
			+ "where gab.auditoriaProspectiva.id = :idAuditoria order by gab.codigo ";
	public static final String ATUALIZAR_ESTADO_EM_AUDITORIA = "UPDATE GuiaApresentacaoBeneficiario SET estado = 'EM_AUDITORIA' where id = :id and "
			+ "(select count(*) from ItemGAB where gab.id = :id and estado in ('AUDITORIA_INICIADA', 'NAO_CONFORME', 'CONFORME')) > 0 and "
			+ "(select count(*) from ItemGAB where gab.id = :id ) > "
			+ "(select count(*) from ItemGAB where gab.id = :id and estado in ('NAO_REALIZADO','CONFORME'))";
	
	public static final String ATUALIZAR_ESTADO_AUDITADA = "UPDATE GuiaApresentacaoBeneficiario SET estado = 'AUDITADA' where id = :id and "
			+ "(select count(*) from ItemGAB where gab.id = :id ) = "
			+ "(select count(*) from ItemGAB where gab.id = :id and estado in ('CONFORME','NAO_REALIZADO'))";
	
	public static final String OBTER_GAB_POR_CODIGO = "select new GuiaApresentacaoBeneficiario (gab.id, gab.divisao, gab.codigo, gab.dataGeracao, gab.estado, beneficiario.nome, beneficiario.saram, beneficiarioTitular.saram)"
			+ " FROM GuiaApresentacaoBeneficiario gab"
			+ " LEFT JOIN gab.beneficiario as beneficiario"
			+ " LEFT JOIN beneficiario.beneficiarioTitular as beneficiarioTitular"
			+ " WHERE gab.codigo LIKE :codigo";
		
	
	@Override
	public List<GuiaApresentacaoBeneficiario> obterGABsPorAuditoriaProspectiva(Integer idAuditoria) {
		return entityManager.createNamedQuery("GAB_OBTER_POR_AUDITORIA_PROSPECTIVA", GuiaApresentacaoBeneficiario.class).setParameter("idAuditoria", idAuditoria).getResultList();
	}
	
	@Override
	public int obterQuantidadeGABSPorOrganizacaoMilitarEAno(Integer idOm, Integer ano) {
		return entityManager.createNamedQuery("GAB_OBTER_QUANTIDADE_GABS_POR_OM_E_ANO", Long.class).setParameter("ano", ano).setParameter("idOm", idOm).getSingleResult().intValue();
	}
	
	@Override
	public List<GuiaApresentacaoBeneficiario> obterGABsPorOrganizacaoMilitar(Integer idOm) {
		return entityManager.createNamedQuery("GAB_OBTER_GABS_POR_OM", GuiaApresentacaoBeneficiario.class).setParameter("idOm",idOm).getResultList();
	}
	
	@Override
	public GuiaApresentacaoBeneficiario obterComItensPorId(Integer id) {
		GuiaApresentacaoBeneficiario gab = findById(id);
		Hibernate.initialize(gab.getItens());
		gab.setItens(ordenarItensPorCodigo(gab.getItens()));
		return gab;
	}
	
	private Set<ItemGAB> ordenarItensPorCodigo(Set<ItemGAB> itens) {
		Set<ItemGAB> itensSorted = new TreeSet<ItemGAB>(new Comparator<ItemGAB>() {
			@Override
			public int compare(ItemGAB o1, ItemGAB o2) {
				return o1.getCodigo().substring(o1.getCodigo().length() - 3)
						.compareTo(o2.getCodigo().substring(o2.getCodigo().length() - 3));
			}
		});
		itensSorted.addAll(itens);
		return itensSorted;
	}

	@Override
	public GuiaApresentacaoBeneficiario obterComItensMetadadosPorId(Integer id) {
		GuiaApresentacaoBeneficiario gab = obterComItensPorId(id);
		Iterator<ItemGAB> itens = gab.getItens().iterator();
		while (itens.hasNext()){
			ItemGAB item = itens.next();
			Hibernate.initialize(item.getValores());
			AuditoriaRetrospectiva auditoria = item.getAuditoriaRetrospectiva();
			if (auditoria != null && auditoria.getValores()!=null) {
				Hibernate.initialize(auditoria.getValores()); 
			}
		}
		return gab;
	}
	
	@Override
	public void atualizarEstadoGAB(EstadoGAB estado, Integer id) {
		entityManager.createNamedQuery("GAB_ATUALIZAR_ESTADO_GAB_POR_ID").setParameter("estado", estado).setParameter("id", id).executeUpdate();
	}
	
	@Override
	public void atualizarDataGAB(Date data, Integer id) {
		entityManager.createNamedQuery("GAB_ATUALIZAR_DATA_GAB_POR_ID").setParameter("data", data).setParameter("id", id).executeUpdate();
	}
	
	@Override
	public void atualizarUsuarioEmissor(Usuario usuario, Integer id) {
		entityManager.createNamedQuery("GAB_PERSISTIR_USUARIO").setParameter("usuario", usuario).setParameter("id", id).executeUpdate();
	}
	
	@Override
	public void cancelarGAB(Integer id, String justificativa) {
		entityManager.createNamedQuery("GAB_CANCELAR_GAB_POR_ID").setParameter("texto", justificativa).setParameter("id", id).executeUpdate();
		entityManager.createNamedQuery("GAB_CANCELAR_ITEM_GAB_POR_ID_GAB").setParameter("id", id).executeUpdate();
	}
	
	@Override
	public GuiaApresentacaoBeneficiario merge(GuiaApresentacaoBeneficiario object) {
		for (ItemGAB item : object.getItens()) {
			if(item.getId() < 0){
				item.setId(null);
				item.setGab(object);
			}
			for (MetadadoValorItemGAB metadado : item.getValores()) {
				if(metadado.getId() < 0){
					metadado.setId(null);
				}
			}
		}
		return super.merge(object);
	}

	@Override
	public List<GuiaApresentacaoBeneficiario> obterGABsEmitidas() {
		return obterGABsPorEstados(EstadoGAB.EMITIDA);
	}
	
	@Override
	public List<GuiaApresentacaoBeneficiario> obterGABsApresentadasEEmAuditoria() {
		return obterGABsPorEstados(EstadoGAB.APRESENTADA, EstadoGAB.EM_AUDITORIA);
	}
	
	@Override
	public List<GuiaApresentacaoBeneficiario> obterGABsPorEstados(EstadoGAB... estadosGAB) {
		return entityManager.createNamedQuery("GAB_OBTER_GABS_POR_ESTADOS", GuiaApresentacaoBeneficiario.class).setParameter("estadosGAB", Arrays.asList(estadosGAB)).getResultList();
	}
	
	public GuiaApresentacaoBeneficiario obterPeloItemGABId(Integer idItemGAB) {
		GuiaApresentacaoBeneficiario gab = entityManager.createNamedQuery("GAB_OBTER_GAB_POR_ITEM_GAB_ID", GuiaApresentacaoBeneficiario.class).setParameter("idItemGAB", idItemGAB).getSingleResult();
		Hibernate.initialize(gab.getItens());
		return gab;
	}

	@Override
	public void atualizarEstadoGABAoRealizarAuditoriaRetrospectiva(Integer idGAB) {
		entityManager.createNamedQuery("GAB_ATUALIZAR_ESTADO_EM_AUDITORIA").setParameter("id", idGAB).executeUpdate();
		entityManager.createNamedQuery("GAB_ATUALIZAR_ESTADO_AUDITADA").setParameter("id", idGAB).executeUpdate();
	}

	@Override
	public List<GuiaApresentacaoBeneficiario> obterGabsPorCodigo(String codigo) {
		return entityManager.createNamedQuery("GAB_OBTER_GAB_POR_CODIGO", GuiaApresentacaoBeneficiario.class).setParameter("codigo", "%" + codigo + "%").getResultList();
	}
	

}
