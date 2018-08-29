package br.ccasj.sisauc.auditoriaretrospectiva.dao.impl;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.auditoriaretrospectiva.dao.GerenciarLoteRessarcimentoDAO;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.LoteRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.ItemAR;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@MappedSuperclass
@Transactional
@Repository(value = "gerenciarLoteRessarcimentoDAO")
@NamedQueries({
	@NamedQuery(name = GerenciarLoteRessarcimentoDAOImpl.OBTER_QUANTIDADE_LOTES_RESSARCIMENTO_POR_OM_ANO_E_CONFORMIDADE, query = GerenciarLoteRessarcimentoDAOImpl.OBTER_QUANTIDADE_LOTES_RESSARCIMENTO_POR_OM_ANO_E_CONFORMIDADE),
	@NamedQuery(name = GerenciarLoteRessarcimentoDAOImpl.LISTAR_LOTES_VIGENTES, query = GerenciarLoteDAOImpl.LISTAR_LOTES_VIGENTES),
	@NamedQuery(name = GerenciarLoteRessarcimentoDAOImpl.OBTER_LOTE_RESSARCIMENTO_PARA_RELATORIO_ANALITICO_FATURA, query = GerenciarLoteRessarcimentoDAOImpl.OBTER_LOTE_RESSARCIMENTO_PARA_RELATORIO_ANALITICO_FATURA),
	@NamedQuery(name = GerenciarLoteRessarcimentoDAOImpl.LISTAR_TODOS, query = GerenciarLoteRessarcimentoDAOImpl.LISTAR_TODOS),
	@NamedQuery(name = GerenciarLoteRessarcimentoDAOImpl.EXISTE_LOTE_RESSARCIMENTO_PARA_UM_DADO_ID_ARE, query = GerenciarLoteRessarcimentoDAOImpl.EXISTE_LOTE_RESSARCIMENTO_PARA_UM_DADO_ID_ARE),
	@NamedQuery(name = "OBTER_CODIGOS_LOTES_DE_UMA_ARE", query = GerenciarLoteRessarcimentoDAOImpl.OBTER_CODIGOS_LOTES_DE_UMA_ARE)
	,
	@NamedQuery(name = "UPDATE_CANCELAMENTO_NULL", query = GerenciarLoteRessarcimentoDAOImpl.UPDATE_CANCELAMENTO_NULL)
})
public class GerenciarLoteRessarcimentoDAOImpl  extends GenericEntityDAOImpl<LoteRessarcimento> implements GerenciarLoteRessarcimentoDAO {

	private static final long serialVersionUID = -4108845058095940258L;

	public static final String UPDATE_CANCELAMENTO_NULL = "update LoteRessarcimento set cancelado=false where cancelado is null";
	
	public static final String LISTAR_TODOS = 
			"SELECT NEW LoteRessarcimento(l.id, l.numero, l.valorTotalRessarcir, l.beneficiario.id, l.beneficiario.nome, " +
					" l.cancelado ) FROM LoteRessarcimento l";
	
	// CASE WHEN (l.cancelado is null) THEN false ELSE l.cancelado 
			
			//"SELECT NEW LoteRessarcimento (l.id, l.numero, l.valorTotalRessarcir, l.beneficiario, l.cancelado) FROM LoteRessarcimento l";

	public static final String LISTAR_LOTES_VIGENTES = 
			"SELECT NEW LoteRessarcimento (l.id, l.numero, l.valorTotal, "
			+ "l.beneficiario, l.notaFiscal.numero,  l.cancelado)"
			+ "FROM Lote l where l.cancelado = false";
	
	public static final String OBTER_CODIGOS_LOTES_DE_UMA_ARE =
			" SELECT 	distinct lote.numero " +
			" FROM 		LoteRessarcimento lote " +
			" 			INNER JOIN lote.itensAr itemAr " +
			" 			INNER JOIN itemAr.ar areNoLote " +
			" WHERE  	areNoLote.id = :idAr ";
	
	
	public static final String OBTER_QUANTIDADE_LOTES_RESSARCIMENTO_POR_OM_ANO_E_CONFORMIDADE = "SELECT COUNT(id) FROM LoteRessarcimento WHERE YEAR(dataCriacao) = :ano AND organizacaoMilitar.id = :idOm";
	
	public static final String OBTER_LOTE_RESSARCIMENTO_PARA_RELATORIO_ANALITICO_FATURA = "SELECT new LoteRessarcimento (loteRessarcimento.id, loteRessarcimento.numero, beneficiario.nome, om.nome, om.sigla) "
			+ "FROM LoteRessarcimento loteRessarcimento LEFT JOIN loteRessarcimento.beneficiario as beneficiario "
			+ "LEFT JOIN loteRessarcimento.organizacaoMilitar as om "
			+ "WHERE loteRessarcimento.id = :idLoteRessarcimento";
	
	public static final String EXISTE_LOTE_RESSARCIMENTO_PARA_UM_DADO_ID_ARE = 
			"SELECT COUNT(id) "
			+ "FROM LoteRessarcimento lote "
			+ "INNER JOIN lote.itensAr as itens "
			+ "INNER JOIN itens.ar as ar "
			+ "WHERE ar.id = :idAR AND lote.cancelado = false";
	 
	
	@Override
	public List<LoteRessarcimento> findAll(){
		updateCanceladoIsNull();
		return entityManager.createNamedQuery(LISTAR_TODOS, LoteRessarcimento.class).getResultList();
	}

	@Override
	public List<String> obterCodigosLotesDeUmaArePeloId(Integer idARE){
		return entityManager.createNamedQuery("OBTER_CODIGOS_LOTES_DE_UMA_ARE", String.class).setParameter("idAr", idARE).getResultList();
	}
	
	@Override
	public LoteRessarcimento obterLoteRessarcimentoComItensById(Integer idLoteRessarcimento) {
		LoteRessarcimento loteRessarcimento = findById(idLoteRessarcimento);
		Hibernate.initialize(loteRessarcimento.getItensAr());
		for (ItemAR item : loteRessarcimento.getItensAr()) {
			Hibernate.initialize(item.getAuditoriaRetrospectiva().getEspecificacoes());
		}
		return loteRessarcimento;
	}
	
	@Override
	public int obterQuantidadeLotesRessarcimentoPorOrganizacaoMilitarEAno(Integer idOm, Integer ano) {
		return entityManager.createNamedQuery(OBTER_QUANTIDADE_LOTES_RESSARCIMENTO_POR_OM_ANO_E_CONFORMIDADE, Long.class).setParameter("ano", ano).setParameter("idOm", idOm).getSingleResult().intValue();
	}
	
	@Override
	public Long existeLoteDadoIdARE(Integer idAR){
		return entityManager.createNamedQuery(EXISTE_LOTE_RESSARCIMENTO_PARA_UM_DADO_ID_ARE, Long.class).setParameter("idAR", idAR).getSingleResult();
	}

	@Override
	public LoteRessarcimento obterLoteRessarcimentoParaRelatorioAnaliticoFatura(Integer idLoteRessarcimento) {
		return entityManager.createNamedQuery(OBTER_LOTE_RESSARCIMENTO_PARA_RELATORIO_ANALITICO_FATURA, LoteRessarcimento.class).setParameter("idLoteRessarcimento", idLoteRessarcimento).getSingleResult();
	}
	
	public void updateCanceladoIsNull(){
		 entityManager.createNamedQuery("UPDATE_CANCELAMENTO_NULL").executeUpdate();
		 entityManager.flush();
	}


}
