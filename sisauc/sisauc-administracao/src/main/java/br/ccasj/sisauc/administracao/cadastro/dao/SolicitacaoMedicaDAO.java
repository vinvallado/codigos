package br.ccasj.sisauc.administracao.cadastro.dao;

import java.util.List;

import br.ccasj.sisauc.administracao.cadastro.domain.EstadoSolicitacaoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoMedica;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

public interface SolicitacaoMedicaDAO<T extends SolicitacaoMedica> extends GenericEntityDAO<SolicitacaoMedica> {
	
	@Override
	public T findById(Integer id);
	public T merge(T object);
	public List<T> listarPorEstado(EstadoSolicitacaoProcedimento... auditoriaProspectivas);
	public List<T> listarPorEstadoEOrganizacaoMilitar(Integer idOm, EstadoSolicitacaoProcedimento... auditoriaProspectivas);
	public T abrirComPedidos(Integer id);
	public void atualizarEstado(EstadoSolicitacaoProcedimento estado, Integer idSolicitacao);
	public List<T> buscarSolicitacaoPorSaram(String saram);
	public List<T> buscarSolicitacaoPorSaramTitular(String saram);
	public List<T> buscarSolicitacaoPorCpf(String cpf);
	public List<T> buscarSolicitacaoPorCpfTitular(String cpf);
	public List<T> buscarSolicitacaoPorCodigo(String codigo);
}
