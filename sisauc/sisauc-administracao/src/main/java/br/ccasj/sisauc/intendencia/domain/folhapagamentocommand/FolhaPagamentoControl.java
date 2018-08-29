package br.ccasj.sisauc.intendencia.domain.folhapagamentocommand;

import java.net.ConnectException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.ccasj.sisauc.intendencia.domain.RelatorioAdapter;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiario;
import br.ccasj.sisauc.intendencia.domain.RelatorioFolhaBeneficiario;
import br.ccasj.sisauc.intendencia.domain.service.EnvioDescontosSisconsigService;

/**
 * Broker
 * 
 * @author amerson
 *
 */
public class FolhaPagamentoControl {

	private static final Logger LOGGER = LoggerFactory.getLogger(FolhaPagamentoControl.class);

	/**
	 * Aqui o Service tem o papel de "receiver".
	 * 
	 */
	private EnvioDescontosSisconsigService sisconsigService;

	private List<RelatorioAdapter> relatorios;

	public FolhaPagamentoControl(EnvioDescontosSisconsigService sisconsigService) {
		this.sisconsigService = sisconsigService;
	}

	public void setRelatoriosFolhaBeneficiario(List<RelatorioAdapter> relatorios) {
		this.relatorios = relatorios;
	}

	public void enviarRelatorios() {
		final Date DATA_START = new Date();
		try {
			System.out.println("Inicio envio ao SISCONSIG [" + DATA_START + "] > " + "Relatorios : "
					+ (relatorios != null ? relatorios.size() : "0"));

			for (RelatorioAdapter relatorio : relatorios) {
				RelatorioFolhaBeneficiario relatorioFolhaBeneficiario = relatorio.getRelatorioFolhaBeneficiario();
				if (relatorio.isDebito()) {
					System.out.println(">>>>> Envio ao SISCONSIG  > " + "Relatorio ["
							+ ((RelatorioDescontoBeneficiario) relatorioFolhaBeneficiario).getCodigo() + "] = "
							+ ((RelatorioDescontoBeneficiario) relatorioFolhaBeneficiario).getItens().size() + " Itens "
							+ (relatorios != null ? relatorios.size() : "0"));
					new DescontoFolhaCommand(relatorioFolhaBeneficiario, sisconsigService).execute();
				} else {
					new RessarcimentoFolhaCommand(relatorioFolhaBeneficiario, sisconsigService).execute();
				}
			}
		} catch (ConnectException e) {
			LOGGER.error("Erro fatal no envio ao SISCONSIG [" + DATA_START + "] > ", e.getMessage());
		}
	}

}
