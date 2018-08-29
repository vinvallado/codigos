package br.ccasj.sisauc.relatorio.domain;

import java.util.HashMap;
import java.util.Map;

import br.ccasj.sisauc.administracao.formatter.ItemGABFormatter;
import br.ccasj.sisauc.administracao.sistema.domain.ConfiguracaoSistema;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.Lote;

public class RelatorioLote extends Relatorio {

	private Lote lote;
	private ConfiguracaoSistema configuracaoSistema;

	public RelatorioLote(Lote lote, ConfiguracaoSistema configuracaoSistema) {
		super();
		this.lote = lote;
		this.configuracaoSistema = configuracaoSistema;
	}

	@Override
	public String getTemplate() {
		return "lote.html";
	}

	@Override
	public Map<String, Object> getMapa() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("lote", lote);
		mapa.put("configuracao", configuracaoSistema);
		mapa.put("itens", new ItemGABFormatter().getListaItensOrdenado(lote
				.getItensGab()));
		mapa.put("nomeRelatorioPaginacao", getNomeRelatorioPaginacao());
		return mapa;
	}

	@Override
	public String getNomeRelatorioPaginacao() {
		return "Lote";
	}
	
	@Override
	public String getFilename() {
		return lote.getNumero();
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public ConfiguracaoSistema getConfiguracaoSistema() {
		return configuracaoSistema;
	}

	public void setConfiguracaoSistema(ConfiguracaoSistema configuracaoSistema) {
		this.configuracaoSistema = configuracaoSistema;
	}

}
