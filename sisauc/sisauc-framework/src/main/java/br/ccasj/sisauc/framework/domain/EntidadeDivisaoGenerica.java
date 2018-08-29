package br.ccasj.sisauc.framework.domain;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public class EntidadeDivisaoGenerica extends EntidadeGenerica {

	private static final long serialVersionUID = -8882316355366142314L;
	
	protected Divisao divisao = Divisao.DIVISAO_MEDICA;

	public EntidadeDivisaoGenerica() {
		super();
	}
	
	public EntidadeDivisaoGenerica(Integer id) {
		super(id);
	}	
	
	@NotNull
	@Enumerated(EnumType.STRING)
	public Divisao getDivisao() {
		return divisao;
	}

	public void setDivisao(Divisao divisao) {
		this.divisao = divisao;
	}

}
