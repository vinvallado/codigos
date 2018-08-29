package br.ccasj.sisauc.intendencia.domain.service;

import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiario;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiarioItem;

import java.util.concurrent.Future;

import br.ccasj.sisauc.intendencia.domain.RelatorioAdapter;

public interface EnvioDescontosSisconsigService {

	public void enviarDescontosSisconsig();
	 
	public void salvarEstadoEnvioDescontoItem(RelatorioDescontoBeneficiarioItem item);
	
	public void salvarEstadoEnvioDesconto(RelatorioDescontoBeneficiario relatorio);
	
	//public void enviarRelatorioSisconsig();
	public Future<RelatorioAdapter> enviarRelatorioSisconsig();
	
	public void setRelatorioAdapterSisconsig(RelatorioAdapter relatorioVO);
	
}
