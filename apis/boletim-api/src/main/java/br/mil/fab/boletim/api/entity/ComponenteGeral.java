package br.mil.fab.boletim.api.entity;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the T_COMPONENTE_GERAL database table.
 * 
 */
@Entity
@Table(name="T_COMPONENTE_GERAL")
@NamedQuery(name="ComponenteGeral.findAll", query="SELECT c FROM ComponenteGeral c")
@AssociationOverrides({
	@AssociationOverride(name = "id.compMotivo",
		joinColumns = @JoinColumn(name = "ID_COMPONENTE_MOTIVO")),
	@AssociationOverride(name = "id.motivo",
		joinColumns = @JoinColumn(name = "ID_LANCAMENTO")) })
public class ComponenteGeral implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ComponenteGeralPK id = new ComponenteGeralPK();

	@Lob
	@Column(name="VL_LANCADO")
	private String vlLancado;



	public ComponenteGeral() {
		
	}

	public ComponenteGeralPK getId() {
		return this.id;
	}

	public void setId(ComponenteGeralPK id) {
		this.id = id;
	}

	public String getVlLancado() {
		return this.vlLancado;
	}

	public void setVlLancado(String vlLancado) {
		this.vlLancado = vlLancado;
	}
	
	public ComponenteMotivo getCompMotivo() {
		return getId().getCompMotivo();
	}

	public void setCompMotivo(ComponenteMotivo compMotivo) {
		 getId().setCompMotivo(compMotivo);
	}

	@JsonIgnore
	public MotivoUtilizado getMotivo() {
		return  getId().getMotivo();
	}
	@JsonIgnore
	public void setMotivo(MotivoUtilizado motivo) {
		getId().setMotivo(motivo); 
	}
	
	

}