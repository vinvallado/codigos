package br.ccasj.sisauc.administracao.cadastro.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.administracao.cadastro.dao.ConfiguracaoEditalCredenciadoProcedimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciado;
import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciadoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.Especialidade;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoBase;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoCBHPM2012;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;

@MappedSuperclass
@Transactional
@Repository(value = "configuracaoEditalCredenciadoProcedimentoDAO")
@NamedQueries({
	@NamedQuery(name = ConfiguracaoEditalCredenciadoProcedimentoDAOImpl.LISTAR_ATIVAS_POR_EDITAL, query = ConfiguracaoEditalCredenciadoProcedimentoDAOImpl.LISTAR_ATIVAS_POR_EDITAL),
	@NamedQuery(name = ConfiguracaoEditalCredenciadoProcedimentoDAOImpl.LISTAR_CONFIGURACOES_ATIVAS_E_EM_EDITAIS_VIGENTES, query = ConfiguracaoEditalCredenciadoProcedimentoDAOImpl.LISTAR_CONFIGURACOES_ATIVAS_E_EM_EDITAIS_VIGENTES),
	@NamedQuery(name = ConfiguracaoEditalCredenciadoProcedimentoDAOImpl.LISTAR_ATIVAS_POR_EDITAL_E_CREDENCIADO, query = ConfiguracaoEditalCredenciadoProcedimentoDAOImpl.LISTAR_ATIVAS_POR_EDITAL_E_CREDENCIADO),
	@NamedQuery(name = ConfiguracaoEditalCredenciadoProcedimentoDAOImpl.DESATIVAR_CONFIGURACOES, query = ConfiguracaoEditalCredenciadoProcedimentoDAOImpl.DESATIVAR_CONFIGURACOES)
})
public class ConfiguracaoEditalCredenciadoProcedimentoDAOImpl extends GenericEntityDAOImpl<ConfiguracaoEditalCredenciadoProcedimento> implements ConfiguracaoEditalCredenciadoProcedimentoDAO {

	private static final long serialVersionUID = -8710388449388333942L;

	public static final String LISTAR_ATIVAS_POR_EDITAL = ""
			+ "select new ConfiguracaoEditalCredenciadoProcedimento("
			+ "config.id, config.ativo, config.valor, config.tipo, "
			+ "credenciado.nomeFantasia, "
			+ "proc.tabela, proc.codigo, proc.descricao, "
			+ "esp.nome, esp.sigla) "
			+ "from ConfiguracaoEditalCredenciadoProcedimento config "
			+ "left join config.especialidade as esp left join config.procedimento as proc "
			+ "left join config.configuracaoEditalCredenciado as configCredenciado "
			+ "left join configCredenciado.credenciado as credenciado "
			+ "where configCredenciado.edital.id = :idEdital and config.ativo = true and configCredenciado.ativo = true";
	
	public static final String LISTAR_ATIVAS_POR_EDITAL_E_CREDENCIADO = LISTAR_ATIVAS_POR_EDITAL + " and credenciado.id=:idCredenciado";
	
	public static final String DESATIVAR_CONFIGURACOES = "UPDATE ConfiguracaoEditalCredenciadoProcedimento conf SET conf.ativo = false, conf.dataExclusao = :data WHERE conf in (:configuracoes)";
	
	public static final String LISTAR_CREDENCIADOS_VIGENTES_POR_PROCEDIMENTO_ESPECIALIDADE = ""
			+ "select new ConfiguracaoEditalCredenciadoProcedimento("
			+ "config.id, config.ativo, config.valor, config.tipo, "
			+ "credenciado.nomeFantasia, credenciado.cidade, credenciado.bairro, "
			+ "proc.tabela, proc.codigo, proc.descricao, "
			+ "esp.nome, esp.sigla) "
			+ "from ConfiguracaoEditalCredenciadoProcedimento config "
			+ "left join config.especialidade as esp "
			+ "left join config.procedimento as proc "
			+ "left join config.configuracaoEditalCredenciado as configCredenciado "
			+ "left join configCredenciado.credenciado as credenciado "
			+ "left join configCredenciado.edital as edital "
			//verifica se estão ativas
			+ "where config.ativo = true and configCredenciado.ativo = true "
			//verifica se o edital está vigente
			+ "and CURRENT_DATE BETWEEN edital.inicio and edital.fim "
			//verifica se o procedimento é o desejado
			+ "and proc.id = :idProcedimento and esp.id = :idEspecialidade order by credenciado.nomeFantasia";

