package br.ccasj.sisauc.emissaoar.domain;

import java.util.Date;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.administracao.cadastro.domain.Especialidade;
import br.ccasj.sisauc.administracao.cadastro.domain.FaceDental;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoBase;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoTRS;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoRessarcimento;
import br.ccasj.sisauc.administracao.cadastro.domain.Tabela;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectivaRessarcimento;
import br.ccasj.sisauc.auditoriaprospectiva.domain.RespostaRessarcimentoAuditoria;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.AuditoriaRetrospectivaRessarcimento;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;
import br.ccasj.sisauc.intendencia.domain.RelatorioFolhaBeneficiarioItem;


@Entity
@Table(name = "item_ar", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class ItemAR extends EntidadeGenerica implements Comparable<ItemAR> {

	private static final long serialVersionUID = -2947200775823270375L;

	private static final String SEQ_NAME = "item_ar_id_seq";

	private String codigo;
	private EstadoItemAR estadoItemAR;
	private AutorizacaoRessarcimento ar;
	private ProcedimentoBase procedimento;
	private Especialidade especialidade;
	private AuditoriaRetrospectivaRessarcimento auditoriaRetrospectiva;
	private Date dataNotaFiscal;
	private String cpfCnpjPrestador;
	private String numeroNotaFiscal;
	private RespostaRessarcimentoAuditoria respostaRessarcimentoAuditoria;
	private String observacaoARE;
	private Integer dente;
	private FaceDental faceDental;
	
	public ItemAR() {
		
	}
	
	//Construtor da pesquisa
	public ItemAR(Integer id, String codigo, EstadoItemAR estado, String observacaoARE,
			String codigoARE, Divisao divisao, EstadoAR estadoARE, Date dataGeracaoARE, Date dataEmissaoARE, Date dataApresentacaoARE,
			String saram, String nomeBeneficiario, Date dataNascimentoBeneficiario, boolean titular, String omBeneficiario, String omTitular,
			Date dataNotaFiscal, String cpfCnpjPrestador, String numeroNotaFiscal,
			Tabela tabela, String codigoProcedimento, String descricaoProcedimento, 
			String siglaEspecialidadeProcedimento, String siglaOM, String saramTitular,
			Double valorApresentado, Double valorEstimado, Double valorRessarcimento){		
		
		super(id);
		this.estadoItemAR = estado;
		this.codigo = codigo;
		this.observacaoARE = observacaoARE;
		
		this.procedimento = new ProcedimentoTRS(id, codigoProcedimento, descricaoProcedimento, tabela);
		
		this.ar = new AutorizacaoRessarcimento();
		this.ar.setOrganizacaoMilitar(new OrganizacaoMilitar());
		this.ar.getOrganizacaoMilitar().setSigla(siglaOM);
		this.ar.setCodigo(codigoARE);
		this.ar.setDivisao(divisao);
		this.ar.setEstado(estadoARE);
		this.ar.setDataGeracao(dataGeracaoARE);
		this.ar.setDataEmissao(dataEmissaoARE);
		this.ar.setDataApresentacao(dataApresentacaoARE);
		this.ar.setBeneficiario(new Beneficiario());
		this.ar.getBeneficiario().setNome(nomeBeneficiario);
		this.ar.getBeneficiario().setDataNascimento(dataNascimentoBeneficiario);
		this.ar.getBeneficiario().setTitular(titular);
		if (ar.getBeneficiario().isTitular()) {
			this.ar.getBeneficiario().setSaram(saram);
			this.ar.getBeneficiario().setOrganizacaoMilitar(new OrganizacaoMilitar(omBeneficiario));			
		} else {
			this.ar.getBeneficiario().setBeneficiarioTitular(new Beneficiario());
			this.ar.getBeneficiario().getBeneficiarioTitular().setOrganizacaoMilitar(new OrganizacaoMilitar(omTitular));
			this.ar.getBeneficiario().getBeneficiarioTitular().setSaram(saramTitular);
		}
		this.dataNotaFiscal = dataNotaFiscal;
		this.cpfCnpjPrestador = cpfCnpjPrestador;
		this.numeroNotaFiscal = numeroNotaFiscal;
		this.ar.setAuditoriaProspectiva(new AuditoriaProspectivaRessarcimento());
		this.ar.getAuditoriaProspectiva().setSolicitacao(new SolicitacaoRessarcimento());
		this.setAuditoriaRetrospectiva(new AuditoriaRetrospectivaRessarcimento());
		this.getAuditoriaRetrospectiva().setValorApresentado(valorApresentado);
		this.getAuditoriaRetrospectiva().setValorEstimado(valorEstimado);
		this.getAuditoriaRetrospectiva().setValorRessarcimento(valorRessarcimento);
	}
	
	// Construtor lista Itens para Lote 
	public ItemAR(Integer id, String codigo, EstadoItemAR estado, 
			Double valorRessarcimento, Date dataEmissao, EstadoAR estadoARE, Divisao divisao,
			boolean titular, Integer idBeneficiario, String nomeBeneficiario, String saram, Integer idBeneficiarioTitular, String nomeBeneficiarioTitular, String saramBeneficiarioTitular){	
		super();
		this.id = id;
		this.codigo = codigo;
		
		this.estadoItemAR = estado;
		
		this.auditoriaRetrospectiva = new AuditoriaRetrospectivaRessarcimento();
		this.auditoriaRetrospectiva.setValorRessarcimento(valorRessarcimento);
		
		this.ar = new AutorizacaoRessarcimento();
		this.ar.setDataEmissao(dataEmissao);
		this.ar.setBeneficiario(new Beneficiario());
		this.ar.getBeneficiario().setTitular(titular);
		this.ar.setDivisao(divisao);
		if(ar.getBeneficiario().isTitular()){
			this.ar.setBeneficiario(new Beneficiario());
			this.ar.getBeneficiario().setId(idBeneficiario);
			this.ar.getBeneficiario().setNome(nomeBeneficiario);
			this.ar.getBeneficiario().setSaram(saram);
		}else{
			this.ar.getBeneficiario().setBeneficiarioTitular(new Beneficiario());
			this.ar.getBeneficiario().getBeneficiarioTitular().setId(idBeneficiarioTitular);
			this.ar.getBeneficiario().getBeneficiarioTitular().setNome(nomeBeneficiarioTitular);
			this.ar.getBeneficiario().getBeneficiarioTitular().setSaram(saramBeneficiarioTitular);
		}
		
	}
	

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}	

	@NotEmpty(message = "Código do item da ARE precisa ser preenchido")
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@NotNull(message = "Campo estado é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "estado")
	public EstadoItemAR getEstadoItemAR() {
		return estadoItemAR;
	}

	public void setEstadoItemAR(EstadoItemAR estadoItemAR) {
		this.estadoItemAR = estadoItemAR;
	}

	@NotNull
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_ar")
	public AutorizacaoRessarcimento getAr() {
		return ar;
	}

	@NotNull
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_procedimento_base")
	public ProcedimentoBase getProcedimento() {
		return procedimento;
	}

	public void setProcedimento(ProcedimentoBase procedimento) {
		this.procedimento = procedimento;
	}

	public void setAr(AutorizacaoRessarcimento ar) {
		this.ar = ar;
	}
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_especialidade")
	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	@OneToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="id_auditoria_retrospectiva")
	public AuditoriaRetrospectivaRessarcimento getAuditoriaRetrospectiva() {
		return auditoriaRetrospectiva;
	}

	public void setAuditoriaRetrospectiva(AuditoriaRetrospectivaRessarcimento auditoriaRetrospectiva) {
		this.auditoriaRetrospectiva = auditoriaRetrospectiva;
	}

	@Transient
	public Date getDataNotaFiscal() {
		return dataNotaFiscal;
	}

	public void setDataNotaFiscal(Date dataNotaFiscal) {
		this.dataNotaFiscal = dataNotaFiscal;
	}

	@Transient
	public String getCpfCnpjPrestador() {
		return cpfCnpjPrestador;
	}

	public void setCpfCnpjPrestador(String cpfCnpjPrestador) {
		this.cpfCnpjPrestador = cpfCnpjPrestador;
	}

	@Transient
	public String getNumeroNotaFiscal() {
		return numeroNotaFiscal;
	}

	public void setNumeroNotaFiscal(String numeroNotaFiscal) {
		this.numeroNotaFiscal = numeroNotaFiscal;
	}

	@Override
	@Transient
	public int compareTo(ItemAR comparado) {
		return this.getCodigo().compareTo(comparado.getCodigo());
	}

	@Transient
	public String getNumeroItem(){
		return codigo.substring(codigo.length() - 3);
	}

	@OneToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "id_resposta_ressarcimento_auditoria_origem")
	public RespostaRessarcimentoAuditoria getRespostaRessarcimentoAuditoria() {
		return respostaRessarcimentoAuditoria;
	}

	public void setRespostaRessarcimentoAuditoria(RespostaRessarcimentoAuditoria respostaRessarcimentoAuditoria) {
		this.respostaRessarcimentoAuditoria = respostaRessarcimentoAuditoria;
	}

	@Column(name = "observacao_are")
	public String getObservacaoARE() {
		return observacaoARE;
	}

	public void setObservacaoARE(String observacaoARE) {
		this.observacaoARE = observacaoARE;
	}

	public Integer getDente() {
		return dente;
	}

	public void setDente(Integer dente) {
		this.dente = dente;
	}

	@Column(name = "face_dental")
	@Enumerated(EnumType.STRING)
	public FaceDental getFaceDental() {
		return faceDental;
	}

	public void setFaceDental(FaceDental faceDental) {
		this.faceDental = faceDental;
	}

}
