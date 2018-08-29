package br.ccasj.sisauc.administracao.cadastro.dao.impl;

import javax.persistence.MappedSuperclass;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.administracao.cadastro.dao.GrupoProcedimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.GrupoProcedimento;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@MappedSuperclass
@Transactional
@Repository(value = "grupoProcedimentoDAO")
public class GrupoProcedimentoDAOImpl extends GenericEntityDAOImpl<GrupoProcedimento> implements GrupoProcedimentoDAO {

	private static final long serialVersionUID = 4927546218402212758L;

}
