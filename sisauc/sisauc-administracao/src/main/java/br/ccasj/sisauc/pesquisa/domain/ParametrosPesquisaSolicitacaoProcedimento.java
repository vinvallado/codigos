package br.ccasj.sisauc.pesquisa.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ccasj.sisauc.administracao.cadastro.domain.CodigoInternacionalDoenca;
import br.ccasj.sisauc.administracao.cadastro.domain.EstadoSolicitacaoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.LocalInternacao;
import br.ccasj.sisauc.auditoriaprospectiva.domain.EstadoAuditoriaProspectiva;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;

public class ParametrosPesquisaSolicitacaoProcedimento {

	private String nomeBeneficiario;
	private String saramBeneficiario;
	private Integer idadeMinimaBeneficiario;
	private Integer idadeMaximaBeneficiario;
	private String nomeProfissional;
	private String procedimento;
	private Date inicioDataSolicitacao;
	private Date fimDataSolicitacao;
	private Date inicioDataAuditoria;
	private Date fimDataAuditoria;
	private OpcoesPesquisaBoolean titular = OpcoesPesquisaBoolean.TODOS;
	private OpcoesPesquisaBoolean urgente = OpcoesPesquisaBoolean.TODOS;
	private OpcoesPesquisaBoolean possuiArquivo = OpcoesPesquisaBoolean.TODOS;
	private OpcoesPesquisaBoolean internacao = OpcoesPesquisaBoolean.TODOS;
	private CodigoInternacionalDoenca cid;
	private LocalInternacao localInternacao;
	private List<EstadoSolicitacaoProcedimento> estadosSolicitacaoProcedimento = new ArrayList<EstadoSolicitacaoProcedimento>();
	private List<EstadoAuditoriaProspectiva> estadoAuditoriaProspectiva = new ArrayList<EstadoAuditoriaProspectiva>();
	private List<OrganizacaoMilitar> organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();
	private List<Divisao> divisoes = new ArrayList<Divisao>();

	public String getNomeBeneficiario() {
		return nomeBeneficiario;
	}

	public void setNomeBeneficiario(String nomeBeneficiario) {
		this.nomeBeneficiario = nomeBeneficiario;
	}

	public String getSaramBeneficiario() {
		return saramBeneficiario;
	}

	public void setSaramBeneficiario(String saramBeneficiario) {
		this.saramBeneficiario = saramBeneficiario;
	}

	public Integer getIdadeMinimaBeneficiario() {
		return idadeMinimaBeneficiario;
	}

	public void setIdadeMinimaBeneficiario(Integer idadeMinimaBeneficiario) {
		this.idadeMinimaBeneficiario = idadeMinimaBeneficiario;
	}

	public Integer getIdadeMaximaBeneficiario() {
		return idadeMaximaBeneficiario;
	}

	public void setIdadeMaximaBeneficiario(Integer idadeMaximaBeneficiario) {
		this.idadeMaximaBeneficiario = idadeMaximaBeneficiario;
	}

	public String getNomeProfissional() {
		return nomeProfissional;
	}

	public void setNomeProfissional(String nomeProfissional) {
		this.nomeProfissional = nomeProfissional;
	}

	public String getProcedimento() {
		return procedimento;
	}

	public void setProcedimento(String procedimento) {
		this.procedimento = procedimento;
	}

	public Date getInicioDataSolicitacao() {
		return inicioDataSolicitacao;
	}

	public void setInicioDataSolicitacao(Date inicioDataSolicitacao) {
		this.inicioDataSolicitacao = inicioDataSolicitacao;
	}

	public Date getFimDataSolicitacao() {
		return fimDataSolicitacao;
	}

	public void setFimDataSolicitacao(Date fimDataSolicitacao) {
		this.fimDataSolicitacao = fimDataSolicitacao;
	}

	public Date getInicioDataAuditoria() {
		return inicioDataAuditoria;
	}

	public void setInicioDataAuditoria(Date inicioDataAuditoria) {
		this.inicioDataAuditoria = inicioDataAuditoria;
	}

	public Date getFimDataAuditoria() {
		return fimDataAuditoria;
	}

	public void setFimDataAuditoria(Date fimDataAuditoria) {
		this.fimDataAuditoria = fimDataAuditoria;
	}

	public OpcoesPesquisaBoolean getTitular() {
		return titular;
	}

	public void setTitular(OpcoesPesquisaBoolean titular) {
		this.titular = titular;
	}

	public OpcoesPesquisaBoolean getUrgente() {
		return urgente;
	}

	public void setUrgente(OpcoesPesquisaBoolean urgente) {
		this.urgente = urgente;
	}

	public OpcoesPesquisaBoolean getPossuiArquivo() {
		return possuiArquivo;
	}

	public void setPossuiArquivo(OpcoesPesquisaBoolean possuiArquivo) {
		this.possuiArquivo = possuiArquivo;
	}

	public OpcoesPesquisaBoolean getInternacao() {
		return internacao;
	}

	public void setInternacao(OpcoesPesquisaBoolean internacao) {
		this.internacao = internacao;
	}

	public CodigoInternacionalDoenca getCid() {
		return cid;
	}

	public void setCid(CodigoInternacionalDoenca cid) {
		this.cid = cid;
	}

	public LocalInternacao getLocalInternacao() {
		return localInternacao;
	}

	public void setLocalInternacao(LocalInternacao localInternacao) {
		this.localInternacao = localInternacao;
	}

	public List<EstadoSolicitacaoProcedimento> getEstadosSolicitacaoProcedimento() {
		return estadosSolicitacaoProcedimento;
	}

	public void setEstadosSolicitacaoProcedimento(List<EstadoSolicitacaoProcedimento> estadosSolicitacaoProcedimento) {
		this.estadosSolicitacaoProcedimento = estadosSolicitacaoProcedimento;
	}

	public List<EstadoAuditoriaProspectiva> getEstadoAuditoriaProspectiva() {
		return estadoAuditoriaProspectiva;
	}

	public void setEstadoAuditoriaProspectiva(List<EstadoAuditoriaProspectiva> estadoAuditoriaProspectiva) {
		this.estadoAuditoriaProspectiva = estadoAuditoriaProspectiva;
	}

	public List<OrganizacaoMilitar> getOrganizacoesMilitares() {
		return organizacoesMilitares;
	}

	public void setOrganizacoesMilitares(List<OrganizacaoMilitar> organizacoesMilitares) {
		this.organizacoesMilitares = organizacoesMilitares;
	}

	public List<Divisao> getDivisoes() {
		return divisoes;
	}

	public void setDivisoes(List<Divisao> divisoes) {
		this.divisoes = divisoes;
	}

}
