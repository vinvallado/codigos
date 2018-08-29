package br.ccasj.sisauc.administracao.auditoriaretrospectiva.ressarcimento.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.administracao.formatter.ItemARFormatter;
import br.ccasj.sisauc.administracao.sistema.dao.ConfiguracaoSistemaDao;
import br.ccasj.sisauc.administracao.sistema.domain.ConfiguracaoSistema;
import br.ccasj.sisauc.auditoriaretrospectiva.dao.GerenciarLoteRessarcimentoDAO;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.LoteRessarcimento;
import br.ccasj.sisauc.auditoriaretrospectiva.service.GerenciarLoteRessarcimentoService;
import br.ccasj.sisauc.emissaoar.dao.ItemARDAO;
import br.ccasj.sisauc.emissaoar.domain.ItemAR;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;
import br.ccasj.sisauc.relatorio.GeradorRelatorio;
import br.ccasj.sisauc.relatorio.domain.RelatorioLoteRessarcimento;

@Scope(value = "view")
@Service(value = "gerenciarLotesRessarcimentoFormularioBean")
public class GerenciarLotesRessarcimentoFormularioBean implements Serializable {

	private static final long serialVersionUID = -5901721622505460269L;
	
	@Autowired
	private GerenciarLoteRessarcimentoDAO gerenciarLoteRessarcimentoDao;
	@Autowired
	private GerenciarLoteRessarcimentoService gerenciarLoteRessarcimentoService;
	@Autowired
	private ItemARDAO itemArDao;
	@Autowired
	private ConfiguracaoSistemaDao configuracaoSistemaDao;
	@Autowired
	private GeradorRelatorio geradorRelatorio;

	private LoteRessarcimento loteRessarcimento;
	private ConfiguracaoSistema configuracaoSistema;
	private Beneficiario beneficiario;
	private List<ItemAR> itensAr;
	private List<ItemAR> itensArSelecionados;
	private List<Beneficiario> beneficiarios;
	private ItemARFormatter formatter = new ItemARFormatter();
	private boolean descreverEspecificacoes = false;

	@PostConstruct
	private void init() {
		loteRessarcimento = obterLote();
		itensAr = itemArDao.obterItensAr();
		beneficiarios = listaBeneficiariosRessarcir();

	}

	private List<Beneficiario> listaBeneficiariosRessarcir() {
		List<Beneficiario> beneficiarios = new ArrayList<Beneficiario>();
		for(ItemAR itemAr: this.itensAr){
			if(itemAr.getAr().getBeneficiario().getBeneficiarioTitular()!=null && !beneficiarios.contains(itemAr.getAr().getBeneficiario().getBeneficiarioTitular())){
				beneficiarios.add(itemAr.getAr().getBeneficiario().getBeneficiarioTitular());
			}else if(itemAr.getAr().getBeneficiario().getBeneficiarioTitular()==null  && !beneficiarios.contains(itemAr.getAr().getBeneficiario())){
				beneficiarios.add(itemAr.getAr().getBeneficiario());
			}
		}
		return beneficiarios;
	}

	public void imprimir(){
		configuracaoSistema = configuracaoSistemaDao.obterConfiguracaoSistema();
		geradorRelatorio.gerar(new RelatorioLoteRessarcimento(loteRessarcimento, configuracaoSistema, descreverEspecificacoes));
	}

	private LoteRessarcimento obterLote() {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		return "novo".equals(id) ? new LoteRessarcimento() : gerenciarLoteRessarcimentoDao.obterLoteRessarcimentoComItensById(Integer.valueOf(id));
	}

	public void atualizarItensArPorBeneficiario() {
		if(beneficiario == null){
			init();
		}else{
			itensAr = itemArDao.obterItensArRealizadosPorBeneficiario(beneficiario.getSaram());
			itensArSelecionados = new ArrayList<ItemAR>();
		}
	}
	
	public void validarGeracaoLoteRessarcimento(){
		if(itensArSelecionados == null || itensArSelecionados.isEmpty() ){
			throw new SystemRuntimeException("É necessário selecionar algum item para Gerar um Lote de Ressarcimento.");
		} else{
			ManagedBeanUtils.showDialog("gerarLoteRessarcimentoDialog");
		}
	}

	public void salvar() {
		loteRessarcimento.setBeneficiario(beneficiario);
		loteRessarcimento.setItensAr(new HashSet<ItemAR>(itensArSelecionados));
		gerenciarLoteRessarcimentoService.salvar(loteRessarcimento);
		Mensagem.informacao("Lote Nº " + loteRessarcimento.getNumero() + " criado com sucesso!");
		ManagedBeanUtils.redirecionar("/auditoria-retrospectiva/gerenciar-lotes-ressarcimento");
	}

	public List<ItemAR> obterListaItensParaImpressao(){
		return formatter.getListaItensOrdenado(loteRessarcimento.getItensAr());
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

	
	public Beneficiario getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}

	public List<ItemAR> getItensAr() {
		return itensAr;
	}

	public void setItensAr(List<ItemAR> itensAr) {
		this.itensAr = itensAr;
	}

	public List<ItemAR> getItensArSelecionados() {
		return itensArSelecionados;
	}

	public void setItensArSelecionados(List<ItemAR> itensArSelecionados) {
		this.itensArSelecionados = itensArSelecionados;
	}

	public List<Beneficiario> getBeneficiarios() {
		return beneficiarios;
	}

	public void setBeneficiarios(List<Beneficiario> beneficiarios) {
		this.beneficiarios = beneficiarios;
	}

	public double getValorTotalParcial() {
		double valorTotalParcial = 0.0;
		if (itensArSelecionados != null && !itensArSelecionados.isEmpty()) {
			for (ItemAR item : itensArSelecionados) {
				valorTotalParcial += item.getAuditoriaRetrospectiva().getValorRessarcimento();
			}
		}
		return valorTotalParcial;
	}

	public boolean isDescreverEspecificacoes() {
		return descreverEspecificacoes;
	}

	public void setDescreverEspecificacoes(boolean descreverEspecificacoes) {
		this.descreverEspecificacoes = descreverEspecificacoes;
	}

}
