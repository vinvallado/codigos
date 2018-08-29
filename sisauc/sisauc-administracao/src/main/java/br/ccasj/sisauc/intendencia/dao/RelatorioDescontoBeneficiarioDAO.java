package br.ccasj.sisauc.intendencia.dao;

import java.util.List;

import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;
import br.ccasj.sisauc.intendencia.domain.EstadoRelatorioFolhaBeneficiario;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiario;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiarioItem;

public interface RelatorioDescontoBeneficiarioDAO extends GenericEntityDAO<RelatorioDescontoBeneficiario> {

	public int obterNumeroDoUltimoRelatorioDoAno(Integer ano);

	public List<RelatorioDescontoBeneficiario> listarAtivos();
	
	public List<RelatorioDescontoBeneficiario> listarTodos();

	public String obterCodigoPorItemGAB(ItemGAB itemGAB);
	
	public void mudarEstadoRelatorio(RelatorioDescontoBeneficiario relatorio);
	
	public void atualizarEstadoRelatorioDescontoItem(RelatorioDescontoBeneficiarioItem relatorioItem);
	
	public List<Integer> obterIdsRelatoriosPelosEstadosDeEnvio(List<EstadoRelatorioFolhaBeneficiario> estadosDeEnvio);
	
	public void atualizarEnvioDesconto(RelatorioDescontoBeneficiario relatorio);
	
}
