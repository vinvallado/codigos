package br.ccasj.sisauc.framework.comparator;

import java.io.Serializable;
import java.util.Comparator;

import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;

public class OrganizacaoMilitarSiglaComparator implements Comparator<OrganizacaoMilitar>, Serializable {
	
	private static final long serialVersionUID = 5320056884236494235L;

	@Override
	public int compare(OrganizacaoMilitar om1, OrganizacaoMilitar om2) {
		return om1.getSigla().compareTo(om2.getSigla());
	}
}
