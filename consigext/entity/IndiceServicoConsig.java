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

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="T_INDICE_SERVICO_CONSIG")
@SequenceGenerator(name = "SQ_ID_SERVICO_CONSIG", sequenceName = "SQ_ID_SERVICO_CONSIG", allocationSize = 1)
public class IndiceServicoConsig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_INDICE_SERVICO_CONSIG")
	@GeneratedValue(generator = "SQ_ID_SERVICO_CONSIG", strategy = GenerationType.SEQUENCE)
	private long id;

	@Column(name="CD_INDICE")
	private String cdIndice;
	
	@Column(name="NM_INDICE")
	private String nmIndice;
	
	@Column(name="ST_EXCLUIDO")
	private int stExcluido;
	
	@ManyToOne
	@JoinColumn(name="ID_SERVICO_CONSIG")
	@JsonIgnore
	private ServicoConsig servicoConsig;

	public IndiceServicoConsig() {
		super(); 
	}
	
	public IndiceServicoConsig(long id, String cdIndice, String nmIndice, int stExcluido, ServicoConsig servicoConsig) {
		super();
		this.id = id;
		this.cdIndice = cdIndice;
		this.nmIndice = nmIndice;
		this.stExcluido = stExcluido;
		this.servicoConsig = servicoConsig;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCdIndice() {
		return cdIndice;
	}

	public void setCdIndice(String cdIndice) {
		this.cdIndice = cdIndice;
	}

	public String getNmIndice() {
		return nmIndice;
	}

	public void setNmIndice(String nmIndice) {
		this.nmIndice = nmIndice;
	}

	public int getStExcluido() {
		return stExcluido;
	}

	public void setStExcluido(int stExcluido) {
		this.stExcluido = stExcluido;
	}

	public ServicoConsig getServicoConsig() {
		return servicoConsig;
	}

	public void setServicoConsig(ServicoConsig servicoConsig) {
		this.servicoConsig = servicoConsig;
	}

}