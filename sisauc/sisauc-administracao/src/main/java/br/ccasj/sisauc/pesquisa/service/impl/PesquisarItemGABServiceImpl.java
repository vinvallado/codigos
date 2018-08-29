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

import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.context.AuthenticationContext;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;
import br.ccasj.sisauc.framework.domain.Perfil;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.framework.utils.DateUtils;
import br.ccasj.sisauc.pesquisa.domain.ComparacaoValoresItemGAB;
import br.ccasj.sisauc.pesquisa.domain.OpcoesPesquisaBoolean;
import br.ccasj.sisauc.pesquisa.domain.OpcoesPesquisaValoresItemGAB;
import br.ccasj.sisauc.pesquisa.domain.ParametrosPesquisaItemGAB;
import br.ccasj.sisauc.pesquisa.service.PesquisarItemGABService;

@Transactional
@Service(value = "pesquisarItemGABService")
public class PesquisarItemGABServiceImpl implements PesquisarItemGABService {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private AuthenticationContext authenticationContext;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// Construtor da pesquisa
	// public ItemGAB(Integer id, String codigo, EstadoItemGAB estado, Double
	// valorTotal,
	// String codigoGAB, EstadoGAB estadoGAB, Date dataEmissaoGAB, Date
	// dataImpressaoGAB, Date dataApresentacaoGAB,
	// String saram, String nomeBeneficiario, Date dataNascimentoBeneficiario,
	// boolean titular,
	// String codigoProcedimento, String descricaoProcedimento, String
	// siglaEspecialidadeProcedimento,
	// String nomeProfissional,
	// Double valorApresentado, Double valorFinal, Double valorAuditado){

