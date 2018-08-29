package br.ccasj.sisauc.administracao.cadastro.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.EspecialidadeDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.MedicoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Especialidade;
import br.ccasj.sisauc.administracao.cadastro.domain.Medico;
import br.ccasj.sisauc.administracao.formatter.EspecialidadeFormatter;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ApplicationBean;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;

@Scope(value = "view")
@Service(value = "cadastroMedicoListaBean")
public class CadastroMedicoListaBean implements Serializable {

	private static final long serialVersionUID = 6292898118228989695L;

	@Autowired
	private MedicoDAO medicoDAO;
	@Autowired
	private EspecialidadeDAO especialidadeDAO;
	
	private List<Medico> medicos;
	private Medico medicoSelecionado;
	private List<SelectItem> filtroEspecialidades = new ArrayList<SelectItem>();
	
	@PostConstruct
	public void init(){
		medicos = medicoDAO.findAll();
		carregarFiltroEspecialidades();
	}
	
	public boolean filtrarEspecialidades(Set<Especialidade> lista, String filter, Locale locale){
		EspecialidadeFormatter formatter = new EspecialidadeFormatter();
		if(filter.isEmpty()) {
			return true;
		}
		for (Especialidade especialidade : lista) {
			if(formatter.getSiglaNome(especialidade).toLowerCase().contains(filter.toLowerCase())){
				return true;
			}
		}
		return false;
	}
	
	private void carregarFiltroEspecialidades() {
		List<Especialidade> especialidades = especialidadeDAO.listarEspecialidadesAtivas();
		for (Especialidade especialidade : especialidades) {
			filtroEspecialidades.add(new SelectItem(especialidade, especialidade.getSigla() + " - " + especialidade.getNome()));
		}
	}

	public void mudarStatusAtivoMedico(){
		medicoDAO.definirStatusAtivoMedico(medicoSelecionado.getId(), !medicoSelecionado.isAtivo());
		medicos = medicoDAO.findAll();
		ManagedBeanUtils.obter("applicationBean", ApplicationBean.class).initMedico();
		String message = "Profissional status com sucesso";
		Mensagem.informacao(message.replace("status", medicoSelecionado.isAtivo() ? "desativado" : "ativado"));
		ManagedBeanUtils.hideDialog("mudarStatusMedicoDialog");
	}
	
	public void selecionarMedicoMostrarEspecialidade(Medico medico){
		setMedicoSelecionado(medico);
		ManagedBeanUtils.showDialog("especialidadesDialog");
	}
	
	public void selecionarMedicoAlterarAtivo(Medico medicoEdicao){
		medicoSelecionado = medicoEdicao;
		ManagedBeanUtils.showDialog("mudarStatusMedicoDialog");
	}
	
	public List<Especialidade> listarEspecialidadesMedicoSelecionado(){
		return medicoSelecionado == null ? new ArrayList<Especialidade>() : medicoDAO.listarEspecialidadesPorIdMedico(medicoSelecionado.getId());
	}

	public List<Medico> getMedicos() {
		return medicos;
	}

	public void setMedicos(List<Medico> medicos) {
		this.medicos = medicos;
	}

	public Medico getMedicoSelecionado() {
		return medicoSelecionado;
	}

	public void setMedicoSelecionado(Medico medicoSelecionado) {
		this.medicoSelecionado = medicoSelecionado;
	}

}
