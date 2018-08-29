package br.ccasj.sisauc.auditoriaretrospectiva.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.ccasj.sisauc.emissaoar.domain.ItemAR;

@Embeddable
public class LoteRessarcimentoItensID implements Serializable{

	private static final long serialVersionUID = -754650082699805501L;

	@ManyToOne
	@JoinColumn(name="id_lote_ar")
	private LoteRessarcimento loteRessarcimento;
	
	@ManyToOne
	@JoinColumn(name="id_item_ar")
	private ItemAR itemAR;

	
	
	

}
