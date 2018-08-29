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

import br.ccasj.sisauc.emissaogab.domain.EstadoGAB;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.framework.context.AuthenticationContext;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;
import br.ccasj.sisauc.framework.domain.Perfil;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.framework.utils.DateUtils;
import br.ccasj.sisauc.pesquisa.domain.OpcoesPesquisaBoolean;
import br.ccasj.sisauc.pesquisa.domain.ParametrosPesquisaGAB;
import br.ccasj.sisauc.pesquisa.service.PesquisarGABService;

@Transactional
@Service(value = "pesquisarGABService")
public class PesquisarGABServiceImpl implements PesquisarGABService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private AuthenticationContext authenticationContext;

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override 
	public List<GuiaApresentacaoBeneficiario> pesquisar(ParametrosPesquisaGAB parametros) {
		validarParametrosPesquisa(parametros);
		
		StringBuilder builder = new StringBuilder("SELECT new GuiaApresentacaoBeneficiario(gab.id, gab.codigo, gab.divisao, gab.dataEmissao, gab.dataApresentacao, gab.estado, ")
				.append("beneficiario.nome, beneficiario.saram, beneficiario.titular, beneficiario.dataNascimento, ")
				.append("omBeneficiario.sigla, omTitular.sigla, ")
				.append("solicitacao.numero, profissional.nome, solicitacao.urgente, solicitacao.localInternacao, ")
				.append("credenciado.nomeFantasia, om.sigla) ")
				.append("FROM GuiaApresentacaoBeneficiario gab ")
				.append("LEFT JOIN gab.organizacaoMilitar as om ")
				.append("LEFT JOIN gab.credenciado as credenciado ")
				.append("LEFT JOIN gab.beneficiario as beneficiario ")
				.append("LEFT JOIN beneficiario.organizacaoMilitar as omBeneficiario ")
				.append("LEFT JOIN beneficiario.beneficiarioTitular as titular ")
				.append("LEFT JOIN titular.organizacaoMilitar as omTitular ")
				.append("LEFT JOIN gab.auditoriaProspectiva as auditoriaProspectiva ")
				.append("LEFT JOIN auditoriaProspectiva.solicitacao as solicitacao ")
				.append("LEFT JOIN solicitacao.hipoteseDiagnostica as cid ")
				.append("LEFT JOIN solicitacao.medicoSolicitante as profissional ")
				.append("WHERE 1 = 1 ");
		
		//verificar se o usuário é diretor
		if(authenticationContext.getUsuarioLogado().getPerfis().contains(Perfil.PERFIL_DIRETOR)){
			builder.append(parametros.getOrganizacoesMilitares() == null || parametros.getOrganizacoesMilitares().isEmpty() ? "" : "AND om.id in (" + obterListaOrganizacoesMilitaresParaQuery(parametros.getOrganizacoesMilitares()) + ") ");
		} else {
			builder.append("AND om.id = ").append(authenticationContext.getUsuarioLogado().getOrganizacaoMilitar().getId()).append(" ");
		}

		//Data emissao
		builder.append(parametros.getInicioDataEmissao() != null ? "AND gab.dataEmissao >= '"+ dateFormat.format(parametros.getInicioDataEmissao()) +"' ":"");
		builder.append(parametros.getFimDataEmissao() != null ? "AND gab.dataEmissao <= '"+ dateFormat.format(DateUtils.getEndOfDate(parametros.getFimDataEmissao())) +"' ":"");
		//Data apresentacao
		builder.append(parametros.getInicioDataApresentacao() != null ? "AND gab.dataApresentacao >= '"+ dateFormat.format(parametros.getInicioDataApresentacao()) +"' ":"");
		builder.append(parametros.getFimDataApresentacao() != null ? "AND gab.dataApresentacao <= '"+ dateFormat.format(DateUtils.getEndOfDate(parametros.getFimDataApresentacao())) +"' ":"");
		
		//Numero da solicitacao
		builder.append(parametros.getNumeroSolicitacao() == null || parametros.getNumeroSolicitacao().equals("") ? "" : "AND UPPER(solicitacao.numero) LIKE '%" + parametros.getNumeroSolicitacao().toUpperCase() + "%' ");
		//credenciado
		builder.append(parametros.getNomeCredenciado() == null || parametros.getNomeCredenciado().equals("") ? "" : "AND UPPER(credenciado.nomeFantasia) LIKE '%" + parametros.getNomeCredenciado().toUpperCase() + "%' ");
		//profissional
		builder.append(parametros.getNomeProfissional() == null || parametros.getNomeProfissional().equals("") ? "" : "AND UPPER(profissional.nome) LIKE '%" + parametros.getNomeProfissional().toUpperCase() + "%' ");
		//nome beneficiario
		builder.append(parametros.getNomeBeneficiario() == null || parametros.getNomeBeneficiario().equals("") ? "" : "AND UPPER(beneficiario.nome) LIKE '%" + parametros.getNomeBeneficiario().toUpperCase() + "%' ");
		//saram beneficiario
		builder.append(parametros.getSaram() == null || parametros.getSaram().equals("") ? "" : "AND UPPER(beneficiario.saram) LIKE '%" + parametros.getSaram().toUpperCase() + "%' ");		

		//titular
		builder.append(parametros.getTitular() != null && parametros.getTitular().equals(OpcoesPesquisaBoolean.SIM) ? "AND beneficiario.titular = true " : "");
		builder.append(parametros.getTitular() != null && parametros.getTitular().equals(OpcoesPesquisaBoolean.NAO) ? "AND beneficiario.titular = false " : "");
		
		// idade/data nascimento beneficiário
		Date dataNascimentoMaxima = parametros.getIdadeMinimaBeneficiario() == null ? null : DateUtils.addYears(new Date(), -parametros.getIdadeMinimaBeneficiario());
		Date dataNascimentoMinima = parametros.getIdadeMaximaBeneficiario() == null ? null : DateUtils.addYears(new Date(), -parametros.getIdadeMaximaBeneficiario()-1);
		builder.append(dataNascimentoMaxima != null ? "AND beneficiario.dataNascimento <= '" + dateFormat.format(dataNascimentoMaxima) + "'" : " ");
		builder.append(dataNascimentoMinima != null ? "AND beneficiario.dataNascimento >= '" + dateFormat.format(dataNascimentoMinima) + "'" : " ");

		//Urgente
		builder.append(parametros.getUrgente() != null && parametros.getUrgente().equals(OpcoesPesquisaBoolean.SIM) ? "AND solicitacao.urgente = true " : "");
		builder.append(parametros.getUrgente() != null && parametros.getUrgente().equals(OpcoesPesquisaBoolean.NAO) ? "AND solicitacao.urgente = false " : "");
		
		//internacao
		builder.append(parametros.getInternacao() != null && parametros.getInternacao().equals(OpcoesPesquisaBoolean.SIM) ? "AND solicitacao.internacao = true " : "");
		builder.append(parametros.getInternacao() != null && parametros.getInternacao().equals(OpcoesPesquisaBoolean.NAO) ? "AND solicitacao.internacao = false " : "");
		//Local de Internação
		builder.append(parametros.getTipoInternacao() == null || parametros.getInternacao().equals(OpcoesPesquisaBoolean.TODOS) || parametros.getInternacao().equals(OpcoesPesquisaBoolean.NAO) ? "" : "AND solicitacao.localInternacao in ('" + parametros.getTipoInternacao() + "') ");
		//CID
		builder.append(parametros.getCid() == null ? "" : "AND cid.id = " + parametros.getCid().getId()).append(" ");
		
		//Estados de GAB
		builder.append(parametros.getEstadosGAB() == null || parametros.getEstadosGAB().isEmpty() ? "" : "AND gab.estado in (" + obterListaEstadosParaQuery(parametros.getEstadosGAB()) + ") ");
		
		//Divisoes
		builder.append(parametros.getDivisoes() == null || parametros.getDivisoes().isEmpty() ? "" : "AND gab.divisao in (" + obterDivisoesParaQuery(parametros.getDivisoes()) + ") ");
		
		return entityManager.createQuery(builder.toString(), GuiaApresentacaoBeneficiario.class).getResultList();
	}

	private void validarParametrosPesquisa(ParametrosPesquisaGAB parametros) {
		if(parametros.getInicioDataEmissao() != null && parametros.getFimDataEmissao() != null && parametros.getFimDataEmissao().before(parametros.getInicioDataEmissao()))
			throw new SystemRuntimeException("Período de data de emissão incorreto");
		if(parametros.getInicioDataApresentacao() != null && parametros.getFimDataApresentacao() != null && parametros.getFimDataApresentacao().before(parametros.getInicioDataApresentacao()))
			throw new SystemRuntimeException("Período de data de apresentação incorreto");
	}

	private String obterListaEstadosParaQuery(List<EstadoGAB> estadosGAB) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < estadosGAB.size(); i++) {
			builder.append("'").append(estadosGAB.get(i).name()).append("'");
			builder.append(i < estadosGAB.size() - 1 ? "," : "");
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
