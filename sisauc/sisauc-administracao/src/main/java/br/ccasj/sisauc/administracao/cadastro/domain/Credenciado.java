package br.ccasj.sisauc.administracao.cadastro.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import br.ccasj.sisauc.framework.domain.Cidade;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.com.caelum.stella.bean.validation.CNPJ;
import br.com.caelum.stella.bean.validation.CPF;

@Entity
@Table(name = "credenciado", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class Credenciado extends EntidadeGenerica {

	private static final long serialVersionUID = -7061591902706935533L;

	private static final String SEQ_NAME = "credenciado_id_seq";
	
	private TipoPessoa tipoPessoa = TipoPessoa.PESSOA_JURIDICA;
	private String cnpj;
	private String cpf;
	private String email;
	private String telefonePrincipal;
	private String telefoneOpcional;
	private String razaoSocial;
	private String nomeFantasia;
	private String logradouro;
	private String complemento;
	private String cep;
	private String bairro;
	
	private boolean ativo = true;
	
	private Cidade cidade;

	public Credenciado() {
		super();
	}
	
	public Credenciado(Integer id) {
		super(id);
	}
	
	public Credenciado(String nomeFantasia) {
		super();
		this.nomeFantasia = nomeFantasia;
	}
	
	public Credenciado(String nomeFantasia, Cidade cidadeCredenciado) {
		super();
		this.nomeFantasia = nomeFantasia;
		this.cidade = cidadeCredenciado;
	}
	
	public Credenciado(String nomeFantasia, Cidade cidadeCredenciado, String bairro) {
		super();
		this.nomeFantasia = nomeFantasia;
		this.cidade = cidadeCredenciado;
		this.bairro = bairro;
	}

	public Credenciado(Integer id, String cpf, String cnpj,  TipoPessoa tipoPessoa, String nomeFantasia) {
		super(id);
		this.tipoPessoa = tipoPessoa;
		this.cnpj = cnpj;
		this.cpf = cpf;
		this.nomeFantasia = nomeFantasia;
	}
	
	public Credenciado(Integer id, String cpf, String cnpj,  TipoPessoa tipoPessoa, String nomeFantasia, String razaoSocial) {
		super(id);
		this.tipoPessoa = tipoPessoa;
		this.cnpj = cnpj;
		this.cpf = cpf;
		this.nomeFantasia = nomeFantasia;
		this.razaoSocial = razaoSocial;
	}
	
	public Credenciado(Integer id, String cpf, String cnpj,  TipoPessoa tipoPessoa, String nomeFantasia, String razaoSocial, boolean ativo) {
		super(id);
		this.tipoPessoa = tipoPessoa;
		this.cnpj = cnpj;
		this.cpf = cpf;
		this.nomeFantasia = nomeFantasia;
		this.razaoSocial = razaoSocial;
		this.ativo = ativo;
	}
	
	public Credenciado(Integer id, String cpf, String cnpj,  TipoPessoa tipoPessoa, String nomeFantasia, String razaoSocial, boolean ativo, String telefonePrincipal) {
		super(id);
		this.tipoPessoa = tipoPessoa;
		this.cnpj = cnpj;
		this.cpf = cpf;
		this.nomeFantasia = nomeFantasia;
		this.razaoSocial = razaoSocial;
		this.ativo = ativo;
		this.telefonePrincipal = telefonePrincipal;
	}

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}

	@NotNull
	@Column(name = "id_tipo_pessoa")
	@Enumerated(EnumType.STRING)
	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	@CNPJ(message = "CNPJ incorreto")
	@Column(unique=true)
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@CPF(message = "CPF incorreto")
	@Column(unique=true)
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@Transient
	public String getCpfCnpj() {
		if (TipoPessoa.PESSOA_FISICA == this.getTipoPessoa()) {
			return this.getCpf();
		} else {
			return this.getCnpj();
		}
	}
	
	@NotNull(message="É necessário registrar ao menos um telefone para contato.")
	@Column(name="telefone1")
	public String getTelefonePrincipal() {
		return telefonePrincipal;
	}

	public void setTelefonePrincipal(String telefonePrincipal) {
		this.telefonePrincipal = telefonePrincipal;
	}

	@Column(name="telefone2")
	public String getTelefoneOpcional() {
		return telefoneOpcional;
	}

	public void setTelefoneOpcional(String telefoneOpcional) {
		this.telefoneOpcional = telefoneOpcional;
	}

	@NotNull(message="O preenchimento do campo Razão Social é obrigatório.")
	@Column(name="razao_social")
	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	@NotNull(message="O preenchimento do campo Nome Fantásia é obrigatório.")
	@Column(name="nome_fantasia")
	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@NotNull(message="O preenchimento do campo Logradouro é obrigatório.")
	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	@NotNull(message="O preenchimento do campo CEP é obrigatório.")
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@NotNull(message="O preenchimento do campo Bairro é obritório.")
	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	@NotNull
	@ManyToOne
	@JoinColumn(name="id_cidade")
	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
