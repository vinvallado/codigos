package br.mil.fab.consigext.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="T_HISTORICO")
@SequenceGenerator(name="SQ_ID_HISTORICO",sequenceName="SQ_ID_HISTORICO",allocationSize=1)
public class Historico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="SQ_ID_HISTORICO",strategy=GenerationType.SEQUENCE)
	@Column(name="ID_HISTORICO")
	private long id;

	@Column(name="DS_TABELA")
	private String dsTabela;
	
	@Column(name="DS_COLUNA")
	private String dsColuna;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_ALTERACAO")
	private Date dtAlteracao;

	@Column(name="NR_AUTOR")
	private String nrCpfAutor;

	@Column(name="VL_COLUNA")
	private String vlColuna;

	@Column(name="ID_TABELA")
	private long idLinhaAlterada;

	public Historico() {
	}
	

	public Historico(String dsTabela, Date dtAlteracao, String nrCpfAutor, String vlColuna, long idLinhaAlterada, String dsColuna) {
		this.dsTabela = dsTabela;
		this.dtAlteracao = dtAlteracao;
		this.nrCpfAutor = nrCpfAutor;
		this.vlColuna = vlColuna;
		this.idLinhaAlterada = idLinhaAlterada;
		this.dsColuna = dsColuna;

	}

	public String getDsColuna() {
		return dsColuna;
	}


	public void setDsColuna(String dsColuna) {
		this.dsColuna = dsColuna;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDsTabela() {
		return dsTabela;
	}

	public void setDsTabela(String dsTabela) {
		this.dsTabela = dsTabela;
	}

	public Date getDtAlteracao() {
		return dtAlteracao;
	}

	public void setDtAlteracao(Date dtAlteracao) {
		this.dtAlteracao = dtAlteracao;
	}

	public String getNrCpfAutor() {
		return nrCpfAutor;
	}

	public void setNrCpfAutor(String nrCpfAutor) {
		this.nrCpfAutor = nrCpfAutor;
	}

	public String getVlColuna() {
		return vlColuna;
	}

	public void setVlColuna(String vlColuna) {
		this.vlColuna = vlColuna;
	}

	public long getIdLinhaAlterada() {
		return idLinhaAlterada;
	}

	public void setIdLinhaAlterada(long idLinhaAlterada) {
		this.idLinhaAlterada = idLinhaAlterada;
	}


}