package br.ccasj.sisauc.intendencia.dao;

import java.util.List;

import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;
import br.ccasj.sisauc.intendencia.domain.DescontoBeneficiario;

public interface DescontoBeneficiarioDAO extends GenericEntityDAO<DescontoBeneficiario> {

	public int obterQuantidadeDescontosPorAnoEBeneficiario(Integer id, Integer ano);

	public List<DescontoBeneficiario> listarDescontos();
	
	public List<DescontoBeneficiario> obterDescontosNaoEnvidaos();

	public DescontoBeneficiario obterComEnviosPorId(Integer id);

	public DescontoBeneficiario obterCompletoPorId(Integer id);

	public DescontoBeneficiario obterDescontoPorItemGAB(ItemGAB itemGAB);

}
