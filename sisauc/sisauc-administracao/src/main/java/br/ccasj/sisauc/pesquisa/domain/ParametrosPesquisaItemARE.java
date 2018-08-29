package br.ccasj.sisauc.pesquisa.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ccasj.sisauc.emissaoar.domain.EstadoAR;
import br.ccasj.sisauc.emissaoar.domain.EstadoItemAR;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;

public class ParametrosPesquisaItemARE {

	private String codigoItemARE;
	private String procedimento;
	private String grupoProcedimento;
	private String subGrupo;
	private String nomeProfissional;
	private String saram;
	private String nomeBeneficiario;
	private Integer idadeMinimaBeneficiario;
	private Integer idadeMaximaBeneficiario;
	private String numeroARE;
	private String nomeCredenciado;
	private Date inicioDataEmissao;
	private Date fimDataEmissao;
	private Date inicioDataGeracao;
	private Date fimDataGeracao;
	private Date inicioDataApresentacao;
	private Date fimDataApresentacao;
	private List<EstadoItemAR> estadosItemARE = new ArrayList<EstadoItemAR>();
	private OpcoesPesquisaBoolean titular = OpcoesPesquisaBoolean.TODOS;
	private List<EstadoAR> estadosARE = new ArrayList<EstadoAR>();
	private String numeroNotaFiscal;
	private Date inicioDataNotaFiscal;
	private Date fimDataNotaFiscal;
	private String cpfCnpjNotaFiscal;
	private List<OrganizacaoMilitar> organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();	
	private List<ComparacaoValoresItemARE> comparacoes = new ArrayList<ComparacaoValoresItemARE>();
	private List<Divisao> divisoes = new ArrayList<Divisao>();

	
	public String getCodigoItemARE() {
		return codigoItemARE;
	}

	public void setCodigoItemARE(String codigoItemARE) {
		this.codigoItemARE = codigoItemARE;
	}

	public List<EstadoItemAR> getEstadosItemARE() {
		return estadosItemARE;
	}

	public void setEstadosItemARE(List<EstadoItemAR> estadosItemARE) {
		this.estadosItemARE = estadosItemARE;
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

	public String getNumeroARE() {
		return numeroARE;
	}

	public void setNumeroARE(String numeroARE) {
		this.numeroARE = numeroARE;
	}

	public List<EstadoAR> getEstadosARE() {
		return estadosARE;
	}

	public void setEstadosARE(List<EstadoAR> estadosARE) {
		this.estadosARE = estadosARE;
	}

	public String getNumeroNotaFiscal() {
		return numeroNotaFiscal;
	}

	public void setNumeroNotaFiscal(String numeroNotaFiscal) {
		this.numeroNotaFiscal = numeroNotaFiscal;
	}

	public Date getInicioDataNotaFiscal() {
		return inicioDataNotaFiscal;
	}

	public void setInicioDataNotaFiscal(Date inicioDataNotaFiscal) {
		this.inicioDataNotaFiscal = inicioDataNotaFiscal;
	}

	public Date getFimDataNotaFiscal() {
		return fimDataNotaFiscal;
	}

	public void setFimDataNotaFiscal(Date fimDataNotaFiscal) {
		this.fimDataNotaFiscal = fimDataNotaFiscal;
	}

	public String getCpfCnpjNotaFiscal() {
		return cpfCnpjNotaFiscal;
	}

	public void setCpfCnpjNotaFiscal(String cpfCnpjNotaFiscal) {
		this.cpfCnpjNotaFiscal = cpfCnpjNotaFiscal;
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

	public List<OrganizacaoMilitar> getOrganizacoesMilitares() {
		return organizacoesMilitares;
	}

	public void setOrganizacoesMilitares(List<OrganizacaoMilitar> organizacoesMilitares) {
		this.organizacoesMilitares = organizacoesMilitares;
	}

	public List<ComparacaoValoresItemARE> getComparacoes() {
		return comparacoes;
	}

	public void setComparacoes(List<ComparacaoValoresItemARE> comparacoes) {
		this.comparacoes = comparacoes;
	}

	public List<Divisao> getDivisoes() {
		return divisoes;
	}

	public void setDivisoes(List<Divisao> divisoes) {
		this.divisoes = divisoes;
	}

}
