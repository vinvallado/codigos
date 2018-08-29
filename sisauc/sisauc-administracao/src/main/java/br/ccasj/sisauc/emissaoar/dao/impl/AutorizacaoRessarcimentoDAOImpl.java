package br.ccasj.sisauc.emissaoar.dao.impl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.emissaoar.dao.AutorizacaoRessarcimentoDAO;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.EstadoAR;
import br.ccasj.sisauc.emissaoar.domain.ItemAR;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;
import br.ccasj.sisauc.framework.domain.Usuario;

@MappedSuperclass
@Transactional
@Repository(value = "autorizacaoRessarcimentoDAO")
@NamedQueries({
	@NamedQuery(name = "AR_OBTER_QUANTIDADE_ARS_POR_OM_E_ANO", query = AutorizacaoRessarcimentoDAOImpl.OBTER_QUANTIDADE_ARS_POR_OM_E_ANO),
	@NamedQuery(name = "AR_OBTER_ARS_POR_OM", query = AutorizacaoRessarcimentoDAOImpl.OBTER_ARS_POR_OM),
	@NamedQuery(name = "AR_CANCELAR_AR_POR_ID", query = AutorizacaoRessarcimentoDAOImpl.CANCELAR_AR_POR_ID),
	@NamedQuery(name = "AR_CANCELAR_ITEM_AR_POR_ID_AR", query = AutorizacaoRessarcimentoDAOImpl.CANCELAR_ITEM_AR_POR_ID_AR),
	@NamedQuery(name = "AR_OBTER_AR_POR_ID_AUDITORIA_PROSPECTIVA", query = AutorizacaoRessarcimentoDAOImpl.OBTER_AR_POR_ID_AUDITORIA_PROSPECTIVA),
	@NamedQuery(name = "AR_OBTER_ARES_PARA_CANCELAMENTO_AUDITORIA_PROSPECTIVA", query = AutorizacaoRessarcimentoDAOImpl.OBTER_ARES_PARA_CANCELAMENTO_AUDITORIA_PROSPECTIVA),
	@NamedQuery(name = "AR_ATUALIZAR_ESTADO_AR_POR_ID", query = AutorizacaoRessarcimentoDAOImpl.ATUALIZAR_ESTADO_AR_POR_ID),
	@NamedQuery(name = "AR_ATUALIZAR_DATA_AR_POR_ID", query = AutorizacaoRessarcimentoDAOImpl.ATUALIZAR_DATA_AR_POR_ID),
	@NamedQuery(name = "AR_PERSISTIR_USUARIO", query = AutorizacaoRessarcimentoDAOImpl.PERSISTIR_USUARIO),
	@NamedQuery(name = "AR_OBTER_POR_AUDITORIA_PROSPECTIVA", query = AutorizacaoRessarcimentoDAOImpl.OBTER_POR_AUDITORIA_PROSPECTIVA),
	@NamedQuery(name = "AR_LISTAR_AR_POR_ESTADOS", query = AutorizacaoRessarcimentoDAOImpl.LISTAR_AR_POR_ESTADOS),
	@NamedQuery(name = "AR_LISTAR_AR_POR_OM_E_ESTADOS", query = AutorizacaoRessarcimentoDAOImpl.LISTAR_AR_POR_OM_E_ESTADOS),
	@NamedQuery(name = "AR_OBTER_POR_ID_PARA_AUDITORIA_RETROSPECTIVA", query = AutorizacaoRessarcimentoDAOImpl.OBTER_POR_ID_PARA_AUDITORIA_RETROSPECTIVA),
	@NamedQuery(name = "AR_OBTER_ARE_POR_CODIGO", query = AutorizacaoRessarcimentoDAOImpl.OBTER_ARE_POR_CODIGO)
})
public class AutorizacaoRessarcimentoDAOImpl extends GenericEntityDAOImpl<AutorizacaoRessarcimento> implements AutorizacaoRessarcimentoDAO {
	
	private static final long serialVersionUID = 3455708952997798058L;
	
