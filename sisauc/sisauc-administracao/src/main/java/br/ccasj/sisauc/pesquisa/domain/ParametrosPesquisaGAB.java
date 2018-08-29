package br.ccasj.sisauc.pesquisa.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ccasj.sisauc.administracao.cadastro.domain.CodigoInternacionalDoenca;
import br.ccasj.sisauc.administracao.cadastro.domain.LocalInternacao;
import br.ccasj.sisauc.emissaogab.domain.EstadoGAB;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;

public class ParametrosPesquisaGAB {

	private String saram;
	private String nomeBeneficiario;
	private Integer idadeMinimaBeneficiario;
	private Integer idadeMaximaBeneficiario;
	private String nomeProfissional;
	private String nomeCredenciado;
	private String numeroSolicitacao;
	//TODO Alterados nomes de datas, confusão com nomes. inicioDataGeracao/fimDataGeracao trocados respectivamentes por inicioDataEmissao/fimDataEmissao
	private Date inicioDataEmissao;
	private Date fimDataEmissao;
	private Date inicioDataApresentacao;
	private Date fimDataApresentacao;
	private String numeroLote;
	private OpcoesPesquisaBoolean urgente = OpcoesPesquisaBoolean.TODOS;
	private OpcoesPesquisaBoolean internacao = OpcoesPesquisaBoolean.TODOS;
	private OpcoesPesquisaBoolean titular = OpcoesPesquisaBoolean.TODOS;
	private LocalInternacao tipoInternacao;
	private CodigoInternacionalDoenca cid;
	private List<EstadoGAB> estadosGAB = new ArrayList<EstadoGAB>();
	private List<OrganizacaoMilitar> organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();
	private List<Divisao> divisoes = new ArrayList<Divisao>();

	public String getSaram() {
		return saram;
	}

	public void setSaram(String saram) {
		this.saram = saram;
	}

	public String getNomeBeneficiario() {
		return nomeBeneficiario;
	}

	public void setNomeBeneficiario(String nomeBeneficiario) {
		this.nomeBeneficiario = nomeBeneficiario;
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

	public String getNomeCredenciado() {
		return nomeCredenciado;
	}

	public void setNomeCredenciado(String nomeCredenciado) {
		this.nomeCredenciado = nomeCredenciado;
	}

	public String getNumeroSolicitacao() {
		return numeroSolicitacao;
	}

	public void setNumeroSolicitacao(String numeroSolicitacao) {
		this.numeroSolicitacao = numeroSolicitacao;
	}

	public Date getInicioDataEmissao() {
		return inicioDataEmissao;
	}

	public void setInicioDataEmissao(Date inicioDataEmissao) {
		this.inicioDataEmissao = inicioDataEmissao;
	}

	public Date getFimDataEmissao() {
		return fimDataEmissao;
	}

	public void setFimDataEmissao(Date fimDataEmissao) {
		this.fimDataEmissao = fimDataEmissao;
	}

	public Date getInicioDataApresentacao() {
		return inicioDataApresentacao;
	}

	public void setInicioDataApresentacao(Date inicioDataApresentacao) {
		this.inicioDataApresentacao = inicioDataApresentacao;
	}

	public Date getFimDataApresentacao() {
		return fimDataApresentacao;
	}

	public void setFimDataApresentacao(Date fimDataApresentacao) {
		this.fimDataApresentacao = fimDataApresentacao;
	}

	public String getNumeroLote() {
		return numeroLote;
	}

	public void setNumeroLote(String numeroLote) {
		this.numeroLote = numeroLote;
	}

	public OpcoesPesquisaBoolean getUrgente() {
		return urgente;
	}

	public void setUrgente(OpcoesPesquisaBoolean urgente) {
		this.urgente = urgente;
	}

	public OpcoesPesquisaBoolean getInternacao() {
		return internacao;
	}

	public void setInternacao(OpcoesPesquisaBoolean internacao) {
		this.internacao = internacao;
	}

	public OpcoesPesquisaBoolean getTitular() {
		return titular;
	}

	public void setTitular(OpcoesPesquisaBoolean titular) {
		this.titular = titular;
	}

	public LocalInternacao getTipoInternacao() {
		return tipoInternacao;
	}

	public void setTipoInternacao(LocalInternacao tipoInternacao) {
		this.tipoInternacao = tipoInternacao;
	}

	public CodigoInternacionalDoenca getCid() {
		return cid;
	}

	public void setCid(CodigoInternacionalDoenca cid) {
		this.cid = cid;
	}

	public List<EstadoGAB> getEstadosGAB() {
		return estadosGAB;
	}

	public void setEstadosGAB(List<EstadoGAB> estadosGAB) {
		this.estadosGAB = estadosGAB;
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
