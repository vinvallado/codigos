package br.ccasj.sisauc.administracao.cadastro.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@DiscriminatorValue(value = "CISSFA")
@Table(name = "procedimento_cissfa", schema = EntidadeGenerica.SCHEMA_SISAUC)
public class ProcedimentoCISSFA extends ProcedimentoBase {

	private static final long serialVersionUID = -867339176633880852L;

	private Double quantidadeFilme;
	private Double quantidadeUsm;
	private SubGrupoProcedimento subGrupo;
	
	public ProcedimentoCISSFA() {
		// TODO Auto-generated constructor stub
	}

	public ProcedimentoCISSFA(Integer id, String codigo, String descricao, Integer idSubGrupo, String codigoSubGrupo, String descricaoSubGrupo, Integer idGrupo, String codigoGrupo, String descricaoGrupo) {
		super(id, codigo, descricao, Tabela.CISSFA);
		this.subGrupo = new SubGrupoProcedimento(idSubGrupo, codigoSubGrupo, descricaoSubGrupo);
		this.subGrupo.setGrupoProcedimento(new GrupoProcedimento(idGrupo, codigoGrupo, descricaoGrupo));
	}


	@Column(name = "quantidade_filme")
	public Double getQuantidadeFilme() {
		return quantidadeFilme;
	}

	public void setQuantidadeFilme(Double quantidadeFilme) {
		this.quantidadeFilme = quantidadeFilme;
	}

	@Column(name = "quantidade_usm")
	public Double getQuantidadeUsm() {
		return quantidadeUsm;
	}

	public void setQuantidadeUsm(Double quantidadeUsm) {
		this.quantidadeUsm = quantidadeUsm;
	}

	@ManyToOne
	@JoinColumn(name = "id_subgrupo")
	public SubGrupoProcedimento getSubGrupo() {
		return subGrupo;
	}

	public void setSubGrupo(SubGrupoProcedimento subGrupo) {
		this.subGrupo = subGrupo;
	}

	
	
}
