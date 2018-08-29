package br.mil.fab.consigext.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="T_HISTORICO_PARCELA")
@SequenceGenerator(name="SQ_ID_HISTORICO_PARCELA",sequenceName="SQ_ID_HISTORICO_PARCELA",allocationSize=1)
public class HistoricoParcela implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="SQ_ID_HISTORICO_PARCELA",strategy=GenerationType.SEQUENCE)
	@Column(name="ID_HISTORICO_PARCELA")
	private Long id;

	@Column(name="VL_ANTERIOR")
	private String vlAnterior;
	
	@Column(name="VL_NOVO")
	private String vlNovo;

	@ManyToOne
	@JoinColumn(name="ID_PARCELA_CONSIGNACAO")
	private ParcelaConsignacao parcela;
	
	@ManyToOne
	@JoinColumn(name="ID_HISTORICO_CONSIGNACAO")
	private HistoricoConsignacao historicoConsignacao;

	public HistoricoParcela() {
	}

	public HistoricoParcela(Long id, String vlAnterior, String vlNovo, ParcelaConsignacao parcela,
			HistoricoConsignacao historicoConsignacao) {
		super();
		this.id = id;
		this.vlAnterior = vlAnterior;
		this.vlNovo = vlNovo;
		this.parcela = parcela;
		this.historicoConsignacao = historicoConsignacao;
	}
	
	public HistoricoParcela(String vlAnterior, String vlNovo, ParcelaConsignacao parcela) {
		super();
		this.vlAnterior = vlAnterior;
		this.vlNovo = vlNovo;
		this.parcela = parcela;
	}


	public HistoricoParcela(String vlAnterior, String vlNovo, ParcelaConsignacao parcela,
			HistoricoConsignacao historicoConsignacao) {
		super();
		this.vlAnterior = vlAnterior;
		this.vlNovo = vlNovo;
		this.parcela = parcela;
		this.historicoConsignacao = historicoConsignacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVlAnterior() {
		return vlAnterior;
	}

	public void setVlAnterior(String vlAnterior) {
		this.vlAnterior = vlAnterior;
	}

	public String getVlNovo() {
		return vlNovo;
	}

	public void setVlNovo(String vlNovo) {
		this.vlNovo = vlNovo;
	}

	public ParcelaConsignacao getParcela() {
		return parcela;
	}

	public void setParcela(ParcelaConsignacao parcela) {
		this.parcela = parcela;
	}

	public HistoricoConsignacao getHistoricoConsignacao() {
		return historicoConsignacao;
	}

	public void setHistoricoConsignacao(HistoricoConsignacao historicoConsignacao) {
		this.historicoConsignacao = historicoConsignacao;
	}

	
	
}