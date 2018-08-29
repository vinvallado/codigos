package br.ccasj.sisauc.intendencia.domain;

public interface RelatorioFolhaBeneficiarioItem {

	/**
	 * Retorna um código com lei de formação única para cada item de 
	 * desconto (GAB) ou ressarcimento (ARE), para evitar duplicação
	 * de lançamento na folha de pagamentos.
	 * 
	 * @return "SISAUC-" + <Codigo Item GAB ou ARE> + "-" + <ID do item da GAB ou ARE>
	 */
	public String getCodigoExternoSisconsig();
	
	public double getValorFolhaBeneficiario();
	
}
