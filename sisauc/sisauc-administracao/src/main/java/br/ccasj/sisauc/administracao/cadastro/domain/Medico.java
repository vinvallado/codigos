package br.ccasj.sisauc.administracao.cadastro.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.Estado;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;

@Entity
@Table(name = "medico", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class Medico extends EntidadeGenerica {

	private static final long serialVersionUID = -4510098964342554793L;
	
	private static final String SEQ_NAME = "medico_id_seq";

	private String nome;
	private String numeroConselhoRegional;
	private Estado estadoConselhoRegional;
	private boolean ativo = true;
	private boolean profissionalSaude = true;
	private boolean militar = true;
	private TipoProfissionalSaude tipoProfissionalSaude = TipoProfissionalSaude.MEDICO;
	private OrganizacaoMilitar organizacaoMilitar;
	private Set<Especialidade> especialidades = new HashSet<Especialidade>();
	
	public Medico() {
		super();
	}

	public Medico(Integer id) {
		super(id);
	}
	
	
	
	public Medico(Integer id, String nome) {
		super(id);
		this.nome = nome;
	}

	public Medico(Integer id, String nome, String numeroConselhoRegional) {
		super(id);
		this.nome = nome;
		this.setNumeroConselhoRegional(numeroConselhoRegional);
	}

	public Medico(Integer id, String nome, String numeroConselhoRegional, boolean ativo) {
		super(id);
		this.nome = nome;
		this.setNumeroConselhoRegional(numeroConselhoRegional);
		this.ativo = ativo;
	}

	public Medico(Integer id, String nome, String numeroConselhoRegional, boolean ativo, boolean profissionalSaude,
			boolean militar, TipoProfissionalSaude tipoProfissionalSaude) {
		super(id);
		this.nome = nome;
		this.numeroConselhoRegional = numeroConselhoRegional;
		this.ativo = ativo;
		this.profissionalSaude = profissionalSaude;
		this.militar = militar;
		this.tipoProfissionalSaude = tipoProfissionalSaude;
	}

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}

	@NotNull
	@NotEmpty(message = "Campo 'Nome' é obrigatório")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "numero_conselho_regional")
	public String getNumeroConselhoRegional() {
		return numeroConselhoRegional;
	}

	public void setNumeroConselhoRegional(String numeroConselhoRegional) {
		this.numeroConselhoRegional = numeroConselhoRegional;
	}
	
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@ManyToOne
	@JoinColumn(name = "id_organizacao_militar")
	public OrganizacaoMilitar getOrganizacaoMilitar() {
		return organizacaoMilitar;
	}

	public void setOrganizacaoMilitar(OrganizacaoMilitar organizacaoMilitar) {
		this.organizacaoMilitar = organizacaoMilitar;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "medico_especialidade", schema = EntidadeGenerica.SCHEMA_SISAUC, 
		joinColumns = @JoinColumn(name = "id_medico"),
		inverseJoinColumns = @JoinColumn(name = "id_especialidade"))
	public Set<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(Set<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	@Column(name = "profissional_saude")
	public boolean isProfissionalSaude() {
		return profissionalSaude;
	}

	public void setProfissionalSaude(boolean profissionalSaude) {
		this.profissionalSaude = profissionalSaude;
	}

	public boolean isMilitar() {
		return militar;
	}

	public void setMilitar(boolean militar) {
		this.militar = militar;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_profissional_saude")
	public TipoProfissionalSaude getTipoProfissionalSaude() {
		return tipoProfissionalSaude;
	}

	public void setTipoProfissionalSaude(TipoProfissionalSaude tipoProfissionalSaude) {
		this.tipoProfissionalSaude = tipoProfissionalSaude;
	}

	@ManyToOne
	@JoinColumn(name = "id_estado_conselho_regional")
	public Estado getEstadoConselhoRegional() {
		return estadoConselhoRegional;
	}

	public void setEstadoConselhoRegional(Estado estadoConselhoRegional) {
		this.estadoConselhoRegional = estadoConselhoRegional;
	}

}
