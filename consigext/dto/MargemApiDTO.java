package br.mil.fab.consigext.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MargemApiDTO {

    private String nrOrdem;
	private double valorMargem30;
    private double valorMargem70;
    private double valorMargem40;
    
    public String getNrOrdem() {
		return nrOrdem;
	}

	public void setNrOrdem(String nrOrdem) {
		this.nrOrdem = nrOrdem;
	}
    public double getValorMargem30() {
		return valorMargem30;
	}

	public void setValorMargem30(double valorMargem30) {
		this.valorMargem30 = valorMargem30;
	}

	public double getValorMargem70() {
		return valorMargem70;
	}

	public void setValorMargem70(double valorMargem70) {
		this.valorMargem70 = valorMargem70;
	}

	public double getValorMargem40() {
		return valorMargem40;
	}

	public void setValorMargem40(double valorMargem40) {
		this.valorMargem40 = valorMargem40;
	}

	public MargemApiDTO() {
    }

    @Override
    public String toString() {
        return "MargemApiDTO{" +
                "nrOrdem='" + nrOrdem + '\'' +
                ",valorMargem30='" + valorMargem30 + '\'' +
                ",valorMargem40='" + valorMargem40 + '\'' +
                ", valorMargem70='" + valorMargem70 + '\'' +
                '}';
    }
}
