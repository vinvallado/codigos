package br.mil.fab.consigext.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the T_PARAMETRO_OPCAO database table.
 * 
 */
@Entity
@Table(name="T_PARAMETRO_OPCAO")
@NamedQuery(name="ParametroOpcao.findAll", query="SELECT p FROM ParametroOpcao p")
public class ParametroOpcao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_PARAMETRO_OPCAO")
	private long id;

	@Column(name="VL_DEFAULT")
	private Long vlDefault;

	@Column(name="VL_OPCAO")
	private String vlOpcao;
	
	@Column(name="TX_LABEL")
	private String txLabel;


	//bi-directional many-to-one association to Parametro
	@ManyToOne
	@JoinColumn(name="ID_PARAMETRO")
	private Parametro parametro;

	public ParametroOpcao() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long idParametroOpcao) {
		this.id = idParametroOpcao;
	}

	public Long getVlDefault() {
		return this.vlDefault;
	}

	public void setVlDefault(Long vlDefault) {
		this.vlDefault = vlDefault;
	}

	public String getVlOpcao() {
		return this.vlOpcao;
	}

	public void setVlOpcao(String vlOpcao) {
		this.vlOpcao = vlOpcao;
	}

	public Parametro getParametro() {
		return this.parametro;
	}

	public void setParametro(Parametro TParametro) {
		this.parametro = TParametro;
	}

	public String getTxLabel() {
		return txLabel;
	}

	public void setTxLabel(String txLabel) {
		this.txLabel = txLabel;
	}

}