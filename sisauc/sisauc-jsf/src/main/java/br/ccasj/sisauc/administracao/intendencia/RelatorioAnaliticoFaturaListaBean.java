package br.ccasj.sisauc.administracao.intendencia;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.auditoriaretrospectiva.dao.GerenciarLoteDAO;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.Lote;
import br.ccasj.sisauc.emissaogab.dao.ItemGABDAO;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.intendencia.domain.ParametrosRelatorioAnaliticoFatura;
import br.ccasj.sisauc.intendencia.domain.service.GerarRelatorioAnaliticoFaturaService;
import br.ccasj.sisauc.relatorio.GeradorRelatorio;
import br.ccasj.sisauc.relatorio.domain.RelatorioAnaliticoFaturas;

@Scope(value = "view")
@Service(value = "relatorioAnaliticoFaturaListaBean")
public class RelatorioAnaliticoFaturaListaBean implements Serializable {

	private static final long serialVersionUID = 2768364952350699166L;
	
	@Autowired
	private GerenciarLoteDAO loteDao;
	@Autowired
	private ItemGABDAO itemGABDAO;
	@Autowired
	private GerarRelatorioAnaliticoFaturaService gerarRelatorioAnaliticoFaturaService;
	@Autowired
	private GeradorRelatorio geradorRelatorio;

	private List<Lote> lotes;
	private Lote lote;
	private String periodo;
	private ParametrosRelatorioAnaliticoFatura parametros;
	private List<ItemGAB> itensGAB = new ArrayList<ItemGAB>();

	@PostConstruct
	private void init() {
		lotes = loteDao.listarLotesVigentes();
	}
	
	public void selecionarLote(Integer id){
		lote = gerarRelatorioAnaliticoFaturaService.obterLoteParaRelatorioAnaliticoFatura(id);
		parametros = gerarRelatorioAnaliticoFaturaService.obterValoresTotais(lote);
		periodo = calcularPeriodoGab(id);
		geradorRelatorio.gerar(new RelatorioAnaliticoFaturas(lote, periodo, parametros), true);
	}
	
	public String calcularPeriodoGab(Integer id) {
		itensGAB = itemGABDAO.obterItensGabParaRelatorioAnaliticoFatura(id);
		Date dataAnterior = itensGAB.get(0).getGab().getDataEmissao();
		Date dataPosterior = itensGAB.get(0).getGab().getDataEmissao();
		for (ItemGAB itemGAB : itensGAB) {
			if(itemGAB.getGab().getDataEmissao().before(dataAnterior)){
				dataAnterior = itemGAB.getGab().getDataEmissao();
			}
		}
		for (ItemGAB itemGAB : itensGAB) {
			if(itemGAB.getGab().getDataEmissao().after(dataPosterior)){
				dataPosterior = itemGAB.getGab().getDataEmissao();
			}
		}
		SimpleDateFormat anterior = new SimpleDateFormat("dd/MM/yy");
		SimpleDateFormat posterior = new SimpleDateFormat("dd/MM/yy");
		String resultado = anterior.format(dataAnterior) + " a " + posterior.format(dataPosterior);
		return resultado;
	}

	public List<Lote> getLotes() {
		return lotes;
	}

	public void setLotes(List<Lote> lotes) {
		this.lotes = lotes;
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
