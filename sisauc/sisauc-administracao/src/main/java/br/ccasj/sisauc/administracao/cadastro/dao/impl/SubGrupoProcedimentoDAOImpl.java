package br.ccasj.sisauc.administracao.cadastro.dao.impl;

import javax.persistence.MappedSuperclass;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.administracao.cadastro.dao.SubGrupoProcedimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.SubGrupoProcedimento;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@MappedSuperclass
@Transactional
@Repository(value = "subGrupoProcedimentoDAO")
public class SubGrupoProcedimentoDAOImpl extends GenericEntityDAOImpl<SubGrupoProcedimento> implements SubGrupoProcedimentoDAO {

	private static final long serialVersionUID = -3610120567901638129L;

}
