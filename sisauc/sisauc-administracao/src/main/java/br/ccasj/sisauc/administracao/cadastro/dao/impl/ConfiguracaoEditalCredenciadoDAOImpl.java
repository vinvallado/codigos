package br.ccasj.sisauc.administracao.cadastro.dao.impl;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.administracao.cadastro.dao.ConfiguracaoEditalCredenciadoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciado;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;


@MappedSuperclass
@Transactional
@Repository(value = "configuracaoEditalCredenciadoDAO")
@NamedQueries({
	@NamedQuery(name = ConfiguracaoEditalCredenciadoDAOImpl.MUDAR_STATUS_ATIVO_CONFIGURACAO, query = ConfiguracaoEditalCredenciadoDAOImpl.MUDAR_STATUS_ATIVO_CONFIGURACAO),
	@NamedQuery(name = ConfiguracaoEditalCredenciadoDAOImpl.OBTER_CONFIGURACOES_ATIVAS_POR_EDITAL, query = ConfiguracaoEditalCredenciadoDAOImpl.OBTER_CONFIGURACOES_ATIVAS_POR_EDITAL),
	@NamedQuery(name = ConfiguracaoEditalCredenciadoDAOImpl.VERIFICAR_SE_EDITAL_POSSUI_CREDENCIADO, query = ConfiguracaoEditalCredenciadoDAOImpl.VERIFICAR_SE_EDITAL_POSSUI_CREDENCIADO)
})
public class ConfiguracaoEditalCredenciadoDAOImpl extends GenericEntityDAOImpl<ConfiguracaoEditalCredenciado> implements ConfiguracaoEditalCredenciadoDAO {
	
	private static final long serialVersionUID = 6686851550033305266L;

	public static final String MUDAR_STATUS_ATIVO_CONFIGURACAO = "update ConfiguracaoEditalCredenciado c set c.ativo = :status where c.id = :id";
	public static final String OBTER_CONFIGURACOES_ATIVAS_POR_EDITAL = "select new ConfiguracaoEditalCredenciado(cec.id, cec.ativo, c.id, c.nomeFantasia, cec.indiceReajustePorte, cec.indiceReajustePorteAnestesico, cec.indiceReajusteCustoOperacional, cec.indiceReajusteAuxiliares) from ConfiguracaoEditalCredenciado cec "
			+ "left join cec.credenciado as c where cec.edital.id = :idEdital and cec.ativo = true and c.ativo = true order by c.nomeFantasia, c.razaoSocial";
	public static final String VERIFICAR_SE_EDITAL_POSSUI_CREDENCIADO = "select case when (count(*) > 0) then true else false end from ConfiguracaoEditalCredenciado conf "
			+ "LEFT JOIN conf.credenciado as credenciado "
			+ "LEFT JOIN conf.edital as edital "
			+ "where :idCredenciado = credenciado.id "
			//verifica se o edital está vigente
			+ "and CURRENT_DATE BETWEEN edital.inicio and edital.fim "
			//verifica se o edital está ativo
			+ "and edital.ativo is true and conf.ativo is true";
	
	@Override
	public ConfiguracaoEditalCredenciado mudarStatusAtivoConfiguracao(Integer id, boolean status) {
		entityManager.createNamedQuery(MUDAR_STATUS_ATIVO_CONFIGURACAO).setParameter("id", id).setParameter("status", status).executeUpdate();
		return findById(id);
	}

	@Override
	public List<ConfiguracaoEditalCredenciado> obterConfiguracoesPorEdital(Integer idEdital) {
		return entityManager.createNamedQuery(OBTER_CONFIGURACOES_ATIVAS_POR_EDITAL, ConfiguracaoEditalCredenciado.class).setParameter("idEdital", idEdital).getResultList();
	}

	@Override
	public boolean verificarPresencaConfiguracaoComCredenciadoAtivoPorEdital(Integer idConfiguracao, Integer idCredenciado, Integer idEdital) {
		StringBuilder builder = new StringBuilder(); 
		builder.append("select case when (count(*) > 0) then true else false end from ConfiguracaoEditalCredenciado where "); 
		builder.append("edital.id = :idEdital and credenciado.id = :idCredenciado and ativo = true ");
		builder.append(idConfiguracao == null ? "" : " and id <> :idConfiguracao");
		TypedQuery<Boolean> query = entityManager.createQuery(builder.toString(), Boolean.class).setParameter("idEdital", idEdital).setParameter("idCredenciado", idCredenciado);
		if(idConfiguracao != null){
			query.setParameter("idConfiguracao", idConfiguracao);
		}
		return query.getSingleResult();
	}

	@Override
	public boolean verificarSeEditalPossuiCredenciado(Integer idCredenciado) {
		idCredenciado = idCredenciado == null ? -1 : idCredenciado;
		boolean resultado = (boolean) entityManager.createNamedQuery(VERIFICAR_SE_EDITAL_POSSUI_CREDENCIADO).setParameter("idCredenciado", idCredenciado).getSingleResult();
		if (resultado) {
			throw new SystemRuntimeException("Este credenciado não pode ser desativado pois pertence a um edital ativo e vigente. Desvincule-o do edital para prosseguir.");
		} else {
			return true;
		}
	}
	
	
	
}
