package br.ccasj.sisauc.sdga.dao;

import java.util.List;

import br.ccasj.sisauc.framework.dao.GenericEntityDAO;
import br.ccasj.sisauc.sdga.domain.CancelamentoAuditoriaRetrospectivaARE;

public interface CancelamentoAuditoriaRetrospectivaAREDAO extends GenericEntityDAO<CancelamentoAuditoriaRetrospectivaARE> {

	public void deletePelosIdsAuditorias(List<Integer> idsAuditRestrospARE);
	
}
