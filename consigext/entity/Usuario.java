package br.mil.fab.consigext.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the T_USUARIO database table.
 * 
 */
@Entity
@Table(name="T_USUARIO", schema="ACESSO")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_USUARIO")
	private long idUsuario;

	@Column(name="DS_RAZAO_BLOQUEIO")
	private String dsRazaoBloqueio;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_ACEITE_TERMO")
	private Date dtAceiteTermo;

	private String email;

	@Column(name="NR_CPF")
	private String nrCpf;

	private BigDecimal salto;

	@Column(name="ST_BLOQUEADO")
	private String stBloqueado;

	@Column(name="ST_EMAIL_CORP")
	private String stEmailCorp;

	@Column(name="ST_NIVEL_PROXY")
	private String stNivelProxy;

	@Column(name="ST_PROXY")
	private String stProxy;

	@Column(name="ST_VPN")
	private String stVpn;
	
	@Column(name="PASSWORD_SHA")
	private String passwordSha;
	
	@OneToOne
	@JoinColumn(name="NR_ORDEM")
	private PesfisComgep pesfis;

	public Usuario() {
	}

	public long getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getDsRazaoBloqueio() {
		return this.dsRazaoBloqueio;
	}

	public void setDsRazaoBloqueio(String dsRazaoBloqueio) {
		this.dsRazaoBloqueio = dsRazaoBloqueio;
	}

	public Date getDtAceiteTermo() {
		return this.dtAceiteTermo;
	}

	public void setDtAceiteTermo(Date dtAceiteTermo) {
		this.dtAceiteTermo = dtAceiteTermo;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNrCpf() {
		return this.nrCpf;
	}

	public void setNrCpf(String nrCpf) {
		this.nrCpf = nrCpf;
	}

	public BigDecimal getSalto() {
		return this.salto;
	}

	public void setSalto(BigDecimal salto) {
		this.salto = salto;
	}

	public String getStBloqueado() {
		return this.stBloqueado;
	}

	public void setStBloqueado(String stBloqueado) {
		this.stBloqueado = stBloqueado;
	}

	public String getStEmailCorp() {
		return this.stEmailCorp;
	}

	public void setStEmailCorp(String stEmailCorp) {
		this.stEmailCorp = stEmailCorp;
	}

	public String getStNivelProxy() {
		return this.stNivelProxy;
	}

	public void setStNivelProxy(String stNivelProxy) {
		this.stNivelProxy = stNivelProxy;
	}

	public String getStProxy() {
		return this.stProxy;
	}

	public void setStProxy(String stProxy) {
		this.stProxy = stProxy;
	}

	public String getStVpn() {
		return this.stVpn;
	}

	public void setStVpn(String stVpn) {
		this.stVpn = stVpn;
	}

	public String getPasswordSha() {
		return passwordSha;
	}

	public void setPasswordSha(String passwordSha) {
		this.passwordSha = passwordSha;
	}

	public PesfisComgep getPesfis() {
		return pesfis;
	}

	public void setPesfis(PesfisComgep pesfis) {
		this.pesfis = pesfis;
	}

}