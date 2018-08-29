package br.ccasj.sisauc.administracao.cadastro.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@DiscriminatorValue(value = "TRS")
@Table(name = "procedimento_trs", schema = EntidadeGenerica.SCHEMA_SISAUC)
public class ProcedimentoTRS extends ProcedimentoBase {

	private static final long serialVersionUID = -8877381256320465150L;

	private Double valorEnfermaria;
	private Double valorApartamento;
	private boolean internacao;
	private boolean odontologico;
	
	public ProcedimentoTRS() {
		super();
	}
	
	public ProcedimentoTRS(Integer id, String codigo, String descricao, Tabela tabela) {
		super(id, codigo, descricao, tabela);
	}

	public ProcedimentoTRS(Integer id, String codigo, String descricao, Double valorEnfermaria, Double valorApartamento, boolean internacao) {
		super(id, codigo, descricao, Tabela.TRS);
		this.valorEnfermaria = valorEnfermaria;
		this.valorApartamento = valorApartamento;
		this.internacao = internacao;
	}

	public ProcedimentoTRS(Integer id, String codigo, String descricao, Double valorEnfermaria, Double valorApartamento, boolean internacao, boolean odontologico) {
		super(id, codigo, descricao, Tabela.TRS);
		this.valorEnfermaria = valorEnfermaria;
		this.valorApartamento = valorApartamento;
		this.internacao = internacao;
		this.odontologico = odontologico;
	}

	@Column(name = "valor_enfermaria")
	public Double getValorEnfermaria() {
		return valorEnfermaria;
	}

	public void setValorEnfermaria(Double valorEnfermaria) {
		this.valorEnfermaria = valorEnfermaria;
	}

	@Column(name = "valor_apartamento")
	public Double getValorApartamento() {
		return valorApartamento;
	}

	public void setValorApartamento(Double valorApartamento) {
		this.valorApartamento = valorApartamento;
	}

	public boolean isInternacao() {
		return internacao;
	}

	public void setInternacao(boolean internacao) {
		this.internacao = internacao;
	}

	public boolean isOdontologico() {
		return odontologico;
	}

	public void setOdontologico(boolean odontologico) {
		this.odontologico = odontologico;
	}

}
