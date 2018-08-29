package br.ccasj.sisauc.relatorio.domain;

import java.util.HashMap;
import java.util.Map;

import br.ccasj.sisauc.administracao.formatter.ItemGABFormatter;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.Lote;
import br.ccasj.sisauc.intendencia.domain.ParametrosRelatorioAnaliticoFatura;

public class RelatorioAnaliticoFaturas extends Relatorio {

	private Lote lote;
	private String periodo;
	private ParametrosRelatorioAnaliticoFatura parametros;

	public RelatorioAnaliticoFaturas(Lote lote, String periodo, ParametrosRelatorioAnaliticoFatura parametros) {
		super();
		this.lote = lote;
		this.periodo = periodo;
		this.parametros = parametros;
	}

	@Override
	public String getTemplate() {
		return "relatorio-analitico-faturas.html";
	}

	@Override
	public Map<String, Object> getMapa() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("lote", lote);
		mapa.put("itens", new ItemGABFormatter().getListaItensOrdenado(lote.getItensGab()));
		mapa.put("periodo", periodo);
		mapa.put("parametros", parametros);
		mapa.put("nomeRelatorioPaginacao", getNomeRelatorioPaginacao());
		return mapa;
	}
	
	@Override
	public String getNomeRelatorioPaginacao() {
		return "Rel. Analitico de Faturas";
	}

	@Override
	public String getFilename() {
		return "RAF " + lote.getNumero();
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public ParametrosRelatorioAnaliticoFatura getParametros() {
		return parametros;
	}

	public void setParametros(ParametrosRelatorioAnaliticoFatura parametros) {
		this.parametros = parametros;
	}

}
