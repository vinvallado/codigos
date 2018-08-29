package br.ccasj.sisauc.relatorio.domain;

import java.util.HashMap;
import java.util.Map;

import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;

public class RelatorioRequerimentoRessarcimento extends Relatorio {
	
	private AutorizacaoRessarcimento ar;
	private OrganizacaoMilitar organizacaoMilitar;
	private Beneficiario beneficiario;
	
	public RelatorioRequerimentoRessarcimento(AutorizacaoRessarcimento ar) {
		this(ar.getOrganizacaoMilitar(), ar.getBeneficiario());
		this.ar = ar;
	}
	
	public RelatorioRequerimentoRessarcimento(OrganizacaoMilitar organizacaoMilitar, Beneficiario beneficiario) {
		this.organizacaoMilitar = organizacaoMilitar;
		this.beneficiario = beneficiario;
	}

	@Override
	public String getTemplate() {
		return "requerimento-ressarcimento.html";
	}
	
	@Override
	public String getFilename() {
		return beneficiario.getNome().replace(" ", "_") + "-requerimento";
	}

	@Override
	public Map<String, Object> getMapa() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("ar", ar);
		mapa.put("organizacaoMilitar", organizacaoMilitar);
		mapa.put("beneficiario", beneficiario);
		mapa.put("nomeRelatorioPaginacao", getNomeRelatorioPaginacao());
		return mapa;
	}

	@Override
	public String getNomeRelatorioPaginacao() {
		return "Req. de Ressarcimento";
	}
	

}
