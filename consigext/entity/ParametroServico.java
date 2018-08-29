package br.mil.fab.consigext.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the T_PARAMETRO_SERVICO database table.
 * 
 */
@Entity
@Table(name="T_PARAMETRO_SERVICO")
@NamedQuery(name="ParametroServico.findAll", query="SELECT p FROM ParametroServico p")
@SequenceGenerator(name="SQ_ID_PARAMETRO_SERVICO",sequenceName="SQ_ID_PARAMETRO_SERVICO",allocationSize=1)
public class ParametroServico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="SQ_ID_PARAMETRO_SERVICO",strategy=GenerationType.SEQUENCE)
	@Column(name="ID_PARAMETRO_SERVICO")
	private long id;

	@Column(name="VL_PARAMETRO")
	private String vlParametro;

	//bi-directional many-to-one association to Parametro
	@ManyToOne
	@JoinColumn(name="ID_PARAMETRO")
	private Parametro parametro;

	//bi-directional many-to-one association to Servico
	@ManyToOne
	@JoinColumn(name="ID_SERVICO", insertable=true, updatable=true)
	private Servico servico;

	public ParametroServico() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long idParametroServico) {
		this.id = idParametroServico;
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

	public Servico getServico() {
		return this.servico;
	}

	public void setServico(Servico TServico) {
		this.servico = TServico;
	}

}