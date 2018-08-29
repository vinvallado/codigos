package br.ccasj.sisauc.emissaogab.service.impl;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciado;
import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciadoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.Credenciado;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoCBHPM2012;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoCISSFA;
import br.ccasj.sisauc.administracao.cadastro.domain.TipoCobrancaCredenciadoProcedimento;
import br.ccasj.sisauc.administracao.sistema.dao.UnidadeMultiplicadoraDao;
import br.ccasj.sisauc.administracao.sistema.domain.UnidadeMultiplicadora;
import br.ccasj.sisauc.auditoriaprospectiva.dao.AuditoriaProspectivaDAO;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectiva;
import br.ccasj.sisauc.auditoriaprospectiva.domain.EstadoAuditoriaProspectiva;
import br.ccasj.sisauc.auditoriaprospectiva.domain.RespostaProcedimentoAuditoria;
import br.ccasj.sisauc.emissaogab.dao.GuiaApresentacaoBeneficiarioDAO;
import br.ccasj.sisauc.emissaogab.domain.EstadoGAB;
import br.ccasj.sisauc.emissaogab.domain.EstadoItemGAB;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.emissaogab.domain.MetadadoValorItemGAB;
import br.ccasj.sisauc.emissaogab.domain.ValoresGuiaApresentacaoBeneficiariosDTO;
import br.ccasj.sisauc.emissaogab.service.GerarGABService;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.framework.utils.EntidadeGenericaUtils;

@Transactional

@Service(value = "gerarGABService")
public class GerarGABServiceImpl implements GerarGABService {

	@Autowired
	private GuiaApresentacaoBeneficiarioDAO gabDAO;
	@Autowired
	private AuditoriaProspectivaDAO auditoriaProspectivaDAO;
	@Autowired
	private UnidadeMultiplicadoraDao unidadeMultiplicadoraDao;

	@Override
	public synchronized GuiaApresentacaoBeneficiario salvar(GuiaApresentacaoBeneficiario gab) {
		verificarNumeracao(gab);
		return gabDAO.merge(gab);
	}
	
	
	private synchronized void  verificarNumeracao(GuiaApresentacaoBeneficiario gab) {
		if(gab.getId() == null){
			Calendar calendar = Calendar.getInstance();
			StringBuilder builder = new StringBuilder();
			builder.append("GAB");
			Integer ano = Integer.valueOf(calendar.get(Calendar.YEAR));
			int quantidade = gabDAO.obterQuantidadeGABSPorOrganizacaoMilitarEAno(gab.getOrganizacaoMilitar().getId(), ano) + 1;
			builder.append(String.valueOf(ano))
				.append("/").append(gab.getOrganizacaoMilitar().getSigla())
				.append("/").append(StringUtils.leftPad(String.valueOf(quantidade), 5, "0"));
			gab.setCodigo(builder.toString());
			gab.setDataGeracao(new Date());
			numerarItens(gab);
		}
	}

	private void numerarItens(GuiaApresentacaoBeneficiario gab) {
		int i = 1;
		for (ItemGAB item : gab.getItens()) {
			String sufix = StringUtils.leftPad(String.valueOf(i), 3, "0");
			item.setCodigo(gab.getCodigo() + "-" + sufix);
			i++;
		}
	}

	@Override
	public List<GuiaApresentacaoBeneficiario> gerarAPartirAuditoriaProspectiva(Integer idAuditoriaProspectiva) {
		AuditoriaProspectiva auditoriaProspectiva = auditoriaProspectivaDAO.findById(idAuditoriaProspectiva);
		List<GuiaApresentacaoBeneficiario> result = new ArrayList<GuiaApresentacaoBeneficiario>();
		if(auditoriaProspectiva.getEstado().equals(EstadoAuditoriaProspectiva.APROVADO)){
			Map<Credenciado, Set<RespostaProcedimentoAuditoria>> mapCredenciados = obterCredenciadosRespostas(auditoriaProspectiva);
			for (Credenciado credenciado : mapCredenciados.keySet()) {
				GuiaApresentacaoBeneficiario gab = criarPorCredenciadoRespostas(credenciado, mapCredenciados.get(credenciado), auditoriaProspectiva);
				gab.setIsento(auditoriaProspectiva.isIsento());
				gab.setEspecificacao(auditoriaProspectiva.getEspecificacao());
				gab.setDivisao(auditoriaProspectiva.getSolicitacao().getDivisao());
				result.add(salvar(gab));
			}
		}
		return result;
	}

