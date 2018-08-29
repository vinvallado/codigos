package br.ccasj.sisauc.intendencia.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.ccasj.sisauc.emissaogab.domain.ItemGAB;

@Embeddable
public class RelatorioRessarcimentoBeneficiarioItemId implements Serializable {

	private static final long serialVersionUID = 3574084766233933624L;

	@ManyToOne
	@JoinColumn(name="id_relatorio_desconto_beneficiario")
	private RelatorioDescontoBeneficiario relatorioDescontoBeneficiario;
	
	@ManyToOne
	@JoinColumn(name="id_item")
	private ItemGAB itemGab;

	public RelatorioDescontoBeneficiario getRelatorioDescontoBeneficiario() {
		return relatorioDescontoBeneficiario;
	}

	public void setRelatorioDescontoBeneficiario(RelatorioDescontoBeneficiario relatorioDescontoBeneficiario) {
		this.relatorioDescontoBeneficiario = relatorioDescontoBeneficiario;
	}

	public ItemGAB getItemGab() {
		return itemGab;
	}

	public void setItemGab(ItemGAB itemGab) {
		this.itemGab = itemGab;
	}
	
}
