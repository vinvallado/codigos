package br.ccasj.sisauc.administracao.auditoriaretrospectiva.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.emissaogab.dao.ItemGABDAO;
import br.ccasj.sisauc.emissaogab.domain.EstadoItemGAB;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;

@Scope(value = "view")
@Service(value = "auditoriaRetrospectivaListaBean")
public class AuditoriaRetrospectivaListaBean implements Serializable {

	private static final long serialVersionUID = 6743207483353567201L;

	private List<ItemGAB> listaItens;
		
	@Autowired
	private ItemGABDAO itemGABDAO;
	
	@PostConstruct
	private void init() {
		this.listaItens = itemGABDAO.obterItensGABParaAuditoriaRetrospectiva();
	}
	
	public List<ItemGAB> getListaItens() {
		return listaItens;
	}

	public void setListaItens(List<ItemGAB> listaItens) {
		this.listaItens = listaItens;
	}
	
	public boolean isEstadoNaoConforme(EstadoItemGAB estadoItemGAB) {
		return EstadoItemGAB.NAO_CONFORME == estadoItemGAB;
	}

	
}
