package br.ccasj.sisauc.auditoriaretrospectiva.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;

import br.ccasj.sisauc.administracao.cadastro.domain.Credenciado;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;

@Entity
@Table(name = "lote", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class Lote extends EntidadeGenerica {

	private static final long serialVersionUID = -3596885419625960809L;
	
	private static final String SEQ_NAME = "lote_id_seq";

	private String numero;
	private double valorTotal;
	private Date dataCriacao;
	private boolean conformidade;
	private boolean cancelado = false;
	private Credenciado credenciado;
	private OrganizacaoMilitar organizacaoMilitar;
	private NotaFiscal notaFiscal;
	private Set<ItemGAB> itensGab;

	public Lote(Integer id, String numero, double valorTotal, Credenciado credenciado, String numeroNotaFiscal, boolean cancelado) {
		super(id);
		this.numero = numero;
		this.valorTotal = valorTotal;
		this.credenciado = credenciado;
		this.cancelado = cancelado;
		
		NotaFiscal notaFiscal = new NotaFiscal();
		notaFiscal.setNumero(numeroNotaFiscal);
		this.setNotaFiscal(notaFiscal);
	}
	
	public Lote(Integer id, String numero, double valorTotal, Credenciado credenciado) {
		super(id);
		this.numero = numero;
		this.valorTotal = valorTotal;
		this.credenciado = credenciado;
	}
	
	public Lote(Integer idLote, String numeroLote, String numeroNotaFiscal, String nomeFantasiaCredenciado, String nomeOM, String siglaOM) {
		super(idLote);
		this.numero = numeroLote;
		
		NotaFiscal notaFiscal = new NotaFiscal();
		notaFiscal.setNumero(numeroNotaFiscal);
		this.setNotaFiscal(notaFiscal);
		
		Credenciado credenciado = new Credenciado();
		credenciado.setNomeFantasia(nomeFantasiaCredenciado);
		this.setCredenciado(credenciado);
		
		OrganizacaoMilitar om = new OrganizacaoMilitar();
		om.setNome(nomeOM);
		om.setSigla(siglaOM);
		this.setOrganizacaoMilitar(om);
	}
	
	public Lote() {
		super();
	}	

	public Lote(Integer id, Set<ItemGAB> itensGab) {
		super();
		this.id = id;
		this.itensGab = itensGab;
	}

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return super.getId();
	}

	@NotEmpty(message = "Número do lote precisa ser preenchido")
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Column(name="valor_total")
	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_criacao")
	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public boolean isConformidade() {
		return conformidade;
	}

	public void setConformidade(boolean conformidade) {
		this.conformidade = conformidade;
	}

	public boolean isCancelado() {
		return cancelado;
	}

	public void setCancelado(boolean cancelado) {
		this.cancelado = cancelado;
	}

	@ManyToOne
	@JoinColumn(name = "id_credenciado")
	public Credenciado getCredenciado() {
		return credenciado;
	}

	public void setCredenciado(Credenciado credenciado) {
		this.credenciado = credenciado;
	}

	@ManyToOne
	@JoinColumn(name="id_organizacao_militar")
	public OrganizacaoMilitar getOrganizacaoMilitar() {
		return organizacaoMilitar;
	}

	public void setOrganizacaoMilitar(OrganizacaoMilitar organizacaoMilitar) {
		this.organizacaoMilitar = organizacaoMilitar;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="id_nota_fiscal")
	public NotaFiscal getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(NotaFiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	@OneToMany
	@JoinTable(name = "lote_itens", schema = EntidadeGenerica.SCHEMA_SISAUC, 
		joinColumns =  @JoinColumn(name = "id_lote"),
		inverseJoinColumns = @JoinColumn(name = "id_item_gab")
	)
	public Set<ItemGAB> getItensGab() {
		return itensGab;
	}

	public void setItensGab(Set<ItemGAB> itensGab) {
		this.itensGab = itensGab;
	}

}
