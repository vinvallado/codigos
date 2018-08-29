package br.ccasj.sisauc.auditoriaretrospectiva.dao;

import java.util.List;

import br.ccasj.sisauc.auditoriaretrospectiva.domain.LoteRessarcimento;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

public interface GerenciarLoteRessarcimentoDAO extends GenericEntityDAO<LoteRessarcimento> {

	public int obterQuantidadeLotesRessarcimentoPorOrganizacaoMilitarEAno(Integer idOm, Integer ano);

	public LoteRessarcimento obterLoteRessarcimentoComItensById(Integer idLoteRessarcimento);
	
	public LoteRessarcimento obterLoteRessarcimentoParaRelatorioAnaliticoFatura(Integer idLoteRessarcimento);
	
	public Long existeLoteDadoIdARE(Integer idAR);

	public List<String> obterCodigosLotesDeUmaArePeloId(Integer idARE);
	
}
