package br.ccasj.sisauc.relatorio.domain;

import java.util.HashMap;
import java.util.Map;

import br.ccasj.sisauc.administracao.formatter.ItemARFormatter;
import br.ccasj.sisauc.administracao.sistema.domain.ConfiguracaoSistema;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.LoteRessarcimento;

public class RelatorioLoteRessarcimento extends Relatorio {

	private LoteRessarcimento loteRessarcimento;
	private ConfiguracaoSistema configuracaoSistema;
	private boolean descreverEspecificacoes = false;

	public RelatorioLoteRessarcimento(LoteRessarcimento loteRessarcimento, ConfiguracaoSistema configuracaoSistema, boolean descreverEspecificacoes) {
		super();
		this.loteRessarcimento = loteRessarcimento;
		this.configuracaoSistema = configuracaoSistema;
		this.descreverEspecificacoes = descreverEspecificacoes;
	}

	@Override
	public String getTemplate() {
		return "loteRessarcimento.html";
	}

	@Override
	public Map<String, Object> getMapa() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("loteRessarcimento", loteRessarcimento);
		mapa.put("configuracao", configuracaoSistema);
		mapa.put("descreverEspecificacoes", descreverEspecificacoes);
		mapa.put("itens", new ItemARFormatter().getListaItensOrdenado(loteRessarcimento
				.getItensAr()));
		mapa.put("nomeRelatorioPaginacao", getNomeRelatorioPaginacao());
		return mapa;
	}

	@Override
	public String getNomeRelatorioPaginacao() {
		return "LoteRessarcimento";
	}
	
	@Override
	public String getFilename() {
		return loteRessarcimento.getNumero();
	}

	public LoteRessarcimento getLoteRessarcimento() {
		return loteRessarcimento;
	}

	public void setLoteRessarcimento(LoteRessarcimento loteRessarcimento) {
		this.loteRessarcimento = loteRessarcimento;
	}

	public ConfiguracaoSistema getConfiguracaoSistema() {
		return configuracaoSistema;
	}

	public void setConfiguracaoSistema(ConfiguracaoSistema configuracaoSistema) {
		this.configuracaoSistema = configuracaoSistema;
	}

	public boolean isDescreverEspecificacoes() {
		return descreverEspecificacoes;
	}

	public void setDescreverEspecificacoes(boolean descreverEspecificacoes) {
		this.descreverEspecificacoes = descreverEspecificacoes;
	}

}
