package br.ccasj.sisauc.administracao.cadastro.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.domain.EntidadeDivisaoGenerica;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;
import br.ccasj.sisauc.framework.domain.Usuario;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
@Table(name = "solicitacao", schema = EntidadeGenerica.SCHEMA_SISAUC)
public class SolicitacaoMedica extends EntidadeDivisaoGenerica {
	
	private static final long serialVersionUID = -1947865498058420920L;
	private static final String SEQ_NAME = "solicitacao_id_seq";

	protected String numero;
	protected String dadosClinicos;
	protected String observacoes;
	protected String textoInconsistencia;
	protected Date dataInsercaoSistema;
	protected Date dataSolicitacaoSistema = new Date();
	protected Date dataEnvioAuditoria;
	protected Date dataUltimaAlteracaoEstado;
	protected boolean internacao = false;
	protected boolean isento = false;
	protected LocalInternacao localInternacao;
	protected EspecificacaoIsencaoCobranca especificacao;
	protected OrganizacaoMilitar organizacaoMilitarSolicitante;
	protected Usuario autorSolicitacaoSistema;
	protected EstadoSolicitacaoProcedimento estado = EstadoSolicitacaoProcedimento.CRIADA;
	protected CodigoInternacionalDoenca hipoteseDiagnostica;
	protected Beneficiario beneficiario;
	protected Arquivo arquivo;
	private String nomeMedicoSolicitante;
	
	public SolicitacaoMedica() {
		super();
	}

	public SolicitacaoMedica(Integer id) {
		super(id);
	}
	
	public SolicitacaoMedica(Integer id, String numero) {
		super(id);
		this.numero = numero;
	}

	public SolicitacaoMedica(Integer id, Date dataSolicitacaoSistema, Date dataEnvioAuditoria, Date dataInsercaoSistema,
			Date dataUltimaAlteracaoEstado, EstadoSolicitacaoProcedimento estado,
			String saramBeneficiario, String nomeBeneficiario) {
		super(id);
		this.dataSolicitacaoSistema = dataSolicitacaoSistema;
		this.dataEnvioAuditoria = dataEnvioAuditoria;
		this.dataInsercaoSistema = dataInsercaoSistema;
		this.dataUltimaAlteracaoEstado = dataUltimaAlteracaoEstado;
		this.estado = estado;
		this.beneficiario = new Beneficiario();
		this.beneficiario.setNome(nomeBeneficiario);
		this.beneficiario.setSaram(saramBeneficiario);
	}
	
	public SolicitacaoMedica(Integer id, Divisao divisao, String numero, Date dataSolicitacaoSistema, Date dataEnvioAuditoria, Date dataInsercaoSistema,
			Date dataUltimaAlteracaoEstado, EstadoSolicitacaoProcedimento estado,
			String saramBeneficiario, String nomeBeneficiario) {
		super(id);
		this.divisao = divisao;
		this.numero = numero;
		this.dataSolicitacaoSistema = dataSolicitacaoSistema;
		this.dataEnvioAuditoria = dataEnvioAuditoria;
		this.dataInsercaoSistema = dataInsercaoSistema;
		this.dataUltimaAlteracaoEstado = dataUltimaAlteracaoEstado;
		this.estado = estado;
		this.beneficiario = new Beneficiario();
		this.beneficiario.setNome(nomeBeneficiario);
		this.beneficiario.setSaram(saramBeneficiario);
	}
	
	//PESQUISA DE SOLICITAÇÕES DE PROCEDIMENTOS
	public SolicitacaoMedica(String numero, String nomeBeneficiario, String saramBeneficiario, boolean urgente, boolean titular, String omBeneficiario, String omTitular,
			EstadoSolicitacaoProcedimento estadoSolicitacaoProcedimento, Date dataSolicitacaoSistema, String nomeProfissional, LocalInternacao localInternacao, String siglaOM) {
		super();
		this.numero = numero;
		this.estado = estadoSolicitacaoProcedimento;
		this.dataSolicitacaoSistema = dataSolicitacaoSistema;
		this.localInternacao = localInternacao;
		
		this.organizacaoMilitarSolicitante = new OrganizacaoMilitar();
		this.organizacaoMilitarSolicitante.setSigla(siglaOM);
		
		this.beneficiario = new Beneficiario();
		this.beneficiario.setNome(nomeBeneficiario);
		this.beneficiario.setSaram(saramBeneficiario);
		
		this.beneficiario.setTitular(titular);
		if (beneficiario.isTitular()) {
			this.beneficiario.setOrganizacaoMilitar(new OrganizacaoMilitar(omBeneficiario));			
		} else {
			this.beneficiario.setBeneficiarioTitular(new Beneficiario());
			this.beneficiario.getBeneficiarioTitular().setOrganizacaoMilitar(new OrganizacaoMilitar(omTitular));
		}
		
	}
	
	public SolicitacaoMedica(Integer id, String numero, Date dataSolicitacaoSistema, Date dataEnvioAuditoria, Date dataInsercaoSistema,
			Date dataUltimaAlteracaoEstado, EstadoSolicitacaoProcedimento estado, String nomeMedicoSolicitante,
			String saramBeneficiario, String nomeBeneficiario) {
		super(id);
		this.numero = numero;
		this.dataSolicitacaoSistema = dataSolicitacaoSistema;
		this.dataEnvioAuditoria = dataEnvioAuditoria;
		this.dataInsercaoSistema = dataInsercaoSistema;
		this.dataUltimaAlteracaoEstado = dataUltimaAlteracaoEstado;
		this.estado = estado;
		this.nomeMedicoSolicitante = nomeMedicoSolicitante;
		this.beneficiario = new Beneficiario();
		this.beneficiario.setNome(nomeBeneficiario);
		this.beneficiario.setSaram(saramBeneficiario);
	}
	
