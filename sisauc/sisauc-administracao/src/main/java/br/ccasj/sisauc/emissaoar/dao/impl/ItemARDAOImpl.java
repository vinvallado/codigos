package br.ccasj.sisauc.emissaoar.dao.impl;

import java.util.List;
import java.util.Set;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.auditoriaretrospectiva.domain.LoteRessarcimento;
import br.ccasj.sisauc.emissaoar.dao.ItemARDAO;
import br.ccasj.sisauc.emissaoar.domain.ItemAR;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@MappedSuperclass
@Transactional
@Repository(value = "itemARDAO")
@NamedQueries({	
	@NamedQuery(name = ItemARDAOImpl.OBTER_ITENS_AR_REALIZADOS, query = ItemARDAOImpl.OBTER_ITENS_AR_REALIZADOS),
	@NamedQuery(name = ItemARDAOImpl.OBTER_ITENS_AR_REALIZADOS_POR_BENEFICIARIO, query = ItemARDAOImpl.OBTER_ITENS_AR_REALIZADOS_POR_BENEFICIARIO),
	@NamedQuery(name = ItemARDAOImpl.OBTER_CODIGO_ITENS_QUE_POSSUEM_LOTE_RESSARCIMENTO, query = ItemARDAOImpl.OBTER_CODIGO_ITENS_QUE_POSSUEM_LOTE_RESSARCIMENTO),
	@NamedQuery(name = ItemARDAOImpl.OBTER_LOTE_POR_ITEM, query = ItemARDAOImpl.OBTER_LOTE_POR_ITEM)
})
public class ItemARDAOImpl extends GenericEntityDAOImpl<ItemAR> implements ItemARDAO {

	private static final long serialVersionUID = 5898990635604937042L;
	
	public static final String OBTER_ITENS_AR_REALIZADOS = "SELECT new ItemAR(item.id, item.codigo, item.estadoItemAR, "
			+ "auditoriaRetrospectiva.valorRessarcimento, "
			+ "ar.dataEmissao, ar.estado, ar.divisao,"
			+ "beneficiario.titular, beneficiario.id, beneficiario.nome, beneficiario.saram, "
			+ "titular.id, titular.nome, titular.saram) from ItemAR item "
			+ "LEFT JOIN item.auditoriaRetrospectiva as auditoriaRetrospectiva "
			+ "LEFT JOIN item.ar as ar "
			+ "LEFT JOIN ar.beneficiario as beneficiario "
			+ "LEFT JOIN beneficiario.beneficiarioTitular as titular "
			+ "WHERE item.auditoriaRetrospectiva IS NOT NULL AND item.estadoItemAR = 'REALIZADO' AND ar.estado = 'AUDITADA' "
			+ "AND item.id NOT IN (SELECT item.id from LoteRessarcimento lote RIGHT join lote.itensAr as item WHERE lote.id IS NOT NULL)"; //ULTIMO WHERE NECESSARIO? 
	
	public static final String OBTER_ITENS_AR_REALIZADOS_POR_BENEFICIARIO = OBTER_ITENS_AR_REALIZADOS + " AND beneficiario.saram=:saram OR titular.saram=:saram AND item.estadoItemAR = 'REALIZADO' AND ar.estado = 'AUDITADA'";
	
	public static final String OBTER_CODIGO_ITENS_QUE_POSSUEM_LOTE_RESSARCIMENTO = "SELECT item.codigo FROM LoteRessarcimento loteRessarcimento JOIN loteRessarcimento.itensAr as item WHERE item IN (:itens) ORDER BY item.codigo";
	
	public static final String OBTER_LOTE_POR_ITEM = "SELECT lote from LoteRessarcimento lote WHERE :item MEMBER OF lote.itensAr";

	@Override
	public List<ItemAR> obterItensAr() {
		return entityManager.createNamedQuery(OBTER_ITENS_AR_REALIZADOS, ItemAR.class).getResultList();
	}
	
	@Override
	public List<ItemAR> obterItensArRealizadosPorBeneficiario(String saram) {
		return entityManager.createNamedQuery(OBTER_ITENS_AR_REALIZADOS_POR_BENEFICIARIO, ItemAR.class).setParameter("saram", saram).getResultList();
	}

	@Override
	public List<String> obterCodigoItensQuePossuemLoteRessarcimento(Set<ItemAR> itensAr) {
		return entityManager.createNamedQuery(OBTER_CODIGO_ITENS_QUE_POSSUEM_LOTE_RESSARCIMENTO, String.class).setParameter("itens", itensAr).getResultList();
	}
	
	@Override
	public ItemAR obterItemARE(Integer idItemARE) {
		ItemAR retorno = this.findById(idItemARE);
		Hibernate.initialize(retorno.getAuditoriaRetrospectiva());
		if(retorno.getAuditoriaRetrospectiva() != null){
			Hibernate.initialize(retorno.getAuditoriaRetrospectiva().getEspecificacoes());
		}
		return retorno;
	}
	
	@Override
	public LoteRessarcimento obterLotePorItemARE(ItemAR itemAre) {
		List<LoteRessarcimento> result = entityManager.createNamedQuery(OBTER_LOTE_POR_ITEM, LoteRessarcimento.class).setParameter("item", itemAre).getResultList();
		return result.isEmpty() ? null : result.get(0);
	}

}
