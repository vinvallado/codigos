package br.ccasj.sisauc.auditoriaretrospectiva.dao;

import java.util.List;

import br.ccasj.sisauc.auditoriaretrospectiva.domain.Lote;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

public interface GerenciarLoteDAO extends GenericEntityDAO<Lote> {

	public int obterQuantidadeLotesPorOrganizacaoMilitarEAno(Integer idOm, Integer ano, boolean conformidade);

	public Lote obterLoteComItensById(Integer idLote);
	
	public Lote obterLoteParaRelatorioAnaliticoFatura(Integer idLote);
	
	public List<Lote> listarLotesVigentes();
	
}
