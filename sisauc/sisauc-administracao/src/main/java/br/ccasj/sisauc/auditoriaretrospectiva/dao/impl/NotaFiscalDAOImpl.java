package br.ccasj.sisauc.auditoriaretrospectiva.dao.impl;

import javax.persistence.MappedSuperclass;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.auditoriaretrospectiva.dao.NotaFiscalDAO;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.NotaFiscal;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@MappedSuperclass
@Transactional
@Repository(value = "notaFiscalDAO")
public class NotaFiscalDAOImpl  extends GenericEntityDAOImpl<NotaFiscal> implements NotaFiscalDAO {

	private static final long serialVersionUID = -6762320950110382859L;

}
