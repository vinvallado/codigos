package br.ccasj.sisauc.administracao.cadastro.dao.impl;

import javax.persistence.MappedSuperclass;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.administracao.cadastro.dao.CodigoInternacionalDoencaDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.CodigoInternacionalDoenca;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@MappedSuperclass
@Transactional
@Repository(value = "codigoInternacionalDoencaDAO")
public class CodigoInternacionalDoencaDAOImpl extends GenericEntityDAOImpl<CodigoInternacionalDoenca> implements CodigoInternacionalDoencaDAO{

	private static final long serialVersionUID = 1352450908130171556L;
	
}
