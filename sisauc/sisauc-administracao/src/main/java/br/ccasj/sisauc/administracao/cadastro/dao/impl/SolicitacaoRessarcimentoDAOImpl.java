package br.ccasj.sisauc.administracao.cadastro.dao.impl;

import java.util.Arrays;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.administracao.cadastro.dao.SolicitacaoRessarcimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.EstadoSolicitacaoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoPedidoSolicitacaoRessarcimento;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoRessarcimento;

@MappedSuperclass
@Transactional
@Repository(value = "solicitacaoRessarcimentoDAO")
@NamedQueries({
	@NamedQuery(name = "SOLICITACAO_PROCEDIMENTO_LISTAR_POR_ESTADO", query = SolicitacaoRessarcimentoDAOImpl.LISTAR_POR_ESTADO),
	@NamedQuery(name = "SOLICITACAO_PROCEDIMENTO_ATUALIZAR_ESTADO", query = SolicitacaoRessarcimentoDAOImpl.ATUALIZAR_ESTADO),
	@NamedQuery(name = "SOLICITACAO_PROCEDIMENTO_LISTAR_POR_ESTADO_E_OM", query = SolicitacaoRessarcimentoDAOImpl.LISTAR_POR_ESTADO_E_OM),
	@NamedQuery(name = "SOLICITACAO_PROCEDIMENTO_OBTER_QUANTIDADE_SOLICITACOES_RESSARCIMENTO_POR_OM_E_ANO", query = SolicitacaoRessarcimentoDAOImpl.OBTER_QUANTIDADE_SOLICITACOES_RESSARCIMENTO_POR_OM_E_ANO)
})
public class SolicitacaoRessarcimentoDAOImpl extends SolicitacaoMedicaDAOImpl<SolicitacaoRessarcimento> implements SolicitacaoRessarcimentoDAO {
	
	private static final long serialVersionUID = -7066800494865173738L;
		
	public static final String ATUALIZAR_ESTADO = "update SolicitacaoRessarcimento set estado = :estado, dataUltimaAlteracaoEstado = CURRENT_DATE where id = :idSolicitacao";

	public static final String LISTAR_POR_ESTADO = "select new SolicitacaoRessarcimento(s.id, s.divisao, s.numero, s.dataSolicitacaoSistema, s.dataEnvioAuditoria, s.dataInsercaoSistema, s.dataUltimaAlteracaoEstado, s.estado, s.naoEletiva, "
			+ "b.saram, b.nome, m.nome) from SolicitacaoRessarcimento s "
			+ "left join s.medicoSolicitante as m left join s.beneficiario as b left join s.organizacaoMilitarSolicitante as om where s.estado in :estados order by s.dataUltimaAlteracaoEstado";
	
	public static final String LISTAR_POR_ESTADO_E_OM = "select new SolicitacaoRessarcimento(s.id, s.divisao, s.numero, s.dataSolicitacaoSistema, s.dataEnvioAuditoria, s.dataInsercaoSistema, s.dataUltimaAlteracaoEstado, s.estado, s.naoEletiva, b.saram, b.nome, m.nome) from SolicitacaoRessarcimento s "
			+ "left join s.medicoSolicitante as m left join s.beneficiario as b left join s.organizacaoMilitarSolicitante as om where s.estado in :estados and om.id = :idOm order by s.dataUltimaAlteracaoEstado";

	public static final String OBTER_QUANTIDADE_SOLICITACOES_RESSARCIMENTO_POR_OM_E_ANO = "SELECT COUNT(id) FROM SolicitacaoRessarcimento WHERE YEAR(dataSolicitacaoSistema) = :ano AND organizacaoMilitarSolicitante.id = :idOm";

	
	@Override
	public void atualizarEstado(EstadoSolicitacaoProcedimento estado, Integer idSolicitacao) {
		entityManager.createNamedQuery("SOLICITACAO_PROCEDIMENTO_ATUALIZAR_ESTADO").setParameter("estado", estado)
		.setParameter("idSolicitacao", idSolicitacao).executeUpdate();
	}		
	
	@Override
	public List<SolicitacaoRessarcimento> listarPorEstado(EstadoSolicitacaoProcedimento... estados) {
		return entityManager.createNamedQuery("SOLICITACAO_PROCEDIMENTO_LISTAR_POR_ESTADO", SolicitacaoRessarcimento.class).setParameter("estados", Arrays.asList(estados)).getResultList();
	}
	
	@Override
	public List<SolicitacaoRessarcimento> listarPorEstadoEOrganizacaoMilitar(Integer idOm, EstadoSolicitacaoProcedimento... estados) {
		List<EstadoSolicitacaoProcedimento> list = Arrays.asList(estados);
		return entityManager.createNamedQuery("SOLICITACAO_PROCEDIMENTO_LISTAR_POR_ESTADO_E_OM", SolicitacaoRessarcimento.class).setParameter("estados", list).setParameter("idOm", idOm).getResultList();
	}

	@Override
	public SolicitacaoRessarcimento abrirComPedidos(Integer id) {
		SolicitacaoRessarcimento solicitacao = findById(id);
		Hibernate.initialize(solicitacao.getProcedimentos());
		return solicitacao;
	}	
	
	@Override
	public SolicitacaoRessarcimento merge(SolicitacaoRessarcimento solicitacao) {
		for (ProcedimentoPedidoSolicitacaoRessarcimento pedido : solicitacao.getProcedimentos()) {
			if(pedido.getId() < 0){
				pedido.setId(null);
			}
		}
		return super.merge(solicitacao);
	}

	@Override
	public int obterQuantidadeSolicitacoesRessarcimentoPorAnoEOM(Integer idOm, Integer ano) {
		return entityManager.createNamedQuery("SOLICITACAO_PROCEDIMENTO_OBTER_QUANTIDADE_SOLICITACOES_RESSARCIMENTO_POR_OM_E_ANO", Long.class)
				.setParameter("idOm", idOm).setParameter("ano", ano).getSingleResult().intValue();
	}
	
	
}