	public static final String OBTER_QUANTIDADE_ARS_POR_OM_E_ANO = "SELECT COUNT(id) FROM AutorizacaoRessarcimento WHERE YEAR(dataGeracao) = :ano AND organizacaoMilitar.id = :idOm";
	public static final String OBTER_ARS_POR_OM = "SELECT new AutorizacaoRessarcimento(ar.id, ar.divisao, ar.codigo, ar.dataGeracao, ar.estado, b.nome, b.saram, beneficiarioTitular.saram) "
			+ "from AutorizacaoRessarcimento ar "
			+ "left join ar.beneficiario as b "
			+ "left join b.beneficiarioTitular as beneficiarioTitular "
			+ "left join ar.organizacaoMilitar as om "
			+ "where om.id = :idOM order by ar.dataGeracao";
	public static final String CANCELAR_AR_POR_ID = "UPDATE AutorizacaoRessarcimento SET estado = 'CANCELADA', justificativaCancelamentoAR = :texto where id = :id";
	public static final String CANCELAR_ITEM_AR_POR_ID_AR = "UPDATE ItemAR SET estado = 'CANCELADO' where ar.id = :id and estado = 'APROVADO'";
	public static final String OBTER_AR_POR_ID_AUDITORIA_PROSPECTIVA = "select new AutorizacaoRessarcimento(ar.id, ar.codigo) from AutorizacaoRessarcimento ar where ar.auditoriaProspectiva.id = :idAuditoria";
	public static final String ATUALIZAR_ESTADO_AR_POR_ID = "UPDATE AutorizacaoRessarcimento SET estado = :estado where id = :id";
	public static final String ATUALIZAR_DATA_AR_POR_ID = "UPDATE AutorizacaoRessarcimento SET dataEmissao = :data where id = :id";
	public static final String PERSISTIR_USUARIO = "UPDATE AutorizacaoRessarcimento SET usuarioEmissorAr = :usuario where id = :id";
	public static final String OBTER_POR_AUDITORIA_PROSPECTIVA = 
			" select distinct ar " +
			" from AutorizacaoRessarcimento ar " +
			" left join fetch ar.itens " +
			" where ar.auditoriaProspectiva.id = :idAuditoria " +
			" order by ar.codigo ";
	public static final String OBTER_ARES_PARA_CANCELAMENTO_AUDITORIA_PROSPECTIVA = 
			//" SELECT new AutorizacaoRessarcimento(ar.id, ar.divisao, ar.codigo, ar.dataGeracao, ar.estado, b.nome, b.saram, beneficiarioTitular.saram) " +
	" SELECT new AutorizacaoRessarcimento(ar.id, ar.divisao, ar.codigo, ar.dataEmissao, ar.estado, b.nome, b.saram, beneficiarioTitular.saram, " + 
				" (SELECT 	max(lote.numero) " +
				" FROM 		LoteRessarcimento lote " +
				" 			INNER JOIN lote.itensAr itemAr " +
				" 			INNER JOIN itemAr.ar areNoLote " +
				" WHERE  	areNoLote.id = ar.id )" +				
	 ") " +
			" from AutorizacaoRessarcimento ar " +
			" left join ar.beneficiario as b " +
			" left join b.beneficiarioTitular as beneficiarioTitular " +
			" left join ar.organizacaoMilitar as om " +
			" where ar.estado = 'AUDITADA' " +
			" order by ar.dataEmissao desc ";
	
	public static final String LISTAR_AR_POR_ESTADOS = 
			  " select new AutorizacaoRessarcimento(ar.id, ar.divisao, ar.codigo, ar.dataEmissao, ar.dataApresentacao, ar.estado, ar.beneficiario.nome, ar.beneficiario.saram, beneficiarioTitular.saram) " +
			  " from AutorizacaoRessarcimento ar " +
			  " left join ar.beneficiario.beneficiarioTitular beneficiarioTitular " +
			  " where ar.estado in :estados " +
			  " order by ar.dataApresentacao desc ";
	public static final String LISTAR_AR_POR_OM_E_ESTADOS = " select new AutorizacaoRessarcimento(ar.id, ar.divisao, ar.codigo, ar.dataEmissao, ar.dataApresentacao, ar.estado, ar.beneficiario.nome, ar.beneficiario.saram, beneficiarioTitular.saram) "
			+ " from AutorizacaoRessarcimento ar "
			+ " left join ar.beneficiario.beneficiarioTitular beneficiarioTitular "
			+ " where ar.organizacaoMilitar = :om "
			+ " and ar.estado in :estados ";
	public static final String OBTER_POR_ID_PARA_AUDITORIA_RETROSPECTIVA = "from AutorizacaoRessarcimento ar join fetch ar.auditoriaProspectiva.solicitacao.procedimentos join fetch ar.auditoriaProspectiva.procedimentos join fetch ar.itens where ar.id = :id";
	
	public static final String OBTER_ARE_POR_CODIGO = "select new AutorizacaoRessarcimento (ar.id, ar.divisao, ar.codigo, ar.dataGeracao, ar.estado, beneficiario.nome, beneficiario.saram, beneficiarioTitular.saram)"
			+ " FROM AutorizacaoRessarcimento ar"
			+ " LEFT JOIN ar.beneficiario as beneficiario"
			+ " LEFT JOIN beneficiario.beneficiarioTitular as beneficiarioTitular"
			+ " WHERE ar.codigo LIKE :codigo";

	
	@Override
	public List<AutorizacaoRessarcimento> obterItensARESParaCancelamentoAuditoriaRetrospectiva(){
		return entityManager.createNamedQuery("AR_OBTER_ARES_PARA_CANCELAMENTO_AUDITORIA_PROSPECTIVA", AutorizacaoRessarcimento.class).getResultList();
	}

	@Override
	public int obterQuantidadeARsPorOrganizacaoMilitarEAno(Integer idOm, Integer ano) {
		return entityManager.createNamedQuery("AR_OBTER_QUANTIDADE_ARS_POR_OM_E_ANO", Long.class).setParameter("ano", ano).setParameter("idOm", idOm).getSingleResult().intValue();
	}

