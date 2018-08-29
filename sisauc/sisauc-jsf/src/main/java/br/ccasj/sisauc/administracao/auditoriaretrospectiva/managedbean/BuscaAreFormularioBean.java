package br.ccasj.sisauc.administracao.auditoriaretrospectiva.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.SolicitacaoRessarcimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoRessarcimento;
import br.ccasj.sisauc.auditoriaprospectiva.dao.AuditoriaProspectivaRessarcimentoDAO;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectivaRessarcimento;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.LoteRessarcimento;
import br.ccasj.sisauc.emissaoar.dao.AutorizacaoRessarcimentoDAO;
import br.ccasj.sisauc.emissaoar.dao.ItemARDAO;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.EspecificacaoItemARE;
import br.ccasj.sisauc.emissaoar.domain.ItemAR;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;

@Scope(value = "view")
@Service(value = "buscaAreFormularioBean")
public class BuscaAreFormularioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private AuditoriaProspectivaRessarcimentoDAO auditoriaProspectivaDAO;
	@Autowired
	private SolicitacaoRessarcimentoDAO solicitacaoRessarcimentoDAO;
	@Autowired
	private AutorizacaoRessarcimentoDAO autorizacaoRessarcimentoDAO;
	@Autowired
	private ItemARDAO itemAREDAO;
	
	private AuditoriaProspectivaRessarcimento auditoriaProspectiva;
	private SolicitacaoRessarcimento solicitacaoRessarcimento;
	private AutorizacaoRessarcimento are;
	private ItemAR itemARE = new ItemAR();
	private List<EspecificacaoItemARE> especificacoes = new ArrayList<EspecificacaoItemARE>();
	private LoteRessarcimento lote;

	@PostConstruct
	public void init() {
		are = obterAre();
		solicitacaoRessarcimento = solicitacaoRessarcimentoDAO.abrirComPedidos(are.getAuditoriaProspectiva().getSolicitacao().getId());
		auditoriaProspectiva = auditoriaProspectivaDAO.obterAuditoriaPorIdComProcedimentosESolicitacao(are.getAuditoriaProspectiva().getId());
	}
	
	public void selecionarItemARE(ItemAR item){
		itemARE = itemAREDAO.obterItemARE(item.getId());
		if(itemARE.getAuditoriaRetrospectiva() != null){
			especificacoes = itemARE.getAuditoriaRetrospectiva().getEspecificacoes();
		}
		lote = itemAREDAO.obterLotePorItemARE(itemARE);
		ManagedBeanUtils.showDialog("dialogInformacoesItemARE");
	}
	
	private AutorizacaoRessarcimento obterAre(){
		String id = ManagedBeanUtils.obterParametroRequest("id");
		AutorizacaoRessarcimento are = new AutorizacaoRessarcimento();
		are = autorizacaoRessarcimentoDAO.obterComItensPorId(Integer.valueOf(id));
		return are;
	}
	
	public AuditoriaProspectivaRessarcimento getAuditoriaProspectiva() {
		return auditoriaProspectiva;
	}

	public void setAuditoriaProspectiva(AuditoriaProspectivaRessarcimento auditoriaProspectiva) {
		this.auditoriaProspectiva = auditoriaProspectiva;
	}

	public SolicitacaoRessarcimento getSolicitacaoRessarcimento() {
		return solicitacaoRessarcimento;
	}

	public void setSolicitacaoRessarcimento(SolicitacaoRessarcimento solicitacaoRessarcimento) {
		this.solicitacaoRessarcimento = solicitacaoRessarcimento;
	}

	public AutorizacaoRessarcimento getAre() {
		return are;
	}

	public void setAre(AutorizacaoRessarcimento are) {
		this.are = are;
	}

	public ItemAR getItemARE() {
		return itemARE;
	}

	public void setItemARE(ItemAR itemARE) {
		this.itemARE = itemARE;
	}

	public List<EspecificacaoItemARE> getEspecificacoes() {
		return especificacoes;
	}

	public void setEspecificacoes(List<EspecificacaoItemARE> especificacoes) {
		this.especificacoes = especificacoes;
	}

	public LoteRessarcimento getLote() {
		return lote;
	}

	public void setLote(LoteRessarcimento lote) {
		this.lote = lote;
	}

}
