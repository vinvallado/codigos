package br.ccasj.sisauc.framework.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "organizacao_militar", schema = EntidadeGenerica.SCHEMA_SISAUC)
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn (name = "tipo_om", discriminatorType = DiscriminatorType.STRING)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class OrganizacaoMilitar extends EntidadeGenerica {

	private static final long serialVersionUID = -8478075499219930510L;

	private static final String SEQ_NAME = "organizacao_militar_id_seq";

	private String sigla;
	private String nome;
	private boolean ativo = true;
	private Cidade cidade;
	
	public OrganizacaoMilitar() {
		super();
	}
	
	public OrganizacaoMilitar(Integer id, String sigla, String nome) {
		super(id);
		this.sigla = sigla;
		this.nome = nome;
	}

	public OrganizacaoMilitar(Integer id, String sigla, String nome, boolean ativo) {
		super(id);
		this.sigla = sigla;
		this.nome = nome;
		this.ativo = ativo;
	}
	
	public OrganizacaoMilitar(String sigla) {
		this.sigla = sigla;
	}

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}

	@NotEmpty(message = "Sigla é um campo obrigatório")
	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@ManyToOne
	@JoinColumn(name = "id_cidade")
	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

}
