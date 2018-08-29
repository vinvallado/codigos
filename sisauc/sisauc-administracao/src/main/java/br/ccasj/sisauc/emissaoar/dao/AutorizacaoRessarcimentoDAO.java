package br.ccasj.sisauc.emissaoar.dao;

import java.util.Date;
import java.util.List;

import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.EstadoAR;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;
import br.ccasj.sisauc.framework.domain.Usuario;

public interface AutorizacaoRessarcimentoDAO extends GenericEntityDAO<AutorizacaoRessarcimento>  {

	public int obterQuantidadeARsPorOrganizacaoMilitarEAno(Integer id, Integer ano);
	public List<AutorizacaoRessarcimento> obterARsPorOrganizacaoMilitar(Integer id);
	public AutorizacaoRessarcimento obterComItensPorId(Integer id);
	public void cancelarAR(Integer id, String justificativa);
	public AutorizacaoRessarcimento obterARPorAuditoriaProspectiva(Integer idAuditoria);
	public void atualizarDataAR(Date date, Integer id);
	public void atualizarEstadoAR(EstadoAR emitida, Integer id);
	public void atualizarUsuarioEmissor(Usuario usuario, Integer id);
	public List<AutorizacaoRessarcimento> obterARsPorAuditoriaProspectiva(Integer idAuditoria);
	public List<AutorizacaoRessarcimento> listarPorEstados(EstadoAR... emitida);
	public List<AutorizacaoRessarcimento> listarPorOMEEstados(OrganizacaoMilitar om, EstadoAR... emitida);
	public AutorizacaoRessarcimento obterPorIdParaAuditoriaRetrospectiva(Integer id);
	public List<AutorizacaoRessarcimento> obterAresPorCodigo(String codigo);
	public List<AutorizacaoRessarcimento> obterAresParaCancelamentoAuditoriaRestrospectiva(Integer idAuditoria);
	List<AutorizacaoRessarcimento> obterItensARESParaCancelamentoAuditoriaRetrospectiva();
	
	

}