	@Override
	public List<ItemGAB> pesquisar(ParametrosPesquisaItemGAB parametros) {
		validarParametrosPesquisa(parametros);
		
		StringBuilder builder = new StringBuilder("SELECT new ItemGAB(item.id, item.codigo, item.estadoItemGAB, item.observacaoGAB, item.valorTotal, ")
			.append("gab.codigo, gab.divisao, gab.estado, gab.dataGeracao, gab.dataEmissao, gab.dataApresentacao, ")
			.append("beneficiario.saram, beneficiario.nome, beneficiario.dataNascimento, beneficiario.titular, ")
			.append("omBeneficiario.sigla, omTitular.sigla, ")
			.append("credenciado.nomeFantasia, ")
			.append("procedimento.tabela, procedimento.codigo, procedimento.descricao, subGrupo.descricao, grupoProcedimento.descricao, ")
			.append("especialidade.sigla, profissional.nome, ")
			.append("auditoriaRetrospectiva.valorApresentado, auditoriaRetrospectiva.valorFinal, auditoriaRetrospectiva.valorAuditado, om.sigla, beneficiarioTitular.saram, lote.numero) FROM Lote lote ")
			.append("RIGHT JOIN lote.itensGab item ")
			.append("LEFT JOIN item.gab as gab ")
			.append("LEFT JOIN gab.organizacaoMilitar as om ")
			.append("LEFT JOIN item.configuracao as configuracao ")
			.append("LEFT JOIN configuracao.especialidade as especialidade ")
			.append("LEFT JOIN configuracao.procedimento as procedimento ")
			.append("LEFT JOIN procedimento.subGrupo as subGrupo ")
			.append("LEFT JOIN subGrupo.grupoProcedimento as grupoProcedimento ")
			
			.append("LEFT JOIN gab.beneficiario as beneficiario ")
			.append("LEFT JOIN beneficiario.beneficiarioTitular as beneficiarioTitular ")
			.append("LEFT JOIN beneficiario.organizacaoMilitar as omBeneficiario ")
			.append("LEFT JOIN beneficiarioTitular.organizacaoMilitar as omTitular ")
			
			.append("LEFT JOIN gab.credenciado as credenciado ")
			.append("LEFT JOIN gab.auditoriaProspectiva as auditoriaProspectiva ")
			.append("LEFT JOIN auditoriaProspectiva.solicitacao as solicitacao ")
			.append("LEFT JOIN solicitacao.medicoSolicitante as profissional ")
			.append("LEFT JOIN item.auditoriaRetrospectiva as auditoriaRetrospectiva ")
			.append("WHERE 1= 1 ");
		
			
		//verificar se o usuário é diretor
		if(authenticationContext.getUsuarioLogado().getPerfis().contains(Perfil.PERFIL_DIRETOR)){
			builder.append(parametros.getOrganizacoesMilitares() == null || parametros.getOrganizacoesMilitares().isEmpty() ? "" : "AND om.id in (" + obterListaOrganizacoesMilitaresParaQuery(parametros.getOrganizacoesMilitares()) + ") ");
		} else {
			builder.append("AND om.id = ").append(authenticationContext.getUsuarioLogado().getOrganizacaoMilitar().getId()).append(" ");
		}
		
		//Data geracao
		builder.append(parametros.getInicioDataGeracao() != null ? "AND gab.dataGeracao >= '"+ dateFormat.format(parametros.getInicioDataGeracao()) +"' ":"");
		builder.append(parametros.getFimDataGeracao() != null ? "AND gab.dataGeracao <= '"+ dateFormat.format(DateUtils.getEndOfDate(parametros.getFimDataGeracao())) +"' ":"");
		//Data emissao
		builder.append(parametros.getInicioDataEmissao() != null ? "AND gab.dataEmissao >= '"+ dateFormat.format(parametros.getInicioDataEmissao()) +"' ":"");
		builder.append(parametros.getFimDataEmissao() != null ? "AND gab.dataEmissao <= '"+ dateFormat.format(DateUtils.getEndOfDate(parametros.getFimDataEmissao())) +"' ":"");
		//Data apresentacao
		builder.append(parametros.getInicioDataApresentacao() != null ? "AND gab.dataApresentacao >= '"+ dateFormat.format(parametros.getInicioDataApresentacao()) +"' ":"");
		builder.append(parametros.getFimDataApresentacao() != null ? "AND gab.dataApresentacao <= '"+ dateFormat.format(DateUtils.getEndOfDate(parametros.getFimDataApresentacao())) +"' ":"");
		
		//codigo ItemGAB
		builder.append(parametros.getCodigoItemGAB() == null || parametros.getCodigoItemGAB().equals("") ? "" : "AND UPPER(item.codigo) LIKE '%" + parametros.getCodigoItemGAB().toUpperCase() + "%' ");
		//codigo GAB
		builder.append(parametros.getNumeroGAB() == null || parametros.getNumeroGAB().equals("") ? "" : "AND UPPER(gab.codigo) LIKE '%" + parametros.getNumeroGAB().toUpperCase() + "%' ");
		
		//número Lote
		builder.append(parametros.getNumeroLote() == null || parametros.getNumeroLote().equals("") ? "" : "AND UPPER(lote.numero) LIKE '%" + parametros.getNumeroLote().toUpperCase() + "%' ");

		//profissional
		builder.append(parametros.getNomeProfissional() == null || parametros.getNomeProfissional().equals("") ? "" : "AND UPPER(profissional.nome) LIKE '%" + parametros.getNomeProfissional().toUpperCase() + "%' ");
		//nome beneficiario
		builder.append(parametros.getNomeBeneficiario() == null || parametros.getNomeBeneficiario().equals("") ? "" : "AND UPPER(beneficiario.nome) LIKE '%" + parametros.getNomeBeneficiario().toUpperCase() + "%' ");
		//saram beneficiario
		builder.append(parametros.getSaram() == null || parametros.getSaram().equals("") ? "" : "AND UPPER(beneficiario.saram) LIKE '%" + parametros.getSaram().toUpperCase() + "%' ");		
		//credenciado
		builder.append(parametros.getNomeCredenciado() == null || parametros.getNomeCredenciado().equals("") ? "" : "AND UPPER(credenciado.nomeFantasia) LIKE '%" + parametros.getNomeCredenciado().toUpperCase() + "%' ");		
		
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

		//grupoProcedimento
		builder.append(parametros.getGrupoProcedimento() == null || parametros.getGrupoProcedimento().equals("") ? "" :	"AND UPPER(grupoProcedimento.descricao) LIKE '%" + parametros.getGrupoProcedimento().toUpperCase() + "%' "); 
		
		//subGrupo
		builder.append(parametros.getSubGrupo() == null || parametros.getSubGrupo().equals("") ? "" : "AND UPPER(subGrupo.descricao) LIKE '%" + parametros.getSubGrupo().toUpperCase() + "%' ");		
		
		//titular
		builder.append(parametros.getTitular() != null && parametros.getTitular().equals(OpcoesPesquisaBoolean.SIM) ? "AND beneficiario.titular = true " : "");
		builder.append(parametros.getTitular() != null && parametros.getTitular().equals(OpcoesPesquisaBoolean.NAO) ? "AND beneficiario.titular = false " : "");
	
		//Estados de GAB
		builder.append(parametros.getEstadosGAB() == null || parametros.getEstadosGAB().isEmpty() ? "" : "AND gab.estado in (" + obterListaParaQuery(parametros.getEstadosGAB()) + ") ");
		//Estados de ItemGAB
		builder.append(parametros.getEstadosItemGAB() == null || parametros.getEstadosItemGAB().isEmpty() ? "" : "AND item.estadoItemGAB in (" + obterListaParaQuery(parametros.getEstadosItemGAB()) + ") ");
		
		//comparacao de valores
		for (ComparacaoValoresItemGAB comparacao : parametros.getComparacoes()) {
			if (comparacao.getOpcaoComparacao() != null && comparacao.getValor1() != null
					&& comparacao.getValor2() != null) {
				if (comparacao.getValor2().equals(OpcoesPesquisaValoresItemGAB.VALOR_DIGITADO)) {
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
		builder.append(parametros.getDivisoes() == null || parametros.getDivisoes().isEmpty() ? "" : "AND gab.divisao in (" + obterDivisoesParaQuery(parametros.getDivisoes()) + ") ");
		
		//auditoriaRetrospectiva		
		builder.append("ORDER BY item.codigo");
		
		return entityManager.createQuery(builder.toString(), ItemGAB.class).getResultList();
	}

	private void validarParametrosPesquisa(ParametrosPesquisaItemGAB parametros) {
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
