package br.ccasj.sisauc.emissaoar.service;

import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;

public interface GerarARService {
	
	public AutorizacaoRessarcimento salvar(AutorizacaoRessarcimento ar);
	public AutorizacaoRessarcimento gerarAPartirAuditoriaProspectiva(Integer idAuditoriaProspectiva);

}
