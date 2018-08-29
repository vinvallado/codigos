package br.ccasj.sisauc.intendencia.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;


@Entity
@Table(name="relatorio_desconto_beneficiario_itens", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class RelatorioDescontoBeneficiarioItem extends EntidadeGenerica implements RelatorioFolhaBeneficiarioItem{

	private static final long serialVersionUID = 1L;

	private static final String SEQ_NAME="relatorio_desconto_beneficiario_itens_id_seq";
	
	private EstadoEnvioFolhaPagamento estado;
	
	private Double valorDescontoEnviado;
	
	private RelatorioDescontoBeneficiario relatorioDescontoBeneficiario;
	
	private ItemGAB itemGab;
	
	private Integer idDescontoGerado;
	
	private Integer codigoErro;
	
	private String mensagemErro;
	
	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId(){
		return id;
	}
	
	@ManyToOne
	@JoinColumn(name="id_relatorio_desconto_beneficiario")
	public RelatorioDescontoBeneficiario getRelatorioDescontoBeneficiario() {
		return relatorioDescontoBeneficiario;
	}

	@ManyToOne
	@JoinColumn(name="id_item")
	public ItemGAB getItemGab() {
		return itemGab;
	}
	
	@Enumerated(EnumType.STRING)
	public EstadoEnvioFolhaPagamento getEstado() {
		return estado;
	}
	
	@Column(name="valor_desconto_enviado")
	public Double getValorDescontoEnviado() {
		return valorDescontoEnviado;
	}
	
	@Column(name="id_desconto_gerado")
	public Integer getIdDescontoGerado() {
		return idDescontoGerado;
	}
	
	@Column(name="codigo_erro_envio")
	public Integer getCodigoErro(){
		return this.codigoErro;
	}

	@Column(name="mensagem_erro",length=180)
	public String getMensagemErro(){
		return this.mensagemErro;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((itemGab == null) ? 0 : itemGab.hashCode());
		result = prime * result
				+ ((relatorioDescontoBeneficiario == null) ? 0 : relatorioDescontoBeneficiario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelatorioDescontoBeneficiarioItem other = (RelatorioDescontoBeneficiarioItem) obj;
		if (itemGab == null) {
			if (other.itemGab != null)
				return false;
		} else if (!itemGab.equals(other.itemGab))
			return false;
		if (relatorioDescontoBeneficiario == null) {
			if (other.relatorioDescontoBeneficiario != null)
				return false;
		} else if (!relatorioDescontoBeneficiario.equals(other.relatorioDescontoBeneficiario))
			return false;
		return true;
	}

	@Transient
	@Override
	public String getCodigoExternoSisconsig() {
		StringBuilder codigo = new StringBuilder();
		codigo.append("SISAUC-");
		codigo.append(this.getItemGab().getCodigo());
		codigo.append("-");
		codigo.append(this.getItemGab().getId());
		return codigo.toString();
	}

	@Transient
	@Override
	public double getValorFolhaBeneficiario() {
		return this.getItemGab().getAuditoriaRetrospectiva().getValorFinal();
	}
	
	public void setEstado(EstadoEnvioFolhaPagamento estado) {
		this.estado = estado;
	}
	
	public void setValorDescontoEnviado(Double valorDescontoEnviado) {
		this.valorDescontoEnviado = valorDescontoEnviado;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setRelatorioDescontoBeneficiario(RelatorioDescontoBeneficiario relatorioDescontoBeneficiario) {
		this.relatorioDescontoBeneficiario = relatorioDescontoBeneficiario;
	}

	public void setItemGab(ItemGAB itemGab) {
		this.itemGab = itemGab;
	}

	public void setIdDescontoGerado(Integer idDescontoGerado) {
		this.idDescontoGerado = idDescontoGerado;
	}

	public void setCodigoErro(Integer codigoErro) {
		this.codigoErro = codigoErro;
	}

	public void setMensagemErro(String mensagemErro) {
		this.mensagemErro = mensagemErro;
	}

}
