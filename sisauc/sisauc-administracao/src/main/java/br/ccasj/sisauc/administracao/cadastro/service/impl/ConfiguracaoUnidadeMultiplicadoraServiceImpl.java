package br.ccasj.sisauc.administracao.cadastro.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.service.ConfiguracaoUnidadeMultiplicadoraService;
import br.ccasj.sisauc.administracao.historico.domain.HistoricoValorUnidadeMultiplicadora;
import br.ccasj.sisauc.administracao.sistema.dao.UnidadeMultiplicadoraDao;
import br.ccasj.sisauc.administracao.sistema.domain.UnidadeMultiplicadora;
import br.ccasj.sisauc.framework.dao.GenericEntityHistoricoDAO;

@Transactional
@Service(value = "configuracaoUnidadeMultiplicadoraService")
public class ConfiguracaoUnidadeMultiplicadoraServiceImpl implements ConfiguracaoUnidadeMultiplicadoraService{
	
	private static final long serialVersionUID = 8850692819969872354L;
	
	@Autowired
	private UnidadeMultiplicadoraDao unidadeMultiplicadoraDao;
	@Autowired
	private GenericEntityHistoricoDAO<HistoricoValorUnidadeMultiplicadora> genericEntityHistoricoDAO;
	
	public UnidadeMultiplicadora salvar(UnidadeMultiplicadora unidadeMultiplicadora){
		UnidadeMultiplicadora unidadeMultiplicadoraSalva = unidadeMultiplicadoraDao.merge(unidadeMultiplicadora);
		genericEntityHistoricoDAO.merge(new HistoricoValorUnidadeMultiplicadora(unidadeMultiplicadora.getValorFilme(), unidadeMultiplicadora.getValorUco(), unidadeMultiplicadora.getValorUsm(), unidadeMultiplicadoraSalva));
		return unidadeMultiplicadoraSalva;
	}

}
