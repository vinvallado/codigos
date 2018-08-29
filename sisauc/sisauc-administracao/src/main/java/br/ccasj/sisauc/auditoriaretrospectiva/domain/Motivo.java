package br.ccasj.sisauc.auditoriaretrospectiva.domain;

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
@Table(name = "motivo", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class Motivo extends EntidadeGenerica {

	private static final long serialVersionUID = -2981112510676566189L;

	private static final String SEQ_NAME = "motivo_id_seq";
	
	private String grupo;
	private String codigo;
	private String descricao;
	private boolean ativo;
	private boolean disponivelRetrospectiva;
	private boolean habilitadoRetrospectiva;

	public Motivo() {}
	
	public Motivo(Integer id, String codigo, String descricao) {
		this.id = id;
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public Motivo(Integer id, String grupo, String codigo, String descricao) {
		this.id = id;
		this.grupo = grupo;
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return super.getId();
	}
	
	@Column(nullable=false)
	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	@Column(nullable=false)
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@NotNull
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Column(name="disponivel_retrospectiva")
	public boolean isDisponivelRetrospectiva() {
		return disponivelRetrospectiva;
	}

	public void setDisponivelRetrospectiva(boolean disponivelRetrospectiva) {
		this.disponivelRetrospectiva = disponivelRetrospectiva;
	}

	@Column(name="habilitado_retrospectiva")
	public boolean isHabilitadoRetrospectiva() {
		return habilitadoRetrospectiva;
	}

	public void setHabilitadoRetrospectiva(boolean habilitadoRetrospectiva) {
		this.habilitadoRetrospectiva = habilitadoRetrospectiva;
	}

}