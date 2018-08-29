package br.ccasj.sisauc.administracao.cadastro.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.service.ConfiguracaoParametrosSistemaService;
import br.ccasj.sisauc.administracao.sistema.dao.ConfiguracaoSistemaDao;
import br.ccasj.sisauc.administracao.sistema.domain.ConfiguracaoSistema;

@Transactional
@Service(value = "configuracaoParametrosSistemaService")
public class ConfiguracaoParametrosSistemaServiceImpl implements ConfiguracaoParametrosSistemaService{
	
	private static final long serialVersionUID = 8850692819969872354L;
	
	@Autowired
	private ConfiguracaoSistemaDao configuracaoSistemaDao;
	
	public void salvar(ConfiguracaoSistema configuracaoSistema){
		configuracaoSistemaDao.merge(configuracaoSistema);
	}



}
