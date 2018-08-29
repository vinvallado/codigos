package br.ccasj.sisauc.intendencia.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.administracao.cadastro.domain.Convenio;
import br.ccasj.sisauc.administracao.cadastro.domain.PostoGraduacao;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.AuditoriaRetrospectiva;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;
import br.ccasj.sisauc.framework.domain.Usuario;

@Entity
@Table(name = "relatorio_desconto_beneficiario", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class RelatorioDescontoBeneficiario extends EntidadeGenerica implements RelatorioFolhaBeneficiario {

	private static final long serialVersionUID = -5386400747045539325L;
	
	private static final String SEQ_NAME = "relatorio_desconto_beneficiario_id_seq";
	
	private String codigo;
	private Usuario autor;
	private Date dataGeracao;
	private Set<RelatorioDescontoBeneficiarioItem> itens = new HashSet<RelatorioDescontoBeneficiarioItem>();
	private Set<ItemGAB> itensIsentos = new HashSet<ItemGAB>();
	private boolean cancelado = false;
	private Date dataInicioEnvio;
	private Date dataFinalizacaoEnvio;
	private EstadoRelatorioFolhaBeneficiario estadoEnvioFolha;
	
	public RelatorioDescontoBeneficiario() {
		
	}
	
	public RelatorioDescontoBeneficiario(Integer id, String codigo, Date dataGeracao) {
		super(id);
		this.codigo = codigo;
		this.dataGeracao = dataGeracao;
	}
	
	public RelatorioDescontoBeneficiario(Integer id, String codigo, String nomeAutor, String loginAutor, Date dataGeracao, boolean cancelado, EstadoRelatorioFolhaBeneficiario estadoEnvioFolha){
		super(id);
		this.codigo = codigo;
		this.autor = new Usuario();
		this.autor.setNome(nomeAutor);
		this.autor.setLogin(loginAutor);
		this.dataGeracao = dataGeracao;
		this.cancelado = cancelado;
		this.estadoEnvioFolha = estadoEnvioFolha;
	}
	
	

	
	//Construtor da query para gerar o relatorio de desconto de beneficiário
	public RelatorioDescontoBeneficiario(
			Integer id, String codigo, 
			Date dataGeracao,Integer itemId, 
			String itemCodigo,EstadoEnvioFolhaPagamento estadoItem, 
			Double valorDescontoEnviadoItem,Usuario autor, 
			Double valorFinalAuditoria,	String beneficiarioSaram, 
			String beneficiarioNome, Boolean beneficiarioIsTitular, 
			String convenioSigla,String siglaPostoGraduacao, 
			String omSigla, String beneficiarioTitularSaram, 
			String beneficiarioTitularNome,String siglaTitularPostoGraduacao, 
			String omTitularSigla, Integer itemRelatId, String cdParentesco){
		
		this(id, codigo, dataGeracao);
		this.setAutor(autor);
		this.itens = new HashSet<RelatorioDescontoBeneficiarioItem>();
		if(itemId != null){
			ItemGAB itemGAB = new ItemGAB(itemId, itemCodigo);
			itemGAB.setAuditoriaRetrospectiva(new AuditoriaRetrospectiva());
			itemGAB.getAuditoriaRetrospectiva().setValorFinal(valorFinalAuditoria);
			Beneficiario beneficiario = new Beneficiario(null, beneficiarioSaram, beneficiarioNome);
			if(omSigla != null){
				beneficiario.setOrganizacaoMilitar(new OrganizacaoMilitar(omSigla));
			}
			beneficiario.setConvenio(new Convenio(convenioSigla));
			beneficiario.setTitular(beneficiarioIsTitular);
			beneficiario.setCdParentesco(cdParentesco);
			if(siglaPostoGraduacao != null) {
				beneficiario.setPostoGraduacao(new PostoGraduacao(siglaPostoGraduacao));				
			}
			if(!beneficiario.isTitular()){
				beneficiario.setBeneficiarioTitular(new Beneficiario(null, beneficiarioTitularSaram, beneficiarioTitularNome));
				if(omTitularSigla!=null){
					beneficiario.getBeneficiarioTitular().setOrganizacaoMilitar(new OrganizacaoMilitar(omTitularSigla));				
				}
				beneficiario.getBeneficiarioTitular().setPostoGraduacao(new PostoGraduacao(siglaTitularPostoGraduacao));				
			}
			itemGAB.setGab(new GuiaApresentacaoBeneficiario());
			itemGAB.getGab().setBeneficiario(beneficiario);
			RelatorioDescontoBeneficiarioItem item = new RelatorioDescontoBeneficiarioItem();
			item.setId(itemRelatId);
			item.setRelatorioDescontoBeneficiario(this);
			item.setItemGab(itemGAB);
			item.setEstado(estadoItem != null ? EstadoEnvioFolhaPagamento.valueOf(estadoItem.name()) : null);
			item.setValorDescontoEnviado(valorDescontoEnviadoItem);
			
			
			this.itens.add(item);
		}
	}
	
	//Construtor da query para gerar o relatorio de desconto de beneficiário
	public RelatorioDescontoBeneficiario(Integer id, String codigo, Date dataGeracao, 
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
		this.itensIsentos = new HashSet<ItemGAB>();
		if(itemIsentoId != null){
			ItemGAB itemIsentoGAB = new ItemGAB(itemIsentoId, itemIsentoCodigo);
			Beneficiario beneficiarioIsento = new Beneficiario(null, beneficiarioIsentoSaram, beneficiarioIsentoNome);
			if(omIsentoSigla != null){
				beneficiarioIsento.setOrganizacaoMilitar(new OrganizacaoMilitar(omIsentoSigla));
			}
			beneficiarioIsento.setTitular(beneficiarioIsentoIsTitular);
			beneficiarioIsento.setConvenio(new Convenio(convenioSigla));
			beneficiarioIsento.setPostoGraduacao(new PostoGraduacao(beneficiarioIsentoPostoGraduacao));
			if(!beneficiarioIsento.isTitular()){
				beneficiarioIsento.setBeneficiarioTitular(new Beneficiario(null, beneficiarioTitularIsentoSaram, beneficiarioTitularIsentoNome));
				if(omTitularIsentoSigla != null){
					beneficiarioIsento.getBeneficiarioTitular().setOrganizacaoMilitar(new OrganizacaoMilitar(omTitularIsentoSigla));				
				}
				beneficiarioIsento.getBeneficiarioTitular().setPostoGraduacao(new PostoGraduacao(beneficiarioTitularIsentoPostoGraduacao));				
			}
			itemIsentoGAB.setGab(new GuiaApresentacaoBeneficiario());
			itemIsentoGAB.getGab().setBeneficiario(beneficiarioIsento);
			itemIsentoGAB.setId(itemIsentoId);
			this.itensIsentos.add(itemIsentoGAB);
		}
		
	}	
	
	@Transient
	@Override
	public boolean isDebito() {
		return true;
	}
	
	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return super.getId();
	}
	
	@Column(nullable=false, unique=true)
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@ManyToOne
	@JoinColumn(name="id_autor", nullable=false)
	public Usuario getAutor() {
		return autor;
	}
	
	public void setAutor(Usuario autor) {
		this.autor = autor;
	}
	
	@Column(name="data_geracao", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataGeracao() {
		return dataGeracao;
	}
	
	public void setDataGeracao(Date dataGeracao) {
		this.dataGeracao = dataGeracao;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name = "id_relatorio_desconto_beneficiario")
	public Set<RelatorioDescontoBeneficiarioItem> getItens() {
		return itens; 
	}
	
	@Transient
	@Override
	public Set<RelatorioFolhaBeneficiarioItem> getItensEnvio(){
		return new HashSet<RelatorioFolhaBeneficiarioItem>(getItens()); 
	}

	
	public void setItens(Set<RelatorioDescontoBeneficiarioItem> itens) {
		this.itens = itens;
	}

	@OneToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "relatorio_desconto_beneficiario_itens_isentos", schema = EntidadeGenerica.SCHEMA_SISAUC,  
		joinColumns = @JoinColumn(name="id_relatorio_desconto_beneficiario"),
		inverseJoinColumns = @JoinColumn(name="id_item"))
	public Set<ItemGAB> getItensIsentos() {
		return itensIsentos;
	}

	public void setItensIsentos(Set<ItemGAB> itensIsentos) {
		this.itensIsentos = itensIsentos;
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
		for (RelatorioDescontoBeneficiarioItem item: this.itens){
			ItemGAB itemGAB = item.getItemGab();
			soma += itemGAB.getAuditoriaRetrospectiva().getValorFinal() * 0.2;
		}
		return soma;
	}

	@Column(name="data_hora_inicio_envio")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataInicioEnvio() {
		return dataInicioEnvio;
	}

	public void setDataInicioEnvio(Date dataInicioEnvio) {
		this.dataInicioEnvio = dataInicioEnvio;
	}

	@Column(name="data_hora_fim_envio")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataFinalizacaoEnvio() {
		return dataFinalizacaoEnvio;
	}

	public void setDataFinalizacaoEnvio(Date dataFinalizacaoEnvio) {
		this.dataFinalizacaoEnvio = dataFinalizacaoEnvio;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "estado")
	public EstadoRelatorioFolhaBeneficiario getEstadoEnvioFolha() {
		return estadoEnvioFolha;
	}

	public void setEstadoEnvioFolha(EstadoRelatorioFolhaBeneficiario estadoEnvioFolha) {
		this.estadoEnvioFolha = estadoEnvioFolha;
	}

}
