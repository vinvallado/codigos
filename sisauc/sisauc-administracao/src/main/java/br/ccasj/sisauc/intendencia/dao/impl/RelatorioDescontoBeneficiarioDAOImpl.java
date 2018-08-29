package br.ccasj.sisauc.intendencia.dao.impl;

import java.util.LinkedHashSet;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;

import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;
import br.ccasj.sisauc.intendencia.dao.RelatorioDescontoBeneficiarioDAO;
import br.ccasj.sisauc.intendencia.domain.EstadoRelatorioFolhaBeneficiario;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiario;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiarioItem;

@MappedSuperclass
@Transactional
@Repository(value = "relatorioDescontoBeneficiarioDAO")
@NamedQueries({
	@NamedQuery(name = RelatorioDescontoBeneficiarioDAOImpl.LISTAR_ATIVOS, query = RelatorioDescontoBeneficiarioDAOImpl.LISTAR_ATIVOS),
	@NamedQuery(name = RelatorioDescontoBeneficiarioDAOImpl.LISTAR_TODOS, query = RelatorioDescontoBeneficiarioDAOImpl.LISTAR_TODOS),
	@NamedQuery(name = RelatorioDescontoBeneficiarioDAOImpl.OBTER_CODIGO_POR_ITEM_GAB, query = RelatorioDescontoBeneficiarioDAOImpl.OBTER_CODIGO_POR_ITEM_GAB),
	@NamedQuery(name = RelatorioDescontoBeneficiarioDAOImpl.OBTER_CODIGO_DO_ULTIMO_RELATORIO_DO_ANO, query = RelatorioDescontoBeneficiarioDAOImpl.OBTER_CODIGO_DO_ULTIMO_RELATORIO_DO_ANO),
	@NamedQuery(name = RelatorioDescontoBeneficiarioDAOImpl.OBTER_POR_ID_COM_ITENS, query = RelatorioDescontoBeneficiarioDAOImpl.OBTER_POR_ID_COM_ITENS),
	@NamedQuery(name = RelatorioDescontoBeneficiarioDAOImpl.OBTER_POR_ID_COM_ITENS_ISENTOS, query = RelatorioDescontoBeneficiarioDAOImpl.OBTER_POR_ID_COM_ITENS_ISENTOS),
	@NamedQuery(name = RelatorioDescontoBeneficiarioDAOImpl.UPDATE_ESTADO, query = RelatorioDescontoBeneficiarioDAOImpl.UPDATE_ESTADO),
	@NamedQuery(name = RelatorioDescontoBeneficiarioDAOImpl.UPDATE_ESTADO_DATAS_RELATORIO, query = RelatorioDescontoBeneficiarioDAOImpl.UPDATE_ESTADO_DATAS_RELATORIO),
	@NamedQuery(name = RelatorioDescontoBeneficiarioDAOImpl.UPDATE_ESTADO_ENVIO_ITEM, query = RelatorioDescontoBeneficiarioDAOImpl.UPDATE_ESTADO_ENVIO_ITEM),
	@NamedQuery(name = RelatorioDescontoBeneficiarioDAOImpl.OBTER_IDS_RELATORIOS_PELOS_ESTADOS_DE_ENVIO, query = RelatorioDescontoBeneficiarioDAOImpl.OBTER_IDS_RELATORIOS_PELOS_ESTADOS_DE_ENVIO)
	
})
public class RelatorioDescontoBeneficiarioDAOImpl  extends GenericEntityDAOImpl<RelatorioDescontoBeneficiario> implements RelatorioDescontoBeneficiarioDAO {

	private static final long serialVersionUID = 4439608245492464672L;