	private GuiaApresentacaoBeneficiario criarPorCredenciadoRespostas(Credenciado credenciado, Set<RespostaProcedimentoAuditoria> procedimentos, AuditoriaProspectiva auditoria) {
		GuiaApresentacaoBeneficiario gab = new GuiaApresentacaoBeneficiario();
		gab.setBeneficiario(auditoria.getSolicitacao().getBeneficiario());
		gab.setCredenciado(credenciado);
		gab.setEstado(EstadoGAB.GERADA);
		gab.setAuditoriaProspectiva(auditoria);
		gab.setItens(new HashSet<ItemGAB>());
		gab.setOrganizacaoMilitar(auditoria.getSolicitacao().getOrganizacaoMilitarSolicitante());
		for (RespostaProcedimentoAuditoria procedimento : procedimentos) {
			gab.getItens().add(criarItemPorProcedimento(procedimento));
		}
		return gab;
	}

	private ItemGAB criarItemPorProcedimento(RespostaProcedimentoAuditoria resposta) {
		ItemGAB item = new ItemGAB();
		ValoresGuiaApresentacaoBeneficiariosDTO dto = calcularValorTotalRespostaProcedimento(resposta);
		item.setId(EntidadeGenericaUtils.gerarIdNegativo());
		item.setEstadoItemGAB(EstadoItemGAB.CRIADO);
		item.setValores(dto.getItens());
		item.setValorTotal(dto.getValorTotal());
		item.setConfiguracao(resposta.getCredenciado());
		item.setObservacaoGAB(resposta.getObservacaoGAB());
		if (resposta.getOpmeDescricao() != null && resposta.getOpmeValor() != null)
		{
			item.setValorOpme(resposta.getOpmeValor());
			item.setDescricaoOpme(resposta.getOpmeDescricao());
		}			
		return item;
	}

	private Map<Credenciado, Set<RespostaProcedimentoAuditoria>> obterCredenciadosRespostas(AuditoriaProspectiva auditoriaProspectiva) {
		Map<Credenciado, Set<RespostaProcedimentoAuditoria>> result = new HashMap<Credenciado, Set<RespostaProcedimentoAuditoria>>();
		for (RespostaProcedimentoAuditoria resposta : auditoriaProspectiva.getProcedimentos()) {
			if(resposta.getAprovado() && resposta.getCredenciado() != null){
				Credenciado credenciado = resposta.getCredenciado().getConfiguracaoEditalCredenciado().getCredenciado();
				if(result.get(credenciado) == null){
					result.put(credenciado, new HashSet<RespostaProcedimentoAuditoria>());
				}
				result.get(credenciado).add(resposta);
			}
		}
		return result;
	}
	
	
	// NÃO REMOVER ESSE COMENTÁRIO
	// PARA CBHPM ed 2012
	// Valor = Porte*(1+DefPorte) + PorteA*(1+DefPortA) + Porte*(1+DefAux)*(0,05*NAux³ – 0,3*NAux² + 1,15*NAux)/3 
	//			+ QntFilme*Filme + QntUCO*UCO*(1+DefUCO) + TAXA 
	
	@Override
	public ValoresGuiaApresentacaoBeneficiariosDTO calcularValorTotalRespostaProcedimento(RespostaProcedimentoAuditoria resposta) {
		ValoresGuiaApresentacaoBeneficiariosDTO dto = calcularValorTotalProcedimento(resposta.getCredenciado());
		if(resposta.isOpme()){
			dto.getItens().add(new MetadadoValorItemGAB(EntidadeGenericaUtils.gerarIdNegativo(), resposta.getOpmeValor(), "valorOPME", "Valor de OPME"));
			dto.setValorTotal(truncar(dto.getValorTotal() + resposta.getOpmeValor()));
		}
		return dto;
	}

