package br.mil.fab.consigext.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the T_PARAMETRO database table.
 * 
 */
@Entity
@Table(name="T_ARQUIVO_ENTIDADE_CONSIG")
@SequenceGenerator(name="SQ_ID_ARQUIVO_ENTIDADE_CONSIG",sequenceName="SQ_ID_ARQUIVO_ENTIDADE_CONSIG",allocationSize=1)
@NamedQuery(name="ArquivoEntidadeConsig.findAll", query="SELECT p FROM ArquivoEntidadeConsig p")
public class ArquivoEntidadeConsig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="SQ_ID_ARQUIVO_ENTIDADE_CONSIG",strategy=GenerationType.SEQUENCE)
	@Column(name="ID_ARQUIVO_ENTIDADE_CONSIG")
	private long id;

	//bi-directional many-to-one association to ParametroSecao
	@ManyToOne
	@JoinColumn(name="ID_CARGA_ARQUIVO")
	@JsonIgnore
	private CargaArquivo cargaArquivo;
	
	//bi-directional many-to-one association to ParametroSecao
	@ManyToOne
	@JoinColumn(name="ID_ENTIDADE_CONSIG")
	@JsonIgnore
	private EntidadeConsig entidadeConsig;
		
	public ArquivoEntidadeConsig() {
	}
	
	public ArquivoEntidadeConsig(long id) {
		super();
		this.id = id;
	}
	public ArquivoEntidadeConsig(CargaArquivo cargaArquivo, EntidadeConsig entidadeConsig ) {
		this.cargaArquivo = cargaArquivo;
		this.entidadeConsig = entidadeConsig; 
	}
	public long getId() {
		return this.id;
	}

	public void setId(long idArquivoEntidadeConsig) {
		this.id = idArquivoEntidadeConsig;
	}
	public CargaArquivo getCargaArquivo() {
		return cargaArquivo;
	}

	public void setCargaArquivo(CargaArquivo cargaArquivo) {
		this.cargaArquivo = cargaArquivo;
	}

	public EntidadeConsig getEntidadeConsig() {
		return entidadeConsig;
	}

	public void setEntidadeConsig(EntidadeConsig entidadeConsig) {
		this.entidadeConsig = entidadeConsig;
	}

}