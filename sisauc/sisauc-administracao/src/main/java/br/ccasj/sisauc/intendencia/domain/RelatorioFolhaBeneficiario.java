package br.ccasj.sisauc.intendencia.domain;

import java.util.List;
import java.util.Set;

public interface RelatorioFolhaBeneficiario {

	public boolean isDebito();	
	
	public Integer getId();
	
	public Set<RelatorioFolhaBeneficiarioItem> getItensEnvio();
}
