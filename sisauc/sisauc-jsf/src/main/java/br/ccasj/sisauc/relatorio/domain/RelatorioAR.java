package br.ccasj.sisauc.relatorio.domain;

import java.util.HashMap;
import java.util.Map;

import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.framework.utils.managedbean.FormatterBean;

public class RelatorioAR extends Relatorio {
	
	private AutorizacaoRessarcimento ar;
	
	private boolean arReimpressa;
	
	public RelatorioAR(AutorizacaoRessarcimento ar, boolean arReimpressa) {
		super();
		this.ar = ar;
		this.arReimpressa = arReimpressa;
	}

	@Override
	public String getTemplate() {
		return "ar.html";
	}
	
	@Override
	public String getFilename() {
		return ar.getCodigo();
	}

	@Override
	public Map<String, Object> getMapa() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("ar", ar);
		mapa.put("formatter", new FormatterBean());
		mapa.put("nomeRelatorioPaginacao", getNomeRelatorioPaginacao());
		mapa.put("arReimpressa", arReimpressa);
		return mapa;
	}
	
	@Override
	public String getNomeRelatorioPaginacao() {
		return "ARE";
	}

}
