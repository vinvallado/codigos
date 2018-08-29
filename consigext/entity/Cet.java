package br.mil.fab.consigext.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the T_CET database table.
 * 
 */
@Entity
@Table(name="T_CET")
@NamedQuery(name="Cet.findAll", query="SELECT c FROM Cet c")
@SequenceGenerator(name = "SQ_ID_CET", sequenceName = "SQ_ID_CET", allocationSize = 1)
public class Cet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_CET")
	@GeneratedValue(generator = "SQ_ID_CET", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name="CD_ANOMES")
	private long cdAnomes;

	@Column(name="NR_PARCELA")
	private long nrParcela;

	@Column(name="VL_CET")
	private BigDecimal vlCet;

	//bi-directional many-to-one association to ServicoConsig
	@ManyToOne
	@JoinColumn(name="ID_SERVICO_CONSIG")
	private ServicoConsig servicoConsig;

	public Cet() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long idCet) {
		this.id = idCet;
	}
	
	
	public long getCdAnomes() {
		return cdAnomes;
	}

	public void setCdAnomes(long cdAnomes) {
		this.cdAnomes = cdAnomes;
	}

	public long getNrParcela() {
		return nrParcela;
	}

	public void setNrParcela(long nrParcela) {
		this.nrParcela = nrParcela;
	}

	public ServicoConsig getServicoConsig() {
		return servicoConsig;
	}

	public void setServicoConsig(ServicoConsig servicoConsig) {
		this.servicoConsig = servicoConsig;
	}

	public BigDecimal getVlCet() {
		return this.vlCet;
	}

	public void setVlCet(BigDecimal vlCet) {
		this.vlCet = vlCet;
	}

	public ServicoConsig getTServicoConsig() {
		return this.servicoConsig;
	}

	public void setTServicoConsig(ServicoConsig TServicoConsig) {
		this.servicoConsig = TServicoConsig;
	}

}