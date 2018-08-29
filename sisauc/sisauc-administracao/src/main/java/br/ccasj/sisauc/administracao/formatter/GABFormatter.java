package br.ccasj.sisauc.administracao.formatter;

import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;

public class GABFormatter {

	public double getValorTotal(GuiaApresentacaoBeneficiario gab) {
		double totalGAB = 0;
		for (ItemGAB item : gab.getItens()) {
			totalGAB += item.getValorTotal();
		}
		return totalGAB;
	}
		
}
