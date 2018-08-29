package br.ccasj.sisauc.auditoriaretrospectiva.dao.impl;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.auditoriaretrospectiva.dao.GerenciarLoteDAO;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.Lote;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@MappedSuperclass
@Transactional
@Repository(value = "gerenciarLoteDAO")
@NamedQueries({
	@NamedQuery(name = GerenciarLoteDAOImpl.OBTER_QUANTIDADE_LOTES_POR_OM_ANO_E_CONFORMIDADE, query = GerenciarLoteDAOImpl.OBTER_QUANTIDADE_LOTES_POR_OM_ANO_E_CONFORMIDADE),
	@NamedQuery(name = GerenciarLoteDAOImpl.LISTAR_LOTES_VIGENTES, query = GerenciarLoteDAOImpl.LISTAR_LOTES_VIGENTES),
	@NamedQuery(name = GerenciarLoteDAOImpl.OBTER_LOTE_PARA_RELATORIO_ANALITICO_FATURA, query = GerenciarLoteDAOImpl.OBTER_LOTE_PARA_RELATORIO_ANALITICO_FATURA),
	@NamedQuery(name = GerenciarLoteDAOImpl.LISTAR_TODOS, query = GerenciarLoteDAOImpl.LISTAR_TODOS)
})
public class GerenciarLoteDAOImpl  extends GenericEntityDAOImpl<Lote> implements GerenciarLoteDAO {

	private static final long serialVersionUID = -7487930054649715622L;
	
	public static final String LISTAR_TODOS = "SELECT NEW Lote (l.id, l.numero, l.valorTotal, l.credenciado, l.notaFiscal.numero, l.cancelado) FROM Lote l";

	public static final String OBTER_QUANTIDADE_LOTES_POR_OM_ANO_E_CONFORMIDADE = "SELECT COUNT(id) FROM Lote WHERE YEAR(dataCriacao) = :ano AND organizacaoMilitar.id = :idOm AND conformidade = :conformidade";
	
	public static final String LISTAR_LOTES_VIGENTES = "SELECT NEW Lote (l.id, l.numero, l.valorTotal, l.credenciado, l.notaFiscal.numero, l.cancelado)"
			+ "FROM Lote l where l.cancelado = false";
	
	public static final String OBTER_LOTE_PARA_RELATORIO_ANALITICO_FATURA = "SELECT new Lote (lote.id, lote.numero, notaFiscal.numero, credenciado.nomeFantasia, om.nome, om.sigla) "
			+ "FROM Lote lote LEFT JOIN lote.notaFiscal as notaFiscal "
			+ "LEFT JOIN lote.credenciado as credenciado "
			+ "LEFT JOIN lote.organizacaoMilitar as om "
			+ "WHERE lote.id = :idLote";
	
	@Override
	public List<Lote> findAll(){
		return entityManager.createNamedQuery(LISTAR_TODOS, Lote.class).getResultList();	
	}

	@Override
	public Lote obterLoteComItensById(Integer idLote) {
		Lote lote = findById(idLote);
		Hibernate.initialize(lote.getItensGab());
		return lote;
	}
	
	@Override
	public int obterQuantidadeLotesPorOrganizacaoMilitarEAno(Integer idOm, Integer ano, boolean conformidade) {
		return entityManager.createNamedQuery(OBTER_QUANTIDADE_LOTES_POR_OM_ANO_E_CONFORMIDADE, Long.class).setParameter("ano", ano).setParameter("idOm", idOm).setParameter("conformidade", conformidade).getSingleResult().intValue();
	}

	@Override
	public Lote obterLoteParaRelatorioAnaliticoFatura(Integer idLote) {
		return entityManager.createNamedQuery(OBTER_LOTE_PARA_RELATORIO_ANALITICO_FATURA, Lote.class).setParameter("idLote", idLote).getSingleResult();
	}

	@Override
	public List<Lote> listarLotesVigentes() {
		return entityManager.createNamedQuery(LISTAR_LOTES_VIGENTES, Lote.class).getResultList();	
	}
		

	

}
