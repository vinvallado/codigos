package br.ccasj.sisauc.emissaogab.dao;

import java.util.Date;
import java.util.List;

import br.ccasj.sisauc.emissaogab.domain.EstadoGAB;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;
import br.ccasj.sisauc.framework.domain.Usuario;

public interface GuiaApresentacaoBeneficiarioDAO extends GenericEntityDAO<GuiaApresentacaoBeneficiario>  {

	public List<GuiaApresentacaoBeneficiario> obterGABsPorOrganizacaoMilitar(Integer idOm);
	public List<GuiaApresentacaoBeneficiario> obterGABsPorEstados(EstadoGAB... estadosGAB);
	public List<GuiaApresentacaoBeneficiario> obterGABsEmitidas();
	public List<GuiaApresentacaoBeneficiario> obterGABsApresentadasEEmAuditoria();
	public List<GuiaApresentacaoBeneficiario> obterGABsPorAuditoriaProspectiva(Integer idAuditoria);
	public GuiaApresentacaoBeneficiario obterComItensPorId(Integer id);
	public GuiaApresentacaoBeneficiario obterComItensMetadadosPorId(Integer id);
	public GuiaApresentacaoBeneficiario obterPeloItemGABId(Integer idItemGAB);
	public int obterQuantidadeGABSPorOrganizacaoMilitarEAno(Integer idOm, Integer ano);
	public void atualizarEstadoGAB(EstadoGAB estado, Integer id);
	public void atualizarUsuarioEmissor(Usuario usuario, Integer id);
	public void cancelarGAB(Integer id, String justificativa);
	public void atualizarDataGAB(Date data, Integer id);
	public void atualizarEstadoGABAoRealizarAuditoriaRetrospectiva(Integer idGAB);
	public List<GuiaApresentacaoBeneficiario> obterGabsPorCodigo(String codigo);
}
