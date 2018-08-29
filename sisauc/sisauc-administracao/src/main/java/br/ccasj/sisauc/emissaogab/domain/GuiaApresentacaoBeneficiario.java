package br.ccasj.sisauc.emissaogab.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.administracao.cadastro.domain.Credenciado;
import br.ccasj.sisauc.administracao.cadastro.domain.EspecificacaoIsencaoCobranca;
import br.ccasj.sisauc.administracao.cadastro.domain.LocalInternacao;
import br.ccasj.sisauc.administracao.cadastro.domain.Medico;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoProcedimento;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectiva;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.domain.EntidadeDivisaoGenerica;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;
import br.ccasj.sisauc.framework.domain.Usuario;

@Entity
@Table(name = "gab", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class GuiaApresentacaoBeneficiario extends EntidadeDivisaoGenerica {

	private static final long serialVersionUID = 6875282501131436950L;

	private static final String SEQ_NAME = "gab_id_seq";
	
	private String codigo;
	private String justificativaCancelamentoGab;
	//TODO Alterados nomes de datas, confusão com nomes. dataEmissao/dataImpressao trocados respectivamentes por dataGeracao/dataEmissao
	private Date dataGeracao;
	private Date dataEmissao;
	private Date dataApresentacao;
	private Beneficiario beneficiario;
	private Credenciado credenciado;
	private AuditoriaProspectiva auditoriaProspectiva;
	private EstadoGAB estado;
	private OrganizacaoMilitar organizacaoMilitar;
	private boolean isento;
	private EspecificacaoIsencaoCobranca especificacao;
	private Set<ItemGAB> itens = new HashSet<ItemGAB>();
	private Usuario usuarioEmissorGab;
	
	public GuiaApresentacaoBeneficiario() {
		super();
	}

	public GuiaApresentacaoBeneficiario(Integer id) {
		super(id);
	}
	
	public GuiaApresentacaoBeneficiario(Integer id, String codigo) {
		super(id);
		this.codigo = codigo;
	}

	public GuiaApresentacaoBeneficiario(Integer id, String codigo, Date dataGeracao, Date dataEmissao, EstadoGAB estado, String justificativaCancelamento) {
		super(id);
		this.codigo = codigo;
		this.dataGeracao = dataGeracao;
		this.dataEmissao = dataEmissao;
		this.estado = estado;
		this.justificativaCancelamentoGab = justificativaCancelamento;
		this.usuarioEmissorGab = new Usuario();
	}

	public GuiaApresentacaoBeneficiario(Integer id, Date dataGeracao, EstadoGAB estado) {
		super(id);
		this.dataGeracao = dataGeracao;
		this.estado = estado;
	}

	public GuiaApresentacaoBeneficiario(Integer id, String codigo, String nomeFantasiaCredenciado) {
		super(id);
		this.codigo = codigo;
		this.credenciado = new Credenciado();
		this.credenciado.setNomeFantasia(nomeFantasiaCredenciado);
	}
	
	public GuiaApresentacaoBeneficiario(Integer id, String codigo, Date dataEmissao, String nomeBeneficiario,
			String nomeFantasiaCredenciado, EstadoGAB estado) {
		this(id, codigo, nomeFantasiaCredenciado);
		this.dataEmissao = dataEmissao;
		this.beneficiario = new Beneficiario();
		this.beneficiario.setNome(nomeBeneficiario);
		this.estado = estado;
	}
	
	public GuiaApresentacaoBeneficiario(Integer id, Divisao divisao, String codigo, Date dataGeracao, Date dataEmissao, EstadoGAB estado, String nomeBeneficiario,
			String saram, String saramTitular, String nomeFantasiaCredenciado, Integer idAuditoria, boolean solicitacaoUrgente) {
		this(id, codigo, dataGeracao, dataEmissao ,nomeFantasiaCredenciado);
		this.divisao = divisao;
		this.beneficiario = new Beneficiario();
		this.beneficiario.setNome(nomeBeneficiario);
		this.beneficiario.setSaram(saram);
		this.beneficiario.setSaramTitular(saramTitular);
		this.estado = estado;
		this.auditoriaProspectiva = new AuditoriaProspectiva();
		this.auditoriaProspectiva.setId(idAuditoria);
		this.auditoriaProspectiva.setSolicitacao(new SolicitacaoProcedimento());
		this.auditoriaProspectiva.getSolicitacao().setUrgente(solicitacaoUrgente);
	}
	
	public GuiaApresentacaoBeneficiario(Integer id, Divisao divisao, String codigo, Date dataGeracao, Date dataEmissao, String nomeBeneficiario, String nomeFantasiaCredenciado, EstadoGAB estado) {
		this(id, codigo, dataGeracao, dataEmissao ,nomeFantasiaCredenciado);
		this.divisao = divisao;
		this.beneficiario = new Beneficiario();
		this.beneficiario.setNome(nomeBeneficiario);
		this.estado = estado;
		this.auditoriaProspectiva = new AuditoriaProspectiva();
	}
	
	public GuiaApresentacaoBeneficiario(Integer id, String codigo, Date dataGeracao, Date dataEmissao, String nomeFantasiaCredenciado) {
		this(id);
		this.codigo = codigo;
		this.dataGeracao = dataGeracao;
		this.dataEmissao = dataEmissao;
		this.beneficiario = new Beneficiario();
		this.credenciado = new Credenciado(nomeFantasiaCredenciado);
	}
	
	//Construtor da pesquisa de GAB
	public GuiaApresentacaoBeneficiario(Integer id, String codigo, Divisao divisao, Date dataEmissao, Date dataApresentacao, EstadoGAB estado, 
			String nomeBeneficiario, String saramBeneficiario, boolean titular, Date dataNascimentoBeneficiario, 
			String omBeneficiario, String omTitular, String numeroSolicitacao, String profissionalSolicitante, boolean urgente, 
			LocalInternacao localInternacao, String nomeFantasiaCredenciado, String siglaOM){
		this.id = id;
		this.codigo = codigo;
		this.divisao = divisao;
		this.dataEmissao = dataEmissao;
		this.dataApresentacao = dataApresentacao;
		this.estado = estado;
		this.beneficiario = new Beneficiario(null, saramBeneficiario, nomeBeneficiario);
		this.beneficiario.setTitular(titular);
		this.beneficiario.setDataNascimento(dataNascimentoBeneficiario);
		if (beneficiario.isTitular()) {
			this.beneficiario.setOrganizacaoMilitar(new OrganizacaoMilitar(omBeneficiario));			
		} else {
			this.beneficiario.setBeneficiarioTitular(new Beneficiario());
			this.beneficiario.getBeneficiarioTitular().setOrganizacaoMilitar(new OrganizacaoMilitar(omTitular));
		}
		this.credenciado = new Credenciado(nomeFantasiaCredenciado);
		this.auditoriaProspectiva = new AuditoriaProspectiva();
		this.auditoriaProspectiva.setSolicitacao(new SolicitacaoProcedimento());
		this.auditoriaProspectiva.getSolicitacao().setNumero(numeroSolicitacao);
		this.auditoriaProspectiva.getSolicitacao().setUrgente(urgente);
		this.auditoriaProspectiva.getSolicitacao().setMedicoSolicitante(new Medico(null, profissionalSolicitante, null));
		this.auditoriaProspectiva.getSolicitacao().setLocalInternacao(localInternacao);
		this.organizacaoMilitar = new OrganizacaoMilitar();
		this.organizacaoMilitar.setSigla(siglaOM);
	}

	public GuiaApresentacaoBeneficiario(Integer idGAB, Divisao divisao, String codigo, Date dataGeracao, EstadoGAB estado, String nomeBeneficiario, String saram, String saramTitular) {
		super();
		this.id = idGAB;
		this.divisao = divisao;
		this.codigo = codigo;
		this.dataGeracao = dataGeracao;
		this.estado = estado;
		
		Beneficiario beneficiario = new Beneficiario();
		beneficiario.setNome(nomeBeneficiario);
		beneficiario.setSaram(saram);
		beneficiario.setSaramTitular(saramTitular);
		this.beneficiario = beneficiario;
	}

	public GuiaApresentacaoBeneficiario(Date dataGeracao, Date dataEmissao, String nomeFantasiaCredenciado,
			EstadoGAB estadoGAB) {
		super();
		this.dataGeracao = dataGeracao;
		this.dataEmissao = dataEmissao;
		this.estado = estadoGAB;
		
		Credenciado credenciado = new Credenciado();
		credenciado.setNomeFantasia(nomeFantasiaCredenciado);
		this.credenciado = credenciado;
	}

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}	
	
	@NotEmpty(message = "Campo código é obrigatório")
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Column(name = "justificativa_cancelamento_gab")
	public String getJustificativaCancelamentoGab() {
		return justificativaCancelamentoGab;
	}

	public void setJustificativaCancelamentoGab(String justificativaCancelamentoGab) {
		this.justificativaCancelamentoGab = justificativaCancelamentoGab;
	}

	@NotNull(message = "Campo data de geração é obrigatório")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_geracao")
	public Date getDataGeracao() {
		return dataGeracao;
	}

	public void setDataGeracao(Date dataGeracao) {
		this.dataGeracao = dataGeracao;
	}
	
	@NotNull(message = "Campo beneficiário é obrigatório")
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

	@NotNull(message = "O credenciado é obrigatório")
	@ManyToOne
	@JoinColumn(name = "id_credenciado")
	public Credenciado getCredenciado() {
		return credenciado;
	}

	public void setCredenciado(Credenciado credenciado) {
		this.credenciado = credenciado;
	}

	@ManyToOne
	@JoinColumn(name = "id_auditoria_prospectiva")
	public AuditoriaProspectiva getAuditoriaProspectiva() {
		return auditoriaProspectiva;
	}

	public void setAuditoriaProspectiva(AuditoriaProspectiva auditoriaProspectiva) {
		this.auditoriaProspectiva = auditoriaProspectiva;
	}

	@NotNull(message = "Campo estado é obrigatório")
	@Enumerated(EnumType.STRING)
	public EstadoGAB getEstado() {
		return estado;
	}

	public void setEstado(EstadoGAB estado) {
		this.estado = estado;
	}

	@NotNull(message = "Campo organização militar obrigatório")
	@ManyToOne
	@JoinColumn(name = "id_organizacao_militar")
	public OrganizacaoMilitar getOrganizacaoMilitar() {
		return organizacaoMilitar;
	}

	public void setOrganizacaoMilitar(OrganizacaoMilitar organizacaoMilitar) {
		this.organizacaoMilitar = organizacaoMilitar;
	}

	@OneToMany(mappedBy = "gab", cascade = CascadeType.MERGE)
	public Set<ItemGAB> getItens() {
		return itens;
	}

	public void setItens(Set<ItemGAB> itens) {
		this.itens = itens;
	}

	//TODO remover esses métodos transientes
	@Transient
	public List<ItemGAB> getItensList() {
		List<ItemGAB>  listaRetorno = new LinkedList<ItemGAB>();
		
		TreeSet<ItemGAB> treeSet = new TreeSet<ItemGAB>(itens);
		Iterator<ItemGAB> iterator = treeSet.iterator();
		while(iterator.hasNext()){
			listaRetorno.add(iterator.next());
		}

		return listaRetorno;
	}
	
	@Transient
	public double getValorTotal() {
		double totalGAB = 0;
		for (ItemGAB item : this.getItens()) {
			totalGAB += item.getValorTotal();
		}
		return totalGAB;
	}
	
	@Column(name = "data_emissao")
	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_apresentacao")
	public Date getDataApresentacao() {
		return dataApresentacao;
	}

	public void setDataApresentacao(Date dataApresentacao) {
		this.dataApresentacao = dataApresentacao;
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

	@ManyToOne
	@JoinColumn(name = "id_usuario_emissor")
	public Usuario getUsuarioEmissorGab() {
		return usuarioEmissorGab;
	}

	public void setUsuarioEmissorGab(Usuario usuarioEmissorGab) {
		this.usuarioEmissorGab = usuarioEmissorGab;
	}
	
}
