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

import br.ccasj.sisauc.emissaoar.domain.ItemAR;
import br.ccasj.sisauc.framework.context.AuthenticationContext;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;
import br.ccasj.sisauc.framework.domain.Perfil;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.framework.utils.DateUtils;
import br.ccasj.sisauc.pesquisa.domain.ComparacaoValoresItemARE;
import br.ccasj.sisauc.pesquisa.domain.OpcoesPesquisaBoolean;
import br.ccasj.sisauc.pesquisa.domain.OpcoesPesquisaValoresItemARE;
import br.ccasj.sisauc.pesquisa.domain.ParametrosPesquisaItemARE;
import br.ccasj.sisauc.pesquisa.service.PesquisarItemAREService;

@Transactional
@Service(value = "pesquisarItemAREService")
public class PesquisarItemAREServiceImpl implements PesquisarItemAREService {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private AuthenticationContext authenticationContext;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public List<ItemAR> pesquisar(ParametrosPesquisaItemARE parametros) {
		validarParametrosPesquisa(parametros);
		
		StringBuilder builder = new StringBuilder("SELECT new ItemAR(item.id, item.codigo, item.estadoItemAR, item.observacaoARE, ")
			.append("ar.codigo, ar.divisao, ar.estado, ar.dataGeracao, ar.dataEmissao, ar.dataApresentacao, ")
			.append("beneficiario.saram, beneficiario.nome, beneficiario.dataNascimento, beneficiario.titular, ")
			.append("omBeneficiario.sigla, omTitular.sigla, autorizacaoAr.dataNotaFiscal, autorizacaoAr.cpfCnpjPrestador, autorizacaoAr.numeroNotaFiscal, ")
			.append("procedimento.tabela, procedimento.codigo, procedimento.descricao, especialidade.sigla, ")
			.append("om.sigla, beneficiarioTitular.saram, ")
			.append("auditoriaRetrospectiva.valorApresentado, auditoriaRetrospectiva.valorEstimado, auditoriaRetrospectiva.valorRessarcimento) ")
			.append("FROM ApresentacaoAutorizacaoRessarcimento autorizacaoAr ")
			.append("RIGHT JOIN autorizacaoAr.ar as ar ")
			.append("LEFT JOIN ar.itens as item ")
			.append("LEFT JOIN ar.organizacaoMilitar as om ")
			.append("LEFT JOIN item.procedimento as procedimento ")
			.append("LEFT JOIN item.especialidade as especialidade ")
			
			.append("LEFT JOIN ar.beneficiario as beneficiario ")
			.append("LEFT JOIN beneficiario.beneficiarioTitular as beneficiarioTitular ")
			.append("LEFT JOIN beneficiario.organizacaoMilitar as omBeneficiario ")
			.append("LEFT JOIN beneficiarioTitular.organizacaoMilitar as omTitular ")
			
			.append("LEFT JOIN ar.auditoriaProspectiva as auditoriaProspectiva ")
			.append("LEFT JOIN auditoriaProspectiva.solicitacao as solicitacao ")
			.append("LEFT JOIN item.auditoriaRetrospectiva as auditoriaRetrospectiva ")
			.append("WHERE 1= 1 ");
		
			
		//verificar se o usuário é diretor
		if(authenticationContext.getUsuarioLogado().getPerfis().contains(Perfil.PERFIL_DIRETOR)){
			builder.append(parametros.getOrganizacoesMilitares() == null || parametros.getOrganizacoesMilitares().isEmpty() ? "" : "AND om.id in (" + obterListaOrganizacoesMilitaresParaQuery(parametros.getOrganizacoesMilitares()) + ") ");
		} else {
			builder.append("AND om.id = ").append(authenticationContext.getUsuarioLogado().getOrganizacaoMilitar().getId()).append(" ");
		}
		
