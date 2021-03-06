package br.ccasj.sisauc.administracao.cadastro.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "convenio", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class Convenio  extends EntidadeGenerica  {
	
	private static final long serialVersionUID = -3830991155247544197L;

	private static final String SEQ_NAME = "convenio_id_seq";
	
	private String descricao;
	private String sigla;
	
	public static String SIGLA_AMHC = "AMHC";
	
	public Convenio() {
	}
	
	public Convenio(String sigla) {
		this.sigla = sigla;
	}

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}
	
	@Column(name = "descricao")
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@NotNull
	@Column(name = "sigla")
	public String getSigla() {
		return sigla;
	}
	
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
}