	@Override
	public ValoresGuiaApresentacaoBeneficiariosDTO calcularValorTotalProcedimento(ConfiguracaoEditalCredenciadoProcedimento configuracao){
		ValoresGuiaApresentacaoBeneficiariosDTO dto = new ValoresGuiaApresentacaoBeneficiariosDTO();
		Double value = 0.0;		
		UnidadeMultiplicadora unidadeMultiplicadora = verificarUnidadesMultiplicadoras();
		if(TipoCobrancaCredenciadoProcedimento.PACOTE.equals(configuracao.getTipo())){
			value = configuracao.getValor();
			dto.getItens().add(new MetadadoValorItemGAB(EntidadeGenericaUtils.gerarIdNegativo(), value, "valorPacote", "Valor do pacote"));
		} else if(TipoCobrancaCredenciadoProcedimento.TAXA.equals(configuracao.getTipo())) {
			
			ConfiguracaoEditalCredenciado credenciado = configuracao.getConfiguracaoEditalCredenciado();
		
			if(configuracao.getProcedimento() instanceof ProcedimentoCBHPM2012){
				value = calcularValoresProcedimentoCBHPM2012(configuracao, dto, unidadeMultiplicadora, credenciado);
			}
			
			Double valorTaxa = configuracao.getValor();
			dto.getItens().add(new MetadadoValorItemGAB(EntidadeGenericaUtils.gerarIdNegativo(), valorTaxa, "valorTaxa", "Valor da taxa"));
			value = value + valorTaxa;

		} else if (configuracao.getProcedimento() instanceof ProcedimentoCISSFA) {
			ProcedimentoCISSFA procedimentoCISSFA = (ProcedimentoCISSFA) configuracao.getProcedimento();
			value = calcularValorProcedimentoCISSFA(procedimentoCISSFA, unidadeMultiplicadora);
			dto.getItens().add(new MetadadoValorItemGAB(EntidadeGenericaUtils.gerarIdNegativo(), unidadeMultiplicadora.getValorUsm(), "valorUsm", "Valor USM"));
			dto.getItens().add(new MetadadoValorItemGAB(EntidadeGenericaUtils.gerarIdNegativo(), procedimentoCISSFA.getQuantidadeUsm(), "qtdUsmProcedimento", "Quantidade USM do Procedimento"));
			if (procedimentoCISSFA.getQuantidadeFilme() != null) {
				dto.getItens().add(new MetadadoValorItemGAB(EntidadeGenericaUtils.gerarIdNegativo(), unidadeMultiplicadora.getValorFilme(), "valorFilme", "Valor Filme"));
				dto.getItens().add(new MetadadoValorItemGAB(EntidadeGenericaUtils.gerarIdNegativo(), procedimentoCISSFA.getQuantidadeFilme(), "qtdUsmProcedimento", "Quantidade de Filme do Procedimento"));
			}
		}
		dto.setValorTotal(truncar(value));
		return dto;
	}

	private Double calcularValorProcedimentoCISSFA(ProcedimentoCISSFA procedimento, UnidadeMultiplicadora unidadeMultiplicadora) {
		Double valor = procedimento.getQuantidadeUsm() * unidadeMultiplicadora.getValorUsm();
		if (procedimento.getQuantidadeFilme() != null) {
			valor = valor + (procedimento.getQuantidadeFilme() * unidadeMultiplicadora.getValorFilme());
		}
		return valor;
	}