	@Override
	public List<AutorizacaoRessarcimento> obterARsPorOrganizacaoMilitar(Integer id) {
		return entityManager.createNamedQuery("AR_OBTER_ARS_POR_OM", AutorizacaoRessarcimento.class).setParameter("idOM", id).getResultList();
	}

	public List<AutorizacaoRessarcimento> obterAresParaCancelamentoAuditoriaRestrospectiva(Integer idAuditoria) {
		return entityManager.createNamedQuery("AR_OBTER_POR_AUDITORIA_PROSPECTIVA", AutorizacaoRessarcimento.class).setParameter("idAuditoria", idAuditoria).getResultList();
	}
	
	@Override
	public AutorizacaoRessarcimento obterComItensPorId(Integer id) {
		AutorizacaoRessarcimento ar = findById(id);
		Hibernate.initialize(ar.getItens());
		ar.setItens(ordenarItensPorCodigo(ar.getItens()));
		return ar;
	}

	@Override
	public void cancelarAR(Integer id, String justificativa) {
		entityManager.createNamedQuery("AR_CANCELAR_AR_POR_ID").setParameter("texto", justificativa).setParameter("id", id).executeUpdate();
		entityManager.createNamedQuery("AR_CANCELAR_ITEM_AR_POR_ID_AR").setParameter("id", id).executeUpdate();
	}
	
	private Set<ItemAR> ordenarItensPorCodigo(Set<ItemAR> itens) {
		Set<ItemAR> itensSorted = new TreeSet<ItemAR>(new Comparator<ItemAR>() {
			@Override
			public int compare(ItemAR o1, ItemAR o2) {
				return o1.getCodigo().substring(o1.getCodigo().length() - 3)
						.compareTo(o2.getCodigo().substring(o2.getCodigo().length() - 3));
			}
		});
		itensSorted.addAll(itens);
		return itensSorted;
	}

	@Override
	public AutorizacaoRessarcimento obterARPorAuditoriaProspectiva(Integer idAuditoria) {
		return entityManager.createNamedQuery("AR_OBTER_AR_POR_ID_AUDITORIA_PROSPECTIVA", AutorizacaoRessarcimento.class).setParameter("idAuditoria", idAuditoria).getSingleResult();
	}
	
	@Override
	public void atualizarEstadoAR(EstadoAR estado, Integer id) {
		entityManager.createNamedQuery("AR_ATUALIZAR_ESTADO_AR_POR_ID").setParameter("estado", estado).setParameter("id", id).executeUpdate();
	}
	
	@Override
	public void atualizarDataAR(Date data, Integer id) {
		entityManager.createNamedQuery("AR_ATUALIZAR_DATA_AR_POR_ID").setParameter("data", data).setParameter("id", id).executeUpdate();
	}
	
	@Override
	public void atualizarUsuarioEmissor(Usuario usuario, Integer id) {
		entityManager.createNamedQuery("AR_PERSISTIR_USUARIO").setParameter("usuario", usuario).setParameter("id", id).executeUpdate();
	}
	
	@Override
	public List<AutorizacaoRessarcimento> obterARsPorAuditoriaProspectiva(Integer idAuditoria) {
		return entityManager.createNamedQuery("AR_OBTER_POR_AUDITORIA_PROSPECTIVA", AutorizacaoRessarcimento.class).setParameter("idAuditoria", idAuditoria).getResultList();
	}

	@Override
	public List<AutorizacaoRessarcimento> listarPorOMEEstados(OrganizacaoMilitar om, EstadoAR... estados) {
		return entityManager.createNamedQuery("AR_LISTAR_AR_POR_OM_E_ESTADOS", AutorizacaoRessarcimento.class)
				.setParameter("om", om).setParameter("estados", Arrays.asList(estados)).getResultList();
	}
	
	@Override
	public List<AutorizacaoRessarcimento> listarPorEstados(EstadoAR... estados) {
		return entityManager.createNamedQuery("AR_LISTAR_AR_POR_ESTADOS", AutorizacaoRessarcimento.class)
				.setParameter("estados", Arrays.asList(estados)).getResultList();
	}

	@Override
	public AutorizacaoRessarcimento obterPorIdParaAuditoriaRetrospectiva(Integer id) {
		AutorizacaoRessarcimento ar = entityManager.createNamedQuery("AR_OBTER_POR_ID_PARA_AUDITORIA_RETROSPECTIVA", AutorizacaoRessarcimento.class)
				.setParameter("id", id).getSingleResult();
		for(ItemAR itemAR: ar.getItens()){
			if(itemAR.getAuditoriaRetrospectiva() != null){
				Hibernate.initialize(itemAR.getAuditoriaRetrospectiva().getEspecificacoes());
			}
		}
		return ar;
	}
	
	@Override
	public List<AutorizacaoRessarcimento> obterAresPorCodigo(String codigo) {
		return entityManager.createNamedQuery("AR_OBTER_ARE_POR_CODIGO", AutorizacaoRessarcimento.class).setParameter("codigo", "%" + codigo + "%").getResultList();
	}
	

}
