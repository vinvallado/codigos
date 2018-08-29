package br.ccasj.sisauc.administracao.cadastro.dao.impl;

import javax.persistence.MappedSuperclass;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.administracao.cadastro.dao.ArquivoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Arquivo;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@MappedSuperclass
@Transactional
@Repository(value = "arquivoDAO")
public class ArquivoDAOImpl extends GenericEntityDAOImpl<Arquivo> implements ArquivoDAO {

	private static final long serialVersionUID = 6723093573767496767L;

}
