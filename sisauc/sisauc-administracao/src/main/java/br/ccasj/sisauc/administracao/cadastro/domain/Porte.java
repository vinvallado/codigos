package br.ccasj.sisauc.administracao.cadastro.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "porte", schema = EntidadeGenerica.SCHEMA_SISAUC, uniqueConstraints={@UniqueConstraint(columnNames="codigo")})
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class Porte extends EntidadeGenerica {

	private static final long serialVersionUID = -7053036236172211510L;

	private static final String SEQ_NAME = "porte_id_seq";
	
	private String codigo;
	private double valor;

	public Porte() {
		super();
	}
	
	public Porte(Integer id, String codigo, float valor) {
		super(id);
		this.codigo = codigo;
		this.valor = valor;
	}
	
	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}

	@NotNull(message="O preenchimento do campo Código é obrigatório.")
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@NotNull(message="O preenchimento do campo Valor é obrigatório.")
	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	
}
