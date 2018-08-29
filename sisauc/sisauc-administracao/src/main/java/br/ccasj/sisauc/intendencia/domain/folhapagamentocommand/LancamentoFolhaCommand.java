package br.ccasj.sisauc.intendencia.domain.folhapagamentocommand;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.ccasj.sisauc.framework.utils.Wso2ResponseException;
import br.ccasj.sisauc.framework.utils.Wso2RestfulHelper;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiarioItem;
import br.ccasj.sisauc.intendencia.domain.RelatorioFolhaBeneficiarioItem;

public abstract class LancamentoFolhaCommand {

	
	public abstract void execute() throws ConnectException ;
	
	
}
