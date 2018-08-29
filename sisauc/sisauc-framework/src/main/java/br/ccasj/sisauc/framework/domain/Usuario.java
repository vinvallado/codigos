package br.ccasj.sisauc.framework.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "usuario", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class Usuario extends EntidadeGenerica {

	private static final long serialVersionUID = -5887420596352902341L;

	private static final String SEQ_NAME = "usuario_id_seq";

	private String nome;
	private String login;
	private String senha;
	private String email;
	private boolean ativo = true;
	private boolean leuNotasVersao = false;
	private OrganizacaoMilitar organizacaoMilitar;
	private Set<Perfil> perfis = new HashSet<Perfil>();
	private String cpf;
	
	public Usuario() {
		super();
	}

	public Usuario(String nome, String login) {
		super();
		this.nome = nome;
		this.login = login;
	}

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}

	@NotEmpty(message = "Campo 'Nome' é obrigatório")
	public String getNome() {
		return nome;
	}


	@NotEmpty(message = "Campo 'Login' é obrigatório")
	public String getLogin() {
		return login;
	}

	public String getSenha() {
		return senha;
	}

	@Email
	public String getEmail() {
		return email;
	}

	public boolean isAtivo() {
		return ativo;
	}

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_organizacao_militar")
	public OrganizacaoMilitar getOrganizacaoMilitar() {
		return organizacaoMilitar;
	}

	@ElementCollection(targetClass = Perfil.class, fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_perfil", schema = EntidadeGenerica.SCHEMA_SISAUC, joinColumns = @JoinColumn(name = "id_usuario"))
	@Enumerated(EnumType.STRING)
	@Column(name = "id_perfil", nullable = false)
	public Set<Perfil> getPerfis() {
		return perfis;
	}

	@Column(name = "leu_notas_versao")
	public boolean isLeuNotasVersao() {
		return leuNotasVersao;
	}

	@Column(name="cpf", length=11)
	public String getCpf(){
		return this.cpf;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public void setOrganizacaoMilitar(OrganizacaoMilitar organizacaoMilitar) {
		this.organizacaoMilitar = organizacaoMilitar;
	}

	public void setPerfis(Set<Perfil> perfis) {
		this.perfis = perfis;
	}

	public void setLeuNotasVersao(boolean leuNotasVersao) {
		this.leuNotasVersao = leuNotasVersao;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
