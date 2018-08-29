package br.ccasj.sisauc.auditoriaretrospectiva.dao.impl;

import java.io.Serializable;
import java.util.Date;

import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciadoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.Credenciado;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.AuditoriaRetrospectiva;
import br.ccasj.sisauc.emissaogab.domain.EstadoItemGAB;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;

public class ItemGABToView implements Serializable {
	private static final long serialVersionUID = -1577494783630082514L;
	
	private ItemGAB itemGAB;
	private GuiaApresentacaoBeneficiario gab;
	
	public ItemGABToView(Integer idItemGAB, String codigoItemGAB, Date dataGeracao, String credenciadoNomeFantasia){
		super();
		this.itemGAB = new ItemGAB();
		this.itemGAB.setId(idItemGAB);
		this.itemGAB.setCodigo(codigoItemGAB);
		
		Credenciado credenciado = new Credenciado();
		credenciado.setNomeFantasia(credenciadoNomeFantasia);
		
		this.gab = new GuiaApresentacaoBeneficiario();
		this.gab.setDataGeracao(dataGeracao);
		this.gab.setCredenciado(credenciado);
	}	
	
	public ItemGABToView(Integer idItemGAB, String codigoItemGAB, EstadoItemGAB estadoItemGAB, Date dataGeracao, String credenciadoNomeFantasia){
		this(idItemGAB, codigoItemGAB, dataGeracao, credenciadoNomeFantasia);
		this.itemGAB.setEstadoItemGAB(estadoItemGAB);
		
	}
	
	public ItemGABToView(ItemGAB itemGab, Double valorFinal, Date dataGeracao, String credenciadoNomeFantasia){
		this(itemGab.getId(), itemGab.getCodigo(), dataGeracao, credenciadoNomeFantasia);
		
		AuditoriaRetrospectiva auditoria = new AuditoriaRetrospectiva();
		auditoria.setValorFinal(valorFinal);
		this.itemGAB.setAuditoriaRetrospectiva(auditoria);
		
	}	
	
	public ItemGABToView(ItemGAB itemGab, Double valorApresentado, Double valorAuditado, Date dataGeracao, String credenciadoNomeFantasia){
		this(itemGab, 0.0d, dataGeracao, credenciadoNomeFantasia);
		
		itemGAB.getAuditoriaRetrospectiva().setValorAuditado(valorAuditado);
		itemGAB.getAuditoriaRetrospectiva().setValorApresentado(valorApresentado);
		
	}	
	
	public ItemGABToView(Integer idItemGAB, String codigoItemGAB, EstadoItemGAB estadoItemGAB, ConfiguracaoEditalCredenciadoProcedimento configuracao,  Date dataGeracao, String credenciadoNomeFantasia){
		this(idItemGAB, codigoItemGAB, estadoItemGAB,  dataGeracao, credenciadoNomeFantasia);
		this.itemGAB.setConfiguracao(configuracao);
	}
	
	public ItemGABToView(Integer idItemGAB, String codigoItemGAB, EstadoItemGAB estadoItemGAB, ConfiguracaoEditalCredenciadoProcedimento configuracao,  Date dataGeracao, String credenciadoNomeFantasia, Beneficiario beneficiario, AuditoriaRetrospectiva auditoriaRetrospectiva){
		this(idItemGAB, codigoItemGAB, estadoItemGAB,  dataGeracao, credenciadoNomeFantasia);
		this.itemGAB.setAuditoriaRetrospectiva(auditoriaRetrospectiva);
		this.itemGAB.setConfiguracao(configuracao);
		this.gab.setBeneficiario(beneficiario);
	}
	
	//Construtor para a consulta 'OBTER_ITENS_GAB_NAO_CONFORMES' - ItemGABDAOImpl
	public ItemGABToView(Integer idItemGAB, String codigoItemGAB, Double valorApresentadoAuditoriaRetrospectiva, Double valorAuditadoAuditoriaRetrospectiva, Double valorFinalAuditoriaRetrospectiva, String nomeFantasiaCredenciado, Date dataGeracaoGab, ConfiguracaoEditalCredenciadoProcedimento configuracao, String justificativaValorAuditado){
		this(idItemGAB, codigoItemGAB, dataGeracaoGab, nomeFantasiaCredenciado);
		
		AuditoriaRetrospectiva auditoria = new AuditoriaRetrospectiva();
		auditoria.setValorApresentado(valorApresentadoAuditoriaRetrospectiva);
		auditoria.setValorAuditado(valorAuditadoAuditoriaRetrospectiva);
		auditoria.setValorFinal(valorFinalAuditoriaRetrospectiva);
		this.itemGAB.setAuditoriaRetrospectiva(auditoria);
		this.itemGAB.setConfiguracao(configuracao);
		this.itemGAB.getAuditoriaRetrospectiva().setJustificativaValorAuditado(justificativaValorAuditado);
	}
	
	public ItemGAB getItemGAB() {
		return itemGAB;
	}
	public void setItemGAB(ItemGAB itemGAB) {
		this.itemGAB = itemGAB;
	}
	public GuiaApresentacaoBeneficiario getGab() {
		return gab;
	}
	public void setGab(GuiaApresentacaoBeneficiario gab) {
		this.gab = gab;
	}
	
	
}
