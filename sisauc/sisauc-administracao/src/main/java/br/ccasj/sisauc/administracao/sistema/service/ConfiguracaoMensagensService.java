package br.ccasj.sisauc.administracao.sistema.service;

import java.util.List;

import br.ccasj.sisauc.auditoriaretrospectiva.domain.Motivo;

public interface ConfiguracaoMensagensService {
	
	public void salvar(List<Motivo> naoHabilitados, List<Motivo> habilitados);

}
