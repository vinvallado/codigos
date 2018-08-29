package br.ccasj.sisauc.administracao.cadastro.dao;

import java.util.List;

import br.ccasj.sisauc.administracao.cadastro.domain.Especialidade;
import br.ccasj.sisauc.administracao.cadastro.domain.Medico;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

public interface MedicoDAO extends GenericEntityDAO<Medico> {

	public void definirStatusAtivoMedico(Integer id, boolean status);
	public List<Medico> listarAtivosParaCombo();
	public List<Especialidade> listarEspecialidadesPorIdMedico(Integer id);
	boolean verificarNumeroConselhoRegional(Integer id, String crm);

}