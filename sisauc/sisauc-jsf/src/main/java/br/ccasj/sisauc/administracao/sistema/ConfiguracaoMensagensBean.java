package br.ccasj.sisauc.administracao.sistema;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.sistema.service.ConfiguracaoMensagensService;
import br.ccasj.sisauc.auditoriaretrospectiva.dao.MotivoDAO;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.Motivo;
import br.ccasj.sisauc.framework.faces.Mensagem;

@Scope(value = "view")
@Service(value = "configuracaoMensagensBean")
public class ConfiguracaoMensagensBean implements Serializable {

	private static final long serialVersionUID = -9103616877098399655L;

	@Autowired
	private MotivoDAO motivoDAO;
	@Autowired
	private ConfiguracaoMensagensService configuracaoMensagensService;

	private DualListModel<Motivo> dualList = new DualListModel<Motivo>();

	@PostConstruct
	private void init() {
		dualList.setSource(motivoDAO.obterNaoHabilitados());
		dualList.setTarget(motivoDAO.obterHabilitados());
	}

	public void salvar() {
		configuracaoMensagensService.salvar(dualList.getSource(), dualList.getTarget());
		Mensagem.informacao("Configuração salva com sucesso!");
	}

	public DualListModel<Motivo> getDualList() {
		return dualList;
	}

	public void setDualList(DualListModel<Motivo> dualList) {
		this.dualList = dualList;
	}

}
