package br.ccasj.sisauc.administracao.auditoriaretrospectiva.managedbean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.auditoriaprospectiva.service.ApresentarGABService;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.AuditoriaRetrospectiva;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.MetadadoValorAuditoriaRetrospectiva;
import br.ccasj.sisauc.emissaogab.dao.GuiaApresentacaoBeneficiarioDAO;
import br.ccasj.sisauc.emissaogab.dao.ItemGABDAO;
import br.ccasj.sisauc.emissaogab.domain.EstadoGAB;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.EntidadeGenericaUtils;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;

@Scope(value = "view")
@Service(value = "apresentacaoGabFormularioBean")
public class ApresentacaoGabFormularioBean implements Serializable {

	private static final long serialVersionUID = -1110267307732766040L;

	@Autowired
	private GuiaApresentacaoBeneficiarioDAO gabDAO;
	@Autowired
	private ItemGABDAO itemGABDAO;
	@Autowired
	private ApresentarGABService apresentarGABService;
	
	private GuiaApresentacaoBeneficiario gab;
	private ItemGAB itemSelecionado;
	private List<ItemGAB> itensGAB;
	private Map<String, MetadadoValorAuditoriaRetrospectiva> valoresMap;

	private Double valorTotalMetadados;
	
	@PostConstruct
	private void init(){
		carregarGAB();
		carregarItensGAB();
	}
	
	private void carregarGAB() {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		gab = gabDAO.findById(Integer.valueOf(id));
		if (gab.getEstado() != EstadoGAB.EMITIDA) {
			Mensagem.erro("Estado da GAB não fazer a apresentação!");
			ManagedBeanUtils.redirecionar("/auditoria-retrospectiva/apresentar-gab");
		}
	}

	private void carregarItensGAB() {
		itensGAB = itemGABDAO.obterItensPorGAB(gab.getId());
	}

	public void marcarItemRealizado(ItemGAB item){
		item.setAuditoriaRetrospectiva(new AuditoriaRetrospectiva(0.0, true, ""));
	}
	
	public void marcarItemNaoRealizado(ItemGAB item){
		item.setAuditoriaRetrospectiva(new AuditoriaRetrospectiva(0.0, false, ""));
	}
	
	public void carregarMetadadosEdicao(ItemGAB item){
		itemSelecionado = item;
		valoresMap = new HashMap<String, MetadadoValorAuditoriaRetrospectiva>();
		if(item.getAuditoriaRetrospectiva().getValores() == null || item.getAuditoriaRetrospectiva().getValores().isEmpty()){
			valoresMap.put("taxaSala", new MetadadoValorAuditoriaRetrospectiva(EntidadeGenericaUtils.gerarIdNegativo(), 0.0, "taxaSala", "Taxa Sala"));
			valoresMap.put("taxaCurativos", new MetadadoValorAuditoriaRetrospectiva(EntidadeGenericaUtils.gerarIdNegativo(), 0.0, "taxaCurativos", "Taxa Curativos"));
			valoresMap.put("taxaDiariaHospitalar", new MetadadoValorAuditoriaRetrospectiva(EntidadeGenericaUtils.gerarIdNegativo(), 0.0, "taxaDiariaHospitalar", "Taxa Diária Hospitalar"));
			valoresMap.put("taxaMaterialMedico", new MetadadoValorAuditoriaRetrospectiva(EntidadeGenericaUtils.gerarIdNegativo(), 0.0, "taxaMaterialMedico", "Taxa Material Médico"));
			valoresMap.put("taxaOutros", new MetadadoValorAuditoriaRetrospectiva(EntidadeGenericaUtils.gerarIdNegativo(), 0.0, "taxaOutros", ""));
		} else {
			for (MetadadoValorAuditoriaRetrospectiva metadado : item.getAuditoriaRetrospectiva().getValores()) {
				valoresMap.put(metadado.getChave(), metadado);
			}
		}
		ManagedBeanUtils.showDialog("entrarValoresExtraItemGabDialog");
	}
	
	public void salvarMetadadosEdicao() {
		itemSelecionado.getAuditoriaRetrospectiva().setValores(new HashSet<MetadadoValorAuditoriaRetrospectiva>());
		for (String key : valoresMap.keySet()) {
			itemSelecionado.getAuditoriaRetrospectiva().getValores().add(valoresMap.get(key));
		}
		valorTotalMetadados = valoresMap.get("taxaSala").getValor() + valoresMap.get("taxaCurativos").getValor() +
				valoresMap.get("taxaDiariaHospitalar").getValor() + valoresMap.get("taxaMaterialMedico").getValor() + valoresMap.get("taxaOutros").getValor();
		if (valorTotalMetadados > itemSelecionado.getAuditoriaRetrospectiva().getValorApresentado()) {
			throw new SystemRuntimeException("O valor total das taxas não deve ultrapassar o valor apresentado");
		}
		if((valoresMap.get("taxaOutros").getValor() != 0) &&
				(valoresMap.get("taxaOutros").getDescricao() == null || valoresMap.get("taxaOutros").getDescricao().isEmpty())){
			throw new SystemRuntimeException("É necessário inserir uma descrição para 'Outros'");
		}
		itensGAB.set(itensGAB.indexOf(itemSelecionado), itemSelecionado);
		ManagedBeanUtils.hideDialog("entrarValoresExtraItemGabDialog");
	}
	
	public void salvar(){
		if (valorTotalMetadados != null)
			if (valorTotalMetadados > itemSelecionado.getAuditoriaRetrospectiva().getValorApresentado()) {
				throw new SystemRuntimeException("O valor total das taxas não deve ultrapassar o valor apresentado");
			}
		apresentarGABService.apresentarGAB(gab, itensGAB);
		Mensagem.informacao("GAB " + this.gab.getCodigo() + " apresentada com sucesso!");
		ManagedBeanUtils.redirecionar("/auditoria-retrospectiva/apresentar-gab");
	}

	public GuiaApresentacaoBeneficiario getGab() {
		return gab;
	}

	public void setGab(GuiaApresentacaoBeneficiario gab) {
		this.gab = gab;
	}

	public ItemGAB getItemSelecionado() {
		return itemSelecionado;
	}

	public void setItemSelecionado(ItemGAB itemSelecionado) {
		this.itemSelecionado = itemSelecionado;
	}

	public List<ItemGAB> getItensGAB() {
		return itensGAB;
	}

	public void setItensGAB(List<ItemGAB> itensGAB) {
		this.itensGAB = itensGAB;
	}

	public Map<String, MetadadoValorAuditoriaRetrospectiva> getValoresMap() {
		return valoresMap;
	}

	public void setValoresMap(Map<String, MetadadoValorAuditoriaRetrospectiva> valoresMap) {
		this.valoresMap = valoresMap;
	}
	
}
