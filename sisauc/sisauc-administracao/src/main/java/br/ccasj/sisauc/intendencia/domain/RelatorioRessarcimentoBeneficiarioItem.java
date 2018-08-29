package br.ccasj.sisauc.intendencia.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.ccasj.sisauc.emissaoar.domain.ItemAR;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;


//@Entity
//@Table(name="relatorio_desconto_beneficiario_itens", schema = EntidadeGenerica.SCHEMA_SISAUC)
public class RelatorioRessarcimentoBeneficiarioItem implements Serializable, RelatorioFolhaBeneficiarioItem {

	private static final long serialVersionUID = 1L;

	private EstadoEnvioFolhaPagamento estado;
	
	private Double valorDescontoEnviado;
	
	private ItemAR itemAR;
	

	
	//	@Enumerated(EnumType.STRING)
	public EstadoEnvioFolhaPagamento getEstado() {
		return estado;
	}
	
//	@Column(name="valor_desconto_enviado")
	public Double getValorDescontoEnviado() {
		return valorDescontoEnviado;
	}
	
	
	public void setEstado(EstadoEnvioFolhaPagamento estado) {
		this.estado = estado;
	}
	
	public void setValorDescontoEnviado(Double valorDescontoEnviado) {
		this.valorDescontoEnviado = valorDescontoEnviado;
	}

	@Override
	public String getCodigoExternoSisconsig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getValorFolhaBeneficiario() {
		return itemAR.getAuditoriaRetrospectiva().getValorRessarcimento();
	}
	
	public ItemAR getItemAR() {
		return itemAR;
	}

	public void setItemAR(ItemAR itemAR) {
		this.itemAR = itemAR;
	}

	
	
}
