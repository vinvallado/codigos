package br.ccasj.sisauc.administracao.cadastro.dao;

import java.util.List;

import br.ccasj.sisauc.administracao.cadastro.domain.Credenciado;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

public interface CredenciadoDAO extends GenericEntityDAO<Credenciado>{

	public Credenciado obterPorNomeCredenciado(String nomeCredenciado);
	public void definirStatusAtivoCredenciado(Integer id, boolean status);
	public List<Credenciado> listarAtivosParaCombo();
	boolean verificarUnicidadeCPF(Integer id, String cpf);
	boolean verificarUnicidadeCNPJ(Integer id, String cnpj);
	
}
