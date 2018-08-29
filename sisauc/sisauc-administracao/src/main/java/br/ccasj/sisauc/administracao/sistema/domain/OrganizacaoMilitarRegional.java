package br.ccasj.sisauc.administracao.sistema.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;

@Entity
@DiscriminatorValue("REGIONAL")
public class OrganizacaoMilitarRegional extends OrganizacaoMilitar {

	private static final long serialVersionUID = -8526785223502310491L;
	
	private boolean regionalSistema = false;
	private Set<OrganizacaoMilitarSubordinada> subordinadas;

	public OrganizacaoMilitarRegional() {
		super();
	}

	public OrganizacaoMilitarRegional(Integer id, String sigla, String nome) {
		super(id, sigla, nome);
	}
	
	@Column(name = "regional_sistema")
	public boolean isRegionalSistema() {
		return regionalSistema;
	}

	public void setRegionalSistema(boolean regionalSistema) {
		this.regionalSistema = regionalSistema;
	}

	@OneToMany(mappedBy = "regional")
	public Set<OrganizacaoMilitarSubordinada> getSubordinadas() {
		return subordinadas;
	}

	public void setSubordinadas(Set<OrganizacaoMilitarSubordinada> subordinadas) {
		this.subordinadas = subordinadas;
	}

}
