package br.mil.fab.consigext.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the T_PARAMETRO_SERVICO database table.
 * 
 */
@Entity
@Table(name="T_PARAMETRO_SISTEMA")
@NamedQuery(name="ParametroSistema.findAll", query="SELECT p FROM ParametroSistema p")
@SequenceGenerator(name="SQ_ID_PARAMETRO_SISTEMA",sequenceName="SQ_ID_PARAMETRO_SISTEMA",allocationSize=1)
public class ParametroSistema implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="SQ_ID_PARAMETRO_SISTEMA",strategy=GenerationType.SEQUENCE)
	@Column(name="ID_PARAMETRO_SISTEMA")
	private Long id;

	@Column(name="VL_PARAMETRO_SISTEMA")
	private String vlParametroSistema;
	
	//bi-directional many-to-one association to Parametro
	@ManyToOne
	@JoinColumn(name="ID_PARAMETRO_TIPO")
	@JsonIgnore
	private ParametroTipo parametroTipo;

	@Column(name="DS_PARAMETRO_SISTEMA")
	private String dsParametroSistema;

	public ParametroSistema() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVlParametroSistema() {
		return vlParametroSistema;
	}

	public void setVlParametroSistema(String vlParametroSistema) {
		this.vlParametroSistema = vlParametroSistema;
	}

	public ParametroTipo getParametroTipo() {
		return parametroTipo;
	}

	public void setParametroTipo(ParametroTipo parametroTipo) {
		this.parametroTipo = parametroTipo;
	}

	public String getDsParametroSistema() {
		return dsParametroSistema;
	}

	public void setDsParametroSistema(String dsParametroSistema) {
		this.dsParametroSistema = dsParametroSistema;
	}

	

}