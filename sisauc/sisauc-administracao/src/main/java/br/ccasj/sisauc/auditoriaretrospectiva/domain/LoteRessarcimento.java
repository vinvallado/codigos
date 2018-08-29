package br.ccasj.sisauc.auditoriaretrospectiva.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import org.hibernate.validator.constraints.NotEmpty;

import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.emissaoar.domain.ItemAR;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;

@Entity
@Table(name = "lote_ressarcimento", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class LoteRessarcimento extends EntidadeGenerica {

	private static final long serialVersionUID = 2423120636183987796L;

	private static final String SEQ_NAME = "lote_ressarcimento_id_seq";

	private String numero;
	private double valorTotalRessarcir;
	private Date dataCriacao;
	private Beneficiario beneficiario;
	private OrganizacaoMilitar organizacaoMilitar;
	private Set<ItemAR> itensAr;
	@Column(name = "cancelado")
	private boolean cancelado;

	public LoteRessarcimento(Integer id, String numero, double valorTotalRessarcir, Beneficiario beneficiario,
			boolean cancelado) {
		super(id);
		this.numero = numero;
		this.valorTotalRessarcir = valorTotalRessarcir;
		this.beneficiario = beneficiario;
		this.cancelado = cancelado; 
	}

	public LoteRessarcimento(Integer id, String numero, double valorTotalRessarcir, Integer idBeneficiario, String nomeBeneficiario,
			boolean cancelado) {
		super(id);
		this.numero = numero;
		this.valorTotalRessarcir = valorTotalRessarcir;
		
		this.beneficiario = new Beneficiario();
		this.beneficiario.setId(idBeneficiario);
		this.beneficiario.setNome(nomeBeneficiario);
		this.cancelado = cancelado;
	}
	
	public LoteRessarcimento(Integer idLote, String numeroLote, String nomeBeneficiario, String nomeOM,
			String siglaOM) {
		super(idLote);
		this.numero = numeroLote;

		Beneficiario beneficiario = new Beneficiario();
		beneficiario.setNome(nomeBeneficiario);
		this.setBeneficiario(beneficiario);

		OrganizacaoMilitar om = new OrganizacaoMilitar();
		om.setNome(nomeOM);
		om.setSigla(siglaOM);
		this.setOrganizacaoMilitar(om);
	}

	public LoteRessarcimento() {
		super();
	}

	public LoteRessarcimento(Integer id, Set<ItemAR> itensAr) {
		super();
		this.id = id;
		this.itensAr = itensAr;
	}

	@Override
	public String toString() {
		return "LoteRessarcimento [numero=" + numero + "]";
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

	@Column(name = "valor_total_ressarcir")
	public double getValorTotalRessarcir() {
		return valorTotalRessarcir;
	}

	public void setValorTotalRessarcir(double valorTotalRessarcir) {
		this.valorTotalRessarcir = valorTotalRessarcir;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao")
	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@ManyToOne
	@JoinColumn(name = "id_beneficiario")
	public Beneficiario getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}

	@ManyToOne
	@JoinColumn(name = "id_organizacao_militar")
	public OrganizacaoMilitar getOrganizacaoMilitar() {
		return organizacaoMilitar;
	}

	public void setOrganizacaoMilitar(OrganizacaoMilitar organizacaoMilitar) {
		this.organizacaoMilitar = organizacaoMilitar;
	}

	@OneToMany
	@JoinTable(name = "lote_ressarcimento_itens_ar", schema = EntidadeGenerica.SCHEMA_SISAUC, joinColumns = @JoinColumn(name = "id_lote_ressarcimento"), inverseJoinColumns = @JoinColumn(name = "id_item_ar"))
	public Set<ItemAR> getItensAr() {
		return itensAr;
	}

	public void setItensAr(Set<ItemAR> itensAr) {
		this.itensAr = itensAr;
	}
	
	
	public boolean getCancelado() {
		return cancelado;
	}

	public void setCancelado(boolean cancelado) {
		this.cancelado = cancelado;
	}

}
