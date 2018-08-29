package br.ccasj.sisauc.emissaogab.dao;

import java.util.List;
import java.util.Set;

import br.ccasj.sisauc.auditoriaretrospectiva.domain.Lote;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.ParametrosRelatorioDescontoBeneficiariosPesquisa;
import br.ccasj.sisauc.emissaogab.domain.EstadoItemGAB;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiarioItem;

public interface ItemGABDAO extends GenericEntityDAO<ItemGAB>  {

	public List<ItemGAB> obterItensPorGAB(Integer idGab);
	public List<ItemGAB> obterItensGABParaAuditoriaRetrospectiva();
	public List<ItemGAB> obterItensGABParaCancelamentoAuditoriaRetrospectiva();
	public List<ItemGAB> obterItensGabConformesPorCredenciado(Integer idCredenciado);
	public List<ItemGAB> obterItensGabConformes();
	public ItemGAB obterItemGABComMetadados(Integer idItemGAB);
	public List<ItemGAB> obterItensGabNaoConformes();
	public List<ItemGAB> obterItensGabNaoConformesPorCredenciado(Integer idCredenciado);
	public List<ItemGAB> obterItensGabParaDesconto(ParametrosRelatorioDescontoBeneficiariosPesquisa parametros);
	public List<ItemGAB> obterItensGabParaRelatorioAnaliticoFatura(Integer idLote);
	public Lote obterLotePorItemGAB(ItemGAB itemGab);
	public List<String> obterCodigoItensQuePossuemLote(Set<ItemGAB> itensGab);
	public void atualizarEstadoItens(List<ItemGAB> itens, EstadoItemGAB estado);
}
