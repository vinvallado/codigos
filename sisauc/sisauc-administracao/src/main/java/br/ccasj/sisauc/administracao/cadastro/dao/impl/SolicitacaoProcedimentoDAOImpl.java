package br.ccasj.sisauc.administracao.cadastro.dao.impl;

import java.util.Arrays;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.administracao.cadastro.dao.SolicitacaoProcedimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.EstadoSolicitacaoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoPedidoSolicitacao;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoProcedimento;

@MappedSuperclass
@Transactional
@Repository(value = "solicitacaoProcedimentoDAO")
@NamedQueries({
	@NamedQuery(name = "SOLICITACAO_RESSARCIMENTO_ATUALIZAR_ESTADO", query = SolicitacaoProcedimentoDAOImpl.ATUALIZAR_ESTADO),
	@NamedQuery(name = "SOLICITACAO_RESSARCIMENTO_LISTAR_POR_ESTADO", query = SolicitacaoProcedimentoDAOImpl.LISTAR_POR_ESTADO),
	@NamedQuery(name = "SOLICITACAO_RESSARCIMENTO_LISTAR_POR_ESTADO_E_OM", query = SolicitacaoProcedimentoDAOImpl.LISTAR_POR_ESTADO_E_OM),
	@NamedQuery(name = "SOLICITACAO_RESSARCIMENTO_OBTER_SOLICITACAO_POR_SARAM", query = SolicitacaoProcedimentoDAOImpl.OBTER_SOLICITACAO_POR_SARAM),
	@NamedQuery(name = "SOLICITACAO_RESSARCIMENTO_OBTER_SOLICITACAO_POR_SARAM_TITULAR", query = SolicitacaoProcedimentoDAOImpl.OBTER_SOLICITACAO_POR_SARAM_TITULAR),
	@NamedQuery(name = "SOLICITACAO_RESSARCIMENTO_OBTER_SOLICITACAO_POR_CPF_TITULAR", query = SolicitacaoProcedimentoDAOImpl.OBTER_SOLICITACAO_POR_CPF_TITULAR),
	@NamedQuery(name = "SOLICITACAO_RESSARCIMENTO_OBTER_SOLICITACAO_POR_CPF", query = SolicitacaoProcedimentoDAOImpl.OBTER_SOLICITACAO_POR_CPF),
	@NamedQuery(name = "SOLICITACAO_RESSARCIMENTO_OBTER_SOLICITACAO_POR_CODIGO", query = SolicitacaoProcedimentoDAOImpl.OBTER_SOLICITACAO_POR_CODIGO),
	@NamedQuery(name = "SOLICITACAO_RESSARCIMENTO_OBTER_QUANTIDADE_SOLICITACOES_PROCEDIMENTO_POR_OM_E_ANO", query = SolicitacaoProcedimentoDAOImpl.OBTER_QUANTIDADE_SOLICITACOES_PROCEDIMENTO_POR_OM_E_ANO) 
})
public class SolicitacaoProcedimentoDAOImpl extends SolicitacaoMedicaDAOImpl<SolicitacaoProcedimento> implements SolicitacaoProcedimentoDAO {

	private static final long serialVersionUID = 8310856786051646619L;
		
	public static final String ATUALIZAR_ESTADO = "update SolicitacaoProcedimento set estado = :estado, dataUltimaAlteracaoEstado = CURRENT_DATE where id = :idSolicitacao";

	public static final String LISTAR_POR_ESTADO = "select new SolicitacaoProcedimento(s.id, s.divisao, s.numero, s.dataSolicitacaoSistema, s.dataEnvioAuditoria, s.dataInsercaoSistema, s.dataUltimaAlteracaoEstado, s.estado, s.urgente, m.nome, b.saram, b.nome) from SolicitacaoProcedimento s "
			+ "left join s.medicoSolicitante as m left join s.beneficiario as b where s.estado in :estados order by s.urgente desc, s.dataUltimaAlteracaoEstado";
	
