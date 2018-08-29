package br.ccasj.sisauc.intendencia.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.administracao.cadastro.domain.Convenio;
import br.ccasj.sisauc.administracao.cadastro.domain.PostoGraduacao;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.AuditoriaRetrospectiva;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.AuditoriaRetrospectivaRessarcimento;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.LoteRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.ItemAR;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;
import br.ccasj.sisauc.framework.domain.Usuario;

//@Entity
//@Table(name = "relatorio_desconto_beneficiario", schema = EntidadeGenerica.SCHEMA_SISAUC)
//@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class RelatorioRessarcimentoBeneficiario extends EntidadeGenerica implements RelatorioFolhaBeneficiario{

	private static final long serialVersionUID = -5386400747045539325L;
	
	private static final String SEQ_NAME = "relatorio_ressarcimento_beneficiario_id_seq";
	
	private String codigo;
	private Usuario autor;
	private Date dataGeracao;
	private Set<RelatorioRessarcimentoBeneficiarioItem> itens = new HashSet<RelatorioRessarcimentoBeneficiarioItem>();
	private boolean cancelado = false;
	private EstadoRessarcimentoBeneficiario estado;
	
	public RelatorioRessarcimentoBeneficiario() {
		
	}
	
	public RelatorioRessarcimentoBeneficiario(Integer id, String codigo, Date dataGeracao) {
		super(id);
		this.codigo = codigo;
		this.dataGeracao = dataGeracao;
	}

	public RelatorioRessarcimentoBeneficiario(Integer id, String codigo, String nomeAutor, String loginAutor, Date dataGeracao, boolean cancelado){
		super(id);
		this.codigo = codigo;
		this.autor = new Usuario();
		this.autor.setNome(nomeAutor);
		this.autor.setLogin(loginAutor);
		this.dataGeracao = dataGeracao;
		this.cancelado = cancelado;
	}
	
	//Construtor da query para gerar o relatorio de desconto de beneficiário
//	public RelatorioRessarcimentoBeneficiario(Integer id, String codigo, Date dataGeracao, 
//			Integer itemId, String itemCodigo,
//			Usuario autor, 
//			Double valorFinalAuditoria,
//			String beneficiarioSaram, String beneficiarioNome, Boolean beneficiarioIsTitular, 
//			String convenioSigla, 
//			String siglaPostoGraduacao, 
//			String omSigla, 
//			String beneficiarioTitularSaram, String beneficiarioTitularNome,String siglaTitularPostoGraduacao, 
//			String omTitularSigla){
//		this(id, codigo, dataGeracao);
//		this.setAutor(autor);
//		this.itens = new HashSet<ItemAR>();
//		if(itemId != null){
//			ItemAR itemAre = new ItemAR();
//			itemAre.setAuditoriaRetrospectiva(new AuditoriaRetrospectivaRessarcimento());
//			itemAre.getAuditoriaRetrospectiva().setValorRessarcimento(valorFinalAuditoria);
//			Beneficiario beneficiario = new Beneficiario(null, beneficiarioSaram, beneficiarioNome);
//			if(omSigla != null){
//				beneficiario.setOrganizacaoMilitar(new OrganizacaoMilitar(omSigla));
//			}
//			beneficiario.setConvenio(new Convenio(convenioSigla));
//			beneficiario.setTitular(beneficiarioIsTitular);
//			if(siglaPostoGraduacao != null) {
//				beneficiario.setPostoGraduacao(new PostoGraduacao(siglaPostoGraduacao));				
//			}
//			if(!beneficiario.isTitular()){
//				beneficiario.setBeneficiarioTitular(new Beneficiario(null, beneficiarioTitularSaram, beneficiarioTitularNome));
//				if(omTitularSigla!=null){
//					beneficiario.getBeneficiarioTitular().setOrganizacaoMilitar(new OrganizacaoMilitar(omTitularSigla));				
//				}
//				beneficiario.getBeneficiarioTitular().setPostoGraduacao(new PostoGraduacao(siglaTitularPostoGraduacao));				
//			}
//			itemAre.setAr(new AutorizacaoRessarcimento());
//			itemAre.getAr().setBeneficiario(beneficiario);
//			this.itens.add(itemAre);
//		}
//	}
	
	//Construtor da query para gerar o relatorio de desconto de beneficiário
	public RelatorioRessarcimentoBeneficiario(Integer id, String codigo, Date dataGeracao, 
			Integer itemIsentoId, String itemIsentoCodigo,
			Usuario autor, 
			String beneficiarioIsentoSaram, String beneficiarioIsentoNome, Boolean beneficiarioIsentoIsTitular,
			String convenioSigla, 
			String beneficiarioIsentoPostoGraduacao, 
			String omIsentoSigla,
			String beneficiarioTitularIsentoSaram, String beneficiarioTitularIsentoNome, String beneficiarioTitularIsentoPostoGraduacao, 
			String omTitularIsentoSigla){
		this(id, codigo, dataGeracao);
		this.setAutor(autor);
	}	

	@Override
	public boolean isDebito() {
		return false;
	}
	
//	@Id
//	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
//	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
//	@Override
	public Integer getId() {
		return super.getId();
	}
	
//	@Column(nullable=false, unique=true)
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
//	@ManyToOne
//	@JoinColumn(name="id_autor", nullable=false)
	public Usuario getAutor() {
		return autor;
	}
	
	public void setAutor(Usuario autor) {
		this.autor = autor;
	}
	
//	@Column(name="data_geracao", nullable=false)
//	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataGeracao() {
		return dataGeracao;
	}
	
	public void setDataGeracao(Date dataGeracao) {
		this.dataGeracao = dataGeracao;
	}
	
//	@OneToMany(fetch=FetchType.LAZY)
//	@JoinTable(name = "relatorio_ressarcimento_beneficiario_item", schema = EntidadeGenerica.SCHEMA_SISAUC,  
//		joinColumns = @JoinColumn(name="id_relatorio_ressarcimento_beneficiario"),
//		inverseJoinColumns = @JoinColumn(name="id_item"))
	public Set<RelatorioRessarcimentoBeneficiarioItem> getItens() {
		return itens;
	}
	
	@Transient
	@Override
	public Set<RelatorioFolhaBeneficiarioItem> getItensEnvio(){
		return new HashSet<RelatorioFolhaBeneficiarioItem>(getItens()); 
	}
	
	public void setItens(Set<RelatorioRessarcimentoBeneficiarioItem> itens) {
		this.itens = itens;
	}

	
	public boolean isCancelado() {
		return cancelado;
	}

	public void setCancelado(boolean cancelado) {
		this.cancelado = cancelado;
	}
	
	@Transient
	public Double getValorTotalDesconto(){
		double soma = 0;
		for (RelatorioFolhaBeneficiarioItem item: this.itens){
			soma += item.getValorFolhaBeneficiario();
		}
		return soma;
	}

	

}
