package br.ccasj.sisauc.emissaoar.dao.impl;

import org.springframework.stereotype.Service;

import br.ccasj.sisauc.emissaoar.dao.ApresentacaoARDAO;
import br.ccasj.sisauc.emissaoar.domain.ApresentacaoAutorizacaoRessarcimento;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@Service("apresentacaoARDAO")
public class ApresentacaoARDAOImpl extends GenericEntityDAOImpl<ApresentacaoAutorizacaoRessarcimento> implements ApresentacaoARDAO{

	private static final long serialVersionUID = -6077048328721760964L;

}
