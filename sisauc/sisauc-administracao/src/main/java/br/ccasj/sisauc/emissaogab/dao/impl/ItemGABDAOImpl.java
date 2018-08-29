package br.ccasj.sisauc.emissaogab.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.auditoriaretrospectiva.domain.Lote;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.ParametrosRelatorioDescontoBeneficiariosPesquisa;
import br.ccasj.sisauc.emissaogab.dao.ItemGABDAO;
import br.ccasj.sisauc.emissaogab.domain.EstadoItemGAB;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;
import br.ccasj.sisauc.framework.utils.DateUtils;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiarioItem;

@MappedSuperclass
@Transactional
@Repository(value = "itemGABDAO")
@NamedQueries({
	@NamedQuery(name = "ITEM_GAB_OBTER_ITENS_GAB_PARA_AUDITORIA", query = ItemGABDAOImpl.OBTER_ITENS_GAB_PARA_AUDITORIA),
	@NamedQuery(name = "ITEM_GAB_OBTER_ITENS_GAB_PARA_CANCELAMENTO_AUDITORIA", query =ItemGABDAOImpl.OBTER_ITENS_GAB_PARA_CANCELAMENTO_AUDITORIA),
	@NamedQuery(name = "ITEM_GAB_OBTER_ITENS_GAB_CONFORMES", query = ItemGABDAOImpl.OBTER_ITENS_GAB_CONFORMES),
	@NamedQuery(name = "ITEM_GAB_OBTER_ITENS_GAB_CONFORMES_POR_CREDENCIADO", query = ItemGABDAOImpl.OBTER_ITENS_GAB_CONFORMES_POR_CREDENCIADO),
	@NamedQuery(name = "ITEM_GAB_OBTER_ITENS_GAB_NAO_CONFORMES", query = ItemGABDAOImpl.OBTER_ITENS_GAB_NAO_CONFORMES),
	@NamedQuery(name = "ITEM_GAB_OBTER_ITENS_GAB_NAO_CONFORMES_POR_CREDENCIADO", query = ItemGABDAOImpl.OBTER_ITENS_GAB_NAO_CONFORMES_POR_CREDENCIADO),
	@NamedQuery(name = "ITEM_GAB_OBTER_ITENS_POR_GAB", query = ItemGABDAOImpl.OBTER_ITENS_POR_GAB),
	@NamedQuery(name = "ITEM_GAB_OBTER_ITENS_POR_LOTE_PARA_RELATORIO_ANALITICO_FATURA", query = ItemGABDAOImpl.OBTER_ITENS_POR_LOTE_PARA_RELATORIO_ANALITICO_FATURA),
	@NamedQuery(name = "ITEM_GAB_OBTER_LOTE_POR_ITEM", query = ItemGABDAOImpl.OBTER_LOTE_POR_ITEM),
	@NamedQuery(name = "ITEM_GAB_OBTER_CODIGO_ITENS_QUE_POSSUEM_LOTE", query = ItemGABDAOImpl.OBTER_CODIGO_ITENS_QUE_POSSUEM_LOTE),
	@NamedQuery(name = "ITEM_GAB_ATUALIZAR_ESTADOS_ITENS", query = ItemGABDAOImpl.ATUALIZAR_ESTADOS_ITENS)
})
public class ItemGABDAOImpl extends GenericEntityDAOImpl<ItemGAB> implements ItemGABDAO {

	private static final long serialVersionUID = -709228480013314685L;


	//TODO melhorar carregamento do item
	public static final String OBTER_ITENS_GAB_PARA_AUDITORIA = "SELECT new ItemGAB(item.id, item.codigo, item.estadoItemGAB, gab.divisao, gab.dataGeracao, gab.dataEmissao, credenciado.nomeFantasia) FROM ItemGAB item "
			+ "LEFT JOIN item.gab as gab LEFT JOIN gab.credenciado as credenciado "
			+ "WHERE item.estadoItemGAB = 'AGUARDANDO_AUDITORIA' OR item.estadoItemGAB = 'AUDITORIA_INICIADA' OR item.estadoItemGAB = 'NAO_CONFORME' order by item.codigo";
	
	public static final String OBTER_ITENS_GAB_PARA_CANCELAMENTO_AUDITORIA = "SELECT "
			+ "new ItemGAB(item.id, item.codigo, item.estadoItemGAB, gab.divisao, item.configuracao, gab.dataGeracao, gab.dataEmissao, credenciado.nomeFantasia, lote.numero) FROM Lote lote "
			+ "RIGHT JOIN lote.itensGab item LEFT JOIN item.gab as gab LEFT JOIN gab.credenciado as credenciado "
			+ "WHERE item.estadoItemGAB = 'CONFORME' order by item.codigo";
		
