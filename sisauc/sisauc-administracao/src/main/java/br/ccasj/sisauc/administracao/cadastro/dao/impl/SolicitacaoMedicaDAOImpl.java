package br.ccasj.sisauc.administracao.cadastro.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.administracao.cadastro.dao.SolicitacaoMedicaDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.EstadoSolicitacaoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoMedica;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@MappedSuperclass
@Transactional
@Repository(value = "solicitacaoMedicaDAO")
@NamedQueries({
	@NamedQuery(name = SolicitacaoMedicaDAOImpl.LISTAR_POR_ESTADO_E_OM, query = SolicitacaoMedicaDAOImpl.LISTAR_POR_ESTADO_E_OM),
	@NamedQuery(name = SolicitacaoMedicaDAOImpl.OBTER_SOLICITACAO_MEDICA_POR_SARAM, query = SolicitacaoMedicaDAOImpl.OBTER_SOLICITACAO_MEDICA_POR_SARAM),
	@NamedQuery(name = SolicitacaoMedicaDAOImpl.OBTER_SOLICITACAO_MEDICA_POR_SARAM_TITULAR, query = SolicitacaoMedicaDAOImpl.OBTER_SOLICITACAO_MEDICA_POR_SARAM_TITULAR),
	@NamedQuery(name = SolicitacaoMedicaDAOImpl.OBTER_SOLICITACAO_MEDICA_POR_CPF, query = SolicitacaoMedicaDAOImpl.OBTER_SOLICITACAO_MEDICA_POR_CPF),
	@NamedQuery(name = SolicitacaoMedicaDAOImpl.OBTER_SOLICITACAO_MEDICA_POR_CPF_TITULAR, query = SolicitacaoMedicaDAOImpl.OBTER_SOLICITACAO_MEDICA_POR_CPF_TITULAR),
	@NamedQuery(name = SolicitacaoMedicaDAOImpl.OBTER_SOLICITACAO_MEDICA_POR_CODIGO, query = SolicitacaoMedicaDAOImpl.OBTER_SOLICITACAO_MEDICA_POR_CODIGO)
})
public class SolicitacaoMedicaDAOImpl<T extends SolicitacaoMedica> extends GenericEntityDAOImpl<SolicitacaoMedica> implements SolicitacaoMedicaDAO<T> {

	private static final long serialVersionUID = -2207010116825370260L;

	public static final String LISTAR_POR_ESTADO_E_OM = "from SolicitacaoMedica s left join s.organizacaoMilitarSolicitante as om where s.estado in :estados and om.id = :idOm";
	
	public static final String OBTER_SOLICITACAO_MEDICA_POR_SARAM = "select new SolicitacaoMedica(s.id, s.numero, s.dataSolicitacaoSistema, s.dataEnvioAuditoria, s.dataInsercaoSistema, s.dataUltimaAlteracaoEstado, s.estado, m.nome, b.saram, b.nome, om.id) from SolicitacaoMedica s "
			+ "left join s.medicoSolicitante as m left join s.beneficiario as b left join s.organizacaoMilitarSolicitante as om where b.saram = :saram order by s.dataUltimaAlteracaoEstado";
	
	public static final String OBTER_SOLICITACAO_MEDICA_POR_SARAM_TITULAR = "select new SolicitacaoMedica(s.id, s.numero, s.dataSolicitacaoSistema, s.dataEnvioAuditoria, s.dataInsercaoSistema, s.dataUltimaAlteracaoEstado, s.estado, m.nome, b.saram, b.nome, om.id) from SolicitacaoMedica s "
			+ "left join s.medicoSolicitante as m left join s.beneficiario as b left join b.beneficiarioTitular as bt left join s.organizacaoMilitarSolicitante as om where bt.saram = :saram order by s.dataUltimaAlteracaoEstado";
	
	public static final String OBTER_SOLICITACAO_MEDICA_POR_CPF = "select new SolicitacaoMedica(s.id, s.numero, s.dataSolicitacaoSistema, s.dataEnvioAuditoria, s.dataInsercaoSistema, s.dataUltimaAlteracaoEstado, s.estado, m.nome, b.saram, b.nome, om.id) from SolicitacaoMedica s "
			+ "left join s.medicoSolicitante as m left join s.beneficiario as b left join s.organizacaoMilitarSolicitante as om where b.cpf = :cpf order by s.dataUltimaAlteracaoEstado";
	
	public static final String OBTER_SOLICITACAO_MEDICA_POR_CPF_TITULAR = "select new SolicitacaoMedica(s.id, s.numero, s.dataSolicitacaoSistema, s.dataEnvioAuditoria, s.dataInsercaoSistema, s.dataUltimaAlteracaoEstado, s.estado, m.nome, b.saram, b.nome, om.id) from SolicitacaoMedica s "
			+ "left join s.medicoSolicitante as m left join s.beneficiario as b left join b.beneficiarioTitular as bt left join s.organizacaoMilitarSolicitante as om where bt.cpf = :cpf order by s.dataUltimaAlteracaoEstado";
	
	public static final String OBTER_SOLICITACAO_MEDICA_POR_CODIGO = "select new SolicitacaoMedica(s.id, s.numero, s.dataSolicitacaoSistema, s.dataEnvioAuditoria, s.dataInsercaoSistema, s.dataUltimaAlteracaoEstado, s.estado, m.nome, b.saram, b.nome, om.id) from SolicitacaoMedica s "
			+ "left join s.medicoSolicitante as m left join s.beneficiario as b left join s.organizacaoMilitarSolicitante as om where s.numero = :numero order by s.dataUltimaAlteracaoEstado";
	
	protected Class<T> type;
	
	@SuppressWarnings("unchecked")
	public SolicitacaoMedicaDAOImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class<T>) pt.getActualTypeArguments()[0];
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T merge(T object) {
		return (T) super.merge(object);
	}
	
	@Override
	public T findById(Integer id) {
		return entityManager.find(type, id);
	}
	
	@Override
	public List<T> listarPorEstado(EstadoSolicitacaoProcedimento... auditoriaProspectivas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> listarPorEstadoEOrganizacaoMilitar(Integer idOm,
			EstadoSolicitacaoProcedimento... auditoriaProspectivas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T abrirComPedidos(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void atualizarEstado(EstadoSolicitacaoProcedimento estado, Integer idSolicitacao) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> buscarSolicitacaoPorSaram(String saram) {
		return entityManager.createNamedQuery(OBTER_SOLICITACAO_MEDICA_POR_SARAM)
				.setParameter("saram", saram).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> buscarSolicitacaoPorSaramTitular(String saram) {
		return entityManager.createNamedQuery(OBTER_SOLICITACAO_MEDICA_POR_SARAM_TITULAR)
				.setParameter("saram", saram).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> buscarSolicitacaoPorCpf(String cpf) {
		return entityManager.createNamedQuery(OBTER_SOLICITACAO_MEDICA_POR_CPF)
				.setParameter("cpf", cpf).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> buscarSolicitacaoPorCpfTitular(String cpf) {
		return entityManager.createNamedQuery(OBTER_SOLICITACAO_MEDICA_POR_CPF_TITULAR)
				.setParameter("cpf", cpf).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> buscarSolicitacaoPorCodigo(String numero) {
		return entityManager.createNamedQuery(OBTER_SOLICITACAO_MEDICA_POR_CODIGO)
				.setParameter("numero", numero).getResultList();
	}

}
