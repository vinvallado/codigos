package br.ccasj.sisauc.auditoriaprospectiva.service;

import java.util.List;

import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;

public interface ApresentarGABService {
	
	public void apresentarGAB(GuiaApresentacaoBeneficiario gab, List<ItemGAB> itens);

}
