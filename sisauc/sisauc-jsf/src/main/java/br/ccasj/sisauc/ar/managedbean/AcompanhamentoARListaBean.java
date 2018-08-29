package br.ccasj.sisauc.ar.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.emissaoar.dao.AutorizacaoRessarcimentoDAO;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.EstadoAR;
import br.ccasj.sisauc.framework.context.AuthenticationContext;

@Scope(value = "view")
@Service(value = "acompanhamentoARListaBean")
public class AcompanhamentoARListaBean implements Serializable{
	
	private static final long serialVersionUID = -3873702304852440488L;

	@Autowired
	private AutorizacaoRessarcimentoDAO arDAO;
	@Autowired
	private AuthenticationContext authenticationContext;
	
	private List<AutorizacaoRessarcimento> ars;
	private EstadoAR filtroEstado = EstadoAR.GERADA;
	
	@PostConstruct
	private void init(){
		ars = arDAO.obterARsPorOrganizacaoMilitar(authenticationContext.getUsuarioLogado().getOrganizacaoMilitar().getId());
	}

	public List<AutorizacaoRessarcimento> getArs() {
		return ars;
	}

	public void setArs(List<AutorizacaoRessarcimento> ars) {
		this.ars = ars;
	}

	public EstadoAR getFiltroEstado() {
		return filtroEstado;
	}

	public void setFiltroEstado(EstadoAR filtroEstado) {
		this.filtroEstado = filtroEstado;
	}

	
	
	
}
