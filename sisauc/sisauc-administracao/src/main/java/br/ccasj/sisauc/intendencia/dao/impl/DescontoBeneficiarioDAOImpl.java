package br.ccasj.sisauc.intendencia.dao.impl;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;
import br.ccasj.sisauc.intendencia.dao.DescontoBeneficiarioDAO;
import br.ccasj.sisauc.intendencia.domain.DescontoBeneficiario;

@MappedSuperclass
@Transactional
@Repository(value = "descontoBeneficiarioDAO")
@NamedQueries({
	@NamedQuery(name = DescontoBeneficiarioDAOImpl.OBTER_QUANTIDADE_DESCONTOS_POR_ANO_E_BENEFICIARIO, query = DescontoBeneficiarioDAOImpl.OBTER_QUANTIDADE_DESCONTOS_POR_ANO_E_BENEFICIARIO),
	@NamedQuery(name = DescontoBeneficiarioDAOImpl.OBTER_DESCONTOS_NAO_ENVIADOS, query = DescontoBeneficiarioDAOImpl.OBTER_DESCONTOS_NAO_ENVIADOS),
	@NamedQuery(name = DescontoBeneficiarioDAOImpl.OBTER_DESCONTO_POR_ITEM_GAB, query = DescontoBeneficiarioDAOImpl.OBTER_DESCONTO_POR_ITEM_GAB)
})
public class DescontoBeneficiarioDAOImpl  extends GenericEntityDAOImpl<DescontoBeneficiario> implements DescontoBeneficiarioDAO {

	private static final long serialVersionUID = 4439608245492464672L;

	public static final String OBTER_QUANTIDADE_DESCONTOS_POR_ANO_E_BENEFICIARIO = "SELECT COUNT(id) FROM DescontoBeneficiario WHERE YEAR(dataEmissao) = :ano AND beneficiario.id = :idBeneficiario";
	
	public static final String OBTER_DESCONTOS_NAO_ENVIADOS = "FROM DescontoBeneficiario b where b.estadoDescontoBeneficiario = 'NAO_FINALIZADO'";
	
	public static final String OBTER_DESCONTO_POR_ITEM_GAB = "SELECT desconto from DescontoBeneficiario desconto WHERE :itemGAB MEMBER OF desconto.itensGabDescontados";

	@Override
	public int obterQuantidadeDescontosPorAnoEBeneficiario(Integer id, Integer ano) {
		return entityManager.createNamedQuery(OBTER_QUANTIDADE_DESCONTOS_POR_ANO_E_BENEFICIARIO, Long.class).setParameter("ano", ano).setParameter("idBeneficiario", id).getSingleResult().intValue();
	}

	@Override
	public List<DescontoBeneficiario> listarDescontos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DescontoBeneficiario> obterDescontosNaoEnvidaos() {
		List<DescontoBeneficiario> resultado = entityManager.createNamedQuery(OBTER_DESCONTOS_NAO_ENVIADOS, DescontoBeneficiario.class).getResultList();
		if (resultado != null){
			for (DescontoBeneficiario descontoBeneficiario : resultado) {
				Hibernate.initialize(descontoBeneficiario.getEnviosDesconto());
			}
		}
		return resultado;
	}
	
	@Override
	public DescontoBeneficiario obterComEnviosPorId(Integer id) {
		DescontoBeneficiario resultado = super.findById(id);
		Hibernate.initialize(resultado.getEnviosDesconto());
		return resultado;
	}
	
	@Override
	public DescontoBeneficiario obterCompletoPorId(Integer id) {
		DescontoBeneficiario resultado = super.findById(id);
		Hibernate.initialize(resultado.getEnviosDesconto());
		Hibernate.initialize(resultado.getItensGabDescontados());
		return resultado;
	}

	@Override
	public DescontoBeneficiario obterDescontoPorItemGAB(ItemGAB itemGAB) {
		List<DescontoBeneficiario> descontos = entityManager.createNamedQuery(OBTER_DESCONTO_POR_ITEM_GAB, DescontoBeneficiario.class).setParameter("itemGAB", itemGAB).getResultList();
		return descontos.isEmpty() ? null : descontos.get(0);
	}

}
