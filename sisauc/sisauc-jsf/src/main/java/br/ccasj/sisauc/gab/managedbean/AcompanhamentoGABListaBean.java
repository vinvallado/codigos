package br.ccasj.sisauc.gab.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.emissaogab.dao.GuiaApresentacaoBeneficiarioDAO;
import br.ccasj.sisauc.emissaogab.domain.EstadoGAB;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.framework.context.AuthenticationContext;

@Scope(value = "view")
@Service(value = "acompanhamentoGABListaBean")
public class AcompanhamentoGABListaBean implements Serializable{
	
	private static final long serialVersionUID = -3873702304852440488L;

	@Autowired
	private GuiaApresentacaoBeneficiarioDAO gabDAO;
	@Autowired
	private AuthenticationContext authenticationContext;
	
	private List<GuiaApresentacaoBeneficiario> gabs;
	private EstadoGAB filtroEstado = EstadoGAB.GERADA;
	
	@PostConstruct
	private void init(){
		gabs = gabDAO.obterGABsPorOrganizacaoMilitar(authenticationContext.getUsuarioLogado().getOrganizacaoMilitar().getId());
	}

	public List<GuiaApresentacaoBeneficiario> getGabs() {
		return gabs;
	}

	public void setGabs(List<GuiaApresentacaoBeneficiario> gabs) {
		this.gabs = gabs;
	}

	public EstadoGAB getFiltroEstado() {
		return filtroEstado;
	}

	public void setFiltroEstado(EstadoGAB filtroEstado) {
		this.filtroEstado = filtroEstado;
	}
	
	
	
}
