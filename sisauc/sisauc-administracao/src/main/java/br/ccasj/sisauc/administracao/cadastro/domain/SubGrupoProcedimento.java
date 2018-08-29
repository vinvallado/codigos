package br.ccasj.sisauc.administracao.cadastro.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "subgrupo", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class SubGrupoProcedimento extends EntidadeGenerica{
	
	private static final long serialVersionUID = 746965455427051438L;
	private static final String SEQ_NAME = "subgrupo_id_seq";

	private String codigo;
	private String descricao;
	private GrupoProcedimento grupoProcedimento;
	
	public SubGrupoProcedimento() {
		super();
	}
	
	public SubGrupoProcedimento(Integer id) {
		super(id);
	}
	
	public SubGrupoProcedimento(Integer id, String codigo, String descricao) {
		super(id);
		this.codigo = codigo;
		this.descricao = descricao;
	}

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_grupo")
	public GrupoProcedimento getGrupoProcedimento() {
		return grupoProcedimento;
	}

	public void setGrupoProcedimento(GrupoProcedimento grupoProcedimento) {
		this.grupoProcedimento = grupoProcedimento;
	}

	

}
