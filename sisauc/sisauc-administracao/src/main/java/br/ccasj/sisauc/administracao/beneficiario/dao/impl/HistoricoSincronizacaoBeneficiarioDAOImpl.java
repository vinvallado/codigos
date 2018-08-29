package br.ccasj.sisauc.administracao.beneficiario.dao.impl;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.administracao.beneficiario.dao.HistoricoSincronizacaoBeneficiarioDAO;
import br.ccasj.sisauc.administracao.beneficiario.domain.HistoricoSincronizacaoBeneficiario;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@MappedSuperclass
@Transactional
@Repository(value = "historicoSincronizacaoBeneficiarioDAO")
@NamedQueries({
	@NamedQuery(name = HistoricoSincronizacaoBeneficiarioDAOImpl.OBTER_MAIOR_DATA_SINCRONIZACAO, query = HistoricoSincronizacaoBeneficiarioDAOImpl.OBTER_MAIOR_DATA_SINCRONIZACAO)
})
public class HistoricoSincronizacaoBeneficiarioDAOImpl  extends GenericEntityDAOImpl<HistoricoSincronizacaoBeneficiario> implements HistoricoSincronizacaoBeneficiarioDAO {

	private static final long serialVersionUID = 8178836058461548117L;

	public static final String OBTER_MAIOR_DATA_SINCRONIZACAO = "select max(dataHoraInicio) from HistoricoSincronizacaoBeneficiario where estadoSincronizacao = 'FINALIZADO'";
	
	@PersistenceContext
	private EntityManager entityManager;


	@Override
	public Date obterDataUltimaSincronizacao() {
		return entityManager.createNamedQuery(OBTER_MAIOR_DATA_SINCRONIZACAO, Date.class).getSingleResult();
	}

	
	
}
