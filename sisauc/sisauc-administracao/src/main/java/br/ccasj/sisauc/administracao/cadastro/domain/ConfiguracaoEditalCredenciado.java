package br.ccasj.sisauc.administracao.cadastro.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "configuracao_edital_credenciado", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class ConfiguracaoEditalCredenciado extends EntidadeGenerica {

	private static final long serialVersionUID = -4869826324295563209L;
	private static final String SEQ_NAME = "configuracao_edital_credenciado_id_seq";

	private boolean ativo = true;
	private Double indiceReajustePorte = 0.0;
	private Double indiceReajustePorteAnestesico = 0.0;
	private Double indiceReajusteCustoOperacional = 0.0;
	private Double indiceReajusteAuxiliares = 0.0;
	private Date dataInsercao;
	private Date dataExclusao;
	private EditalCredenciamento edital;
	private Credenciado credenciado;
	private Set<ConfiguracaoEditalCredenciadoProcedimento> procedimentos;

	public ConfiguracaoEditalCredenciado() {
		super();
	}

	public ConfiguracaoEditalCredenciado(EditalCredenciamento edital) {
		this();
		this.edital = edital;
	}

	public ConfiguracaoEditalCredenciado(Integer id, boolean ativo, String nomeFantasiaCredenciado, Double indiceReajustePorte, Double indiceReajustePorteAnestesico, Double indiceReajusteCustoOperacional, Double indiceReajusteAuxiliares) {
		super(id);
		this.ativo = ativo;
		this.credenciado = new Credenciado(nomeFantasiaCredenciado);
		this.indiceReajustePorte = indiceReajustePorte;
		this.indiceReajustePorteAnestesico = indiceReajustePorteAnestesico;
		this.indiceReajusteCustoOperacional = indiceReajusteCustoOperacional;
		this.indiceReajusteAuxiliares = indiceReajusteAuxiliares;
	}
	
	public ConfiguracaoEditalCredenciado(Integer id, boolean ativo, Integer idCredenciado,String nomeFantasiaCredenciado, Double indiceReajustePorte, Double indiceReajustePorteAnestesico, Double indiceReajusteCustoOperacional, Double indiceReajusteAuxiliares) {
		this(id,ativo,nomeFantasiaCredenciado,indiceReajustePorte,indiceReajustePorteAnestesico,indiceReajusteCustoOperacional,indiceReajusteAuxiliares);
		this.credenciado.setId(idCredenciado);
	}

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Column(name = "indice_reajuste_porte")
	public Double getIndiceReajustePorte() {
		return indiceReajustePorte;
	}

	public void setIndiceReajustePorte(Double indiceReajustePorte) {
		this.indiceReajustePorte = indiceReajustePorte;
	}

	@Column(name = "indice_reajuste_porte_anestesico")
	public Double getIndiceReajustePorteAnestesico() {
		return indiceReajustePorteAnestesico;
	}

	public void setIndiceReajustePorteAnestesico(Double indiceReajustePorteAnestesico) {
		this.indiceReajustePorteAnestesico = indiceReajustePorteAnestesico;
	}

	@Column(name = "indice_reajuste_custo_operacional")
	public Double getIndiceReajusteCustoOperacional() {
		return indiceReajusteCustoOperacional;
	}

	public void setIndiceReajusteCustoOperacional(Double indiceReajusteCustoOperacional) {
		this.indiceReajusteCustoOperacional = indiceReajusteCustoOperacional;
	}

	@Column(name = "indice_reajuste_auxiliares")
	public Double getIndiceReajusteAuxiliares() {
		return indiceReajusteAuxiliares;
	}

	public void setIndiceReajusteAuxiliares(Double indiceReajusteAuxiliares) {
		this.indiceReajusteAuxiliares = indiceReajusteAuxiliares;
	}

	@NotNull
	@Column(name = "data_insercao")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataInsercao() {
		return dataInsercao;
	}

	public void setDataInsercao(Date dataInsercao) {
		this.dataInsercao = dataInsercao;
	}

	@Column(name = "data_exclusao")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataExclusao() {
		return dataExclusao;
	}

	public void setDataExclusao(Date dataExclusao) {
		this.dataExclusao = dataExclusao;
	}

	@ManyToOne
	@JoinColumn(name = "id_edital_credenciamento")
	public EditalCredenciamento getEdital() {
		return edital;
	}

	public void setEdital(EditalCredenciamento edital) {
		this.edital = edital;
	}

	@ManyToOne
	@JoinColumn(name = "id_credenciado")
	public Credenciado getCredenciado() {
		return credenciado;
	}

	public void setCredenciado(Credenciado credenciado) {
		this.credenciado = credenciado;
	}

	@OneToMany(mappedBy = "configuracaoEditalCredenciado")
	public Set<ConfiguracaoEditalCredenciadoProcedimento> getProcedimentos() {
		return procedimentos;
	}

	public void setProcedimentos(Set<ConfiguracaoEditalCredenciadoProcedimento> procedimentos) {
		this.procedimentos = procedimentos;
	}

}
