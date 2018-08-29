package br.ccasj.sisauc.administracao.cadastro.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.EmpenhoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Empenho;
import br.ccasj.sisauc.administracao.cadastro.service.ControleEmpenhoService;
import br.ccasj.sisauc.administracao.historico.domain.HistoricoEmpenho;
import br.ccasj.sisauc.framework.dao.GenericEntityHistoricoDAO;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;

@Transactional
@Service(value = "controleEmpenhoService")
public class ControleEmpenhoServiceImpl implements ControleEmpenhoService {

	@Autowired
	private EmpenhoDAO empenhoDAO;

	@Autowired
	private GenericEntityHistoricoDAO<HistoricoEmpenho> genericEntityHistoricoDAO;

	@Override
	public Empenho salvar(Empenho empenho) {
		if (empenho.getDataCriacao().after(empenho.getDataLimite())) {
			throw new SystemRuntimeException("A data de criação deve ser anterior a data limite.");
		}
		Empenho empenhoSalvo = empenhoDAO.merge(empenho);
		genericEntityHistoricoDAO.merge(new HistoricoEmpenho(empenhoSalvo));
		return empenhoSalvo;
	}

}