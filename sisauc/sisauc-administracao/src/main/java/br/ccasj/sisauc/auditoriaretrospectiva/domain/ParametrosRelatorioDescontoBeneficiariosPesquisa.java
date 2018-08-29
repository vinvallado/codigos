package br.ccasj.sisauc.auditoriaretrospectiva.domain;

import java.io.Serializable;
import java.util.Date;

public class ParametrosRelatorioDescontoBeneficiariosPesquisa implements Serializable {

	private static final long serialVersionUID = 1665088986153266912L;
	
	private Date dataInicial;
	private Date dataFinal; 
	private String lote;
	private String gab;

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getGab() {
		return gab;
	}

	public void setGab(String gab) {
		this.gab = gab;
	}

}
