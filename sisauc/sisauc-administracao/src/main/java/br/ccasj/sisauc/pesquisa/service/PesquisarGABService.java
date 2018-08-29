package br.ccasj.sisauc.pesquisa.service;

import java.util.List;

import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.pesquisa.domain.ParametrosPesquisaGAB;

public interface PesquisarGABService {
	
	public List<GuiaApresentacaoBeneficiario> pesquisar(ParametrosPesquisaGAB parametros);

}
