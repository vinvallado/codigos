package br.ccasj.sisauc.administracao.cadastro.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.EditalCredenciamentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.EditalCredenciamento;
import br.ccasj.sisauc.administracao.cadastro.service.CadastroEditalCredenciamentoService;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;

@Transactional
@Service(value = "cadastroEditalCredenciamentoService")
public class CadastroEditalCredenciamentoServiceImpl implements CadastroEditalCredenciamentoService {

	private static final long serialVersionUID = 1213682680581852539L;

	@Autowired
	private EditalCredenciamentoDAO editalCredenciamentoDAO;

	@Override
	public EditalCredenciamento salvar(EditalCredenciamento editalCredenciamento) {
		if (editalCredenciamentoDAO.verificarUnidadeEdital(editalCredenciamento.getId(), editalCredenciamento.getNumero())) {
			throw new SystemRuntimeException("Já existe um edital com esta numeração!");
		}
		if (editalCredenciamento.getInicio().after(editalCredenciamento.getFim())) {
			throw new SystemRuntimeException("A data de Início deve ser anterior a data de Fim.");
		}
		return editalCredenciamentoDAO.merge(editalCredenciamento);
	}

	@Override
	public void mudarStatusAtivoCredenciado(Integer id, boolean status) {
		EditalCredenciamento edital = editalCredenciamentoDAO.findById(id);
		edital.setAtivo(status);
		salvar(edital);
	}
}