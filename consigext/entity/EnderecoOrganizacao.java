package br.mil.fab.consigext.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name="T_ENDERECO_ORGANIZACAO", schema="SIG")
public class EnderecoOrganizacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EnderecoOrganizacaoPK id;

	@Column(name="CD_CEP")
	private String cdCep;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FIM")
	private Date dtFim;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INICIO")
	private Date dtInicio;

	@Column(name="NM_BAIRRO")
	private String nmBairro;

	@Column(name="NM_COMPL")
	private String nmCompl;

	@Column(name="NM_LOGRD")
	private String nmLogrd;

	@Column(name="ST_EXTINTO")
	private String stExtinto;

	@Column(name="ST_SEDE")
	private String stSede;

	//bi-directional many-to-one association to Localidade
	@ManyToOne
	@JoinColumn(name="CD_LOCALD")
	private Localidade localidade;

	public EnderecoOrganizacao() {}
	

	public EnderecoOrganizacao(String stSede) {
		super();
		this.stSede = stSede;
	}

	public EnderecoOrganizacaoPK getId() {
		return this.id;
	}

	public void setId(EnderecoOrganizacaoPK id) {
		this.id = id;
	}

	public String getCdCep() {
		return this.cdCep;
	}

	public void setCdCep(String cdCep) {
		this.cdCep = cdCep;
	}

	public Date getDtFim() {
		return this.dtFim;
	}

	public void setDtFim(Date dtFim) {
		this.dtFim = dtFim;
	}

	public Date getDtInicio() {
		return this.dtInicio;
	}

	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}

	public String getNmBairro() {
		return this.nmBairro;
	}

	public void setNmBairro(String nmBairro) {
		this.nmBairro = nmBairro;
	}

	public String getNmCompl() {
		return this.nmCompl;
	}

	public void setNmCompl(String nmCompl) {
		this.nmCompl = nmCompl;
	}

	public String getNmLogrd() {
		return this.nmLogrd;
	}

	public void setNmLogrd(String nmLogrd) {
		this.nmLogrd = nmLogrd;
	}

	public String getStExtinto() {
		return this.stExtinto;
	}

	public void setStExtinto(String stExtinto) {
		this.stExtinto = stExtinto;
	}

	public String getStSede() {
		return this.stSede;
	}

	public void setStSede(String stSede) {
		this.stSede = stSede;
	}

	public Localidade getLocalidade() {
		return this.localidade;
	}

	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}

}