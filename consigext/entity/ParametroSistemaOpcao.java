package br.mil.fab.consigext.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the T_PARAMETRO_OPCAO database table.
 * 
 */
@Entity
@Table(name="T_PARAMETRO_SISTEMA_OPCAO")
@NamedQuery(name="ParametroSistemaOpcao.findAll", query="SELECT p FROM ParametroSistemaOpcao p")
public class ParametroSistemaOpcao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_PARAMETRO_SISTEMA_OPCAO")
	private long id;

	@Column(name="VL_DEFAULT")
	private Long vlDefault;

	@Column(name="VL_OPCAO")
	private String vlOpcao;
	
	@Column(name="TX_LABEL")
	private String txLabel;

	//bi-directional many-to-one association to Parametro
	@ManyToOne
	@JoinColumn(name="ID_PARAMETRO_SISTEMA")
	private ParametroSistema parametroSistema;

	public ParametroSistemaOpcao() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getVlDefault() {
		return vlDefault;
	}

	public void setVlDefault(Long vlDefault) {
		this.vlDefault = vlDefault;
	}

	public String getVlOpcao() {
		return vlOpcao;
	}

	public void setVlOpcao(String vlOpcao) {
		this.vlOpcao = vlOpcao;
	}

	public String getTxLabel() {
		return txLabel;
	}

	public void setTxLabel(String txLabel) {
		this.txLabel = txLabel;
	}

	public ParametroSistema getParametroSistema() {
		return parametroSistema;
	}

	public void setParametroSistema(ParametroSistema parametroSistema) {
		this.parametroSistema = parametroSistema;
	}


}