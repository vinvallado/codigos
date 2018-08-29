package br.ccasj.sisauc.administracao.cadastro.dao;

import java.util.List;

import br.ccasj.sisauc.administracao.cadastro.domain.Especialidade;
import br.ccasj.sisauc.administracao.cadastro.domain.TipoEspecialidade;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

public interface EspecialidadeDAO extends GenericEntityDAO<Especialidade> {

	public List<Especialidade> listarEspecialidadesAtivas();
	public List<Especialidade> listarEspecialidadesAtivasPorTipos(TipoEspecialidade... tipo);
	public List<Especialidade> listarEspecialidadesAtivasNaoOdontologicas();
	
}