	public static final String OBTER_IDS_RELATORIOS_PELOS_ESTADOS_DE_ENVIO = "select r.id from RelatorioDescontoBeneficiario r where estadoEnvioFolha in :estadosDeEnvio and r.cancelado = false";
	public static final String UPDATE_ESTADO = "update RelatorioDescontoBeneficiario r set r.estadoEnvioFolha = :estado where r.id = :id";
	public static final String UPDATE_ESTADO_DATAS_RELATORIO = "update RelatorioDescontoBeneficiario r set r.estadoEnvioFolha = :estadoEnvioFolha, r.dataInicioEnvio = :dataInicioEnvio, r.dataFinalizacaoEnvio = :dataFinalizacaoEnvio  where r.id = :id";
	public static final String UPDATE_ESTADO_ENVIO_ITEM = "update RelatorioDescontoBeneficiarioItem item set item.estado = :estado, item.codigoErro = :codigoErro, item.valorDescontoEnviado = :valorDescontoEnviado, item.idDescontoGerado = :idDescontoGerado  where item.id = :id";
	public static final String LISTAR_ATIVOS = "select new RelatorioDescontoBeneficiario(r.id, r.codigo, r.autor.nome, r.autor.login, r.dataGeracao, r.cancelado, r.estadoEnvioFolha) from RelatorioDescontoBeneficiario r where r.cancelado = false order by r.dataGeracao desc";
	public static final String LISTAR_TODOS = "select new RelatorioDescontoBeneficiario(r.id, r.codigo, r.autor.nome, r.autor.login, r.dataGeracao, r.cancelado, r.estadoEnvioFolha) from RelatorioDescontoBeneficiario r order by r.dataGeracao desc";
	public static final String OBTER_CODIGO_POR_ITEM_GAB = "SELECT rel.codigo from RelatorioDescontoBeneficiario rel WHERE :itemGAB MEMBER OF rel.itens";
	public static final String OBTER_CODIGO_DO_ULTIMO_RELATORIO_DO_ANO = "SELECT rdb.codigo FROM RelatorioDescontoBeneficiario rdb WHERE rdb.id= (SELECT MAX(id) FROM RelatorioDescontoBeneficiario WHERE YEAR(dataGeracao) = :ano)";
	public static final String OBTER_POR_ID_COM_ITENS = "select new RelatorioDescontoBeneficiario("
			+ "rel.id, rel.codigo, rel.dataGeracao, "
			+ "gabItem.id, gabItem.codigo, "
			+ "item.estado, item.valorDescontoEnviado, "
			+ "autor, "
			+ "auditoriaRetrospectiva.valorFinal, "
			+ "beneficiario.saram, beneficiario.nome, beneficiario.titular, "
			+ "convenio.sigla, "
			+ "postoGraduacao.sigla, "
			+ "om.sigla, "
			+ "titular.saram, titular.nome, postoGraduacaoTitular.sigla, "
			+ "omTitular.sigla, item.id, beneficiario.cdParentesco) "
			+ "from RelatorioDescontoBeneficiario rel "
			+ "LEFT JOIN rel.autor as autor "			
			+ "LEFT JOIN rel.itens as item "
			
			+ "LEFT JOIN item.id.itemGab as gabItem "
			+ "LEFT JOIN gabItem.gab as gab "
			+ "LEFT JOIN gabItem.auditoriaRetrospectiva as auditoriaRetrospectiva "
			+ "LEFT JOIN gab.beneficiario as beneficiario "
			+ "LEFT JOIN beneficiario.convenio as convenio "
			+ "LEFT JOIN beneficiario.organizacaoMilitar as om "
			+ "LEFT JOIN beneficiario.postoGraduacao as postoGraduacao "
			
			+ "LEFT JOIN beneficiario.beneficiarioTitular as titular "
			+ "LEFT JOIN titular.postoGraduacao as postoGraduacaoTitular "
			+ "LEFT JOIN titular.organizacaoMilitar as omTitular "
			
			+ "where rel.id = :id "
			+ "order by beneficiario.nome";

	public static final String OBTER_POR_ID_COM_ITENS_ISENTOS = "select new RelatorioDescontoBeneficiario("
			+ "rel.id, rel.codigo, rel.dataGeracao, "
			+ "itemIsento.id, itemIsento.codigo, "
			+ "autor, "
			+ "beneficiarioIsento.saram, beneficiarioIsento.nome, beneficiarioIsento.titular, "
			+ "convenio.sigla, "
			+ "postoGraduacaoIsento.sigla, "
			+ "omIsento.sigla, "
			+ "titularIsento.saram, titularIsento.nome, postoGraduacaoTitularIsento.sigla, "
			+ "omTitularIsento.sigla) "
			+ "from RelatorioDescontoBeneficiario rel "
			+ "LEFT JOIN rel.autor as autor "			
			+ "LEFT JOIN rel.itensIsentos as itemIsento "
						
			+ "LEFT JOIN itemIsento.gab as gabIsento "
			+ "LEFT JOIN gabIsento.beneficiario as beneficiarioIsento "
			+ "LEFT JOIN beneficiarioIsento.organizacaoMilitar as omIsento "
			+ "LEFT JOIN beneficiarioIsento.convenio as convenio "
			+ "LEFT JOIN beneficiarioIsento.postoGraduacao as postoGraduacaoIsento "
			+ "LEFT JOIN beneficiarioIsento.beneficiarioTitular as titularIsento "
			+ "LEFT JOIN titularIsento.postoGraduacao as postoGraduacaoTitularIsento "
			+ "LEFT JOIN titularIsento.organizacaoMilitar as omTitularIsento "
			+ "where rel.id = :id "
			+ "order by beneficiarioIsento.nome";
	
	
	
