package br.ccasj.sisauc.sdga.service;

import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.sdga.domain.CancelamentoAuditoriaRetrospectiva;

public interface CancelarAuditoriaRetrospectivaService {

	public void cancelarAuditoriaRetrospectiva(CancelamentoAuditoriaRetrospectiva cancelamento, ItemGAB itemGAB);
	public void verificarSeAuditoriaPodeSerCanceladaItemGAB(ItemGAB itemGAB);
}