		//Data geracao
		builder.append(parametros.getInicioDataGeracao() != null ? "AND ar.dataGeracao >= '"+ dateFormat.format(parametros.getInicioDataGeracao()) +"' ":"");
		builder.append(parametros.getFimDataGeracao() != null ? "AND ar.dataGeracao <= '"+ dateFormat.format(DateUtils.getEndOfDate(parametros.getFimDataGeracao())) +"' ":"");
		//Data emissao
		builder.append(parametros.getInicioDataEmissao() != null ? "AND ar.dataEmissao >= '"+ dateFormat.format(parametros.getInicioDataEmissao()) +"' ":"");
		builder.append(parametros.getFimDataEmissao() != null ? "AND ar.dataEmissao <= '"+ dateFormat.format(DateUtils.getEndOfDate(parametros.getFimDataEmissao())) +"' ":"");
		//Data apresentacao
		builder.append(parametros.getInicioDataApresentacao() != null ? "AND ar.dataApresentacao >= '"+ dateFormat.format(parametros.getInicioDataApresentacao()) +"' ":"");
		builder.append(parametros.getFimDataApresentacao() != null ? "AND ar.dataApresentacao <= '"+ dateFormat.format(DateUtils.getEndOfDate(parametros.getFimDataApresentacao())) +"' ":"");
		
		//codigo ItemARE
		builder.append(parametros.getCodigoItemARE() == null || parametros.getCodigoItemARE().equals("") ? "" : "AND UPPER(item.codigo) LIKE '%" + parametros.getCodigoItemARE().toUpperCase() + "%' ");
		//codigo ARE
		builder.append(parametros.getNumeroARE() == null || parametros.getNumeroARE().equals("") ? "" : "AND UPPER(ar.codigo) LIKE '%" + parametros.getNumeroARE().toUpperCase() + "%' ");
		
		//nome beneficiario
		builder.append(parametros.getNomeBeneficiario() == null || parametros.getNomeBeneficiario().equals("") ? "" : "AND UPPER(beneficiario.nome) LIKE '%" + parametros.getNomeBeneficiario().toUpperCase() + "%' ");
		//saram beneficiario
		builder.append(parametros.getSaram() == null || parametros.getSaram().equals("") ? "" : "AND UPPER(beneficiario.saram) LIKE '%" + parametros.getSaram().toUpperCase() + "%' ");		
		
		// idade/data nascimento beneficiário
		Date dataNascimentoMaxima = parametros.getIdadeMinimaBeneficiario() == null ? null : DateUtils.addYears(new Date(), -parametros.getIdadeMinimaBeneficiario());
		Date dataNascimentoMinima = parametros.getIdadeMaximaBeneficiario() == null ? null : DateUtils.addYears(new Date(), -parametros.getIdadeMaximaBeneficiario()-1);
		builder.append(dataNascimentoMaxima != null ? "AND beneficiario.dataNascimento <= '" + dateFormat.format(dataNascimentoMaxima) + "'" : " ");
		builder.append(dataNascimentoMinima != null ? "AND beneficiario.dataNascimento >= '" + dateFormat.format(dataNascimentoMinima) + "'" : " ");
		
		//procedimento
		builder.append(parametros.getProcedimento() == null || parametros.getProcedimento().equals("") ? "" : 
			"AND (UPPER(especialidade.sigla) LIKE '%" + parametros.getProcedimento().toUpperCase() + "%' " + 
			"OR UPPER(procedimento.codigo) LIKE '%" + parametros.getProcedimento().toUpperCase() + "%' " +
			"OR UPPER(procedimento.descricao) LIKE '%" + parametros.getProcedimento().toUpperCase() + "%') ");		

		//titular
		builder.append(parametros.getTitular() != null && parametros.getTitular().equals(OpcoesPesquisaBoolean.SIM) ? "AND beneficiario.titular = true " : "");
		builder.append(parametros.getTitular() != null && parametros.getTitular().equals(OpcoesPesquisaBoolean.NAO) ? "AND beneficiario.titular = false " : "");
	
		//Estados de ARE
		builder.append(parametros.getEstadosARE() == null || parametros.getEstadosARE().isEmpty() ? "" : "AND ar.estado in (" + obterListaParaQuery(parametros.getEstadosARE()) + ") ");
		//Estados de ItemARE
		builder.append(parametros.getEstadosItemARE() == null || parametros.getEstadosItemARE().isEmpty() ? "" : "AND item.estadoItemAR in (" + obterListaParaQuery(parametros.getEstadosItemARE()) + ") ");
		
