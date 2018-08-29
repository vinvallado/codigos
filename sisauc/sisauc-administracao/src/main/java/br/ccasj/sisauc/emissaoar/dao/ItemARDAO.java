package br.ccasj.sisauc.emissaoar.dao;

import java.util.List;
import java.util.Set;

import br.ccasj.sisauc.auditoriaretrospectiva.domain.LoteRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.ItemAR;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

public interface ItemARDAO extends GenericEntityDAO<ItemAR>  {

	public List<ItemAR> obterItensAr();
	public List<ItemAR> obterItensArRealizadosPorBeneficiario(String saram);
	public List<String> obterCodigoItensQuePossuemLoteRessarcimento(Set<ItemAR> itensAr);
	public ItemAR obterItemARE(Integer idItemARE);
	public LoteRessarcimento obterLotePorItemARE(ItemAR itemAre);
}
