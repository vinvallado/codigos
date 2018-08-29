package br.ccasj.sisauc.administracao.cadastro.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.EspecialidadeDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.EstadoDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.MedicoDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.OrganizacaoMilitarDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Especialidade;
import br.ccasj.sisauc.administracao.cadastro.domain.Medico;
import br.ccasj.sisauc.administracao.cadastro.domain.TipoEspecialidade;
import br.ccasj.sisauc.administracao.cadastro.domain.TipoProfissionalSaude;
import br.ccasj.sisauc.administracao.cadastro.service.CadastroMedicoService;
import br.ccasj.sisauc.framework.domain.Estado;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ApplicationBean;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;

@Scope(value = "view")
@Service(value = "cadastroMedicoFormularioBean")
public class CadastroMedicoFormularioBean implements Serializable {
	
	private static final long serialVersionUID = -6712827120154568671L;

	@Autowired
	private MedicoDAO medicoDAO;
	@Autowired
	private OrganizacaoMilitarDAO organizacaoMilitarDAO;
	@Autowired
	private EspecialidadeDAO especialidadeDAO;
	@Autowired
	private CadastroMedicoService cadastroMedicoService;
	@Autowired
	private EstadoDAO estadoDAO;

	private Medico medico;
	private List<OrganizacaoMilitar> oms;
	private List<Especialidade> especialidades;
	private List<TipoProfissionalSaude> tipos = new ArrayList<TipoProfissionalSaude>();
	private List<Estado> estados = new ArrayList<Estado>();
	
	@PostConstruct
	private void init() {
		medico = obterMedico();
		listarEspecialidades();
		oms = organizacaoMilitarDAO.listarOMSAtivasPorRegional();
		estados = estadoDAO.findAll();
		getTipos().addAll(Arrays.asList(TipoProfissionalSaude.values()));
	}
	
	private Medico obterMedico() {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		return "novo".equals(id) ? new Medico() : medicoDAO.findById(Integer.valueOf(id));
	}
	
	private void listarEspecialidades(){
		TipoEspecialidade tipoEspecialidade;
		if(medico.getTipoProfissionalSaude().equals(TipoProfissionalSaude.MEDICO)){
			tipoEspecialidade = TipoEspecialidade.MEDICA;
			especialidades = especialidadeDAO.listarEspecialidadesAtivasPorTipos(tipoEspecialidade);
		}
		if(medico.getTipoProfissionalSaude().equals(TipoProfissionalSaude.DENTISTA)){
			tipoEspecialidade = TipoEspecialidade.ODONTOLOGICA;
			especialidades = especialidadeDAO.listarEspecialidadesAtivasPorTipos(tipoEspecialidade);
		}
		if(medico.getTipoProfissionalSaude().equals(TipoProfissionalSaude.NUTRICIONISTA)){
			tipoEspecialidade = TipoEspecialidade.NUTRICAO;
			especialidades = especialidadeDAO.listarEspecialidadesAtivasPorTipos(tipoEspecialidade);
		}
		if(medico.getTipoProfissionalSaude().equals(TipoProfissionalSaude.FISIOTERAPEUTA)){
			tipoEspecialidade = TipoEspecialidade.FISIOTERAPIA;
			especialidades = especialidadeDAO.listarEspecialidadesAtivasPorTipos(tipoEspecialidade);
		}
		if(medico.getTipoProfissionalSaude().equals(TipoProfissionalSaude.TERAPEUTA_OCUPACIONAL)){
			tipoEspecialidade = TipoEspecialidade.TERAPIA_OCUPACIONAL;
			especialidades = especialidadeDAO.listarEspecialidadesAtivasPorTipos(tipoEspecialidade);
		}
		if(medico.getTipoProfissionalSaude().equals(TipoProfissionalSaude.PSICOLOGO)){
			tipoEspecialidade = TipoEspecialidade.PSICOLOGIA;
			especialidades = especialidadeDAO.listarEspecialidadesAtivasPorTipos(tipoEspecialidade);
		}
		if(medico.getTipoProfissionalSaude().equals(TipoProfissionalSaude.FONOAUDIOLOGO)){
			tipoEspecialidade = TipoEspecialidade.FONOAUDIOLOGIA;
			especialidades = especialidadeDAO.listarEspecialidadesAtivasPorTipos(tipoEspecialidade);
		}
	}
	
	public void salvar(){
		cadastroMedicoService.salvar(medico);
		ManagedBeanUtils.obter("applicationBean", ApplicationBean.class).initMedico();
		Mensagem.informacao("Profissional salvo com sucesso!");
		ManagedBeanUtils.redirecionar("/administracao/cadastro/medico");
	}

	public void onSelectTipoProfissional(AjaxBehaviorEvent event){
		listarEspecialidades();
	}
	
	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public List<OrganizacaoMilitar> getOms() {
		return oms;
	}

	public void setOms(List<OrganizacaoMilitar> oms) {
		this.oms = oms;
	}

	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	public List<TipoProfissionalSaude> getTipos() {
		return tipos;
	}

	public void setTipos(List<TipoProfissionalSaude> tipos) {
		this.tipos = tipos;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}
}
