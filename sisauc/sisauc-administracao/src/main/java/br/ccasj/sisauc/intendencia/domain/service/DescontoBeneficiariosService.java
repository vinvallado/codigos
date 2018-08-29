package br.ccasj.sisauc.intendencia.domain.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import br.ccasj.sisauc.auditoriaretrospectiva.domain.ParametrosRelatorioDescontoBeneficiariosPesquisa;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiario;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiarioItem;


public interface DescontoBeneficiariosService {
	
	public List<ItemGAB> pesquisa(ParametrosRelatorioDescontoBeneficiariosPesquisa parametros);

	public void salvar(List<ItemGAB> itensSelecionados);

	public void verificarBeneficiarios(List<ItemGAB> itensSelecionados);
	
	public void verificarEnvioDescontos();

	public double calcularValorDesconto(double valorTotal);

	public InputStream obterStreamRelatorioXLS(RelatorioDescontoBeneficiario relatorio) throws IOException;
}
