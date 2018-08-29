package br.ccasj.sisauc.administracao.beneficiario.service.dto;

import java.io.Serializable;
import java.util.Date;

public class BeneficiarioDTO implements Serializable {

	private static final long serialVersionUID = -4283375818460937619L;

	private String siglaOrganizacaoMilitar;

	private String tipoContribuicao;

	private Integer ativo;

	private String siglaPostoGraduacao;

	private String siglaConvenio;

	private String nome;

	private String saram;

	private Long idBeneficiarioTitular;

	private Date dataAtualizacao;

	private String cpf;

	private Long id;

	private String sexo;

	private java.util.Date dataNascimento;

	private Integer titular;
	
	private String cdParentesco;

	public BeneficiarioDTO() {
		super();
	}

	public BeneficiarioDTO(String siglaOrganizacaoMilitar, String tipoContribuicao, Integer ativo,
			String siglaPostoGraduacao, String siglaConvenio, String nome, String saram, Long idBeneficiarioTitular,
			Date dataAtualizacao, String cpf, Long id, String sexo, Date dataNascimento, Integer titular) {
		super();
		this.siglaOrganizacaoMilitar = siglaOrganizacaoMilitar;
		this.tipoContribuicao = tipoContribuicao;
		this.ativo = ativo;
		this.siglaPostoGraduacao = siglaPostoGraduacao;
		this.siglaConvenio = siglaConvenio;
		this.nome = nome;
		this.saram = saram;
		this.idBeneficiarioTitular = idBeneficiarioTitular;
		this.dataAtualizacao = dataAtualizacao;
		this.cpf = cpf;
		this.id = id;
		this.sexo = sexo;
		this.dataNascimento = dataNascimento;
		this.titular = titular;
	}

	public String getSiglaOrganizacaoMilitar() {
		return siglaOrganizacaoMilitar;
	}

	public void setSiglaOrganizacaoMilitar(String siglaOrganizacaoMilitar) {
		this.siglaOrganizacaoMilitar = siglaOrganizacaoMilitar;
	}

	public String getTipoContribuicao() {
		return tipoContribuicao;
	}

	public void setTipoContribuicao(String tipoContribuicao) {
		this.tipoContribuicao = tipoContribuicao;
	}

	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}

	public String getSiglaPostoGraduacao() {
		return siglaPostoGraduacao;
	}

	public void setSiglaPostoGraduacao(String siglaPostoGraduacao) {
		this.siglaPostoGraduacao = siglaPostoGraduacao;
	}

	public String getSiglaConvenio() {
		return siglaConvenio;
	}

	public void setSiglaConvenio(String siglaConvenio) {
		this.siglaConvenio = siglaConvenio;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSaram() {
		return saram;
	}

	public void setSaram(String saram) {
		this.saram = saram;
	}

	public Long getIdBeneficiarioTitular() {
		return idBeneficiarioTitular;
	}

	public void setIdBeneficiarioTitular(Long idBeneficiarioTitular) {
		this.idBeneficiarioTitular = idBeneficiarioTitular;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public java.util.Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(java.util.Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Integer getTitular() {
		return titular;
	}

	public void setTitular(Integer titular) {
		this.titular = titular;
	}

	public String getCdParentesco() {
		return cdParentesco;
	}

	public void setCdParentesco(String cdParentesco) {
		this.cdParentesco = cdParentesco;
	}
	
}
