package br.mil.fab.consigext.entity;

import java.math.BigDecimal;

public class Processamento {
	private String nrAde;
	private String cdOrg;
	private BigDecimal vlParcela;
	private String cdAnoMesFim;
	private String nrOrdem;
	private String nmPessoa;
	private String tpPessoa;
	private String cdPostoPgm;
	private String cdCaixaAcantus;
	private String nrIndice;


	public Processamento(String cdOrg,String tpPessoa, String nrOrdem,String cdPostoPgm, String cdCaixaAcantus,
			BigDecimal vlParcela, String cdAnoMesFim, 	String nmPessoa,String nrAde, String nrIndice) {
		this.nrAde = nrAde;
		this.cdOrg = cdOrg;
		this.vlParcela = vlParcela;
		this.cdAnoMesFim = cdAnoMesFim;
		this.nrOrdem = nrOrdem;
		this.nmPessoa = nmPessoa;
		this.tpPessoa = tpPessoa;
		this.cdPostoPgm = cdPostoPgm;
		this.cdCaixaAcantus = cdCaixaAcantus;
		this.nrIndice = nrIndice;
	}

	public Processamento() {
		super();
	}

	public void setCdOrg(String cdOrg) {
		this.cdOrg = cdOrg;
	}

	public String getCdOrg() {
		return cdOrg;
	}

	public void setNrOrdem(String nrOrdem) {
		this.nrOrdem = nrOrdem;
	}

	public String getNrOrdem() {
		return nrOrdem;
	}

	public void setNmPessoa(String nmPessoa) {
		this.nmPessoa = nmPessoa;
	}

	public String getNmPessoa() {
		return nmPessoa;
	}

	public void setTpPessoa(String tpPessoa) {
		this.tpPessoa = tpPessoa;
	}

	public String getTpPessoa() {
		switch (tpPessoa) {
		case "RR":
			return "3";
		case "RF":
			return "4";
		case "PM":
			return "5";
		case "AT":
			return "1";
		}
		return "1";
	}

	public void setCdCaixaAcantus(String cdCaixaAcantus) {
		this.cdCaixaAcantus = cdCaixaAcantus;
	}

	public String getCdCaixaAcantus() {
		return cdCaixaAcantus;
	}

	public String getNrAde() {
		return nrAde;
	}

	public void setNrAde(String nrAde) {
		this.nrAde = nrAde;
	}

	public BigDecimal getVlParcela() {
		return vlParcela;
	}

	public void setVlParcela(BigDecimal vlParcela) {
		this.vlParcela = vlParcela;
	}

	public String getCdAnoMesFim() {
		return cdAnoMesFim;
	}

	public void setCdAnoMesFim(String cdAnoMesFim) {
		this.cdAnoMesFim = cdAnoMesFim;
	}

	public String getCdPostoPgm() {
		return cdPostoPgm;
	}

	public void setCdPostoPgm(String cdPostoPgm) {
		this.cdPostoPgm = cdPostoPgm;
	}

	public String getNrIndice() {
		return nrIndice;
	}

	public void setNrIndice(String nrIndice) {
		this.nrIndice = nrIndice;
	}


}