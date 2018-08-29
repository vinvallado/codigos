package br.ccasj.sisauc.pesquisa.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.domain.EstadoSolicitacaoProcedimento;
import br.ccasj.sisauc.auditoriaprospectiva.domain.EstadoAuditoriaProspectiva;
import br.ccasj.sisauc.framework.context.AuthenticationContext;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;
import br.ccasj.sisauc.framework.domain.Perfil;
import br.ccasj.sisauc.framework.utils.DateUtils;
import br.ccasj.sisauc.pesquisa.domain.OpcoesPesquisaBoolean;
import br.ccasj.sisauc.pesquisa.domain.ParametrosPesquisaSolicitacaoRessarcimento;
import br.ccasj.sisauc.pesquisa.domain.ResultadoPesquisaSolicitacaoRessarcimentoVO;
import br.ccasj.sisauc.pesquisa.service.PesquisarSolicitacaoRessarcimentoService;

@Transactional
@Service(value = "pesquisarSolicitacaoRessarcimentoService")
public class PesquisarSolicitacaoRessarcimentoServiceImpl implements PesquisarSolicitacaoRessarcimentoService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private AuthenticationContext authenticationContext;

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultadoPesquisaSolicitacaoRessarcimentoVO> pesquisar(ParametrosPesquisaSolicitacaoRessarcimento parametros) {
		
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		StringBuilder builder = new StringBuilder("SELECT DISTINCT new br.ccasj.sisauc.pesquisa.domain.ResultadoPesquisaSolicitacaoRessarcimentoVO(solicitacao.numero, solicitacao.divisao, beneficiario.nome, beneficiario.saram, ")
			.append("beneficiario.titular, beneficiario.dataNascimento, omBeneficiario.sigla, omTitular.sigla, solicitacao.estado, solicitacao.dataSolicitacaoSistema, solicitacao.localInternacao, om.sigla, auditoria.estado) ")
			.append("FROM AuditoriaProspectivaRessarcimento auditoria ")
			.append("RIGHT JOIN auditoria.solicitacao as solicitacao ")
			.append("LEFT JOIN solicitacao.organizacaoMilitarSolicitante as om ")
			
			.append("LEFT JOIN solicitacao.beneficiario as beneficiario ")
			.append("LEFT JOIN beneficiario.beneficiarioTitular as beneficiarioTitular ")
			.append("LEFT JOIN beneficiario.organizacaoMilitar as omBeneficiario ")
			.append("LEFT JOIN beneficiarioTitular.organizacaoMilitar as omTitular ")
			
			.append("LEFT JOIN solicitacao.hipoteseDiagnostica as cid ")
			.append("LEFT JOIN solicitacao.procedimentos as procedimentoPedidoSolicitacao ")
			.append("LEFT JOIN procedimentoPedidoSolicitacao.procedimento as procedimento ")
			.append("LEFT JOIN procedimentoPedidoSolicitacao.especialidade as especialidade ")
			.append("WHERE solicitacao.class = 'RESSARCIMENTO' ");
		
		//verificar se o usuário é diretor
		if(authenticationContext.getUsuarioLogado().getPerfis().contains(Perfil.PERFIL_DIRETOR)){
			builder.append(parametros.getOrganizacoesMilitares() == null || parametros.getOrganizacoesMilitares().isEmpty() ? "" : "AND om.id in (" + obterListaOrganizacoesMilitaresParaQuery(parametros.getOrganizacoesMilitares()) + ") ");
		} else {
			builder.append("AND om.id = ").append(authenticationContext.getUsuarioLogado().getOrganizacaoMilitar().getId()).append(" ");
		}
		
		//Código Solicitação Ressarcimento
		builder.append(parametros.getNumeroSolicitacaoRessarcimento() == null || parametros.getNumeroSolicitacaoRessarcimento().equals("") ? "" : "AND UPPER(solicitacao.numero) LIKE '%" + parametros.getNumeroSolicitacaoRessarcimento().toUpperCase() + "%' ");
		
		//Nome do Beneficiário
		builder.append(parametros.getNomeBeneficiario() == null || parametros.getNomeBeneficiario().equals("") ? "" : "AND UPPER(beneficiario.nome) LIKE '%" + parametros.getNomeBeneficiario().toUpperCase() + "%' ");
		
		//Saram do Beneficiário
		builder.append(parametros.getSaramBeneficiario() == null || parametros.getSaramBeneficiario().equals("") ? "" : "AND UPPER(beneficiario.saram) LIKE '%" + parametros.getSaramBeneficiario().toUpperCase() + "%' ");	
		
		// idade/data nascimento beneficiário
		Date dataNascimentoMaxima = parametros.getIdadeMinimaBeneficiario() == null ? null : DateUtils.addYears(new Date(), -parametros.getIdadeMinimaBeneficiario());
		Date dataNascimentoMinima = parametros.getIdadeMaximaBeneficiario() == null ? null : DateUtils.addYears(new Date(), -parametros.getIdadeMaximaBeneficiario()-1);
		builder.append(dataNascimentoMaxima != null ? "AND beneficiario.dataNascimento <= '" + dateFormat.format(dataNascimentoMaxima) + "'" : " ");
		builder.append(dataNascimentoMinima != null ? "AND beneficiario.dataNascimento >= '" + dateFormat.format(dataNascimentoMinima) + "'" : " ");

		//ProcedimentoBase
		builder.append(parametros.getProcedimento() == null || parametros.getProcedimento().equals("") ? "" : 
			"AND UPPER(procedimento.codigo) LIKE '%" + parametros.getProcedimento().toUpperCase() + "%' " +
			"OR UPPER(procedimento.descricao) LIKE '%" + parametros.getProcedimento().toUpperCase() + "%' " +
			"OR (UPPER(especialidade.sigla) LIKE '%" + parametros.getProcedimento().toUpperCase() + "%') "); 
		
		//Titular
		builder.append(parametros.getTitular() != null && parametros.getTitular().equals(OpcoesPesquisaBoolean.SIM) ? "AND beneficiario.titular = true " : "");
		builder.append(parametros.getTitular() != null && parametros.getTitular().equals(OpcoesPesquisaBoolean.NAO) ? "AND beneficiario.titular = false " : "");
		
		//Urgente(Não ELETIVO)
//		builder.append(parametros.getUrgente() != null && parametros.getUrgente().equals(OpcoesPesquisaBoolean.SIM) ? "AND solicitacao.urgente = true " : "");
//		builder.append(parametros.getUrgente() != null && parametros.getUrgente().equals(OpcoesPesquisaBoolean.NAO) ? "AND solicitacao.urgente = false " : "");
		
		//internacao
		builder.append(parametros.getInternacao() != null && parametros.getInternacao().equals(OpcoesPesquisaBoolean.SIM) ? "AND solicitacao.internacao = true " : "");
		builder.append(parametros.getInternacao() != null && parametros.getInternacao().equals(OpcoesPesquisaBoolean.NAO) ? "AND solicitacao.internacao = false " : "");	
		
		//Local de Internação
		builder.append(parametros.getLocalInternacao() == null || parametros.getInternacao().equals(OpcoesPesquisaBoolean.TODOS) || parametros.getInternacao().equals(OpcoesPesquisaBoolean.NAO) ? "" : "AND solicitacao.localInternacao in ('" + parametros.getLocalInternacao() + "') ");
		
		//CID
		builder.append(parametros.getCid() == null ? "" : "AND cid.id = " + parametros.getCid().getId()).append(" ");
		
		//Estados da Solicitacao
		builder.append(parametros.getEstadosSolicitacaoProcedimento() == null || parametros.getEstadosSolicitacaoProcedimento().isEmpty() ? "" : "AND solicitacao.estado in (" + obterListaEstadosParaQuery(parametros.getEstadosSolicitacaoProcedimento()) + ") ");
		
		//Data Solicitacao
		builder.append(parametros.getInicioDataSolicitacao() != null ? "AND solicitacao.dataSolicitacaoSistema >= '"+ dateFormat.format(parametros.getInicioDataSolicitacao()) +"' ":"");
		builder.append(parametros.getFimDataSolicitacao() != null ? "AND solicitacao.dataSolicitacaoSistema <= '"+ dateFormat.format(DateUtils.getEndOfDate(parametros.getFimDataSolicitacao())) +"' ":"");
		
		//Estados da Auditoria Prospectiva
		builder.append(parametros.getEstadoAuditoriaProspectiva() == null || parametros.getEstadoAuditoriaProspectiva().isEmpty() ? "" : "AND auditoria.estado in (" + obterListaEstadosAuditoriaParaQuery(parametros.getEstadoAuditoriaProspectiva()) + ") ");
	
		//Divisoes
		builder.append(parametros.getDivisoes() == null || parametros.getDivisoes().isEmpty() ? "" : "AND solicitacao.divisao in (" + obterDivisoesParaQuery(parametros.getDivisoes()) + ") ");
		
		
		builder.append("ORDER BY solicitacao.numero");
		
		
		return entityManager.createQuery(builder.toString()).getResultList();
	}
	
	
	private String obterListaEstadosParaQuery(List<EstadoSolicitacaoProcedimento> estadoSolicitacaoProcedimento) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < estadoSolicitacaoProcedimento.size(); i++) {
			builder.append("'").append(estadoSolicitacaoProcedimento.get(i).name()).append("'");
			builder.append(i < estadoSolicitacaoProcedimento.size() - 1 ? "," : "");
		}
		return builder.toString();
	}
	
	private String obterListaEstadosAuditoriaParaQuery(List<EstadoAuditoriaProspectiva> estadoAuditoria) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < estadoAuditoria.size(); i++) {
			builder.append("'").append(estadoAuditoria.get(i).name()).append("'");
			builder.append(i < estadoAuditoria.size() - 1 ? "," : "");
		}
		return builder.toString();
	}

	private String obterListaOrganizacoesMilitaresParaQuery(List<OrganizacaoMilitar> organizacoesMilitares){
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < organizacoesMilitares.size(); i++) {
			builder.append("'").append(organizacoesMilitares.get(i).getId()).append("'");
			builder.append(i < organizacoesMilitares.size() - 1 ? "," : "");
		}
		return builder.toString();	
	}
	
	private String obterDivisoesParaQuery(List<Divisao> divisao) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < divisao.size(); i++) {
			builder.append("'").append(divisao.get(i).name()).append("'");
			builder.append(i < divisao.size() - 1 ? "," : "");
		}
		return builder.toString();
	}
}
