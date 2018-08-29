package br.ccasj.sisauc.relatorio.domain;

import java.util.HashMap;
import java.util.Map;

import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiario;

public class RelatorioRelatorioDescontoBeneficiario extends Relatorio {

	private RelatorioDescontoBeneficiario relatorio;

	public RelatorioRelatorioDescontoBeneficiario(RelatorioDescontoBeneficiario relatorio) {
		super();
		this.relatorio = relatorio;
	}

	@Override
	public String getTemplate() {
		return "relatorio-desconto-beneficiario.html";
	}

	@Override
	public Map<String, Object> getMapa() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("relatorio", relatorio);
		return mapa;
	}

	@Override
	public String getNomeRelatorioPaginacao() {
		return "Relatório";
	}
	
	@Override
	public String getFilename() {
		return relatorio.getCodigo();
	}

	public RelatorioDescontoBeneficiario getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(RelatorioDescontoBeneficiario relatorio) {
		this.relatorio = relatorio;
	}

}
