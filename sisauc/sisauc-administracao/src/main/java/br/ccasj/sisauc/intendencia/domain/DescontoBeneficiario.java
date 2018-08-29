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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "desconto_beneficiario", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class DescontoBeneficiario extends EntidadeGenerica {

	private static final long serialVersionUID = -3596885419625960809L;
	
	private static final String SEQ_NAME = "desconto_beneficiario_id_seq";

	private String numero;
	private double valorTotal;
	private Date dataEmissao;
	private Beneficiario beneficiario;
	private Set<ItemGAB> itensGabDescontados;
	private EstadoRelatorioFolhaBeneficiario estadoDescontoBeneficiario;
	private Set<EnvioDesconto> enviosDesconto;
	
	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return super.getId();
	}
	
	public DescontoBeneficiario(){
		super();
	}

	@NotEmpty(message = "Número do desconto precisa ser preenchido")
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
	@Column(name="data_emissao")
	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

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

	@OneToMany
	@JoinTable(name = "desconto_beneficiario_itens", schema = EntidadeGenerica.SCHEMA_SISAUC, 
		joinColumns =  @JoinColumn(name = "id_desconto_beneficiario"),
		inverseJoinColumns = @JoinColumn(name = "id_item_gab")
	)
	public Set<ItemGAB> getItensGabDescontados() {
		return itensGabDescontados;
	}

	public void setItensGabDescontados(Set<ItemGAB> itensGabDescontados) {
		this.itensGabDescontados = itensGabDescontados;
	}
	
	public void addItensGabDescontado(ItemGAB itensGabDescontados) {
		if (this.itensGabDescontados  == null) { 
			this.itensGabDescontados  = new HashSet<ItemGAB>();
		}
		this.itensGabDescontados.add(itensGabDescontados);
	}

	
	@Column(name="estado_desconto_beneficiario")
	@Enumerated(EnumType.STRING)
	public EstadoRelatorioFolhaBeneficiario getEstadoDescontoBeneficiario() {
		return estadoDescontoBeneficiario;
	}

	public void setEstadoDescontoBeneficiario(
			EstadoRelatorioFolhaBeneficiario estadoDescontoBeneficiario) {
		this.estadoDescontoBeneficiario = estadoDescontoBeneficiario;
	}

	@OneToMany(mappedBy = "descontoBeneficiario", cascade = CascadeType.MERGE)
//	@OneToMany(cascade = CascadeType.MERGE)
//	@JoinColumn(name = "id_desconto_beneficiario")
	public Set<EnvioDesconto> getEnviosDesconto() {
		return enviosDesconto;
	}

	public void setEnviosDesconto(Set<EnvioDesconto> enviosDesconto) {
		this.enviosDesconto = enviosDesconto;
	}

	@Transient
	public void addEnvioDesconto(EnvioDesconto envio) {
		if (this.enviosDesconto == null) {
			this.enviosDesconto = new HashSet<EnvioDesconto>();
		}
		this.enviosDesconto.add(envio);
		envio.setDescontoBeneficiario(this);
	}
	
}
