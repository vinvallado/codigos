package br.ccasj.sisauc.administracao.sistema.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;

@Entity
@DiscriminatorValue("SUBORDINADA")
public class OrganizacaoMilitarSubordinada extends OrganizacaoMilitar {

	private static final long serialVersionUID = -5922374498192740063L;
	
	private OrganizacaoMilitarRegional regional;
	
	@ManyToOne
	@JoinColumn(name = "id_regional")
	public OrganizacaoMilitarRegional getRegional() {
		return regional;
	}

	public OrganizacaoMilitarSubordinada() {
		super();
	}

	public OrganizacaoMilitarSubordinada(OrganizacaoMilitarRegional regional) {
		super();
		this.regional = regional;
	}

	public OrganizacaoMilitarSubordinada(Integer id, String sigla, String nome) {
		super(id, sigla, nome);
	}

	public void setRegional(OrganizacaoMilitarRegional regional) {
		this.regional = regional;
	}
	
}
