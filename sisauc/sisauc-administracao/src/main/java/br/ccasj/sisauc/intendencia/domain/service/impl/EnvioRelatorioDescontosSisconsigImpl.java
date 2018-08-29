package br.ccasj.sisauc.intendencia.domain.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.ccasj.sisauc.intendencia.dao.RelatorioDescontoBeneficiarioDAO;
import br.ccasj.sisauc.intendencia.domain.EstadoRelatorioFolhaBeneficiario;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiario;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiarioItem;
import br.ccasj.sisauc.intendencia.domain.RelatorioFolhaBeneficiario;
import br.ccasj.sisauc.intendencia.domain.RelatorioAdapter;
import br.ccasj.sisauc.intendencia.domain.folhapagamentocommand.FolhaPagamentoControl;
import br.ccasj.sisauc.intendencia.domain.service.EnvioDescontosSisconsigService;


@Component("envioDescontosSisconsigService")

public class EnvioRelatorioDescontosSisconsigImpl implements EnvioDescontosSisconsigService {

	@Autowired 
	private RelatorioDescontoBeneficiarioDAO relatorioDescontoBeneficiarioDAO;

	private RelatorioAdapter relatorioAdapter;
	
	/**
	 * Busca o conjunto de Descontos e Ressarcimentos para enviar ao SISCONSIG
	 * e aciona o Control para gerar os respectivos commands.
	 * 
	 */
	@Override
	public void enviarDescontosSisconsig() {
		try {
			List<Integer> idsRelatoriosNovos = relatorioDescontoBeneficiarioDAO.obterIdsRelatoriosPelosEstadosDeEnvio(Arrays.asList( new EstadoRelatorioFolhaBeneficiario[] {EstadoRelatorioFolhaBeneficiario.INICIADO}));
			List<Integer> idsRelatoriosInterrompidos = relatorioDescontoBeneficiarioDAO.obterIdsRelatoriosPelosEstadosDeEnvio(Arrays.asList( new EstadoRelatorioFolhaBeneficiario[] {EstadoRelatorioFolhaBeneficiario.INTERROMPIDO_ERRO}));
			
			List<Integer> idsRelatoriosEnvio = new ArrayList<Integer>(idsRelatoriosNovos);
			idsRelatoriosEnvio.addAll(idsRelatoriosInterrompidos);
			List<RelatorioAdapter> relatorios = new ArrayList<RelatorioAdapter>();
			for (Integer id : idsRelatoriosEnvio) {
				RelatorioDescontoBeneficiario relatorio = relatorioDescontoBeneficiarioDAO.findById(id);
				relatorios.add(new RelatorioAdapter(relatorio));
			}
			FolhaPagamentoControl control = new FolhaPagamentoControl(this);
			control.setRelatoriosFolhaBeneficiario(relatorios);
			control.enviarRelatorios();
		} catch (RuntimeException e){
			e.printStackTrace();
		} finally {
			System.out.println("");
		}	
	}
	
	@Override
	public void setRelatorioAdapterSisconsig(RelatorioAdapter relatorioAdapter) {
		this.relatorioAdapter = relatorioAdapter;
		
	}
	
	
	//@Scheduled(cron="* * * * * *")
	@Override
	@Async
	public Future<RelatorioAdapter> enviarRelatorioSisconsig() {
		try {
			List<RelatorioAdapter> relatorios = new ArrayList<RelatorioAdapter>();
			RelatorioDescontoBeneficiario relatorio = relatorioDescontoBeneficiarioDAO.findById(this.relatorioAdapter.getRelatorioFolhaBeneficiario().getId());
			RelatorioAdapter relatorioAdapter = new RelatorioAdapter(relatorio);
			relatorios.add(relatorioAdapter);
			FolhaPagamentoControl control = new FolhaPagamentoControl(this);
			control.setRelatoriosFolhaBeneficiario(relatorios);
			control.enviarRelatorios();
		} catch (RuntimeException e){
			e.printStackTrace();
		} finally {
			System.out.println("");
		}
		return new AsyncResult<RelatorioAdapter>(relatorioAdapter);
	}
	
	public void salvarEstadoEnvioDescontoItem(RelatorioDescontoBeneficiarioItem relatorioItem){
		relatorioDescontoBeneficiarioDAO.atualizarEstadoRelatorioDescontoItem(relatorioItem);
	}
	
	public void salvarEstadoEnvioDesconto(RelatorioDescontoBeneficiario relatorio){
		relatorioDescontoBeneficiarioDAO.atualizarEnvioDesconto(relatorio);
	}

	
}
