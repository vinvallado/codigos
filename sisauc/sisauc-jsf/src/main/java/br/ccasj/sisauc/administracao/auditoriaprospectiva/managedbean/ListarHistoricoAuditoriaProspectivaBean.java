package br.ccasj.sisauc.administracao.auditoriaprospectiva.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.SolicitacaoMedicaDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoMedica;
import br.ccasj.sisauc.auditoriaprospectiva.dao.AuditoriaProspectivaBaseDAO;
import br.ccasj.sisauc.auditoriaprospectiva.dao.AuditoriaProspectivaDAO;
import br.ccasj.sisauc.auditoriaprospectiva.dao.AuditoriaProspectivaRessarcimentoDAO;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectiva;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectivaBase;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectivaRessarcimento;
import br.ccasj.sisauc.emissaoar.dao.AutorizacaoRessarcimentoDAO;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaogab.dao.GuiaApresentacaoBeneficiarioDAO;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;

@Scope(value = "view")
@Service(value = "listarHistoricoAuditoriaProspectivaBean")
public class ListarHistoricoAuditoriaProspectivaBean implements Serializable {

	private static final long serialVersionUID = -221233103387856778L;

	@Autowired
	private AuditoriaProspectivaBaseDAO auditoriaProspectivaBaseDAO;
	@Autowired
	private AuditoriaProspectivaDAO auditoriaProspectivaDAO;
	@Autowired
	private AuditoriaProspectivaRessarcimentoDAO auditoriaProspectivaRessarcimentoDAO;
	@Autowired
	private SolicitacaoMedicaDAO<? extends SolicitacaoMedica> solicitacaoMedicaDAO;
	@Autowired
	private GuiaApresentacaoBeneficiarioDAO gabDAO;
	@Autowired
	private AutorizacaoRessarcimentoDAO autorizacaoRessarcimentoDAO;

	private Integer idBeneficiario;
	private AuditoriaProspectivaBase auditoriaSelecionada;
	private String procedimentoPesquisa;
	private List<AuditoriaProspectivaBase> auditorias = new ArrayList<AuditoriaProspectivaBase>();
	private List<SelectItem> tipos = new ArrayList<SelectItem>();
	private List<String> tiposSelecionados = new ArrayList<String>();
	private List<GuiaApresentacaoBeneficiario> gabs = new ArrayList<GuiaApresentacaoBeneficiario>();
	private List<AutorizacaoRessarcimento> ares = new ArrayList<AutorizacaoRessarcimento>();

	@PostConstruct
	private void init() {
		idBeneficiario = obterIdBeneficiario();
		auditorias = auditoriaProspectivaBaseDAO.listarAuditoriasPorBeneficiario(idBeneficiario, procedimentoPesquisa);
		carregarFiltros();
	}

	private void carregarFiltros() {
		tipos.add(new SelectItem(AuditoriaProspectiva.class.getName(), "GAB"));
		tipos.add(new SelectItem(AuditoriaProspectivaRessarcimento.class.getName(), "Ressarcimento"));
		tiposSelecionados.add(AuditoriaProspectiva.class.getName());
		tiposSelecionados.add(AuditoriaProspectivaRessarcimento.class.getName());
	}

	private Integer obterIdBeneficiario() {
		String idSolicitacao = ManagedBeanUtils.obterParametroRequest("id");
		return solicitacaoMedicaDAO.findById(Integer.parseInt(idSolicitacao)).getBeneficiario().getId();
	}
		
	public void selecionarAuditoria(Integer idAuditoria, String tipo) {
		gabs = new ArrayList<GuiaApresentacaoBeneficiario>();
		ares = new ArrayList<AutorizacaoRessarcimento>();
		auditoriaSelecionada = tipo.equals("AuditoriaProspectiva") ? auditoriaProspectivaDAO.obterAuditoriaPorIdComProcedimentosESolicitacao(idAuditoria) : auditoriaProspectivaRessarcimentoDAO.obterAuditoriaPorIdComProcedimentosESolicitacao(idAuditoria); 
		if(tipo.equals("AuditoriaProspectiva")){
			gabs = gabDAO.obterGABsPorAuditoriaProspectiva(idAuditoria);
		} else {
			ares = autorizacaoRessarcimentoDAO.obterARsPorAuditoriaProspectiva(idAuditoria);
		}
		ManagedBeanUtils.showDialog("detalhesAuditoriaDialog");
	}
	
	public void pesquisarHistorico() {
		auditorias = auditoriaProspectivaBaseDAO.listarAuditoriasPorBeneficiario(idBeneficiario, procedimentoPesquisa, obterTiposSelecionados());
	}

	@SuppressWarnings("rawtypes")
	private Class<?>[] obterTiposSelecionados() {
		try {
			Class[] types = new Class[tiposSelecionados.size()];
			for (int i = 0; i < tiposSelecionados.size(); i++) {
				types[i] = Class.forName(tiposSelecionados.get(i));
			}
			return types;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	public String getProcedimentoPesquisa() {
		return procedimentoPesquisa;
	}

	public void setProcedimentoPesquisa(String procedimentoPesquisa) {
		this.procedimentoPesquisa = procedimentoPesquisa;
	}

	public List<AuditoriaProspectivaBase> getAuditorias() {
		return auditorias;
	}

	public void setAuditorias(List<AuditoriaProspectivaBase> auditorias) {
		this.auditorias = auditorias;
	}

	public List<SelectItem> getTipos() {
		return tipos;
	}

	public void setTipos(List<SelectItem> tipos) {
		this.tipos = tipos;
	}

	public List<String> getTiposSelecionados() {
		return tiposSelecionados;
	}

	public void setTiposSelecionados(List<String> tiposSelecionados) {
		this.tiposSelecionados = tiposSelecionados;
	}

	/**
	 * @return the auditoriaSelecionada
	 */
	public AuditoriaProspectivaBase getAuditoriaSelecionada() {
		return auditoriaSelecionada;
	}

	/**
	 * @param auditoriaSelecionada the auditoriaSelecionada to set
	 */
	public void setAuditoriaSelecionada(AuditoriaProspectivaBase auditoriaSelecionada) {
		this.auditoriaSelecionada = auditoriaSelecionada;
	}

	/**
	 * @return the gabs
	 */
	public List<GuiaApresentacaoBeneficiario> getGabs() {
		return gabs;
	}

	/**
	 * @param gabs the gabs to set
	 */
	public void setGabs(List<GuiaApresentacaoBeneficiario> gabs) {
		this.gabs = gabs;
	}

	/**
	 * @return the ares
	 */
	public List<AutorizacaoRessarcimento> getAres() {
		return ares;
	}

	/**
	 * @param ares the ares to set
	 */
	public void setAres(List<AutorizacaoRessarcimento> ares) {
		this.ares = ares;
	}

}
