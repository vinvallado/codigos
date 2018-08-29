package br.ccasj.sisauc.relatorio.domain;

import java.util.HashMap;
import java.util.Map;

import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.framework.utils.managedbean.FormatterBean;

public class RelatorioGAB extends Relatorio {
	
	private GuiaApresentacaoBeneficiario gab;
	
	private boolean gabReimpressa;
	
	public RelatorioGAB(GuiaApresentacaoBeneficiario gab, boolean gabReimpressa) {
		super();
		this.gab = gab;
		this.gabReimpressa = gabReimpressa;
	}

	@Override
	public String getTemplate() {
		return "gab.html";
	}
	
	@Override
	public String getFilename() {
		return gab.getCodigo();
	}

	@Override
	public Map<String, Object> getMapa() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("gab", gab);
		mapa.put("formatter", new FormatterBean());
		mapa.put("nomeRelatorioPaginacao", getNomeRelatorioPaginacao());
		mapa.put("gabReimpressa", gabReimpressa);
		return mapa;
	}

	@Override
	public String getNomeRelatorioPaginacao() {
		return "GAB";
	}
	
}
