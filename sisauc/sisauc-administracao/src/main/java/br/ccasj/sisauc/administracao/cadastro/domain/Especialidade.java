package br.ccasj.sisauc.administracao.cadastro.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "especialidade", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class Especialidade extends EntidadeGenerica{

	private static final long serialVersionUID = -5612634038871072873L;
	
	private static final String SEQ_NAME = "especialidade_id_seq";
	
	private String nome;
	private String sigla;
	private boolean ativo = true;
	private TipoEspecialidade tipo;
	
	public Especialidade() {
		super();
	}

	public Especialidade(Integer id) {
		super(id);
	}
	
	public Especialidade(String nome) {
		super();
		this.nome = nome;
	}
	
	public Especialidade(Integer id, String nome) {
		super(id);
		this.nome = nome;
	}
	
	public Especialidade(String nome, String sigla) {
		super();
		this.nome = nome;
		this.sigla = sigla;
	}

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getSigla() {
		return sigla;
	}
	
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Column
	@Enumerated(EnumType.STRING)
	public TipoEspecialidade getTipo() {
		return tipo;
	}

	public void setTipo(TipoEspecialidade tipo) {
		this.tipo = tipo;
	}

}