	public static final String OBTER_ITENS_GAB_CONFORMES = "SELECT new ItemGAB(item.id, item.codigo, auditoriaRetrospectiva.valorFinal, gab.divisao, gab.dataGeracao, gab.dataEmissao, credenciado.nomeFantasia, gab.estado) from ItemGAB item "
			+ "LEFT JOIN item.auditoriaRetrospectiva as auditoriaRetrospectiva LEFT JOIN item.gab as gab LEFT JOIN gab.credenciado as credenciado "
			+ "WHERE item.auditoriaRetrospectiva IS NOT NULL AND item.estadoItemGAB = 'CONFORME' AND item.id NOT IN "
			+ "(SELECT item.id from Lote lote RIGHT join lote.itensGab as item where lote.conformidade = true)";
		
	public static final String OBTER_ITENS_GAB_NAO_CONFORMES = "SELECT "
			+ "new ItemGAB(item.id, item.codigo, auditoria.valorApresentado, auditoria.valorAuditado, auditoria.valorFinal, credenciado.nomeFantasia, gab.divisao, gab.dataGeracao, gab.dataEmissao, "
			+ "item.configuracao, auditoria.justificativaValorAuditado, motivo, beneficiario.nome) FROM ItemGAB item "
			+ "LEFT JOIN item.auditoriaRetrospectiva as auditoria "
			+ "LEFT JOIN auditoria.motivo as motivo "
			+ "LEFT JOIN item.gab as gab "
			+ "LEFT JOIN gab.credenciado as credenciado "
			+ "LEFT JOIN gab.beneficiario as beneficiario "
			+ "WHERE item.estadoItemGAB = 'NAO_CONFORME'";
		
	public static final String OBTER_ITENS_GAB_CONFORMES_POR_CREDENCIADO = OBTER_ITENS_GAB_CONFORMES + " AND credenciado.id=:idCredenciado";
	
	public static final String OBTER_ITENS_GAB_NAO_CONFORMES_POR_CREDENCIADO = OBTER_ITENS_GAB_NAO_CONFORMES + " AND credenciado.id=:idCredenciado";
	
	public static final String OBTER_ITENS_POR_GAB = "from ItemGAB where gab.id = :idGab order by codigo";
	
	public static final String OBTER_ITENS_POR_LOTE_PARA_RELATORIO_ANALITICO_FATURA = "SELECT new ItemGAB (item.id, item.codigo, item.valorTotal, auditoria.id, auditoria.valorApresentado, "
			+ "auditoria.valorAuditado, auditoria.valorFinal, gab.codigo, gab.isento, gab.dataEmissao, beneficiario.nome, beneficiario.saram, beneficiarioTitular.saram) "
			+ "FROM Lote lote RIGHT JOIN lote.itensGab as item "
			+ "LEFT JOIN item.auditoriaRetrospectiva as auditoria "
			+ "LEFT JOIN item.gab as gab "
			+ "LEFT JOIN gab.beneficiario as beneficiario "
			+ "LEFT JOIN beneficiario.beneficiarioTitular as beneficiarioTitular "
			+ "WHERE lote.id = :idLote";
	
	public static final String OBTER_LOTE_POR_ITEM = "SELECT lote from Lote lote WHERE :item MEMBER OF lote.itensGab";

	public static final String OBTER_CODIGO_ITENS_QUE_POSSUEM_LOTE = "SELECT item.codigo FROM Lote lote JOIN lote.itensGab as item WHERE item IN (:itens) ORDER BY item.codigo";

	public static final String ATUALIZAR_ESTADOS_ITENS = "UPDATE ItemGAB item SET item.estadoItemGAB = :estado where item in (:itens)";
	
	@Override
	public List<ItemGAB> obterItensPorGAB(Integer idGab) {
		List<ItemGAB> result = entityManager.createNamedQuery("ITEM_GAB_OBTER_ITENS_POR_GAB", ItemGAB.class).setParameter("idGab", idGab).getResultList();
		for (ItemGAB itemGAB : result) {
			Hibernate.initialize(itemGAB.getValores());
			if(itemGAB.getAuditoriaRetrospectiva() != null){
				Hibernate.initialize(itemGAB.getAuditoriaRetrospectiva().getValores());
			}
		}
		return result;
	}
	
	@Override
	public List<ItemGAB> obterItensGABParaAuditoriaRetrospectiva() {
		return entityManager.createNamedQuery("ITEM_GAB_OBTER_ITENS_GAB_PARA_AUDITORIA", ItemGAB.class).getResultList();
	}
	
	@Override
	public List<ItemGAB> obterItensGABParaCancelamentoAuditoriaRetrospectiva() {
		return entityManager.createNamedQuery("ITEM_GAB_OBTER_ITENS_GAB_PARA_CANCELAMENTO_AUDITORIA", ItemGAB.class).getResultList();
	}
	
