package br.mil.fab.consigext.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the T_PARAMETRO database table.
 * 
 */
@Entity
@Table(name="T_PARAMETRO")
@NamedQuery(name="Parametro.findAll", query="SELECT p FROM Parametro p")
public class Parametro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_PARAMETRO")
	private long id;

	@Column(name="DS_PARAMETRO")
	private String dsParametro;

	@Column(name="SG_PARAMETRO")
	private String sgParametro;
	
	@Column(name="ST_EXCLUIDO")
	private long stExcluido;

	//bi-directional many-to-one association to ParametroSecao
	@ManyToOne
	@JoinColumn(name="ID_PARAMETRO_SECAO")
	@JsonIgnore
	private ParametroSecao parametroSecao;

	//bi-directional many-to-one association to ParametroTipo
	@ManyToOne
	@JoinColumn(name="ID_PARAMETRO_TIPO")
	@JsonIgnore
	private ParametroTipo parametroTipo;

	//bi-directional many-to-one association to ParametroVisualizacao
	@ManyToOne
	@JoinColumn(name="ID_PARAMETRO_VISUALIZACAO")
	@JsonIgnore
	private ParametroVisualizacao parametroVisualizacao;

	//bi-directional many-to-one association to ParametroOpcao
	@OneToMany(mappedBy="parametro")
	@JsonIgnore
	private List<ParametroOpcao> parametroOpcaos;

	//bi-directional many-to-one association to ParametroServico
	@OneToMany(mappedBy="parametro")
	@JsonIgnore
	private List<ParametroServico> parametroServicos;

	//bi-directional many-to-one association to ParametroServicoConsig
	@OneToMany(mappedBy="parametro")
	@JsonIgnore
	private List<ParametroServicoConsig> parametroServicoConsigs;

	public Parametro() {
	}
	
	public Parametro(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long idParametro) {
		this.id = idParametro;
	}

	public String getDsParametro() {
		return this.dsParametro;
	}

	public void setDsParametro(String dsParametro) {
		this.dsParametro = dsParametro;
	}

	public String getSgParametro() {
		return this.sgParametro;
	}

	public void setSgParametro(String sgParametro) {
		this.sgParametro = sgParametro;
	}

	public ParametroSecao getParametroSecao() {
		return this.parametroSecao;
	}

	public void setParametroSecao(ParametroSecao TParametroSecao) {
		this.parametroSecao = TParametroSecao;
	}

	public ParametroTipo getParametroTipo() {
		return this.parametroTipo;
	}

	public void setParametroTipo(ParametroTipo TParametroTipo) {
		this.parametroTipo = TParametroTipo;
	}

	public ParametroVisualizacao getParametroVisualizacao() {
		return this.parametroVisualizacao;
	}

	public void setParametroVisualizacao(ParametroVisualizacao TParametroVisualizacao) {
		this.parametroVisualizacao = TParametroVisualizacao;
	}

	public List<ParametroOpcao> getParametroOpcaos() {
		return this.parametroOpcaos;
	}
	
	public void setParametroOpcaos(List<ParametroOpcao> TParametroOpcaos) {
		this.parametroOpcaos = TParametroOpcaos;
	}

	public ParametroOpcao addTParametroOpcao(ParametroOpcao TParametroOpcao) {
		getParametroOpcaos().add(TParametroOpcao);
		TParametroOpcao.setParametro(this);

		return TParametroOpcao;
	}

	public ParametroOpcao removeTParametroOpcao(ParametroOpcao TParametroOpcao) {
		getParametroOpcaos().remove(TParametroOpcao);
		TParametroOpcao.setParametro(null);

		return TParametroOpcao;
	}

	public List<ParametroServico> getParametroServicos() {
		return this.parametroServicos;
	}

	public void setParametroServicos(List<ParametroServico> TParametroServicos) {
		this.parametroServicos = TParametroServicos;
	}

	public ParametroServico addTParametroServico(ParametroServico TParametroServico) {
		getParametroServicos().add(TParametroServico);
		TParametroServico.setParametro(this);

		return TParametroServico;
	}

	public ParametroServico removeTParametroServico(ParametroServico TParametroServico) {
		getParametroServicos().remove(TParametroServico);
		TParametroServico.setParametro(null);

		return TParametroServico;
	}

	public List<ParametroServicoConsig> getParametroServicoConsigs() {
		return this.parametroServicoConsigs;
	}

	public void setParametroServicoConsigs(List<ParametroServicoConsig> TParametroServicoConsigs) {
		this.parametroServicoConsigs = TParametroServicoConsigs;
	}

	public ParametroServicoConsig addTParametroServicoConsig(ParametroServicoConsig TParametroServicoConsig) {
		getParametroServicoConsigs().add(TParametroServicoConsig);
		TParametroServicoConsig.setParametro(this);

		return TParametroServicoConsig;
	}

	public ParametroServicoConsig removeTParametroServicoConsig(ParametroServicoConsig TParametroServicoConsig) {
		getParametroServicoConsigs().remove(TParametroServicoConsig);
		TParametroServicoConsig.setParametro(null);

		return TParametroServicoConsig;
	}

	public long getStExcluido() {
		return stExcluido;
	}

	public void setStExcluido(long stExcluido) {
		this.stExcluido = stExcluido;
	}

}