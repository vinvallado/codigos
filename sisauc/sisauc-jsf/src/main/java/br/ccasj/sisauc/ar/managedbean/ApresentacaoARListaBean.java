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

@Service("apresentacaoARListaBean")
@Scope("view")
public class ApresentacaoARListaBean implements Serializable {

	private static final long serialVersionUID = 2830770229153980192L;

	private List<AutorizacaoRessarcimento> listaARsParaApresentacao;
	
	@Autowired
	private transient AutorizacaoRessarcimentoDAO autorizacaoRessarcimentoDAO;
	@Autowired
	private AuthenticationContext authenticationContext;
	
	@PostConstruct
	public void init(){
		listaARsParaApresentacao = autorizacaoRessarcimentoDAO.listarPorOMEEstados(
				authenticationContext.getUsuarioLogado().getOrganizacaoMilitar(), 
				EstadoAR.EMITIDA, EstadoAR.INCONSISTENTE);
	}

	public List<AutorizacaoRessarcimento> getListaARsParaApresentacao() {
		return listaARsParaApresentacao;
	}

	public void setListaARsParaApresentacao(List<AutorizacaoRessarcimento> listaARsParaApresentacao) {
		this.listaARsParaApresentacao = listaARsParaApresentacao;
	}

}
