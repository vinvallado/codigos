package br.ccasj.sisauc.administracao.intendencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.auditoriaretrospectiva.domain.ParametrosRelatorioDescontoBeneficiariosPesquisa;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiario;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiarioItem;
import br.ccasj.sisauc.intendencia.domain.service.DescontoBeneficiariosService;
import br.ccasj.sisauc.intendencia.domain.service.GerarRelatorioDescontoBeneficiarioService;

@Scope(value = "view")
@Service(value = "relatorioDescontoBeneficiarioFormBean")
public class RelatorioDescontoBeneficiarioFormBean implements Serializable {

	private static final long serialVersionUID = -7604632839043689695L;

	@Autowired
	private DescontoBeneficiariosService descontoBeneficiariosService;
	@Autowired
	private GerarRelatorioDescontoBeneficiarioService gerarRelatorioDescontoBeneficiarioService;
	
	private ParametrosRelatorioDescontoBeneficiariosPesquisa parametros = new ParametrosRelatorioDescontoBeneficiariosPesquisa();
	private List<ItemGAB> itens = new ArrayList<ItemGAB>();
	private List<ItemGAB> itensSelecionados = new ArrayList<ItemGAB>();
	
	public void pesquisar(){
		itens = descontoBeneficiariosService.pesquisa(parametros);
	}
	
	public void validarGeracaoRelatorio(){
		if (itensSelecionados.isEmpty()){
			Mensagem.erro("Selecione ao menos um item para gerar o relatório.");
		} else {
			ManagedBeanUtils.showDialog("descontoBeneficiariosDialog");
		}
	}
	
	public void gerarRelatorio(){
		RelatorioDescontoBeneficiario relatorio = gerarRelatorioDescontoBeneficiarioService.gerarRelatorio(itensSelecionados);
		Mensagem.informacao("Relatório de descontos criado com sucesso.");
		ManagedBeanUtils.redirecionar("/desconto-beneficiario/relatorio-desconto-beneficiario/" + relatorio.getId());
	}
	
	public ParametrosRelatorioDescontoBeneficiariosPesquisa getParametros() {
		return parametros;
	}

	public void setParametros(ParametrosRelatorioDescontoBeneficiariosPesquisa parametros) {
		this.parametros = parametros;
	}

	public List<ItemGAB> getItensSelecionados() {
		return itensSelecionados;
	}

	public void setItensSelecionados(List<ItemGAB> itensSelecionados) {
		this.itensSelecionados = itensSelecionados;
	}

	public List<ItemGAB> getItens() {
		return itens;
	}

	public void setItens(List<ItemGAB> itens) {
		this.itens = itens;
	}
	
}
