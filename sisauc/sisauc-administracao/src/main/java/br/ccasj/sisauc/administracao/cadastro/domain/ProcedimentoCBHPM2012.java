package br.ccasj.sisauc.administracao.cadastro.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@DiscriminatorValue(value = "CBHPM2012")
@Table(name = "procedimento_cbhpm_2012", schema = EntidadeGenerica.SCHEMA_SISAUC)
public class ProcedimentoCBHPM2012 extends ProcedimentoBase {

	private static final long serialVersionUID = -8389049061142077888L;

	//código do sub grupo consuta na CBHPM
	//Serve como referencia no codigo fonte
	public static final String CODIGO_CONSULTA = "10101004";
	
	private Integer numeroAuxiliares;
	private Integer incidencias;
	private Double quantidadeFilme;
	private Double custoOperacional;
	private Porte porte;
	private PorteAnestesico porteAnestesico;
	private SubGrupoProcedimento subGrupo;
	private Double deflatorPorte;

	public ProcedimentoCBHPM2012() {
		super();
	}
	
	public ProcedimentoCBHPM2012(String codigo, String descricao) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public ProcedimentoCBHPM2012(Integer id, String codigo, String descricao) {
		super(id);
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public ProcedimentoCBHPM2012(Integer id, String codigo, String descricao, Integer idSubGrupo, String codigoSubGrupo, String descricaoSubGrupo, Integer idGrupo, String codigoGrupo, String descricaoGrupo) {
		super(id, codigo, descricao, Tabela.CBHPM2012);
		this.subGrupo = new SubGrupoProcedimento(idSubGrupo, codigoSubGrupo, descricaoSubGrupo);
		this.subGrupo.setGrupoProcedimento(new GrupoProcedimento(idGrupo, codigoGrupo, descricaoGrupo));
	}

	@Column(name = "numero_auxiliares")
	public Integer getNumeroAuxiliares() {
		return numeroAuxiliares;
	}

	public void setNumeroAuxiliares(Integer numeroAuxiliares) {
		this.numeroAuxiliares = numeroAuxiliares;
	}

	public Integer getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(Integer incidencias) {
		this.incidencias = incidencias;
	}

	@Column(name = "quantidade_filme")
	public Double getQuantidadeFilme() {
		return quantidadeFilme;
	}

	public void setQuantidadeFilme(Double quantidadeFilme) {
		this.quantidadeFilme = quantidadeFilme;
	}

	@Column(name = "custo_operacional")
	public Double getCustoOperacional() {
		return custoOperacional;
	}

	public void setCustoOperacional(Double custoOperacional) {
		this.custoOperacional = custoOperacional;
	}

	@ManyToOne
	@JoinColumn(name = "id_porte")
	public Porte getPorte() {
		return porte;
	}

	public void setPorte(Porte porte) {
		this.porte = porte;
	}

	@ManyToOne
	@JoinColumn(name = "id_porte_anestesico")
	public PorteAnestesico getPorteAnestesico() {
		return porteAnestesico;
	}

	public void setPorteAnestesico(PorteAnestesico porteAnestesico) {
		this.porteAnestesico = porteAnestesico;
	}

	@ManyToOne
	@JoinColumn(name = "id_subgrupo")
	public SubGrupoProcedimento getSubGrupo() {
		return subGrupo;
	}

	public void setSubGrupo(SubGrupoProcedimento subGrupo) {
		this.subGrupo = subGrupo;
	}

	@Column(name = "deflator_porte")
	public Double getDeflatorPorte() {
		return deflatorPorte;
	}

	public void setDeflatorPorte(Double deflatorPorte) {
		this.deflatorPorte = deflatorPorte;
	}
	
}