		//numeroNotaFiscal nota fiscal
 		builder.append(parametros.getNumeroNotaFiscal() == null || parametros.getNumeroNotaFiscal().equals("") ? "" : "AND UPPER(autorizacaoAr.numeroNotaFiscal) LIKE '%" + parametros.getNumeroNotaFiscal().toUpperCase() + "%' ");
		
 		//Data nota fiscal
 		builder.append(parametros.getInicioDataNotaFiscal() != null ? "AND autorizacaoAr.dataNotaFiscal >= '"+ dateFormat.format(parametros.getInicioDataNotaFiscal()) +"' ":"");
 		builder.append(parametros.getFimDataNotaFiscal() != null ? "AND autorizacaoAr.dataNotaFiscal <= '"+ dateFormat.format(DateUtils.getEndOfDate(parametros.getFimDataNotaFiscal())) +"' ":"");

 		//cnpj/cpf nota fiscal
 		builder.append(parametros.getCpfCnpjNotaFiscal() == null || parametros.getCpfCnpjNotaFiscal().equals("") ? "" : "AND UPPER(autorizacaoAr.cpfCnpjPrestador) LIKE '%" + parametros.getCpfCnpjNotaFiscal().toUpperCase() + "%' ");
		
		//comparacao de valores
		for (ComparacaoValoresItemARE comparacao : parametros.getComparacoes()) {
			if (comparacao.getOpcaoComparacao() != null && comparacao.getValor1() != null
					&& comparacao.getValor2() != null) {
				if (comparacao.getValor2().equals(OpcoesPesquisaValoresItemARE.VALOR_DIGITADO)) {
					builder.append("AND " + comparacao.getValor1().getCampo()
							+ comparacao.getOpcaoComparacao().getCampo() + comparacao.getValorDigitado() + " ");
				} else {
					builder.append("AND " + comparacao.getValor1().getCampo()
							+ comparacao.getOpcaoComparacao().getCampo() + comparacao.getValor2().getCampo() + " ");
				}
			} else {
				builder.append(" ");
			}
		}
		
		//Divisoes
		builder.append(parametros.getDivisoes() == null || parametros.getDivisoes().isEmpty() ? "" : "AND ar.divisao in (" + obterDivisoesParaQuery(parametros.getDivisoes()) + ") ");
		
		//auditoriaRetrospectiva		
		builder.append("ORDER BY item.codigo");
		
		return entityManager.createQuery(builder.toString(), ItemAR.class).getResultList();
	}

	private void validarParametrosPesquisa(ParametrosPesquisaItemARE parametros) {
		if (parametros.getInicioDataGeracao() != null && parametros.getFimDataGeracao() != null
				&& parametros.getFimDataGeracao().before(parametros.getInicioDataGeracao()))
			throw new SystemRuntimeException("Período de data de geração incorreto");
		if (parametros.getInicioDataEmissao() != null && parametros.getFimDataEmissao() != null
				&& parametros.getFimDataEmissao().before(parametros.getInicioDataEmissao()))
			throw new SystemRuntimeException("Período de data de emissão incorreto");
		if (parametros.getInicioDataApresentacao() != null && parametros.getFimDataApresentacao() != null
				&& parametros.getFimDataApresentacao().before(parametros.getInicioDataApresentacao()))
			throw new SystemRuntimeException("Período de data de apresentação incorreto");
	}

	@SuppressWarnings("rawtypes")
	private String obterListaParaQuery(List<? extends Enum> itens) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < itens.size(); i++) {
			builder.append("'").append(itens.get(i).name()).append("'");
			builder.append(i < itens.size() - 1 ? "," : "");
		}
		return builder.toString();
	}

	private String obterListaOrganizacoesMilitaresParaQuery(List<OrganizacaoMilitar> organizacoesMilitares) {
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
