package br.ccasj.sisauc.intendencia.domain;

import java.io.Serializable;

public class ParametrosRelatorioAnaliticoFatura implements Serializable {

	private static final long serialVersionUID = 1665088986153266912L;

	private double somaValorFinal = 0;
	private double somaValorApresentado = 0;
	private double somaValorAuditado = 0;
	private double somaValorTotalItem = 0;
	private double somaValoresMetadados = 0;
	private double somaValoresDesconto = 0;

	public double getSomaValorFinal() {
		return somaValorFinal;
	}

	public ParametrosRelatorioAnaliticoFatura(double somaValorFinal, double somaValorApresentado, 
			double somaValorAuditado, double somaValorTotalItem, double somaValoresMetadados, double somaValoresDesconto) {
		super();
		this.somaValorFinal = somaValorFinal;
		this.somaValorApresentado = somaValorApresentado;
		this.somaValorAuditado = somaValorAuditado;
		this.somaValorTotalItem = somaValorTotalItem;
		this.somaValoresMetadados = somaValoresMetadados;
		this.somaValoresDesconto = somaValoresDesconto;
	}

	public void setSomaValorFinal(double somaValorFinal) {
		this.somaValorFinal = somaValorFinal;
	}

	public double getSomaValorApresentado() {
		return somaValorApresentado;
	}

	public void setSomaValorApresentado(double somaValorApresentado) {
		this.somaValorApresentado = somaValorApresentado;
	}

	public double getSomaValorAuditado() {
		return somaValorAuditado;
	}

	public void setSomaValorAuditado(double somaValorAuditado) {
		this.somaValorAuditado = somaValorAuditado;
	}

	public double getSomaValorTotalItem() {
		return somaValorTotalItem;
	}

	public void setSomaValorTotalItem(double somaValorTotalItem) {
		this.somaValorTotalItem = somaValorTotalItem;
	}

	public double getSomaValoresMetadados() {
		return somaValoresMetadados;
	}

	public void setSomaValoresMetadados(double somaValoresMetadados) {
		this.somaValoresMetadados = somaValoresMetadados;
	}

	public double getSomaValoresDesconto() {
		return somaValoresDesconto;
	}

	public void setSomaValoresDesconto(double somaValoresDesconto) {
		this.somaValoresDesconto = somaValoresDesconto;
	}


}