	public static final String LISTAR_POR_ESTADO_E_OM = "select new SolicitacaoProcedimento(s.id, s.divisao, s.numero, s.dataSolicitacaoSistema, s.dataEnvioAuditoria, s.dataInsercaoSistema, s.dataUltimaAlteracaoEstado, s.estado, s.urgente, m.nome, b.saram, b.nome) from SolicitacaoProcedimento s "
			+ "left join s.medicoSolicitante as m left join s.beneficiario as b left join s.organizacaoMilitarSolicitante as om where s.estado in :estados and om.id = :idOm order by s.urgente desc, s.dataUltimaAlteracaoEstado";

	public static final String OBTER_QUANTIDADE_SOLICITACOES_PROCEDIMENTO_POR_OM_E_ANO = "SELECT COUNT(id) FROM SolicitacaoProcedimento WHERE YEAR(dataSolicitacaoSistema) = :ano AND organizacaoMilitarSolicitante.id = :idOm";

	public static final String OBTER_SOLICITACAO_POR_SARAM = "select new SolicitacaoProcedimento(s.id, s.divisao, s.numero, s.dataSolicitacaoSistema, s.dataEnvioAuditoria, s.dataInsercaoSistema, s.dataUltimaAlteracaoEstado, s.estado, m.nome, b.saram, b.nome) from SolicitacaoProcedimento s "
			+ "left join s.medicoSolicitante as m left join s.beneficiario as b where b.saram = :saram order by s.dataUltimaAlteracaoEstado";
	
	public static final String OBTER_SOLICITACAO_POR_SARAM_TITULAR = "select new SolicitacaoProcedimento(s.id, s.divisao, s.numero, s.dataSolicitacaoSistema, s.dataEnvioAuditoria, s.dataInsercaoSistema, s.dataUltimaAlteracaoEstado, s.estado, m.nome, b.saram, b.nome) from SolicitacaoProcedimento s "
			+ "left join s.medicoSolicitante as m left join s.beneficiario as b left join b.beneficiarioTitular as bt where bt.saram = :saram order by s.dataUltimaAlteracaoEstado";
	
	public static final String OBTER_SOLICITACAO_POR_CPF = "select new SolicitacaoProcedimento(s.id, s.divisao, s.numero, s.dataSolicitacaoSistema, s.dataEnvioAuditoria, s.dataInsercaoSistema, s.dataUltimaAlteracaoEstado, s.estado, m.nome, b.saram, b.nome) from SolicitacaoProcedimento s "
			+ "left join s.medicoSolicitante as m left join s.beneficiario as b where b.cpf = :cpf order by s.dataUltimaAlteracaoEstado";
	
	public static final String OBTER_SOLICITACAO_POR_CODIGO = "select new SolicitacaoProcedimento(s.id, s.divisao, s.numero, s.dataSolicitacaoSistema, s.dataEnvioAuditoria, s.dataInsercaoSistema, s.dataUltimaAlteracaoEstado, s.estado, m.nome, b.saram, b.nome) from SolicitacaoProcedimento s "
			+ "left join s.medicoSolicitante as m left join s.beneficiario as b where s.numero = :numero order by s.dataUltimaAlteracaoEstado";
	
	public static final String OBTER_SOLICITACAO_POR_CPF_TITULAR = "select new SolicitacaoProcedimento(s.id, s.divisao, s.numero, s.dataSolicitacaoSistema, s.dataEnvioAuditoria, s.dataInsercaoSistema, s.dataUltimaAlteracaoEstado, s.estado, m.nome, b.saram, b.nome) from SolicitacaoProcedimento s "
			+ "left join s.medicoSolicitante as m left join s.beneficiario as b left join b.beneficiarioTitular as bt where bt.cpf = :cpf order by s.dataUltimaAlteracaoEstado";

	@Override
	public void atualizarEstado(EstadoSolicitacaoProcedimento estado, Integer idSolicitacao) {
		entityManager.createNamedQuery("SOLICITACAO_RESSARCIMENTO_ATUALIZAR_ESTADO").setParameter("estado", estado)
		.setParameter("idSolicitacao", idSolicitacao).executeUpdate();
	}	
	
