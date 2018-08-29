package br.mil.fab.consigext.entity;

import javax.persistence.Embeddable;

import java.io.Serializable;

import javax.persistence.Column;
@Embeddable
public class KeyOfCfgBasicaPgm implements Serializable{
	@Column(name = "CD_ANO_MES", nullable = false)
    private String cdAnomes;

    @Column(name = "NR_VERSAO", nullable = false)
    private int version;
    
    @Column(name = "CD_MOEDA", nullable = false)
    private int cdMoeda;
    
	public int getCdMoeda() {
		return cdMoeda;
	}

	public void setCdMoeda(int cdMoeda) {
		this.cdMoeda = cdMoeda;
	}

	public String getCdAnomes() {
		return cdAnomes;
	}

	public void setCdAnomes(String cdAnomes) {
		this.cdAnomes = cdAnomes;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
