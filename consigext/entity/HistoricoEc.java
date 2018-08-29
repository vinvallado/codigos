package br.mil.fab.consigext.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.mil.fab.consigext.enums.TipoHistoricoEc;


/**
 * The persistent class for the T_HISTORICO_EC database table.
 * 
 */
@Entity
@Table(name="T_HISTORICO_EC")
@SequenceGenerator(name="sg_hec_id_historico",sequenceName="sg_hec_id_historico",allocationSize=1)
public class HistoricoEc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sg_hec_id_historico",strategy=GenerationType.SEQUENCE)
	@Column(name="ID_HISTORICO")
	private long id;

	@Column(name="DS_HISTORICO")
	private String dsHistorico;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_HISTORICO")
	private Date dtHistorico;

	@Column(name="NR_CPF_RESPONSAVEL")
	private String nrCpfResponsavel;

	@Column(name="TP_HISTORICO")
	private String tpHistorico;

	@Column(name="TX_IP")
	private String txIp;

	//bi-directional many-to-one association to EntidadeConsig
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="ID_ENTIDADE_CONSIG")
	private EntidadeConsig entidadeConsig;

	public HistoricoEc() {
	}
	public HistoricoEc(Date dtHistorico, String txIp, TipoHistoricoEc tipoHistoricoEc, EntidadeConsig entidadeConsig, String nrCpfResponsavel, String dsHistorico) {
		this.dtHistorico = dtHistorico;
		this.txIp = txIp;
		this.tpHistorico = tipoHistoricoEc.toString();
		this.entidadeConsig = entidadeConsig;
		this.nrCpfResponsavel = nrCpfResponsavel;
		this.dsHistorico = dsHistorico;
	}
	public long getId() {
		return this.id;
	}

	public void setId(long idHistorico) {
		this.id = idHistorico;
	}

	public String getDsHistorico() {
		return this.dsHistorico;
	}

	public void setDsHistorico(String dsHistorico) {
		this.dsHistorico = dsHistorico;
	}

	public Date getDtHistorico() {
		return this.dtHistorico;
	}

	public void setDtHistorico(Date dtHistorico) {
		this.dtHistorico = dtHistorico;
	}

	public String getNrCpfResponsavel() {
		return nrCpfResponsavel;
	}
	public void setNrCpfResponsavel(String nrCpfResponsavel) {
		this.nrCpfResponsavel = nrCpfResponsavel;
	}
	public String getTpHistorico() {
		return this.tpHistorico;
	}

	public void setTpHistorico(String tpHistorico) {
		this.tpHistorico = tpHistorico;
	}

	public String getTxIp() {
		return this.txIp;
	}

	public void setTxIp(String txIp) {
		this.txIp = txIp;
	}

	public EntidadeConsig getEntidadeConsig() {
		return this.entidadeConsig;
	}

	public void setEntidadeConsig(EntidadeConsig TEntidadeConsig) {
		this.entidadeConsig = TEntidadeConsig;
	}

}