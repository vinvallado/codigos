package br.ccasj.sisauc.administracao.auditoriaretrospectiva.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.CredenciadoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Credenciado;
import br.ccasj.sisauc.administracao.formatter.ItemGABFormatter;
import br.ccasj.sisauc.administracao.sistema.dao.ConfiguracaoSistemaDao;
import br.ccasj.sisauc.administracao.sistema.domain.ConfiguracaoSistema;
import br.ccasj.sisauc.auditoriaretrospectiva.dao.GerenciarLoteDAO;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.Lote;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.NotaFiscal;
import br.ccasj.sisauc.auditoriaretrospectiva.service.GerenciarLoteService;
import br.ccasj.sisauc.emissaogab.dao.ItemGABDAO;
import br.ccasj.sisauc.emissaogab.domain.EstadoItemGAB;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;
import br.ccasj.sisauc.relatorio.GeradorRelatorio;
import br.ccasj.sisauc.relatorio.domain.RelatorioLote;

@Scope(value = "view")
@Service(value = "gerenciarLotesFormularioBean")
public class GerenciarLotesFormularioBean implements Serializable {

	private static final long serialVersionUID = 2768364952350699166L;

	@Autowired
	private GerenciarLoteDAO gerenciarLoteDao;
	@Autowired
	private GerenciarLoteService gerenciarLoteService;
	@Autowired
	private ItemGABDAO itemGabDao;
	@Autowired
	private CredenciadoDAO credenciadoDao;
	@Autowired
	private ConfiguracaoSistemaDao configuracaoSistemaDao;
	@Autowired
	private GeradorRelatorio geradorRelatorio;

	private Lote lote;
	private ConfiguracaoSistema configuracaoSistema;
	private Credenciado credenciado;
	private List<ItemGAB> itensGab;
	private List<ItemGAB> itensGabSelecionados;
	private List<Credenciado> credenciados;
	private ItemGABFormatter formatter = new ItemGABFormatter();

	@PostConstruct
	private void init() {
		lote = obterLote();
		credenciados = credenciadoDao.findAll();
		itensGab = itemGabDao.obterItensGabConformes();
		verificarNotaFiscal();
	}

	public void imprimir(){
		configuracaoSistema = configuracaoSistemaDao.obterConfiguracaoSistema();
		geradorRelatorio.gerar(new RelatorioLote(lote, configuracaoSistema));
	}
	
	private void verificarNotaFiscal() {
		if (lote.getNotaFiscal() == null) {
			NotaFiscal notaFiscal = new NotaFiscal();
			lote.setNotaFiscal(notaFiscal);
		}
	}

	private Lote obterLote() {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		return "novo".equals(id) ? new Lote() : gerenciarLoteDao.obterLoteComItensById(Integer.valueOf(id));
	}

	public void atualizarItensGabPorCredenciado() {
		if(credenciado == null){
			init();
		}else{
			itensGab = itemGabDao.obterItensGabConformesPorCredenciado(credenciado.getId());
			itensGabSelecionados = new ArrayList<ItemGAB>();
		}
	}
	
	public void validarGeracaoLote(){
		if(itensGabSelecionados == null || itensGabSelecionados.isEmpty() ){
			throw new SystemRuntimeException("É necessário selecionar algum item para Gerar um Lote.");
		} else{
			ManagedBeanUtils.showDialog("gerarLoteDialog");
		}
	}

	public void salvar() {
		lote.setCredenciado(credenciado);
		lote.setItensGab(new HashSet<ItemGAB>(itensGabSelecionados));
		gerenciarLoteService.salvar(lote, true);
		Mensagem.informacao("Lote Nº " + lote.getNumero() + " criado com sucesso!");
		ManagedBeanUtils.redirecionar("/auditoria-retrospectiva/gerenciar-lotes");
	}

	public void informarNotaFiscalLote() {
		gerenciarLoteService.informarNotaFiscalLote(lote);
		Mensagem.informacao("Nota Fiscal salva com sucesso!");
		ManagedBeanUtils.redirecionar("/auditoria-retrospectiva/apresentar-nota-fiscal/" + lote.getId());
	}
	
	public void validarAltercaoNotaFiscal(){
		int itensEnviadosAuditoria = 0;
		for(ItemGAB itemGAB : lote.getItensGab()){
			while(itemGAB.getEstadoItemGAB().equals(EstadoItemGAB.ENVIADO_PARA_DESCONTO)){
				itensEnviadosAuditoria++;
				break;
			}
		}
		if(itensEnviadosAuditoria >= 1){
			throw new SystemRuntimeException("Este Lote possui um ou mais itens que já foram enviados para desconto.");
		}else{
			ManagedBeanUtils.showDialog("informarNotaFiscalDialog");
		}
	}

	public List<ItemGAB> obterListaItensParaImpressao(){
		return formatter.getListaItensOrdenado(lote.getItensGab());
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

	public Credenciado getCredenciado() {
		return credenciado;
	}

	public void setCredenciado(Credenciado credenciado) {
		this.credenciado = credenciado;
	}

	public List<ItemGAB> getItensGab() {
		return itensGab;
	}

	public void setItensGab(List<ItemGAB> itensGab) {
		this.itensGab = itensGab;
	}

	public List<ItemGAB> getItensGabSelecionados() {
		return itensGabSelecionados;
	}

	public void setItensGabSelecionados(List<ItemGAB> itensGabSelecionados) {
		this.itensGabSelecionados = itensGabSelecionados;
	}

	public List<Credenciado> getCredenciados() {
		return credenciados;
	}

	public void setCredenciados(List<Credenciado> credenciados) {
		this.credenciados = credenciados;
	}

	public double getValorTotalParcial() {
		double valorTotalParcial = 0.0;
		if (itensGabSelecionados != null && !itensGabSelecionados.isEmpty()) {
			for (ItemGAB item : itensGabSelecionados) {
				valorTotalParcial += item.getAuditoriaRetrospectiva().getValorFinal();
			}
		}
		return valorTotalParcial;
	}

}
