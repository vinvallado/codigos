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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the T_HISTORICO_CONSIGNACAO database table.
 * 
 */
@Entity
@Table(name="T_PERMISSAO")
@SequenceGenerator(name="SQ_ID_PERMISSAO",sequenceName="SQ_ID_PERMISSAO",allocationSize=1)
public class Permissao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="SQ_ID_PERMISSAO",strategy=GenerationType.SEQUENCE)
	@Column(name="ID_PERMISSAO")
	private long id;

	@Column(name="NM_PERMISSAO")
	private String nmPermissao;
	
	@Column(name="TP_PERMISSAO")
	private String tpPermissao;

	@ManyToMany(mappedBy = "permissao", fetch = FetchType.LAZY)
    private List<Perfil> perfil;

	public Permissao() {
	}
	
	public Permissao(long id, String nmPermissao, String tpPermissao, List<Perfil> perfil) {
		super();
		this.id = id;
		this.nmPermissao = nmPermissao;
		this.tpPermissao = tpPermissao;
		this.perfil = perfil;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNmPermissao() {
		return nmPermissao;
	}

	public void setNmPermissao(String nmPermissao) {
		this.nmPermissao = nmPermissao;
	}

	public String getTpPermissao() {
		return tpPermissao;
	}

	public void setTpPermissao(String tpPermissao) {
		this.tpPermissao = tpPermissao;
	}

	public List<Perfil> getPerfil() {
		return perfil;
	}

	public void setPerfil(List<Perfil> perfil) {
		this.perfil = perfil;
	}
	
		
}