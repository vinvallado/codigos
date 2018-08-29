package br.mil.fab.boletim.api.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Embeddable
public class ComponenteGeralPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	


	@ManyToOne
	@JoinColumn(name="ID_COMPONENTE_MOTIVO")
	private ComponenteMotivo compMotivo;


	@ManyToOne
	@JoinColumn(name="ID_LANCAMENTO")
	private MotivoUtilizado motivo;

	public ComponenteGeralPK() {
	}

	public ComponenteMotivo getCompMotivo() {
		return compMotivo;
	}
	
	public void setCompMotivo(ComponenteMotivo compMotivo) {
		this.compMotivo = compMotivo;
	}
	@JsonIgnore
	public MotivoUtilizado getMotivo() {
		return motivo;
	}
	@JsonIgnore
	public void setMotivo(MotivoUtilizado motivo) {
		this.motivo = motivo;
	}
	
}