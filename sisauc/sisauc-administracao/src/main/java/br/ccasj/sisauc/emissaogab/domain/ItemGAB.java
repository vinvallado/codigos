package br.ccasj.sisauc.emissaogab.domain;

import java.util.Date;
import java.util.Set;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciadoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.Credenciado;
import br.ccasj.sisauc.administracao.cadastro.domain.Especialidade;
import br.ccasj.sisauc.administracao.cadastro.domain.GrupoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.Medico;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoCBHPM2012;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.SubGrupoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.Tabela;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectiva;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.AuditoriaRetrospectiva;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.Motivo;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;


@Entity
@Table(name = "item_gab", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class ItemGAB extends EntidadeGenerica implements Comparable<ItemGAB> {

	private static final long serialVersionUID = -2947200775823270375L;

	private static final String SEQ_NAME = "item_gab_id_seq";

	private String codigo;
	private ConfiguracaoEditalCredenciadoProcedimento configuracao;
	private Double valorTotal;
	private Set<MetadadoValorItemGAB> valores;
	private AuditoriaRetrospectiva auditoriaRetrospectiva;
	private EstadoItemGAB estadoItemGAB;
	private String descricaoOpme;
	private String observacaoGAB;
	private double valorOpme;
	private GuiaApresentacaoBeneficiario gab;
	private String numeroLote;

	public ItemGAB() {
		super();
	}

	public ItemGAB(Integer id) {
		super(id);
	}
	
	public ItemGAB(Integer id, String codigo){
		this(id);
		this.codigo = codigo;
	}
	
	public ItemGAB(Integer id, String codigo, EstadoItemGAB estadoItemGAB) {
		this(id, codigo);
		this.estadoItemGAB = estadoItemGAB;
	}

	public ItemGAB(Integer id, String codigo, EstadoItemGAB estadoItemGAB, Divisao divisao, Date dataGeracao, Date dataEmissao, String nomeFantasiaCredenciado){
		this(id,codigo, estadoItemGAB);
		this.gab = new GuiaApresentacaoBeneficiario(null, null, dataGeracao, dataEmissao, nomeFantasiaCredenciado);
		this.gab.setDivisao(divisao);
	}
	
	public ItemGAB(Integer id, String codigo, EstadoItemGAB estadoItemGAB, ConfiguracaoEditalCredenciadoProcedimento configuracao, Date dataGeracao, Date dataEmissao, String nomeFantasiaCredenciado){
		this(id);
		this.codigo = codigo;
		this.estadoItemGAB = estadoItemGAB;
		this.configuracao = configuracao;
		this.gab = new GuiaApresentacaoBeneficiario(null, null, dataGeracao, dataEmissao, nomeFantasiaCredenciado);
	}
	
	public ItemGAB(Integer id, String codigo, EstadoItemGAB estadoItemGAB, Divisao divisao, ConfiguracaoEditalCredenciadoProcedimento configuracao, Date dataGeracao, Date dataEmissao, String nomeFantasiaCredenciado, String numeroLote){
		this(id);
		this.codigo = codigo;
		this.estadoItemGAB = estadoItemGAB;
		this.configuracao = configuracao;
		this.gab = new GuiaApresentacaoBeneficiario(null, null, dataGeracao, dataEmissao, nomeFantasiaCredenciado);
		this.gab.setDivisao(divisao);
		this.numeroLote = numeroLote;
	}

	public ItemGAB(Integer id, String codigo, Double valorFinalAuditoriaRetrospectiva, Divisao divisao, Date dataGeracao, Date dataEmissao, String nomeFantasiaCredenciado, EstadoGAB estadoGAB){
		this(id);
		this.codigo = codigo;
		this.auditoriaRetrospectiva = new AuditoriaRetrospectiva();
		this.auditoriaRetrospectiva.setValorFinal(valorFinalAuditoriaRetrospectiva);
		this.gab = new GuiaApresentacaoBeneficiario(dataGeracao, dataEmissao, nomeFantasiaCredenciado, estadoGAB);
		this.gab.setDivisao(divisao);
	}
	
	public ItemGAB(Integer id, String codigo, Double valorApresentadolAuditoriaRetrospectiva, Double valorAuditadoAuditoriaRetrospectiva, Double valorFinalAuditoriaRetrospectiva,String nomeFantasiaCredenciado,  Date dataGeracao, Date dataEmissao){
		this(id);
		this.codigo = codigo;
		this.auditoriaRetrospectiva = new AuditoriaRetrospectiva();
		this.auditoriaRetrospectiva.setValorApresentado(valorApresentadolAuditoriaRetrospectiva);
		this.auditoriaRetrospectiva.setValorAuditado(valorAuditadoAuditoriaRetrospectiva);
		this.auditoriaRetrospectiva.setValorFinal(valorFinalAuditoriaRetrospectiva);
		this.gab = new GuiaApresentacaoBeneficiario(null, null, dataGeracao, dataEmissao, nomeFantasiaCredenciado);
	}
	
	public ItemGAB(Integer id, String codigo, Double valorApresentadolAuditoriaRetrospectiva, Double valorAuditadoAuditoriaRetrospectiva, Double valorFinalAuditoriaRetrospectiva,String nomeFantasiaCredenciado,  Date dataGeracao, Date dataEmissao, ConfiguracaoEditalCredenciadoProcedimento configuracao, String justifivativa){
		this(id);
		this.codigo = codigo;
		this.auditoriaRetrospectiva = new AuditoriaRetrospectiva();
		this.auditoriaRetrospectiva.setValorApresentado(valorApresentadolAuditoriaRetrospectiva);
		this.auditoriaRetrospectiva.setValorAuditado(valorAuditadoAuditoriaRetrospectiva);
		this.auditoriaRetrospectiva.setValorFinal(valorFinalAuditoriaRetrospectiva);
		this.auditoriaRetrospectiva.setJustificativaValorAuditado(justifivativa);
		this.configuracao = configuracao;
		this.gab = new GuiaApresentacaoBeneficiario(null, null, dataGeracao, dataEmissao, nomeFantasiaCredenciado);
	}
	
	public ItemGAB(Integer id, String codigo, Double valorApresentadolAuditoriaRetrospectiva, Double valorAuditadoAuditoriaRetrospectiva, Double valorFinalAuditoriaRetrospectiva,String nomeFantasiaCredenciado,  Date dataGeracao, Date dataEmissao, ConfiguracaoEditalCredenciadoProcedimento configuracao, String justifivativa, Motivo motivo){
		this(id);
		this.codigo = codigo;
		this.auditoriaRetrospectiva = new AuditoriaRetrospectiva();
		this.auditoriaRetrospectiva.setValorApresentado(valorApresentadolAuditoriaRetrospectiva);
		this.auditoriaRetrospectiva.setValorAuditado(valorAuditadoAuditoriaRetrospectiva);
		this.auditoriaRetrospectiva.setValorFinal(valorFinalAuditoriaRetrospectiva);
		this.auditoriaRetrospectiva.setJustificativaValorAuditado(justifivativa);
		this.auditoriaRetrospectiva.setMotivo(motivo);
		this.configuracao = configuracao;
		this.gab = new GuiaApresentacaoBeneficiario(null, null, dataGeracao, dataEmissao, nomeFantasiaCredenciado);
	}
	
	//Relatório Não Conformidade
	public ItemGAB(Integer id, String codigo, Double valorApresentadolAuditoriaRetrospectiva, Double valorAuditadoAuditoriaRetrospectiva, Double valorFinalAuditoriaRetrospectiva, String nomeFantasiaCredenciado,
			Divisao divisao, Date dataGeracao, Date dataEmissao, ConfiguracaoEditalCredenciadoProcedimento configuracao, String justifivativa, Motivo motivo, String nomeBeneficiario){
		this(id);
		this.codigo = codigo;
		this.auditoriaRetrospectiva = new AuditoriaRetrospectiva();
		this.auditoriaRetrospectiva.setValorApresentado(valorApresentadolAuditoriaRetrospectiva);
		this.auditoriaRetrospectiva.setValorAuditado(valorAuditadoAuditoriaRetrospectiva);
		this.auditoriaRetrospectiva.setValorFinal(valorFinalAuditoriaRetrospectiva);
		this.auditoriaRetrospectiva.setJustificativaValorAuditado(justifivativa);
		this.auditoriaRetrospectiva.setMotivo(motivo);
		this.configuracao = configuracao;
		
		this.gab = new GuiaApresentacaoBeneficiario();
		this.gab.setDataGeracao(dataGeracao);
		this.gab.setDataEmissao(dataEmissao);
		this.gab.setDivisao(divisao);
		
		this.gab.setCredenciado(new Credenciado(nomeFantasiaCredenciado));
		
		this.gab.setBeneficiario(new Beneficiario());
		this.gab.getBeneficiario().setNome(nomeBeneficiario);
	}
	
		/**
		 * Construtor da pesquisa.
		 */
		public ItemGAB(Integer id, String codigo, EstadoItemGAB estado, String observacaoGAB, Double valorTotal,
				String codigoGAB, Divisao divisao, EstadoGAB estadoGAB, Date dataGeracaoGAB, Date dataEmissaoGAB, Date dataApresentacaoGAB,
				String saram, String nomeBeneficiario, Date dataNascimentoBeneficiario, boolean titular, String omBeneficiario, String omTitular,
				String nomeFantasiaCredenciado,
				Tabela tabela, String codigoProcedimento, String descricaoProcedimento, String subGrupo, String grupoProcedimento,
				String siglaEspecialidadeProcedimento,
				String nomeProfissional,
				Double valorApresentado, Double valorFinal, Double valorAuditado, String siglaOM, String saramTitular, String numeroLote){		
			super(id);
			this.estadoItemGAB = estado;
			this.codigo = codigo;
			this.observacaoGAB = observacaoGAB;
			this.valorTotal = valorTotal;
			this.configuracao = new ConfiguracaoEditalCredenciadoProcedimento();
			
			this.configuracao.setProcedimento(ProcedimentoCBHPM2012.criarProcedimento(tabela, codigoProcedimento, descricaoProcedimento));
			if(Tabela.CBHPM2012.equals(tabela)){
				((ProcedimentoCBHPM2012)this.configuracao.getProcedimento()).setSubGrupo(new SubGrupoProcedimento());
				((ProcedimentoCBHPM2012)this.configuracao.getProcedimento()).getSubGrupo().setDescricao(subGrupo);
				((ProcedimentoCBHPM2012)this.configuracao.getProcedimento()).getSubGrupo().setGrupoProcedimento(new GrupoProcedimento());
				((ProcedimentoCBHPM2012)this.configuracao.getProcedimento()).getSubGrupo().getGrupoProcedimento().setDescricao(grupoProcedimento);
			}
			
			this.configuracao.setEspecialidade(new Especialidade());
			this.configuracao.getEspecialidade().setSigla(siglaEspecialidadeProcedimento);
			this.gab = new GuiaApresentacaoBeneficiario();
			this.gab.setOrganizacaoMilitar(new OrganizacaoMilitar());
			this.gab.getOrganizacaoMilitar().setSigla(siglaOM);
			this.gab.setCodigo(codigoGAB);
			this.gab.setDivisao(divisao);
			this.gab.setEstado(estadoGAB);
			this.gab.setDataGeracao(dataGeracaoGAB);
			this.gab.setDataEmissao(dataEmissaoGAB);
			this.gab.setDataApresentacao(dataApresentacaoGAB);
			this.gab.setBeneficiario(new Beneficiario());
			this.gab.getBeneficiario().setNome(nomeBeneficiario);
			this.gab.getBeneficiario().setDataNascimento(dataNascimentoBeneficiario);
			this.gab.getBeneficiario().setTitular(titular);
			if (gab.getBeneficiario().isTitular()) {
				this.gab.getBeneficiario().setSaram(saram);
				this.gab.getBeneficiario().setOrganizacaoMilitar(new OrganizacaoMilitar(omBeneficiario));			
			} else {
				this.gab.getBeneficiario().setBeneficiarioTitular(new Beneficiario());
				this.gab.getBeneficiario().getBeneficiarioTitular().setOrganizacaoMilitar(new OrganizacaoMilitar(omTitular));
				this.gab.getBeneficiario().getBeneficiarioTitular().setSaram(saramTitular);
			}
			this.gab.setCredenciado(new Credenciado(nomeFantasiaCredenciado));
			this.gab.setAuditoriaProspectiva(new AuditoriaProspectiva());
			this.gab.getAuditoriaProspectiva().setSolicitacao(new SolicitacaoProcedimento());
			this.gab.getAuditoriaProspectiva().getSolicitacao().setMedicoSolicitante(new Medico(null, nomeProfissional, null));
			this.setAuditoriaRetrospectiva(new AuditoriaRetrospectiva());
			this.getAuditoriaRetrospectiva().setValorApresentado(valorApresentado);
			this.getAuditoriaRetrospectiva().setValorAuditado(valorAuditado);
			this.getAuditoriaRetrospectiva().setValorFinal(valorFinal);
			this.numeroLote = numeroLote;
		}
	
	public ItemGAB(Integer id, String codigo, String nomeBeneficiario, String nomeTitular, Date dataEmissao, boolean isento){
		this(id);
		this.codigo = codigo;
		this.gab = new GuiaApresentacaoBeneficiario();
		this.gab.setDataEmissao(dataEmissao);
		this.gab.setIsento(isento);
		
		Beneficiario beneficiario = new Beneficiario();
		beneficiario.setNome(nomeBeneficiario);
		
		Beneficiario titular = new Beneficiario();
		titular.setNome(nomeTitular);
		beneficiario.setBeneficiarioTitular(titular);
		
		this.gab.setBeneficiario(beneficiario);
	}
	
	//Relatório Analítico de Fatura
	public ItemGAB(Integer idItem, String codigoItem, double valorTotalItem, Integer idAuditoria, double valorApresentado, 
			double valorAuditado, double valorFinal, String codigoGab, boolean isento, Date dataEmissao, String nomeBeneficiario, String saram, String saramTitular){
		super(idItem);
		this.codigo = codigoItem;
		this.valorTotal = valorTotalItem;
		
		AuditoriaRetrospectiva auditoria = new AuditoriaRetrospectiva();
		auditoria.setId(idAuditoria);
		auditoria.setValorApresentado(valorApresentado);
		auditoria.setValorAuditado(valorAuditado);
		auditoria.setValorFinal(valorFinal);
		this.auditoriaRetrospectiva = auditoria;
		
		GuiaApresentacaoBeneficiario gab = new GuiaApresentacaoBeneficiario();
		gab.setCodigo(codigoGab);
		gab.setIsento(isento);
		gab.setDataEmissao(dataEmissao);
		Beneficiario beneficiario = new Beneficiario();
		beneficiario.setNome(nomeBeneficiario);
		beneficiario.setSaram(saram);
		beneficiario.setSaramTitular(saramTitular);
		gab.setBeneficiario(beneficiario);
		this.gab = gab;
	}
	
	/**
	 * Retorna o valor a descontar na folha do titular, usando o arredondamento
	 * aritmético.
	 *  
	 * @return Valor arredondado. Zero, se auditoria ou valor final nulos.
	 */
	@Transient
	public Double getValorADescontar(){
		if (this.auditoriaRetrospectiva != null && this.auditoriaRetrospectiva.getValorFinal() != null){
		 return Math.round(this.auditoriaRetrospectiva.getValorFinal() * 0.2 * 100) / 100D;
		} else {
			return 0D;
		}
	}
	
	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}	

	@NotEmpty(message = "Código do item da GAB precisa ser preenchido")
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@NotNull(message = "Campo procedimento é obrigatório")
	@ManyToOne
	@JoinColumn(name = "id_configuracao_edital_credenciado_procedimento")
	public ConfiguracaoEditalCredenciadoProcedimento getConfiguracao() {
		return configuracao;
	}

	public void setConfiguracao(ConfiguracaoEditalCredenciadoProcedimento configuracao) {
		this.configuracao = configuracao;
	}

	@Column(name = "valor_total")
	@NotNull(message = "Campo valor é obrigatório")
	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "item_gab_metadado_valor_item_gab", schema = EntidadeGenerica.SCHEMA_SISAUC, 
		joinColumns =  @JoinColumn(name = "id_item_gab"),
		inverseJoinColumns = @JoinColumn(name = "id_metadado_valor_item_gab")
	)
	public Set<MetadadoValorItemGAB> getValores() {
		return valores;
	}

	public void setValores(Set<MetadadoValorItemGAB> valores) {
		this.valores = valores;
	}
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_auditoria_retrospectiva")
	public AuditoriaRetrospectiva getAuditoriaRetrospectiva() {
		return auditoriaRetrospectiva;
	}
	
	public void setAuditoriaRetrospectiva(
			AuditoriaRetrospectiva auditoriaRetrospectiva) {
		this.auditoriaRetrospectiva = auditoriaRetrospectiva;
	}
	
	@NotNull(message = "Campo estado é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "estado")
	public EstadoItemGAB getEstadoItemGAB() {
		return estadoItemGAB;
	}

	public void setEstadoItemGAB(EstadoItemGAB estadoItemGAB) {
		this.estadoItemGAB = estadoItemGAB;
	}
	
	@Column(name = "descricao_opme")
	public String getDescricaoOpme() {
		return descricaoOpme;
	}

	public void setDescricaoOpme(String descricaoOpme) {
		this.descricaoOpme = descricaoOpme;
	}

	@Column(name = "observacao_gab")
	public String getObservacaoGAB() {
		return observacaoGAB;
	}

	public void setObservacaoGAB(String observacaoGAB) {
		this.observacaoGAB = observacaoGAB;
	}

	@Column(name = "valor_opme")
	public double getValorOpme() {
		return valorOpme;
	}

	public void setValorOpme(double valorOpme) {
		this.valorOpme = valorOpme;
	}

	@NotNull
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_gab")
	public GuiaApresentacaoBeneficiario getGab() {
		return gab;
	}

	public void setGab(GuiaApresentacaoBeneficiario gab) {
		this.gab = gab;
	}
	
	@Transient
	public String getNumeroLote() {
		return numeroLote;
	}

	public void setNumeroLote(String numeroLote) {
		this.numeroLote = numeroLote;
	}

	@Override
	@Transient
	public int compareTo(ItemGAB comparado) {
		return this.getCodigo().compareTo(comparado.getCodigo());
	}
	
}
