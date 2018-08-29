package br.ccasj.sisauc.auditoriaretrospectiva.service;

import br.ccasj.sisauc.auditoriaretrospectiva.domain.AuditoriaRetrospectiva;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;

public interface AuditoriaRetrospectivaService {
	
	AuditoriaRetrospectiva salvar(AuditoriaRetrospectiva auditoriaRetrospectiva, ItemGAB itemGAB);
	
	public AuditoriaRetrospectiva salvarSemFinalizar(AuditoriaRetrospectiva auditoriaRetrospectiva, ItemGAB itemGAB);
	
	public AuditoriaRetrospectiva finalizarAuditoriaConforme(AuditoriaRetrospectiva auditoriaRetrospectiva, ItemGAB itemGab);

	public AuditoriaRetrospectiva finalizarAuditoriaNaoConforme(AuditoriaRetrospectiva auditoriaRetrospectiva, ItemGAB itemGAB);

} 

