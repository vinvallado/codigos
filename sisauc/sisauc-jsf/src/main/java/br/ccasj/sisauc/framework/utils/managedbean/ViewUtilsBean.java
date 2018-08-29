package br.ccasj.sisauc.framework.utils.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import br.ccasj.sisauc.administracao.cadastro.domain.Arquivo;
import br.ccasj.sisauc.administracao.cadastro.domain.EstadoSolicitacaoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.GrupoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoBase;
import br.ccasj.sisauc.administracao.cadastro.domain.SubGrupoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.Tabela;
import br.ccasj.sisauc.autenticacao.dao.UsuarioDAO;
import br.ccasj.sisauc.emissaoar.domain.EstadoAR;
import br.ccasj.sisauc.emissaogab.domain.EstadoGAB;
import br.ccasj.sisauc.emissaogab.domain.EstadoItemGAB;
import br.ccasj.sisauc.framework.context.AuthenticationContext;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.domain.Perfil;
import br.ccasj.sisauc.framework.domain.UsuarioUserDetails;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.intendencia.domain.EstadoRelatorioFolhaBeneficiario;

@Scope(value = "view")
@Controller(value = "viewUtilsBean")
public class ViewUtilsBean implements Serializable {

	private static final long serialVersionUID = -6416091191431084130L;

	@Autowired
	private UsuarioDAO usuarioDAO; 
	@Autowired
	private AuthenticationContext authenticationContext;
	
	private List<Object> filtrados;
	private List<Object> convertedList;
	private String tipoTabelaAutocomplete = "TRS";
	
