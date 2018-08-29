package br.ccasj.sisauc.administracao.auditoriaretrospectiva.managedbean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.SolicitacaoProcedimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoProcedimento;
import br.ccasj.sisauc.auditoriaprospectiva.dao.AuditoriaProspectivaDAO;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectiva;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.Lote;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.MetadadoValorAuditoriaRetrospectiva;
import br.ccasj.sisauc.emissaogab.dao.GuiaApresentacaoBeneficiarioDAO;
import br.ccasj.sisauc.emissaogab.dao.ItemGABDAO;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;
import br.ccasj.sisauc.intendencia.dao.DescontoBeneficiarioDAO;
import br.ccasj.sisauc.intendencia.dao.RelatorioDescontoBeneficiarioDAO;
import br.ccasj.sisauc.intendencia.domain.DescontoBeneficiario;

@Scope(value = "view")
@Service(value = "buscaGabFormularioBean")
public class BuscaGabFormularioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private AuditoriaProspectivaDAO auditoriaProspectivaDAO;
	@Autowired
	private SolicitacaoProcedimentoDAO solicitacaoProcedimentoDAO;
	@Autowired
	private GuiaApresentacaoBeneficiarioDAO guiaApresentacaoBeneficiarioDAO;
	@Autowired
	private ItemGABDAO itemGABDAO;
	@Autowired
	private DescontoBeneficiarioDAO descontoBeneficiarioDAO;
	@Autowired
	private RelatorioDescontoBeneficiarioDAO relatorioDescontoBeneficiarioDAO;
	
	private AuditoriaProspectiva auditoriaProspectiva;
	private SolicitacaoProcedimento solicitacaoProcedimento;
	private GuiaApresentacaoBeneficiario gab;
	private ItemGAB itemGAB = new ItemGAB();
	private Lote lote;
	private DescontoBeneficiario descontoBeneficiario;
	private String codigoRelatorioDescontoBeneficiario;
	private Set<MetadadoValorAuditoriaRetrospectiva> valoresAuditoriaFiltrados;

	@PostConstruct
	public void init() {
		gab = obterGab();
		solicitacaoProcedimento = solicitacaoProcedimentoDAO.abrirComPedidos(gab.getAuditoriaProspectiva().getSolicitacao().getId());
		auditoriaProspectiva = auditoriaProspectivaDAO.obterAuditoriaPorIdComProcedimentosESolicitacao(gab.getAuditoriaProspectiva().getId());
	}
	
	public void selecionarItemGAB(ItemGAB item){
		itemGAB = itemGABDAO.obterItemGABComMetadados(item.getId());
		lote = itemGABDAO.obterLotePorItemGAB(itemGAB);
		descontoBeneficiario = descontoBeneficiarioDAO.obterDescontoPorItemGAB(itemGAB);
		codigoRelatorioDescontoBeneficiario = relatorioDescontoBeneficiarioDAO.obterCodigoPorItemGAB(itemGAB);
		valoresAuditoriaFiltrados = getValoresAuditoriaFiltrado();
		ManagedBeanUtils.showDialog("dialogInformacoesItemGAB");
	}
	
	private GuiaApresentacaoBeneficiario obterGab(){
		String id = ManagedBeanUtils.obterParametroRequest("id");
		GuiaApresentacaoBeneficiario guia = new GuiaApresentacaoBeneficiario();
		guia = guiaApresentacaoBeneficiarioDAO.obterComItensPorId(Integer.valueOf(id));
		return guia;
	}
	
	public Set<MetadadoValorAuditoriaRetrospectiva> getValoresAuditoriaFiltrado() {
		Set<MetadadoValorAuditoriaRetrospectiva> valores = new HashSet<MetadadoValorAuditoriaRetrospectiva>();
		if(itemGAB.getAuditoriaRetrospectiva() == null){
			return valores;
		}
		for (MetadadoValorAuditoriaRetrospectiva metadado: itemGAB.getAuditoriaRetrospectiva().getValores()){
			if (!StringUtils.isEmpty(metadado.getDescricao())){
				valores.add(metadado);
			}
		}
		return valores;
	}	

	public AuditoriaProspectiva getAuditoriaProspectiva() {
		return auditoriaProspectiva;
	}

	public void setAuditoriaProspectiva(AuditoriaProspectiva auditoriaProspectiva) {
		this.auditoriaProspectiva = auditoriaProspectiva;
	}

	public SolicitacaoProcedimento getSolicitacaoProcedimento() {
		return solicitacaoProcedimento;
	}

	public void setSolicitacaoProcedimento(SolicitacaoProcedimento solicitacaoProcedimento) {
		this.solicitacaoProcedimento = solicitacaoProcedimento;
	}

	public GuiaApresentacaoBeneficiario getGab() {
		return gab;
	}

	public void setGab(GuiaApresentacaoBeneficiario gab) {
		this.gab = gab;
	}

	public ItemGAB getItemGAB() {
		return itemGAB;
	}

	public void setItemGAB(ItemGAB itemGAB) {
		this.itemGAB = itemGAB;
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public DescontoBeneficiario getDescontoBeneficiario() {
		return descontoBeneficiario;
	}

	public void setDescontoBeneficiario(DescontoBeneficiario descontoBeneficiario) {
		this.descontoBeneficiario = descontoBeneficiario;
	}

	public String getCodigoRelatorioDescontoBeneficiario() {
		return codigoRelatorioDescontoBeneficiario;
	}

	public void setCodigoRelatorioDescontoBeneficiario(String codigoRelatorioDescontoBeneficiario) {
		this.codigoRelatorioDescontoBeneficiario = codigoRelatorioDescontoBeneficiario;
	}
	
	public Set<MetadadoValorAuditoriaRetrospectiva> getValoresAuditoriaFiltrados() {
		return valoresAuditoriaFiltrados;
	}

	public void setValoresAuditoriaFiltrados(Set<MetadadoValorAuditoriaRetrospectiva> valoresAuditoriaFiltrados) {
		this.valoresAuditoriaFiltrados = valoresAuditoriaFiltrados;
	}
}
