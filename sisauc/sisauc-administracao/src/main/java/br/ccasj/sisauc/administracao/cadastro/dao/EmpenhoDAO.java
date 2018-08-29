package br.ccasj.sisauc.administracao.cadastro.dao;

import java.util.List;

import br.ccasj.sisauc.administracao.cadastro.domain.Empenho;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

public interface EmpenhoDAO  extends GenericEntityDAO<Empenho> {
	public List<Empenho> obterEmpenhos();
	public Empenho obterEmpenho(Integer id);
}
