package br.ccasj.sisauc.relatorio.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ccasj.sisauc.administracao.cadastro.domain.Credenciado;
import br.ccasj.sisauc.administracao.sistema.domain.ConfiguracaoSistema;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;

public class RelatorioNaoConformidade extends Relatorio {
	
	private Credenciado credenciado;
	private OrganizacaoMilitar om;
	private ConfiguracaoSistema configuracaoSistema;
	private List<ItemGAB> itensGabSelecionados;
	
	
	public RelatorioNaoConformidade(List<ItemGAB> itensGabSelecionados, Credenciado credenciado,
			OrganizacaoMilitar om, ConfiguracaoSistema configuracaoSistema) {
		super();
		this.itensGabSelecionados = new ArrayList<ItemGAB>();
		this.itensGabSelecionados.addAll(itensGabSelecionados);
		this.credenciado = credenciado;
		this.om = om;
		this.configuracaoSistema = new ConfiguracaoSistema();
		this.configuracaoSistema = configuracaoSistema;
	}

	@Override
	public String getTemplate() {
		return "naoConformidade.html";
	}

	@Override
	public Map<String, Object> getMapa() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("itensGabSelecionados", itensGabSelecionados);
		mapa.put("credenciado", credenciado);
		mapa.put("dataEmissao", new Date());
		mapa.put("om", om);
		mapa.put("configuracao", configuracaoSistema);
		mapa.put("nomeRelatorioPaginacao", getNomeRelatorioPaginacao());
		return mapa;
	}
	
	@Override
	public String getNomeRelatorioPaginacao() {
		return "Rel. de não conformidades - " + this.credenciado.getNomeFantasia();
	}
	

	@Override
	public String getFilename() {
		return "RNC " + credenciado.getNomeFantasia();
	}

	public Credenciado getCredenciado() {
		return credenciado;
	}

	public void setCredenciado(Credenciado credenciado) {
		this.credenciado = credenciado;
	}

	public OrganizacaoMilitar getOm() {
		return om;
	}

	public void setOm(OrganizacaoMilitar om) {
		this.om = om;
	}
	
	public ConfiguracaoSistema getConfiguracaoSistema() {
		return configuracaoSistema;
	}

	public void setConfiguracaoSistema(ConfiguracaoSistema configuracaoSistema) {
		this.configuracaoSistema = configuracaoSistema;
	}

	public List<ItemGAB> getItensGabSelecionados() {
		return itensGabSelecionados;
	}
	
	public void setItensGabSelecionados(List<ItemGAB> itensGabSelecionados) {
		this.itensGabSelecionados = itensGabSelecionados;
	}

}