	public static final String LISTAR_CONFIGURACOES_ATIVAS_E_EM_EDITAIS_VIGENTES = ""
			+ "select new ConfiguracaoEditalCredenciadoProcedimento("
			+ "config.id, config.ativo, config.valor, config.tipo, "
			+ "credenciado.nomeFantasia, cidadeCredenciado, "
			+ "proc.tabela, proc.codigo, proc.descricao, "
			+ "esp.nome, esp.sigla) "
			+ "from ConfiguracaoEditalCredenciadoProcedimento config "
			+ "left join config.especialidade as esp "
			+ "left join config.procedimento as proc "
			+ "left join config.configuracaoEditalCredenciado as configCredenciado "
			+ "left join configCredenciado.credenciado as credenciado "
			+ "left join credenciado.cidade as cidadeCredenciado "
			+ "left join configCredenciado.edital as edital "
			//verifica se estão ativas
			+ "where config.ativo = true and configCredenciado.ativo = true "
			//verifica se o edital está vigente
			+ "and CURRENT_DATE BETWEEN edital.inicio and edital.fim "
			//verifica se o edital está ativo
			+ "and edital.ativo = true "
			//verifica se o procedimento é o desejado
			+ "order by credenciado.nomeFantasia";

	
	
	@Override
	public List<ConfiguracaoEditalCredenciadoProcedimento> listarAtivasPorEdital(Integer idEdital) {
		return entityManager.createNamedQuery(LISTAR_ATIVAS_POR_EDITAL, ConfiguracaoEditalCredenciadoProcedimento.class).setParameter("idEdital", idEdital).getResultList();
	}
	
	@Override
	public List<ConfiguracaoEditalCredenciadoProcedimento> listarAtivasPorEditalECredenciado(Integer idEdital, Integer idCredenciado) {
		return entityManager.createNamedQuery(LISTAR_ATIVAS_POR_EDITAL_E_CREDENCIADO, ConfiguracaoEditalCredenciadoProcedimento.class)
				.setParameter("idEdital", idEdital).setParameter("idCredenciado", idCredenciado).getResultList();
	}

	@Override
	public boolean verificarPresencaCredenciadoProcedimentoEspecialidadePorEdital(Integer idConfig, Integer idConfiguracaoEditalCredenciado, ProcedimentoBase procedimento, Especialidade especialidade) {

		verificarParametrosConsulta(procedimento, especialidade);
		
		StringBuilder builder = new StringBuilder(); 
		builder.append("select case when (count(*) > 0) then true else false end from ConfiguracaoEditalCredenciadoProcedimento config ");
		builder.append("left join config.especialidade as esp left join config.procedimento as proc left join config.configuracaoEditalCredenciado as configCredenciado where ");
		builder.append("proc.id = :idProcedimento and configCredenciado.id = :idConfiguracaoEditalCredenciado ");
		builder.append("and config.ativo = true ");
		builder.append(especialidade == null || especialidade.getId() == null ? "" : " and esp.id = :idEspecialidade");
		builder.append(idConfig == null ? "" : " and config.id <> :idConfig");
		TypedQuery<Boolean> query = entityManager.createQuery(builder.toString(), Boolean.class)
				.setParameter("idConfiguracaoEditalCredenciado", idConfiguracaoEditalCredenciado)
				.setParameter("idProcedimento", procedimento.getId());
		if(especialidade != null && especialidade.getId() != null){
			query.setParameter("idEspecialidade", especialidade.getId());
		}
		if(idConfig != null){
			query.setParameter("idConfig", idConfig);
		}
		return query.getSingleResult();
	}
	
	@Override
	public List<ConfiguracaoEditalCredenciadoProcedimento> listarCredenciadosVigentesPorProcedimentoEspecialidade(ProcedimentoBase procedimento, Especialidade especialidade) {
		
		verificarParametrosConsulta(procedimento, especialidade);
		
		StringBuilder builder = new StringBuilder();
		builder.append("select new ConfiguracaoEditalCredenciadoProcedimento("
				+ "config.id, config.ativo, config.valor, config.tipo, "
				+ "credenciado.nomeFantasia, credenciado.cidade, credenciado.bairro, "
				+ "proc.tabela, proc.codigo, proc.descricao, "
				+ "esp.nome, esp.sigla)");
		builder.append("from ConfiguracaoEditalCredenciadoProcedimento config ");
		builder.append("left join config.especialidade as esp ");
		builder.append("left join config.procedimento as proc ");
		builder.append("left join config.configuracaoEditalCredenciado as configCredenciado ");
		builder.append("left join configCredenciado.credenciado as credenciado ");
		builder.append("left join configCredenciado.edital as edital ");
		//verifica se estão ativas
		builder.append("where config.ativo = true and configCredenciado.ativo = true ");
		//verifica se o edital está vigente
		builder.append("and CURRENT_DATE BETWEEN edital.inicio and edital.fim ");
		//verifica se o edital está ativo
		builder.append("and edital.ativo = true ");
		//verifica se o credenciado está ativo
		builder.append("and credenciado.ativo = true ");
		//verifica se o procedimento é o desejado
		builder.append("and proc.codigo = :codigoProcedimento ");
		builder.append(especialidade != null && especialidade.getId() != null ? "and esp.id = :idEspecialidade " : "");
		builder.append("order by credenciado.nomeFantasia");
		
		TypedQuery<ConfiguracaoEditalCredenciadoProcedimento> query = entityManager.createQuery(builder.toString(), ConfiguracaoEditalCredenciadoProcedimento.class)
				.setParameter("codigoProcedimento", procedimento.getCodigo());
		if(especialidade != null && especialidade.getId() != null){
			query.setParameter("idEspecialidade", especialidade.getId());
		}
		
		return query.getResultList();
	}

