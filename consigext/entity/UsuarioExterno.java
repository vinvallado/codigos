package br.mil.fab.consigext.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the T_USUARIO_EXTERNO database table.
 * 
 */
@Entity
@Table(name="T_USUARIO_EXTERNO", schema="ACESSO")
@SequenceGenerator(name="ACESSO.SQ_USUARIO",sequenceName="ACESSO.SQ_USUARIO",allocationSize=1)
@NamedQuery(name="UsuarioExterno.findAll", query="SELECT u FROM UsuarioExterno u")
public class UsuarioExterno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="ACESSO.SQ_USUARIO",strategy=GenerationType.SEQUENCE)
	@Column(name="ID_USUARIO")
	private long id;

	@Column(name="CD_ORG")
	private String cdOrg;

	@Column(name="DS_RAZAO_BLOQUEIO")
	private String dsRazaoBloqueio;

	@Column(name="EMAIL")
	private String nmEmail;

	@Column(name="NM_PESSOA")
	private String nmPessoa;

	@Column(name="NR_CPF")
	private String nrCpf;

	@Column(name="PASSWORD_SHA")
	private String passwordSha;

	@Column(name="ST_BLOQUEADO")
	private String stBloqueado;

	@Column(name="TP_USUARIO")
	private String tpUsuario;
	
	public UsuarioExterno() {
	}

	public long getIdUsuarioExterno() {
		return this.id;
	}

	public void setIdUsuarioExterno(long idUsuarioExterno) {
		this.id = idUsuarioExterno;
	}

	public String getCdOrg() {
		return this.cdOrg;
	}

	public void setCdOrg(String cdOrg) {
		this.cdOrg = cdOrg;
	}

	public String getDsRazaoBloqueio() {
		return this.dsRazaoBloqueio;
	}

	public void setDsRazaoBloqueio(String dsRazaoBloqueio) {
		this.dsRazaoBloqueio = dsRazaoBloqueio;
	}

	public String getNmEmail() {
		return this.nmEmail;
	}

	public void setNmEmail(String nmEmail) {
		this.nmEmail = nmEmail;
	}

	public String getNmPessoa() {
		return this.nmPessoa;
	}

	public void setNmPessoa(String nmPessoa) {
		this.nmPessoa = nmPessoa;
	}

	public String getNrCpf() {
		return this.nrCpf;
	}

	public void setNrCpf(String nrCpf) {
		this.nrCpf = nrCpf;
	}

	public String getPasswordSha() {
		return this.passwordSha;
	}

	public void setPasswordSha(String passwordSha) {
		this.passwordSha = passwordSha;
	}

	public String getStBloqueado() {
		return this.stBloqueado;
	}

	public void setStBloqueado(String stBloqueado) {
		this.stBloqueado = stBloqueado;
	}

	public String getTpUsuario() {
		return this.tpUsuario;
	}

	public void setTpUsuario(String tpUsuario) {
		this.tpUsuario = tpUsuario;
	}


}