	private Double calcularValoresProcedimentoCBHPM2012(ConfiguracaoEditalCredenciadoProcedimento configuracao, ValoresGuiaApresentacaoBeneficiariosDTO dto, UnidadeMultiplicadora unidadeMultiplicadora, ConfiguracaoEditalCredenciado credenciado) {
		Double value;
		ProcedimentoCBHPM2012 procedimentoCBHPM2012 = (ProcedimentoCBHPM2012) configuracao.getProcedimento();
		Double valorPorte = procedimentoCBHPM2012.getPorte() == null ? 0 : obterValorReajustado(procedimentoCBHPM2012.getPorte().getValor(), credenciado.getIndiceReajustePorte());
		if (procedimentoCBHPM2012.getDeflatorPorte() != null)
			valorPorte = valorPorte * procedimentoCBHPM2012.getDeflatorPorte();
		dto.getItens().add(new MetadadoValorItemGAB(EntidadeGenericaUtils.gerarIdNegativo(), valorPorte, "valorPorteReajustado", "Valor do porte reajustado"));
		
		Double valorPorteAnestesico = procedimentoCBHPM2012.getPorteAnestesico() == null ? 0 : obterValorReajustado(procedimentoCBHPM2012.getPorteAnestesico().getValor(), credenciado.getIndiceReajustePorteAnestesico());
		dto.getItens().add(new MetadadoValorItemGAB(EntidadeGenericaUtils.gerarIdNegativo(), valorPorteAnestesico, "valorPorteAnestesicoReajustado", "Valor do porte anestésico reajustado"));
		
		Double valorUCO = procedimentoCBHPM2012.getCustoOperacional() == null ? 0 : obterValorReajustado(procedimentoCBHPM2012.getCustoOperacional() * unidadeMultiplicadora.getValorUco(), credenciado.getIndiceReajusteCustoOperacional()); 
		dto.getItens().add(new MetadadoValorItemGAB(EntidadeGenericaUtils.gerarIdNegativo(), valorUCO, "valorUCOReajustado", "Valor do UCO reajustado"));
		
		Double valorFilme = procedimentoCBHPM2012.getQuantidadeFilme() == null ? 0 : procedimentoCBHPM2012.getQuantidadeFilme() * unidadeMultiplicadora.getValorFilme();
		dto.getItens().add(new MetadadoValorItemGAB(EntidadeGenericaUtils.gerarIdNegativo(), valorFilme, "valorFilmeReajustado", "Valor do filme reajustado"));
		
		int qtAux = procedimentoCBHPM2012.getNumeroAuxiliares() == null ? 0 : procedimentoCBHPM2012.getNumeroAuxiliares();
		Double valorAuxiliares = obterValorReajustado(valorPorte, credenciado.getIndiceReajusteAuxiliares());
		valorAuxiliares *= (-1 * Math.pow(qtAux, 4) + 10 * Math.pow(qtAux, 3) - 35 * Math.pow(qtAux, 2) + 98 * qtAux)/240;
		dto.getItens().add(new MetadadoValorItemGAB(EntidadeGenericaUtils.gerarIdNegativo(), valorAuxiliares, "valorAuxiliaresReajustado", "Valor dos auxiliares reajustado"));
		value = valorPorte + valorPorteAnestesico + valorAuxiliares + valorFilme + valorUCO;
		return value;
	}
	
	private UnidadeMultiplicadora verificarUnidadesMultiplicadoras() {
		UnidadeMultiplicadora multiplicadora = unidadeMultiplicadoraDao.obterUnidadeMultiplicadora();
		if(multiplicadora == null){
			throw new SystemRuntimeException("Atenção! Antes de continuar, é necessário que sejam cadastradas unidades múltiplicadoras. Entre em contato com o administrador do sistema!");
		}
		return multiplicadora;
	}

	private Double obterValorReajustado(Double valor, Double indice){
		DecimalFormat format = new DecimalFormat("#.##");
		format.setRoundingMode(RoundingMode.DOWN);
		Double result = 0.0;
		if(valor == null){
			result = 0.0;
		} else if(indice == null){
			result = valor;
		} else {
			result = valor * (1 + indice/100);
		}
		return truncar(result);
	}
	
	private Double truncar(Double value){
		return Math.floor(value * 100) / 100;
	}
	
}
