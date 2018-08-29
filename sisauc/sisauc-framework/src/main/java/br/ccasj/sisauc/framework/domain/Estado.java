package br.ccasj.sisauc.framework.domain;

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

@Entity
@Table(name = "estado", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class Estado extends EntidadeGenerica {

	private static final long serialVersionUID = -3836173207355296002L;

	private static final String SEQ_NAME = "estado_id_seq";

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}

	private String sigla;
	private String nome;
	private Pais pais;

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

	@ManyToOne
	@JoinColumn(name = "id_pais")
	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

}
