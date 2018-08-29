package br.ccasj.sisauc.administracao.beneficiario.dao;

import java.util.Date;

import br.ccasj.sisauc.administracao.beneficiario.domain.HistoricoSincronizacaoBeneficiario;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

public interface HistoricoSincronizacaoBeneficiarioDAO extends GenericEntityDAO<HistoricoSincronizacaoBeneficiario> {
	public Date obterDataUltimaSincronizacao();
}
