package br.ccasj.sisauc.pesquisa.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ccasj.sisauc.emissaogab.domain.EstadoGAB;
import br.ccasj.sisauc.emissaogab.domain.EstadoItemGAB;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;

public class ParametrosPesquisaItemGAB {

	private String codigoItemGAB;
	private String procedimento;
	private String grupoProcedimento;
	private String subGrupo;
	private String nomeProfissional;
	private String saram;
	private String nomeBeneficiario;
	private Integer idadeMinimaBeneficiario;
	private Integer idadeMaximaBeneficiario;
	private String numeroGAB;
	private String nomeCredenciado;
	private String numeroLote;
	//TODO Alterados nomes de datas, confusão com nomes. dataEmissao/dataImpressao trocados respectivamentes por dataGeracao/dataEmissao
	private Date inicioDataEmissao;
	private Date fimDataEmissao;
	private Date inicioDataGeracao;
	private Date fimDataGeracao;
	private Date inicioDataApresentacao;
	private Date fimDataApresentacao;
	private List<EstadoItemGAB> estadosItemGAB = new ArrayList<EstadoItemGAB>();
	private OpcoesPesquisaBoolean titular = OpcoesPesquisaBoolean.TODOS;
	private List<EstadoGAB> estadosGAB = new ArrayList<EstadoGAB>();
	private List<OrganizacaoMilitar> organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();
	private List<ComparacaoValoresItemGAB> comparacoes = new ArrayList<ComparacaoValoresItemGAB>();
	private List<Divisao> divisoes = new ArrayList<Divisao>();

	public String getCodigoItemGAB() {
		return codigoItemGAB;
	}

	public void setCodigoItemGAB(String codigoItemGAB) {
		this.codigoItemGAB = codigoItemGAB;
	}

	public List<EstadoItemGAB> getEstadosItemGAB() {
		return estadosItemGAB;
	}

	public void setEstadosItemGAB(List<EstadoItemGAB> estadosItemGAB) {
		this.estadosItemGAB = estadosItemGAB;
	}

	public String getProcedimento() {
		return procedimento;
	}

	public void setProcedimento(String procedimento) {
		this.procedimento = procedimento;
	}

	public String getGrupoProcedimento() {
		return grupoProcedimento;
	}

	public void setGrupoProcedimento(String grupoProcedimento) {
		this.grupoProcedimento = grupoProcedimento;
	}

	public String getSubGrupo() {
		return subGrupo;
	}

	public void setSubGrupo(String subGrupo) {
		this.subGrupo = subGrupo;
	}

	public String getNomeProfissional() {
		return nomeProfissional;
	}

	public void setNomeProfissional(String nomeProfissional) {
		this.nomeProfissional = nomeProfissional;
	}

	public String getSaram() {
		return saram;
	}

	public void setSaram(String saram) {
		this.saram = saram;
	}

	public OpcoesPesquisaBoolean getTitular() {
		return titular;
	}

	public void setTitular(OpcoesPesquisaBoolean titular) {
		this.titular = titular;
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

	public String getNumeroGAB() {
		return numeroGAB;
	}

	public void setNumeroGAB(String numeroGAB) {
		this.numeroGAB = numeroGAB;
	}

	public List<EstadoGAB> getEstadosGAB() {
		return estadosGAB;
	}

	public void setEstadosGAB(List<EstadoGAB> estadosGAB) {
		this.estadosGAB = estadosGAB;
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

	public Date getInicioDataGeracao() {
		return inicioDataGeracao;
	}

	public void setInicioDataGeracao(Date inicioDataGeracao) {
		this.inicioDataGeracao = inicioDataGeracao;
	}

	public Date getFimDataGeracao() {
		return fimDataGeracao;
	}

	public void setFimDataGeracao(Date fimDataGeracao) {
		this.fimDataGeracao = fimDataGeracao;
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

	public String getNomeCredenciado() {
		return nomeCredenciado;
	}

	public void setNomeCredenciado(String nomeCredenciado) {
		this.nomeCredenciado = nomeCredenciado;
	}

	public String getNumeroLote() {
		return numeroLote;
	}

	public void setNumeroLote(String numeroLote) {
		this.numeroLote = numeroLote;
	}

	public List<OrganizacaoMilitar> getOrganizacoesMilitares() {
		return organizacoesMilitares;
	}

	public void setOrganizacoesMilitares(List<OrganizacaoMilitar> organizacoesMilitares) {
		this.organizacoesMilitares = organizacoesMilitares;
	}

	public List<ComparacaoValoresItemGAB> getComparacoes() {
		return comparacoes;
	}

	public void setComparacoes(List<ComparacaoValoresItemGAB> comparacoes) {
		this.comparacoes = comparacoes;
	}

	public List<Divisao> getDivisoes() {
		return divisoes;
	}

	public void setDivisoes(List<Divisao> divisoes) {
		this.divisoes = divisoes;
	}

}