	private void verificarParametrosConsulta(ProcedimentoBase procedimento, Especialidade especialidade) {
		if(procedimento == null){
			throw new SystemRuntimeException("É necessário escolher um procedimento");
		}
		if(procedimento instanceof ProcedimentoCBHPM2012 && ProcedimentoCBHPM2012.CODIGO_CONSULTA.equalsIgnoreCase(((ProcedimentoCBHPM2012)procedimento).getSubGrupo().getCodigo()) && (especialidade == null || especialidade.getId() == null)){
			throw new SystemRuntimeException("Para este procedimento é necessário escolher uma especialidade");
		}
	}
	
	@Override
	public List<ConfiguracaoEditalCredenciadoProcedimento> listarConfiguracoesAtivasEmEditaisVigentes() {
		return entityManager.createNamedQuery(LISTAR_CONFIGURACOES_ATIVAS_E_EM_EDITAIS_VIGENTES, ConfiguracaoEditalCredenciadoProcedimento.class).getResultList();
	}
	
	@Override
	public void desativar(List<ConfiguracaoEditalCredenciadoProcedimento> configuracoes) {
		entityManager.createNamedQuery(DESATIVAR_CONFIGURACOES).setParameter("data", new Date()).setParameter("configuracoes", configuracoes).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ConfiguracaoEditalCredenciadoProcedimento> listarAtivasPorEditalLazyModel(int first, int pageSize, String sortField, String sortOrder, Map<String, Object> filters) {
		StringBuilder builder = new StringBuilder("select config from ConfiguracaoEditalCredenciadoProcedimento config ");
		builder.append(montarQueryLazyPorFiltros(filters));
		builder.append(montarOrderByParaLazy(sortField, sortOrder));
		return (List<ConfiguracaoEditalCredenciadoProcedimento>)entityManager.createQuery(builder.toString()).setFirstResult(first).setMaxResults(pageSize).getResultList();
	}	
	
	private String montarQueryLazyPorFiltros(Map<String, Object> filters){
		StringBuilder builder = new StringBuilder();
		builder.append(" "
			+ "left join config.especialidade as especialidade "
			+ "left join config.procedimento as procedimento "
			+ "left join config.configuracaoEditalCredenciado as configCredenciado "
			+ "left join configCredenciado.credenciado as credenciado ");

		builder.append("WHERE configCredenciado.edital.id = " + filters.get("edital") + " and config.ativo = true and credenciado.ativo = true and configCredenciado.ativo = true ");
		if(filters.get("procedimento") != null){
			String value = ((String)filters.get("procedimento")).toUpperCase();
			builder.append("AND (UPPER(procedimento.codigo) LIKE '%"+value+"%' OR ");
			builder.append("UPPER(procedimento.descricao) LIKE '%"+value+"%' OR ");
			builder.append("UPPER(especialidade.sigla) LIKE '%"+value+"%') ");
		}
		if(filters.get("tipo") != null){
			String value = (String) filters.get("tipo");
			builder.append("AND config.tipo = '"+ value + "' ");
		}
		if(filters.get("credenciado") != null){
			ConfiguracaoEditalCredenciado credenciado = (ConfiguracaoEditalCredenciado)filters.get("credenciado");
			builder.append("AND credenciado.id = " + credenciado.getCredenciado().getId() + " ");
		}
		
		return builder.toString();
	}
	
	private String montarOrderByParaLazy(String coluna, String order){
		StringBuilder builder = new StringBuilder("ORDER BY ");
		if(coluna == null || coluna.equals("") || coluna.equals("credenciado")){
			builder.append("credenciado.nomeFantasia");
		} else if(coluna.equals("procedimento")){
			builder.append("procedimento.codigo");
		} else if(coluna.equals("tipo")){
			builder.append("config.tipo");
		} else if(coluna.equals("valor")){
			builder.append("config.valor");
		}
		return builder.append("desc".equals(order) ? " desc" : "").toString();
	};

	@Override
	public int obterQuantidadeResultadosLazyModel(Map<String, Object> filters) {
		StringBuilder builder = new StringBuilder("select count(*) from ConfiguracaoEditalCredenciadoProcedimento config ");
		builder.append(montarQueryLazyPorFiltros(filters));
		return (int)(long)entityManager.createQuery(builder.toString(), Long.class).getSingleResult();
	}
	
	
}