	@Override
	public List<SolicitacaoProcedimento> listarPorEstado(EstadoSolicitacaoProcedimento... estados) {
		List<EstadoSolicitacaoProcedimento> list = Arrays.asList(estados);
		return entityManager.createNamedQuery("SOLICITACAO_RESSARCIMENTO_LISTAR_POR_ESTADO", SolicitacaoProcedimento.class).setParameter("estados", list).getResultList();
	}
	
	@Override
	public List<SolicitacaoProcedimento> listarPorEstadoEOrganizacaoMilitar(Integer idOm, EstadoSolicitacaoProcedimento... estados) {
		List<EstadoSolicitacaoProcedimento> list = Arrays.asList(estados);
		return entityManager.createNamedQuery("SOLICITACAO_RESSARCIMENTO_LISTAR_POR_ESTADO_E_OM", SolicitacaoProcedimento.class).setParameter("estados", list).setParameter("idOm", idOm).getResultList();
	}	

	@Override
	public List<SolicitacaoProcedimento> buscarSolicitacaoPorSaram(String saram) {
		return entityManager.createNamedQuery("SOLICITACAO_RESSARCIMENTO_OBTER_SOLICITACAO_POR_SARAM", SolicitacaoProcedimento.class)
				.setParameter("saram", saram).getResultList();
	}

	@Override
	public SolicitacaoProcedimento merge(SolicitacaoProcedimento solicitacaoProcedimento) {
		for (ProcedimentoPedidoSolicitacao pedido : solicitacaoProcedimento.getProcedimentos()) {
			if (pedido.getId() < 0) {
				pedido.setId(null);
			}
		}
		return super.merge(solicitacaoProcedimento);
	}

	@Override
	public SolicitacaoProcedimento abrirComPedidos(Integer id) {
		SolicitacaoProcedimento solicitacaoProcedimento = findById(id);
		Hibernate.initialize(solicitacaoProcedimento.getProcedimentos());
		return solicitacaoProcedimento;
	}

	@Override
	public int obterQuantidadeSolicitacoesProcedimentoPorAnoEOM(Integer idOm, Integer ano) {
		return entityManager.createNamedQuery("SOLICITACAO_RESSARCIMENTO_OBTER_QUANTIDADE_SOLICITACOES_PROCEDIMENTO_POR_OM_E_ANO", Long.class)
				.setParameter("idOm", idOm).setParameter("ano", ano).getSingleResult().intValue();
	}

	@Override
	public List<SolicitacaoProcedimento> buscarSolicitacaoPorSaramTitular(String saram) {
		return entityManager.createNamedQuery("SOLICITACAO_RESSARCIMENTO_OBTER_SOLICITACAO_POR_SARAM_TITULAR", SolicitacaoProcedimento.class)
				.setParameter("saram", saram).getResultList();
	}
	
	@Override
	public List<SolicitacaoProcedimento> buscarSolicitacaoPorCpf(String cpf) {
		return entityManager.createNamedQuery("SOLICITACAO_RESSARCIMENTO_OBTER_SOLICITACAO_POR_CPF", SolicitacaoProcedimento.class)
				.setParameter("cpf", cpf).getResultList();
	}
	
	@Override
	public List<SolicitacaoProcedimento> buscarSolicitacaoPorCpfTitular(String cpf) {
		return entityManager.createNamedQuery("SOLICITACAO_RESSARCIMENTO_OBTER_SOLICITACAO_POR_CPF_TITULAR", SolicitacaoProcedimento.class)
				.setParameter("cpf", cpf).getResultList();
	}

	@Override
	public List<SolicitacaoProcedimento> buscarSolicitacaoPorCodigo(String numero) {
		return entityManager.createNamedQuery("SOLICITACAO_RESSARCIMENTO_OBTER_SOLICITACAO_POR_CODIGO", SolicitacaoProcedimento.class)
				.setParameter("numero", numero).getResultList();
	}

}
