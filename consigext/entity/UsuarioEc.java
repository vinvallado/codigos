package br.mil.fab.consigext.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;


/**
 * The persistent class for the T_USUARIO_EC database table.
 * 
 */
@Entity
@Table(name="T_USUARIO_EC")
@NamedQuery(name="UsuarioEc.findAll", query="SELECT u FROM UsuarioEc u")
public class UsuarioEc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_USUARIO_EC")
	private long id;

	@Column(name="ID_USUARIO_EXTERNO")
	private Long idUsuarioExterno;

	@Column(name="ST_USUARIO")
	private Long stUsuario;

	//bi-directional many-to-one association to EntidadeConsig
	@ManyToOne
	@JoinColumn(name="ID_ENTIDADE_CONSIG")
	@JsonIgnore
	private EntidadeConsig entidadeConsig;

	public UsuarioEc() {
	}

	public long getIdUsuarioEc() {
		return this.id;
	}

	public void setIdUsuarioEc(long idUsuarioEc) {
		this.id = idUsuarioEc;
	}

	public Long getIdUsuarioExterno() {
		return this.idUsuarioExterno;
	}

	public void setIdUsuarioExterno(Long idUsuarioExterno) {
		this.idUsuarioExterno = idUsuarioExterno;
	}

	public Long getStUsuario() {
		return this.stUsuario;
	}

	public void setStUsuario(Long stUsuario) {
		this.stUsuario = stUsuario;
	}

	public EntidadeConsig getTEntidadeConsig() {
		return this.entidadeConsig;
	}

	public void setTEntidadeConsig(EntidadeConsig TEntidadeConsig) {
		this.entidadeConsig = TEntidadeConsig;
	}

}