	//Buscar Solicitações
	public SolicitacaoMedica(Integer id, String numero, Date dataSolicitacaoSistema, Date dataEnvioAuditoria, Date dataInsercaoSistema,
			Date dataUltimaAlteracaoEstado, EstadoSolicitacaoProcedimento estado, String nomeMedicoSolicitante,
			String saramBeneficiario, String nomeBeneficiario, Integer idOM) {
		super(id);
		this.numero = numero;
		this.dataSolicitacaoSistema = dataSolicitacaoSistema;
		this.dataEnvioAuditoria = dataEnvioAuditoria;
		this.dataInsercaoSistema = dataInsercaoSistema;
		this.dataUltimaAlteracaoEstado = dataUltimaAlteracaoEstado;
		this.estado = estado;
		this.nomeMedicoSolicitante = nomeMedicoSolicitante;
		this.beneficiario = new Beneficiario();
		this.beneficiario.setNome(nomeBeneficiario);
		this.beneficiario.setSaram(saramBeneficiario);

		this.organizacaoMilitarSolicitante = new OrganizacaoMilitar();
		this.organizacaoMilitarSolicitante.setId(idOM);
	}

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@NotNull(message = "O campo 'Dados Clínicos' deve ser preenchido!")
	@Column(name = "dados_clinicos")
	public String getDadosClinicos() {
		return dadosClinicos;
	}

	public void setDadosClinicos(String dadosClinicos) {
		this.dadosClinicos = dadosClinicos;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_insercao_sistema")
	public Date getDataInsercaoSistema() {
		return dataInsercaoSistema;
	}

	public void setDataInsercaoSistema(Date dataInsercaoSistema) {
		this.dataInsercaoSistema = dataInsercaoSistema;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_solicitacao_sistema")
	public Date getDataSolicitacaoSistema() {
		return dataSolicitacaoSistema;
	}

	public void setDataSolicitacaoSistema(Date dataSolicitacaoSistema) {
		this.dataSolicitacaoSistema = dataSolicitacaoSistema;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_envio_auditoria")
	public Date getDataEnvioAuditoria() {
		return dataEnvioAuditoria;
	}

	public void setDataEnvioAuditoria(Date dataEnvioAuditoria) {
		this.dataEnvioAuditoria = dataEnvioAuditoria;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_ultima_alteracao_estado")
	public Date getDataUltimaAlteracaoEstado() {
		return dataUltimaAlteracaoEstado;
	}

	public void setDataUltimaAlteracaoEstado(Date dataUltimaAlteracaoEstado) {
		this.dataUltimaAlteracaoEstado = dataUltimaAlteracaoEstado;
	}

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_organizacao_militar")
	public OrganizacaoMilitar getOrganizacaoMilitarSolicitante() {
		return organizacaoMilitarSolicitante;
	}

	public void setOrganizacaoMilitarSolicitante(OrganizacaoMilitar organizacaoMilitarSolicitante) {
		this.organizacaoMilitarSolicitante = organizacaoMilitarSolicitante;
	}
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	public Usuario getAutorSolicitacaoSistema() {
		return autorSolicitacaoSistema;
	}

	public void setAutorSolicitacaoSistema(Usuario autorSolicitacaoSistema) {
		this.autorSolicitacaoSistema = autorSolicitacaoSistema;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	public EstadoSolicitacaoProcedimento getEstado() {
		return estado;
	}

	public void setEstado(EstadoSolicitacaoProcedimento estado) {
		this.estado = estado;
	}

	@ManyToOne
	@JoinColumn(name = "id_cid")
	public CodigoInternacionalDoenca getHipoteseDiagnostica() {
		return hipoteseDiagnostica;
	}

	public void setHipoteseDiagnostica(CodigoInternacionalDoenca hipoteseDiagnostica) {
		this.hipoteseDiagnostica = hipoteseDiagnostica;
	}

	@NotNull
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "id_beneficiario", referencedColumnName = "id"),
	})
	public Beneficiario getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}

	@ManyToOne
	@JoinColumn(name = "id_arquivo")
	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	@Transient
	public String getNomeMedicoSolicitante() {
		return nomeMedicoSolicitante;
	}

	public void setNomeMedicoSolicitante(String nomeMedicoSolicitante) {
		this.nomeMedicoSolicitante = nomeMedicoSolicitante;
	}

	public boolean isInternacao() {
		return internacao;
	}

	public void setInternacao(boolean internacao) {
		this.internacao = internacao;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "local_internacao")
	public LocalInternacao getLocalInternacao() {
		return localInternacao;
	}

	public void setLocalInternacao(LocalInternacao localInternacao) {
		this.localInternacao = localInternacao;
	}
	
	@Column(name = "texto_inconsistencia")
	public String getTextoInconsistencia() {
		return textoInconsistencia;
	}
	
	public void setTextoInconsistencia(String textoInconsistencia) {
		this.textoInconsistencia = textoInconsistencia;
	}

	public boolean isIsento() {
		return isento;
	}

	public void setIsento(boolean isento) {
		this.isento = isento;
	}

	@ManyToOne
	@JoinColumn(name = "id_especificacao_isencao_cobranca")
	public EspecificacaoIsencaoCobranca getEspecificacao() {
		return especificacao;
	}

	public void setEspecificacao(EspecificacaoIsencaoCobranca especificacao) {
		this.especificacao = especificacao;
	}
	
	
}
