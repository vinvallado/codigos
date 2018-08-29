package br.ccasj.sisauc.auditoriaprospectiva.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.auditoriaprospectiva.dao.AuditoriaProspectivaRessarcimentoDAO;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectivaRessarcimento;
import br.ccasj.sisauc.auditoriaprospectiva.domain.RespostaRessarcimentoAuditoria;
import br.ccasj.sisauc.emissaoar.dao.AutorizacaoRessarcimentoDAO;
import br.ccasj.sisauc.emissaoar.dao.ItemARDAO;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.EstadoAR;
import br.ccasj.sisauc.emissaoar.domain.EstadoItemAR;
import br.ccasj.sisauc.emissaoar.domain.ItemAR;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;
import br.ccasj.sisauc.sdga.dao.AcaoSDGADAO;
import br.ccasj.sisauc.sdga.domain.CancelamentoAuditoriaRetrospectivaARE;

@MappedSuperclass
@Transactional
@Repository(value = "auditoriaProspectivaRessarcimentoDAO")
@NamedQueries({
	@NamedQuery(name = AuditoriaProspectivaRessarcimentoDAOImpl.OBTER_POR_SOLICITACAO, query = AuditoriaProspectivaRessarcimentoDAOImpl.OBTER_POR_SOLICITACAO),
	@NamedQuery(name = AuditoriaProspectivaRessarcimentoDAOImpl.OBTER_POR_BENEFICIARIO, query = AuditoriaProspectivaRessarcimentoDAOImpl.OBTER_POR_BENEFICIARIO)
})
public class AuditoriaProspectivaRessarcimentoDAOImpl extends GenericEntityDAOImpl<AuditoriaProspectivaRessarcimento> implements AuditoriaProspectivaRessarcimentoDAO {

	private static final long serialVersionUID = -7762200580478586646L;
	
	@Autowired
	private AutorizacaoRessarcimentoDAO areDAO;
	
	@Autowired
	private ItemARDAO itemARDAO;
	
	@Autowired
	private AcaoSDGADAO acaoSdgaDao;
	
	
	public static final String OBTER_POR_SOLICITACAO = "FROM AuditoriaProspectivaRessarcimento a WHERE a.solicitacao.id=:idSolicitacao";

	public static final String OBTER_POR_BENEFICIARIO = "select new AuditoriaProspectivaRessarcimento(auditoria.id, auditoria.estado, auditoria.dataFinalAuditoria, "
			+ "solicitacao.id, solicitacao.numero, "
			+ "resposta.id, resposta.justificativa, resposta.aprovado, resposta.observacaoARE, "
			+ "procedimento.id, procedimento.tabela, procedimento.codigo, procedimento.descricao, "
			+ "especialidade.id, especialidade.sigla) "
			+ "from AuditoriaProspectivaRessarcimento auditoria "
			+ "LEFT JOIN auditoria.solicitacao as solicitacao "
			+ "RIGHT JOIN auditoria.procedimentos as resposta "
			+ "LEFT JOIN resposta.procedimento as procedimento "
			+ "LEFT JOIN resposta.especialidade as especialidade "
			+ "LEFT JOIN solicitacao.beneficiario as beneficiario "
			+ "WHERE beneficiario.id = :idBeneficiario and (UPPER(procedimento.codigo) LIKE :textoPesquisa or UPPER(procedimento.descricao) LIKE :textoPesquisa or UPPER(especialidade.sigla) LIKE :textoPesquisa) "
			+ "order by auditoria.dataFinalAuditoria desc";
	
	
	
	@Override
	public AuditoriaProspectivaRessarcimento merge(AuditoriaProspectivaRessarcimento object) {
		for (RespostaRessarcimentoAuditoria resposta : object.getProcedimentos()) {
			if(resposta.getId() != null && resposta.getId() < 0){
				resposta.setId(null);
			}
		}
		return super.merge(object);
	}
	
	@Override
	public AuditoriaProspectivaRessarcimento obterAuditoriaPorSolicitacaoRessarcimentoComProcedimentos(Integer idSolicitacao) {
		AuditoriaProspectivaRessarcimento auditoria = entityManager.createNamedQuery(OBTER_POR_SOLICITACAO, AuditoriaProspectivaRessarcimento.class)
				.setParameter("idSolicitacao", idSolicitacao).getSingleResult();
		Hibernate.initialize(auditoria.getProcedimentos());
		return auditoria;
	}

	@Override
	public AuditoriaProspectivaRessarcimento obterAuditoriaPorIdComProcedimentosESolicitacao(Integer idAuditoria) {
		AuditoriaProspectivaRessarcimento auditoria = findById(idAuditoria);
		Hibernate.initialize(auditoria.getProcedimentos());
		Hibernate.initialize(auditoria.getSolicitacao().getProcedimentos());
		return auditoria;
	}
	
	@Override
	public void cancelarAuditoriaProspectivaRessarcimento(AutorizacaoRessarcimento are, String justificativa){
		are = areDAO.findById(are.getId());
		Hibernate.initialize(are.getItens());
		CancelamentoAuditoriaRetrospectivaARE cancelamento;
		Set<ItemAR> itens = new HashSet<ItemAR>();
		itens.addAll(are.getItens());
		for (ItemAR itemAR : itens) {
			cancelamento = new CancelamentoAuditoriaRetrospectivaARE();
			cancelamento.setJustificativa(justificativa);
			_preencherCancelamento(cancelamento, itemAR);
			itemAR.setEstadoItemAR(EstadoItemAR.EM_AUDITORIA);
			itemAR.getAr().setEstado(EstadoAR.EM_AUDITORIA);
			itemARDAO.merge(itemAR);
			acaoSdgaDao.merge(cancelamento);
		}
	}
	
	@Override
	public List<AuditoriaProspectivaRessarcimento> listarAuditoriasPorBeneficiario(Integer idBeneficiario, String textoPesquisa) {
		if(textoPesquisa == null){
			textoPesquisa = "";
		}
		textoPesquisa = "%"+textoPesquisa+"%";
		List<AuditoriaProspectivaRessarcimento> listaCompleta = entityManager.createNamedQuery(OBTER_POR_BENEFICIARIO, AuditoriaProspectivaRessarcimento.class).setParameter("idBeneficiario", idBeneficiario).setParameter("textoPesquisa", textoPesquisa.toUpperCase()).getResultList();
		List<AuditoriaProspectivaRessarcimento> result = new ArrayList<AuditoriaProspectivaRessarcimento>();

		for (AuditoriaProspectivaRessarcimento auditoria : listaCompleta) {
			if(!result.contains(auditoria)){
				result.add(auditoria);
			}
			result.get(result.indexOf(auditoria)).getProcedimentos().addAll(auditoria.getProcedimentos());
		}
		return result;
	}

	private void _preencherCancelamento (CancelamentoAuditoriaRetrospectivaARE cancelamento, ItemAR itemAR){
		cancelamento.setItemAR(itemAR);
		cancelamento.setValorApresentado(itemAR.getAuditoriaRetrospectiva().getValorApresentado());
		cancelamento.setValorEstimado(itemAR.getAuditoriaRetrospectiva().getValorEstimado());
		cancelamento.setValorRessarcimento(itemAR.getAuditoriaRetrospectiva().getValorRessarcimento());
		cancelamento.setNaoRealizado(itemAR.getAuditoriaRetrospectiva().getNaoRealizado());
		cancelamento.setJustificativaAuditRetrospARE(itemAR.getAuditoriaRetrospectiva().getJustificativa());
		cancelamento.setAuditoriaRetrospectivaRessarcimento(itemAR.getAuditoriaRetrospectiva());
	}
	
}
