package br.ccasj.sisauc.emissaoar.domain;

import java.util.ArrayList;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;

import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoTRS;
import br.ccasj.sisauc.administracao.cadastro.domain.Tabela;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectivaRessarcimento;
import br.ccasj.sisauc.auditoriaretrospectiva.dao.GerenciarLoteRessarcimentoDAO;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.domain.EntidadeDivisaoGenerica;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;
import br.ccasj.sisauc.framework.domain.Usuario;

@Entity
@Table(name = "ar", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class AutorizacaoRessarcimento extends EntidadeDivisaoGenerica {

	private static final long serialVersionUID = 6875282501131436950L;

	private static final String SEQ_NAME = "ar_id_seq";
	
	private String codigo;
	private String justificativaCancelamentoAR;
	private Date dataGeracao;
	private Date dataEmissao;
	private Date dataApresentacao;
	private Beneficiario beneficiario;
	private AuditoriaProspectivaRessarcimento auditoriaProspectiva;
	private ApresentacaoAutorizacaoRessarcimento apresentacao;
	private EstadoAR estado;
	private OrganizacaoMilitar organizacaoMilitar;
	private boolean isento;
	private Set<ItemAR> itens = new HashSet<ItemAR>();
	private String motivoInconsistencia;
	private Date dataNotaFiscal;
	private String cpfCnpjPrestador;
	private String numeroNotaFiscal;
	private ProcedimentoTRS procedimento;
	private Usuario usuarioEmissorAr;

	@Autowired
	private GerenciarLoteRessarcimentoDAO loteRessarcimentoDAO;
	
	public AutorizacaoRessarcimento() {
		super();
	}

	public AutorizacaoRessarcimento(Integer id) {
		super(id);
	}
	
	public AutorizacaoRessarcimento(Integer id, String codigo) {
		super(id);
		this.codigo = codigo;
	}

	public AutorizacaoRessarcimento(Integer id, Divisao divisao, String codigo, Date dataEmissao, Date dataApresentacao,
			EstadoAR estado, String nomeBeneficiario, String saramBeneficiario, String saramBeneficiarioTitular) {
		this.id = id;
		this.divisao = divisao;
		this.codigo = codigo;
		this.dataEmissao = dataEmissao;
		this.dataApresentacao = dataApresentacao;
		this.estado = estado;
		this.beneficiario = new Beneficiario();
		this.beneficiario.setNome(nomeBeneficiario);
		this.beneficiario.setSaramTitular(saramBeneficiarioTitular != null ? saramBeneficiarioTitular : saramBeneficiario);
	}

	private String numeroLote ;

	@Transient
	public String getNumeroLote(){
		return this.numeroLote;
	}
	
	public void setNumeroLote(String numeroLote) {
		this.numeroLote = numeroLote;
	}

	public AutorizacaoRessarcimento(Integer id, Divisao divisao, String codigo, Date dataEmissao,
			EstadoAR estado, String nomeBeneficiario, String saramBeneficiario, String saramBeneficiarioTitular, String numeroLote) {
		this.id = id;
		this.divisao = divisao;
		this.codigo = codigo;
		this.dataEmissao = dataEmissao;
		this.estado = estado;
		this.beneficiario = new Beneficiario();
		this.beneficiario.setNome(nomeBeneficiario);
		this.beneficiario.setSaramTitular(saramBeneficiarioTitular != null ? saramBeneficiarioTitular : saramBeneficiario);
		this.numeroLote = numeroLote;
	}
	
	public AutorizacaoRessarcimento(Integer id, Divisao divisao,String codigo, Date dataGeracao, EstadoAR estado,
			String nomeBeneficiario, String saramBeneficiario, String saramTitutlar) {
		super();
		this.id = id;
		this.divisao = divisao;
		this.codigo = codigo;
		this.dataGeracao = dataGeracao;
		this.estado = estado;
		
		Beneficiario beneficiario = new Beneficiario();
		beneficiario.setNome(nomeBeneficiario);
		beneficiario.setSaram(saramBeneficiario);
		beneficiario.setSaramTitular(saramTitutlar);
		this.beneficiario = beneficiario;
	}

	public AutorizacaoRessarcimento(Integer id, String codigo, Date dataGeracao, Date dataEmissao, EstadoAR estado,
			String justificativaCancelamentoAR) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.dataGeracao = dataGeracao;
		this.dataEmissao = dataEmissao;
		this.estado = estado;
		this.justificativaCancelamentoAR = justificativaCancelamentoAR;
	}

	// Construtor da pesquisa de ARE
	public AutorizacaoRessarcimento(Integer id, String codigo, Divisao divisao, Date dataEmissao, Date dataApresentacao,
			EstadoAR estado, String nomeBeneficiario, String saramBeneficiario, boolean titular, String omBeneficiario,
			String omTitular, String siglaOM, Date dataNotaFiscal, String cpfCnpjPrestador, String numeroNotaFiscal,
			Tabela tabela, String codigoProcedimento, String descricaoProcedimento, String siglaEspecialidadeProcedimento) {
		this.id = id;
		this.codigo = codigo;
		this.divisao = divisao;
		this.dataEmissao = dataEmissao;
		this.dataApresentacao = dataApresentacao;
		this.estado = estado;
		this.beneficiario = new Beneficiario(null, saramBeneficiario, nomeBeneficiario);
		this.beneficiario.setTitular(titular);
		if (beneficiario.isTitular()) {
			this.beneficiario.setOrganizacaoMilitar(new OrganizacaoMilitar(omBeneficiario));
		} else {
			this.beneficiario.setBeneficiarioTitular(new Beneficiario());
			this.beneficiario.getBeneficiarioTitular().setOrganizacaoMilitar(new OrganizacaoMilitar(omTitular));
		}
		this.organizacaoMilitar = new OrganizacaoMilitar();
		this.organizacaoMilitar.setSigla(siglaOM);
		this.dataNotaFiscal = dataNotaFiscal;
		this.cpfCnpjPrestador = cpfCnpjPrestador;
		this.numeroNotaFiscal = numeroNotaFiscal;
		this.procedimento = new ProcedimentoTRS(id, codigoProcedimento, descricaoProcedimento, tabela);
	}

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}

	@Transient
	public List<String> getNumerosLotesARE(){
		return new ArrayList<String>();
	}
	
	@NotEmpty(message = "Campo código é obrigatório")
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@Column(name = "justificativa_cancelamento_ar")
	public String getJustificativaCancelamentoAR() {
		return justificativaCancelamentoAR;
	}

	public void setJustificativaCancelamentoAR(String justificativaCancelamentoAR) {
		this.justificativaCancelamentoAR = justificativaCancelamentoAR;
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
	@JoinColumns({ @JoinColumn(name = "id_beneficiario", referencedColumnName = "id"), })
	public Beneficiario getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}

	@ManyToOne
	@JoinColumn(name = "id_auditoria_prospectiva")
	public AuditoriaProspectivaRessarcimento getAuditoriaProspectiva() {
		return auditoriaProspectiva;
	}

	public void setAuditoriaProspectiva(AuditoriaProspectivaRessarcimento auditoriaProspectiva) {
		this.auditoriaProspectiva = auditoriaProspectiva;
	}

	@OneToOne(mappedBy="ar", cascade=CascadeType.MERGE)
	public ApresentacaoAutorizacaoRessarcimento getApresentacao() {
		return apresentacao;
	}

	public void setApresentacao(ApresentacaoAutorizacaoRessarcimento apresentacao) {
		this.apresentacao = apresentacao;
	}

	@NotNull(message = "Campo estado é obrigatório")
	@Enumerated(EnumType.STRING)
	public EstadoAR getEstado() {
		return estado;
	}

	public void setEstado(EstadoAR estado) {
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

	@OneToMany(mappedBy = "ar", cascade = CascadeType.MERGE)
	public Set<ItemAR> getItens() {
		return itens;
	}

	public void setItens(Set<ItemAR> itens) {
		this.itens = itens;
	}
	
	@Transient
	public List<ItemAR> getItensNaoNaoAprovados() {
		List<ItemAR> itensNaoNaoAprovados = new ArrayList<ItemAR>();
		for (ItemAR item: getItens()){
			if (!EstadoItemAR.NAO_APROVADO.equals(item.getEstadoItemAR())){
				itensNaoNaoAprovados.add(item);
			}
		}
		return itensNaoNaoAprovados;
	}

	// TODO remover esses métodos transientes
	@Transient
	public List<ItemAR> getItensList() {
		List<ItemAR> listaRetorno = new LinkedList<ItemAR>();

		TreeSet<ItemAR> treeSet = new TreeSet<ItemAR>(itens);
		Iterator<ItemAR> iterator = treeSet.iterator();
		while (iterator.hasNext()) {
			listaRetorno.add(iterator.next());
		}

		return listaRetorno;
	}
	
	@Column(name="motivo_inconsistencia")
	public String getMotivoInconsistencia() {
		return motivoInconsistencia;
	}

	public void setMotivoInconsistencia(String motivoInconsistencia) {
		this.motivoInconsistencia = motivoInconsistencia;
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

	@Transient
	public ProcedimentoTRS getProcedimento() {
		return procedimento;
	}

	public void setProcedimento(ProcedimentoTRS procedimento) {
		this.procedimento = procedimento;
	}

	@ManyToOne
	@JoinColumn(name = "id_usuario_emissor")
	public Usuario getUsuarioEmissorAr() {
		return usuarioEmissorAr;
	}

	public void setUsuarioEmissorAr(Usuario usuarioEmissorAr) {
		this.usuarioEmissorAr = usuarioEmissorAr;
	}

}
