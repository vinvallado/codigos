package br.ccasj.sisauc.administracao.cadastro.managedbean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.CredenciadoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Credenciado;
import br.ccasj.sisauc.administracao.cadastro.domain.TipoPessoa;
import br.ccasj.sisauc.administracao.cadastro.service.CadastroCredenciadoService;
import br.ccasj.sisauc.framework.exception.SisaucException;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ApplicationBean;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;

@Scope(value = "view")
@Service(value = "cadastroCredenciadoFormularioBean")
public class CadastroCredenciadoFormularioBean implements Serializable{

	private static final long serialVersionUID = 2516859266152582950L;

	@Autowired
	private CredenciadoDAO credenciadoDAO;
	@Autowired
	private CadastroCredenciadoService cadastroCredenciadoService;
	
	private Credenciado credenciado;
	private List<TipoPessoa> tiposPessoa;
	
	@PostConstruct
	private void init() {
		credenciado = obterCredenciado();
		tiposPessoa = Arrays.asList(TipoPessoa.values());
	}

	private Credenciado obterCredenciado() {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		return id.equals("novo") ? new Credenciado() : credenciadoDAO.findById(Integer.valueOf(id));
	}
	
	public void salvar() throws SisaucException {
		try{
			cadastroCredenciadoService.salvar(credenciado) ;
			ManagedBeanUtils.obter("applicationBean", ApplicationBean.class).initConfiguracoes();
			Mensagem.informacao("Credenciado salvo com sucesso!");
			ManagedBeanUtils.redirecionar("/administracao/cadastro/credenciado");
		} catch (SisaucException e) {
			e.printStackTrace();
			Mensagem.erro(e.getMessage());
		}
	}
	
	public Credenciado getCredenciado() {
		return credenciado;
	}

	public void setCredenciado(Credenciado credenciado) {
		this.credenciado = credenciado;
	}

	public List<TipoPessoa> getTiposPessoa() {
		return tiposPessoa;
	}

	public void setTiposPessoa(List<TipoPessoa> tiposPessoa) {
		this.tiposPessoa = tiposPessoa;
	}
}
