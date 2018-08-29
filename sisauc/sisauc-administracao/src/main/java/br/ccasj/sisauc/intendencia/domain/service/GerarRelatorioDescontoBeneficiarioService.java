package br.ccasj.sisauc.intendencia.domain.service;

import java.util.List;

import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiario;

public interface GerarRelatorioDescontoBeneficiarioService {

	RelatorioDescontoBeneficiario gerarRelatorio(List<ItemGAB> itens);
}
