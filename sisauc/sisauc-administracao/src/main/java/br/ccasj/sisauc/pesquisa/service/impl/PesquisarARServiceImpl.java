package br.ccasj.sisauc.pesquisa.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.EstadoAR;
import br.ccasj.sisauc.framework.context.AuthenticationContext;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;
import br.ccasj.sisauc.framework.domain.Perfil;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.framework.utils.DateUtils;
import br.ccasj.sisauc.pesquisa.domain.OpcoesPesquisaBoolean;
import br.ccasj.sisauc.pesquisa.domain.ParametrosPesquisaAR;
import br.ccasj.sisauc.pesquisa.service.PesquisarARService;

@Transactional
@Service(value = "pesquisarARService")
public class PesquisarARServiceImpl implements PesquisarARService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private AuthenticationContext authenticationContext;

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override 
	public List<AutorizacaoRessarcimento> pesquisar(ParametrosPesquisaAR parametros) {
		validarParametrosPesquisa(parametros);
		
		StringBuilder builder = new StringBuilder("SELECT new AutorizacaoRessarcimento(ar.id, ar.codigo, ar.divisao, ar.dataEmissao, ar.dataApresentacao, ar.estado, ")
				.append("beneficiario.nome, beneficiario.saram, beneficiario.titular, ")
				.append("omBeneficiario.sigla, omTitular.sigla, ")
				.append("om.sigla, autorizacaoAr.dataNotaFiscal, autorizacaoAr.cpfCnpjPrestador, autorizacaoAr.numeroNotaFiscal, ")
				.append("procedimento.tabela, procedimento.codigo, procedimento.descricao, especialidade.sigla) ")
				.append("FROM ApresentacaoAutorizacaoRessarcimento autorizacaoAr ")
				.append("RIGHT JOIN autorizacaoAr.ar as ar ")
				.append("LEFT JOIN ar.organizacaoMilitar as om ")
				.append("LEFT JOIN ar.beneficiario as beneficiario ")
				.append("LEFT JOIN beneficiario.organizacaoMilitar as omBeneficiario ")
				.append("LEFT JOIN beneficiario.beneficiarioTitular as titular ")
				.append("LEFT JOIN titular.organizacaoMilitar as omTitular ")
				.append("LEFT JOIN ar.itens as item ")
				.append("LEFT JOIN item.especialidade as especialidade ")
				.append("LEFT JOIN item.procedimento as procedimento ")
				.append("WHERE 1 = 1 ");
		
		//verificar se o usuário é diretor
		if(authenticationContext.getUsuarioLogado().getPerfis().contains(Perfil.PERFIL_DIRETOR)){
			builder.append(parametros.getOrganizacoesMilitares() == null || parametros.getOrganizacoesMilitares().isEmpty() ? "" : "AND om.id in (" + obterListaOrganizacoesMilitaresParaQuery(parametros.getOrganizacoesMilitares()) + ") ");
		} else {
			builder.append("AND om.id = ").append(authenticationContext.getUsuarioLogado().getOrganizacaoMilitar().getId()).append(" ");
		}

		//Numero da ARE
		builder.append(parametros.getNumeroAR() == null || parametros.getNumeroAR().equals("") ? "" : "AND UPPER(ar.codigo) LIKE '%" + parametros.getNumeroAR().toUpperCase() + "%' ");

		//nome beneficiario
		builder.append(parametros.getNomeBeneficiario() == null || parametros.getNomeBeneficiario().equals("") ? "" : "AND UPPER(beneficiario.nome) LIKE '%" + parametros.getNomeBeneficiario().toUpperCase() + "%' ");
		
		//saram beneficiario
		builder.append(parametros.getSaram() == null || parametros.getSaram().equals("") ? "" : "AND UPPER(beneficiario.saram) LIKE '%" + parametros.getSaram().toUpperCase() + "%' ");		

		//procedimento
		builder.append(parametros.getProcedimento() == null || parametros.getProcedimento().equals("") ? "" : 
			"AND (UPPER(especialidade.sigla) LIKE '%" + parametros.getProcedimento().toUpperCase() + "%' " + 
			"OR UPPER(procedimento.codigo) LIKE '%" + parametros.getProcedimento().toUpperCase() + "%' " +
			"OR UPPER(procedimento.descricao) LIKE '%" + parametros.getProcedimento().toUpperCase() + "%') ");
				
		//Data emissao
		builder.append(parametros.getInicioDataEmissao() != null ? "AND ar.dataEmissao >= '"+ dateFormat.format(parametros.getInicioDataEmissao()) +"' ":"");
		builder.append(parametros.getFimDataEmissao() != null ? "AND ar.dataEmissao <= '"+ dateFormat.format(DateUtils.getEndOfDate(parametros.getFimDataEmissao())) +"' ":"");
		
		//Data apresentacao
		builder.append(parametros.getInicioDataApresentacao() != null ? "AND ar.dataApresentacao >= '"+ dateFormat.format(parametros.getInicioDataApresentacao()) +"' ":"");
		builder.append(parametros.getFimDataApresentacao() != null ? "AND ar.dataApresentacao <= '"+ dateFormat.format(DateUtils.getEndOfDate(parametros.getFimDataApresentacao())) +"' ":"");

 		//Data nota fiscal
 		builder.append(parametros.getInicioDataNotaFiscal() != null ? "AND autorizacaoAr.dataNotaFiscal >= '"+ dateFormat.format(parametros.getInicioDataNotaFiscal()) +"' ":"");
 		builder.append(parametros.getFimDataNotaFiscal() != null ? "AND autorizacaoAr.dataNotaFiscal <= '"+ dateFormat.format(DateUtils.getEndOfDate(parametros.getFimDataNotaFiscal())) +"' ":"");

 		//cnpj/cpf nota fiscal
 		builder.append(parametros.getCpfCnpjNotaFiscal() == null || parametros.getCpfCnpjNotaFiscal().equals("") ? "" : "AND UPPER(autorizacaoAr.cpfCnpjPrestador) LIKE '%" + parametros.getCpfCnpjNotaFiscal().toUpperCase() + "%' ");
		
		//titular
		builder.append(parametros.getTitular() != null && parametros.getTitular().equals(OpcoesPesquisaBoolean.SIM) ? "AND beneficiario.titular = true " : "");
		builder.append(parametros.getTitular() != null && parametros.getTitular().equals(OpcoesPesquisaBoolean.NAO) ? "AND beneficiario.titular = false " : "");
		
		//Estados de ARE
		builder.append(parametros.getEstadosAR() == null || parametros.getEstadosAR().isEmpty() ? "" : "AND ar.estado in (" + obterListaEstadosParaQuery(parametros.getEstadosAR()) + ") ");
		
		//Divisoes
		builder.append(parametros.getDivisoes() == null || parametros.getDivisoes().isEmpty() ? "" : "AND ar.divisao in (" + obterDivisoesParaQuery(parametros.getDivisoes()) + ") ");

		List<AutorizacaoRessarcimento> ares = agruparResultado(entityManager.createQuery(builder.toString(), AutorizacaoRessarcimento.class).getResultList());
		
		return ares;
	}
	
	private List<AutorizacaoRessarcimento> agruparResultado(List<AutorizacaoRessarcimento> resultList) {
		List<AutorizacaoRessarcimento> finalResult = new ArrayList<AutorizacaoRessarcimento>();
		for (AutorizacaoRessarcimento autorizacaoRessarcimento : resultList) {
			if(!finalResult.contains(autorizacaoRessarcimento)){
				finalResult.add(autorizacaoRessarcimento);
			}
		}
		return finalResult;
	}

	private void validarParametrosPesquisa(ParametrosPesquisaAR parametros) {
		if(parametros.getInicioDataEmissao() != null && parametros.getFimDataEmissao() != null && parametros.getFimDataEmissao().before(parametros.getInicioDataEmissao()))
			throw new SystemRuntimeException("Período de data de emissão incorreto");
		if(parametros.getInicioDataApresentacao() != null && parametros.getFimDataApresentacao() != null && parametros.getFimDataApresentacao().before(parametros.getInicioDataApresentacao()))
			throw new SystemRuntimeException("Período de data de apresentação incorreto");
	}

	private String obterListaEstadosParaQuery(List<EstadoAR> estadosAR) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < estadosAR.size(); i++) {
			builder.append("'").append(estadosAR.get(i).name()).append("'");
			builder.append(i < estadosAR.size() - 1 ? "," : "");
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