	@Override
	public ItemGAB obterItemGABComMetadados(Integer idItemGAB) {
		ItemGAB retorno = this.findById(idItemGAB);
		Hibernate.initialize(retorno.getValores());
		Hibernate.initialize(retorno.getAuditoriaRetrospectiva());
		if (retorno.getAuditoriaRetrospectiva() != null && retorno.getAuditoriaRetrospectiva().getValores()!= null) {
			Hibernate.initialize(retorno.getAuditoriaRetrospectiva().getValores());
		}
		return retorno;
	}
	
	@Override
	public List<ItemGAB> obterItensGabConformes() {
		return entityManager.createNamedQuery("ITEM_GAB_OBTER_ITENS_GAB_CONFORMES", ItemGAB.class).getResultList();
	}

	@Override
	public List<ItemGAB> obterItensGabConformesPorCredenciado(Integer idCredenciado) {
		return entityManager.createNamedQuery("ITEM_GAB_OBTER_ITENS_GAB_CONFORMES_POR_CREDENCIADO", ItemGAB.class).setParameter("idCredenciado", idCredenciado).getResultList();
	}

	@Override
	public List<ItemGAB> obterItensGabNaoConformes() {
		return entityManager.createNamedQuery("ITEM_GAB_OBTER_ITENS_GAB_NAO_CONFORMES", ItemGAB.class).getResultList();
	}
	
	@Override
	public List<ItemGAB> obterItensGabParaDesconto(ParametrosRelatorioDescontoBeneficiariosPesquisa parametros) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT new ItemGAB(item.id, item.codigo, beneficiario.nome, beneficiarioTitular.nome, gab.dataEmissao, gab.isento) ");
		sb.append("FROM Lote lote JOIN lote.itensGab item JOIN item.gab gab ");
		sb.append("JOIN gab.beneficiario beneficiario LEFT JOIN beneficiario.beneficiarioTitular beneficiarioTitular ");
		sb.append("WHERE lote.notaFiscal.numero is not null AND item.estadoItemGAB = 'CONFORME'");
		aplicarFiltros(sb, parametros);
		return entityManager.createQuery(sb.toString(), ItemGAB.class).getResultList();
	}
	
	private void aplicarFiltros(StringBuilder sb, ParametrosRelatorioDescontoBeneficiariosPesquisa parametros) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (parametros.getDataInicial() != null){
			sb.append(" AND lote.notaFiscal.dataApresentacao >= '" + df.format(parametros.getDataInicial()) + "'");
		}
		if (parametros.getDataFinal() != null){
			sb.append(" AND lote.notaFiscal.dataApresentacao <= '" + df.format(DateUtils.getEndOfDate(parametros.getDataFinal())) + "'");
		}
		if (parametros.getLote() != null){
			sb.append(" AND lote.numero LIKE '%" + parametros.getLote().toUpperCase().trim() + "%'");
		}
		if (parametros.getGab() != null){
			sb.append(" AND gab.codigo LIKE '%" + parametros.getGab().toUpperCase().trim() + "%'");
		}
	}

	@Override
	public List<ItemGAB> obterItensGabNaoConformesPorCredenciado(Integer idCredenciado) {
		return entityManager.createNamedQuery("ITEM_GAB_OBTER_ITENS_GAB_NAO_CONFORMES_POR_CREDENCIADO", ItemGAB.class).setParameter("idCredenciado", idCredenciado).getResultList();
	}

	@Override
	public List<ItemGAB> obterItensGabParaRelatorioAnaliticoFatura(Integer idLote) {
		return entityManager.createNamedQuery("ITEM_GAB_OBTER_ITENS_POR_LOTE_PARA_RELATORIO_ANALITICO_FATURA", ItemGAB.class).setParameter("idLote", idLote).getResultList();
	}

	@Override
	public Lote obterLotePorItemGAB(ItemGAB itemGab) {
		List<Lote> result = entityManager.createNamedQuery("ITEM_GAB_OBTER_LOTE_POR_ITEM", Lote.class).setParameter("item", itemGab).getResultList();
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public List<String> obterCodigoItensQuePossuemLote(Set<ItemGAB> itensGab) {
		return entityManager.createNamedQuery("ITEM_GAB_OBTER_CODIGO_ITENS_QUE_POSSUEM_LOTE", String.class).setParameter("itens", itensGab).getResultList();
	}

	@Override
	public void atualizarEstadoItens(List<ItemGAB> itens, EstadoItemGAB estado) {
		entityManager.createNamedQuery("ITEM_GAB_ATUALIZAR_ESTADOS_ITENS").setParameter("itens", itens)
				.setParameter("estado", estado).executeUpdate();
	}
}
