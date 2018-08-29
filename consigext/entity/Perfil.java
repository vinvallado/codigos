package br.mil.fab.consigext.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

/**
 * The persistent class for the T_HISTORICO_CONSIGNACAO database table.
 * 
 */
@Entity
@Table(name = "T_PERFIL")
@SequenceGenerator(name = "SQ_ID_PERFIL", sequenceName = "SQ_ID_PERFIL", allocationSize = 1)
public class Perfil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "SQ_ID_PERFIL", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_PERFIL")
	private long id;

	@Column(name = "NM_PERFIL")
	@NotBlank
	private String nmPerfil;

	@Column(name = "ST_BLOQUEIO")
	private long stBloqueioPerfil;

	// bi-directional many-to-one association to Consignacao
	@ManyToOne
	@JoinColumn(name = "ID_ENTIDADE_CONSIG")
	private EntidadeConsig entidadeConsig;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "T_PERFIL_PERMISSAO", joinColumns = { @JoinColumn(name = "ID_PERFIL") }, inverseJoinColumns = {
			@JoinColumn(name = "ID_PERMISSAO") })

	private List<Permissao> permissao;

	public Perfil() {
	}

	public Perfil(long id) {
		super();
		this.id = id;
	}

	public Perfil(long id, String nmPerfil, EntidadeConsig entidadeConsig, List<Permissao> permissao,
			long bloqueioPerfil) {
		super();
		this.id = id;
		this.nmPerfil = nmPerfil;
		this.entidadeConsig = entidadeConsig;
		this.permissao = permissao;
		this.stBloqueioPerfil = bloqueioPerfil;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNmPerfil() {
		return nmPerfil;
	}

	public void setNmPerfil(String nmPerfil) {
		this.nmPerfil = nmPerfil;
	}

	public EntidadeConsig getEntidadeConsig() {
		return entidadeConsig;
	}

	public void setEntidadeConsig(EntidadeConsig entidadeConsig) {
		this.entidadeConsig = entidadeConsig;
	}

	public List<Permissao> getPermissao() {
		return permissao;
	}

	public void setPermissao(List<Permissao> permissao) {
		this.permissao = permissao;
	}

	public long getStBloqueioPerfil() {
		return stBloqueioPerfil;
	}

	public void setStBloqueioPerfil(long stBloqueioPerfil) {
		this.stBloqueioPerfil = stBloqueioPerfil;
	}

}