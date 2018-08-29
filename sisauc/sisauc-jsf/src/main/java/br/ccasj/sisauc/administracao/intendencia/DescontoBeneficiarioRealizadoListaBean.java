package br.ccasj.sisauc.administracao.intendencia;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.intendencia.dao.DescontoBeneficiarioDAO;
import br.ccasj.sisauc.intendencia.domain.DescontoBeneficiario;

@Scope(value = "view")
@Service(value = "descontoBeneficiarioRealizadoListaBean")
public class DescontoBeneficiarioRealizadoListaBean implements Serializable {

	private static final long serialVersionUID = 2768364952350699166L;
	
	@Autowired
	private DescontoBeneficiarioDAO descontoBeneficiarioDAO;

	private List<DescontoBeneficiario> descontos;
	
	@PostConstruct
	private void init() {
		descontos = descontoBeneficiarioDAO.findAll();
	}

	public List<DescontoBeneficiario> getDescontos() {
		return descontos;
	}

	public void setDescontos(List<DescontoBeneficiario> descontos) {
		this.descontos = descontos;
	}
}
