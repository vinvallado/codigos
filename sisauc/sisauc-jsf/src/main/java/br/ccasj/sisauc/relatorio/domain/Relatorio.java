package br.ccasj.sisauc.relatorio.domain;

import java.util.Map;

public abstract class Relatorio {

	public abstract String getTemplate();
	public abstract Map<String, Object> getMapa();
	public abstract String getFilename();
	public abstract String getNomeRelatorioPaginacao();

}