	@Override
	public RelatorioDescontoBeneficiario findById(Integer id) {
		List<RelatorioDescontoBeneficiario> rels = entityManager.createNamedQuery(OBTER_POR_ID_COM_ITENS, RelatorioDescontoBeneficiario.class).setParameter("id", id).getResultList();
		List<RelatorioDescontoBeneficiario> relsIsentos = entityManager.createNamedQuery(OBTER_POR_ID_COM_ITENS_ISENTOS, RelatorioDescontoBeneficiario.class).setParameter("id", id).getResultList();
		RelatorioDescontoBeneficiario base = !rels.isEmpty() ? rels.get(0) : relsIsentos.get(0);
		RelatorioDescontoBeneficiario result = new RelatorioDescontoBeneficiario();
		result.setId(base.getId());
		result.setAutor(base.getAutor());
		result.setCodigo(base.getCodigo());
		result.setDataGeracao(base.getDataGeracao());
		result.setItens(new LinkedHashSet<RelatorioDescontoBeneficiarioItem>());
		result.setItensIsentos(new LinkedHashSet<ItemGAB>());
		if(!rels.isEmpty() || !relsIsentos.isEmpty()){
			for (RelatorioDescontoBeneficiario itemAtual : rels) {
				if(!itemAtual.getItens().isEmpty()){
					result.getItens().addAll(itemAtual.getItens());
				}
			}
			for (RelatorioDescontoBeneficiario itemAtual : relsIsentos) {
				if(!itemAtual.getItensIsentos().isEmpty()){
					result.getItensIsentos().addAll(itemAtual.getItensIsentos());
				}
			}
		}
		return result;
	}

	@Override
	public int obterNumeroDoUltimoRelatorioDoAno(Integer ano) {
		List<String> codigo = entityManager.createNamedQuery(OBTER_CODIGO_DO_ULTIMO_RELATORIO_DO_ANO, String.class).setParameter("ano", ano)
				.getResultList();
		return codigo.isEmpty()? 0 : Integer.parseInt(codigo.get(0).split("/")[2]);
	}

	@Override
	public List<RelatorioDescontoBeneficiario> listarAtivos() {
		return entityManager.createNamedQuery(LISTAR_ATIVOS, RelatorioDescontoBeneficiario.class).getResultList();
	}
	
	@Override
	public List<RelatorioDescontoBeneficiario> listarTodos() {
		return entityManager.createNamedQuery(LISTAR_TODOS, RelatorioDescontoBeneficiario.class).getResultList();
	}

	@Override
	public String obterCodigoPorItemGAB(ItemGAB itemGAB) {
		try {
			return entityManager.createNamedQuery(OBTER_CODIGO_POR_ITEM_GAB, String.class)
				.setParameter("itemGAB", itemGAB).getSingleResult();
		} catch (NoResultException e){
			return null;
		}
	}

	public void mudarEstadoRelatorio(RelatorioDescontoBeneficiario relatorio){
		entityManager.createNamedQuery(UPDATE_ESTADO).setParameter("estado", relatorio.getEstadoEnvioFolha()).setParameter("id", relatorio.getId()).executeUpdate();
	}

	/**
	 * O uso do TxType.REQUIRES_NEW abre uma nova transação atômica, sem a necessidade de merge
	 * e de trabalhar com o estado do objeto.
	 */
	@Transactional(value=TxType.REQUIRES_NEW)
	public void atualizarEstadoRelatorioDescontoItem(RelatorioDescontoBeneficiarioItem relatorioItem){
		entityManager.createNamedQuery(UPDATE_ESTADO_ENVIO_ITEM).setParameter("estado", relatorioItem.getEstado()).setParameter("codigoErro", relatorioItem.getCodigoErro()).setParameter("valorDescontoEnviado", relatorioItem.getValorDescontoEnviado()).setParameter("idDescontoGerado", relatorioItem.getIdDescontoGerado()).setParameter("id", relatorioItem.getId()).executeUpdate();
	}
	
	@Transactional(value=TxType.REQUIRES_NEW)
	public void atualizarEnvioDesconto(RelatorioDescontoBeneficiario relatorio){
		entityManager.createNamedQuery(UPDATE_ESTADO_DATAS_RELATORIO).setParameter("estadoEnvioFolha", relatorio.getEstadoEnvioFolha()).setParameter("dataInicioEnvio", relatorio.getDataInicioEnvio()).setParameter("dataFinalizacaoEnvio", relatorio.getDataFinalizacaoEnvio()).setParameter("id", relatorio.getId()).executeUpdate();
	}
	
	public List<Integer> obterIdsRelatoriosPelosEstadosDeEnvio(List<EstadoRelatorioFolhaBeneficiario> estadosDeEnvio){
		return entityManager.createNamedQuery(OBTER_IDS_RELATORIOS_PELOS_ESTADOS_DE_ENVIO, Integer.class).setParameter("estadosDeEnvio", estadosDeEnvio).getResultList();
	}
	
}