	public List<GrupoProcedimento> procurarGrupos(String value){
		try {
			return ManagedBeanUtils.obter("applicationBean", ApplicationBean.class).procurarGrupos(value, Tabela.valueOf(tipoTabelaAutocomplete));
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<SubGrupoProcedimento> procurarSubgrupos(String value){
		try {
			return ManagedBeanUtils.obter("applicationBean", ApplicationBean.class).procurarSubgrupos(value, Tabela.valueOf(tipoTabelaAutocomplete));
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<ProcedimentoBase> procurarProcedimento(String value){
		try {
			return ManagedBeanUtils.obter("applicationBean", ApplicationBean.class).procurarProcedimentos(value, Tabela.valueOf(tipoTabelaAutocomplete));
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<ProcedimentoBase> procurarProcedimentoParaAtendente(String value){
		try {
			return ManagedBeanUtils.obter("applicationBean", ApplicationBean.class).procurarProcedimentosParaAtendente(value, Tabela.valueOf(tipoTabelaAutocomplete));
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<ProcedimentoBase> procurarProcedimentoCBHPM(String value){
		try {
			return ManagedBeanUtils.obter("applicationBean", ApplicationBean.class).procurarProcedimentos(value, Tabela.CBHPM2012);
		} catch (Exception e) {
			return null;
		}
	}

	public List<ProcedimentoBase> procurarProcedimentoCBHPMouCISSFA(String value){
		try {
			Map<String, ProcedimentoBase> mapaProcedimentos = new HashMap<String, ProcedimentoBase>();
			List<ProcedimentoBase> procedimentos = ManagedBeanUtils.obter("applicationBean", ApplicationBean.class)
					.procurarProcedimentos(value, Tabela.CBHPM2012, Tabela.CISSFA);
			for (ProcedimentoBase procedimento: procedimentos){
				if (!mapaProcedimentos.containsKey(procedimento.getCodigo())){
					mapaProcedimentos.put(procedimento.getCodigo(), procedimento);
				}
			}
			return new ArrayList<ProcedimentoBase>(mapaProcedimentos.values());
		} catch (Exception e) {
			return null;
		}
	}
	
	public String booleanLabel(boolean valor) {
		return valor ? "Sim" : "Não";
	}	
	
	public boolean filterIgnoreCase(String value, String filter, Locale locale){
			filter = filter.toLowerCase();
			value = value.toLowerCase();
			filter = filter.replaceAll("[áàâãä]", "a");
			value = value.replaceAll("[áàâãä]", "a");
			filter = filter.replaceAll("[éèêë]", "e");
			value = value.replaceAll("[éèêë]", "e");
			filter = filter.replaceAll("[íìîï]", "i");
			value = value.replaceAll("[íìîï]", "i");
			filter = filter.replaceAll("[óòôõö]", "o");
			value = value.replaceAll("[óòôõö]", "o");
			filter = filter.replaceAll("[úùûü]", "u");
			value = value.replaceAll("[úùûü]", "u");
		return value.toLowerCase().contains(filter.toLowerCase());
	}
	
	public boolean filterValor(String value, String filter, Locale locale){
		if (filter.contains(","))
			value = value.replace(".", ",");
		if (filter.contains("."))
			filter = filter.replace(".", "");
		return value.toLowerCase().contains(filter.toLowerCase());
	}
	
	public boolean filterCnpjCpf(String value, String filter, Locale locale){
		filter = filter.replace(".", "");
			value = value.replace(".", "");
		filter = filter.replace("-", "");
			value = value.replace("-", "");
		filter = filter.replace("/", "");
			value = value.replace("/", "");
		return value.toLowerCase().contains(filter.toLowerCase());
	}

	public SelectItem[] divisoesOptions(){
		SelectItem[] opcoes = new SelectItem[3];
		opcoes[0] = new SelectItem(null, "");
		opcoes[1] = new SelectItem(Divisao.DIVISAO_MEDICA, "Méd.");
		opcoes[2] = new SelectItem(Divisao.DIVISAO_ODONTOLOGICA, "Odont.");
		return opcoes;
	}
	
	
	public SelectItem[] perfisOptions(){
		SelectItem[] opcoes = new SelectItem[8];
		opcoes[0] = new SelectItem(null, "");
		opcoes[1] = new SelectItem(Perfil.PERFIL_ADMINISTRADOR_SISTEMA, "Administrador do Sistema");
		opcoes[2] = new SelectItem(Perfil.PERFIL_ATENDENTE_FUNSA, "Atendente FUNSA");
		opcoes[3] = new SelectItem(Perfil.PERFIL_AUDITOR_FINANCEIRO, "Auditor Retrospectivo");
		opcoes[4] = new SelectItem(Perfil.PERFIL_AUDITOR_FUNSA, "Auditor Prospectivo");
		opcoes[5] = new SelectItem(Perfil.PERFIL_DIRETOR, "Diretor");
		opcoes[6] = new SelectItem(Perfil.PERFIL_CHEFE_FUNSA, "Chefe FUNSA");
		opcoes[7] = new SelectItem(Perfil.PERFIL_INTENDENCIA, "Intendência");
		return opcoes;
	}
	
	public SelectItem[] estadosSolicitacaoOptions(){
		SelectItem[] opcoes = new SelectItem[3];
		opcoes[0] = new SelectItem(null, "");
		opcoes[1] = new SelectItem(EstadoSolicitacaoProcedimento.CRIADA, EstadoSolicitacaoProcedimento.CRIADA.getLabel());
		opcoes[2] = new SelectItem(EstadoSolicitacaoProcedimento.INCONSISTENTE, EstadoSolicitacaoProcedimento.INCONSISTENTE.getLabel());
		return opcoes;
	}
	
	public SelectItem[] estadoGabOptions(){
		SelectItem[] opcoes = new SelectItem[7];
		opcoes[0] = new SelectItem(null, "");
		opcoes[1] = new SelectItem(EstadoGAB.GERADA, EstadoGAB.GERADA.getLabel());
		opcoes[2] = new SelectItem(EstadoGAB.EMITIDA, EstadoGAB.EMITIDA.getLabel());
		opcoes[3] = new SelectItem(EstadoGAB.CANCELADA, EstadoGAB.CANCELADA.getLabel());
		opcoes[4] = new SelectItem(EstadoGAB.APRESENTADA, EstadoGAB.APRESENTADA.getLabel());
		opcoes[5] = new SelectItem(EstadoGAB.EM_AUDITORIA, EstadoGAB.EM_AUDITORIA.getLabel());
		opcoes[6] = new SelectItem(EstadoGAB.AUDITADA, EstadoGAB.AUDITADA.getLabel());
		
		return opcoes;
	}	
	
	public SelectItem[] estadoArOptions(){
		SelectItem[] opcoes = new SelectItem[8];
		opcoes[0] = new SelectItem(null, "");
		opcoes[1] = new SelectItem(EstadoAR.GERADA, EstadoAR.GERADA.getLabel());
		opcoes[2] = new SelectItem(EstadoAR.EMITIDA, EstadoAR.EMITIDA.getLabel());
		opcoes[3] = new SelectItem(EstadoAR.CANCELADA, EstadoAR.CANCELADA.getLabel());
		opcoes[4] = new SelectItem(EstadoAR.APRESENTADA, EstadoAR.APRESENTADA.getLabel());
		opcoes[5] = new SelectItem(EstadoAR.EM_AUDITORIA, EstadoAR.EM_AUDITORIA.getLabel());
		opcoes[6] = new SelectItem(EstadoAR.AUDITADA, EstadoAR.AUDITADA.getLabel());
		opcoes[7] = new SelectItem(EstadoAR.INCONSISTENTE, EstadoAR.INCONSISTENTE.getLabel());
		
		return opcoes;
	}		
	
	public SelectItem[] estadoItemGabAuditoriaRetrospectivaOptions(){
		SelectItem[] opcoes = new SelectItem[4];
		opcoes[0] = new SelectItem(null, "");
		opcoes[1] = new SelectItem(EstadoItemGAB.AGUARDANDO_AUDITORIA, EstadoItemGAB.AGUARDANDO_AUDITORIA.getLabel());
		opcoes[2] = new SelectItem(EstadoItemGAB.AUDITORIA_INICIADA, EstadoItemGAB.AUDITORIA_INICIADA.getLabel());
		opcoes[3] = new SelectItem(EstadoItemGAB.NAO_CONFORME, EstadoItemGAB.NAO_CONFORME.getLabel());
		return opcoes;
	}	
	
	public SelectItem[] estadoDescontoBeneficiarioOptions(){
		SelectItem[] opcoes = new SelectItem[4];
		opcoes[0] = new SelectItem(null, "");
		opcoes[1] = new SelectItem(EstadoRelatorioFolhaBeneficiario.NAO_ENVIADO, EstadoRelatorioFolhaBeneficiario.NAO_ENVIADO.getLabel());
		opcoes[3] = new SelectItem(EstadoRelatorioFolhaBeneficiario.INICIADO, EstadoRelatorioFolhaBeneficiario.INICIADO.getLabel());
		opcoes[4] = new SelectItem(EstadoRelatorioFolhaBeneficiario.INTERROMPIDO_ERRO, EstadoRelatorioFolhaBeneficiario.INTERROMPIDO_ERRO.getLabel());
		opcoes[5] = new SelectItem(EstadoRelatorioFolhaBeneficiario.ENVIADO, EstadoRelatorioFolhaBeneficiario.ENVIADO.getLabel());
		
		return opcoes;
	}	
	
	public SelectItem[] booleanOptions(){
		SelectItem[] opcoes = new SelectItem[3];
		opcoes[0] = new SelectItem(null, "");
		opcoes[1] = new SelectItem("Sim", "Sim");
		opcoes[2] = new SelectItem("Não", "Não");
		return opcoes;
	}
	
	public SelectItem[] convenioOptions(){
		SelectItem[] opcoes = new SelectItem[3];
		opcoes[0] = new SelectItem(null, "");
		opcoes[1] = new SelectItem("AMH", "AMH");
		opcoes[2] = new SelectItem("AMHC", "AMHC");
		return opcoes;
	}	
	
	public List<Object> convertSetToList(Set<Object> set){
		if(set!= null && !set.isEmpty() ){
			convertedList = new ArrayList<Object>(set);
		} 
		return convertedList;
	}
	
	public Arquivo subirArquivo(FileUploadEvent event) {
		try {
			Arquivo arquivo = new Arquivo();
			arquivo.setStream(event.getFile().getInputstream());
			arquivo.setNome(event.getFile().getFileName());
			arquivo.setTamanho(event.getFile().getInputstream().available());
			return arquivo;
		} catch (IOException e) {
			throw new SystemRuntimeException("Falha ao subir arquivo: " + e.getLocalizedMessage());
		}
	}
	
	public void atualizarLeuNotasVersao(){
		if(!authenticationContext.getUsuarioLogado().isLeuNotasVersao()){
			((UsuarioUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsuario().setLeuNotasVersao(true);;
			usuarioDAO.atualizarLeuNotasVersao(authenticationContext.getUsuarioLogado().getId());
		}
	}
	
	public List<Perfil> getPerfisUsuario(){
		return new ArrayList<Perfil>(authenticationContext.getUsuarioLogado().getPerfis()); 
	}
	
	public List<Integer> listarDentes(){
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int i = 10; i < 87; i++) {
			if((i >= 11 && i <= 18) || (i >= 21 && i <= 28) || 
				(i >= 31 && i <= 38) || (i >= 41 && i <= 48) ||
				(i >= 51 && i <= 56) || (i >= 61 && i <= 66) || 
				(i >= 71 && i <= 76) || (i >= 81 && i <= 86)){
				result.add(i);
			}
		}
		return result;
	}

	public List<Object> getFiltrados() {
		return filtrados;
	}

	public void setFiltrados(List<Object> filtrados) {
		this.filtrados = filtrados;
	}

	public String getTipoTabelaAutocomplete() {
		return tipoTabelaAutocomplete;
	}

	public void setTipoTabelaAutocomplete(String tipoTabelaAutocomplete) {
		this.tipoTabelaAutocomplete = tipoTabelaAutocomplete;
	}

}
