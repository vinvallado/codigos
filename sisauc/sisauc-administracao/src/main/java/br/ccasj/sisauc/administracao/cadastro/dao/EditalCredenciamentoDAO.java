package br.ccasj.sisauc.administracao.cadastro.dao;

import java.util.List;

import br.ccasj.sisauc.administracao.cadastro.domain.EditalCredenciamento;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

public interface EditalCredenciamentoDAO extends GenericEntityDAO<EditalCredenciamento> {

	public List<EditalCredenciamento> listarEditaisAtivos();
	public boolean verificarUnidadeEdital(Integer id, String numero);
	public boolean verificarEditalAtivoConcorrentePorData(EditalCredenciamento edital);

}
