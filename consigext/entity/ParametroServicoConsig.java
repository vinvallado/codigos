package br.mil.fab.consigext.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the T_PARAMETRO_SERVICO_CONSIG database table.
 * 
 */
@Entity
@Table(name="T_PARAMETRO_SERVICO_CONSIG")
@NamedQuery(name="ParametroServicoConsig.findAll", query="SELECT p FROM ParametroServicoConsig p")
public class ParametroServicoConsig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_PARAMETRO_SERVICO_CONSIG")
	private long id;

	@Column(name="VL_PARAMETRO")
	private String vlParametro;

	//bi-directional many-to-one association to Parametro
	@ManyToOne
	@JoinColumn(name="ID_PARAMETRO")
	private Parametro parametro;

	//bi-directional many-to-one association to ServicoConsig
	@ManyToOne
	@JoinColumn(name="ID_SERVICO_CONSIG")
	private ServicoConsig servicoConsig;

	public ParametroServicoConsig() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long idParametroServicoConsig) {
		this.id = idParametroServicoConsig;
	}

	public String getVlParametro() {
		return this.vlParametro;
	}

	public void setVlParametro(String vlParametro) {
		this.vlParametro = vlParametro;
	}

	public Parametro getParametro() {
		return this.parametro;
	}

	public void setParametro(Parametro TParametro) {
		this.parametro = TParametro;
	}

	public ServicoConsig getServicoConsig() {
		return this.servicoConsig;
	}

	public void setServicoConsig(ServicoConsig TServicoConsig) {
		this.servicoConsig = TServicoConsig;
	}

}