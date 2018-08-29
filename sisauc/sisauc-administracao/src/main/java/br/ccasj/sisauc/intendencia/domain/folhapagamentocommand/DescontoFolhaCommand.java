package br.ccasj.sisauc.intendencia.domain.folhapagamentocommand;

import java.net.ConnectException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.ccasj.sisauc.framework.utils.Wso2ResponseException;
import br.ccasj.sisauc.framework.utils.Wso2RestfulHelper;
import br.ccasj.sisauc.intendencia.domain.EstadoEnvioFolhaPagamento;
import br.ccasj.sisauc.intendencia.domain.EstadoRelatorioFolhaBeneficiario;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiario;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiarioItem;
import br.ccasj.sisauc.intendencia.domain.RelatorioFolhaBeneficiario;
import br.ccasj.sisauc.intendencia.domain.RelatorioFolhaBeneficiarioItem;
import br.ccasj.sisauc.intendencia.domain.service.EnvioDescontosSisconsigService;

public class DescontoFolhaCommand extends LancamentoFolhaCommand {

	private static final Logger LOGGER = LoggerFactory.getLogger(DescontoFolhaCommand.class);

	private RelatorioFolhaBeneficiario folhaBeneficiario;

	private EnvioDescontosSisconsigService service;

	public DescontoFolhaCommand(RelatorioFolhaBeneficiario folhaBeneficiario, EnvioDescontosSisconsigService service) {
		this.folhaBeneficiario = folhaBeneficiario;
		this.service = service;
	}

	@Override
	public void execute() throws ConnectException {
		try {
			RelatorioDescontoBeneficiario relatDesc = (RelatorioDescontoBeneficiario) folhaBeneficiario;
			boolean sucesso = true;
			Integer idDesconto = null;
			int numeroErros = 0;
			if (relatDesc.getDataInicioEnvio() == null) {
				relatDesc.setDataInicioEnvio(new Date());
			}
			for (RelatorioDescontoBeneficiarioItem item : relatDesc.getItens()) {
				if (item.getEstado() == null || !item.getEstado().equals(EstadoEnvioFolhaPagamento.ENVIADO)) {
					try {
						idDesconto = sendToSisconsig(item);
					} catch (Wso2ResponseException eWso2) {
						item.setCodigoErro(eWso2.getResponseErrorCode());
						item.setMensagemErro(eWso2.getMessage().substring(Math.min(180, eWso2.getMessage().length())));
						if (new Integer(500).equals(eWso2.getResponseErrorCode()))
							++numeroErros;
						if (numeroErros > 10)
							break;
					}
					if (idDesconto != null && idDesconto.intValue() >= 0) {
						item.setEstado(EstadoEnvioFolhaPagamento.ENVIADO);
						if (idDesconto.intValue() > 0) {
							item.setIdDescontoGerado(idDesconto);
						}
						item.setCodigoErro(null);
						item.setValorDescontoEnviado(item.getItemGab().getValorADescontar());
					} else {
						if (idDesconto == -1){
							item.setMensagemErro("Militar sem Órgão Sacada");
						}
						item.setEstado(EstadoEnvioFolhaPagamento.ERRO);
						sucesso = false;
					}
					service.salvarEstadoEnvioDescontoItem(item);
				}
			}
			if (sucesso) {
				relatDesc.setDataFinalizacaoEnvio(new Date());
				relatDesc.setEstadoEnvioFolha(EstadoRelatorioFolhaBeneficiario.ENVIADO);
			} else {
				relatDesc.setEstadoEnvioFolha(EstadoRelatorioFolhaBeneficiario.INTERROMPIDO_ERRO);
			}
			service.salvarEstadoEnvioDesconto(relatDesc);
		} catch (ConnectException e) {
			throw e;
		}
	}

	private Integer sendToSisconsig(RelatorioDescontoBeneficiarioItem item)
			throws ConnectException, Wso2ResponseException {
		System.out.println("Item enviado : " + ((RelatorioFolhaBeneficiarioItem) item).getCodigoExternoSisconsig() + " VALOR : " + item.getItemGab().getValorADescontar());
		final Map<String, Object> parametros = new HashMap<String, Object>();

		parametros.put("cdCaixa", "AD88");
		parametros.put("vlDesconto", item.getItemGab().getValorADescontar());
		parametros.put("nrCriadoPor", item.getRelatorioDescontoBeneficiario().getAutor().getCpf());
		parametros.put("tpDesconto", item.getRelatorioDescontoBeneficiario().isDebito() ? "0" : "1");
		parametros.put("sgCaixaServico", "FAMHS");
		parametros.put("cdOrgOperador", // item.getRelatorioDescontoBeneficiario().getAutor().getOrganizacaoMilitar().getSigla()
				"231402");
		parametros.put("tpAssistencia", item.getItemGab().getGab().getBeneficiario().isTitular() ? "0" : "1");
		parametros.put("nrOrdem",
				item.getItemGab().getGab().getBeneficiario().isTitular()
						? item.getItemGab().getGab().getBeneficiario().getSaram()
						: item.getItemGab().getGab().getBeneficiario().getBeneficiarioTitular().getSaram());
		parametros.put("tpFunsa", "AMHC");
		parametros.put("nmDependente", !item.getItemGab().getGab().getBeneficiario().isTitular()
				? item.getItemGab().getGab().getBeneficiario().getNome() : "");
		parametros.put("cdParentesco",
				!item.getItemGab().getGab().getBeneficiario().isTitular()
						? (item.getItemGab().getGab().getBeneficiario().getCdParentesco() == null ? ""
								: item.getItemGab().getGab().getBeneficiario().getCdParentesco())
						: "");
		parametros.put("cdExterno", ((RelatorioFolhaBeneficiarioItem) item).getCodigoExternoSisconsig());
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		final RetornoDesconto retorno = (RetornoDesconto) Wso2RestfulHelper.doPost(Wso2RestfulHelper.baseURIConsig,
//				"criarDescontoComBoletim/", "_postcriardescontocomboletim", parametros, RetornoDesconto.class);

//		if (retorno == null || retorno.getDescontoList() == null || retorno.getDescontoList().getDesconto() == null
//				|| retorno.getDescontoList().getDesconto().size() == 0
//				|| retorno.getDescontoList().getDesconto().get(0).getIdDesconto() == null) {
//			LOGGER.error("Response vazio para " + item.getItemGab().getCodigo() + " , " + item.getId());
//			return null;
//		} else {
			return new Integer(
					1000
					// retorno.getDescontoList().getDesconto().get(0).getIdDesconto()
					);
//		}
	}

}
